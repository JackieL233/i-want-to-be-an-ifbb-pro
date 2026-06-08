package com.iwanttobeanifbbpro.app.ui

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Base64
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.iwanttobeanifbbpro.app.core.CoachMode
import com.iwanttobeanifbbpro.app.core.DailySummaryBuilder
import com.iwanttobeanifbbpro.app.core.SkillAssetRepository
import com.iwanttobeanifbbpro.app.core.SkillPromptBuilder
import com.iwanttobeanifbbpro.app.data.AiReviewEntry
import com.iwanttobeanifbbpro.app.data.AiReviewStore
import com.iwanttobeanifbbpro.app.data.AthleteProfile
import com.iwanttobeanifbbpro.app.data.AthleteProfileStore
import com.iwanttobeanifbbpro.app.data.DailyLog
import com.iwanttobeanifbbpro.app.data.DailyLogStore
import com.iwanttobeanifbbpro.app.data.DailyMetrics
import com.iwanttobeanifbbpro.app.data.DailyTargets
import com.iwanttobeanifbbpro.app.data.ExerciseEntry
import com.iwanttobeanifbbpro.app.data.MealEntry
import com.iwanttobeanifbbpro.app.data.PlannedExercise
import com.iwanttobeanifbbpro.app.data.SetEntry
import com.iwanttobeanifbbpro.app.data.TrainingPlanStore
import com.iwanttobeanifbbpro.app.data.WeeklyTrainingPlan
import com.iwanttobeanifbbpro.app.health.HealthConnectRepository
import com.iwanttobeanifbbpro.app.health.HealthSnapshot
import com.iwanttobeanifbbpro.app.network.OpenAiResponsesClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.min

enum class AppTab(val title: String) {
    TODAY("Today"),
    PLAN("Plan"),
    TRAINING("Training"),
    NUTRITION("Nutrition"),
    METRICS("Metrics"),
    AI_COACH("AI Coach")
}

data class AiSettings(
    val baseUrl: String = "https://api.openai.com/v1",
    val apiKey: String = "",
    val model: String = "gpt-4.1-mini"
)

data class ImageAttachment(
    val name: String,
    val mimeType: String,
    val base64: String
)

data class RestTimerState(
    val exerciseName: String,
    val setNumber: Int,
    val remainingSeconds: Int,
    val totalSeconds: Int
)

data class CoachUiState(
    val selectedTab: AppTab = AppTab.TODAY,
    val mode: CoachMode = CoachMode.LINKED_TRAINING_NUTRITION,
    val userInput: String = CoachMode.LINKED_TRAINING_NUTRITION.defaultPrompt,
    val settings: AiSettings = AiSettings(),
    val dailyLog: DailyLog = DailyLog(),
    val recentLogs: List<DailyLog> = emptyList(),
    val athleteProfile: AthleteProfile = AthleteProfile(),
    val trainingPlan: WeeklyTrainingPlan = WeeklyTrainingPlan(),
    val selectedPlanDayIndex: Int = 0,
    val restTimer: RestTimerState? = null,
    val healthSnapshot: HealthSnapshot = HealthSnapshot(message = "Health Connect not checked yet."),
    val isHealthSyncing: Boolean = false,
    val reviewHistory: List<AiReviewEntry> = emptyList(),
    val images: List<ImageAttachment> = emptyList(),
    val result: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)

class CoachViewModel(application: Application) : AndroidViewModel(application) {
    private val settingsStore = SettingsStore(application)
    private val dailyLogStore = DailyLogStore(application)
    private val trainingPlanStore = TrainingPlanStore(application)
    private val aiReviewStore = AiReviewStore(application)
    private val athleteProfileStore = AthleteProfileStore(application)
    private val dailySummaryBuilder = DailySummaryBuilder()
    private val promptBuilder = SkillPromptBuilder(SkillAssetRepository(application))
    private val apiClient = OpenAiResponsesClient()
    private val healthRepository = HealthConnectRepository(application)
    private var restTimerJob: Job? = null

    var uiState by mutableStateOf(
        CoachUiState(
            settings = settingsStore.load(),
            dailyLog = dailyLogStore.readLog(),
            recentLogs = dailyLogStore.readRecentLogs(),
            athleteProfile = athleteProfileStore.readProfile(),
            trainingPlan = trainingPlanStore.readPlan(),
            reviewHistory = aiReviewStore.readReviews()
        )
    )
        private set

    fun selectTab(tab: AppTab) {
        uiState = uiState.copy(selectedTab = tab)
    }

    fun updateMode(mode: CoachMode) {
        uiState = uiState.copy(mode = mode, userInput = mode.defaultPrompt)
    }

    fun updateUserInput(value: String) {
        uiState = uiState.copy(userInput = value)
    }

    fun updateSettings(settings: AiSettings) {
        settingsStore.save(settings)
        uiState = uiState.copy(settings = settings)
    }

    fun updateAthleteProfile(profile: AthleteProfile) {
        athleteProfileStore.saveProfile(profile)
        uiState = uiState.copy(athleteProfile = profile)
    }

    fun selectPlanDay(index: Int) {
        val safeIndex = index.coerceIn(0, (uiState.trainingPlan.days.size - 1).coerceAtLeast(0))
        uiState = uiState.copy(selectedPlanDayIndex = safeIndex)
    }

    fun updateTrainingPlanName(name: String) {
        updatePlan(uiState.trainingPlan.copy(name = name))
    }

    fun updateTrainingPlanGoal(goal: String) {
        updatePlan(uiState.trainingPlan.copy(phaseGoal = goal))
    }

    fun updateTrainingDay(index: Int, focus: String, notes: String) {
        val days = uiState.trainingPlan.days.mapIndexed { dayIndex, day ->
            if (dayIndex == index) day.copy(focus = focus, notes = notes) else day
        }
        updatePlan(uiState.trainingPlan.copy(days = days))
    }

    fun addPlannedExercise(
        dayIndex: Int,
        name: String,
        targetMuscle: String,
        sets: Int,
        reps: String,
        loadKg: Double?,
        rir: Double?,
        restSeconds: Int,
        notes: String
    ) {
        if (name.isBlank()) return
        val exercise = PlannedExercise(
            name = name.trim(),
            targetMuscle = targetMuscle.trim(),
            sets = sets.coerceAtLeast(0),
            reps = reps.trim().ifBlank { "8-12" },
            loadKg = loadKg,
            rir = rir,
            restSeconds = restSeconds.coerceIn(30, 600),
            notes = notes.trim()
        )
        val days = uiState.trainingPlan.days.mapIndexed { index, day ->
            if (index == dayIndex) day.copy(exercises = day.exercises + exercise) else day
        }
        updatePlan(uiState.trainingPlan.copy(days = days))
    }

    fun removePlannedExercise(dayIndex: Int, exerciseIndex: Int) {
        val days = uiState.trainingPlan.days.mapIndexed { index, day ->
            if (index == dayIndex) {
                day.copy(exercises = day.exercises.filterIndexed { itemIndex, _ -> itemIndex != exerciseIndex })
            } else {
                day
            }
        }
        updatePlan(uiState.trainingPlan.copy(days = days))
    }

    fun applyPlanDayToToday(dayIndex: Int) {
        val day = uiState.trainingPlan.days.getOrNull(dayIndex) ?: return
        val session = uiState.dailyLog.trainingSession.copy(
            plannedFocus = day.focus.ifBlank { day.dayName },
            completed = false,
            exercises = day.exercises.map { it.toExerciseEntry() },
            sessionNotes = day.notes
        )
        updateLog(uiState.dailyLog.copy(trainingSession = session))
        uiState = uiState.copy(selectedTab = AppTab.TRAINING)
    }

    fun resetTrainingPlan() {
        trainingPlanStore.resetPlan()
        uiState = uiState.copy(trainingPlan = trainingPlanStore.readPlan(), selectedPlanDayIndex = 0)
    }

    fun updateTargets(targets: DailyTargets) {
        updateLog(uiState.dailyLog.copy(targets = targets))
    }

    fun updateMetrics(metrics: DailyMetrics) {
        updateLog(uiState.dailyLog.copy(metrics = metrics))
    }

    fun healthPermissions(): Set<String> = healthRepository.permissions()

    fun refreshHealthStatus() {
        uiState = uiState.copy(isHealthSyncing = true)
        viewModelScope.launch {
            val snapshot = withContext(Dispatchers.IO) { healthRepository.permissionSnapshot() }
            uiState = uiState.copy(healthSnapshot = snapshot, isHealthSyncing = false)
        }
    }

    fun onHealthPermissionsResult(granted: Set<String>) {
        val permissionsGranted = healthRepository.permissions().all { it in granted }
        uiState = uiState.copy(
            healthSnapshot = uiState.healthSnapshot.copy(
                available = true,
                permissionsGranted = permissionsGranted,
                message = if (permissionsGranted) {
                    "Health Connect permissions granted. Sync today's metrics next."
                } else {
                    "Some Health Connect permissions were not granted; manual metrics still work."
                }
            )
        )
        if (permissionsGranted) {
            syncHealthData()
        }
    }

    fun syncHealthData() {
        uiState = uiState.copy(isHealthSyncing = true)
        viewModelScope.launch {
            val snapshot = withContext(Dispatchers.IO) { healthRepository.readTodaySnapshot() }
            val log = if (snapshot.hasImportableMetrics()) {
                uiState.dailyLog.copy(metrics = uiState.dailyLog.metrics.mergeHealthSnapshot(snapshot))
            } else {
                uiState.dailyLog
            }
            if (snapshot.hasImportableMetrics()) {
                dailyLogStore.saveLog(log)
            }
            uiState = uiState.copy(
                dailyLog = log,
                recentLogs = dailyLogStore.readRecentLogs(),
                healthSnapshot = snapshot,
                isHealthSyncing = false
            )
        }
    }

    fun updateTrainingFocus(focus: String) {
        val session = uiState.dailyLog.trainingSession.copy(plannedFocus = focus)
        updateLog(uiState.dailyLog.copy(trainingSession = session))
    }

    fun updateSessionNotes(notes: String) {
        val session = uiState.dailyLog.trainingSession.copy(sessionNotes = notes)
        updateLog(uiState.dailyLog.copy(trainingSession = session))
    }

    fun toggleTrainingCompleted() {
        val session = uiState.dailyLog.trainingSession
        updateLog(uiState.dailyLog.copy(trainingSession = session.copy(completed = !session.completed)))
    }

    fun addExercise(
        name: String,
        targetMuscle: String,
        sets: Int,
        reps: String,
        loadKg: Double?,
        rir: Double?,
        notes: String,
        restSeconds: Int
    ) {
        if (name.isBlank()) return
        val plannedSets = sets.coerceAtLeast(0)
        val entry = ExerciseEntry(
            name = name.trim(),
            targetMuscle = targetMuscle.trim(),
            sets = plannedSets,
            reps = reps.trim(),
            loadKg = loadKg,
            rir = rir,
            notes = notes.trim(),
            restSeconds = restSeconds.coerceIn(30, 600),
            setEntries = (1..plannedSets).map { setNumber ->
                SetEntry(
                    setNumber = setNumber,
                    targetReps = reps.trim(),
                    actualReps = null,
                    loadKg = loadKg,
                    rir = rir,
                    restSeconds = restSeconds.coerceIn(30, 600)
                )
            }
        )
        val session = uiState.dailyLog.trainingSession
        updateLog(uiState.dailyLog.copy(trainingSession = session.copy(exercises = session.exercises + entry)))
    }

    fun removeExercise(index: Int) {
        val session = uiState.dailyLog.trainingSession
        val exercises = session.exercises.filterIndexed { itemIndex, _ -> itemIndex != index }
        updateLog(uiState.dailyLog.copy(trainingSession = session.copy(exercises = exercises)))
    }

    fun updateSetEntry(
        exerciseIndex: Int,
        setIndex: Int,
        actualReps: Int?,
        loadKg: Double?,
        rir: Double?,
        notes: String
    ) {
        updateExercise(exerciseIndex) { exercise ->
            val sets = exercise.trackedSets().mapIndexed { index, set ->
                if (index == setIndex) {
                    set.copy(
                        actualReps = actualReps?.coerceAtLeast(0),
                        loadKg = loadKg?.coerceAtLeast(0.0),
                        rir = rir?.coerceIn(0.0, 10.0),
                        notes = notes.trim()
                    )
                } else {
                    set
                }
            }
            exercise.copy(setEntries = sets)
        }
    }

    fun completeSet(exerciseIndex: Int, setIndex: Int) {
        val exercise = uiState.dailyLog.trainingSession.exercises.getOrNull(exerciseIndex) ?: return
        val targetSet = exercise.trackedSets().getOrNull(setIndex) ?: return
        updateExercise(exerciseIndex) { current ->
            current.copy(
                setEntries = current.trackedSets().mapIndexed { index, set ->
                    if (index == setIndex) set.copy(completed = true) else set
                }
            )
        }
        startRestTimer(exercise.name, targetSet.setNumber, targetSet.restSeconds)
    }

    fun skipRestTimer() {
        restTimerJob?.cancel()
        restTimerJob = null
        uiState = uiState.copy(restTimer = null)
    }

    fun addMeal(
        name: String,
        calories: Int,
        protein: Double,
        carbs: Double,
        fat: Double,
        fiber: Double,
        notes: String
    ) {
        if (name.isBlank()) return
        val meal = MealEntry(
            name = name.trim(),
            calories = calories.coerceAtLeast(0),
            protein = protein.coerceAtLeast(0.0),
            carbs = carbs.coerceAtLeast(0.0),
            fat = fat.coerceAtLeast(0.0),
            fiber = fiber.coerceAtLeast(0.0),
            notes = notes.trim()
        )
        updateLog(uiState.dailyLog.copy(meals = uiState.dailyLog.meals + meal))
    }

    fun removeMeal(index: Int) {
        updateLog(uiState.dailyLog.copy(meals = uiState.dailyLog.meals.filterIndexed { itemIndex, _ -> itemIndex != index }))
    }

    fun updateReflection(reflection: String) {
        updateLog(uiState.dailyLog.copy(reflection = reflection))
    }

    fun resetToday() {
        skipRestTimer()
        dailyLogStore.resetToday()
        uiState = uiState.copy(
            dailyLog = dailyLogStore.readLog(),
            recentLogs = dailyLogStore.readRecentLogs(),
            result = "",
            error = null
        )
    }

    fun addImages(uris: List<Uri>) {
        viewModelScope.launch {
            val resolver = getApplication<Application>().contentResolver
            val attachments = withContext(Dispatchers.IO) {
                uris.mapNotNull { uri -> resolver.toImageAttachment(uri) }
            }
            uiState = uiState.copy(images = (uiState.images + attachments).takeLast(6))
        }
    }

    fun clearImages() {
        uiState = uiState.copy(images = emptyList())
    }

    fun runAnalysis() {
        runAi(mode = uiState.mode, request = uiState.userInput)
    }

    fun runDailyReview() {
        val context = dailySummaryBuilder.buildAiReviewContext(
            log = uiState.dailyLog,
            recentLogs = uiState.recentLogs,
            profile = uiState.athleteProfile,
            plan = uiState.trainingPlan,
            extraRequest = uiState.userInput
        )
        runAi(mode = CoachMode.CHECK_IN, request = context)
    }

    private fun runAi(mode: CoachMode, request: String) {
        val current = uiState
        uiState = current.copy(isLoading = true, error = null, result = "")
        viewModelScope.launch {
            val instructions = promptBuilder.buildInstructions(mode)
            val prompt = promptBuilder.buildUserPrompt(mode, request)
            val result = apiClient.analyze(
                settings = current.settings,
                instructions = instructions,
                prompt = prompt,
                images = current.images
            )
            uiState = result.fold(
                onSuccess = { body ->
                    val entry = AiReviewEntry(
                        logDate = current.dailyLog.date,
                        modeTitle = mode.title,
                        body = body
                    )
                    aiReviewStore.saveReview(entry)
                    uiState.copy(
                        isLoading = false,
                        result = body,
                        reviewHistory = aiReviewStore.readReviews(),
                        error = null
                    )
                },
                onFailure = { uiState.copy(isLoading = false, result = "", error = it.message) }
            )
        }
    }

    private fun updateLog(log: DailyLog) {
        dailyLogStore.saveLog(log)
        uiState = uiState.copy(dailyLog = log, recentLogs = dailyLogStore.readRecentLogs())
    }

    private fun updatePlan(plan: WeeklyTrainingPlan) {
        trainingPlanStore.savePlan(plan)
        uiState = uiState.copy(trainingPlan = plan)
    }

    private fun updateExercise(index: Int, transform: (ExerciseEntry) -> ExerciseEntry) {
        val session = uiState.dailyLog.trainingSession
        val exercises = session.exercises.mapIndexed { exerciseIndex, exercise ->
            if (exerciseIndex == index) transform(exercise) else exercise
        }
        updateLog(uiState.dailyLog.copy(trainingSession = session.copy(exercises = exercises)))
    }

    private fun startRestTimer(exerciseName: String, setNumber: Int, totalSeconds: Int) {
        restTimerJob?.cancel()
        restTimerJob = viewModelScope.launch {
            val clampedTotal = totalSeconds.coerceIn(30, 600)
            for (remaining in clampedTotal downTo 0) {
                uiState = uiState.copy(
                    restTimer = RestTimerState(
                        exerciseName = exerciseName,
                        setNumber = setNumber,
                        remainingSeconds = remaining,
                        totalSeconds = clampedTotal
                    )
                )
                delay(1000)
            }
            uiState = uiState.copy(restTimer = null)
        }
    }
}

private fun DailyMetrics.mergeHealthSnapshot(snapshot: HealthSnapshot): DailyMetrics {
    return copy(
        bodyWeightKg = snapshot.bodyWeightKg ?: bodyWeightKg,
        bodyFatPercent = snapshot.bodyFatPercent ?: bodyFatPercent,
        leanBodyMassKg = snapshot.leanBodyMassKg ?: leanBodyMassKg,
        sleepHours = snapshot.sleepHours ?: sleepHours,
        steps = snapshot.steps?.let { min(it, Int.MAX_VALUE.toLong()).toInt() } ?: steps,
        restingHeartRateBpm = snapshot.restingHeartRateBpm ?: restingHeartRateBpm,
        totalCaloriesBurnedKcal = snapshot.totalCaloriesBurnedKcal ?: totalCaloriesBurnedKcal,
        healthDataSource = snapshot.source,
        healthSyncedAt = snapshot.syncedAt
    )
}

private class SettingsStore(context: Context) {
    private val prefs = context.getSharedPreferences("ai_settings", Context.MODE_PRIVATE)

    fun load(): AiSettings {
        return AiSettings(
            baseUrl = prefs.getString("base_url", AiSettings().baseUrl) ?: AiSettings().baseUrl,
            apiKey = prefs.getString("api_key", "") ?: "",
            model = prefs.getString("model", AiSettings().model) ?: AiSettings().model
        )
    }

    fun save(settings: AiSettings) {
        prefs.edit()
            .putString("base_url", settings.baseUrl)
            .putString("api_key", settings.apiKey)
            .putString("model", settings.model)
            .apply()
    }
}

private fun ContentResolver.toImageAttachment(uri: Uri): ImageAttachment? {
    val mimeType = getType(uri) ?: "image/jpeg"
    val name = query(uri, null, null, null, null)?.use { cursor ->
        val index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        if (index >= 0 && cursor.moveToFirst()) cursor.getString(index) else uri.lastPathSegment
    } ?: uri.lastPathSegment ?: "image"

    val bytes = openInputStream(uri)?.use { it.readBytes() } ?: return null
    return ImageAttachment(
        name = name,
        mimeType = mimeType,
        base64 = Base64.encodeToString(bytes, Base64.NO_WRAP)
    )
}
