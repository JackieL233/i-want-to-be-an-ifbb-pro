package com.iwanttobeanifbbpro.app.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.iwanttobeanifbbpro.app.core.BodyCompositionGuidance
import com.iwanttobeanifbbpro.app.core.CoachMode
import com.iwanttobeanifbbpro.app.core.ExerciseHistorySummary
import com.iwanttobeanifbbpro.app.core.ExerciseVisualSpec
import com.iwanttobeanifbbpro.app.core.ExerciseVisualType
import com.iwanttobeanifbbpro.app.core.ProgressionCue
import com.iwanttobeanifbbpro.app.core.RecoveryGuidance
import com.iwanttobeanifbbpro.app.core.TrainingReadinessBuilder
import com.iwanttobeanifbbpro.app.core.bodyCompositionGuidance
import com.iwanttobeanifbbpro.app.core.exerciseVisualLibrarySpecs
import com.iwanttobeanifbbpro.app.core.exerciseVisualSpec
import com.iwanttobeanifbbpro.app.core.exerciseHistorySummary
import com.iwanttobeanifbbpro.app.core.progressionCue
import com.iwanttobeanifbbpro.app.core.recoveryGuidance
import com.iwanttobeanifbbpro.app.core.trainingReadinessBuilder
import com.iwanttobeanifbbpro.app.data.AiReviewEntry
import com.iwanttobeanifbbpro.app.data.AthleteProfile
import com.iwanttobeanifbbpro.app.data.DailyLog
import com.iwanttobeanifbbpro.app.data.DailyMetrics
import com.iwanttobeanifbbpro.app.data.DailyTargets
import com.iwanttobeanifbbpro.app.data.ExerciseEntry
import com.iwanttobeanifbbpro.app.data.MealTemplate
import com.iwanttobeanifbbpro.app.data.PlannedExercise
import com.iwanttobeanifbbpro.app.data.SetEntry
import com.iwanttobeanifbbpro.app.data.TrainingPlanTemplate
import com.iwanttobeanifbbpro.app.data.TrainingDay
import com.iwanttobeanifbbpro.app.data.mealTemplates
import com.iwanttobeanifbbpro.app.data.trainingPlanTemplates
import com.iwanttobeanifbbpro.app.health.HealthConnectRepository
import java.util.Locale

@Composable
fun IfbbProCoachApp(viewModel: CoachViewModel = viewModel()) {
    val state = viewModel.uiState
    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(6),
        onResult = viewModel::addImages
    )
    val healthPermissionLauncher = rememberLauncherForActivityResult(
        contract = HealthConnectRepository.permissionContract(),
        onResult = viewModel::onHealthPermissionsResult
    )

    LaunchedEffect(Unit) {
        viewModel.refreshHealthStatus()
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            BottomNavigation(
                selected = state.selectedTab,
                onSelect = viewModel::selectTab
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(padding),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item { Header(state = state) }
            state.restTimer?.let { timer ->
                item { RestTimerBanner(timer = timer, onSkip = viewModel::skipRestTimer) }
            }
            when (state.selectedTab) {
                AppTab.TODAY -> {
                    item {
                        TodayDashboard(
                            state = state,
                            onDailyReview = viewModel::runDailyReview,
                            onReset = viewModel::resetToday,
                            onOpenPlan = { viewModel.selectTab(AppTab.PLAN) },
                            onOpenTraining = { viewModel.selectTab(AppTab.TRAINING) },
                            onOpenNutrition = { viewModel.selectTab(AppTab.NUTRITION) },
                            onOpenMetrics = { viewModel.selectTab(AppTab.METRICS) },
                            onOpenAi = { viewModel.selectTab(AppTab.AI_COACH) }
                        )
                    }
                }

                AppTab.PLAN -> {
                    item {
                        PlanPage(
                            state = state,
                            onProfileChange = viewModel::updateAthleteProfile,
                            onNameChange = viewModel::updateTrainingPlanName,
                            onGoalChange = viewModel::updateTrainingPlanGoal,
                            onSelectDay = viewModel::selectPlanDay,
                            onUpdateDay = viewModel::updateTrainingDay,
                            onAddPlannedExercise = viewModel::addPlannedExercise,
                            onRemovePlannedExercise = viewModel::removePlannedExercise,
                            onApplyDay = viewModel::applyPlanDayToToday,
                            onApplyTemplate = viewModel::applyTrainingPlanTemplate,
                            onResetPlan = viewModel::resetTrainingPlan
                        )
                    }
                }

                AppTab.TRAINING -> {
                    item {
                        TrainingPage(
                            state = state,
                            onFocusChange = viewModel::updateTrainingFocus,
                            onNotesChange = viewModel::updateSessionNotes,
                            onCompletedChange = { viewModel.toggleTrainingCompleted() },
                            onAddExercise = viewModel::addExercise,
                            onRemoveExercise = viewModel::removeExercise,
                            onUpdateSetEntry = viewModel::updateSetEntry,
                            onCompleteSet = viewModel::completeSet
                        )
                    }
                }

                AppTab.NUTRITION -> {
                    item {
                        NutritionPage(
                            state = state,
                            onTargetsChange = viewModel::updateTargets,
                            onAddMeal = viewModel::addMeal,
                            onAddMealTemplate = viewModel::addMealTemplate,
                            onRemoveMeal = viewModel::removeMeal,
                            onPickMealPhoto = {
                                imagePicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                            }
                        )
                    }
                }

                AppTab.METRICS -> {
                    item {
                        MetricsPage(
                            state = state,
                            onMetricsChange = viewModel::updateMetrics,
                            onReflectionChange = viewModel::updateReflection,
                            onConnectHealthData = { healthPermissionLauncher.launch(viewModel.healthPermissions()) },
                            onSyncHealthData = viewModel::syncHealthData
                        )
                    }
                }

                AppTab.AI_COACH -> {
                    item {
                        AiCoachPage(
                            state = state,
                            onSettingsChange = viewModel::updateSettings,
                            onModeChange = viewModel::updateMode,
                            onPromptChange = viewModel::updateUserInput,
                            onPickImages = {
                                imagePicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                            },
                            onClearImages = viewModel::clearImages,
                            onRunAnalysis = viewModel::runAnalysis,
                            onDailyReview = viewModel::runDailyReview
                        )
                    }
                }
            }
            item {
                state.error?.let { MessageCard(title = "Error", body = it) }
                if (state.result.isNotBlank()) {
                    MessageCard(title = "AI Coach result", body = state.result)
                }
                SafetyNote()
            }
        }
    }
}

@Composable
private fun Header(state: CoachUiState) {
    Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
        Text(
            text = state.selectedTab.title,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = "I Want to be an IFBB PRO",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Daily bodybuilding execution: training, food, recovery, photos, and AI adjustments in one log.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun BottomNavigation(selected: AppTab, onSelect: (AppTab) -> Unit) {
    NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
        AppTab.entries.forEach { tab ->
            NavigationBarItem(
                selected = selected == tab,
                onClick = { onSelect(tab) },
                icon = { Text(tab.navIcon()) },
                label = { Text(tab.shortTitle()) }
            )
        }
    }
}

private fun AppTab.navIcon(): String {
    return when (this) {
        AppTab.TODAY -> "T"
        AppTab.PLAN -> "P"
        AppTab.TRAINING -> "+"
        AppTab.NUTRITION -> "N"
        AppTab.METRICS -> "M"
        AppTab.AI_COACH -> "AI"
    }
}

private fun AppTab.shortTitle(): String {
    return when (this) {
        AppTab.AI_COACH -> "Coach"
        else -> title
    }
}

private data class DailyReadiness(
    val score: Int,
    val label: String,
    val nextAction: String
)

private data class NutritionPacing(
    val caloriesRemaining: Int,
    val proteinRemaining: Int,
    val carbsRemaining: Int,
    val fatRemaining: Int,
    val fiberRemaining: Int,
    val adherenceScore: Int,
    val statusLabel: String,
    val nextMealFocus: String
)

private data class DailyCoachTask(
    val title: String,
    val detail: String,
    val done: Boolean,
    val actionLabel: String,
    val actionEnabled: Boolean = true,
    val onAction: () -> Unit
)

private data class NextMealBuilder(
    val title: String,
    val summary: String,
    val proteinGrams: Int,
    val carbsGrams: Int,
    val fatGrams: Int,
    val fiberGrams: Int,
    val timingCue: String,
    val portionCue: String,
    val photoCue: String
)

private fun DailyLog.nutritionPacing(): NutritionPacing {
    val totals = nutritionTotals()
    val caloriesRemaining = targets.calories - totals.calories
    val proteinRemaining = targets.protein - totals.protein
    val carbsRemaining = targets.carbs - totals.carbs
    val fatRemaining = targets.fat - totals.fat
    val fiberRemaining = targets.fiber - totals.fiber
    val targetSum = listOf(targets.calories, targets.protein, targets.carbs, targets.fat, targets.fiber)
        .sumOf { it.coerceAtLeast(1) }
        .toDouble()
    val missSum = listOf(caloriesRemaining, proteinRemaining, carbsRemaining, fatRemaining, fiberRemaining)
        .sumOf { kotlin.math.abs(it).coerceAtMost(400) }
        .toDouble()
    val adherenceScore = (100 - (missSum / targetSum * 100)).toInt().coerceIn(0, 100)
    val statusLabel = when {
        caloriesRemaining < -150 || fatRemaining < -15 -> "Over target"
        proteinRemaining > 30 -> "Protein behind"
        carbsRemaining > 80 -> "Carbs available"
        fiberRemaining > 10 -> "Fiber behind"
        adherenceScore >= 88 -> "On pace"
        else -> "Needs logging"
    }
    val nextMealFocus = when {
        caloriesRemaining < -150 -> "Keep the next meal lean and mostly protein/vegetables."
        proteinRemaining > 35 -> "Prioritize a lean protein serving before adding extra fats."
        carbsRemaining > 90 -> "Place carbs around training or the next high-output window."
        fiberRemaining > 10 -> "Add fruit, vegetables, oats, beans, or another high-fiber carb."
        fatRemaining < -10 -> "Keep fats low for the rest of the day."
        meals.isEmpty() -> "Log the first meal or attach a food photo for portion estimation."
        else -> "Stay close to targets; use photos only where portions are uncertain."
    }
    return NutritionPacing(
        caloriesRemaining = caloriesRemaining,
        proteinRemaining = proteinRemaining,
        carbsRemaining = carbsRemaining,
        fatRemaining = fatRemaining,
        fiberRemaining = fiberRemaining,
        adherenceScore = adherenceScore,
        statusLabel = statusLabel,
        nextMealFocus = nextMealFocus
    )
}

private fun DailyLog.nextMealBuilder(): NextMealBuilder {
    val pacing = nutritionPacing()
    val remainingMeals = meals.size.let { logged ->
        when {
            logged <= 0 -> 3
            logged == 1 -> 2
            else -> 1
        }
    }
    val proteinTarget = (pacing.proteinRemaining.coerceAtLeast(0) / remainingMeals).coerceIn(25, 65)
    val carbsTarget = (pacing.carbsRemaining.coerceAtLeast(0) / remainingMeals).coerceIn(20, 95)
    val fatTarget = (pacing.fatRemaining.coerceAtLeast(0) / remainingMeals).coerceIn(5, 25)
    val fiberTarget = (pacing.fiberRemaining.coerceAtLeast(0) / remainingMeals).coerceIn(4, 14)
    val hasTrainingDemand = plannedHardSets() > 0 && completedHardSets() < plannedHardSets()
    val title = when {
        pacing.caloriesRemaining < -150 -> "Lean recovery meal"
        pacing.proteinRemaining > 35 -> "Protein-first meal"
        hasTrainingDemand && pacing.carbsRemaining > 60 -> "Training-fuel meal"
        pacing.fiberRemaining > 10 -> "Fiber + micronutrient meal"
        else -> "Balanced target meal"
    }
    val summary = when (title) {
        "Lean recovery meal" -> "Keep calories controlled: lean protein, vegetables, minimal added fats."
        "Protein-first meal" -> "Close the protein gap before adding extra carbs or fats."
        "Training-fuel meal" -> "Place carbs around the remaining training work or post-workout window."
        "Fiber + micronutrient meal" -> "Add fruit, vegetables, oats, beans, or potatoes with a lean protein base."
        else -> "Stay close to targets with a simple protein, carb, vegetable, and fat structure."
    }
    val timingCue = when {
        hasTrainingDemand -> "Use this 60-120 min pre-workout or within the post-workout meal window."
        trainingSession.completed -> "Use this as the next recovery meal after today's completed session."
        else -> "Use this as the next logged meal; keep portions measurable."
    }
    val portionCue = "Aim near P $proteinTarget g, C $carbsTarget g, F $fatTarget g, fiber $fiberTarget g."
    val photoCue = if (meals.isEmpty()) {
        "Attach a food photo if this is your first meal and portions are uncertain."
    } else {
        "Use a label/photo when oil, sauce, or restaurant portions are uncertain."
    }
    return NextMealBuilder(
        title = title,
        summary = summary,
        proteinGrams = proteinTarget,
        carbsGrams = carbsTarget,
        fatGrams = fatTarget,
        fiberGrams = fiberTarget,
        timingCue = timingCue,
        portionCue = portionCue,
        photoCue = photoCue
    )
}

private fun CoachUiState.dailyReadiness(): DailyReadiness {
    val metrics = dailyLog.metrics
    val recoveryPenalty = listOf(metrics.fatigue, metrics.soreness, metrics.stress).sumOf { (it - 3).coerceAtLeast(0) } * 8
    val sleepBonus = when {
        (metrics.sleepHours ?: 0.0) >= 7.0 -> 8
        metrics.sleepHours == null -> 0
        metrics.sleepHours >= 6.0 -> 2
        else -> -12
    }
    val completionBonus = if (dailyLog.plannedHardSets() > 0) {
        (dailyLog.completedHardSets() * 18 / dailyLog.plannedHardSets()).coerceIn(0, 18)
    } else {
        0
    }
    val score = (72 + sleepBonus + completionBonus - recoveryPenalty).coerceIn(35, 98)
    val nextAction = when {
        dailyLog.trainingSession.exercises.isEmpty() -> "Apply a plan day or add today's first exercise."
        dailyLog.completedHardSets() < dailyLog.plannedHardSets() -> "Finish the remaining working sets and keep RIR honest."
        dailyLog.meals.isEmpty() -> "Log the first meal or add a food photo for AI estimation."
        dailyLog.nutritionPacing().adherenceScore < 72 -> "Use Nutrition Pacing to fix the biggest macro gap before AI review."
        metrics.bodyWeightKg == null || metrics.sleepHours == null -> "Sync Health Connect or fill today's recovery metrics."
        else -> "Run AI review and lock tomorrow's training and food targets."
    }
    val label = when {
        score >= 82 -> "Ready to progress"
        score >= 65 -> "Controlled push"
        else -> "Hold or recover"
    }
    return DailyReadiness(score = score, label = label, nextAction = nextAction)
}

private fun CoachUiState.dailyCoachTasks(
    onOpenPlan: () -> Unit,
    onOpenTraining: () -> Unit,
    onOpenNutrition: () -> Unit,
    onOpenMetrics: () -> Unit,
    onRunAiReview: () -> Unit,
    onOpenAi: () -> Unit
): List<DailyCoachTask> {
    val log = dailyLog
    val planReady = trainingPlan.days.any { day -> day.exercises.isNotEmpty() }
    val sessionReady = log.trainingSession.exercises.isNotEmpty()
    val plannedSets = log.plannedHardSets()
    val completedSets = log.completedHardSets()
    val trainingDone = plannedSets > 0 && completedSets >= plannedSets
    val nutritionLogged = log.meals.isNotEmpty()
    val pacing = log.nutritionPacing()
    val metricsReady = log.metrics.bodyWeightKg != null ||
        log.metrics.sleepHours != null ||
        log.metrics.steps > 0 ||
        log.metrics.healthSyncedAt.isNotBlank()
    val aiReviewedToday = reviewHistory.any { it.logDate == log.date }

    return listOf(
        DailyCoachTask(
            title = "Plan prepared",
            detail = when {
                sessionReady -> "${log.trainingSession.plannedFocus}: $plannedSets planned sets loaded."
                planReady -> "Weekly plan exists; apply the right day before training."
                else -> "Choose a Plan Template or build today's first training day."
            },
            done = sessionReady,
            actionLabel = if (sessionReady) "View workout" else if (planReady) "Apply day" else "Pick plan",
            onAction = if (sessionReady) onOpenTraining else onOpenPlan
        ),
        DailyCoachTask(
            title = "Training executed",
            detail = when {
                !sessionReady -> "No workout loaded yet."
                trainingDone -> "$completedSets/$plannedSets sets completed; add notes before review."
                else -> "$completedSets/$plannedSets sets completed; finish working sets and rest timers."
            },
            done = trainingDone,
            actionLabel = "Log sets",
            actionEnabled = sessionReady,
            onAction = onOpenTraining
        ),
        DailyCoachTask(
            title = "Food logged",
            detail = if (nutritionLogged) {
                "${log.meals.size} meals logged; ${formatRemaining(pacing.caloriesRemaining, "kcal")} and protein ${formatRemaining(pacing.proteinRemaining, "g")}."
            } else {
                "Add a meal template or food photo so nutrition can be compared with training demand."
            },
            done = nutritionLogged,
            actionLabel = if (nutritionLogged) "Review food" else "Add meal",
            onAction = onOpenNutrition
        ),
        DailyCoachTask(
            title = "Metrics synced",
            detail = when {
                log.metrics.healthSyncedAt.isNotBlank() -> "Health data synced; source ${log.metrics.healthDataSource.ifBlank { "Health Connect" }}."
                metricsReady -> "Manual metrics available; sync Health Connect when possible."
                else -> "Add body weight, sleep, steps, HR, soreness, fatigue, and stress."
            },
            done = metricsReady,
            actionLabel = if (metricsReady) "Check metrics" else "Sync metrics",
            onAction = onOpenMetrics
        ),
        DailyCoachTask(
            title = "AI review locked",
            detail = if (aiReviewedToday) {
                "Today's review is saved; use it to set tomorrow's training and food targets."
            } else {
                "Run after training, food, and metrics are logged for the cleanest adjustment."
            },
            done = aiReviewedToday,
            actionLabel = if (aiReviewedToday) "View review" else "Run review",
            actionEnabled = !isLoading,
            onAction = if (aiReviewedToday) onOpenAi else onRunAiReview
        )
    )
}

@Composable
private fun ActionCard(
    title: String,
    value: String,
    detail: String,
    actionLabel: String,
    onAction: () -> Unit
) {
    ElevatedCard(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(title, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(value, style = MaterialTheme.typography.titleMedium)
            Text(detail, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            TextButton(onClick = onAction) {
                Text(actionLabel)
            }
        }
    }
}

@Composable
private fun CommandCenterCard(
    readiness: DailyReadiness,
    state: CoachUiState,
    onDailyReview: () -> Unit,
    onOpenTraining: () -> Unit,
    onOpenNutrition: () -> Unit,
    onOpenMetrics: () -> Unit
) {
    SectionCard(title = "Command Center", subtitle = "Start here each day: execute, log, sync, then review.") {
        MetricGrid(
            metrics = listOf(
                "Readiness" to "${readiness.score}",
                "State" to readiness.label,
                "Phase" to state.athleteProfile.currentPhase,
                "Sets" to "${state.dailyLog.completedHardSets()}/${state.dailyLog.plannedHardSets()}",
                "Meals" to state.dailyLog.meals.size.toString()
            )
        )
        Text(
            text = "Goal: ${state.athleteProfile.primaryGoal}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = readiness.nextAction,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = onDailyReview,
                enabled = !state.isLoading,
                modifier = Modifier.weight(1f)
            ) {
                Text(if (state.isLoading) "Reviewing" else "AI review")
            }
            ElevatedButton(
                onClick = when {
                    state.dailyLog.trainingSession.exercises.isEmpty() -> onOpenTraining
                    state.dailyLog.meals.isEmpty() -> onOpenNutrition
                    else -> onOpenMetrics
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Next")
            }
        }
        if (state.isLoading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }
        state.reviewHistory.firstOrNull()?.let { review ->
            HorizontalDivider()
            Text(
                text = "Latest AI guidance",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = review.preview(),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
private fun DailyCoachChecklistCard(tasks: List<DailyCoachTask>) {
    val completed = tasks.count { it.done }
    SectionCard(
        title = "Daily Coach Checklist",
        subtitle = "$completed/${tasks.size} complete. Follow this loop before trusting today's AI adjustment."
    ) {
        tasks.forEachIndexed { index, task ->
            if (index > 0) {
                HorizontalDivider()
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = if (task.done) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant,
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                ) {
                    Text(
                        text = if (task.done) "Done" else "Next",
                        modifier = Modifier.padding(horizontal = 9.dp, vertical = 6.dp),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                    Text(task.title, fontWeight = FontWeight.SemiBold)
                    Text(
                        text = task.detail,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                TextButton(onClick = task.onAction, enabled = task.actionEnabled) {
                    Text(task.actionLabel)
                }
            }
        }
    }
}

@Composable
private fun TodayActionGrid(
    state: CoachUiState,
    onOpenPlan: () -> Unit,
    onOpenTraining: () -> Unit,
    onOpenNutrition: () -> Unit,
    onOpenMetrics: () -> Unit
) {
    val log = state.dailyLog
    val totals = log.nutritionTotals()
    val pacing = log.nutritionPacing()
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        ActionCard(
            title = "Training",
            value = "${log.completedHardSets()}/${log.plannedHardSets()} sets",
            detail = "${formatDecimal(log.trainingVolumeKg())} kg volume",
            actionLabel = if (log.trainingSession.exercises.isEmpty()) "Build session" else "Log sets",
            onAction = if (log.trainingSession.exercises.isEmpty()) onOpenPlan else onOpenTraining
        )
        ActionCard(
            title = "Nutrition",
            value = formatRemaining(pacing.caloriesRemaining, "kcal"),
            detail = "${pacing.statusLabel} | Protein ${totals.protein}/${log.targets.protein} g",
            actionLabel = "Log meal",
            onAction = onOpenNutrition
        )
        ActionCard(
            title = "Recovery",
            value = formatOptional(log.metrics.sleepHours, "h"),
            detail = "Steps ${log.metrics.steps}, fatigue ${log.metrics.fatigue}/5",
            actionLabel = "Sync metrics",
            onAction = onOpenMetrics
        )
        ActionCard(
            title = "Body",
            value = formatOptional(log.metrics.bodyWeightKg, "kg"),
            detail = "Body fat ${formatOptional(log.metrics.bodyFatPercent, "%")}",
            actionLabel = "Update",
            onAction = onOpenMetrics
        )
    }
}

@Composable
private fun BeginnerGuideCard(onOpenPlan: () -> Unit, onOpenNutrition: () -> Unit, onOpenMetrics: () -> Unit) {
    SectionCard(title = "Beginner Friendly Flow", subtitle = "A simple daily loop before the deeper pro-level details.") {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("1. Pick or apply today's plan.")
            Text("2. Complete each working set and respect the rest timer.")
            Text("3. Log meals or attach photos when portions are uncertain.")
            Text("4. Sync health data, then run AI review.")
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            TextButton(onClick = onOpenPlan, modifier = Modifier.weight(1f)) {
                Text("Plan")
            }
            TextButton(onClick = onOpenNutrition, modifier = Modifier.weight(1f)) {
                Text("Food")
            }
            TextButton(onClick = onOpenMetrics, modifier = Modifier.weight(1f)) {
                Text("Metrics")
            }
        }
    }
}

@Composable
private fun TrendOverviewCard(logs: List<DailyLog>) {
    val window = logs.sortedBy { it.date }.takeLast(7)
    if (window.isEmpty()) {
        SectionCard(title = "7-Day Trend", subtitle = "Recent logs will appear here after you save a few days.") {
            EmptyState("Log training, meals, and metrics for several days to unlock trend-based AI adjustments.")
        }
        return
    }
    val firstWeight = window.firstNotNullOfOrNull { it.metrics.bodyWeightKg }
    val lastWeight = window.asReversed().firstNotNullOfOrNull { it.metrics.bodyWeightKg }
    val weightChange = if (firstWeight != null && lastWeight != null) {
        "${formatSigned(lastWeight - firstWeight)} kg"
    } else {
        "--"
    }
    val nutrition = window.map { it.nutritionTotals() }
    val avgCalories = nutrition.map { it.calories }.averageIntOrNull()
    val avgProtein = nutrition.map { it.protein }.averageIntOrNull()
    val avgSleep = window.mapNotNull { it.metrics.sleepHours }.averageDoubleOrNull()
    val avgSteps = window.map { it.metrics.steps }.averageIntOrNull()
    val totalCompletedSets = window.sumOf { it.completedHardSets() }
    val totalPlannedSets = window.sumOf { it.plannedHardSets() }
    SectionCard(title = "7-Day Trend", subtitle = "Used by AI review to avoid overreacting to a single day.") {
        MetricGrid(
            metrics = listOf(
                "Days" to window.size.toString(),
                "Weight" to weightChange,
                "Avg kcal" to (avgCalories?.let { formatDecimal(it) } ?: "--"),
                "Avg protein" to (avgProtein?.let { "${formatDecimal(it)} g" } ?: "--"),
                "Avg sleep" to (avgSleep?.let { "${formatDecimal(it)} h" } ?: "--"),
                "Avg steps" to (avgSteps?.let { formatDecimal(it) } ?: "--"),
                "Hard sets" to "$totalCompletedSets/$totalPlannedSets"
            )
        )
        window.forEach { day ->
            val totals = day.nutritionTotals()
            Text(
                text = "${day.date}: ${day.completedHardSets()}/${day.plannedHardSets()} sets, ${totals.calories} kcal, P ${totals.protein} g, sleep ${formatOptional(day.metrics.sleepHours, "h")}, weight ${formatOptional(day.metrics.bodyWeightKg, "kg")}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun TodayDashboard(
    state: CoachUiState,
    onDailyReview: () -> Unit,
    onReset: () -> Unit,
    onOpenPlan: () -> Unit,
    onOpenTraining: () -> Unit,
    onOpenNutrition: () -> Unit,
    onOpenMetrics: () -> Unit,
    onOpenAi: () -> Unit
) {
    val log = state.dailyLog
    val totals = log.nutritionTotals()
    val pacing = log.nutritionPacing()
    val readiness = state.dailyReadiness()
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        CommandCenterCard(
            readiness = readiness,
            state = state,
            onDailyReview = onDailyReview,
            onOpenTraining = onOpenTraining,
            onOpenNutrition = onOpenNutrition,
            onOpenMetrics = onOpenMetrics
        )
        DailyCoachChecklistCard(
            tasks = state.dailyCoachTasks(
                onOpenPlan = onOpenPlan,
                onOpenTraining = onOpenTraining,
                onOpenNutrition = onOpenNutrition,
                onOpenMetrics = onOpenMetrics,
                onRunAiReview = onDailyReview,
                onOpenAi = onOpenAi
            )
        )
        TodayActionGrid(
            state = state,
            onOpenPlan = onOpenPlan,
            onOpenTraining = onOpenTraining,
            onOpenNutrition = onOpenNutrition,
            onOpenMetrics = onOpenMetrics
        )
        SectionCard(title = "Today Snapshot", subtitle = log.date) {
            MetricGrid(
                metrics = listOf(
                    "Volume" to "${formatDecimal(log.trainingVolumeKg())} kg",
                    "Calories" to "${totals.calories}/${log.targets.calories}",
                    "Protein" to "${totals.protein}/${log.targets.protein} g",
                    "Macro pace" to "${pacing.adherenceScore}%",
                    "Body fat" to formatOptional(log.metrics.bodyFatPercent, "%"),
                    "Sleep" to formatOptional(log.metrics.sleepHours, "h"),
                    "Resting HR" to formatOptional(log.metrics.restingHeartRateBpm, "bpm")
                )
            )
            Text(
                text = "Focus: ${log.trainingSession.plannedFocus}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Nutrition: C ${totals.carbs}/${log.targets.carbs} g, F ${totals.fat}/${log.targets.fat} g, fiber ${totals.fiber}/${log.targets.fiber} g.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "Next meal: ${pacing.nextMealFocus}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "Recovery: waist ${formatOptional(log.metrics.waistCm, "cm")}, steps ${log.metrics.steps}, hunger ${log.metrics.hunger}/5, fatigue ${log.metrics.fatigue}/5, soreness ${log.metrics.soreness}/5.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                TextButton(onClick = onReset, modifier = Modifier.weight(1f)) {
                    Text("Reset today")
                }
                TextButton(onClick = onOpenMetrics, modifier = Modifier.weight(1f)) {
                    Text("Edit metrics")
                }
            }
        }
        BodyCompositionCard(
            guidance = bodyCompositionGuidance(log, state.recentLogs, state.athleteProfile),
            subtitle = "Trend-based target check before changing calories."
        )
        RecoveryGuidanceCard(
            guidance = recoveryGuidance(log, state.recentLogs),
            subtitle = "Sleep, soreness, stress, resting HR, and set pressure before pushing harder."
        )
        TrendOverviewCard(logs = state.recentLogs)
        BeginnerGuideCard(onOpenPlan = onOpenPlan, onOpenNutrition = onOpenNutrition, onOpenMetrics = onOpenMetrics)
    }
}

@Composable
private fun NutritionPacingCard(log: DailyLog) {
    val pacing = log.nutritionPacing()
    SectionCard(title = "Nutrition Pacing", subtitle = "What is still available today and what the next meal should bias toward.") {
        MetricGrid(
            metrics = listOf(
                "Pace" to "${pacing.adherenceScore}%",
                "Status" to pacing.statusLabel,
                "Kcal" to formatRemaining(pacing.caloriesRemaining, "kcal"),
                "Protein" to formatRemaining(pacing.proteinRemaining, "g"),
                "Carbs" to formatRemaining(pacing.carbsRemaining, "g"),
                "Fat" to formatRemaining(pacing.fatRemaining, "g"),
                "Fiber" to formatRemaining(pacing.fiberRemaining, "g")
            )
        )
        LinearProgressIndicator(
            progress = { pacing.adherenceScore / 100f },
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = "Next meal focus: ${pacing.nextMealFocus}",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = "Negative remaining values mean the target is already exceeded; AI review uses this with training demand and recent trends before changing tomorrow's targets.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun NextMealBuilderCard(log: DailyLog) {
    val builder = log.nextMealBuilder()
    SectionCard(title = "Next Meal Builder", subtitle = "Turn remaining macros and training demand into the next practical meal.") {
        Text(builder.title, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
        Text(
            text = builder.summary,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        MetricGrid(
            metrics = listOf(
                "Protein" to "${builder.proteinGrams} g",
                "Carbs" to "${builder.carbsGrams} g",
                "Fat" to "${builder.fatGrams} g",
                "Fiber" to "${builder.fiberGrams} g"
            )
        )
        Text(
            text = builder.timingCue,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = builder.portionCue,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = builder.photoCue,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun BodyCompositionCard(guidance: BodyCompositionGuidance, subtitle: String) {
    SectionCard(title = "Body Composition Guidance", subtitle = subtitle) {
        MetricGrid(
            metrics = listOf(
                "Status" to guidance.statusLabel,
                "Phase" to guidance.phaseGoal,
                "Weight trend" to guidance.weightChangeKg.formatSignedHistoryValue("kg"),
                "Avg kcal" to guidance.averageCalories.formatHistoryValue("kcal"),
                "Avg protein" to guidance.averageProtein.formatHistoryValue("g"),
                "Set avg" to guidance.averageCompletedSets.formatHistoryValue("sets"),
                "Kcal adjust" to guidance.calorieAdjustmentKcal.formatSignedInt("kcal"),
                "Target" to "${guidance.targetCalories} kcal"
            )
        )
        Text(
            text = guidance.rationale,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = "Next action: ${guidance.nextAction}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun RecoveryGuidanceCard(guidance: RecoveryGuidance, subtitle: String) {
    SectionCard(title = "Recovery Guidance", subtitle = subtitle) {
        MetricGrid(
            metrics = listOf(
                "Status" to guidance.statusLabel,
                "Score" to guidance.readinessScore.toString(),
                "Pressure" to guidance.trainingPressure,
                "Sleep signal" to guidance.sleepSignal,
                "Stress signal" to guidance.stressSignal,
                "Soreness" to guidance.sorenessSignal,
                "HR signal" to guidance.heartRateSignal,
                "Training action" to guidance.recommendedTrainingAction
            )
        )
        Text(
            text = guidance.rationale,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = "Next action: ${guidance.nextAction}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun RestTimerBanner(timer: RestTimerState, onSkip: () -> Unit) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                Text("Rest timer", fontWeight = FontWeight.SemiBold)
                Text(
                    text = "${timer.exerciseName} set ${timer.setNumber} finished. Next set in ${formatTimer(timer.remainingSeconds)}.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            TextButton(onClick = onSkip) {
                Text("Skip")
            }
        }
    }
}

@Composable
private fun AthleteProfileCard(profile: AthleteProfile, onProfileChange: (AthleteProfile) -> Unit) {
    SectionCard(title = "Athlete Profile", subtitle = "Set the long-term target once so plans, nutrition, and AI reviews stay personal.") {
        MetricGrid(
            metrics = listOf(
                "Phase" to profile.currentPhase,
                "Experience" to profile.trainingExperience,
                "Training days" to profile.weeklyTrainingDays.toString(),
                "Target BF" to formatOptional(profile.targetBodyFatPercent, "%")
            )
        )
        OutlinedTextField(
            value = profile.displayName,
            onValueChange = { onProfileChange(profile.copy(displayName = it)) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Name") },
            singleLine = true
        )
        OutlinedTextField(
            value = profile.primaryGoal,
            onValueChange = { onProfileChange(profile.copy(primaryGoal = it)) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Primary physique goal") },
            minLines = 2
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = profile.currentPhase,
                onValueChange = { onProfileChange(profile.copy(currentPhase = it)) },
                modifier = Modifier.weight(1f),
                label = { Text("Phase") },
                singleLine = true
            )
            OutlinedTextField(
                value = profile.trainingExperience,
                onValueChange = { onProfileChange(profile.copy(trainingExperience = it)) },
                modifier = Modifier.weight(1f),
                label = { Text("Experience") },
                singleLine = true
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = profile.sex,
                onValueChange = { onProfileChange(profile.copy(sex = it)) },
                modifier = Modifier.weight(1f),
                label = { Text("Sex") },
                singleLine = true
            )
            NumberField(
                value = profile.age?.toString().orEmpty(),
                onChange = { onProfileChange(profile.copy(age = it.toIntOrNull())) },
                label = "Age",
                modifier = Modifier.weight(1f)
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            DecimalField(
                value = profile.heightCm?.toString().orEmpty(),
                onChange = { onProfileChange(profile.copy(heightCm = it.toDoubleOrNull())) },
                label = "Height cm",
                modifier = Modifier.weight(1f)
            )
            NumberField(
                value = profile.weeklyTrainingDays.toString(),
                onChange = {
                    onProfileChange(
                        profile.copy(
                            weeklyTrainingDays = (it.toIntOrNull() ?: profile.weeklyTrainingDays).coerceIn(1, 7)
                        )
                    )
                },
                label = "Days/wk",
                modifier = Modifier.weight(1f)
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            DecimalField(
                value = profile.startWeightKg?.toString().orEmpty(),
                onChange = { onProfileChange(profile.copy(startWeightKg = it.toDoubleOrNull())) },
                label = "Start kg",
                modifier = Modifier.weight(1f)
            )
            DecimalField(
                value = profile.targetWeightKg?.toString().orEmpty(),
                onChange = { onProfileChange(profile.copy(targetWeightKg = it.toDoubleOrNull())) },
                label = "Target kg",
                modifier = Modifier.weight(1f)
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            DecimalField(
                value = profile.targetBodyFatPercent?.toString().orEmpty(),
                onChange = { onProfileChange(profile.copy(targetBodyFatPercent = it.toDoubleOrNull())) },
                label = "Target BF %",
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = profile.availableEquipment,
                onValueChange = { onProfileChange(profile.copy(availableEquipment = it)) },
                modifier = Modifier.weight(1f),
                label = { Text("Equipment") },
                singleLine = true
            )
        }
        OutlinedTextField(
            value = profile.weakPoints,
            onValueChange = { onProfileChange(profile.copy(weakPoints = it)) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Weak points or physique priorities") },
            minLines = 2
        )
        OutlinedTextField(
            value = profile.dietaryPreference,
            onValueChange = { onProfileChange(profile.copy(dietaryPreference = it)) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Diet preference") },
            singleLine = true
        )
        OutlinedTextField(
            value = profile.constraints,
            onValueChange = { onProfileChange(profile.copy(constraints = it)) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Injuries, schedule, recovery, or food constraints") },
            minLines = 2
        )
    }
}

@Composable
private fun PlanTemplateLibrary(
    currentPlanName: String,
    onApplyTemplate: (String) -> Unit
) {
    SectionCard(
        title = "Plan Templates",
        subtitle = "Pick a ready-to-train weekly structure, then edit exercises, sets, RIR, rest, or weak-point emphasis."
    ) {
        trainingPlanTemplates().forEach { template ->
            PlanTemplateCard(
                template = template,
                isCurrent = currentPlanName == template.plan.name,
                onApplyTemplate = onApplyTemplate
            )
        }
    }
}

@Composable
private fun PlanTemplateCard(
    template: TrainingPlanTemplate,
    isCurrent: Boolean,
    onApplyTemplate: (String) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        color = if (isCurrent) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
    ) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.Top) {
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                    Text(template.title, fontWeight = FontWeight.SemiBold)
                    Text(
                        text = template.subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                TextButton(onClick = { onApplyTemplate(template.id) }) {
                    Text(if (isCurrent) "Current" else "Use")
                }
            }
            MetricGrid(
                metrics = listOf(
                    "Days/wk" to template.weeklyDays.toString(),
                    "Hard sets" to template.plan.days.sumOf { day -> day.exercises.sumOf { it.sets } }.toString(),
                    "Exercises" to template.plan.days.sumOf { it.exercises.size }.toString(),
                    "Best for" to template.bestFor
                )
            )
            val activeDays = template.plan.days.filter { it.exercises.isNotEmpty() }
            Text(
                text = activeDays.joinToString(" | ") { "${it.dayName} ${it.focus}" },
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
@OptIn(ExperimentalLayoutApi::class)
private fun PlanPage(
    state: CoachUiState,
    onProfileChange: (AthleteProfile) -> Unit,
    onNameChange: (String) -> Unit,
    onGoalChange: (String) -> Unit,
    onSelectDay: (Int) -> Unit,
    onUpdateDay: (Int, String, String) -> Unit,
    onAddPlannedExercise: (Int, String, String, Int, String, Double?, Double?, Int, String) -> Unit,
    onRemovePlannedExercise: (Int, Int) -> Unit,
    onApplyDay: (Int) -> Unit,
    onApplyTemplate: (String) -> Unit,
    onResetPlan: () -> Unit
) {
    val plan = state.trainingPlan
    val selectedIndex = state.selectedPlanDayIndex.coerceIn(0, (plan.days.size - 1).coerceAtLeast(0))
    val selectedDay = plan.days.getOrNull(selectedIndex) ?: TrainingDay(dayName = "Day")

    var exerciseName by remember(selectedIndex) { mutableStateOf("") }
    var targetMuscle by remember(selectedIndex) { mutableStateOf("") }
    var sets by remember(selectedIndex) { mutableStateOf("3") }
    var reps by remember(selectedIndex) { mutableStateOf("8-12") }
    var load by remember(selectedIndex) { mutableStateOf("") }
    var rir by remember(selectedIndex) { mutableStateOf("2") }
    var rest by remember(selectedIndex) { mutableStateOf("120") }
    var notes by remember(selectedIndex) { mutableStateOf("") }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        AthleteProfileCard(profile = state.athleteProfile, onProfileChange = onProfileChange)
        PlanTemplateLibrary(
            currentPlanName = plan.name,
            onApplyTemplate = onApplyTemplate
        )
        SectionCard(title = "Weekly Plan", subtitle = "Build the plan first, then apply a training day to today's executable workout log.") {
            OutlinedTextField(
                value = plan.name,
                onValueChange = onNameChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Plan name") },
                singleLine = true
            )
            OutlinedTextField(
                value = plan.phaseGoal,
                onValueChange = onGoalChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Phase goal") },
                minLines = 2
            )
            MetricGrid(
                metrics = listOf(
                    "Training days" to plan.days.count { it.exercises.isNotEmpty() }.toString(),
                    "Planned exercises" to plan.days.sumOf { it.exercises.size }.toString(),
                    "Weekly hard sets" to plan.days.sumOf { day -> day.exercises.sumOf { it.sets } }.toString()
                )
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = { onApplyDay(selectedIndex) }, modifier = Modifier.weight(1f)) {
                    Text("Apply today")
                }
                TextButton(onClick = onResetPlan) {
                    Text("Reset plan")
                }
            }
        }

        SectionCard(title = "Training Days", subtitle = "Select the day you want to edit or apply.") {
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                plan.days.forEachIndexed { index, day ->
                    FilterChip(
                        selected = selectedIndex == index,
                        onClick = { onSelectDay(index) },
                        label = { Text(day.dayName) }
                    )
                }
            }
            OutlinedTextField(
                value = selectedDay.focus,
                onValueChange = { onUpdateDay(selectedIndex, it, selectedDay.notes) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("${selectedDay.dayName} focus") },
                singleLine = true
            )
            OutlinedTextField(
                value = selectedDay.notes,
                onValueChange = { onUpdateDay(selectedIndex, selectedDay.focus, it) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Day notes") },
                minLines = 2
            )
        }

        ExerciseVisualGuideLibrary()

        SectionCard(title = "Add Planned Exercise", subtitle = "These planned movements become set-level rows when you apply the day.") {
            OutlinedTextField(
                value = exerciseName,
                onValueChange = { exerciseName = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Exercise") },
                singleLine = true
            )
            OutlinedTextField(
                value = targetMuscle,
                onValueChange = { targetMuscle = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Target muscle") },
                singleLine = true
            )
            ExerciseVisualRecognitionPreview(name = exerciseName, targetMuscle = targetMuscle)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                NumberField(value = sets, onChange = { sets = it }, label = "Sets", modifier = Modifier.weight(1f))
                OutlinedTextField(
                    value = reps,
                    onValueChange = { reps = it },
                    modifier = Modifier.weight(1f),
                    label = { Text("Reps") },
                    singleLine = true
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                DecimalField(value = load, onChange = { load = it }, label = "Load kg", modifier = Modifier.weight(1f))
                DecimalField(value = rir, onChange = { rir = it }, label = "RIR", modifier = Modifier.weight(1f))
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                NumberField(value = rest, onChange = { rest = it }, label = "Rest sec", modifier = Modifier.weight(1f))
                Button(
                    onClick = {
                        onAddPlannedExercise(
                            selectedIndex,
                            exerciseName,
                            targetMuscle,
                            sets.toIntOrNull() ?: 3,
                            reps,
                            load.toDoubleOrNull(),
                            rir.toDoubleOrNull(),
                            rest.toIntOrNull() ?: 120,
                            notes
                        )
                        exerciseName = ""
                        targetMuscle = ""
                        notes = ""
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Add")
                }
            }
            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Technique, stimulus, substitution, progression note") },
                minLines = 2
            )
        }

        if (selectedDay.exercises.isEmpty()) {
            EmptyState("No planned exercises for ${selectedDay.dayName}. Add movements before applying this day.")
        } else {
            selectedDay.exercises.forEachIndexed { exerciseIndex, exercise ->
                PlannedExerciseCard(
                    exercise = exercise,
                    onRemove = { onRemovePlannedExercise(selectedIndex, exerciseIndex) }
                )
            }
        }
    }
}

@Composable
private fun PlannedExerciseCard(exercise: PlannedExercise, onRemove: () -> Unit) {
    Card(shape = RoundedCornerShape(8.dp)) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            ExerciseVisualGuide(name = exercise.name, targetMuscle = exercise.targetMuscle)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.Top) {
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                    Text(exercise.name, fontWeight = FontWeight.SemiBold)
                    Text(
                        text = "${exercise.targetMuscle.ifBlank { "Target muscle not set" }} | ${exercise.sets} x ${exercise.reps} | RIR ${exercise.rir?.let { formatDecimal(it) } ?: "--"} | rest ${exercise.restSeconds}s",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    if (exercise.notes.isNotBlank()) {
                        Text(
                            text = exercise.notes,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                TextButton(onClick = onRemove) {
                    Text("Remove")
                }
            }
        }
    }
}

@Composable
private fun ExerciseVisualGuide(name: String, targetMuscle: String) {
    val spec = remember(name, targetMuscle) { exerciseVisualSpec(name, targetMuscle) }
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Canvas(
                modifier = Modifier
                    .weight(0.82f)
                    .height(92.dp)
            ) {
                drawExerciseVisual(type = spec.type)
            }
            Column(modifier = Modifier.weight(1.18f), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text("${spec.visualId} Exercise visual guide / 统一动作图例", fontWeight = FontWeight.SemiBold)
                Text(
                    text = "${spec.equipment} (${spec.equipmentZh}) | ${spec.pattern}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Unified instance diagram: ${spec.figureTitle}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Example: ${spec.example}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Instance cue: ${spec.instanceCue}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "动作识别: ${spec.beginnerCue}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                DataChipGrid(items = listOf(spec.primaryMuscle, spec.setupCue, spec.actionPathCue, spec.lookFor))
            }
        }
    }
}

@Composable
private fun ExerciseVisualGuideLibrary() {
    SectionCard(
        title = "Exercise Visual Library",
        subtitle = "Unified equipment/action instance diagrams help non-pro users recognize what an exercise name refers to before adding it. 中文图例会把动作名翻译成大概该找哪种器械。"
    ) {
        Text(
            text = "When the app or AI sees an exercise name, it maps it to one of these shared visual IDs: equipment name, Chinese label, simple instance diagram, action path cue, beginner recognition cue, example movement, look-for cue, equipment markers, and common movements.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        val examples = exerciseVisualLibrarySpecs()
        examples.forEachIndexed { index, spec ->
            ExerciseVisualGuideSample(spec = spec)
            if (index != examples.lastIndex) {
                HorizontalDivider()
            }
        }
        Text(
            text = "For real gym equipment, attach an equipment photo or exercise frame in AI Coach so the multimodal model can identify the setup and connect it to your log.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun ExerciseVisualRecognitionPreview(name: String, targetMuscle: String) {
    val trimmedName = name.trim()
    val trimmedMuscle = targetMuscle.trim()
    val spec = remember(trimmedName, trimmedMuscle) { exerciseVisualSpec(trimmedName, trimmedMuscle) }
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Canvas(
                modifier = Modifier
                    .weight(0.55f)
                    .height(64.dp)
            ) {
                drawExerciseVisual(type = spec.type)
            }
            Column(modifier = Modifier.weight(1.45f), verticalArrangement = Arrangement.spacedBy(5.dp)) {
                Text("Live equipment/action preview", fontWeight = FontWeight.SemiBold)
                Text(
                    text = if (trimmedName.isBlank()) {
                        "Type an exercise name to preview the likely equipment or movement pattern."
                    } else {
                        "$trimmedName -> ${spec.visualId} ${spec.equipment} (${spec.equipmentZh})"
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Unified instance diagram: ${spec.figureTitle}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "常见动作: ${spec.commonMovements.joinToString(", ")}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = spec.lookFor,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun ExerciseVisualGuideSample(spec: ExerciseVisualSpec) {
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
        Canvas(
            modifier = Modifier
                .weight(0.75f)
                .height(70.dp)
        ) {
            drawExerciseVisual(type = spec.type)
        }
        Column(modifier = Modifier.weight(1.25f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text("${spec.visualId} ${spec.equipment} (${spec.equipmentZh})", fontWeight = FontWeight.SemiBold)
            Text(
                text = "${spec.pattern} | Example: ${spec.example}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "Unified instance diagram: ${spec.figureTitle}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "Instance: ${spec.instanceCue}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "Action path: ${spec.actionPathCue}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "Beginner cue: ${spec.beginnerCue}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "Markers: ${spec.equipmentMarkers.joinToString(", ")}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "Common: ${spec.commonMovements.joinToString(", ")}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "Look-for cue: ${spec.lookFor}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawExerciseVisual(type: ExerciseVisualType) {
    val primary = Color(0xFF007AFF)
    val secondary = Color(0xFF6E6E73)
    val accent = Color(0xFF34C759)
    val stroke = 4.dp.toPx()
    val thin = 2.dp.toPx()
    val w = size.width
    val h = size.height

    drawLine(
        secondary.copy(alpha = 0.35f),
        Offset(w * 0.08f, h * 0.82f),
        Offset(w * 0.92f, h * 0.82f),
        strokeWidth = thin
    )

    fun drawPerson(head: Offset, hip: Offset, leftHand: Offset, rightHand: Offset, leftFoot: Offset, rightFoot: Offset) {
        drawCircle(accent, radius = 6.dp.toPx(), center = head)
        drawLine(accent, Offset(head.x, head.y + h * 0.07f), hip, strokeWidth = thin, cap = StrokeCap.Round)
        drawLine(accent, Offset(head.x, head.y + h * 0.11f), leftHand, strokeWidth = thin, cap = StrokeCap.Round)
        drawLine(accent, Offset(head.x, head.y + h * 0.11f), rightHand, strokeWidth = thin, cap = StrokeCap.Round)
        drawLine(accent, hip, leftFoot, strokeWidth = thin, cap = StrokeCap.Round)
        drawLine(accent, hip, rightFoot, strokeWidth = thin, cap = StrokeCap.Round)
    }

    when (type) {
        ExerciseVisualType.SMITH_MACHINE -> {
            drawLine(secondary, Offset(w * 0.18f, h * 0.12f), Offset(w * 0.18f, h * 0.82f), strokeWidth = stroke)
            drawLine(secondary, Offset(w * 0.82f, h * 0.12f), Offset(w * 0.82f, h * 0.82f), strokeWidth = stroke)
            drawLine(primary, Offset(w * 0.22f, h * 0.34f), Offset(w * 0.78f, h * 0.34f), strokeWidth = stroke, cap = StrokeCap.Round)
            drawLine(secondary, Offset(w * 0.32f, h * 0.70f), Offset(w * 0.70f, h * 0.58f), strokeWidth = stroke, cap = StrokeCap.Round)
            drawPerson(
                head = Offset(w * 0.46f, h * 0.43f),
                hip = Offset(w * 0.59f, h * 0.62f),
                leftHand = Offset(w * 0.38f, h * 0.34f),
                rightHand = Offset(w * 0.58f, h * 0.34f),
                leftFoot = Offset(w * 0.50f, h * 0.72f),
                rightFoot = Offset(w * 0.68f, h * 0.68f)
            )
        }

        ExerciseVisualType.CABLE -> {
            drawLine(secondary, Offset(w * 0.16f, h * 0.12f), Offset(w * 0.16f, h * 0.82f), strokeWidth = stroke)
            drawLine(secondary, Offset(w * 0.16f, h * 0.12f), Offset(w * 0.46f, h * 0.12f), strokeWidth = stroke)
            drawCircle(primary, radius = 7.dp.toPx(), center = Offset(w * 0.44f, h * 0.18f))
            drawLine(primary, Offset(w * 0.44f, h * 0.18f), Offset(w * 0.64f, h * 0.46f), strokeWidth = thin, cap = StrokeCap.Round)
            drawPerson(
                head = Offset(w * 0.72f, h * 0.34f),
                hip = Offset(w * 0.62f, h * 0.62f),
                leftHand = Offset(w * 0.52f, h * 0.56f),
                rightHand = Offset(w * 0.78f, h * 0.56f),
                leftFoot = Offset(w * 0.56f, h * 0.80f),
                rightFoot = Offset(w * 0.72f, h * 0.80f)
            )
            drawLine(primary, Offset(w * 0.64f, h * 0.46f), Offset(w * 0.52f, h * 0.56f), strokeWidth = thin, cap = StrokeCap.Round)
        }

        ExerciseVisualType.DUMBBELL -> {
            drawPerson(
                head = Offset(w * 0.50f, h * 0.24f),
                hip = Offset(w * 0.50f, h * 0.58f),
                leftHand = Offset(w * 0.32f, h * 0.46f),
                rightHand = Offset(w * 0.68f, h * 0.46f),
                leftFoot = Offset(w * 0.38f, h * 0.78f),
                rightFoot = Offset(w * 0.62f, h * 0.78f)
            )
            drawLine(primary, Offset(w * 0.25f, h * 0.46f), Offset(w * 0.38f, h * 0.46f), strokeWidth = stroke, cap = StrokeCap.Round)
            drawLine(primary, Offset(w * 0.62f, h * 0.46f), Offset(w * 0.75f, h * 0.46f), strokeWidth = stroke, cap = StrokeCap.Round)
            drawCircle(primary, radius = 3.dp.toPx(), center = Offset(w * 0.24f, h * 0.46f))
            drawCircle(primary, radius = 3.dp.toPx(), center = Offset(w * 0.76f, h * 0.46f))
        }

        ExerciseVisualType.BARBELL -> {
            drawLine(primary, Offset(w * 0.16f, h * 0.34f), Offset(w * 0.84f, h * 0.34f), strokeWidth = stroke, cap = StrokeCap.Round)
            drawLine(primary, Offset(w * 0.18f, h * 0.26f), Offset(w * 0.18f, h * 0.42f), strokeWidth = stroke)
            drawLine(primary, Offset(w * 0.82f, h * 0.26f), Offset(w * 0.82f, h * 0.42f), strokeWidth = stroke)
            drawPerson(
                head = Offset(w * 0.50f, h * 0.45f),
                hip = Offset(w * 0.50f, h * 0.70f),
                leftHand = Offset(w * 0.38f, h * 0.36f),
                rightHand = Offset(w * 0.62f, h * 0.36f),
                leftFoot = Offset(w * 0.36f, h * 0.82f),
                rightFoot = Offset(w * 0.64f, h * 0.82f)
            )
            drawLine(secondary.copy(alpha = 0.8f), Offset(w * 0.30f, h * 0.18f), Offset(w * 0.30f, h * 0.78f), strokeWidth = thin)
            drawLine(secondary.copy(alpha = 0.8f), Offset(w * 0.70f, h * 0.18f), Offset(w * 0.70f, h * 0.78f), strokeWidth = thin)
        }

        ExerciseVisualType.MACHINE -> {
            drawLine(secondary, Offset(w * 0.22f, h * 0.18f), Offset(w * 0.22f, h * 0.82f), strokeWidth = stroke)
            drawLine(secondary, Offset(w * 0.22f, h * 0.78f), Offset(w * 0.78f, h * 0.78f), strokeWidth = stroke)
            drawLine(primary, Offset(w * 0.40f, h * 0.62f), Offset(w * 0.70f, h * 0.42f), strokeWidth = stroke, cap = StrokeCap.Round)
            drawLine(secondary, Offset(w * 0.36f, h * 0.66f), Offset(w * 0.58f, h * 0.66f), strokeWidth = stroke, cap = StrokeCap.Round)
            drawPath(
                path = Path().apply {
                    moveTo(w * 0.70f, h * 0.42f)
                    quadraticTo(w * 0.82f, h * 0.54f, w * 0.66f, h * 0.66f)
                },
                color = primary,
                style = Stroke(width = thin, cap = StrokeCap.Round)
            )
            drawPerson(
                head = Offset(w * 0.50f, h * 0.36f),
                hip = Offset(w * 0.46f, h * 0.62f),
                leftHand = Offset(w * 0.66f, h * 0.44f),
                rightHand = Offset(w * 0.66f, h * 0.51f),
                leftFoot = Offset(w * 0.36f, h * 0.74f),
                rightFoot = Offset(w * 0.58f, h * 0.74f)
            )
        }

        ExerciseVisualType.BENCH -> {
            drawLine(secondary, Offset(w * 0.22f, h * 0.74f), Offset(w * 0.76f, h * 0.62f), strokeWidth = stroke, cap = StrokeCap.Round)
            drawLine(secondary, Offset(w * 0.34f, h * 0.74f), Offset(w * 0.28f, h * 0.82f), strokeWidth = thin, cap = StrokeCap.Round)
            drawLine(secondary, Offset(w * 0.68f, h * 0.64f), Offset(w * 0.74f, h * 0.82f), strokeWidth = thin, cap = StrokeCap.Round)
            drawPerson(
                head = Offset(w * 0.48f, h * 0.38f),
                hip = Offset(w * 0.58f, h * 0.58f),
                leftHand = Offset(w * 0.35f, h * 0.32f),
                rightHand = Offset(w * 0.66f, h * 0.32f),
                leftFoot = Offset(w * 0.42f, h * 0.78f),
                rightFoot = Offset(w * 0.68f, h * 0.76f)
            )
            drawLine(primary, Offset(w * 0.31f, h * 0.32f), Offset(w * 0.40f, h * 0.32f), strokeWidth = stroke, cap = StrokeCap.Round)
            drawLine(primary, Offset(w * 0.62f, h * 0.32f), Offset(w * 0.71f, h * 0.32f), strokeWidth = stroke, cap = StrokeCap.Round)
        }

        ExerciseVisualType.PULL_UP_STATION -> {
            drawLine(secondary, Offset(w * 0.24f, h * 0.18f), Offset(w * 0.24f, h * 0.82f), strokeWidth = stroke)
            drawLine(secondary, Offset(w * 0.76f, h * 0.18f), Offset(w * 0.76f, h * 0.82f), strokeWidth = stroke)
            drawLine(primary, Offset(w * 0.20f, h * 0.20f), Offset(w * 0.80f, h * 0.20f), strokeWidth = stroke, cap = StrokeCap.Round)
            drawPerson(
                head = Offset(w * 0.50f, h * 0.38f),
                hip = Offset(w * 0.50f, h * 0.62f),
                leftHand = Offset(w * 0.38f, h * 0.22f),
                rightHand = Offset(w * 0.62f, h * 0.22f),
                leftFoot = Offset(w * 0.44f, h * 0.78f),
                rightFoot = Offset(w * 0.56f, h * 0.78f)
            )
        }

        ExerciseVisualType.BAND -> {
            drawLine(secondary, Offset(w * 0.18f, h * 0.16f), Offset(w * 0.18f, h * 0.82f), strokeWidth = stroke)
            drawLine(primary, Offset(w * 0.20f, h * 0.42f), Offset(w * 0.58f, h * 0.52f), strokeWidth = thin, cap = StrokeCap.Round)
            drawLine(primary, Offset(w * 0.20f, h * 0.42f), Offset(w * 0.60f, h * 0.42f), strokeWidth = thin, cap = StrokeCap.Round)
            drawPerson(
                head = Offset(w * 0.72f, h * 0.34f),
                hip = Offset(w * 0.64f, h * 0.62f),
                leftHand = Offset(w * 0.58f, h * 0.52f),
                rightHand = Offset(w * 0.60f, h * 0.42f),
                leftFoot = Offset(w * 0.58f, h * 0.80f),
                rightFoot = Offset(w * 0.74f, h * 0.80f)
            )
        }

        ExerciseVisualType.LEG_PRESS -> {
            drawLine(secondary, Offset(w * 0.18f, h * 0.78f), Offset(w * 0.74f, h * 0.78f), strokeWidth = stroke, cap = StrokeCap.Round)
            drawLine(secondary, Offset(w * 0.30f, h * 0.72f), Offset(w * 0.62f, h * 0.52f), strokeWidth = stroke, cap = StrokeCap.Round)
            drawLine(primary, Offset(w * 0.62f, h * 0.30f), Offset(w * 0.84f, h * 0.58f), strokeWidth = stroke, cap = StrokeCap.Round)
            drawLine(primary, Offset(w * 0.72f, h * 0.28f), Offset(w * 0.90f, h * 0.52f), strokeWidth = thin, cap = StrokeCap.Round)
            drawPerson(
                head = Offset(w * 0.38f, h * 0.42f),
                hip = Offset(w * 0.48f, h * 0.62f),
                leftHand = Offset(w * 0.36f, h * 0.60f),
                rightHand = Offset(w * 0.50f, h * 0.56f),
                leftFoot = Offset(w * 0.67f, h * 0.45f),
                rightFoot = Offset(w * 0.70f, h * 0.52f)
            )
        }

        ExerciseVisualType.BODYWEIGHT -> {
            drawLine(primary, Offset(w * 0.26f, h * 0.70f), Offset(w * 0.84f, h * 0.70f), strokeWidth = stroke, cap = StrokeCap.Round)
            drawPerson(
                head = Offset(w * 0.30f, h * 0.36f),
                hip = Offset(w * 0.58f, h * 0.48f),
                leftHand = Offset(w * 0.32f, h * 0.68f),
                rightHand = Offset(w * 0.46f, h * 0.45f),
                leftFoot = Offset(w * 0.78f, h * 0.70f),
                rightFoot = Offset(w * 0.72f, h * 0.58f)
            )
        }
    }
}

@Composable
private fun TrainingPage(
    state: CoachUiState,
    onFocusChange: (String) -> Unit,
    onNotesChange: (String) -> Unit,
    onCompletedChange: () -> Unit,
    onAddExercise: (String, String, Int, String, Double?, Double?, String, Int) -> Unit,
    onRemoveExercise: (Int) -> Unit,
    onUpdateSetEntry: (Int, Int, Int?, Double?, Double?, String) -> Unit,
    onCompleteSet: (Int, Int) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var muscle by remember { mutableStateOf("") }
    var sets by remember { mutableStateOf("3") }
    var reps by remember { mutableStateOf("8-12") }
    var load by remember { mutableStateOf("") }
    var rir by remember { mutableStateOf("2") }
    var rest by remember { mutableStateOf("120") }
    var notes by remember { mutableStateOf("") }
    val session = state.dailyLog.trainingSession
    val recovery = recoveryGuidance(state.dailyLog, state.recentLogs)
    val readinessBuilder = trainingReadinessBuilder(state.dailyLog, recovery)

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        TrainingReadinessBuilderCard(builder = readinessBuilder)
        SectionCard(title = "Training Execution", subtitle = "Log every working set, hard sets, rest time, and effort so AI can compare performance, pain, and recovery.") {
            MetricGrid(
                metrics = listOf(
                    "Exercises" to session.exercises.size.toString(),
                    "Completed sets" to "${state.dailyLog.completedHardSets()}/${state.dailyLog.plannedHardSets()}",
                    "Tonnage" to "${formatDecimal(state.dailyLog.trainingVolumeKg())} kg"
                )
            )
            OutlinedTextField(
                value = session.plannedFocus,
                onValueChange = onFocusChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Planned focus") }
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = session.completed, onCheckedChange = { onCompletedChange() })
                Text("Training completed")
            }
            OutlinedTextField(
                value = session.sessionNotes,
                onValueChange = onNotesChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Session notes: pump, stimulus, pain, technique, energy") },
                minLines = 2
            )
        }

        SectionCard(title = "Add Exercise", subtitle = "Create planned set rows first; the app adds a simple equipment/action visual so the movement is easier to recognize.") {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Exercise") },
                singleLine = true
            )
            OutlinedTextField(
                value = muscle,
                onValueChange = { muscle = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Target muscle") },
                singleLine = true
            )
            ExerciseVisualRecognitionPreview(name = name, targetMuscle = muscle)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                NumberField(value = sets, onChange = { sets = it }, label = "Sets", modifier = Modifier.weight(1f))
                OutlinedTextField(
                    value = reps,
                    onValueChange = { reps = it },
                    modifier = Modifier.weight(1f),
                    label = { Text("Target reps") },
                    singleLine = true
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                DecimalField(value = load, onChange = { load = it }, label = "Load kg", modifier = Modifier.weight(1f))
                DecimalField(value = rir, onChange = { rir = it }, label = "RIR", modifier = Modifier.weight(1f))
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                NumberField(value = rest, onChange = { rest = it }, label = "Rest sec", modifier = Modifier.weight(1f))
                Button(
                    onClick = {
                        onAddExercise(
                            name,
                            muscle,
                            sets.toIntOrNull() ?: 0,
                            reps,
                            load.toDoubleOrNull(),
                            rir.toDoubleOrNull(),
                            notes,
                            rest.toIntOrNull() ?: 120
                        )
                        name = ""
                        muscle = ""
                        notes = ""
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Add")
                }
            }
            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Technique, stimulus, substitutions, pain") },
                minLines = 2
            )
        }

        if (session.exercises.isEmpty()) {
            EmptyState("No exercises yet. Add your first movement to start set-level tracking.")
        } else {
            session.exercises.forEachIndexed { exerciseIndex, exercise ->
                ExerciseExecutionCard(
                    exerciseIndex = exerciseIndex,
                    exercise = exercise,
                    currentLog = state.dailyLog,
                    recentLogs = state.recentLogs,
                    onRemove = { onRemoveExercise(exerciseIndex) },
                    onUpdateSetEntry = onUpdateSetEntry,
                    onCompleteSet = onCompleteSet
                )
            }
        }
    }
}

@Composable
private fun TrainingReadinessBuilderCard(builder: TrainingReadinessBuilder) {
    SectionCard(
        title = "Training Readiness Builder",
        subtitle = "Warm up, choose the first working set, adjust volume, and know when to stop before logging sets."
    ) {
        MetricGrid(
            metrics = listOf(
                "Status" to builder.statusLabel,
                "Readiness" to builder.readinessScore.toString(),
                "Recovery gate" to builder.recoveryGate
            )
        )
        Text(
            text = "Warm-up cue: ${builder.warmUpCue}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = "Ramp-up cue: ${builder.rampUpCue}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = "First working set: ${builder.firstWorkingSetCue}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = "Volume adjustment: ${builder.volumeAdjustmentCue}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = "Stop rule: ${builder.stopRule}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun ExerciseExecutionCard(
    exerciseIndex: Int,
    exercise: ExerciseEntry,
    currentLog: DailyLog,
    recentLogs: List<DailyLog>,
    onRemove: () -> Unit,
    onUpdateSetEntry: (Int, Int, Int?, Double?, Double?, String) -> Unit,
    onCompleteSet: (Int, Int) -> Unit
) {
    Card(shape = RoundedCornerShape(8.dp)) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            ExerciseVisualGuide(name = exercise.name, targetMuscle = exercise.targetMuscle)
            Row(verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                    Text(exercise.name, fontWeight = FontWeight.SemiBold)
                    Text(
                        text = "${exercise.targetMuscle.ifBlank { "Target muscle not set" }} | ${exercise.sets} x ${exercise.reps} | rest ${exercise.restSeconds}s",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    if (exercise.notes.isNotBlank()) {
                        Text(
                            text = exercise.notes,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                TextButton(onClick = onRemove) {
                    Text("Remove")
                }
            }
            MetricGrid(
                metrics = listOf(
                    "Done" to "${exercise.completedSetCount()}/${exercise.trackedSets().size}",
                    "Volume" to "${formatDecimal(exercise.volumeKg())} kg",
                    "Default RIR" to (exercise.rir?.let { formatDecimal(it) } ?: "--")
                )
            )
            ExerciseHistoryCard(summary = exercise.exerciseHistorySummary(currentLog, recentLogs))
            ProgressionCueCard(cue = exercise.progressionCue())
            exercise.trackedSets().forEachIndexed { setIndex, set ->
                if (setIndex > 0) HorizontalDivider()
                SetRow(
                    exerciseIndex = exerciseIndex,
                    setIndex = setIndex,
                    set = set,
                    onUpdateSetEntry = onUpdateSetEntry,
                    onCompleteSet = onCompleteSet
                )
            }
        }
    }
}

@Composable
private fun ExerciseHistoryCard(summary: ExerciseHistorySummary) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Top) {
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                    Text("Exercise History", fontWeight = FontWeight.SemiBold)
                    Text(
                        text = summary.statusLabel,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Text(
                    text = summary.previousDate ?: "No previous",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            MetricGrid(
                metrics = listOf(
                    "Last volume" to summary.previousVolumeKg.formatHistoryValue("kg"),
                    "Today volume" to "${formatDecimal(summary.currentVolumeKg)} kg",
                    "Best load" to "${summary.previousBestLoadKg.formatHistoryValue("kg")} -> ${summary.currentBestLoadKg.formatHistoryValue("kg")}",
                    "Best reps" to "${summary.previousBestReps?.toString() ?: "--"} -> ${summary.currentBestReps?.toString() ?: "--"}",
                    "Sets" to "${summary.previousCompletedSets?.toString() ?: "--"} -> ${summary.currentCompletedSets}",
                    "Avg RIR" to "${summary.previousAverageRir.formatHistoryValue("")} -> ${summary.currentAverageRir.formatHistoryValue("")}"
                )
            )
            Text(
                text = summary.guidance,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun ProgressionCueCard(cue: ProgressionCue) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(5.dp)) {
            Text("Progression Cue", fontWeight = FontWeight.SemiBold)
            Text(cue.label, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
            Text(
                text = "${cue.reason} ${cue.nextAction}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

private fun Double?.formatHistoryValue(unit: String): String {
    if (this == null) return "--"
    val value = formatDecimal(this)
    return if (unit.isBlank()) value else "$value $unit"
}

private fun Double?.formatSignedHistoryValue(unit: String): String {
    if (this == null) return "--"
    val value = formatDecimal(kotlin.math.abs(this))
    val prefix = when {
        this > 0.0 -> "+"
        this < 0.0 -> "-"
        else -> ""
    }
    return if (unit.isBlank()) "$prefix$value" else "$prefix$value $unit"
}

private fun Int.formatSignedInt(unit: String): String {
    val prefix = if (this > 0) "+" else ""
    return if (unit.isBlank()) "$prefix$this" else "$prefix$this $unit"
}

@Composable
private fun SetRow(
    exerciseIndex: Int,
    setIndex: Int,
    set: SetEntry,
    onUpdateSetEntry: (Int, Int, Int?, Double?, Double?, String) -> Unit,
    onCompleteSet: (Int, Int) -> Unit
) {
    var reps by remember(exerciseIndex, setIndex) { mutableStateOf(set.actualReps?.toString().orEmpty()) }
    var load by remember(exerciseIndex, setIndex) { mutableStateOf(set.loadKg?.let { formatDecimal(it) }.orEmpty()) }
    var rir by remember(exerciseIndex, setIndex) { mutableStateOf(set.rir?.let { formatDecimal(it) }.orEmpty()) }
    var notes by remember(exerciseIndex, setIndex) { mutableStateOf(set.notes) }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text("Set ${set.setNumber}", fontWeight = FontWeight.SemiBold)
                Text(
                    text = "Target ${set.targetReps.ifBlank { "--" }} reps | rest ${set.restSeconds}s",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Button(
                onClick = {
                    onUpdateSetEntry(
                        exerciseIndex,
                        setIndex,
                        reps.toIntOrNull(),
                        load.toDoubleOrNull(),
                        rir.toDoubleOrNull(),
                        notes
                    )
                    onCompleteSet(exerciseIndex, setIndex)
                },
                enabled = !set.completed
            ) {
                Text(if (set.completed) "Done" else "Complete")
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            NumberField(
                value = reps,
                onChange = {
                    reps = it
                    onUpdateSetEntry(exerciseIndex, setIndex, it.toIntOrNull(), load.toDoubleOrNull(), rir.toDoubleOrNull(), notes)
                },
                label = "Reps",
                modifier = Modifier.weight(1f)
            )
            DecimalField(
                value = load,
                onChange = {
                    load = it
                    onUpdateSetEntry(exerciseIndex, setIndex, reps.toIntOrNull(), it.toDoubleOrNull(), rir.toDoubleOrNull(), notes)
                },
                label = "kg",
                modifier = Modifier.weight(1f)
            )
            DecimalField(
                value = rir,
                onChange = {
                    rir = it
                    onUpdateSetEntry(exerciseIndex, setIndex, reps.toIntOrNull(), load.toDoubleOrNull(), it.toDoubleOrNull(), notes)
                },
                label = "RIR",
                modifier = Modifier.weight(1f)
            )
        }
        OutlinedTextField(
            value = notes,
            onValueChange = {
                notes = it
                onUpdateSetEntry(exerciseIndex, setIndex, reps.toIntOrNull(), load.toDoubleOrNull(), rir.toDoubleOrNull(), it)
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Set notes") },
            minLines = 1
        )
    }
}

@Composable
private fun MealTemplateLibrary(onAddMealTemplate: (String) -> Unit) {
    SectionCard(
        title = "Meal Templates",
        subtitle = "Quick-add reliable meals, then edit notes or use photos when portions are uncertain."
    ) {
        mealTemplates().forEach { template ->
            MealTemplateCard(template = template, onAddMealTemplate = onAddMealTemplate)
        }
    }
}

@Composable
private fun MealTemplateCard(template: MealTemplate, onAddMealTemplate: (String) -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.Top) {
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                    Text(template.title, fontWeight = FontWeight.SemiBold)
                    Text(
                        text = template.subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                TextButton(onClick = { onAddMealTemplate(template.id) }) {
                    Text("Add")
                }
            }
            MetricGrid(
                metrics = listOf(
                    "Kcal" to template.calories.toString(),
                    "Protein" to "${formatDecimal(template.protein)} g",
                    "Carbs" to "${formatDecimal(template.carbs)} g",
                    "Fat" to "${formatDecimal(template.fat)} g",
                    "Fiber" to "${formatDecimal(template.fiber)} g"
                )
            )
            Text(
                text = template.notes,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun NutritionPage(
    state: CoachUiState,
    onTargetsChange: (DailyTargets) -> Unit,
    onAddMeal: (String, Int, Double, Double, Double, Double, String) -> Unit,
    onAddMealTemplate: (String) -> Unit,
    onRemoveMeal: (Int) -> Unit,
    onPickMealPhoto: () -> Unit
) {
    val targets = state.dailyLog.targets
    var mealName by remember { mutableStateOf("") }
    var calories by remember { mutableStateOf("") }
    var protein by remember { mutableStateOf("") }
    var carbs by remember { mutableStateOf("") }
    var fat by remember { mutableStateOf("") }
    var fiber by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    val totals = state.dailyLog.nutritionTotals()

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        NutritionPacingCard(log = state.dailyLog)
        NextMealBuilderCard(log = state.dailyLog)
        BodyCompositionCard(
            guidance = bodyCompositionGuidance(state.dailyLog, state.recentLogs, state.athleteProfile),
            subtitle = "Use body-weight trend, average intake, and phase goal before changing targets."
        )
        MealTemplateLibrary(onAddMealTemplate = onAddMealTemplate)
        SectionCard(title = "Nutrition Targets", subtitle = "Use weighed food when possible; use photos for AI estimates when weighing is not practical.") {
            MacroLine("Calories", totals.calories.toString(), targets.calories.toString(), "kcal")
            MacroLine("Protein", totals.protein.toString(), targets.protein.toString(), "g")
            MacroLine("Carbs", totals.carbs.toString(), targets.carbs.toString(), "g")
            MacroLine("Fat", totals.fat.toString(), targets.fat.toString(), "g")
            MacroLine("Fiber", totals.fiber.toString(), targets.fiber.toString(), "g")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                NumberField(
                    value = targets.calories.toString(),
                    onChange = { onTargetsChange(targets.copy(calories = it.toIntOrNull() ?: targets.calories)) },
                    label = "Target kcal",
                    modifier = Modifier.weight(1f)
                )
                NumberField(
                    value = targets.protein.toString(),
                    onChange = { onTargetsChange(targets.copy(protein = it.toIntOrNull() ?: targets.protein)) },
                    label = "Protein",
                    modifier = Modifier.weight(1f)
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                NumberField(
                    value = targets.carbs.toString(),
                    onChange = { onTargetsChange(targets.copy(carbs = it.toIntOrNull() ?: targets.carbs)) },
                    label = "Carbs",
                    modifier = Modifier.weight(1f)
                )
                NumberField(
                    value = targets.fat.toString(),
                    onChange = { onTargetsChange(targets.copy(fat = it.toIntOrNull() ?: targets.fat)) },
                    label = "Fat",
                    modifier = Modifier.weight(1f)
                )
            }
        }

        SectionCard(title = "Add Meal", subtitle = "Log macros manually or attach food photos for portion and label analysis.") {
            OutlinedTextField(
                value = mealName,
                onValueChange = { mealName = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Meal") },
                singleLine = true
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                NumberField(value = calories, onChange = { calories = it }, label = "kcal", modifier = Modifier.weight(1f))
                DecimalField(value = protein, onChange = { protein = it }, label = "Protein", modifier = Modifier.weight(1f))
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                DecimalField(value = carbs, onChange = { carbs = it }, label = "Carbs", modifier = Modifier.weight(1f))
                DecimalField(value = fat, onChange = { fat = it }, label = "Fat", modifier = Modifier.weight(1f))
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                DecimalField(value = fiber, onChange = { fiber = it }, label = "Fiber", modifier = Modifier.weight(1f))
                ElevatedButton(onClick = onPickMealPhoto, modifier = Modifier.weight(1f)) {
                    Text("meal photo")
                }
            }
            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Food source, portion, oils, label, uncertainty") },
                minLines = 2
            )
            Button(
                onClick = {
                    onAddMeal(
                        mealName,
                        calories.toIntOrNull() ?: 0,
                        protein.toDoubleOrNull() ?: 0.0,
                        carbs.toDoubleOrNull() ?: 0.0,
                        fat.toDoubleOrNull() ?: 0.0,
                        fiber.toDoubleOrNull() ?: 0.0,
                        notes
                    )
                    mealName = ""
                    calories = ""
                    protein = ""
                    carbs = ""
                    fat = ""
                    fiber = ""
                    notes = ""
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add meal")
            }
        }

        if (state.dailyLog.meals.isEmpty()) {
            EmptyState("No meals logged yet. Add meals or attach photos before running a nutrition review.")
        } else {
            SectionCard(title = "Meals", subtitle = "${state.dailyLog.meals.size} logged today") {
                state.dailyLog.meals.forEachIndexed { index, meal ->
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.Top) {
                        Text(
                            text = "${meal.name}: ${meal.calories} kcal, P ${formatDecimal(meal.protein)} g, C ${formatDecimal(meal.carbs)} g, F ${formatDecimal(meal.fat)} g",
                            modifier = Modifier.weight(1f)
                        )
                        TextButton(onClick = { onRemoveMeal(index) }) {
                            Text("Remove")
                        }
                    }
                    if (meal.notes.isNotBlank()) {
                        Text(
                            text = meal.notes,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MetricsPage(
    state: CoachUiState,
    onMetricsChange: (DailyMetrics) -> Unit,
    onReflectionChange: (String) -> Unit,
    onConnectHealthData: () -> Unit,
    onSyncHealthData: () -> Unit
) {
    val metrics = state.dailyLog.metrics
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        HealthConnectCard(
            state = state,
            onConnectHealthData = onConnectHealthData,
            onSyncHealthData = onSyncHealthData
        )
        RecoveryGuidanceCard(
            guidance = recoveryGuidance(state.dailyLog, state.recentLogs),
            subtitle = "Conservative training-pressure guidance from logged recovery and health signals."
        )
        TrendOverviewCard(logs = state.recentLogs)
        SectionCard(title = "Metrics", subtitle = "These recovery and physique signals help AI decide whether to push, hold, deload, or adjust food.") {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                DecimalField(
                    value = metrics.bodyWeightKg?.toString().orEmpty(),
                    onChange = { onMetricsChange(metrics.copy(bodyWeightKg = it.toDoubleOrNull())) },
                    label = "Weight kg",
                    modifier = Modifier.weight(1f)
                )
                DecimalField(
                    value = metrics.bodyFatPercent?.toString().orEmpty(),
                    onChange = { onMetricsChange(metrics.copy(bodyFatPercent = it.toDoubleOrNull())) },
                    label = "Body fat %",
                    modifier = Modifier.weight(1f)
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                DecimalField(
                    value = metrics.leanBodyMassKg?.toString().orEmpty(),
                    onChange = { onMetricsChange(metrics.copy(leanBodyMassKg = it.toDoubleOrNull())) },
                    label = "Lean mass kg",
                    modifier = Modifier.weight(1f)
                )
                DecimalField(
                    value = metrics.waistCm?.toString().orEmpty(),
                    onChange = { onMetricsChange(metrics.copy(waistCm = it.toDoubleOrNull())) },
                    label = "Waist cm",
                    modifier = Modifier.weight(1f)
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                DecimalField(
                    value = metrics.sleepHours?.toString().orEmpty(),
                    onChange = { onMetricsChange(metrics.copy(sleepHours = it.toDoubleOrNull())) },
                    label = "Sleep h",
                    modifier = Modifier.weight(1f)
                )
                NumberField(
                    value = metrics.steps.toString(),
                    onChange = { onMetricsChange(metrics.copy(steps = it.toIntOrNull() ?: metrics.steps)) },
                    label = "Steps",
                    modifier = Modifier.weight(1f)
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                DecimalField(
                    value = metrics.restingHeartRateBpm?.toString().orEmpty(),
                    onChange = { onMetricsChange(metrics.copy(restingHeartRateBpm = it.toDoubleOrNull())) },
                    label = "Resting HR",
                    modifier = Modifier.weight(1f)
                )
                DecimalField(
                    value = metrics.totalCaloriesBurnedKcal?.toString().orEmpty(),
                    onChange = { onMetricsChange(metrics.copy(totalCaloriesBurnedKcal = it.toDoubleOrNull())) },
                    label = "Burned kcal",
                    modifier = Modifier.weight(1f)
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                NumberField(
                    value = metrics.hunger.toString(),
                    onChange = { onMetricsChange(metrics.copy(hunger = it.toIntOrNull() ?: metrics.hunger)) },
                    label = "Hunger 1-5",
                    modifier = Modifier.weight(1f)
                )
                NumberField(
                    value = metrics.fatigue.toString(),
                    onChange = { onMetricsChange(metrics.copy(fatigue = it.toIntOrNull() ?: metrics.fatigue)) },
                    label = "Fatigue 1-5",
                    modifier = Modifier.weight(1f)
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                NumberField(
                    value = metrics.soreness.toString(),
                    onChange = { onMetricsChange(metrics.copy(soreness = it.toIntOrNull() ?: metrics.soreness)) },
                    label = "Soreness 1-5",
                    modifier = Modifier.weight(1f)
                )
                NumberField(
                    value = metrics.stress.toString(),
                    onChange = { onMetricsChange(metrics.copy(stress = it.toIntOrNull() ?: metrics.stress)) },
                    label = "Stress 1-5",
                    modifier = Modifier.weight(1f)
                )
            }
            if (metrics.healthDataSource.isNotBlank()) {
                Text(
                    text = "Last health sync: ${metrics.healthDataSource} at ${metrics.healthSyncedAt.ifBlank { "--" }}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            OutlinedTextField(
                value = state.dailyLog.reflection,
                onValueChange = onReflectionChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Daily reflection") },
                minLines = 3
            )
        }
    }
}

@Composable
private fun HealthConnectCard(
    state: CoachUiState,
    onConnectHealthData: () -> Unit,
    onSyncHealthData: () -> Unit
) {
    val snapshot = state.healthSnapshot
    SectionCard(
        title = "Health Connect",
        subtitle = "Read user-authorized body and recovery records from Health Connect, then feed them into daily AI review."
    ) {
        MetricGrid(
            metrics = listOf(
                "Status" to when {
                    snapshot.permissionsGranted -> "Authorized"
                    snapshot.available -> "Needs access"
                    else -> "Unavailable"
                },
                "Weight" to formatOptional(snapshot.bodyWeightKg, "kg"),
                "Body fat" to formatOptional(snapshot.bodyFatPercent, "%"),
                "Steps" to (snapshot.steps?.toString() ?: "--"),
                "Sleep" to formatOptional(snapshot.sleepHours, "h"),
                "Resting HR" to formatOptional(snapshot.restingHeartRateBpm, "bpm"),
                "Calories" to formatOptional(snapshot.totalCaloriesBurnedKcal, "kcal")
            )
        )
        DataChipGrid(
            items = listOf(
                "Xiaomi/Mi Fitness -> Health Connect",
                "Huawei Health -> Health Connect or Health Kit",
                "Scale/watch/phone -> Health Connect",
                "Manual fallback"
            )
        )
        Text(
            text = snapshot.message.ifBlank { "Connect Health Connect to import metrics into the AI review." },
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = "Vendor data is readable only when the source app writes compatible records into Health Connect and you approve permissions. Huawei Health Kit can be added as a dedicated provider for deeper Huawei ecosystem syncing.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = onConnectHealthData,
                enabled = !state.isHealthSyncing,
                modifier = Modifier.weight(1f)
            ) {
                Text("Connect health data")
            }
            ElevatedButton(
                onClick = onSyncHealthData,
                enabled = !state.isHealthSyncing,
                modifier = Modifier.weight(1f)
            ) {
                Text(if (state.isHealthSyncing) "Syncing" else "Sync today")
            }
        }
        if (state.isHealthSyncing) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
@OptIn(ExperimentalLayoutApi::class)
private fun AiCoachPage(
    state: CoachUiState,
    onSettingsChange: (AiSettings) -> Unit,
    onModeChange: (CoachMode) -> Unit,
    onPromptChange: (String) -> Unit,
    onPickImages: () -> Unit,
    onClearImages: () -> Unit,
    onRunAnalysis: () -> Unit,
    onDailyReview: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SettingsCard(settings = state.settings, onChange = onSettingsChange)
        SectionCard(title = "AI Coach", subtitle = "Use the bundled skill, daily logs, and optional photos together.") {
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CoachMode.entries.forEach { mode ->
                    FilterChip(
                        selected = state.mode == mode,
                        onClick = { onModeChange(mode) },
                        label = { Text(mode.title) }
                    )
                }
            }
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                value = state.userInput,
                onValueChange = onPromptChange,
                label = { Text("Question, goal, or extra AI review instruction") },
                minLines = 5
            )
            ImageCard(images = state.images, onPick = onPickImages, onClear = onClearImages)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = onDailyReview, enabled = !state.isLoading, modifier = Modifier.weight(1f)) {
                    Text("Daily review")
                }
                ElevatedButton(onClick = onRunAnalysis, enabled = !state.isLoading, modifier = Modifier.weight(1f)) {
                    Text("Run mode")
                }
            }
            if (state.isLoading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }
        }
        ReviewHistoryCard(reviews = state.reviewHistory)
        SectionCard(title = "AI Data Map", subtitle = "These app records are designed to be linked in the same analysis request.") {
            DataChipGrid(
                items = listOf(
                    "Exercise selection",
                    "Set load",
                    "Actual reps",
                    "RIR/RPE intent",
                    "Rest time",
                    "Hard sets",
                    "Tonnage",
                    "Training Readiness Builder",
                    "Warm-up cue",
                    "Ramp-up cue",
                    "First working set",
                    "Volume adjustment",
                    "Stop rule",
                    "Progression Cue",
                    "Exercise History",
                    "Exercise visual guide",
                    "Visual guide ID",
                    "Equipment/action diagrams",
                    "Live equipment/action preview",
                    "Chinese equipment labels",
                    "Unified instance diagram",
                    "Action path cue",
                    "Beginner recognition cue",
                    "Equipment markers",
                    "Instance diagram cue",
                    "Common movement examples",
                    "Look-for cue",
                    "Equipment photo recognition",
                    "Exercise name -> visual category",
                    "Technique notes",
                    "Pain flags",
                    "Meal macros",
                    "Nutrition Pacing",
                    "Next Meal Builder",
                    "Next meal macro targets",
                    "Meal timing cue",
                    "Portion uncertainty cue",
                    "Body Composition Guidance",
                    "Recovery Guidance",
                    "Food photos",
                    "Equipment photos",
                    "Form photos",
                    "Body weight",
                    "Body fat",
                    "Lean mass",
                    "Waist",
                    "Sleep",
                    "Steps",
                    "Resting HR",
                    "Calorie burn",
                    "Hunger",
                    "Fatigue",
                    "Soreness",
                    "Stress"
                )
            )
        }
    }
}

@Composable
private fun ReviewHistoryCard(reviews: List<AiReviewEntry>) {
    SectionCard(title = "Saved AI Reviews", subtitle = "Recent guidance stays available after you close the app.") {
        if (reviews.isEmpty()) {
            EmptyState("Run a daily review to save the first AI coaching note.")
        } else {
            reviews.take(5).forEach { review ->
                Text(
                    text = "${review.logDate} | ${review.modeTitle} | ${review.createdAt}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = review.preview(),
                    style = MaterialTheme.typography.bodySmall
                )
                if (review != reviews.take(5).last()) {
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
private fun SettingsCard(settings: AiSettings, onChange: (AiSettings) -> Unit) {
    SectionCard(title = "API Settings", subtitle = "Bring your own AI provider key; credentials stay in local SharedPreferences.") {
        OutlinedTextField(
            value = settings.baseUrl,
            onValueChange = { onChange(settings.copy(baseUrl = it)) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Base URL") },
            singleLine = true
        )
        OutlinedTextField(
            value = settings.model,
            onValueChange = { onChange(settings.copy(model = it)) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Model") },
            singleLine = true
        )
        OutlinedTextField(
            value = settings.apiKey,
            onValueChange = { onChange(settings.copy(apiKey = it)) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("API key") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation()
        )
    }
}

@Composable
private fun ImageCard(images: List<ImageAttachment>, onPick: () -> Unit, onClear: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                ElevatedButton(onClick = onPick) {
                    Text("Add photos")
                }
                TextButton(onClick = onClear, enabled = images.isNotEmpty()) {
                    Text("Clear")
                }
            }
            if (images.isEmpty()) {
                Text(
                    text = "Attach exercise frames, equipment photos, food photos, labels, menus, or progress photos.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                images.forEach { image ->
                    Text("${image.name} (${image.mimeType})", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

@Composable
private fun MessageCard(title: String, body: String) {
    Card(shape = RoundedCornerShape(8.dp)) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(title, fontWeight = FontWeight.SemiBold)
            Text(body)
        }
    }
}

@Composable
private fun SectionCard(title: String, subtitle: String? = null, content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                Text(title, fontWeight = FontWeight.SemiBold)
                subtitle?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            content()
        }
    }
}

@Composable
@OptIn(ExperimentalLayoutApi::class)
private fun MetricGrid(metrics: List<Pair<String, String>>) {
    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        metrics.forEach { (label, value) ->
            Surface(
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                color = MaterialTheme.colorScheme.surface
            ) {
                Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)) {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(text = value, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalLayoutApi::class)
private fun DataChipGrid(items: List<String>) {
    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items.forEach { item ->
            Surface(
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                color = MaterialTheme.colorScheme.surface
            ) {
                Text(
                    text = item,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
private fun MacroLine(label: String, current: String, target: String, unit: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label)
        Text("$current / $target $unit", fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun EmptyState(text: String) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(14.dp),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun NumberField(value: String, onChange: (String) -> Unit, label: String, modifier: Modifier = Modifier) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        modifier = modifier,
        label = { Text(label) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}

@Composable
private fun DecimalField(value: String, onChange: (String) -> Unit, label: String, modifier: Modifier = Modifier) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        modifier = modifier,
        label = { Text(label) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
    )
}

@Composable
private fun SafetyNote() {
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = "This app is coaching support, not medical care. Photo form checks and food estimates are approximate.",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

private fun formatTimer(seconds: Int): String {
    val minutes = seconds / 60
    val remainder = seconds % 60
    return "%d:%02d".format(Locale.US, minutes, remainder)
}

private fun formatOptional(value: Double?, unit: String): String {
    return value?.let { "${formatDecimal(it)} $unit" } ?: "--"
}

private fun formatRemaining(value: Int, unit: String): String {
    return when {
        value > 0 -> "$value $unit left"
        value < 0 -> "${kotlin.math.abs(value)} $unit over"
        else -> "on target"
    }
}

private fun formatDecimal(value: Double): String {
    return if (value % 1.0 == 0.0) {
        value.toInt().toString()
    } else {
        "%.1f".format(Locale.US, value)
    }
}

private fun formatSigned(value: Double): String {
    val prefix = if (value > 0) "+" else ""
    return "$prefix${formatDecimal(value)}"
}

private fun List<Int>.averageIntOrNull(): Double? = takeIf { it.isNotEmpty() }?.map { it.toDouble() }?.average()

private fun List<Double>.averageDoubleOrNull(): Double? = takeIf { it.isNotEmpty() }?.average()
