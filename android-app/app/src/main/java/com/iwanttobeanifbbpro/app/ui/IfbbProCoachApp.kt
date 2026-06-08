package com.iwanttobeanifbbpro.app.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.iwanttobeanifbbpro.app.core.CoachMode
import com.iwanttobeanifbbpro.app.data.AiReviewEntry
import com.iwanttobeanifbbpro.app.data.AthleteProfile
import com.iwanttobeanifbbpro.app.data.DailyLog
import com.iwanttobeanifbbpro.app.data.DailyMetrics
import com.iwanttobeanifbbpro.app.data.DailyTargets
import com.iwanttobeanifbbpro.app.data.ExerciseEntry
import com.iwanttobeanifbbpro.app.data.PlannedExercise
import com.iwanttobeanifbbpro.app.data.SetEntry
import com.iwanttobeanifbbpro.app.data.TrainingDay
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
                            onOpenMetrics = { viewModel.selectTab(AppTab.METRICS) }
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
private fun TodayActionGrid(
    state: CoachUiState,
    onOpenPlan: () -> Unit,
    onOpenTraining: () -> Unit,
    onOpenNutrition: () -> Unit,
    onOpenMetrics: () -> Unit
) {
    val log = state.dailyLog
    val totals = log.nutritionTotals()
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
            value = "${totals.calories}/${log.targets.calories}",
            detail = "Protein ${totals.protein}/${log.targets.protein} g",
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
    val lastWeight = window.lastNotNullOfOrNull { it.metrics.bodyWeightKg }
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
    onOpenMetrics: () -> Unit
) {
    val log = state.dailyLog
    val totals = log.nutritionTotals()
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
        TrendOverviewCard(logs = state.recentLogs)
        BeginnerGuideCard(onOpenPlan = onOpenPlan, onOpenNutrition = onOpenNutrition, onOpenMetrics = onOpenMetrics)
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

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
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

        SectionCard(title = "Add Exercise", subtitle = "Create planned set rows first, then update actual reps and load during the workout.") {
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
                    onRemove = { onRemoveExercise(exerciseIndex) },
                    onUpdateSetEntry = onUpdateSetEntry,
                    onCompleteSet = onCompleteSet
                )
            }
        }
    }
}

@Composable
private fun ExerciseExecutionCard(
    exerciseIndex: Int,
    exercise: ExerciseEntry,
    onRemove: () -> Unit,
    onUpdateSetEntry: (Int, Int, Int?, Double?, Double?, String) -> Unit,
    onCompleteSet: (Int, Int) -> Unit
) {
    Card(shape = RoundedCornerShape(8.dp)) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
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
private fun NutritionPage(
    state: CoachUiState,
    onTargetsChange: (DailyTargets) -> Unit,
    onAddMeal: (String, Int, Double, Double, Double, Double, String) -> Unit,
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
                    "Technique notes",
                    "Pain flags",
                    "Meal macros",
                    "Food photos",
                    "Equipment photos",
                    "Form photos",
                    "Body weight",
                    "Waist",
                    "Sleep",
                    "Steps",
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
