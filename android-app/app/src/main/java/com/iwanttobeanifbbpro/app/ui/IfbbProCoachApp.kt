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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.MonitorHeart
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.iwanttobeanifbbpro.app.core.AiReviewActionItem
import com.iwanttobeanifbbpro.app.core.AiReviewActionQueue
import com.iwanttobeanifbbpro.app.core.AiIntegratedDecisionMatrix
import com.iwanttobeanifbbpro.app.core.AiPlanAdjustmentProposal
import com.iwanttobeanifbbpro.app.core.AiRestPrescription
import com.iwanttobeanifbbpro.app.core.BodyCompositionGuidance
import com.iwanttobeanifbbpro.app.core.CoachMode
import com.iwanttobeanifbbpro.app.core.ConditioningHydrationGuidance
import com.iwanttobeanifbbpro.app.core.DailyExecutionPlan
import com.iwanttobeanifbbpro.app.core.DailyExecutionRoute
import com.iwanttobeanifbbpro.app.core.ExerciseHistorySummary
import com.iwanttobeanifbbpro.app.core.ExerciseSubstitutionGuide
import com.iwanttobeanifbbpro.app.core.ExerciseVisualSpec
import com.iwanttobeanifbbpro.app.core.ExerciseVisualType
import com.iwanttobeanifbbpro.app.core.MealAssemblyGuide
import com.iwanttobeanifbbpro.app.core.NextSetCoach
import com.iwanttobeanifbbpro.app.core.PhysiqueMeasurementSummary
import com.iwanttobeanifbbpro.app.core.ProgressionCue
import com.iwanttobeanifbbpro.app.core.RecoveryGuidance
import com.iwanttobeanifbbpro.app.core.SessionQualityDashboard
import com.iwanttobeanifbbpro.app.core.TomorrowCoachBrief
import com.iwanttobeanifbbpro.app.core.TrainingCloseoutCoach
import com.iwanttobeanifbbpro.app.core.TrainingReadinessBuilder
import com.iwanttobeanifbbpro.app.core.WarmUpRampPlan
import com.iwanttobeanifbbpro.app.core.WeeklyCheckInSummary
import com.iwanttobeanifbbpro.app.core.aiReviewActionQueue
import com.iwanttobeanifbbpro.app.core.aiIntegratedDecisionMatrix
import com.iwanttobeanifbbpro.app.core.aiPlanAdjustmentProposal
import com.iwanttobeanifbbpro.app.core.aiRestPrescription
import com.iwanttobeanifbbpro.app.core.bodyCompositionGuidance
import com.iwanttobeanifbbpro.app.core.conditioningHydrationGuidance
import com.iwanttobeanifbbpro.app.core.dailyExecutionPlan
import com.iwanttobeanifbbpro.app.core.exerciseVisualAtlas
import com.iwanttobeanifbbpro.app.core.exerciseVisualSpec
import com.iwanttobeanifbbpro.app.core.exerciseHistorySummary
import com.iwanttobeanifbbpro.app.core.exerciseSubstitutionGuide
import com.iwanttobeanifbbpro.app.core.progressionCue
import com.iwanttobeanifbbpro.app.core.mealAssemblyGuide
import com.iwanttobeanifbbpro.app.core.nextSetCoach
import com.iwanttobeanifbbpro.app.core.physiqueMeasurementSummary
import com.iwanttobeanifbbpro.app.core.recoveryGuidance
import com.iwanttobeanifbbpro.app.core.sessionQualityDashboard
import com.iwanttobeanifbbpro.app.core.tomorrowCoachBrief
import com.iwanttobeanifbbpro.app.core.trainingCloseoutCoach
import com.iwanttobeanifbbpro.app.core.trainingReadinessBuilder
import com.iwanttobeanifbbpro.app.core.warmUpRampPlan
import com.iwanttobeanifbbpro.app.core.weeklyCheckInSummary
import com.iwanttobeanifbbpro.app.data.AiReviewEntry
import com.iwanttobeanifbbpro.app.data.AthleteProfile
import com.iwanttobeanifbbpro.app.data.DailyConditioning
import com.iwanttobeanifbbpro.app.data.DailyLog
import com.iwanttobeanifbbpro.app.data.DailyMetrics
import com.iwanttobeanifbbpro.app.data.DailyTargets
import com.iwanttobeanifbbpro.app.data.ExerciseEntry
import com.iwanttobeanifbbpro.app.data.MealTemplate
import com.iwanttobeanifbbpro.app.data.PlannedExercise
import com.iwanttobeanifbbpro.app.data.PhotoEvidenceEntry
import com.iwanttobeanifbbpro.app.data.PhotoEvidenceType
import com.iwanttobeanifbbpro.app.data.SetEntry
import com.iwanttobeanifbbpro.app.data.TrainingPlanTemplate
import com.iwanttobeanifbbpro.app.data.TrainingDay
import com.iwanttobeanifbbpro.app.data.mealTemplates
import com.iwanttobeanifbbpro.app.data.trainingPlanTemplates
import com.iwanttobeanifbbpro.app.health.HealthConnectRepository
import com.iwanttobeanifbbpro.app.health.HealthSnapshot
import java.util.Locale

@Composable
fun IfbbProCoachApp(viewModel: CoachViewModel = viewModel()) {
    val state = viewModel.uiState
    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(6),
        onResult = viewModel::addPreparedImages
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
                language = state.appLanguage,
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
            item {
                Header(
                    state = state,
                    onLanguageChange = viewModel::updateLanguage
                )
            }
            state.restTimer?.let { timer ->
                item {
                    RestTimerBanner(
                        timer = timer,
                        language = state.appLanguage,
                        onSkip = viewModel::skipRestTimer
                    )
                }
            }
            item {
                GlobalNextActionStrip(
                    state = state,
                    onOpenPlan = { viewModel.selectTab(AppTab.TRAINING) },
                    onOpenTraining = { viewModel.selectTab(AppTab.TRAINING) },
                    onOpenNutrition = { viewModel.selectTab(AppTab.NUTRITION) },
                    onOpenMetrics = { viewModel.selectTab(AppTab.METRICS) },
                    onRunAiReview = viewModel::runDailyReview,
                    onOpenAi = { viewModel.selectTab(AppTab.AI_COACH) }
                )
            }
            when (state.selectedTab) {
                AppTab.TRAINING -> {
                    item {
                        var showTrainingPlan by remember {
                            mutableStateOf(state.dailyLog.trainingSession.exercises.isEmpty())
                        }
                        val planContent: @Composable () -> Unit = {
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
                                onResetPlan = viewModel::resetTrainingPlan,
                                onOpenTraining = { showTrainingPlan = false }
                            )
                        }
                        val trainingContent: @Composable () -> Unit = {
                            TrainingPage(
                                state = state,
                                onFocusChange = viewModel::updateTrainingFocus,
                                onNotesChange = viewModel::updateSessionNotes,
                                onCompletedChange = { viewModel.toggleTrainingCompleted() },
                                onAddExercise = viewModel::addExercise,
                                onRemoveExercise = viewModel::removeExercise,
                                onUpdateSetEntry = viewModel::updateSetEntry,
                                onCompleteSet = viewModel::completeSet,
                                onRunDailyReview = viewModel::runDailyReview,
                                onOpenPlan = { showTrainingPlan = true },
                                onOpenTraining = { viewModel.selectTab(AppTab.TRAINING) },
                                onOpenNutrition = { viewModel.selectTab(AppTab.NUTRITION) },
                                onOpenMetrics = { viewModel.selectTab(AppTab.METRICS) },
                                onOpenAi = { viewModel.selectTab(AppTab.AI_COACH) },
                                onPickTrainingPhoto = { type, note ->
                                    viewModel.preparePhotoEvidence(type, note)
                                    imagePicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                                }
                            )
                        }
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            TrainingModeSwitch(
                                showPlan = showTrainingPlan,
                                language = state.appLanguage,
                                onShowToday = { showTrainingPlan = false },
                                onShowPlan = { showTrainingPlan = true }
                            )
                            if (showTrainingPlan) {
                                planContent()
                            } else {
                                trainingContent()
                            }
                        }
                    }
                }

                AppTab.NUTRITION -> {
                    item {
                        NutritionPage(
                            state = state,
                            onTargetsChange = viewModel::updateTargets,
                            onConditioningChange = viewModel::updateConditioning,
                            onAddMeal = viewModel::addMeal,
                            onAddMealTemplate = viewModel::addMealTemplate,
                            onRemoveMeal = viewModel::removeMeal,
                            onPickMealPhoto = { note ->
                                viewModel.preparePhotoEvidence(PhotoEvidenceType.MEAL, note)
                                imagePicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                            },
                            onOpenAi = { viewModel.selectTab(AppTab.AI_COACH) }
                        )
                    }
                }

                AppTab.METRICS -> {
                    item {
                        MetricsPage(
                            state = state,
                            onMetricsChange = viewModel::updateMetrics,
                            onConditioningChange = viewModel::updateConditioning,
                            onReflectionChange = viewModel::updateReflection,
                            onPickPhysiquePhoto = {
                                viewModel.preparePhotoEvidence(PhotoEvidenceType.PHYSIQUE, "Progress photo for physique, symmetry, posture, and waist-control comparison.")
                                imagePicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                            },
                            onConnectHealthData = { healthPermissionLauncher.launch(viewModel.healthPermissions()) },
                            onSyncHealthData = viewModel::syncHealthData
                        )
                    }
                }

                AppTab.AI_COACH -> {
                    item {
                        var showAiDailyOverview by remember { mutableStateOf(false) }
                        val language = state.appLanguage
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            AiCoachPage(
                                state = state,
                                onSettingsChange = viewModel::updateSettings,
                                onModeChange = viewModel::updateMode,
                                onPromptChange = viewModel::updateUserInput,
                                onPreparePhoto = viewModel::preparePhotoEvidence,
                                onPickImages = {
                                    imagePicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                                },
                                onClearImages = viewModel::clearImages,
                                onRunAnalysis = viewModel::runAnalysis,
                                onDailyReview = viewModel::runDailyReview,
                                onOpenPlan = { viewModel.selectTab(AppTab.TRAINING) },
                                onOpenTraining = { viewModel.selectTab(AppTab.TRAINING) },
                                onOpenNutrition = { viewModel.selectTab(AppTab.NUTRITION) },
                                onOpenMetrics = { viewModel.selectTab(AppTab.METRICS) },
                                onOpenAi = { viewModel.selectTab(AppTab.AI_COACH) }
                            )
                            SectionCard(
                                title = language.t("AI Daily Overview", "AI 每日总览"),
                                subtitle = language.t(
                                    "Open this only when you want the whole daily cockpit under the AI review flow.",
                                    "只有需要在 AI 复盘下查看完整每日驾驶舱时再展开。"
                                )
                            ) {
                                Text(
                                    text = language.t(
                                        "The default AI page stays focused on review, tomorrow handoff, and API setup.",
                                        "AI 页默认聚焦复盘、明日交接和 API 设置。"
                                    ),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                TextButton(onClick = { showAiDailyOverview = !showAiDailyOverview }, modifier = Modifier.fillMaxWidth()) {
                                    Text(
                                        if (showAiDailyOverview) {
                                            language.t("Hide daily overview", "收起每日总览")
                                        } else {
                                            language.t("Show daily overview", "展开每日总览")
                                        }
                                    )
                                }
                            }
                            if (showAiDailyOverview) {
                                TodayDashboard(
                                    state = state,
                                    onDailyReview = viewModel::runDailyReview,
                                    onReset = viewModel::resetToday,
                                    onOpenPlan = { viewModel.selectTab(AppTab.TRAINING) },
                                    onOpenTraining = { viewModel.selectTab(AppTab.TRAINING) },
                                    onOpenNutrition = { viewModel.selectTab(AppTab.NUTRITION) },
                                    onOpenMetrics = { viewModel.selectTab(AppTab.METRICS) },
                                    onOpenAi = { viewModel.selectTab(AppTab.AI_COACH) }
                                )
                            }
                        }
                    }
                }
            }
            item {
                state.error?.let {
                    MessageCard(
                        title = state.appLanguage.t("Error", "错误"),
                        body = it
                    )
                }
                if (state.result.isNotBlank()) {
                    MessageCard(
                        title = state.appLanguage.t("AI Coach result", "AI 教练结果"),
                        body = state.result
                    )
                }
                SafetyNote(language = state.appLanguage)
            }
        }
    }
}

@Composable
private fun Header(state: CoachUiState, onLanguageChange: (AppLanguage) -> Unit) {
    val language = state.appLanguage
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = state.selectedTab.localizedTitle(language),
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
            )
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                AppLanguage.entries.forEach { item ->
                    FilterChip(
                        selected = language == item,
                        onClick = { onLanguageChange(item) },
                        label = { Text(item.label) }
                    )
                }
            }
        }
        Text(
            text = "I Want to be an IFBB PRO",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = language.t(
                "Four sections: training plan and sets, food, body data, and AI review.",
                "四个区：训练计划与组记录、饮食、身体数据和 AI 复盘。"
            ),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun GlobalNextActionStrip(
    state: CoachUiState,
    onOpenPlan: () -> Unit,
    onOpenTraining: () -> Unit,
    onOpenNutrition: () -> Unit,
    onOpenMetrics: () -> Unit,
    onRunAiReview: () -> Unit,
    onOpenAi: () -> Unit
) {
    val language = state.appLanguage
    val steps = state.dailyStartSteps(
        language = language,
        onOpenPlan = onOpenPlan,
        onOpenTraining = onOpenTraining,
        onOpenNutrition = onOpenNutrition,
        onOpenMetrics = onOpenMetrics,
        onRunAiReview = onRunAiReview,
        onOpenAi = onOpenAi
    )
    TodayCommandStrip(steps = steps, language = language)
}


@Composable
private fun TodayCommandStrip(steps: List<DailyStartStep>, language: AppLanguage) {
    val flow = todayFlowCoachState(steps)
    val nextStep = flow.nextStep
    val statusRows = listOf(
        language.t("Training status", "训练状态") to steps.getOrNull(1),
        language.t("Nutrition status", "饮食状态") to steps.getOrNull(2),
        language.t("Metrics status", "身体数据状态") to steps.getOrNull(3),
        language.t("AI status", "AI 状态") to steps.getOrNull(4)
    )

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, IfbbProGlassBorder),
        color = IfbbProGlassStrongSurface
    ) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.Top) {
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                    Text(
                        text = language.t("TODAY COMMAND", "今日指挥台"),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = language.t("Today Command", "今日指挥台"),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = language.t(
                            "One command for today: Train -> Eat -> Sync -> Review. One tap daily start.",
                            "今天只跟随一个指令：训练 -> 饮食 -> 同步 -> 复盘。一键式每日开始。"
                        ),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Text(
                    text = "${flow.doneCount}/${flow.totalCount}",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.58f),
                border = BorderStroke(1.dp, IfbbProGlassBorder)
            ) {
                Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = language.t("TODAY NEXT", "今天下一步"),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = nextStep?.title ?: language.t("Daily loop complete", "今日闭环已完成"),
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = nextStep?.detail ?: language.t(
                            "Review saved guidance, prepare tomorrow, and keep logging changes.",
                            "查看已保存建议，准备明天计划，并继续记录变化。"
                        ),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            LinearProgressIndicator(progress = { flow.progress }, modifier = Modifier.fillMaxWidth())
            Button(
                onClick = { nextStep?.onAction?.invoke() },
                enabled = nextStep?.actionEnabled == true,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(nextStep?.actionLabel ?: language.t("Review tomorrow", "查看明天安排"))
            }
            DataChipGrid(
                items = statusRows.map { (label, step) ->
                    val status = if (step?.done == true) language.t("Done", "已完成") else language.t("Next", "待完成")
                    "$label: $status"
                }
            )
            Text(
                text = language.t(
                    "Evidence confidence rises when training sets, food, sleep, body trend, and photos are linked before AI changes the plan.",
                    "训练组数、饮食、睡眠、身体趋势和照片联动后，AI 调整计划的证据置信度会更高。"
                ),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun BottomNavigation(selected: AppTab, language: AppLanguage, onSelect: (AppTab) -> Unit) {
    NavigationBar(containerColor = GlassNavigationBarColor) {
        AppTab.entries.forEach { tab ->
            NavigationBarItem(
                selected = selected == tab,
                onClick = { onSelect(tab) },
                icon = {
                    Icon(
                        imageVector = tab.navIcon(),
                        contentDescription = tab.localizedTitle(language)
                    )
                },
                label = { Text(tab.shortTitle(language)) }
            )
        }
    }
}

@Composable
private fun TrainingModeSwitch(
    showPlan: Boolean,
    language: AppLanguage,
    onShowToday: () -> Unit,
    onShowPlan: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, IfbbProGlassBorder),
        color = IfbbProGlassSurface
    ) {
        Row(
            modifier = Modifier.padding(6.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            FilterChip(
                selected = !showPlan,
                onClick = onShowToday,
                modifier = Modifier.weight(1f),
                label = { Text(language.t("Today workout", "今日训练")) }
            )
            FilterChip(
                selected = showPlan,
                onClick = onShowPlan,
                modifier = Modifier.weight(1f),
                label = { Text(language.t("Plan library", "计划库")) }
            )
        }
    }
}

private fun AppTab.navIcon(): ImageVector {
    return when (this) {
        AppTab.TRAINING -> Icons.Filled.FitnessCenter
        AppTab.NUTRITION -> Icons.Filled.Restaurant
        AppTab.METRICS -> Icons.Filled.MonitorHeart
        AppTab.AI_COACH -> Icons.Filled.AutoAwesome
    }
}

private fun AppTab.shortTitle(language: AppLanguage): String {
    return when (this) {
        AppTab.TRAINING -> language.t("Training", "训练")
        AppTab.NUTRITION -> language.t("Nutrition", "饮食")
        AppTab.METRICS -> language.t("Metrics", "身体数据")
        AppTab.AI_COACH -> language.t("AI", "AI")
    }
}

private fun AppTab.localizedTitle(language: AppLanguage): String {
    return when (this) {
        AppTab.TRAINING -> language.t("Training", "训练")
        AppTab.NUTRITION -> language.t("Nutrition", "饮食")
        AppTab.METRICS -> language.t("Metrics", "身体数据")
        AppTab.AI_COACH -> language.t("AI", "AI")
    }
}

private fun AppLanguage.t(en: String, zh: String): String {
    return if (this == AppLanguage.CHINESE) zh else en
}

private fun BodyCompositionGuidance.localizedStatusLabel(language: AppLanguage): String {
    return when (statusLabel) {
        "Need trend data" -> language.t("Need trend data", "需要趋势数据")
        "Small calorie increase" -> language.t("Small calorie increase", "小幅增加热量")
        "Small calorie decrease" -> language.t("Small calorie decrease", "小幅降低热量")
        "Hold targets" -> language.t("Hold targets", "保持目标")
        else -> statusLabel
    }
}

private fun BodyCompositionGuidance.localizedPhaseGoal(language: AppLanguage): String {
    return when (phaseGoal) {
        "Fat loss" -> language.t("Fat loss", "减脂")
        "Lean gain" -> language.t("Lean gain", "精益增肌")
        "Maintain/Recomp" -> language.t("Maintain/Recomp", "维持/重组")
        "Physique improvement" -> language.t("Physique improvement", "体型提升")
        else -> phaseGoal
    }
}

private fun BodyCompositionGuidance.localizedRationale(language: AppLanguage): String {
    return when (rationale) {
        "Log body weight and food for at least four days before changing targets." -> language.t(
            "Log body weight and food for at least four days before changing targets.",
            "调整目标前，至少连续四天记录体重和饮食。"
        )
        "Recent weight trend is below the phase target, so a small increase is safer than a large jump." -> language.t(
            "Recent weight trend is below the phase target, so a small increase is safer than a large jump.",
            "近期体重趋势低于阶段目标，小幅增加比大幅跳升更稳妥。"
        )
        "Recent weight trend is above the phase target, so a small decrease is enough before reassessing." -> language.t(
            "Recent weight trend is above the phase target, so a small decrease is enough before reassessing.",
            "近期体重趋势高于阶段目标，先小幅降低再重新评估。"
        )
        "Recent weight, calories, protein, and training trend do not justify changing targets yet." -> language.t(
            "Recent weight, calories, protein, and training trend do not justify changing targets yet.",
            "近期体重、热量、蛋白质和训练趋势还不足以支持修改目标。"
        )
        else -> rationale
    }
}

private fun BodyCompositionGuidance.localizedNextAction(language: AppLanguage): String {
    return when (nextAction) {
        "Keep current targets, weigh in consistently, and complete meal logs." -> language.t(
            "Keep current targets, weigh in consistently, and complete meal logs.",
            "保持当前目标，稳定称重，并补完整餐食记录。"
        )
        "Fix protein consistency before changing calories aggressively." -> language.t(
            "Fix protein consistency before changing calories aggressively.",
            "先稳定蛋白质摄入，再大幅调整热量。"
        )
        "Confirm training output before using scale trend alone." -> language.t(
            "Confirm training output before using scale trend alone.",
            "先确认训练输出，再单独依据体重趋势调整。"
        )
        "Hold calories for another check-in and reassess with the next trend window." -> language.t(
            "Hold calories for another check-in and reassess with the next trend window.",
            "热量先保持到下一次打卡，再用新的趋势窗口重新评估。"
        )
        "Add mostly carbs around training or lean protein if protein is behind." -> language.t(
            "Add mostly carbs around training or lean protein if protein is behind.",
            "优先在训练前后增加碳水；如果蛋白落后，就补低脂蛋白。"
        )
        "Remove calories from low-satiety fats/snacks before cutting training carbs." -> language.t(
            "Remove calories from low-satiety fats/snacks before cutting training carbs.",
            "先从低饱腹感脂肪或零食里减少热量，再削减训练碳水。"
        )
        else -> nextAction
    }
}

private fun ConditioningHydrationGuidance.localizedStatusLabel(language: AppLanguage): String {
    return statusLabel.localizedConditioningText(language)
}

private fun String.localizedConditioningText(language: AppLanguage): String {
    if (language != AppLanguage.CHINESE) return this
    var localized = this
    val replacements = listOf(
        "Cut/recomp support ready" to "减脂/重组支持变量就绪",
        "NEAT behind" to "日常活动偏低",
        "Hydration gap" to "补水缺口",
        "Stimulant pressure" to "兴奋剂压力",
        "Recovery confounder" to "恢复干扰因素",
        "Cardio optional" to "有氧可选",
        "Support variables logged" to "支持变量已记录",
        "No cardio logged; use steps first, then low-intensity cardio if fat-loss pace needs help." to "未记录有氧；先补步数，如果减脂速度需要帮助，再加低强度有氧。",
        "Cardio below today's phase target; keep it easy enough not to steal leg recovery." to "有氧低于今天阶段目标；保持轻松，避免影响腿部恢复。",
        "High cardio load; watch leg performance, hunger, and sleep before adding more." to "有氧负荷较高；继续增加前先观察腿部表现、饥饿和睡眠。",
        "Cardio is in a useful range for this phase." to "有氧处在当前阶段有用范围内。",
        "Water not logged; AI should treat scale weight and pump notes with lower confidence." to "未记录饮水；AI 应降低对体重和泵感记录的置信度。",
        "Water is low; improve hydration before judging pump, appetite, or weight spikes." to "饮水偏低；先改善补水，再判断泵感、食欲或体重波动。",
        "Very high water intake; check sodium balance and avoid forcing fluids." to "饮水非常高；检查钠平衡，避免强行灌水。",
        "Water intake is logged and usable for recovery and weight interpretation." to "饮水已记录，可用于恢复和体重解读。",
        "Sodium not logged; note salty restaurant meals or unusual cramping/pump changes." to "未记录钠；请备注高盐外食、异常抽筋或泵感变化。",
        "Sodium may be low for hard training; watch pump, dizziness, and cramping." to "对高强度训练而言钠可能偏低；注意泵感、头晕和抽筋。",
        "High sodium can explain next-day scale weight and thirst without implying fat gain." to "高钠可能解释次日体重和口渴，不一定代表脂肪增加。",
        "Sodium looks plausible; compare with water, sweat, and scale trend." to "钠摄入看起来合理；结合饮水、出汗和体重趋势判断。",
        "Caffeine not logged; note late stimulants when sleep or resting HR worsens." to "未记录咖啡因；睡眠或静息心率变差时，备注晚间兴奋剂。",
        "High caffeine; protect sleep and avoid masking fatigue." to "咖啡因偏高；保护睡眠，避免掩盖疲劳。",
        "Moderate-high caffeine; keep timing early if sleep quality matters." to "咖啡因中高；如果睡眠质量重要，就尽量提前摄入。",
        "Caffeine is unlikely to be the main recovery limiter." to "咖啡因不太可能是主要恢复限制因素。",
        "Alcohol logged; treat recovery, sleep, weight, and hunger as lower-confidence signals." to "已记录酒精；恢复、睡眠、体重和饥饿信号置信度降低。",
        "Small alcohol exposure logged; note it if sleep, appetite, or weight changes." to "记录到少量酒精；如睡眠、食欲或体重变化，请一起备注。",
        "Not enough weight trend data; do not change calories from one scale point." to "体重趋势数据不足；不要根据单次体重改变热量。",
        "Scale is stable day to day; use 7-14 day trend before changing calories." to "日间体重稳定；调整热量前先看 7-14 天趋势。",
        "Walk enough to reach at least 80% of step goal before adding more calorie cuts." to "先走到至少 80% 步数目标，再考虑继续削减热量。",
        "Log water and bring hydration into range before judging pump or scale changes." to "先记录饮水并把补水拉回范围，再判断泵感或体重变化。",
        "Cap caffeine earlier tomorrow and compare sleep/resting HR before pushing volume." to "明天更早控制咖啡因，再比较睡眠/静息心率后决定是否加量。",
        "Treat today's recovery and weight as confounded; hold plan changes unless trend agrees." to "把今天恢复和体重视为有干扰；除非趋势也支持，否则不改计划。",
        "Add short easy cardio only if fat-loss pace or appetite control needs it." to "只有减脂速度或食欲控制需要时，才加短时轻松有氧。",
        "Keep steps, cardio, water, sodium, caffeine, and digestion notes consistent for cleaner AI review." to "保持步数、有氧、饮水、钠、咖啡因和消化备注一致，让 AI 复盘更干净。",
        "high sodium" to "高钠",
        "low water" to "低饮水",
        "alcohol exposure" to "酒精暴露",
        "high cardio load" to "高有氧负荷",
        "digestion:" to "消化："
    )
    replacements.forEach { (en, zh) -> localized = localized.replace(en, zh) }
    localized = localized
        .replace("Scale changed ", "体重变化 ")
        .replace(" kg; possible confounders: ", " kg；可能干扰因素：")
        .replace(
            " kg without an obvious water/sodium/cardio note; keep watching trend.",
            " kg，且没有明显水分/钠/有氧备注；继续观察趋势。"
        )
    return localized
}

private fun String.localizedTrainingText(language: AppLanguage): String {
    if (language != AppLanguage.CHINESE) return this
    var localized = this
    val replacements = listOf(
        "No loaded session" to "未载入训练",
        "No session loaded" to "未载入训练",
        "No active ramp" to "未生成热身递增",
        "Ready to execute" to "可以执行",
        "Hold before pushing" to "先稳住再推进",
        "Reduce session stress" to "降低本次训练压力",
        "Deload check" to "减量检查",
        "Pain check" to "疼痛检查",
        "Technique check" to "技术检查",
        "Technique watch" to "技术观察",
        "Ready for first set" to "准备第一组",
        "Manage fatigue" to "管理疲劳",
        "Hold or reduce" to "保持或降低",
        "Continue next set" to "继续下一组",
        "All sets complete" to "所有组已完成",
        "Ready to log" to "准备记录",
        "Incomplete session" to "训练未完成",
        "Under-logged" to "记录不足",
        "High fatigue" to "疲劳偏高",
        "Productive session" to "高质量训练",
        "Usable session" to "可用于复盘",
        "Review before progressing" to "进阶前先复盘",
        "Finish workout" to "完成训练",
        "Complete set data" to "补全组数据",
        "Risk review needed" to "需要风险复盘",
        "Nutrition missing" to "缺少训练后饮食",
        "Metrics missing" to "缺少恢复数据",
        "Closeout locked" to "收尾已锁定",
        "Ready for AI review" to "可以 AI 复盘",
        "Working-load ramp" to "正式重量递增",
        "Conservative ramp" to "保守递增",
        "Reduced-stress ramp" to "低压力递增",
        "Pain-limited ramp" to "疼痛限制递增",
        "Deload-style ramp" to "减量式递增",
        "First tracked session" to "首次记录本动作",
        "Log sets to compare" to "记录组数后再比较",
        "Volume up" to "容量上升",
        "Volume down" to "容量下降",
        "Load PR" to "重量 PR",
        "Rep PR" to "次数 PR",
        "Matched last time" to "接近上次表现",
        "Plan first" to "先载入计划",
        "Not enough data" to "数据不足",
        "Modify or hold" to "修改或保持",
        "Finish baseline" to "先完成基线",
        "Add load next time" to "下次小幅加重",
        "Add reps first" to "先加次数",
        "Hold load" to "保持重量",
        "Repeat and refine" to "重复并优化",
        "Pain-aware swap" to "疼痛保护替代",
        "Equipment unavailable" to "器械不可用",
        "Equipment mismatch" to "器械不匹配",
        "Technique-stability swap" to "技术稳定替代",
        "Plan A available" to "计划动作可用",
        "Apply plan" to "应用计划",
        "Finish sets" to "完成组数",
        "Complete set logs" to "补全组记录",
        "Add form evidence" to "添加动作证据",
        "Add session notes" to "添加训练备注",
        "Log post-workout food" to "记录训练后饮食",
        "Sync recovery metrics" to "同步恢复数据",
        "View review" to "查看复盘",
        "Run AI review" to "运行 AI 复盘",
        "Reviewing" to "复盘中",
        "Done" to "完成",
        "Todo" to "待办",
        "No previous" to "无上次记录",
        "bodyweight/plan" to "自重/按计划",
        "no working load" to "无正式重量",
        "5-10 minutes" to "5-10 分钟"
    )
    replacements.forEach { (en, zh) -> localized = localized.replace(en, zh) }
    localized = localized
        .replace("After tapping Complete", "点击完成后")
        .replace("before the next working set", "再进入下一组正式组")
        .replace("No next-set", "暂无下一组")
        .replace("working set(s) remaining", "个正式组剩余")
        .replace("working sets remaining", "个正式组剩余")
        .replace("No completed working sets logged", "还没有已完成正式组记录")
        .replace("completed set(s) missing reps, load, or RIR", "个已完成组缺少次数、重量或 RIR")
        .replace("pain flag(s)", "个疼痛标记")
        .replace("technique flag(s)", "个技术标记")
        .replace("meal(s) or food photo evidence logged", "餐或食物照片证据已记录")
        .replace("form photo(s)", "张动作照片")
        .replace("equipment photo(s)", "张器械照片")
        .replace("sets", "组")
        .replace("reps", "次")
        .replace("Load", "重量")
        .replace("Reps", "次数")
        .replace("Rest", "休息")
    return localized
}

private fun PhotoEvidenceType.localizedLabel(language: AppLanguage): String {
    return when (this) {
        PhotoEvidenceType.MEAL -> language.t("Food or label photo", "食物或标签照片")
        PhotoEvidenceType.TRAINING_FORM -> language.t("Exercise form frame", "动作技术帧")
        PhotoEvidenceType.EQUIPMENT -> language.t("Equipment photo", "器械照片")
        PhotoEvidenceType.PHYSIQUE -> language.t("Physique progress photo", "体型进度照片")
        PhotoEvidenceType.MENU_LABEL -> language.t("Menu or nutrition label", "菜单或营养标签")
        PhotoEvidenceType.OTHER -> language.t("Other check-in photo", "其他打卡照片")
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

private data class DailyStartStep(
    val number: Int,
    val title: String,
    val detail: String,
    val done: Boolean,
    val actionLabel: String,
    val actionEnabled: Boolean = true,
    val onAction: () -> Unit
)

private data class TodayFlowCoachState(
    val doneCount: Int,
    val totalCount: Int,
    val progress: Float,
    val nextStep: DailyStartStep?,
    val completed: Boolean
)

private data class AiSetupStatus(
    val statusLabel: String,
    val detail: String,
    val canRunAi: Boolean,
    val primaryActionLabel: String,
    val missingItems: List<String>,
    val apiKeyLabel: String,
    val baseUrlLabel: String,
    val modelLabel: String,
    val photoContextLabel: String
)

private data class ReviewReadinessChecklistItem(
    val label: String,
    val detail: String,
    val ready: Boolean,
    val actionLabel: String,
    val onAction: () -> Unit
)

private data class AiChangeReceiptItem(
    val label: String,
    val detail: String,
    val status: String,
    val actionLabel: String,
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

private fun NutritionPacing.localizedStatusLabel(language: AppLanguage): String {
    return when (statusLabel) {
        "Over target" -> language.t("Over target", "已超目标")
        "Protein behind" -> language.t("Protein behind", "蛋白质落后")
        "Carbs available" -> language.t("Carbs available", "碳水仍可用")
        "Fiber behind" -> language.t("Fiber behind", "纤维不足")
        "On pace" -> language.t("On pace", "节奏正常")
        "Needs logging" -> language.t("Needs logging", "需要记录")
        else -> statusLabel
    }
}

private fun NutritionPacing.localizedNextMealFocus(language: AppLanguage): String {
    return when (nextMealFocus) {
        "Keep the next meal lean and mostly protein/vegetables." -> language.t(
            "Keep the next meal lean and mostly protein/vegetables.",
            "下一餐保持清淡，以瘦蛋白和蔬菜为主。"
        )
        "Prioritize a lean protein serving before adding extra fats." -> language.t(
            "Prioritize a lean protein serving before adding extra fats.",
            "先补足一份瘦蛋白，再考虑额外脂肪。"
        )
        "Place carbs around training or the next high-output window." -> language.t(
            "Place carbs around training or the next high-output window.",
            "把碳水安排在训练前后或下一段高输出窗口。"
        )
        "Add fruit, vegetables, oats, beans, or another high-fiber carb." -> language.t(
            "Add fruit, vegetables, oats, beans, or another high-fiber carb.",
            "加入水果、蔬菜、燕麦、豆类或其他高纤维碳水。"
        )
        "Keep fats low for the rest of the day." -> language.t(
            "Keep fats low for the rest of the day.",
            "当天剩余餐次尽量低脂。"
        )
        "Log the first meal or attach a food photo for portion estimation." -> language.t(
            "Log the first meal or attach a food photo for portion estimation.",
            "先记录第一餐；份量不确定就添加食物照片估算。"
        )
        "Stay close to targets; use photos only where portions are uncertain." -> language.t(
            "Stay close to targets; use photos only where portions are uncertain.",
            "继续贴近目标；只在份量不确定时使用照片。"
        )
        else -> nextMealFocus
    }
}

private fun NextMealBuilder.localizedTitle(language: AppLanguage): String {
    return when (title) {
        "Lean recovery meal" -> language.t("Lean recovery meal", "低脂恢复餐")
        "Protein-first meal" -> language.t("Protein-first meal", "蛋白质优先餐")
        "Training-fuel meal" -> language.t("Training-fuel meal", "训练供能餐")
        "Fiber + micronutrient meal" -> language.t("Fiber + micronutrient meal", "纤维与微量营养餐")
        "Balanced target meal" -> language.t("Balanced target meal", "均衡目标餐")
        else -> title
    }
}

private fun NextMealBuilder.localizedSummary(language: AppLanguage): String {
    return when (summary) {
        "Keep calories controlled: lean protein, vegetables, minimal added fats." -> language.t(
            "Keep calories controlled: lean protein, vegetables, minimal added fats.",
            "控制热量：瘦蛋白、蔬菜，并减少额外脂肪。"
        )
        "Close the protein gap before adding extra carbs or fats." -> language.t(
            "Close the protein gap before adding extra carbs or fats.",
            "先补齐蛋白质缺口，再增加额外碳水或脂肪。"
        )
        "Place carbs around the remaining training work or post-workout window." -> language.t(
            "Place carbs around the remaining training work or post-workout window.",
            "把碳水放在剩余训练或训练后恢复窗口。"
        )
        "Add fruit, vegetables, oats, beans, or potatoes with a lean protein base." -> language.t(
            "Add fruit, vegetables, oats, beans, or potatoes with a lean protein base.",
            "以瘦蛋白为底，加入水果、蔬菜、燕麦、豆类或土豆。"
        )
        "Stay close to targets with a simple protein, carb, vegetable, and fat structure." -> language.t(
            "Stay close to targets with a simple protein, carb, vegetable, and fat structure.",
            "用蛋白质、碳水、蔬菜和脂肪的简单结构贴近目标。"
        )
        else -> summary
    }
}

private fun NextMealBuilder.localizedTimingCue(language: AppLanguage): String {
    return when (timingCue) {
        "Use this 60-120 min pre-workout or within the post-workout meal window." -> language.t(
            "Use this 60-120 min pre-workout or within the post-workout meal window.",
            "适合训练前 60-120 分钟，或训练后的恢复餐窗口。"
        )
        "Use this as the next recovery meal after today's completed session." -> language.t(
            "Use this as the next recovery meal after today's completed session.",
            "适合作为今天训练完成后的下一顿恢复餐。"
        )
        "Use this as the next logged meal; keep portions measurable." -> language.t(
            "Use this as the next logged meal; keep portions measurable.",
            "作为下一顿记录餐，尽量让份量可称重或可估算。"
        )
        else -> timingCue
    }
}

private fun NextMealBuilder.localizedPortionCue(language: AppLanguage): String {
    return language.t(
        "Aim near P $proteinGrams g, C $carbsGrams g, F $fatGrams g, fiber $fiberGrams g.",
        "尽量接近：蛋白质 $proteinGrams g、碳水 $carbsGrams g、脂肪 $fatGrams g、纤维 $fiberGrams g。"
    )
}

private fun NextMealBuilder.localizedPhotoCue(language: AppLanguage): String {
    return when (photoCue) {
        "Attach a food photo if this is your first meal and portions are uncertain." -> language.t(
            "Attach a food photo if this is your first meal and portions are uncertain.",
            "如果这是第一餐且份量不确定，添加食物照片。"
        )
        "Use a label/photo when oil, sauce, or restaurant portions are uncertain." -> language.t(
            "Use a label/photo when oil, sauce, or restaurant portions are uncertain.",
            "油、酱汁、外食份量不确定时，添加营养标签或照片。"
        )
        else -> photoCue
    }
}

private fun NextMealBuilder.estimatedCalories(): Int {
    return (proteinGrams * 4 + carbsGrams * 4 + fatGrams * 9).coerceAtLeast(120)
}

private fun MealAssemblyGuide.localizedTitle(language: AppLanguage): String {
    return when (title) {
        "Lean correction plate" -> language.t("Lean correction plate", "低脂修正餐盘")
        "Protein-first bodybuilding plate" -> language.t("Protein-first bodybuilding plate", "蛋白质优先健美餐盘")
        "Training fuel plate" -> language.t("Training fuel plate", "训练供能餐盘")
        "Post-workout recovery plate" -> language.t("Post-workout recovery plate", "训练后恢复餐盘")
        "Fiber and micronutrient plate" -> language.t("Fiber and micronutrient plate", "纤维与微量营养餐盘")
        "Balanced physique plate" -> language.t("Balanced physique plate", "均衡体型餐盘")
        else -> title
    }
}

private fun MealAssemblyGuide.localizedPlateStructure(language: AppLanguage): String {
    return when (plateStructure) {
        "One palm-plus lean protein, two fists vegetables, very small starch, sauce measured separately." -> language.t(
            "One palm-plus lean protein, two fists vegetables, very small starch, sauce measured separately.",
            "一掌多的瘦蛋白、两拳蔬菜、少量主食，酱汁单独测量。"
        )
        "Two palms lean protein, one fist carb if calories allow, one to two fists vegetables." -> language.t(
            "Two palms lean protein, one fist carb if calories allow, one to two fists vegetables.",
            "两掌瘦蛋白；热量允许时加一拳碳水；一到两拳蔬菜。"
        )
        "One to two palms lean protein, one to two fists easy carbs, low fat, low-to-moderate fiber." -> language.t(
            "One to two palms lean protein, one to two fists easy carbs, low fat, low-to-moderate fiber.",
            "一到两掌瘦蛋白、一到两拳易消化碳水，低脂、低到中等纤维。"
        )
        "One to two palms protein, one to two fists carbs, vegetables or fruit, fats kept moderate." -> language.t(
            "One to two palms protein, one to two fists carbs, vegetables or fruit, fats kept moderate.",
            "一到两掌蛋白质、一到两拳碳水、蔬菜或水果，脂肪保持中等。"
        )
        "One palm protein, one fist high-fiber carb, two fists colorful vegetables or fruit." -> language.t(
            "One palm protein, one fist high-fiber carb, two fists colorful vegetables or fruit.",
            "一掌蛋白质、一拳高纤维碳水、两拳彩色蔬菜或水果。"
        )
        "One palm protein, one fist carb, one thumb fat, one to two fists vegetables." -> language.t(
            "One palm protein, one fist carb, one thumb fat, one to two fists vegetables.",
            "一掌蛋白质、一拳碳水、一拇指脂肪、一到两拳蔬菜。"
        )
        else -> plateStructure
    }
}

private fun MealAssemblyGuide.localizedProteinAnchor(language: AppLanguage): String {
    return when (proteinAnchor) {
        "Use chicken breast, turkey, white fish, lean beef, egg whites, whey, tofu, or Greek yogurt for about 45-65 g protein." -> language.t(
            "Use chicken breast, turkey, white fish, lean beef, egg whites, whey, tofu, or Greek yogurt for about 45-65 g protein.",
            "用鸡胸、火鸡、白鱼、瘦牛肉、蛋清、乳清、豆腐或希腊酸奶补约 45-65 g 蛋白质。"
        )
        "Use one large lean protein serving for about 30-45 g protein." -> language.t(
            "Use one large lean protein serving for about 30-45 g protein.",
            "使用一份较大的瘦蛋白，约 30-45 g 蛋白质。"
        )
        "Keep a normal protein serving so the day stays anchored without forcing extra calories." -> language.t(
            "Keep a normal protein serving so the day stays anchored without forcing extra calories.",
            "保持正常蛋白份量，让当天饮食有基础，但不强行增加热量。"
        )
        else -> proteinAnchor
    }
}

private fun MealAssemblyGuide.localizedCarbAnchor(language: AppLanguage): String {
    return when (carbAnchor) {
        "Skip dense carbs unless training is still pending; use vegetables or berries." -> language.t(
            "Skip dense carbs unless training is still pending; use vegetables or berries.",
            "除非还有训练没完成，否则跳过高密度碳水，使用蔬菜或莓果。"
        )
        "Use rice, oats, potatoes, pasta, bread, bananas, or cereal 60-120 minutes around training." -> language.t(
            "Use rice, oats, potatoes, pasta, bread, bananas, or cereal 60-120 minutes around training.",
            "训练前后 60-120 分钟使用米饭、燕麦、土豆、意面、面包、香蕉或谷物。"
        )
        "Use rice, potatoes, oats, fruit, or noodles for recovery without pushing fats high." -> language.t(
            "Use rice, potatoes, oats, fruit, or noodles for recovery without pushing fats high.",
            "用米饭、土豆、燕麦、水果或面条恢复，同时不要把脂肪推高。"
        )
        "Use a measured carb serving and keep it easy to log." -> language.t(
            "Use a measured carb serving and keep it easy to log.",
            "使用可测量的一份碳水，让记录更容易。"
        )
        "Keep carbs modest and spend remaining calories on protein and vegetables." -> language.t(
            "Keep carbs modest and spend remaining calories on protein and vegetables.",
            "碳水保持适中，把剩余热量优先用在蛋白质和蔬菜上。"
        )
        else -> carbAnchor
    }
}

private fun MealAssemblyGuide.localizedFatControl(language: AppLanguage): String {
    return when (fatControl) {
        "Avoid added oil, nuts, cheese, fatty sauces, and fried food for the rest of the day." -> language.t(
            "Avoid added oil, nuts, cheese, fatty sauces, and fried food for the rest of the day.",
            "当天剩余时间避免额外油、坚果、奶酪、高脂酱料和油炸食物。"
        )
        "Keep fats very low: grill, steam, air fry, and measure sauces." -> language.t(
            "Keep fats very low: grill, steam, air fry, and measure sauces.",
            "脂肪保持很低：烤、蒸、空气炸，并测量酱料。"
        )
        "Use one measured thumb of fats such as olive oil, avocado, nuts, whole eggs, or salmon if it fits the target." -> language.t(
            "Use one measured thumb of fats such as olive oil, avocado, nuts, whole eggs, or salmon if it fits the target.",
            "如果符合目标，可使用一拇指可测量脂肪，如橄榄油、牛油果、坚果、全蛋或三文鱼。"
        )
        else -> fatControl
    }
}

private fun MealAssemblyGuide.localizedFiberMicros(language: AppLanguage): String {
    return when (fiberMicros) {
        "Add a high-fiber side: vegetables, berries, oats, beans, lentils, potatoes with skin, or whole grains." -> language.t(
            "Add a high-fiber side: vegetables, berries, oats, beans, lentils, potatoes with skin, or whole grains.",
            "添加高纤维配菜：蔬菜、莓果、燕麦、豆类、扁豆、带皮土豆或全谷物。"
        )
        "Add at least one vegetable or fruit serving." -> language.t(
            "Add at least one vegetable or fruit serving.",
            "至少加入一份蔬菜或水果。"
        )
        "Keep vegetables present, but do not force extra fiber if digestion or training timing would suffer." -> language.t(
            "Keep vegetables present, but do not force extra fiber if digestion or training timing would suffer.",
            "保留蔬菜，但如果会影响消化或训练时间，不要强行增加额外纤维。"
        )
        else -> fiberMicros
    }
}

private fun MealAssemblyGuide.localizedShoppingCue(language: AppLanguage): String {
    return when (shoppingCue) {
        "Keep lean protein, microwave rice or potatoes, Greek yogurt, and frozen vegetables stocked." -> language.t(
            "Keep lean protein, microwave rice or potatoes, Greek yogurt, and frozen vegetables stocked.",
            "常备瘦蛋白、微波米饭或土豆、希腊酸奶和冷冻蔬菜。"
        )
        "Keep fast carbs ready: rice packs, oats, bananas, cereal, bagels, potatoes, or sports drink if needed." -> language.t(
            "Keep fast carbs ready: rice packs, oats, bananas, cereal, bagels, potatoes, or sports drink if needed.",
            "准备快速碳水：米饭包、燕麦、香蕉、谷物、贝果、土豆，必要时运动饮料。"
        )
        "Choose lean protein and vegetables; skip restaurant meals with hidden oil when possible." -> language.t(
            "Choose lean protein and vegetables; skip restaurant meals with hidden oil when possible.",
            "选择瘦蛋白和蔬菜；尽量避开隐藏油脂多的外食。"
        )
        "Use simple repeatable staples so the meal can be logged without guessing." -> language.t(
            "Use simple repeatable staples so the meal can be logged without guessing.",
            "使用简单、可重复的基础食材，让记录不靠猜。"
        )
        else -> shoppingCue
    }
}

private fun MealAssemblyGuide.localizedPrepCue(language: AppLanguage): String {
    return when (prepCue) {
        "Prep this as a low-fat, easy-digesting meal so training performance is not limited by digestion." -> language.t(
            "Prep this as a low-fat, easy-digesting meal so training performance is not limited by digestion.",
            "做成低脂、易消化的一餐，避免消化影响训练表现。"
        )
        "Prep this as a recovery meal and log the cooked carb amount, protein weight, oil, and sauce." -> language.t(
            "Prep this as a recovery meal and log the cooked carb amount, protein weight, oil, and sauce.",
            "做成恢复餐，并记录熟碳水重量、蛋白重量、油和酱汁。"
        )
        "Batch protein and carbs separately so portions can be adjusted meal by meal." -> language.t(
            "Batch protein and carbs separately so portions can be adjusted meal by meal.",
            "蛋白质和碳水分开批量准备，这样每餐份量都能调整。"
        )
        else -> prepCue
    }
}

private fun MealAssemblyGuide.localizedPhotoLoggingCue(language: AppLanguage): String {
    return when (photoLoggingCue) {
        "If this is the first meal, photograph the full plate and include any labels or package weights." -> language.t(
            "If this is the first meal, photograph the full plate and include any labels or package weights.",
            "如果这是第一餐，拍完整餐盘，并包含标签或包装重量。"
        )
        "Photograph uncertain restaurant portions, oils, sauces, labels, and bowl depth before AI estimation." -> language.t(
            "Photograph uncertain restaurant portions, oils, sauces, labels, and bowl depth before AI estimation.",
            "AI 估算前，拍下不确定的外食份量、油、酱汁、标签和碗深。"
        )
        else -> photoLoggingCue
    }
}

private fun MealAssemblyGuide.localizedAvoidCue(language: AppLanguage): String {
    return when (avoidCue) {
        "Do not compensate by cutting tomorrow's protein or skipping recovery nutrition." -> language.t(
            "Do not compensate by cutting tomorrow's protein or skipping recovery nutrition.",
            "不要用明天少吃蛋白或跳过恢复营养来补偿。"
        )
        "Do not add fats because the meal looks small; use vegetables and lean protein for volume." -> language.t(
            "Do not add fats because the meal looks small; use vegetables and lean protein for volume.",
            "不要因为餐看起来少就加脂肪；用蔬菜和瘦蛋白增加体积。"
        )
        "Do not spend remaining calories on snacks before protein is handled." -> language.t(
            "Do not spend remaining calories on snacks before protein is handled.",
            "蛋白质没处理好前，不要把剩余热量花在零食上。"
        )
        "Do not chase perfect numbers; get close, log honestly, and let the trend guide adjustments." -> language.t(
            "Do not chase perfect numbers; get close, log honestly, and let the trend guide adjustments.",
            "不要追求完美数字；接近目标、诚实记录，让趋势指导调整。"
        )
        else -> avoidCue
    }
}

private fun DailyLog.recommendedMealTemplate(): MealTemplate {
    val pacing = nutritionPacing()
    val hasTrainingDemand = plannedHardSets() > 0 && completedHardSets() < plannedHardSets()
    val templateId = when {
        pacing.proteinRemaining > 35 && pacing.fatRemaining <= 20 -> "low-fat-protein"
        hasTrainingDemand && pacing.carbsRemaining > 60 -> "pre-workout-carbs"
        pacing.fiberRemaining > 10 -> "fiber-micronutrient"
        pacing.fatRemaining > 22 && pacing.caloriesRemaining > 500 -> "salmon-potato"
        else -> "lean-protein-bowl"
    }
    return mealTemplates().first { it.id == templateId }
}

private fun DailyLog.recommendedMealTemplateReason(language: AppLanguage): String {
    val template = recommendedMealTemplate()
    return when (template.id) {
        "low-fat-protein" -> language.t(
            "Protein is behind while fat room is limited, so this closes the gap cleanly.",
            "蛋白质落后且脂肪空间有限，用这个模板能更干净地补齐缺口。"
        )
        "pre-workout-carbs" -> language.t(
            "Training demand is still open and carbs are available, so this supports performance.",
            "今天还有训练输出需求且碳水空间充足，用这个模板支持表现。"
        )
        "fiber-micronutrient" -> language.t(
            "Fiber is behind, so add food quality before spending calories elsewhere.",
            "纤维不足，先补食物质量，再把热量用到其他地方。"
        )
        "salmon-potato" -> language.t(
            "Fat and calories are still available, so this can improve satiety and food quality.",
            "脂肪和热量仍有空间，这个模板能提高饱腹感和食物质量。"
        )
        else -> language.t(
            "A stable bodybuilding default: protein, carbs, vegetables, and controlled fats.",
            "稳定的健美基础餐：蛋白质、碳水、蔬菜和可控脂肪。"
        )
    }
}

private fun MealTemplate.localizedTitle(language: AppLanguage): String {
    return when (id) {
        "lean-protein-bowl" -> language.t("Lean Protein Bowl", "瘦蛋白碗")
        "pre-workout-carbs" -> language.t("Pre-Workout Carbs", "训练前碳水餐")
        "low-fat-protein" -> language.t("Low-Fat Protein Fix", "低脂蛋白补齐")
        "salmon-potato" -> language.t("Salmon Potato Plate", "三文鱼土豆餐盘")
        "fiber-micronutrient" -> language.t("Fiber + Micronutrient Add", "纤维与微量营养补充")
        else -> title
    }
}

private fun MealTemplate.localizedSubtitle(language: AppLanguage): String {
    return when (id) {
        "lean-protein-bowl" -> language.t(
            "Chicken, rice, vegetables; reliable bodybuilding staple",
            "鸡肉、米饭、蔬菜；可靠的健美基础餐"
        )
        "pre-workout-carbs" -> language.t(
            "Oats, whey, banana; useful before hard training",
            "燕麦、乳清、香蕉；适合高强度训练前"
        )
        "low-fat-protein" -> language.t(
            "Greek yogurt or lean protein when protein is behind",
            "蛋白质落后时，用希腊酸奶或瘦蛋白补齐"
        )
        "salmon-potato" -> language.t(
            "Higher-fat whole-food meal for satiety",
            "脂肪稍高的完整食物餐，提高饱腹感"
        )
        "fiber-micronutrient" -> language.t(
            "Fruit, vegetables, beans, or oats to close fiber gap",
            "用水果、蔬菜、豆类或燕麦补齐纤维"
        )
        else -> subtitle
    }
}

private fun MealTemplate.localizedNotes(language: AppLanguage): String {
    return when (id) {
        "lean-protein-bowl" -> language.t(
            "Template estimate: adjust rice, oil, sauce, and meat weight when known.",
            "模板估算：知道具体份量时，调整米饭、油、酱汁和肉类重量。"
        )
        "pre-workout-carbs" -> language.t(
            "Template estimate: place 60-120 minutes before training if digestion is comfortable.",
            "模板估算：消化舒适时，安排在训练前 60-120 分钟。"
        )
        "low-fat-protein" -> language.t(
            "Template estimate: use when protein is behind and fats need to stay low.",
            "模板估算：蛋白质落后且需要低脂时使用。"
        )
        "salmon-potato" -> language.t(
            "Template estimate: fats vary with salmon size and added oil.",
            "模板估算：脂肪会随三文鱼大小和额外用油变化。"
        )
        "fiber-micronutrient" -> language.t(
            "Template estimate: add when fiber or food quality is behind.",
            "模板估算：纤维或食物质量落后时添加。"
        )
        else -> notes
    }
}

@Composable
private fun ConditioningHydrationCard(
    log: DailyLog,
    recentLogs: List<DailyLog>,
    profile: AthleteProfile,
    language: AppLanguage,
    onConditioningChange: ((DailyConditioning) -> Unit)? = null,
    compact: Boolean = false
) {
    val guidance = conditioningHydrationGuidance(log, recentLogs, profile)
    val conditioning = log.conditioning
    SectionCard(
        title = language.t("Conditioning & Hydration", "有氧、活动量与补水"),
        subtitle = language.t(
            "Steps, cardio, water, sodium, caffeine, digestion, and scale-weight noise before changing calories or volume.",
            "调整热量或训练量前，先看步数、有氧、水、钠、咖啡因、消化和体重波动噪音。"
        )
    ) {
        MetricGrid(
            metrics = listOf(
                language.t("Status", "状态") to guidance.localizedStatusLabel(language),
                language.t("Score", "评分") to guidance.conditioningScore.toString(),
                language.t("Steps", "步数") to "${log.metrics.steps}/${conditioning.stepGoal}",
                language.t("Cardio", "有氧") to "${conditioning.cardioMinutes} min",
                language.t("Water", "饮水") to formatOptional(conditioning.waterLiters, "L"),
                language.t("Caffeine", "咖啡因") to (conditioning.caffeineMg?.let { "$it mg" } ?: "--")
            )
        )
        LinearProgressIndicator(
            progress = { guidance.stepProgressPercent.coerceIn(0, 100) / 100f },
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = guidance.nextAction.localizedConditioningText(language),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = guidance.weightFluctuationCue.localizedConditioningText(language),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        DataChipGrid(
            items = listOf(
                guidance.cardioStatus.localizedConditioningText(language),
                guidance.hydrationStatus.localizedConditioningText(language),
                guidance.sodiumCue.localizedConditioningText(language),
                guidance.caffeineCue.localizedConditioningText(language)
            ).filter { it.isNotBlank() }
        )
        if (!compact && onConditioningChange != null) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                NumberField(
                    value = conditioning.stepGoal.toString(),
                    onChange = { onConditioningChange(conditioning.copy(stepGoal = (it.toIntOrNull() ?: conditioning.stepGoal).coerceAtLeast(0))) },
                    label = language.t("Step goal", "步数目标"),
                    modifier = Modifier.weight(1f)
                )
                NumberField(
                    value = conditioning.cardioMinutes.toString(),
                    onChange = { onConditioningChange(conditioning.copy(cardioMinutes = (it.toIntOrNull() ?: conditioning.cardioMinutes).coerceAtLeast(0))) },
                    label = language.t("Cardio min", "有氧分钟"),
                    modifier = Modifier.weight(1f)
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = conditioning.cardioType,
                    onValueChange = { onConditioningChange(conditioning.copy(cardioType = it)) },
                    modifier = Modifier.weight(1f),
                    label = { Text(language.t("Cardio type", "有氧类型")) },
                    singleLine = true
                )
                OutlinedTextField(
                    value = conditioning.cardioIntensity,
                    onValueChange = { onConditioningChange(conditioning.copy(cardioIntensity = it.ifBlank { "Zone 2" })) },
                    modifier = Modifier.weight(1f),
                    label = { Text(language.t("Intensity", "强度")) },
                    singleLine = true
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                DecimalField(
                    value = conditioning.waterLiters?.toString().orEmpty(),
                    onChange = { onConditioningChange(conditioning.copy(waterLiters = it.toDoubleOrNull())) },
                    label = language.t("Water L", "饮水 L"),
                    modifier = Modifier.weight(1f)
                )
                NumberField(
                    value = conditioning.sodiumMg?.toString().orEmpty(),
                    onChange = { onConditioningChange(conditioning.copy(sodiumMg = it.toIntOrNull())) },
                    label = language.t("Sodium mg", "钠 mg"),
                    modifier = Modifier.weight(1f)
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                NumberField(
                    value = conditioning.caffeineMg?.toString().orEmpty(),
                    onChange = { onConditioningChange(conditioning.copy(caffeineMg = it.toIntOrNull())) },
                    label = language.t("Caffeine mg", "咖啡因 mg"),
                    modifier = Modifier.weight(1f)
                )
                DecimalField(
                    value = conditioning.alcoholServings?.toString().orEmpty(),
                    onChange = { onConditioningChange(conditioning.copy(alcoholServings = it.toDoubleOrNull())) },
                    label = language.t("Alcohol", "酒精"),
                    modifier = Modifier.weight(1f)
                )
            }
            OutlinedTextField(
                value = conditioning.digestion,
                onValueChange = { onConditioningChange(conditioning.copy(digestion = it)) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(language.t("Digestion, bloating, high-salt meal, cramps, pump", "消化、胀气、高盐餐、抽筋、泵感")) },
                minLines = 2
            )
            OutlinedTextField(
                value = conditioning.notes,
                onValueChange = { onConditioningChange(conditioning.copy(notes = it)) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(language.t("Conditioning notes", "有氧与补水备注")) },
                minLines = 2
            )
        }
    }
}

private fun CoachUiState.dailyReadiness(language: AppLanguage): DailyReadiness {
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
        dailyLog.trainingSession.exercises.isEmpty() -> language.t(
            "Apply a plan day or add today's first exercise.",
            "先应用一个训练日，或者添加今天第一个动作。"
        )
        dailyLog.completedHardSets() < dailyLog.plannedHardSets() -> language.t(
            "Finish the remaining working sets and keep RIR honest.",
            "完成剩余工作组，并如实记录 RIR。"
        )
        dailyLog.meals.isEmpty() -> language.t(
            "Log the first meal or add a food photo for AI estimation.",
            "先记录第一餐，或添加食物照片给 AI 估算。"
        )
        dailyLog.nutritionPacing().adherenceScore < 72 -> language.t(
            "Use Nutrition Pacing to fix the biggest macro gap before AI review.",
            "先用营养节奏修正最大的宏量营养缺口，再做 AI 复盘。"
        )
        metrics.bodyWeightKg == null || metrics.sleepHours == null -> language.t(
            "Sync Health Connect or fill today's recovery metrics.",
            "同步 Health Connect，或手动补齐今天的恢复数据。"
        )
        else -> language.t(
            "Run AI review and lock tomorrow's training and food targets.",
            "运行 AI 复盘，并锁定明天训练和饮食目标。"
        )
    }
    val label = when {
        score >= 82 -> language.t("Ready to progress", "可以推进")
        score >= 65 -> language.t("Controlled push", "可控推进")
        else -> language.t("Hold or recover", "维持或恢复")
    }
    return DailyReadiness(score = score, label = label, nextAction = nextAction)
}

private fun CoachUiState.aiSetupStatus(language: AppLanguage): AiSetupStatus {
    val missing = buildList {
        if (settings.apiKey.isBlank()) add(language.t("API key", "API key"))
        if (settings.baseUrl.isBlank()) add(language.t("Base URL", "Base URL"))
        if (settings.model.isBlank()) add(language.t("Model", "Model"))
    }
    val canRun = missing.isEmpty()
    val statusLabel = if (canRun) {
        language.t("Ready", "已就绪")
    } else {
        language.t("Setup needed", "需要设置")
    }
    val detail = if (canRun) {
        language.t(
            "AI can run daily review, mode analysis, and multimodal photo checks using today's linked training, food, metrics, and evidence.",
            "AI 已可使用今天的训练、饮食、身体数据和照片证据运行每日复盘、模式分析和多模态照片检查。"
        )
    } else {
        language.t(
            "Complete ${missing.joinToString(", ")} before daily review; the app will still save training, nutrition, metrics, and photos locally.",
            "先补齐 ${missing.joinToString(", ")} 再运行每日复盘；训练、饮食、身体数据和照片仍会先保存在本地。"
        )
    }
    return AiSetupStatus(
        statusLabel = statusLabel,
        detail = detail,
        canRunAi = canRun,
        primaryActionLabel = if (canRun) {
            language.t("Run daily review", "运行每日复盘")
        } else {
            language.t("Set API first", "先设置 API")
        },
        missingItems = missing,
        apiKeyLabel = if (settings.apiKey.isBlank()) language.t("Missing", "未填写") else language.t("Saved", "已保存"),
        baseUrlLabel = settings.baseUrl.ifBlank { language.t("Missing", "未填写") },
        modelLabel = settings.model.ifBlank { language.t("Missing", "未填写") },
        photoContextLabel = if (images.isEmpty()) {
            language.t("No photos queued", "未加入照片")
        } else {
            language.t("${images.size} photo(s) queued", "已加入 ${images.size} 张照片")
        }
    )
}

private fun CoachUiState.dailyCoachTasks(
    language: AppLanguage,
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
    val aiSetup = aiSetupStatus(language)

    return listOf(
        DailyCoachTask(
            title = language.t("Plan prepared", "训练计划已准备"),
            detail = when {
                sessionReady -> language.t(
                    "${log.trainingSession.plannedFocus}: $plannedSets planned sets loaded.",
                    "${log.trainingSession.plannedFocus}：已载入 $plannedSets 个计划组。"
                )
                planReady -> language.t(
                    "Weekly plan exists; apply the right day before training.",
                    "已经有周计划；训练前先应用正确的训练日。"
                )
                else -> language.t(
                    "Choose a Plan Template or build today's first training day.",
                    "选择一个计划模板，或创建今天的第一个训练日。"
                )
            },
            done = sessionReady,
            actionLabel = if (sessionReady) {
                language.t("View workout", "查看训练")
            } else if (planReady) {
                language.t("Apply day", "应用训练日")
            } else {
                language.t("Pick plan", "选择计划")
            },
            onAction = if (sessionReady) onOpenTraining else onOpenPlan
        ),
        DailyCoachTask(
            title = language.t("Training executed", "训练已执行"),
            detail = when {
                !sessionReady -> language.t("No workout loaded yet.", "今天还没有载入训练。")
                trainingDone -> language.t(
                    "$completedSets/$plannedSets sets completed; add notes before review.",
                    "已完成 $completedSets/$plannedSets 组；复盘前补充训练备注。"
                )
                else -> language.t(
                    "$completedSets/$plannedSets sets completed; finish working sets and rest timers.",
                    "已完成 $completedSets/$plannedSets 组；继续完成工作组并跟随休息倒计时。"
                )
            },
            done = trainingDone,
            actionLabel = language.t("Log sets", "记录组数"),
            actionEnabled = sessionReady,
            onAction = onOpenTraining
        ),
        DailyCoachTask(
            title = language.t("Food logged", "饮食已记录"),
            detail = if (nutritionLogged) {
                language.t(
                    "${log.meals.size} meals logged; ${formatRemaining(pacing.caloriesRemaining, "kcal")} and protein ${formatRemaining(pacing.proteinRemaining, "g")}.",
                    "已记录 ${log.meals.size} 餐；热量${formatRemainingLocalized(pacing.caloriesRemaining, "kcal", language)}，蛋白质${formatRemainingLocalized(pacing.proteinRemaining, "g", language)}。"
                )
            } else {
                language.t(
                    "Add a meal template or food photo so nutrition can be compared with training demand.",
                    "添加餐食模板或食物照片，让饮食能和训练需求一起分析。"
                )
            },
            done = nutritionLogged,
            actionLabel = if (nutritionLogged) language.t("Review food", "查看饮食") else language.t("Add meal", "添加餐食"),
            onAction = onOpenNutrition
        ),
        DailyCoachTask(
            title = language.t("Metrics synced", "身体数据已同步"),
            detail = when {
                log.metrics.healthSyncedAt.isNotBlank() -> language.t(
                    "Health data synced; source ${log.metrics.healthDataSource.ifBlank { "Health Connect" }}.",
                    "健康数据已同步；来源 ${log.metrics.healthDataSource.ifBlank { "Health Connect" }}。"
                )
                metricsReady -> language.t(
                    "Manual metrics available; sync Health Connect when possible.",
                    "已有手动数据；条件允许时同步 Health Connect。"
                )
                else -> language.t(
                    "Add body weight, sleep, steps, HR, soreness, fatigue, and stress.",
                    "补齐体重、睡眠、步数、静息心率、酸痛、疲劳和压力。"
                )
            },
            done = metricsReady,
            actionLabel = if (metricsReady) language.t("Check metrics", "查看数据") else language.t("Sync metrics", "同步数据"),
            onAction = onOpenMetrics
        ),
        DailyCoachTask(
            title = language.t("AI review locked", "AI 复盘已锁定"),
            detail = when {
                !aiSetup.canRunAi -> aiSetup.detail
                aiReviewedToday -> language.t(
                    "Today's review is saved; use it to set tomorrow's training and food targets.",
                    "今天的复盘已保存；用它安排明天训练和饮食目标。"
                )
                else -> language.t(
                    "Run after training, food, and metrics are logged for the cleanest adjustment.",
                    "训练、饮食和身体数据记录后再运行，调整会更可靠。"
                )
            },
            done = aiReviewedToday,
            actionLabel = when {
                !aiSetup.canRunAi -> aiSetup.primaryActionLabel
                aiReviewedToday -> language.t("View review", "查看复盘")
                else -> language.t("Run review", "运行复盘")
            },
            actionEnabled = !isLoading,
            onAction = when {
                !aiSetup.canRunAi -> onOpenAi
                aiReviewedToday -> onOpenAi
                else -> onRunAiReview
            }
        )
    )
}

private fun CoachUiState.dailyStartSteps(
    language: AppLanguage,
    onOpenPlan: () -> Unit,
    onOpenTraining: () -> Unit,
    onOpenNutrition: () -> Unit,
    onOpenMetrics: () -> Unit,
    onRunAiReview: () -> Unit,
    onOpenAi: () -> Unit
): List<DailyStartStep> {
    return dailyCoachTasks(
        language = language,
        onOpenPlan = onOpenPlan,
        onOpenTraining = onOpenTraining,
        onOpenNutrition = onOpenNutrition,
        onOpenMetrics = onOpenMetrics,
        onRunAiReview = onRunAiReview,
        onOpenAi = onOpenAi
    ).mapIndexed { index, task ->
        DailyStartStep(
            number = index + 1,
            title = task.title,
            detail = task.detail,
            done = task.done,
            actionLabel = task.actionLabel,
            actionEnabled = task.actionEnabled,
            onAction = task.onAction
        )
    }
}

private fun todayFlowCoachState(steps: List<DailyStartStep>): TodayFlowCoachState {
    val doneCount = steps.count { it.done }
    val nextStep = steps.firstOrNull { !it.done && it.actionEnabled } ?: steps.firstOrNull { !it.done } ?: steps.lastOrNull()
    val totalCount = steps.size
    return TodayFlowCoachState(
        doneCount = doneCount,
        totalCount = totalCount,
        progress = if (totalCount == 0) 0f else doneCount.toFloat() / totalCount.toFloat(),
        nextStep = nextStep,
        completed = totalCount > 0 && doneCount >= totalCount
    )
}

@Composable
private fun TodayFlowCoachCard(
    state: CoachUiState,
    readiness: DailyReadiness,
    flow: TodayFlowCoachState,
    executionPlan: DailyExecutionPlan,
    showDetails: Boolean,
    onToggleDetails: () -> Unit,
    onOpenTraining: () -> Unit,
    onOpenNutrition: () -> Unit,
    onOpenMetrics: () -> Unit
) {
    val language = state.appLanguage
    val log = state.dailyLog
    val totals = log.nutritionTotals()
    val pacing = log.nutritionPacing()
    val nextStep = flow.nextStep
    val primaryTitle = if (flow.completed) {
        language.t("Daily loop complete", "今日闭环已完成")
    } else {
        nextStep?.title ?: language.t("Start today's loop", "开始今日闭环")
    }
    val primaryDetail = if (flow.completed) {
        language.t(
            "Review saved guidance, prepare tomorrow, and keep logging meals or recovery changes.",
            "查看已保存建议，准备明天计划，并继续记录餐食或恢复变化。"
        )
    } else {
        nextStep?.detail ?: executionPlan.nextBestAction
    }
    val primaryLabel = if (flow.completed) {
        language.t("Review tomorrow", "查看明天安排")
    } else {
        nextStep?.actionLabel ?: executionPlan.primaryActionLabel
    }
    val primaryAction = if (flow.completed) {
        onOpenMetrics
    } else {
        nextStep?.onAction ?: onOpenTraining
    }

    SectionCard(
        title = language.t("Today Flow Coach", "今日流程教练"),
        subtitle = language.t(
            "Open the app, do the next step, and keep the professional reasoning underneath.",
            "打开 App 后只做下一步；专业判断放在下方，需要时再展开。"
        )
    ) {
        Text(
            text = language.t("NEXT BEST ACTION", "现在该做"),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = primaryTitle,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = primaryDetail,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        LinearProgressIndicator(progress = { flow.progress }, modifier = Modifier.fillMaxWidth())
        Button(
            onClick = primaryAction,
            enabled = nextStep?.actionEnabled ?: true,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(primaryLabel)
        }
        TextButton(onClick = onToggleDetails, modifier = Modifier.fillMaxWidth()) {
            Text(if (showDetails) language.t("Hide detail layers", "收起细节层") else language.t("Show detail layers", "展开细节层"))
        }
        if (showDetails) {
            MetricGrid(
                metrics = listOf(
                    language.t("Daily loop", "今日闭环") to "${flow.doneCount}/${flow.totalCount}",
                    language.t("Readiness", "状态") to readiness.score.toString(),
                    language.t("Sets", "组数") to "${log.completedHardSets()}/${log.plannedHardSets()}",
                    language.t("Protein", "蛋白质") to formatRemainingLocalized(pacing.proteinRemaining, "g", language),
                    language.t("Calories", "热量") to formatRemainingLocalized(pacing.caloriesRemaining, "kcal", language),
                    language.t("AI gate", "AI 闸门") to executionPlan.aiReviewGate
                )
            )
            Text(
                text = language.t(
                    "Coach read: ${executionPlan.priorityFocus}. ${executionPlan.trainingDecision} ${executionPlan.nutritionDecision}",
                    "教练判断：${executionPlan.priorityFocus}。${executionPlan.trainingDecision} ${executionPlan.nutritionDecision}"
                ),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            DataChipGrid(
                items = listOf(
                    language.t("Plan -> train -> eat -> sync -> review", "计划 -> 训练 -> 饮食 -> 同步 -> 复盘"),
                    language.t("Primary action: ${executionPlan.primaryActionLabel}", "主动作：${executionPlan.primaryActionLabel}"),
                    language.t("Next meal: ${pacing.nextMealFocus}", "下一餐：${pacing.nextMealFocus}"),
                    language.t("Training volume: ${formatDecimal(log.trainingVolumeKg())} kg", "训练容量：${formatDecimal(log.trainingVolumeKg())} kg")
                )
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                TextButton(onClick = onOpenTraining, modifier = Modifier.weight(1f)) {
                    Text(language.t("Train", "训练"))
                }
                TextButton(onClick = onOpenNutrition, modifier = Modifier.weight(1f)) {
                    Text(language.t("Food", "饮食"))
                }
                TextButton(onClick = onOpenMetrics, modifier = Modifier.weight(1f)) {
                    Text(language.t("Metrics", "数据"))
                }
            }
            Text(
                text = language.t(
                    "Logged today: ${totals.calories} kcal, P ${totals.protein} g, ${state.dailyLog.photoEvidence.size} photo evidence item(s).",
                    "今日已记录：${totals.calories} kcal，蛋白质 ${totals.protein} g，${state.dailyLog.photoEvidence.size} 条照片证据。"
                ),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun StartHereCoachCard(
    state: CoachUiState,
    readiness: DailyReadiness,
    steps: List<DailyStartStep>,
    onOpenTraining: () -> Unit,
    onOpenNutrition: () -> Unit,
    onOpenMetrics: () -> Unit
) {
    val language = state.appLanguage
    val doneCount = steps.count { it.done }
    val nextStep = steps.firstOrNull { !it.done && it.actionEnabled } ?: steps.lastOrNull()
    val progress = if (steps.isEmpty()) 0f else doneCount.toFloat() / steps.size.toFloat()
    SectionCard(
        title = language.t("Start Here", "从这里开始"),
        subtitle = language.t(
            "A clear daily loop: plan, train, eat, sync, then let AI review the evidence.",
            "每天只按这个闭环走：先计划，再训练，再记录饮食，同步身体数据，最后让 AI 基于证据复盘。"
        )
    ) {
        MetricGrid(
            metrics = listOf(
                language.t("Progress", "今日进度") to "$doneCount/${steps.size}",
                language.t("Readiness", "状态") to "${readiness.score}",
                language.t("Phase", "阶段") to state.athleteProfile.currentPhase,
                language.t("Next", "下一步") to (nextStep?.title ?: language.t("Complete", "已完成"))
            )
        )
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = nextStep?.detail ?: language.t(
                "Today's loop is complete. Use the saved review queue to prepare tomorrow.",
                "今天的闭环已经完成。可以用已保存的复盘队列准备明天。"
            ),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )
        Button(
            onClick = { nextStep?.onAction?.invoke() },
            enabled = nextStep?.actionEnabled == true,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(nextStep?.actionLabel ?: language.t("Done", "已完成"))
        }
        steps.forEach { step ->
            StartHereStepRow(step = step, language = language)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            TextButton(onClick = onOpenTraining, modifier = Modifier.weight(1f)) {
                Text(language.t("Train", "训练"))
            }
            TextButton(onClick = onOpenNutrition, modifier = Modifier.weight(1f)) {
                Text(language.t("Food", "饮食"))
            }
            TextButton(onClick = onOpenMetrics, modifier = Modifier.weight(1f)) {
                Text(language.t("Metrics", "数据"))
            }
        }
    }
}

@Composable
private fun StartHereStepRow(step: DailyStartStep, language: AppLanguage) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        color = if (step.done) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = if (step.done) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
            ) {
                Text(
                    text = step.number.toString(),
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp),
                    color = if (step.done) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                Text(step.title, fontWeight = FontWeight.SemiBold)
                Text(
                    text = step.detail,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(
                text = if (step.done) language.t("Done", "完成") else language.t("Next", "下一步"),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun AiSetupStatusCard(
    status: AiSetupStatus,
    language: AppLanguage,
    isLoading: Boolean,
    primaryLabel: String,
    primaryEnabled: Boolean,
    onPrimaryAction: () -> Unit,
    secondaryLabel: String? = null,
    onSecondaryAction: (() -> Unit)? = null
) {
    SectionCard(
        title = language.t("AI Setup & Review Readiness", "AI 设置与复盘就绪状态"),
        subtitle = language.t(
            "Make sure the AI layer is usable before the daily loop reaches review.",
            "在每日闭环走到复盘前，先确认 AI 层真的可以运行。"
        )
    ) {
        MetricGrid(
            metrics = listOf(
                language.t("Status", "状态") to status.statusLabel,
                language.t("API key", "API key") to status.apiKeyLabel,
                language.t("Base URL", "Base URL") to status.baseUrlLabel,
                language.t("Model", "Model") to status.modelLabel,
                language.t("Photos", "照片") to status.photoContextLabel
            )
        )
        Text(
            text = status.detail,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )
        DataChipGrid(
            items = if (status.missingItems.isEmpty()) {
                listOf(
                    language.t("AI review ready", "AI 复盘已就绪"),
                    language.t("Daily review", "每日复盘"),
                    language.t("Mode analysis", "模式分析"),
                    language.t("Photo context linked", "照片上下文可联动")
                )
            } else {
                listOf(language.t("Missing setup", "设置缺失")) + status.missingItems
            }
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = onPrimaryAction,
                enabled = primaryEnabled && !isLoading,
                modifier = Modifier.weight(1f)
            ) {
                Text(if (isLoading) language.t("Reviewing", "复盘中") else primaryLabel)
            }
            if (secondaryLabel != null && onSecondaryAction != null) {
                TextButton(
                    onClick = onSecondaryAction,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(secondaryLabel)
                }
            }
        }
    }
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
    language: AppLanguage,
    onDailyReview: () -> Unit,
    onOpenTraining: () -> Unit,
    onOpenNutrition: () -> Unit,
    onOpenMetrics: () -> Unit
) {
    SectionCard(
        title = language.t("Command Center", "今日指挥台"),
        subtitle = language.t(
            "Use this after the Start Here loop when you want the decision layer and latest guidance.",
            "完成“从这里开始”闭环后，在这里查看当前决策层和最新建议。"
        )
    ) {
        MetricGrid(
            metrics = listOf(
                language.t("Readiness", "状态分") to "${readiness.score}",
                language.t("State", "状态") to readiness.label,
                language.t("Phase", "阶段") to state.athleteProfile.currentPhase,
                language.t("Sets", "组数") to "${state.dailyLog.completedHardSets()}/${state.dailyLog.plannedHardSets()}",
                language.t("Meals", "餐数") to state.dailyLog.meals.size.toString()
            )
        )
        Text(
            text = language.t("Goal: ${state.athleteProfile.primaryGoal}", "目标：${state.athleteProfile.primaryGoal}"),
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
                Text(if (state.isLoading) language.t("Reviewing", "复盘中") else language.t("AI review", "AI 复盘"))
            }
            ElevatedButton(
                onClick = when {
                    state.dailyLog.trainingSession.exercises.isEmpty() -> onOpenTraining
                    state.dailyLog.meals.isEmpty() -> onOpenNutrition
                    else -> onOpenMetrics
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(language.t("Next", "下一步"))
            }
        }
        if (state.isLoading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }
        state.reviewHistory.firstOrNull()?.let { review ->
            HorizontalDivider()
            Text(
                text = language.t("Latest AI guidance", "最新 AI 建议"),
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
private fun DailyExecutionPlanCard(
    plan: DailyExecutionPlan,
    language: AppLanguage,
    onOpenPlan: () -> Unit,
    onOpenTraining: () -> Unit,
    onOpenNutrition: () -> Unit,
    onOpenMetrics: () -> Unit,
    onDailyReview: () -> Unit,
    onOpenAi: () -> Unit
) {
    SectionCard(
        title = language.t("Daily Execution Plan", "今日执行计划"),
        subtitle = language.t(
            "The app's decision layer: what to do first, what to adjust, and when AI review is reliable.",
            "App 的每日决策层：先做什么、该调整什么、什么时候 AI 复盘才可靠。"
        )
    ) {
        MetricGrid(
            metrics = listOf(
                language.t("Status", "状态") to plan.statusLabel,
                language.t("Priority", "优先级") to plan.priorityFocus,
                language.t("Readiness", "状态分") to plan.readinessScore.toString(),
                language.t("Primary", "主动作") to plan.primaryActionLabel
            )
        )
        Text(
            text = plan.nextBestAction,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = language.t("Training: ${plan.trainingDecision}", "训练：${plan.trainingDecision}"),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = language.t("Nutrition: ${plan.nutritionDecision}", "饮食：${plan.nutritionDecision}"),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = language.t("Recovery: ${plan.recoveryDecision}", "恢复：${plan.recoveryDecision}"),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        DataChipGrid(
            items = listOf(
                plan.dataQualityGate,
                plan.aiReviewGate,
                plan.planAdjustmentSignal
            )
        )
        Button(
            onClick = when (plan.primaryActionRoute) {
                DailyExecutionRoute.PLAN -> onOpenPlan
                DailyExecutionRoute.TRAINING -> onOpenTraining
                DailyExecutionRoute.NUTRITION -> onOpenNutrition
                DailyExecutionRoute.METRICS -> onOpenMetrics
                DailyExecutionRoute.AI_REVIEW -> if (plan.primaryActionLabel == "View review") onOpenAi else onDailyReview
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(plan.primaryActionLabel)
        }
    }
}

@Composable
private fun AiIntegratedDecisionMatrixCard(
    decision: AiIntegratedDecisionMatrix,
    language: AppLanguage,
    onOpenPlan: () -> Unit,
    onOpenTraining: () -> Unit,
    onOpenNutrition: () -> Unit,
    onOpenMetrics: () -> Unit,
    onDailyReview: () -> Unit
) {
    SectionCard(
        title = language.t("AI Integrated Decision Matrix", "AI 综合决策矩阵"),
        subtitle = language.t(
            "All data linked: training effect, nutrition, body trend, sleep/recovery, photos, and split decision.",
            "联动全部数据：训练效果、饮食、身体趋势、睡眠恢复、照片和分化决策。"
        )
    ) {
        MetricGrid(
            metrics = listOf(
                language.t("Training effect", "训练效果") to decision.statusLabel,
                language.t("Split decision", "分化决策") to decision.recommendedSplit,
                language.t("Confidence", "置信度") to "${decision.dataConfidencePercent}%",
                language.t("Current split", "当前分化") to decision.currentSplit
            )
        )
        Text(
            text = decision.trainingEffectVerdict,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = language.t("Split: ${decision.splitDecision}", "分化：${decision.splitDecision}"),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = language.t("Nutrition: ${decision.nutritionLever}", "饮食：${decision.nutritionLever}"),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = language.t("Recovery: ${decision.recoveryLever}", "恢复：${decision.recoveryLever}"),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        DataChipGrid(
            items = listOf(
                decision.dataConfidenceLabel,
                decision.allowedChangesNow,
                "Do not change split until ${decision.holdUntil}",
                "3-day / 4-day / 5-day split decision",
                "all data linked"
            )
        )
        DataChipGrid(items = decision.linkedEvidence)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = onDailyReview, modifier = Modifier.weight(1f)) {
                Text(language.t("Run AI review", "运行 AI 复盘"))
            }
            ElevatedButton(
                onClick = when {
                    decision.nextAction.contains("Training", ignoreCase = true) ||
                        decision.nextAction.contains("split", ignoreCase = true) -> onOpenPlan
                    decision.nextAction.contains("food", ignoreCase = true) ||
                        decision.nextAction.contains("nutrition", ignoreCase = true) -> onOpenNutrition
                    decision.nextAction.contains("metrics", ignoreCase = true) ||
                        decision.nextAction.contains("sleep", ignoreCase = true) -> onOpenMetrics
                    else -> onOpenTraining
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(language.t("Open next step", "打开下一步"))
            }
        }
        Text(
            text = decision.nextAction,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun AiReviewActionQueueCard(
    queue: AiReviewActionQueue,
    isLoading: Boolean,
    language: AppLanguage,
    onDailyReview: () -> Unit,
    onOpenPlan: () -> Unit,
    onOpenTraining: () -> Unit,
    onOpenNutrition: () -> Unit,
    onOpenMetrics: () -> Unit,
    onOpenAi: () -> Unit
) {
    SectionCard(
        title = language.t("AI Review Action Queue", "AI 复盘行动队列"),
        subtitle = language.t(
            "Turns the latest saved AI review into training, nutrition, recovery, tracking, and plan actions.",
            "把最新保存的 AI 复盘，转成训练、饮食、恢复、追踪和计划修改动作。"
        )
    ) {
        MetricGrid(
            metrics = listOf(
                language.t("Status", "状态") to queue.statusLabel,
                language.t("Source", "来源") to queue.sourceLabel,
                language.t("Confidence", "可信度") to queue.confidenceLabel,
                language.t("Primary", "主动作") to queue.primaryAction.title
            )
        )
        Text(
            text = queue.primaryAction.detail,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )
        DataChipGrid(
            items = listOf(
                "AI Review Action Queue",
                "Review saved guidance",
                "Run daily review",
                "training action",
                "nutrition action",
                "recovery action",
                "tracking action"
            ) + queue.actions.map { "${it.category.label} priority ${it.priority}" }
        )
        queue.actions.forEachIndexed { index, action ->
            AiReviewActionRow(
                action = action,
                isPrimary = action == queue.primaryAction,
                isLoading = isLoading,
                language = language,
                onDailyReview = onDailyReview,
                onOpenPlan = onOpenPlan,
                onOpenTraining = onOpenTraining,
                onOpenNutrition = onOpenNutrition,
                onOpenMetrics = onOpenMetrics,
                onOpenAi = onOpenAi
            )
            if (index != queue.actions.lastIndex) {
                HorizontalDivider()
            }
        }
        Text(
            text = queue.aiReviewFocus,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun ReviewReadinessChecklistCard(
    state: CoachUiState,
    setup: AiSetupStatus,
    onOpenTraining: () -> Unit,
    onOpenNutrition: () -> Unit,
    onOpenMetrics: () -> Unit,
    onOpenAi: () -> Unit,
    onPickImages: () -> Unit,
    onDailyReview: () -> Unit
) {
    val language = state.appLanguage
    val log = state.dailyLog
    val plannedSets = log.plannedHardSets()
    val completedSets = log.completedHardSets()
    val trainingReady = plannedSets > 0 && completedSets >= plannedSets
    val nutritionReady = log.meals.isNotEmpty() && log.nutritionPacing().adherenceScore >= 65
    val metricsReady = log.metrics.bodyWeightKg != null ||
        log.metrics.sleepHours != null ||
        log.metrics.steps > 0 ||
        log.metrics.healthSyncedAt.isNotBlank()
    val photoReady = state.images.isNotEmpty() || log.photoEvidence.isNotEmpty()
    val items = listOf(
        ReviewReadinessChecklistItem(
            label = language.t("Training evidence", "训练证据"),
            detail = if (plannedSets == 0) {
                language.t("No workout loaded. Apply a plan day before AI changes training.", "还没有载入训练。AI 改训练前先应用一个训练日。")
            } else {
                language.t("$completedSets/$plannedSets planned sets logged.", "已记录 $completedSets/$plannedSets 个计划组。")
            },
            ready = trainingReady,
            actionLabel = if (trainingReady) language.t("Review sets", "查看组记录") else language.t("Finish training", "完成训练"),
            onAction = onOpenTraining
        ),
        ReviewReadinessChecklistItem(
            label = language.t("Nutrition evidence", "饮食证据"),
            detail = if (log.meals.isEmpty()) {
                language.t("No meal logged. Add food or a food photo before changing macros.", "还没有饮食记录。改宏量前先记录餐食或食物照片。")
            } else {
                val totals = log.nutritionTotals()
                language.t("${log.meals.size} meal(s), ${totals.calories} kcal, P ${totals.protein} g logged.", "已记录 ${log.meals.size} 餐，${totals.calories} kcal，蛋白质 ${totals.protein} g。")
            },
            ready = nutritionReady,
            actionLabel = if (nutritionReady) language.t("Review food", "查看饮食") else language.t("Log food", "记录饮食"),
            onAction = onOpenNutrition
        ),
        ReviewReadinessChecklistItem(
            label = language.t("Metrics evidence", "身体数据证据"),
            detail = if (metricsReady) {
                language.t("Body, sleep, steps, or health sync data is available.", "已有体重、睡眠、步数或健康同步数据。")
            } else {
                language.t("Sync health data or add weight, sleep, HR, soreness, and stress.", "同步健康数据，或补体重、睡眠、心率、酸痛和压力。")
            },
            ready = metricsReady,
            actionLabel = if (metricsReady) language.t("Check metrics", "查看数据") else language.t("Sync metrics", "同步数据"),
            onAction = onOpenMetrics
        ),
        ReviewReadinessChecklistItem(
            label = language.t("Photo/API context", "照片/API 上下文"),
            detail = language.t(
                "Photos ${state.images.size}/${log.photoEvidence.size}; API ${setup.statusLabel}.",
                "照片 ${state.images.size}/${log.photoEvidence.size}；API ${setup.statusLabel}。"
            ),
            ready = setup.canRunAi && photoReady,
            actionLabel = when {
                !setup.canRunAi -> language.t("Set API first", "先设置 API")
                !photoReady -> language.t("Add photos", "添加照片")
                else -> language.t("Context ready", "上下文就绪")
            },
            onAction = if (setup.canRunAi && !photoReady) onPickImages else onOpenAi
        )
    )
    val readyCount = items.count { it.ready }
    val allReady = readyCount == items.size

    SectionCard(
        title = language.t("Review Readiness Checklist", "复盘准备度清单"),
        subtitle = language.t(
            "Fix missing evidence before AI changes training or food.",
            "AI 改训练或饮食前，先补齐缺失证据。"
        )
    ) {
        Text(
            text = language.t("REVIEW READINESS", "复盘准备度"),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
        MetricGrid(
            metrics = listOf(
                language.t("Ready", "已就绪") to "$readyCount/${items.size}",
                language.t("Training", "训练") to if (trainingReady) language.t("Ready", "就绪") else language.t("Missing", "缺失"),
                language.t("Nutrition", "饮食") to if (nutritionReady) language.t("Ready", "就绪") else language.t("Missing", "缺失"),
                language.t("Metrics", "数据") to if (metricsReady) language.t("Ready", "就绪") else language.t("Missing", "缺失")
            )
        )
        LinearProgressIndicator(progress = { readyCount / items.size.toFloat() }, modifier = Modifier.fillMaxWidth())
        items.forEach { item ->
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                color = if (item.ready) IfbbProGlassStrongSurface else MaterialTheme.colorScheme.surface
            ) {
                Row(
                    modifier = Modifier.padding(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                        Text(item.label, fontWeight = FontWeight.SemiBold)
                        Text(
                            text = item.detail,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    TextButton(onClick = item.onAction) {
                        Text(if (item.ready) language.t("View", "查看") else item.actionLabel)
                    }
                }
            }
        }
        Button(
            onClick = onDailyReview,
            enabled = allReady && setup.canRunAi && !state.isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (allReady) language.t("Run daily review", "运行每日复盘") else language.t("Fix missing evidence", "补齐缺失证据"))
        }
        DataChipGrid(
            items = listOf(
                "ReviewReadinessChecklistCard",
                "REVIEW READINESS",
                "Training evidence",
                "Nutrition evidence",
                "Metrics evidence",
                "Photo/API context",
                "Fix missing evidence before AI changes training or food",
                "复盘准备度"
            )
        )
    }
}

@Composable
private fun AiChangeReceiptCard(
    reviewQueue: AiReviewActionQueue,
    decision: AiIntegratedDecisionMatrix,
    tomorrowBrief: TomorrowCoachBrief,
    hasAiReviewToday: Boolean,
    language: AppLanguage,
    onOpenPlan: () -> Unit,
    onOpenTraining: () -> Unit,
    onOpenNutrition: () -> Unit,
    onOpenMetrics: () -> Unit,
    onOpenAi: () -> Unit,
    onDailyReview: () -> Unit
) {
    val appliedLabel = language.t("Applied to next workout", "已应用到下一次训练")
    val heldLabel = language.t("Held until more evidence", "证据更多前保持不变")
    val defaultStatus = if (hasAiReviewToday) appliedLabel else heldLabel
    val recoveryStatus = if (decision.recoveryLever.contains("hold", ignoreCase = true)) {
        heldLabel
    } else {
        defaultStatus
    }
    val items = listOf(
        AiChangeReceiptItem(
            label = language.t("Training changes", "训练变更"),
            detail = if (hasAiReviewToday) tomorrowBrief.trainingAction else decision.splitDecision,
            status = defaultStatus,
            actionLabel = language.t("Open training", "打开训练"),
            onAction = onOpenTraining
        ),
        AiChangeReceiptItem(
            label = language.t("Rest changes", "休息变更"),
            detail = language.t(
                "Next-session rest timers follow the latest AI rest prescription and closeout quality.",
                "下一次训练的休息倒计时会跟随最新 AI 休息处方和训练收尾质量。"
            ),
            status = defaultStatus,
            actionLabel = language.t("Check rest", "查看休息"),
            onAction = onOpenTraining
        ),
        AiChangeReceiptItem(
            label = language.t("Nutrition changes", "饮食变更"),
            detail = tomorrowBrief.nutritionAction,
            status = defaultStatus,
            actionLabel = language.t("Open food", "打开饮食"),
            onAction = onOpenNutrition
        ),
        AiChangeReceiptItem(
            label = language.t("Recovery guardrail", "恢复护栏"),
            detail = tomorrowBrief.recoveryAction,
            status = recoveryStatus,
            actionLabel = language.t("Check recovery", "查看恢复"),
            onAction = onOpenMetrics
        ),
        AiChangeReceiptItem(
            label = language.t("Tracking lock", "追踪锁定"),
            detail = tomorrowBrief.trackingAction,
            status = defaultStatus,
            actionLabel = language.t("Open AI", "打开 AI"),
            onAction = onOpenAi
        )
    )
    val primaryAction = {
        if (hasAiReviewToday) {
            performAiReviewAction(
                action = reviewQueue.primaryAction,
                onDailyReview = onDailyReview,
                onOpenPlan = onOpenPlan,
                onOpenTraining = onOpenTraining,
                onOpenNutrition = onOpenNutrition,
                onOpenMetrics = onOpenMetrics,
                onOpenAi = onOpenAi
            )
        } else {
            onDailyReview()
        }
    }

    SectionCard(
        title = language.t("AI Change Receipt", "AI 变更回执"),
        subtitle = language.t(
            "What changed for next session, what stayed held, and where to follow it.",
            "下一次训练改了什么、哪些保持不变，以及从哪里继续执行。"
        )
    ) {
        Text(
            text = language.t("AI CHANGE RECEIPT", "AI 变更回执"),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
        MetricGrid(
            metrics = listOf(
                language.t("Source", "来源") to reviewQueue.sourceLabel,
                language.t("Confidence", "可信度") to reviewQueue.confidenceLabel,
                language.t("Next", "下一步") to reviewQueue.primaryAction.title,
                language.t("Plan", "计划") to decision.splitDecision
            )
        )
        Text(
            text = language.t("What changed for next session", "下一次训练变更摘要"),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        items.forEach { item ->
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                color = IfbbProGlassStrongSurface
            ) {
                Row(
                    modifier = Modifier.padding(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                        Text(item.label, fontWeight = FontWeight.SemiBold)
                        Text(
                            text = item.status,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = item.detail,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    TextButton(onClick = item.onAction) {
                        Text(item.actionLabel)
                    }
                }
            }
        }
        Button(onClick = primaryAction, modifier = Modifier.fillMaxWidth()) {
            Text(
                if (hasAiReviewToday) {
                    reviewQueue.primaryAction.actionLabel
                } else {
                    language.t("Run review to apply changes", "运行复盘以应用变更")
                }
            )
        }
        DataChipGrid(
            items = listOf(
                "AiChangeReceiptCard",
                "AI CHANGE RECEIPT",
                "What changed for next session",
                "Training changes",
                "Rest changes",
                "Nutrition changes",
                "Recovery guardrail",
                "Tracking lock",
                "Applied to next workout",
                "Held until more evidence",
                "AI 变更回执"
            )
        )
    }
}

@Composable
private fun AiReviewActionRow(
    action: AiReviewActionItem,
    isPrimary: Boolean,
    isLoading: Boolean,
    language: AppLanguage,
    onDailyReview: () -> Unit,
    onOpenPlan: () -> Unit,
    onOpenTraining: () -> Unit,
    onOpenNutrition: () -> Unit,
    onOpenMetrics: () -> Unit,
    onOpenAi: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        color = if (isPrimary) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
    ) {
        Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.Top) {
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                    Text(
                        language.t("${action.category.label} action", "${action.category.label} 动作"),
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = action.title,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = action.detail,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Text(
                    text = "P${action.priority}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Text(
                text = language.t("Evidence: ${action.evidenceCue}", "证据：${action.evidenceCue}"),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            TextButton(
                onClick = {
                    performAiReviewAction(
                        action = action,
                        onDailyReview = onDailyReview,
                        onOpenPlan = onOpenPlan,
                        onOpenTraining = onOpenTraining,
                        onOpenNutrition = onOpenNutrition,
                        onOpenMetrics = onOpenMetrics,
                        onOpenAi = onOpenAi
                    )
                },
                enabled = !(isLoading && action.route == DailyExecutionRoute.AI_REVIEW)
            ) {
                Text(
                    if (isLoading && action.route == DailyExecutionRoute.AI_REVIEW) {
                        language.t("Reviewing", "复盘中")
                    } else {
                        action.actionLabel
                    }
                )
            }
        }
    }
}

private fun performAiReviewAction(
    action: AiReviewActionItem,
    onDailyReview: () -> Unit,
    onOpenPlan: () -> Unit,
    onOpenTraining: () -> Unit,
    onOpenNutrition: () -> Unit,
    onOpenMetrics: () -> Unit,
    onOpenAi: () -> Unit
) {
    when (action.route) {
        DailyExecutionRoute.PLAN -> onOpenPlan()
        DailyExecutionRoute.TRAINING -> onOpenTraining()
        DailyExecutionRoute.NUTRITION -> onOpenNutrition()
        DailyExecutionRoute.METRICS -> onOpenMetrics()
        DailyExecutionRoute.AI_REVIEW -> {
            if (action.actionLabel.contains("Run", ignoreCase = true)) {
                onDailyReview()
            } else {
                onOpenAi()
            }
        }
    }
}

@Composable
private fun TomorrowCoachBriefCard(
    brief: TomorrowCoachBrief,
    language: AppLanguage,
    onOpenPlan: () -> Unit,
    onOpenNutrition: () -> Unit,
    onOpenMetrics: () -> Unit
) {
    SectionCard(
        title = language.t("Tomorrow Coach Brief", "明日教练简报"),
        subtitle = language.t(
            "Lock the next-day training, food, recovery, and tracking priorities after today's review.",
            "在今天复盘后，锁定明天的训练、饮食、恢复和追踪重点。"
        )
    ) {
        MetricGrid(
            metrics = listOf(
                language.t("Status", "状态") to brief.statusLabel,
                language.t("Date", "日期") to brief.tomorrowDate,
                language.t("Plan day", "训练日") to brief.planDayName,
                language.t("Focus", "重点") to brief.planFocus,
                language.t("Calories", "热量") to brief.targetCalories.toString(),
                language.t("Protein", "蛋白质") to "${brief.targetProtein} g"
            )
        )
        Text(
            text = brief.primaryAction,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = language.t("Training: ${brief.trainingAction}", "训练：${brief.trainingAction}"),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = language.t("Nutrition: ${brief.nutritionAction}", "饮食：${brief.nutritionAction}"),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = language.t("Recovery: ${brief.recoveryAction}", "恢复：${brief.recoveryAction}"),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = language.t("Tracking: ${brief.trackingAction}", "追踪：${brief.trackingAction}"),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        DataChipGrid(
            items = listOf(
                language.t("Readiness gate: ${brief.readinessGate}", "状态门槛：${brief.readinessGate}"),
                language.t("AI review focus: ${brief.aiReviewFocus}", "AI 复盘重点：${brief.aiReviewFocus}")
            )
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = onOpenPlan, modifier = Modifier.weight(1f)) {
                Text(language.t("Plan tomorrow", "安排明天"))
            }
            TextButton(onClick = onOpenNutrition, modifier = Modifier.weight(1f)) {
                Text(language.t("Food targets", "饮食目标"))
            }
            TextButton(onClick = onOpenMetrics, modifier = Modifier.weight(1f)) {
                Text(language.t("Metrics", "数据"))
            }
        }
    }
}

@Composable
private fun NextDayHandoffCard(
    brief: TomorrowCoachBrief,
    language: AppLanguage,
    onOpenPlan: () -> Unit,
    onOpenTraining: () -> Unit,
    onOpenNutrition: () -> Unit,
    onOpenMetrics: () -> Unit
) {
    SectionCard(
        title = language.t("Next Day Handoff", "明日交接"),
        subtitle = language.t(
            "Wake up and follow this one-tap next day plan after AI links training, food, recovery, and tracking into tomorrow.",
            "明天醒来直接按这里执行：AI 会把训练、饮食、恢复和追踪联动成一个一键式明日计划。"
        )
    ) {
        MetricGrid(
            metrics = listOf(
                language.t("Plan day", "训练日") to brief.planDayName,
                language.t("Focus", "重点") to brief.planFocus,
                language.t("Food target", "饮食目标") to "${brief.targetCalories} kcal / ${brief.targetProtein} g",
                language.t("Gate", "闸门") to brief.readinessGate
            )
        )
        Text(
            text = language.t("Wake up and follow this", "醒来后按这个执行"),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = brief.primaryAction,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        DataChipGrid(
            items = listOf(
                language.t("tomorrow handoff action", "明日交接动作"),
                language.t("one-tap next day plan", "一键式明日计划"),
                language.t("AI links training, food, recovery, and tracking into tomorrow", "AI 将训练、饮食、恢复和追踪联动到明天"),
                language.t("Tomorrow training focus", "明天训练重点"),
                language.t("Tomorrow nutrition target", "明天饮食目标"),
                language.t("Tomorrow recovery gate", "明天恢复闸门"),
                language.t("Tomorrow tracking action", "明天追踪动作")
            )
        )
        Text(
            text = language.t("Training: ${brief.trainingAction}", "训练：${brief.trainingAction}"),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = language.t("Nutrition: ${brief.nutritionAction}", "饮食：${brief.nutritionAction}"),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = language.t("Recovery: ${brief.recoveryAction}", "恢复：${brief.recoveryAction}"),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = language.t("Tracking: ${brief.trackingAction}", "追踪：${brief.trackingAction}"),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Button(onClick = onOpenTraining, modifier = Modifier.fillMaxWidth()) {
            Text(language.t("Start tomorrow training", "开始明天训练"))
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            TextButton(onClick = onOpenPlan, modifier = Modifier.weight(1f)) {
                Text(language.t("Plan", "计划"))
            }
            TextButton(onClick = onOpenNutrition, modifier = Modifier.weight(1f)) {
                Text(language.t("Food", "饮食"))
            }
            TextButton(onClick = onOpenMetrics, modifier = Modifier.weight(1f)) {
                Text(language.t("Metrics", "数据"))
            }
        }
    }
}

@Composable
private fun WeeklyCheckInCard(
    summary: WeeklyCheckInSummary,
    language: AppLanguage,
    onOpenPlan: () -> Unit,
    onOpenNutrition: () -> Unit,
    onOpenMetrics: () -> Unit
) {
    SectionCard(
        title = language.t("Weekly Check-in", "周复盘"),
        subtitle = language.t(
            "Use 7-14 day execution before changing weekly volume, calories, or recovery pressure.",
            "用 7-14 天执行数据，再调整周训练量、热量或恢复压力。"
        )
    ) {
        MetricGrid(
            metrics = listOf(
                language.t("Status", "状态") to summary.statusLabel,
                language.t("Days", "天数") to summary.daysLogged.toString(),
                language.t("Training completion", "训练完成率") to "${summary.trainingCompletionPercent}%",
                language.t("Avg kcal", "平均热量") to (summary.averageCalories?.let { formatDecimal(it) } ?: "--"),
                language.t("Avg protein", "平均蛋白") to (summary.averageProtein?.let { "${formatDecimal(it)} g" } ?: "--"),
                language.t("Weight", "体重") to (summary.weightChangeKg?.let { "${formatSigned(it)} kg" } ?: "--"),
                language.t("Recovery", "恢复") to (summary.recoveryScoreAverage?.toString() ?: "--")
            )
        )
        Text(
            text = language.t("Next week action: ${summary.nextWeekAction}", "下周动作：${summary.nextWeekAction}"),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = language.t("Plan adjustment: ${summary.planAdjustment}", "计划调整：${summary.planAdjustment}"),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = language.t("Nutrition adjustment: ${summary.nutritionAdjustment}", "饮食调整：${summary.nutritionAdjustment}"),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        DataChipGrid(
            items = listOf(
                language.t("Weak point: ${summary.weakPointFocus}", "弱项：${summary.weakPointFocus}"),
                summary.dataQualityGate,
                summary.aiReviewFocus
            )
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            TextButton(onClick = onOpenPlan, modifier = Modifier.weight(1f)) {
                Text(language.t("Plan", "计划"))
            }
            TextButton(onClick = onOpenNutrition, modifier = Modifier.weight(1f)) {
                Text(language.t("Food", "饮食"))
            }
            TextButton(onClick = onOpenMetrics, modifier = Modifier.weight(1f)) {
                Text(language.t("Metrics", "数据"))
            }
        }
    }
}

@Composable
private fun DailyCoachChecklistCard(tasks: List<DailyCoachTask>, language: AppLanguage) {
    val completed = tasks.count { it.done }
    SectionCard(
        title = language.t("Daily Coach Checklist", "每日教练清单"),
        subtitle = language.t(
            "$completed/${tasks.size} complete. Follow this loop before trusting today's AI adjustment.",
            "已完成 $completed/${tasks.size}。先完成这个闭环，再信任今天的 AI 调整。"
        )
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
                        text = if (task.done) language.t("Done", "完成") else language.t("Next", "下一步"),
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
private fun DailyAutopilotCard(tasks: List<DailyCoachTask>, language: AppLanguage) {
    val completed = tasks.count { it.done }
    val nextTask = tasks.firstOrNull { !it.done && it.actionEnabled } ?: tasks.firstOrNull { !it.done } ?: tasks.lastOrNull()
    val gaps = tasks.filterNot { it.done }.take(3)
    SectionCard(
        title = language.t("Daily Autopilot", "每日自动教练"),
        subtitle = language.t(
            "One screen daily loop: follow the order, fill evidence gaps, then let AI update tomorrow.",
            "一个屏幕完成每日闭环：按顺序执行、补齐证据缺口，再让 AI 更新明天。"
        )
    ) {
        Text(
            text = language.t("DAILY AUTOPILOT", "每日自动教练"),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
        MetricGrid(
            metrics = listOf(
                language.t("Loop", "闭环") to language.t("Train -> Eat -> Sync -> Review", "训练 -> 饮食 -> 同步 -> 复盘"),
                language.t("Progress", "进度") to "$completed/${tasks.size}",
                language.t("Current next step", "当前下一步") to (nextTask?.title ?: language.t("Review complete", "复盘完成")),
                language.t("Today's evidence gaps", "今日证据缺口") to gaps.size.toString()
            )
        )
        LinearProgressIndicator(
            progress = { if (tasks.isEmpty()) 0f else completed.toFloat() / tasks.size.toFloat() },
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = language.t("Follow this order today", "今天按这个顺序执行"),
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.SemiBold
        )
        DataChipGrid(
            items = listOf(
                language.t("Training", "训练"),
                language.t("Nutrition", "饮食"),
                language.t("Metrics", "身体数据"),
                language.t("AI review", "AI 复盘")
            )
        )
        nextTask?.let { task ->
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                color = IfbbProGlassStrongSurface
            ) {
                Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(language.t("Autopilot action", "自动教练动作"), fontWeight = FontWeight.SemiBold)
                    Text(task.title, fontWeight = FontWeight.SemiBold)
                    Text(
                        text = task.detail,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Button(onClick = task.onAction, enabled = task.actionEnabled, modifier = Modifier.fillMaxWidth()) {
                        Text(task.actionLabel)
                    }
                }
            }
        }
        if (gaps.isEmpty()) {
            Text(
                text = language.t(
                    "All core evidence is ready. Run or review AI before changing training, calories, or split.",
                    "核心证据已就绪。改变训练、热量或分化前，先运行或查看 AI 复盘。"
                ),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        } else {
            Text(
                text = language.t("Today's evidence gaps", "今日证据缺口"),
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.SemiBold
            )
            gaps.forEach { gap ->
                Text(
                    text = "${gap.title}: ${gap.detail}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun TodayActionGrid(
    state: CoachUiState,
    language: AppLanguage,
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
            title = language.t("Training", "训练"),
            value = "${log.completedHardSets()}/${log.plannedHardSets()} sets",
            detail = "${formatDecimal(log.trainingVolumeKg())} kg volume",
            actionLabel = if (log.trainingSession.exercises.isEmpty()) {
                language.t("Build session", "创建训练")
            } else {
                language.t("Log sets", "记录组数")
            },
            onAction = if (log.trainingSession.exercises.isEmpty()) onOpenPlan else onOpenTraining
        )
        ActionCard(
            title = language.t("Nutrition", "饮食"),
            value = formatRemainingLocalized(pacing.caloriesRemaining, "kcal", language),
            detail = "${pacing.statusLabel} | Protein ${totals.protein}/${log.targets.protein} g",
            actionLabel = language.t("Log meal", "记录餐食"),
            onAction = onOpenNutrition
        )
        ActionCard(
            title = language.t("Recovery", "恢复"),
            value = formatOptional(log.metrics.sleepHours, "h"),
            detail = "Steps ${log.metrics.steps}, fatigue ${log.metrics.fatigue}/5",
            actionLabel = language.t("Sync metrics", "同步数据"),
            onAction = onOpenMetrics
        )
        ActionCard(
            title = language.t("Body", "身体"),
            value = formatOptional(log.metrics.bodyWeightKg, "kg"),
            detail = "Body fat ${formatOptional(log.metrics.bodyFatPercent, "%")}",
            actionLabel = language.t("Update", "更新"),
            onAction = onOpenMetrics
        )
    }
}

@Composable
private fun BeginnerGuideCard(
    language: AppLanguage,
    onOpenPlan: () -> Unit,
    onOpenNutrition: () -> Unit,
    onOpenMetrics: () -> Unit
) {
    SectionCard(
        title = language.t("Beginner Friendly Flow", "新手友好流程"),
        subtitle = language.t(
            "A simple daily loop before the deeper pro-level details.",
            "先按简单日常闭环执行，再进入更专业的细节分析。"
        )
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(language.t("1. Pick or apply today's plan.", "1. 选择或应用今天的训练计划。"))
            Text(language.t("2. Complete each working set and respect the rest timer.", "2. 完成每个工作组，并按休息倒计时执行。"))
            Text(language.t("3. Log meals or attach photos when portions are uncertain.", "3. 记录餐食；份量不确定时添加照片。"))
            Text(language.t("4. Sync health data, then run AI review.", "4. 同步健康数据，然后运行 AI 复盘。"))
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            TextButton(onClick = onOpenPlan, modifier = Modifier.weight(1f)) {
                Text(language.t("Plan", "计划"))
            }
            TextButton(onClick = onOpenNutrition, modifier = Modifier.weight(1f)) {
                Text(language.t("Food", "饮食"))
            }
            TextButton(onClick = onOpenMetrics, modifier = Modifier.weight(1f)) {
                Text(language.t("Metrics", "数据"))
            }
        }
    }
}

@Composable
private fun TrendOverviewCard(logs: List<DailyLog>, language: AppLanguage) {
    val window = logs.sortedBy { it.date }.takeLast(7)
    if (window.isEmpty()) {
        SectionCard(
            title = language.t("7-Day Trend", "7 日趋势"),
            subtitle = language.t(
                "Recent logs will appear here after you save a few days.",
                "保存几天记录后，近期趋势会显示在这里。"
            )
        ) {
            EmptyState(
                language.t(
                    "Log training, meals, and metrics for several days to unlock trend-based AI adjustments.",
                    "连续记录几天训练、餐食和身体数据后，AI 才能基于趋势做调整。"
                )
            )
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
    val weightSeries = window.mapNotNull { it.metrics.bodyWeightKg }
    val waistSeries = window.mapNotNull { it.metrics.waistCm }
    val sleepSeries = window.mapNotNull { it.metrics.sleepHours }
    val setSeries = window.map { it.completedHardSets().toDouble() }.filter { it > 0.0 }
    SectionCard(
        title = language.t("7-Day Trend", "7 日趋势"),
        subtitle = language.t(
            "Used by AI review to avoid overreacting to a single day.",
            "用于 AI 复盘，避免因为单日波动过度调整。"
        )
    ) {
        MetricGrid(
            metrics = listOf(
                language.t("Days", "天数") to window.size.toString(),
                language.t("Weight", "体重") to weightChange,
                language.t("Avg kcal", "平均热量") to (avgCalories?.let { formatDecimal(it) } ?: "--"),
                language.t("Avg protein", "平均蛋白") to (avgProtein?.let { "${formatDecimal(it)} g" } ?: "--"),
                language.t("Avg sleep", "平均睡眠") to (avgSleep?.let { "${formatDecimal(it)} h" } ?: "--"),
                language.t("Avg steps", "平均步数") to (avgSteps?.let { formatDecimal(it) } ?: "--"),
                language.t("Hard sets", "有效组") to "$totalCompletedSets/$totalPlannedSets"
            )
        )
        TrendSparkline(
            title = language.t("Weight trend graph", "体重趋势图"),
            values = weightSeries,
            unit = "kg",
            language = language
        )
        TrendSparkline(
            title = language.t("Waist trend graph", "腰围趋势图"),
            values = waistSeries,
            unit = "cm",
            language = language
        )
        TrendSparkline(
            title = language.t("Sleep trend graph", "睡眠趋势图"),
            values = sleepSeries,
            unit = "h",
            language = language
        )
        TrendSparkline(
            title = language.t("Training sets trend graph", "训练组数趋势图"),
            values = setSeries,
            unit = "sets",
            language = language
        )
        DataChipGrid(
            items = listOf(
                language.t("Body Trend Chart", "身体趋势图"),
                language.t("7-day trend graph", "7 天趋势图"),
                language.t("AI reads trend before adjusting", "AI 调整前先读趋势"),
                language.t("weight / waist / sleep / sets", "体重 / 腰围 / 睡眠 / 组数")
            )
        )
        window.forEach { day ->
            val totals = day.nutritionTotals()
            Text(
                text = if (language == AppLanguage.CHINESE) {
                    "${day.date}: ${day.completedHardSets()}/${day.plannedHardSets()} 组, ${totals.calories} kcal, 蛋白 ${totals.protein} g, 睡眠 ${formatOptional(day.metrics.sleepHours, "h")}, 体重 ${formatOptional(day.metrics.bodyWeightKg, "kg")}"
                } else {
                    "${day.date}: ${day.completedHardSets()}/${day.plannedHardSets()} sets, ${totals.calories} kcal, P ${totals.protein} g, sleep ${formatOptional(day.metrics.sleepHours, "h")}, weight ${formatOptional(day.metrics.bodyWeightKg, "kg")}"
                },
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun TrendSparkline(title: String, values: List<Double>, unit: String, language: AppLanguage) {
    val primary = MaterialTheme.colorScheme.primary
    val outline = MaterialTheme.colorScheme.outlineVariant
    val onSurface = MaterialTheme.colorScheme.onSurfaceVariant
    val minValue = values.minOrNull()
    val maxValue = values.maxOrNull()
    val latest = values.lastOrNull()
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(title, fontWeight = FontWeight.SemiBold)
                Text(
                    text = latest?.let { "${formatDecimal(it)} $unit" } ?: "--",
                    style = MaterialTheme.typography.bodySmall,
                    color = onSurface
                )
            }
            if (values.size < 2 || minValue == null || maxValue == null) {
                Text(
                    text = language.t("Need trend data", "需要趋势数据"),
                    style = MaterialTheme.typography.bodySmall,
                    color = onSurface
                )
            } else {
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                ) {
                    val horizontalPad = 6.dp.toPx()
                    val verticalPad = 10.dp.toPx()
                    val range = (maxValue - minValue).takeIf { it > 0.0001 } ?: 1.0
                    drawLine(
                        color = outline,
                        start = Offset(horizontalPad, size.height - verticalPad),
                        end = Offset(size.width - horizontalPad, size.height - verticalPad),
                        strokeWidth = 1.dp.toPx()
                    )
                    val points = values.mapIndexed { index, value ->
                        val x = if (values.size == 1) {
                            size.width / 2f
                        } else {
                            horizontalPad + ((size.width - horizontalPad * 2f) * index / (values.size - 1).toFloat())
                        }
                        val normalized = ((value - minValue) / range).toFloat()
                        val y = size.height - verticalPad - normalized * (size.height - verticalPad * 2f)
                        Offset(x, y)
                    }
                    points.zipWithNext().forEach { (start, end) ->
                        drawLine(
                            color = primary,
                            start = start,
                            end = end,
                            strokeWidth = 3.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    }
                    points.forEach { point ->
                        drawCircle(color = primary, radius = 3.5.dp.toPx(), center = point)
                    }
                }
            }
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
    val language = state.appLanguage
    val totals = log.nutritionTotals()
    val pacing = log.nutritionPacing()
    val readiness = state.dailyReadiness(language)
    val aiSetup = state.aiSetupStatus(language)
    val startSteps = state.dailyStartSteps(
        language = language,
        onOpenPlan = onOpenPlan,
        onOpenTraining = onOpenTraining,
        onOpenNutrition = onOpenNutrition,
        onOpenMetrics = onOpenMetrics,
        onRunAiReview = onDailyReview,
        onOpenAi = onOpenAi
    )
    val executionPlan = dailyExecutionPlan(
        log = log,
        recentLogs = state.recentLogs,
        profile = state.athleteProfile,
        hasWeeklyPlan = state.trainingPlan.days.any { it.exercises.isNotEmpty() },
        hasAiReviewToday = state.reviewHistory.any { it.logDate == log.date }
    )
    val tomorrowBrief = tomorrowCoachBrief(
        log = log,
        recentLogs = state.recentLogs,
        profile = state.athleteProfile,
        plan = state.trainingPlan
    )
    val weeklyCheckIn = weeklyCheckInSummary(
        log = log,
        recentLogs = state.recentLogs,
        profile = state.athleteProfile,
        plan = state.trainingPlan
    )
    val reviewQueue = aiReviewActionQueue(
        latestReview = state.reviewHistory.firstOrNull(),
        log = log
    )
    val flow = todayFlowCoachState(startSteps)
    var showTodayDetails by remember { mutableStateOf(false) }
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        TodayFlowCoachCard(
            state = state,
            readiness = readiness,
            flow = flow,
            executionPlan = executionPlan,
            showDetails = showTodayDetails,
            onToggleDetails = { showTodayDetails = !showTodayDetails },
            onOpenTraining = onOpenTraining,
            onOpenNutrition = onOpenNutrition,
            onOpenMetrics = onOpenMetrics
        )
        if (showTodayDetails) {
            StartHereCoachCard(
            state = state,
            readiness = readiness,
            steps = startSteps,
            onOpenTraining = onOpenTraining,
            onOpenNutrition = onOpenNutrition,
            onOpenMetrics = onOpenMetrics
        )
            AiSetupStatusCard(
            status = aiSetup,
            language = language,
            isLoading = state.isLoading,
            primaryLabel = aiSetup.primaryActionLabel,
            primaryEnabled = true,
            onPrimaryAction = if (aiSetup.canRunAi) onDailyReview else onOpenAi,
            secondaryLabel = language.t("Open AI Coach", "打开 AI 教练"),
            onSecondaryAction = onOpenAi
        )
            CommandCenterCard(
            readiness = readiness,
            state = state,
            language = language,
            onDailyReview = onDailyReview,
            onOpenTraining = onOpenTraining,
            onOpenNutrition = onOpenNutrition,
            onOpenMetrics = onOpenMetrics
        )
            DailyExecutionPlanCard(
            plan = executionPlan,
            language = language,
            onOpenPlan = onOpenPlan,
            onOpenTraining = onOpenTraining,
            onOpenNutrition = onOpenNutrition,
            onOpenMetrics = onOpenMetrics,
            onDailyReview = onDailyReview,
            onOpenAi = onOpenAi
        )
            AiReviewActionQueueCard(
            queue = reviewQueue,
            isLoading = state.isLoading,
            language = language,
            onDailyReview = onDailyReview,
            onOpenPlan = onOpenPlan,
            onOpenTraining = onOpenTraining,
            onOpenNutrition = onOpenNutrition,
            onOpenMetrics = onOpenMetrics,
            onOpenAi = onOpenAi
        )
            TomorrowCoachBriefCard(
            brief = tomorrowBrief,
            language = language,
            onOpenPlan = onOpenPlan,
            onOpenNutrition = onOpenNutrition,
            onOpenMetrics = onOpenMetrics
        )
            WeeklyCheckInCard(
            summary = weeklyCheckIn,
            language = language,
            onOpenPlan = onOpenPlan,
            onOpenNutrition = onOpenNutrition,
            onOpenMetrics = onOpenMetrics
        )
            DailyCoachChecklistCard(
            tasks = state.dailyCoachTasks(
                language = language,
                onOpenPlan = onOpenPlan,
                onOpenTraining = onOpenTraining,
                onOpenNutrition = onOpenNutrition,
                onOpenMetrics = onOpenMetrics,
                onRunAiReview = onDailyReview,
                onOpenAi = onOpenAi
            ),
            language = language
        )
            TodayExerciseVisualPrimerCard(
            log = log,
            language = language,
            onOpenPlan = onOpenPlan,
            onOpenTraining = onOpenTraining
        )
            TodayActionGrid(
            state = state,
            language = language,
            onOpenPlan = onOpenPlan,
            onOpenTraining = onOpenTraining,
            onOpenNutrition = onOpenNutrition,
            onOpenMetrics = onOpenMetrics
        )
            SectionCard(title = language.t("Today Snapshot", "今日快照"), subtitle = log.date) {
            MetricGrid(
                metrics = listOf(
                    language.t("Volume", "容量") to "${formatDecimal(log.trainingVolumeKg())} kg",
                    language.t("Calories", "热量") to "${totals.calories}/${log.targets.calories}",
                    language.t("Protein", "蛋白质") to "${totals.protein}/${log.targets.protein} g",
                    language.t("Macro pace", "宏量节奏") to "${pacing.adherenceScore}%",
                    language.t("Body fat", "体脂") to formatOptional(log.metrics.bodyFatPercent, "%"),
                    language.t("Sleep", "睡眠") to formatOptional(log.metrics.sleepHours, "h"),
                    language.t("Resting HR", "静息心率") to formatOptional(log.metrics.restingHeartRateBpm, "bpm")
                )
            )
            Text(
                text = language.t("Focus: ${log.trainingSession.plannedFocus}", "训练重点：${log.trainingSession.plannedFocus}"),
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = language.t(
                    "Nutrition: C ${totals.carbs}/${log.targets.carbs} g, F ${totals.fat}/${log.targets.fat} g, fiber ${totals.fiber}/${log.targets.fiber} g.",
                    "营养：碳水 ${totals.carbs}/${log.targets.carbs} g，脂肪 ${totals.fat}/${log.targets.fat} g，纤维 ${totals.fiber}/${log.targets.fiber} g。"
                ),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = language.t("Next meal: ${pacing.nextMealFocus}", "下一餐：${pacing.nextMealFocus}"),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = language.t(
                    "Recovery: waist ${formatOptional(log.metrics.waistCm, "cm")}, steps ${log.metrics.steps}, hunger ${log.metrics.hunger}/5, fatigue ${log.metrics.fatigue}/5, soreness ${log.metrics.soreness}/5.",
                    "恢复：腰围 ${formatOptional(log.metrics.waistCm, "cm")}，步数 ${log.metrics.steps}，饥饿 ${log.metrics.hunger}/5，疲劳 ${log.metrics.fatigue}/5，酸痛 ${log.metrics.soreness}/5。"
                ),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                TextButton(onClick = onReset, modifier = Modifier.weight(1f)) {
                    Text(language.t("Reset today", "重置今日"))
                }
                TextButton(onClick = onOpenMetrics, modifier = Modifier.weight(1f)) {
                    Text(language.t("Edit metrics", "编辑数据"))
                }
            }
        }
            PhotoEvidenceCard(photoEvidence = log.photoEvidence, language = language, compact = true)
            BodyCompositionCard(
            guidance = bodyCompositionGuidance(log, state.recentLogs, state.athleteProfile),
            subtitle = language.t("Trend-based target check before changing calories.", "调整热量前，先看趋势目标检查。"),
            language = language
        )
            ConditioningHydrationCard(
            log = log,
            recentLogs = state.recentLogs,
            profile = state.athleteProfile,
            language = language,
            compact = true
        )
            RecoveryGuidanceCard(
            guidance = recoveryGuidance(log, state.recentLogs),
            subtitle = language.t(
                "Sleep, soreness, stress, resting HR, and set pressure before pushing harder.",
                "继续加压前，先看睡眠、酸痛、压力、静息心率和训练组压力。"
            ),
            language = language
        )
            TrendOverviewCard(logs = state.recentLogs, language = language)
            BeginnerGuideCard(
            language = language,
            onOpenPlan = onOpenPlan,
            onOpenNutrition = onOpenNutrition,
            onOpenMetrics = onOpenMetrics
        )
        }
    }
}

@Composable
private fun TodayExerciseVisualPrimerCard(
    log: DailyLog,
    language: AppLanguage,
    onOpenPlan: () -> Unit,
    onOpenTraining: () -> Unit
) {
    val items = log.trainingSession.exercises.map { exercise ->
        ExerciseVisualMapItem(
            name = exercise.name,
            targetMuscle = exercise.targetMuscle,
            detail = "${exercise.sets} x ${exercise.reps} | done ${exercise.completedSetCount()}/${exercise.trackedSets().size}"
        )
    }
    SectionCard(
        title = language.t("Today Visual Primer", "今日动作图例预览"),
        subtitle = language.t(
            "Unified simple equipment/action instance diagrams before training, so exercise names map to a real station, free weight, or movement path.",
            "训练前先看统一器械/动作简图，把动作名称对应到真实器械、自由重量或动作轨迹。"
        )
    ) {
        if (items.isEmpty()) {
            Text(
                text = language.t(
                    "Apply a plan day or add exercises to see today's visual-map thumbnails and beginner recognition cues.",
                    "应用训练日或添加动作后，这里会显示今天的动作缩略图和新手识别提示。"
                ),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Button(onClick = onOpenPlan, modifier = Modifier.fillMaxWidth()) {
                Text(language.t("Open training plan", "打开训练计划"))
            }
        } else {
            val atlas = exerciseVisualAtlas()
            val specs = items.map { exerciseVisualSpec(it.name, it.targetMuscle) }
            DataChipGrid(
                items = listOf(
                    "Unified Exercise Visual Atlas",
                    "simple equipment/action instance diagrams",
                    "visual-map thumbnails",
                    "Exercise name -> visual category",
                    "Beginner recognition cue"
                ) + specs.distinctBy { it.visualId }.map { "${it.visualId} ${it.equipmentZh}" }
            )
            Text(
                text = language.t(
                    "Three-step recognition: ${atlas.recognitionFlow.joinToString(" -> ")}.",
                    "三步识别：${atlas.recognitionFlow.joinToString(" -> ")}。"
                ),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            items.take(4).forEachIndexed { index, item ->
                if (index > 0) {
                    HorizontalDivider()
                }
                ExerciseVisualMapRow(item = item)
            }
            if (items.size > 4) {
                Text(
                    text = language.t(
                        "+${items.size - 4} more exercises. Open Training for the full Today's Exercise Visual Map.",
                        "还有 ${items.size - 4} 个动作。打开训练页查看完整今日动作图。"
                    ),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Button(onClick = onOpenTraining, modifier = Modifier.fillMaxWidth()) {
                Text(language.t("Open visual map", "打开动作图"))
            }
        }
    }
}

@Composable
private fun NutritionPacingCard(log: DailyLog, language: AppLanguage) {
    val pacing = log.nutritionPacing()
    SectionCard(
        title = language.t("Nutrition Pacing", "营养节奏"),
        subtitle = language.t(
            "What is still available today and what the next meal should bias toward.",
            "今天还剩多少宏量空间，以及下一餐应该优先补什么。"
        )
    ) {
        MetricGrid(
            metrics = listOf(
                language.t("Pace", "节奏") to "${pacing.adherenceScore}%",
                language.t("Status", "状态") to pacing.localizedStatusLabel(language),
                "Kcal" to formatRemainingLocalized(pacing.caloriesRemaining, "kcal", language),
                language.t("Protein", "蛋白质") to formatRemainingLocalized(pacing.proteinRemaining, "g", language),
                language.t("Carbs", "碳水") to formatRemainingLocalized(pacing.carbsRemaining, "g", language),
                language.t("Fat", "脂肪") to formatRemainingLocalized(pacing.fatRemaining, "g", language),
                language.t("Fiber", "纤维") to formatRemainingLocalized(pacing.fiberRemaining, "g", language)
            )
        )
        LinearProgressIndicator(
            progress = { pacing.adherenceScore / 100f },
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = language.t(
                "Next meal focus: ${pacing.localizedNextMealFocus(language)}",
                "下一餐重点：${pacing.localizedNextMealFocus(language)}"
            ),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = language.t(
                "Negative remaining values mean the target is already exceeded; AI review uses this with training demand and recent trends before changing tomorrow's targets.",
                "剩余值为负数表示已经超出目标；AI 复盘会结合训练需求和近期趋势，再决定是否调整明天目标。"
            ),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun NextMealBuilderCard(log: DailyLog, language: AppLanguage) {
    val builder = log.nextMealBuilder()
    SectionCard(
        title = language.t("Next Meal Builder", "下一餐构建器"),
        subtitle = language.t(
            "Turn remaining macros and training demand into the next practical meal.",
            "把剩余宏量和训练需求转成下一顿真正能吃的餐。"
        )
    ) {
        Text(builder.localizedTitle(language), style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
        Text(
            text = builder.localizedSummary(language),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        MetricGrid(
            metrics = listOf(
                language.t("Protein", "蛋白质") to "${builder.proteinGrams} g",
                language.t("Carbs", "碳水") to "${builder.carbsGrams} g",
                language.t("Fat", "脂肪") to "${builder.fatGrams} g",
                language.t("Fiber", "纤维") to "${builder.fiberGrams} g"
            )
        )
        Text(
            text = builder.localizedTimingCue(language),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = builder.localizedPortionCue(language),
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = builder.localizedPhotoCue(language),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun MealLoggingCoachCard(
    log: DailyLog,
    language: AppLanguage,
    onPrefillMeal: (NextMealBuilder) -> Unit,
    onAddMealTemplate: (String) -> Unit,
    onPickMealPhoto: (String) -> Unit,
    onOpenAi: () -> Unit
) {
    val pacing = log.nutritionPacing()
    val builder = log.nextMealBuilder()
    val template = log.recommendedMealTemplate()
    val mealPhotoCount = log.photoEvidence.count { it.type == PhotoEvidenceType.MEAL || it.type == PhotoEvidenceType.MENU_LABEL }
    SectionCard(
        title = language.t("Meal Logging Coach", "餐食记录教练"),
        subtitle = language.t(
            "A guided loop for logging, photo evidence, and AI nutrition review.",
            "把记录餐食、照片证据和 AI 营养复盘接成一个清晰流程。"
        )
    ) {
        MetricGrid(
            metrics = listOf(
                language.t("Next target", "下一餐目标") to builder.localizedTitle(language),
                language.t("Suggested kcal", "建议热量") to "${builder.estimatedCalories()} kcal",
                language.t("Template", "推荐模板") to template.localizedTitle(language),
                language.t("Food photos", "食物照片") to mealPhotoCount.toString()
            )
        )
        Text(
            text = builder.localizedSummary(language),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = log.recommendedMealTemplateReason(language),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        DataChipGrid(
            items = listOf(
                language.t("Weigh food when practical", "能称重时优先称重"),
                language.t("Photo portions, oil, sauce, label, and bowl depth", "拍清份量、油、酱汁、标签和碗深"),
                language.t("AI compares food data with training volume and body trend", "AI 会把饮食与训练容量、身体趋势联动分析"),
                pacing.localizedStatusLabel(language)
            )
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { onPrefillMeal(builder) }, modifier = Modifier.weight(1f)) {
                Text(language.t("Prefill target", "填入建议餐"))
            }
            ElevatedButton(onClick = { onAddMealTemplate(template.id) }, modifier = Modifier.weight(1f)) {
                Text(language.t("Add template", "添加模板"))
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            ElevatedButton(
                onClick = {
                    onPickMealPhoto(
                        "Food/menu/label photo for AI nutrition estimate. Next meal target: ${builder.title}; P ${builder.proteinGrams} g, C ${builder.carbsGrams} g, F ${builder.fatGrams} g, fiber ${builder.fiberGrams} g. Capture portion size, oil, sauce, label, menu, and bowl depth."
                    )
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(language.t("Food photo", "食物照片"))
            }
            TextButton(onClick = onOpenAi, modifier = Modifier.weight(1f)) {
                Text(language.t("AI review", "AI 复盘"))
            }
        }
        Text(
            text = language.t(
                "AI link: daily review uses logged macros, meal/menu photos, training demand, health metrics, and body-composition trend before changing calories or macros.",
                "AI 联动：每日复盘会结合已记录宏量、餐食/菜单照片、训练需求、健康数据和身体趋势，再决定是否调整热量或宏量。"
            ),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun LiveMealCoachCard(
    log: DailyLog,
    language: AppLanguage,
    mealDescription: String,
    onMealDescriptionChange: (String) -> Unit,
    onUseAiEstimate: () -> Unit,
    onPickMealPhoto: (String) -> Unit,
    onOpenAi: () -> Unit
) {
    val pacing = log.nutritionPacing()
    val builder = log.nextMealBuilder()
    val totals = log.nutritionTotals()
    val targets = log.targets
    val estimateCalories = builder.estimatedCalories()
    val caloriesAfter = targets.calories - totals.calories - estimateCalories
    val proteinAfter = targets.protein - totals.protein - builder.proteinGrams
    val carbsAfter = targets.carbs - totals.carbs - builder.carbsGrams
    val fatAfter = targets.fat - totals.fat - builder.fatGrams
    val evidenceCount = log.meals.size + log.photoEvidence.count {
        it.type == PhotoEvidenceType.MEAL || it.type == PhotoEvidenceType.MENU_LABEL
    }

    SectionCard(
        title = language.t("Live Meal Coach", "当前餐教练"),
        subtitle = language.t(
            "Eat or log this meal now; AI uses the estimate with training demand, sleep, health metrics, and body trend before changing tomorrow.",
            "现在吃或记录这一餐；AI 会把估算与训练需求、睡眠、健康数据和身体趋势联动后，再决定明天是否调整。"
        )
    ) {
        Text(
            text = language.t("LIVE MEAL COACH", "当前餐教练"),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
            color = IfbbProGlassStrongSurface
        ) {
            Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(7.dp)) {
                Text(language.t("Eat or log this meal now", "现在吃或记录这一餐"), fontWeight = FontWeight.SemiBold)
                Text(
                    text = builder.localizedTitle(language),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = builder.localizedSummary(language),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = builder.localizedPortionCue(language),
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
        MetricGrid(
            metrics = listOf(
                language.t("Estimated kcal", "估算热量") to "$estimateCalories kcal",
                language.t("Protein", "蛋白质") to "${builder.proteinGrams} g",
                language.t("Carbs", "碳水") to "${builder.carbsGrams} g",
                language.t("Fat", "脂肪") to "${builder.fatGrams} g",
                language.t("Remaining after", "餐后剩余") to listOf(
                    "Kcal ${formatRemainingLocalized(caloriesAfter, "kcal", language)}",
                    "P ${formatRemainingLocalized(proteinAfter, "g", language)}",
                    "C ${formatRemainingLocalized(carbsAfter, "g", language)}",
                    "F ${formatRemainingLocalized(fatAfter, "g", language)}"
                ).joinToString(" | "),
                language.t("One-tap meal evidence", "一键餐食证据") to "$evidenceCount"
            )
        )
        OutlinedTextField(
            value = mealDescription,
            onValueChange = onMealDescriptionChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(language.t("Photo or text meal note", "拍照或文字记录这一餐")) },
            placeholder = {
                Text(language.t("chicken rice bowl, sauce on side", "鸡肉米饭碗，酱汁另放"))
            },
            minLines = 2
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            ElevatedButton(
                onClick = {
                    onPickMealPhoto(
                        "One-tap meal evidence for AI nutrition estimate. Target: ${builder.title}; estimate $estimateCalories kcal, P ${builder.proteinGrams} g, C ${builder.carbsGrams} g, F ${builder.fatGrams} g. Capture portion size, oil, sauce, label, menu, and bowl depth."
                    )
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(language.t("Food photo", "食物照片"))
            }
            Button(onClick = onUseAiEstimate, modifier = Modifier.weight(1f)) {
                Text(language.t("Log this meal estimate", "记录这餐估算"))
            }
        }
        TextButton(onClick = onOpenAi, modifier = Modifier.fillMaxWidth()) {
            Text(language.t("Send meal evidence to AI review", "把餐食证据交给 AI 复盘"))
        }
        DataChipGrid(
            items = listOf(
                "LiveMealCoachCard",
                "LIVE MEAL COACH",
                language.t("Photo or text -> AI estimate -> meal log", "照片或文字 -> AI 估算 -> 餐食记录"),
                language.t("One-tap meal evidence", "一键餐食证据"),
                pacing.localizedStatusLabel(language),
                language.t("当前餐教练", "当前餐教练"),
                language.t("记录这餐估算", "记录这餐估算")
            )
        )
    }
}

@Composable
private fun NutritionAutopilotCard(
    log: DailyLog,
    language: AppLanguage,
    onPrefillMeal: (NextMealBuilder) -> Unit,
    onPickMealPhoto: (String) -> Unit,
    onOpenAi: () -> Unit
) {
    val pacing = log.nutritionPacing()
    val builder = log.nextMealBuilder()
    val mealPhotoCount = log.photoEvidence.count { it.type == PhotoEvidenceType.MEAL || it.type == PhotoEvidenceType.MENU_LABEL }
    val photoConfidence = when {
        mealPhotoCount > 0 && log.meals.isNotEmpty() -> language.t("High: photo + logged meal", "高：照片 + 已记录餐食")
        mealPhotoCount > 0 -> language.t("Medium: photo ready", "中：已有照片")
        log.meals.isNotEmpty() -> language.t("Medium: text log only", "中：仅文字记录")
        else -> language.t("Low: capture first", "低：先拍照或描述")
    }
    val nextNutritionAction = when {
        log.meals.isEmpty() && mealPhotoCount == 0 -> language.t("Capture first meal", "记录第一餐")
        pacing.proteinRemaining > 30 -> language.t("Close protein gap", "补齐蛋白质缺口")
        pacing.caloriesRemaining < -150 -> language.t("Keep next meal lean", "下一餐保持低脂清淡")
        mealPhotoCount == 0 -> language.t("Add photo if portions are uncertain", "份量不确定就加照片")
        else -> language.t("Confirm and review with AI", "确认后交给 AI 复盘")
    }
    SectionCard(
        title = language.t("Nutrition Autopilot", "饮食自动教练"),
        subtitle = language.t(
            "One-tap meal loop: eat the target, capture uncertainty, confirm the log, then let AI adjust tomorrow.",
            "一键式饮食闭环：按目标吃、拍下不确定份量、确认记录，再让 AI 调整明天。"
        )
    ) {
        Text(
            text = language.t("NUTRITION AUTOPILOT", "饮食自动教练"),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
        MetricGrid(
            metrics = listOf(
                language.t("Loop", "闭环") to language.t("Eat -> Capture -> Confirm -> Review", "吃 -> 拍照/描述 -> 确认 -> 复盘"),
                language.t("Current meal target", "当前餐目标") to builder.localizedTitle(language),
                language.t("Macro gaps", "宏量缺口") to "P ${formatRemainingLocalized(pacing.proteinRemaining, "g", language)} / C ${formatRemainingLocalized(pacing.carbsRemaining, "g", language)}",
                language.t("Photo confidence", "照片置信度") to photoConfidence
            )
        )
        LinearProgressIndicator(
            progress = { pacing.adherenceScore / 100f },
            modifier = Modifier.fillMaxWidth()
        )
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
            color = IfbbProGlassStrongSurface
        ) {
            Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(language.t("Next nutrition action", "下一步饮食动作"), fontWeight = FontWeight.SemiBold)
                Text(nextNutritionAction, fontWeight = FontWeight.SemiBold)
                Text(
                    text = builder.localizedSummary(language),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Button(onClick = { onPrefillMeal(builder) }, modifier = Modifier.fillMaxWidth()) {
                    Text(language.t("Prefill next meal", "填入下一餐"))
                }
            }
        }
        DataChipGrid(
            items = listOf(
                language.t("Eat", "吃"),
                language.t("Capture", "拍照/描述"),
                language.t("Confirm", "确认"),
                language.t("Review", "复盘")
            )
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            ElevatedButton(
                onClick = {
                    onPickMealPhoto(
                        "Nutrition Autopilot food/menu/label photo. Current meal target: ${builder.title}; P ${builder.proteinGrams} g, C ${builder.carbsGrams} g, F ${builder.fatGrams} g, fiber ${builder.fiberGrams} g. Capture portion size, oil, sauce, labels, menu, and bowl depth for AI nutrition estimate."
                    )
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(language.t("Capture food", "拍摄食物"))
            }
            TextButton(onClick = onOpenAi, modifier = Modifier.weight(1f)) {
                Text(language.t("AI review", "AI 复盘"))
            }
        }
        Text(
            text = language.t(
                "AI uses this with training volume, sleep, weight trend, photos, and logged macros before changing calories or macros.",
                "AI 会把这里的数据与训练容量、睡眠、体重趋势、照片和已记录宏量联动，再决定是否调整热量或宏量。"
            ),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun MealFlowCoachCard(
    log: DailyLog,
    language: AppLanguage,
    showDetails: Boolean,
    onToggleDetails: () -> Unit,
    onPrefillMeal: (NextMealBuilder) -> Unit,
    onAddMealTemplate: (String) -> Unit,
    onPickMealPhoto: (String) -> Unit,
    onOpenAi: () -> Unit
) {
    val pacing = log.nutritionPacing()
    val builder = log.nextMealBuilder()
    val template = log.recommendedMealTemplate()
    val mealPhotoCount = log.photoEvidence.count { it.type == PhotoEvidenceType.MEAL || it.type == PhotoEvidenceType.MENU_LABEL }
    val aiGate = if (log.meals.isNotEmpty() || mealPhotoCount > 0) {
        language.t("Evidence ready", "证据可用")
    } else {
        language.t("Log food first", "先记录食物")
    }
    SectionCard(
        title = language.t("Meal Flow Coach", "餐食流程教练"),
        subtitle = language.t(
            "Open Nutrition, follow the next meal, then expand macro reasoning only when needed.",
            "打开饮食页后先执行下一餐；需要原因时再展开宏量营养推理。"
        )
    ) {
        Text(
            text = language.t("NEXT MEAL ACTION", "下一餐动作"),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = builder.localizedTitle(language),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = builder.localizedSummary(language),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = builder.localizedPortionCue(language),
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.SemiBold
        )
        LinearProgressIndicator(
            progress = { pacing.adherenceScore / 100f },
            modifier = Modifier.fillMaxWidth()
        )
        Button(onClick = { onPrefillMeal(builder) }, modifier = Modifier.fillMaxWidth()) {
            Text(language.t("Prefill next meal", "填入下一餐"))
        }
        TextButton(onClick = onToggleDetails, modifier = Modifier.fillMaxWidth()) {
            Text(
                if (showDetails) {
                    language.t("Hide nutrition details", "收起饮食细节")
                } else {
                    language.t("Show nutrition details", "展开饮食细节")
                }
            )
        }
        if (showDetails) {
            MetricGrid(
                metrics = listOf(
                    language.t("Next meal", "下一餐") to builder.localizedTitle(language),
                    "Kcal" to formatRemainingLocalized(pacing.caloriesRemaining, "kcal", language),
                    language.t("Protein", "蛋白质") to formatRemainingLocalized(pacing.proteinRemaining, "g", language),
                    language.t("Template", "模板") to template.localizedTitle(language),
                    language.t("Food photos", "食物照片") to mealPhotoCount.toString(),
                    language.t("AI gate", "AI 门控") to aiGate
                )
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ElevatedButton(
                    onClick = {
                        onPickMealPhoto(
                            "Food/menu/label photo for AI nutrition estimate. Next meal target: ${builder.title}; P ${builder.proteinGrams} g, C ${builder.carbsGrams} g, F ${builder.fatGrams} g, fiber ${builder.fiberGrams} g. Capture portion size, oil, sauce, label, menu, and bowl depth."
                        )
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(language.t("Food photo", "食物照片"))
                }
                ElevatedButton(onClick = { onAddMealTemplate(template.id) }, modifier = Modifier.weight(1f)) {
                    Text(language.t("Add template", "添加模板"))
                }
            }
            TextButton(onClick = onOpenAi, modifier = Modifier.fillMaxWidth()) {
                Text(language.t("AI nutrition review", "AI 饮食复盘"))
            }
            DataChipGrid(
                items = listOf(
                    language.t("Next meal macro targets", "下一餐宏量目标"),
                    language.t("Portion uncertainty cue", "份量不确定提示"),
                    language.t("Food photo nutrition estimate", "食物照片营养估算"),
                    language.t("Nutrition detail layers open", "饮食细节层已展开")
                )
            )
        } else {
            Text(
                text = language.t("Nutrition detail layers hidden", "饮食细节层已收起"),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun QuickMealCaptureCard(
    log: DailyLog,
    language: AppLanguage,
    mealDescription: String,
    onMealDescriptionChange: (String) -> Unit,
    onUseAiEstimate: () -> Unit,
    onPickMealPhoto: (String) -> Unit,
    onOpenAi: () -> Unit
) {
    val pacing = log.nutritionPacing()
    val builder = log.nextMealBuilder()
    val totals = log.nutritionTotals()
    val targets = log.targets
    SectionCard(
        title = language.t("Quick meal capture", "快速记录餐食"),
        subtitle = language.t(
            "Take a photo or describe the meal; AI estimates calories, protein, carbs, and fat, then you confirm the log.",
            "拍照或描述这餐；AI 估算热量、蛋白质、碳水和脂肪，然后你确认记录。"
        )
    ) {
        MetricGrid(
            metrics = listOf(
                language.t("Calories left", "剩余热量") to formatRemainingLocalized(pacing.caloriesRemaining, "kcal", language),
                language.t("Protein left", "剩余蛋白质") to formatRemainingLocalized(pacing.proteinRemaining, "g", language),
                language.t("Carbs left", "剩余碳水") to formatRemainingLocalized(pacing.carbsRemaining, "g", language),
                language.t("Fat left", "剩余脂肪") to formatRemainingLocalized(pacing.fatRemaining, "g", language)
            )
        )
        LinearProgressIndicator(
            progress = { pacing.adherenceScore / 100f },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = mealDescription,
            onValueChange = onMealDescriptionChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(language.t("Describe meal", "描述这餐")) },
            placeholder = {
                Text(language.t("chicken rice bowl, sauce on side", "鸡肉米饭碗，酱汁另放"))
            },
            minLines = 2
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            ElevatedButton(
                onClick = {
                    onPickMealPhoto(
                        "Food photo nutrition estimate. Describe meal: ${mealDescription.ifBlank { builder.title }}. Capture portion size, oil, sauce, label, menu, and bowl depth."
                    )
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(language.t("Food photo", "食物照片"))
            }
            ElevatedButton(onClick = onOpenAi, modifier = Modifier.weight(1f)) {
                Text(language.t("Ask AI", "询问 AI"))
            }
        }
        Text(
            text = language.t(
                "AI estimate preview: ${builder.localizedTitle(language)} | ${builder.estimatedCalories()} kcal | P ${builder.proteinGrams} g | C ${builder.carbsGrams} g | F ${builder.fatGrams} g.",
                "AI 估算预览：${builder.localizedTitle(language)} | ${builder.estimatedCalories()} kcal | 蛋白质 ${builder.proteinGrams} g | 碳水 ${builder.carbsGrams} g | 脂肪 ${builder.fatGrams} g。"
            ),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Button(onClick = onUseAiEstimate, modifier = Modifier.fillMaxWidth()) {
            Text(language.t("Use AI estimate", "采用 AI 估算"))
        }
        DataChipGrid(
            items = listOf(
                language.t("Photo or text -> AI estimate -> meal log", "照片或文字 -> AI 估算 -> 餐食记录"),
                language.t("Daily remaining macros stay visible", "每日剩余宏量始终可见"),
                language.t("Totals now ${totals.calories}/${targets.calories} kcal", "当前热量 ${totals.calories}/${targets.calories} kcal")
            )
        )
    }
}

@Composable
private fun MealEstimateReceiptCard(
    log: DailyLog,
    language: AppLanguage,
    mealDescription: String,
    onUseAiEstimate: () -> Unit,
    onPickMealPhoto: (String) -> Unit,
    onOpenAi: () -> Unit
) {
    val builder = log.nextMealBuilder()
    val totals = log.nutritionTotals()
    val targets = log.targets
    val estimateCalories = builder.estimatedCalories()
    val caloriesAfter = targets.calories - totals.calories - estimateCalories
    val proteinAfter = targets.protein - totals.protein - builder.proteinGrams
    val carbsAfter = targets.carbs - totals.carbs - builder.carbsGrams
    val fatAfter = targets.fat - totals.fat - builder.fatGrams
    val latestMealPhoto = log.photoEvidence.lastOrNull {
        it.type == PhotoEvidenceType.MEAL || it.type == PhotoEvidenceType.MENU_LABEL
    }
    val loggedStatus = if (log.meals.any { it.notes.contains("AI estimate", ignoreCase = true) }) {
        language.t("Logged to today", "已计入今日目标")
    } else {
        language.t("Ready to confirm", "等待确认计入")
    }
    val confidence = when {
        latestMealPhoto != null && mealDescription.isNotBlank() -> language.t("High with photo + text", "照片 + 文字，较高")
        latestMealPhoto != null -> language.t("Medium with photo", "照片估算，中等")
        mealDescription.isNotBlank() -> language.t("Medium with text", "文字估算，中等")
        else -> language.t("Low until photo or description", "需照片或描述提高可信度")
    }

    SectionCard(
        title = language.t("Meal estimate receipt", "餐食估算回执"),
        subtitle = language.t(
            "Confirm portions, oil, sauce, and labels before treating the AI estimate as exact.",
            "把 AI 估算当作精确记录前，先确认份量、油、酱汁和标签。"
        )
    ) {
        Text(
            text = language.t("AI MEAL ESTIMATE", "AI 餐食估算"),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
        MetricGrid(
            metrics = listOf(
                language.t("Meal", "餐食") to mealDescription.ifBlank { builder.localizedTitle(language) },
                language.t("Confidence", "可信度") to confidence,
                language.t("Status", "状态") to loggedStatus,
                language.t("Photo", "照片") to (latestMealPhoto?.type?.localizedLabel(language) ?: language.t("Not attached", "未添加"))
            )
        )
        Text(
            text = language.t("Estimated macros", "估算宏量营养"),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        MetricGrid(
            metrics = listOf(
                "Kcal" to "$estimateCalories",
                language.t("Protein", "蛋白质") to "${builder.proteinGrams} g",
                language.t("Carbs", "碳水") to "${builder.carbsGrams} g",
                language.t("Fat", "脂肪") to "${builder.fatGrams} g",
                language.t("Fiber", "纤维") to "${builder.fiberGrams} g"
            )
        )
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
            color = IfbbProGlassStrongSurface
        ) {
            Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(language.t("Remaining after this meal", "这餐后剩余目标"), fontWeight = FontWeight.SemiBold)
                Text(
                    text = listOf(
                        "Kcal ${formatRemainingLocalized(caloriesAfter, "kcal", language)}",
                        "${language.t("Protein", "蛋白质")} ${formatRemainingLocalized(proteinAfter, "g", language)}",
                        "${language.t("Carbs", "碳水")} ${formatRemainingLocalized(carbsAfter, "g", language)}",
                        "${language.t("Fat", "脂肪")} ${formatRemainingLocalized(fatAfter, "g", language)}"
                    ).joinToString(" | "),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        Text(
            text = language.t(
                "Confirm portions, oil, sauce, and labels. If uncertain, attach a food/menu/label photo before AI changes calories or macros.",
                "确认份量、油、酱汁和标签。不确定时先添加食物/菜单/标签照片，再让 AI 改热量或宏量。"
            ),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = onUseAiEstimate, modifier = Modifier.weight(1f)) {
                Text(language.t("Log estimate", "计入估算"))
            }
            ElevatedButton(
                onClick = {
                    onPickMealPhoto(
                        "Food/menu/label photo to improve AI meal estimate. Current estimate: ${builder.localizedTitle(language)} | $estimateCalories kcal | P ${builder.proteinGrams} g | C ${builder.carbsGrams} g | F ${builder.fatGrams} g. Confirm portions, oil, sauce, and labels."
                    )
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(language.t("Add photo", "添加照片"))
            }
        }
        TextButton(onClick = onOpenAi, modifier = Modifier.fillMaxWidth()) {
            Text(language.t("Ask AI to review meal", "让 AI 复盘这餐"))
        }
        DataChipGrid(
            items = listOf(
                "MealEstimateReceiptCard",
                "AI MEAL ESTIMATE",
                "Meal estimate receipt",
                "Estimated macros",
                "Confidence",
                "Logged to today",
                "Remaining after this meal",
                "Confirm portions, oil, sauce, and labels",
                "餐食估算回执"
            )
        )
    }
}

@Composable
private fun MealAssemblyGuideCard(guide: MealAssemblyGuide, language: AppLanguage) {
    SectionCard(
        title = language.t("Meal Assembly Guide", "餐盘组合指南"),
        subtitle = language.t(
            "Turn the macro target into a simple plate, shopping cue, prep cue, and photo logging cue.",
            "把宏量目标转成餐盘结构、采购提示、备餐提示和拍照记录提示。"
        )
    ) {
        Text(guide.localizedTitle(language), style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
        Text(
            text = guide.localizedPlateStructure(language),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        DataChipGrid(
            items = listOf(
                guide.localizedProteinAnchor(language),
                guide.localizedCarbAnchor(language),
                guide.localizedFatControl(language),
                guide.localizedFiberMicros(language)
            )
        )
        Text(
            text = language.t(
                "Shopping: ${guide.localizedShoppingCue(language)}",
                "采购：${guide.localizedShoppingCue(language)}"
            ),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = language.t(
                "Prep: ${guide.localizedPrepCue(language)}",
                "备餐：${guide.localizedPrepCue(language)}"
            ),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = language.t(
                "Photo/logging: ${guide.localizedPhotoLoggingCue(language)}",
                "照片/记录：${guide.localizedPhotoLoggingCue(language)}"
            ),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = language.t(
                "Avoid: ${guide.localizedAvoidCue(language)}",
                "避免：${guide.localizedAvoidCue(language)}"
            ),
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun BodyCompositionCard(guidance: BodyCompositionGuidance, subtitle: String, language: AppLanguage) {
    SectionCard(title = language.t("Body Composition Guidance", "身体组成指导"), subtitle = subtitle) {
        MetricGrid(
            metrics = listOf(
                language.t("Status", "状态") to guidance.localizedStatusLabel(language),
                language.t("Phase", "阶段") to guidance.localizedPhaseGoal(language),
                language.t("Weight trend", "体重趋势") to guidance.weightChangeKg.formatSignedHistoryValue("kg"),
                language.t("Avg kcal", "平均热量") to guidance.averageCalories.formatHistoryValue("kcal"),
                language.t("Avg protein", "平均蛋白") to guidance.averageProtein.formatHistoryValue("g"),
                language.t("Set avg", "平均组数") to guidance.averageCompletedSets.formatHistoryValue("sets"),
                language.t("Kcal adjust", "热量调整") to guidance.calorieAdjustmentKcal.formatSignedInt("kcal"),
                language.t("Target", "目标") to "${guidance.targetCalories} kcal"
            )
        )
        Text(
            text = guidance.localizedRationale(language),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = language.t(
                "Next action: ${guidance.localizedNextAction(language)}",
                "下一步：${guidance.localizedNextAction(language)}"
            ),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun RecoveryGuidanceCard(guidance: RecoveryGuidance, subtitle: String, language: AppLanguage) {
    SectionCard(title = language.t("Recovery Guidance", "恢复建议"), subtitle = subtitle) {
        MetricGrid(
            metrics = listOf(
                language.t("Status", "状态") to guidance.statusLabel,
                language.t("Score", "评分") to guidance.readinessScore.toString(),
                language.t("Pressure", "训练压力") to guidance.trainingPressure,
                language.t("Sleep signal", "睡眠信号") to guidance.sleepSignal,
                language.t("Stress signal", "压力信号") to guidance.stressSignal,
                language.t("Soreness", "酸痛") to guidance.sorenessSignal,
                language.t("HR signal", "心率信号") to guidance.heartRateSignal,
                language.t("Training action", "训练动作") to guidance.recommendedTrainingAction
            )
        )
        Text(
            text = guidance.rationale,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = language.t("Next action: ${guidance.nextAction}", "下一步：${guidance.nextAction}"),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun RestTimerBanner(timer: RestTimerState, language: AppLanguage, onSkip: () -> Unit) {
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
                Text(language.t("Rest timer", "休息倒计时"), fontWeight = FontWeight.SemiBold)
                Text(
                    text = language.t(
                        "${timer.exerciseName} set ${timer.setNumber} finished. Next set in ${formatTimer(timer.remainingSeconds)}.",
                        "${timer.exerciseName} 第 ${timer.setNumber} 组完成。下一组还有 ${formatTimer(timer.remainingSeconds)}。"
                    ),
                    style = MaterialTheme.typography.bodyMedium
                )
                if (timer.prescriptionLabel.isNotBlank() || timer.coachCue.isNotBlank()) {
                    Text(
                        text = language.t(
                            "AI matched rest: ${timer.prescriptionLabel.ifBlank { "active" }}. ${timer.coachCue}",
                            "AI 已匹配休息：${timer.prescriptionLabel.ifBlank { "进行中" }}。${timer.coachCue}"
                        ),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
            TextButton(onClick = onSkip) {
                Text(language.t("Skip", "跳过"))
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
private fun QuickSetupGateCard(
    profile: AthleteProfile,
    language: AppLanguage,
    onProfileChange: (AthleteProfile) -> Unit,
    onApplyTemplate: (String) -> Unit
) {
    val recommendedTemplate = trainingPlanTemplates().firstOrNull { it.id == recommendedPlanTemplateId(profile) }
        ?: trainingPlanTemplates().first()
    val goalOptions = listOf(
        language.t("Build muscle", "增肌") to "Build an IFBB PRO-inspired physique",
        language.t("Recomp", "增肌减脂") to "Build muscle while slowly reducing body fat",
        language.t("Cut", "减脂") to "Reduce body fat while preserving muscle"
    )
    val dayOptions = listOf(3, 4, 5)
    val equipmentOptions = listOf(
        language.t("Full gym", "完整健身房") to "Full gym",
        language.t("Basic gym", "基础健身房") to "Basic gym",
        language.t("Dumbbells", "哑铃/居家") to "Dumbbells and adjustable bench"
    )

    SectionCard(
        title = language.t("Quick Setup Gate", "快速起始设置"),
        subtitle = language.t(
            "3-tap setup gate: choose goal, weekly days, and equipment before the app recommends the first split.",
            "三步起始设置：先选目标、每周天数和器械条件，再让 App 推荐第一个分化。"
        )
    ) {
        MetricGrid(
            metrics = listOf(
                language.t("Goal", "目标") to profile.primaryGoal,
                language.t("Days/wk", "每周天数") to profile.weeklyTrainingDays.toString(),
                language.t("Equipment", "器械") to profile.availableEquipment,
                language.t("Recommended split", "推荐分化") to recommendedTemplate.localizedTitle(language)
            )
        )
        Text(
            text = language.t("Goal / Days / Equipment", "目标 / 天数 / 器械"),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            goalOptions.forEach { (label, value) ->
                FilterChip(
                    selected = profile.primaryGoal == value,
                    onClick = { onProfileChange(profile.copy(primaryGoal = value)) },
                    label = { Text(label) }
                )
            }
        }
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            dayOptions.forEach { days ->
                FilterChip(
                    selected = profile.weeklyTrainingDays == days,
                    onClick = { onProfileChange(profile.copy(weeklyTrainingDays = days)) },
                    label = { Text(language.t("$days days/wk", "每周 $days 天")) }
                )
            }
        }
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            equipmentOptions.forEach { (label, value) ->
                FilterChip(
                    selected = profile.availableEquipment == value,
                    onClick = { onProfileChange(profile.copy(availableEquipment = value)) },
                    label = { Text(label) }
                )
            }
        }
        Text(
            text = language.t(
                "Recommended split from setup: ${recommendedTemplate.localizedTitle(language)}. AI uses setup: goal, training days, equipment, phase, constraints.",
                "根据起始设置推荐分化：${recommendedTemplate.localizedTitle(language)}。AI 会读取起始设置：目标、训练天数、器械、阶段和限制。"
            ),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Button(
            onClick = { onApplyTemplate(recommendedTemplate.id) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(language.t("Use recommended split", "使用推荐分化"))
        }
        DataChipGrid(
            items = listOf(
                "QuickSetupGateCard",
                "3-tap setup gate",
                "Goal / Days / Equipment",
                "Recommended split from setup",
                "AI uses setup: goal, training days, equipment, phase, constraints",
                "目标 / 天数 / 器械"
            )
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

private fun recommendedPlanTemplateId(profile: AthleteProfile): String {
    val experience = profile.trainingExperience.lowercase(Locale.US)
    return when {
        profile.weeklyTrainingDays <= 3 || experience.contains("beginner") || experience.contains("new") -> "beginner-full-body"
        profile.weeklyTrainingDays >= 5 -> "physique-priority"
        else -> "four-day-hypertrophy"
    }
}

private fun TrainingPlanTemplate.localizedTitle(language: AppLanguage): String {
    return when (id) {
        "beginner-full-body" -> language.t("3-Day Full Body", "三分化全身基础")
        "four-day-hypertrophy" -> language.t("4-Day Upper/Lower", "四分化上下肢")
        "physique-priority" -> language.t("5-Day Physique Priority", "五分化体型优先")
        else -> title
    }
}

private fun TrainingPlanTemplate.localizedSubtitle(language: AppLanguage): String {
    return when (id) {
        "beginner-full-body" -> language.t(
            "Best when you want the simplest start: three repeatable full-body days.",
            "适合最简单地开始：每周三天，全身动作稳定重复。"
        )
        "four-day-hypertrophy" -> language.t(
            "The default recommendation for most lifters: upper/lower split with recoverable volume.",
            "多数训练者的默认推荐：上下肢分化，训练量更容易恢复。"
        )
        "physique-priority" -> language.t(
            "A bodybuilding-style split for more weekly days and weak-point emphasis.",
            "更偏健美的分化：适合更多训练日和弱项优先。"
        )
        else -> subtitle
    }
}

private fun TrainingPlanTemplate.localizedBestFor(language: AppLanguage): String {
    return when (id) {
        "beginner-full-body" -> language.t(
            "new lifters, return-to-training, 3 days/wk",
            "新手、恢复训练、每周 3 天"
        )
        "four-day-hypertrophy" -> language.t(
            "most intermediate lifters, 4 days/wk",
            "多数中级训练者、每周 4 天"
        )
        "physique-priority" -> language.t(
            "bodybuilding weak points, 5 days/wk",
            "健美弱项优先、每周 5 天"
        )
        else -> bestFor
    }
}

private fun recommendedPlanReason(profile: AthleteProfile, template: TrainingPlanTemplate, language: AppLanguage): String {
    return language.t(
        "Recommended from your ${profile.weeklyTrainingDays} training days, experience, phase, equipment, recovery, and physique goal.",
        "根据你的每周 ${profile.weeklyTrainingDays} 天训练、经验、阶段、器械、恢复和体型目标推荐。"
    ) + " " + template.localizedBestFor(language)
}

@Composable
private fun AiPlanAdjustmentProposalCard(
    proposal: AiPlanAdjustmentProposal,
    language: AppLanguage,
    onApplyTemplate: (String) -> Unit,
    onApplyToday: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(9.dp)) {
            Text(
                text = language.t("AI Plan Adjustment Proposal", "AI 计划调整提案"),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            MetricGrid(
                metrics = listOf(
                    language.t("Status", "状态") to proposal.statusLabel,
                    language.t("Template", "模板") to proposal.recommendedTemplateName,
                    language.t("Confidence", "置信度") to proposal.confidenceLabel,
                    language.t("Primary", "主动作") to proposal.primaryActionLabel
                )
            )
            Text(
                text = proposal.proposalTitle,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = language.t("Split action: ${proposal.splitAction}", "分化动作：${proposal.splitAction}"),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = language.t("Volume action: ${proposal.volumeAction}", "容量动作：${proposal.volumeAction}"),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = language.t("Exercise action: ${proposal.exerciseAction}", "动作动作：${proposal.exerciseAction}"),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            DataChipGrid(
                items = listOf(
                    proposal.nutritionGuardrail,
                    proposal.recoveryGuardrail,
                    "AI Plan Adjustment Proposal",
                    "Apply AI split template",
                    "template proposal from linked evidence"
                )
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = { onApplyTemplate(proposal.recommendedTemplateId) },
                    enabled = proposal.primaryActionLabel != "Collect evidence first",
                    modifier = Modifier.weight(1f)
                ) {
                    Text(proposal.primaryActionLabel)
                }
                ElevatedButton(onClick = onApplyToday, modifier = Modifier.weight(1f)) {
                    Text(language.t("Apply today", "应用今天"))
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalLayoutApi::class)
private fun PlanFlowCoachCard(
    state: CoachUiState,
    selectedDay: TrainingDay,
    selectedIndex: Int,
    showDetails: Boolean,
    onToggleDetails: () -> Unit,
    onApplyTemplate: (String) -> Unit,
    onApplyDay: (Int) -> Unit,
    onOpenTraining: () -> Unit
) {
    val language = state.appLanguage
    val plan = state.trainingPlan
    val profile = state.athleteProfile
    val activeDays = plan.days.count { it.exercises.isNotEmpty() }
    val plannedExercises = plan.days.sumOf { it.exercises.size }
    val weeklyHardSets = plan.days.sumOf { day -> day.exercises.sumOf { it.sets } }
    val selectedDayReady = selectedDay.exercises.isNotEmpty()
    val todayLoaded = state.dailyLog.trainingSession.exercises.isNotEmpty()
    val profileReady = profile.primaryGoal.isNotBlank() &&
        profile.currentPhase.isNotBlank() &&
        profile.availableEquipment.isNotBlank() &&
        profile.weeklyTrainingDays in 1..7
    val templates = trainingPlanTemplates()
    val recommendedTemplate = trainingPlanTemplates().firstOrNull { it.id == recommendedPlanTemplateId(profile) }
        ?: trainingPlanTemplates().first()
    val planProposal = aiPlanAdjustmentProposal(
        log = state.dailyLog,
        recentLogs = state.recentLogs,
        profile = profile,
        plan = plan
    )
    val primaryTitle: String
    val primaryDetail: String
    val primaryLabel: String
    val primaryAction: () -> Unit
    when {
        !profileReady -> {
            primaryTitle = language.t("Set physique target", "先设置体型目标")
            primaryDetail = language.t(
                "Add goal, phase, equipment, and training days so templates and AI review match your real life.",
                "补齐目标、阶段、器械和每周训练天数，让模板和 AI 复盘贴合真实情况。"
            )
            primaryLabel = language.t("Open profile", "打开档案")
            primaryAction = onToggleDetails
        }
        activeDays == 0 || plannedExercises == 0 -> {
            primaryTitle = language.t("Choose starter plan", "选择起始计划")
            primaryDetail = language.t(
                "Use ${recommendedTemplate.localizedTitle(language)} as the first ready-to-train structure, then edit details only if needed.",
                "先使用 ${recommendedTemplate.localizedTitle(language)} 作为可直接训练的结构；需要时再展开修改细节。"
            )
            primaryLabel = language.t("Use recommended template", "使用推荐模板")
            primaryAction = { onApplyTemplate(recommendedTemplate.id) }
        }
        !selectedDayReady -> {
            primaryTitle = language.t("Pick a training day", "选择训练日")
            primaryDetail = language.t(
                "The selected day has no exercises. Pick a day with movements or add exercises in the detail layer.",
                "当前训练日没有动作。选择已有动作的训练日，或在细节层添加动作。"
            )
            primaryLabel = language.t("Show plan details", "展开计划细节")
            primaryAction = onToggleDetails
        }
        !todayLoaded -> {
            primaryTitle = language.t("Apply today's plan", "应用今天计划")
            primaryDetail = language.t(
                "${selectedDay.dayName} ${selectedDay.focus}: ${selectedDay.exercises.size} exercises and ${selectedDay.exercises.sumOf { it.sets }} hard sets will become today's set log.",
                "${selectedDay.dayName} ${selectedDay.focus}：${selectedDay.exercises.size} 个动作、${selectedDay.exercises.sumOf { it.sets }} 个有效组会转换成今天训练记录。"
            )
            primaryLabel = language.t("Apply today", "应用到今天")
            primaryAction = { onApplyDay(selectedIndex) }
        }
        else -> {
            primaryTitle = language.t("Today's workout is loaded", "今天训练已载入")
            primaryDetail = language.t(
                "Open Training and follow the next set coach, rest timer, and closeout review.",
                "打开训练页，跟随下一组教练、休息倒计时和训练收尾复盘。"
            )
            primaryLabel = language.t("Open Training", "打开训练")
            primaryAction = onOpenTraining
        }
    }
    SectionCard(
        title = language.t("Plan Flow Coach", "计划流程教练"),
        subtitle = language.t(
            "Start from the right weekly structure, then apply one day into today's executable workout.",
            "先选对周训练结构，再把其中一天应用成今天可执行的训练。"
        )
    ) {
        MetricGrid(
            metrics = listOf(
                language.t("Phase", "阶段") to profile.currentPhase.ifBlank { "--" },
                language.t("Days/wk", "每周天数") to profile.weeklyTrainingDays.toString(),
                language.t("Plan", "计划") to plan.name,
                language.t("Active days", "训练日") to activeDays.toString(),
                language.t("Exercises", "动作") to plannedExercises.toString(),
                language.t("Hard sets", "有效组") to weeklyHardSets.toString(),
                language.t("Recommended split", "推荐分化") to recommendedTemplate.localizedTitle(language),
                language.t("Selected", "当前日") to selectedDay.dayName,
                language.t("Today", "今天") to if (todayLoaded) language.t("Loaded", "已载入") else language.t("Not loaded", "未载入")
            )
        )
        Text(
            text = language.t("Recommended split", "推荐分化") + ": " + recommendedTemplate.localizedTitle(language),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = recommendedPlanReason(profile, recommendedTemplate, language),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            templates.forEach { template ->
                FilterChip(
                    selected = plan.name == template.plan.name,
                    onClick = { onApplyTemplate(template.id) },
                    label = { Text(template.localizedTitle(language)) }
                )
            }
        }
        Text(
            text = recommendedTemplate.localizedSubtitle(language),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        AiPlanAdjustmentProposalCard(
            proposal = planProposal,
            language = language,
            onApplyTemplate = onApplyTemplate,
            onApplyToday = { onApplyDay(selectedIndex) }
        )
        Text(
            text = primaryTitle,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = primaryDetail,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Button(onClick = primaryAction, modifier = Modifier.fillMaxWidth()) {
            Text(primaryLabel)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            TextButton(onClick = onToggleDetails, modifier = Modifier.weight(1f)) {
                Text(if (showDetails) language.t("Hide plan details", "收起计划细节") else language.t("Show plan details", "展开计划细节"))
            }
            TextButton(
                onClick = { onApplyDay(selectedIndex) },
                enabled = selectedDayReady,
                modifier = Modifier.weight(1f)
            ) {
                Text(language.t("Apply today", "应用今天"))
            }
            TextButton(onClick = onOpenTraining, enabled = todayLoaded, modifier = Modifier.weight(1f)) {
                Text(language.t("Training", "训练"))
            }
        }
        DataChipGrid(
            items = listOf(
                language.t("Plan Flow Coach", "计划流程教练"),
                language.t("Template -> weekly plan -> Apply today", "模板 -> 周计划 -> 应用今天"),
                language.t("Visual guide IDs before gym floor", "进健身房前先看视觉图例 ID"),
                language.t("Selected day visual map", "当前训练日视觉图"),
                if (showDetails) {
                    language.t("Plan detail layers open", "计划细节层已展开")
                } else {
                    language.t("Plan detail layers hidden", "计划细节层已收起")
                }
            )
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
    onApplyTemplate: (String) -> Unit,
    onResetPlan: () -> Unit,
    onOpenTraining: () -> Unit
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
    var showPlanDetails by remember { mutableStateOf(false) }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        QuickSetupGateCard(
            profile = state.athleteProfile,
            language = state.appLanguage,
            onProfileChange = onProfileChange,
            onApplyTemplate = onApplyTemplate
        )
        PlanFlowCoachCard(
            state = state,
            selectedDay = selectedDay,
            selectedIndex = selectedIndex,
            showDetails = showPlanDetails,
            onToggleDetails = { showPlanDetails = !showPlanDetails },
            onApplyTemplate = onApplyTemplate,
            onApplyDay = onApplyDay,
            onOpenTraining = onOpenTraining
        )
        if (showPlanDetails) {
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

        ExerciseVisualMap(
            title = "Selected Day Visual Map",
            subtitle = "Scan the equipment/action thumbnails before applying this plan day.",
            emptyText = "Add planned exercises to see the visual equipment map for this day.",
            items = selectedDay.exercises.map { exercise ->
                ExerciseVisualMapItem(
                    name = exercise.name,
                    targetMuscle = exercise.targetMuscle,
                    detail = "${exercise.sets} x ${exercise.reps} | RIR ${exercise.rir?.let { formatDecimal(it) } ?: "--"} | rest ${exercise.restSeconds}s"
                )
            }
        )

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
}

@Composable
private fun PlannedExerciseCard(exercise: PlannedExercise, onRemove: () -> Unit) {
    Card(shape = RoundedCornerShape(8.dp)) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.Top) {
                ExerciseVisualHeader(
                    name = exercise.name,
                    targetMuscle = exercise.targetMuscle,
                    detail = "${exercise.targetMuscle.ifBlank { "Target muscle not set" }} | ${exercise.sets} x ${exercise.reps} | RIR ${exercise.rir?.let { formatDecimal(it) } ?: "--"} | rest ${exercise.restSeconds}s",
                    modifier = Modifier.weight(1f)
                )
                TextButton(onClick = onRemove) {
                    Text("Remove")
                }
            }
            if (exercise.notes.isNotBlank()) {
                Text(
                    text = exercise.notes,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            ExerciseVisualGuide(name = exercise.name, targetMuscle = exercise.targetMuscle)
        }
    }
}

private data class ExerciseVisualMapItem(
    val name: String,
    val targetMuscle: String,
    val detail: String
)

@Composable
private fun ExerciseVisualMap(
    title: String,
    subtitle: String,
    emptyText: String,
    items: List<ExerciseVisualMapItem>
) {
    SectionCard(title = title, subtitle = subtitle) {
        if (items.isEmpty()) {
            Text(
                text = emptyText,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        } else {
            val atlas = exerciseVisualAtlas()
            val categoryChips = items
                .map { exerciseVisualSpec(it.name, it.targetMuscle) }
                .distinctBy { it.visualId }
                .map { "${it.visualId} ${it.equipmentZh}" }
            DataChipGrid(items = categoryChips + atlas.recognitionFlow)
            items.forEachIndexed { index, item ->
                ExerciseVisualMapRow(item = item)
                if (index != items.lastIndex) {
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
private fun ExerciseVisualMapRow(item: ExerciseVisualMapItem) {
    val spec = remember(item.name, item.targetMuscle) { exerciseVisualSpec(item.name, item.targetMuscle) }
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
        Canvas(
            modifier = Modifier
                .weight(0.55f)
                .height(58.dp)
        ) {
            drawExerciseVisual(type = spec.type)
        }
        Column(modifier = Modifier.weight(1.45f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(item.name, fontWeight = FontWeight.SemiBold)
            Text(
                text = "${spec.visualId} ${spec.equipment} (${spec.equipmentZh}) | ${item.detail}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "Visual cue: ${spec.quickVisualCue}; ${spec.findEquipmentCue}; ${spec.movementPathCue}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "Beginner recognition cue: ${spec.lookFor}; ${spec.beginnerCue}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "Unified visual atlas: ${spec.recognitionSteps().joinToString(" ")}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun ExerciseVisualHeader(
    name: String,
    targetMuscle: String,
    detail: String,
    modifier: Modifier = Modifier
) {
    val spec = remember(name, targetMuscle) { exerciseVisualSpec(name, targetMuscle) }
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
        Canvas(
            modifier = Modifier
                .weight(0.36f)
                .height(54.dp)
        ) {
            drawExerciseVisual(type = spec.type)
        }
        Column(modifier = Modifier.weight(1.64f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
            Text(name, fontWeight = FontWeight.SemiBold)
            Text(
                text = "${spec.visualId} ${spec.equipmentZh} | $detail",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = spec.lookFor,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = spec.quickVisualCue,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = spec.movementPathCue,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = spec.recognitionSteps().joinToString(" "),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
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
                ExerciseVisualCueSteps(spec = spec)
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
                    text = "Beginner recognition cue: ${spec.beginnerCue}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Three-step recognition: ${spec.recognitionSteps().joinToString(" ")}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Setup steps: ${spec.setupSteps().joinToString(" ")}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Common mistakes: ${spec.commonMistakes().joinToString(", ")}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Photo recognition: ${spec.equipmentPhotoCue()}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                DataChipGrid(
                    items = listOf(
                        spec.primaryMuscle,
                        spec.setupCue,
                        spec.actionPathCue,
                        spec.lookFor,
                        "find this equipment",
                        "setup steps",
                        "common mistakes",
                        "attach equipment photo"
                    )
                )
            }
        }
    }
}

@Composable
private fun ExerciseVisualGuideLibrary() {
    val atlas = exerciseVisualAtlas()
    SectionCard(
        title = "Exercise Visual Library",
        subtitle = "Unified equipment/action instance diagrams help non-pro users recognize what an exercise name refers to before adding it. 中文图例会把动作名翻译成大概该找哪种器械。"
    ) {
        ExerciseVisualAtlasOverview()
        ExerciseVisualLegendGrid(specs = atlas.specs)
        Text(
            text = "When the app or AI sees an exercise name, it maps it to one of these shared visual IDs: equipment name, Chinese label, simple instance diagram, action path cue, beginner recognition cue, example movement, look-for cue, equipment markers, and common movements.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = "Each card uses the same three beginner steps: see the simplified instance diagram, find the matching gym equipment, then follow the intended movement path.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        val examples = atlas.specs
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
private fun ExerciseVisualLegendGrid(specs: List<ExerciseVisualSpec>) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Exercise Visual Legend / 统一动作图例", fontWeight = FontWeight.SemiBold)
        Text(
            text = "A compact visual atlas: first memorize the simple instance diagram, then match the real gym markers and movement path.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        specs.chunked(2).forEach { rowSpecs ->
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                rowSpecs.forEach { spec ->
                    ExerciseVisualLegendTile(
                        spec = spec,
                        modifier = Modifier.weight(1f)
                    )
                }
                if (rowSpecs.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun ExerciseVisualLegendTile(spec: ExerciseVisualSpec, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(modifier = Modifier.padding(8.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp)
            ) {
                drawExerciseVisual(type = spec.type)
            }
            Text(
                text = "${spec.visualId} ${spec.equipmentZh}",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = spec.equipment,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = spec.quickVisualCue,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = spec.movementPathCue,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun ExerciseVisualAtlasOverview() {
    val atlas = exerciseVisualAtlas()
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(atlas.title, fontWeight = FontWeight.SemiBold)
            Text(
                text = atlas.summary,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            DataChipGrid(items = atlas.recognitionFlow)
            DataChipGrid(items = atlas.specs.map { "${it.visualId} ${it.equipmentZh}" })
        }
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
                ExerciseVisualCueSteps(spec = spec)
                Text(
                    text = "Common movement examples: ${spec.commonMovements.joinToString(", ")}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = spec.lookFor,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = spec.recognitionSteps().joinToString(" "),
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
                text = "Quick visual cue: ${spec.quickVisualCue}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "Find equipment cue: ${spec.findEquipmentCue}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "Movement path cue: ${spec.movementPathCue}",
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
                text = "Three-step recognition: ${spec.recognitionSteps().joinToString(" ")}",
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

@Composable
private fun ExerciseVisualCueSteps(spec: ExerciseVisualSpec) {
    Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
        Text(
            text = "Quick visual cue: ${spec.quickVisualCue}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = "Find equipment cue: ${spec.findEquipmentCue}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = "Movement path cue: ${spec.movementPathCue}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
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
private fun TodayReadinessGateCard(
    log: DailyLog,
    readinessBuilder: TrainingReadinessBuilder,
    nextSet: NextSetCoach,
    restPrescription: AiRestPrescription,
    language: AppLanguage
) {
    val pacing = log.nutritionPacing()
    val decision = when (readinessBuilder.statusLabel) {
        "Ready to execute" -> language.t("Push carefully", "谨慎推进")
        "Hold before pushing" -> language.t("Hold today", "今天保持")
        "Reduce session stress" -> language.t("Reduce volume", "降低容量")
        else -> language.t("Recover first", "先恢复")
    }
    val fuelGate = when {
        pacing.carbsRemaining > 60 -> language.t("Carbs available", "碳水可用")
        pacing.proteinRemaining > 30 -> language.t("Protein behind", "蛋白质落后")
        pacing.caloriesRemaining < -150 -> language.t("Over target", "热量已超")
        log.meals.isEmpty() -> language.t("Log food first", "先记录饮食")
        else -> language.t("Fuel acceptable", "供能可接受")
    }
    val rirGate = nextSet.plannedRir?.let { "RIR ${formatDecimal(it)}" } ?: language.t("Log RIR", "记录 RIR")
    val restGate = if (restPrescription.recommendedRestSeconds > 0) {
        "${restPrescription.recommendedRestSeconds}s"
    } else {
        "--"
    }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, IfbbProGlassBorder),
        color = IfbbProGlassStrongSurface
    ) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(9.dp)) {
            Text(
                text = language.t("Today Readiness Gate", "今日准备闸门"),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = language.t(
                    "Push / Hold / Reduce / Recover: decide before the first hard set, then let AI compare the result after training.",
                    "推进 / 保持 / 减量 / 恢复：先在第一组正式组前做决定，训练后再让 AI 对照结果。"
                ),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            MetricGrid(
                metrics = listOf(
                    language.t("Training decision", "训练决策") to decision,
                    language.t("Fuel gate", "供能闸门") to fuelGate,
                    language.t("RIR gate", "RIR 闸门") to rirGate,
                    language.t("Rest gate", "休息闸门") to restGate
                )
            )
            Text(
                text = readinessBuilder.recoveryGate,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            DataChipGrid(
                items = listOf(
                    language.t("today readiness gate", "今日准备闸门"),
                    language.t("push hold reduce recover", "推进 保持 减量 恢复"),
                    language.t("Sleep + HR + soreness + RIR", "睡眠 + 心率 + 酸痛 + RIR"),
                    language.t("warm-up decides first working set", "热身决定第一组正式组"),
                    language.t("fuel before load progression", "加重量前先看供能")
                )
            )
        }
    }
}

@Composable
private fun WorkoutFlowCoachCard(
    log: DailyLog,
    readinessBuilder: TrainingReadinessBuilder,
    rampPlan: WarmUpRampPlan,
    nextSet: NextSetCoach,
    restPrescription: AiRestPrescription,
    qualityDashboard: SessionQualityDashboard,
    closeoutCoach: TrainingCloseoutCoach,
    language: AppLanguage,
    isLoading: Boolean,
    onOpenPlan: () -> Unit,
    onMarkTrainingComplete: () -> Unit,
    onRunDailyReview: () -> Unit,
    onOpenNutrition: () -> Unit,
    onOpenMetrics: () -> Unit,
    onOpenAi: () -> Unit,
    onPickTrainingPhoto: (PhotoEvidenceType, String) -> Unit
) {
    val totalSets = log.plannedHardSets()
    val completedSets = log.completedHardSets()
    val remainingSets = (totalSets - completedSets).coerceAtLeast(0)
    val hasWorkout = log.trainingSession.exercises.isNotEmpty() && totalSets > 0
    val allSetsComplete = hasWorkout && completedSets >= totalSets
    val sessionMarkedComplete = log.trainingSession.completed
    val stepLabel = when {
        !hasWorkout -> language.t("Load workout", "载入训练")
        completedSets == 0 -> language.t("Warm up", "热身")
        !allSetsComplete -> language.t("Execute sets", "执行组数")
        !sessionMarkedComplete -> language.t("Close out", "训练收尾")
        else -> language.t("Review", "复盘")
    }
    val nextStepTitle = when {
        !hasWorkout -> language.t("Apply a plan day or add today's first movement.", "应用训练日，或添加今天第一个动作。")
        completedSets == 0 -> language.t("Warm up, then enter the first working set.", "先热身，再进入第一组正式组。")
        !allSetsComplete && nextSet.hasActiveSet -> language.t(
            "Log ${nextSet.currentExerciseName} set ${nextSet.setNumber}/${nextSet.totalSets}.",
            "记录 ${nextSet.currentExerciseName} 第 ${nextSet.setNumber}/${nextSet.totalSets} 组。"
        )
        !allSetsComplete -> language.t("Finish the remaining working sets.", "完成剩余正式组。")
        !sessionMarkedComplete -> language.t("Mark training complete and check closeout.", "标记训练完成，并检查收尾清单。")
        else -> language.t("Run AI review after food and metrics are ready.", "饮食和身体数据就绪后运行 AI 复盘。")
    }
    val nextStepDetail = when {
        !hasWorkout -> language.t(
            "Use the training plan layer first so every exercise has target reps, load, RIR, rest time, and a visual guide.",
            "先在训练区的计划层载入训练，让每个动作都有目标次数、重量、RIR、休息时间和动作图例。"
        )
        completedSets == 0 -> language.t(
            "Follow the Warm-up Ramp Plan, then use the active exercise cards below to log kg, reps, RIR, notes, and Complete.",
            "按热身递增计划执行，然后在下方动作卡记录重量、次数、RIR、备注，并点击完成。"
        )
        !allSetsComplete -> language.t(
            "$remainingSets working set(s) remain. Fill actual reps, kg, and RIR before tapping Complete so the rest timer and AI review have clean evidence.",
            "还剩 $remainingSets 个正式组。点击完成前先填实际次数、重量和 RIR，这样休息倒计时和 AI 复盘才有干净证据。"
        )
        !sessionMarkedComplete -> language.t(
            "All planned sets are done. Add session notes, photos if needed, and mark the workout complete.",
            "计划组数已完成。补充训练备注，必要时添加照片，然后标记训练完成。"
        )
        else -> language.t(
            "Use closeout, post-workout nutrition, metrics, and photos as the evidence package for AI adjustment.",
            "把训练收尾、训练后饮食、身体数据和照片作为 AI 调整的证据包。"
        )
    }
    val primaryLabel = when {
        !hasWorkout -> language.t("Open training plan", "打开训练计划")
        allSetsComplete && !sessionMarkedComplete -> language.t("Mark complete", "标记完成")
        sessionMarkedComplete -> language.t("Run review", "运行复盘")
        else -> language.t("Form check", "动作检查")
    }
    val primaryAction: () -> Unit = when {
        !hasWorkout -> onOpenPlan
        allSetsComplete && !sessionMarkedComplete -> onMarkTrainingComplete
        sessionMarkedComplete -> onRunDailyReview
        else -> {
            {
                onPickTrainingPhoto(
                    PhotoEvidenceType.TRAINING_FORM,
                    "Workout Flow Coach form check for current set, target-muscle stimulus, technique, pain flags, and set-log context."
                )
            }
        }
    }

    SectionCard(
        title = language.t("Workout Flow Coach", "训练执行教练"),
        subtitle = language.t(
            "The first-screen path: load plan, warm up, log the next set, rest, close out, then run AI review.",
            "首屏训练路径：载入计划、热身、记录下一组、休息、收尾，然后运行 AI 复盘。"
        )
    ) {
        TodayReadinessGateCard(
            log = log,
            readinessBuilder = readinessBuilder,
            nextSet = nextSet,
            restPrescription = restPrescription,
            language = language
        )
        MetricGrid(
            metrics = listOf(
                language.t("Step", "步骤") to stepLabel,
                language.t("Sets", "组数") to "$completedSets/$totalSets",
                language.t("Next set", "下一组") to if (nextSet.hasActiveSet) {
                    "${nextSet.currentExerciseName} ${nextSet.setNumber}/${nextSet.totalSets}"
                } else {
                    "--"
                },
                language.t("AI rest", "AI 休息") to if (restPrescription.recommendedRestSeconds > 0) {
                    "${restPrescription.recommendedRestSeconds}s"
                } else {
                    "--"
                },
                language.t("Readiness", "状态") to readinessBuilder.readinessScore.toString(),
                language.t("Quality", "质量") to qualityDashboard.qualityScore.toString(),
                language.t("Closeout", "收尾") to closeoutCoach.closeoutScore.toString()
            )
        )
        Text(nextStepTitle, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
        Text(
            text = nextStepDetail,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        DataChipGrid(
            items = listOf(
                language.t("Workout Flow Coach", "训练执行教练"),
                language.t("Warm up -> ramp -> next set -> log -> rest -> closeout -> AI review", "热身 -> 递增 -> 下一组 -> 记录 -> 休息 -> 收尾 -> AI 复盘"),
                language.t("Set rows below are the source of truth", "下方组记录才是真实数据来源"),
                language.t("Rest timer starts after Complete", "点击完成后启动休息倒计时"),
                language.t("AI Rest Prescription", "AI 休息处方"),
                "${restPrescription.statusLabel}: ${restPrescription.recommendedRestSeconds}s",
                rampPlan.visualSpec.visualId
            )
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = primaryAction,
                enabled = !isLoading,
                modifier = Modifier.weight(1f)
            ) {
                Text(primaryLabel)
            }
            ElevatedButton(
                onClick = {
                    onPickTrainingPhoto(
                        PhotoEvidenceType.EQUIPMENT,
                        "Workout Flow Coach equipment photo for visual guide ID mapping, setup recognition, and substitution context."
                    )
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(language.t("Equipment", "器械照片"))
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            TextButton(onClick = onOpenNutrition, modifier = Modifier.weight(1f)) {
                Text(language.t("Food", "饮食"))
            }
            TextButton(onClick = onOpenMetrics, modifier = Modifier.weight(1f)) {
                Text(language.t("Metrics", "数据"))
            }
            TextButton(onClick = onOpenAi, modifier = Modifier.weight(1f)) {
                Text(language.t("AI", "AI"))
            }
        }
    }
}

@Composable
private fun TrainingAutopilotCard(
    log: DailyLog,
    readinessBuilder: TrainingReadinessBuilder,
    nextSet: NextSetCoach,
    restPrescription: AiRestPrescription,
    qualityDashboard: SessionQualityDashboard,
    closeoutCoach: TrainingCloseoutCoach,
    language: AppLanguage,
    isLoading: Boolean,
    onOpenPlan: () -> Unit,
    onMarkTrainingComplete: () -> Unit,
    onRunDailyReview: () -> Unit,
    onPickTrainingPhoto: (PhotoEvidenceType, String) -> Unit
) {
    val totalSets = log.plannedHardSets()
    val completedSets = log.completedHardSets()
    val hasWorkout = log.trainingSession.exercises.isNotEmpty() && totalSets > 0
    val allSetsComplete = hasWorkout && completedSets >= totalSets
    val sessionMarkedComplete = log.trainingSession.completed
    val currentStep = when {
        !hasWorkout -> language.t("Plan", "计划")
        completedSets == 0 -> language.t("Warm up", "热身")
        !allSetsComplete -> language.t("Work sets", "正式组")
        !sessionMarkedComplete -> language.t("Closeout", "收尾")
        else -> language.t("Review", "复盘")
    }
    val nextSetTarget = if (nextSet.hasActiveSet) {
        val load = nextSet.plannedLoadKg?.let { "${formatDecimal(it)} kg" } ?: language.t("planned load", "计划重量")
        "${nextSet.currentExerciseName} ${nextSet.setNumber}/${nextSet.totalSets} | $load | ${nextSet.targetReps} | RIR ${nextSet.plannedRir?.let { formatDecimal(it) } ?: "--"}"
    } else {
        language.t("No active set", "暂无当前组")
    }
    val restLabel = if (restPrescription.recommendedRestSeconds > 0) {
        "${restPrescription.recommendedRestSeconds}s (${restPrescription.statusLabel})"
    } else {
        language.t("After a set is logged", "记录一组后生成")
    }
    val nextTrainingAction: String
    val actionLabel: String
    val action: () -> Unit
    when {
        !hasWorkout -> {
            nextTrainingAction = language.t("Apply today's plan first", "先应用今天的训练计划")
            actionLabel = language.t("Open training plan", "打开训练计划")
            action = onOpenPlan
        }
        !allSetsComplete -> {
            nextTrainingAction = language.t("Log the next working set, then start rest", "记录下一组并开始休息")
            actionLabel = language.t("Form check", "动作检查")
            action = {
                onPickTrainingPhoto(
                    PhotoEvidenceType.TRAINING_FORM,
                    "Training Autopilot form check for current working set, target-muscle stimulus, pain flags, and rest prescription context."
                )
            }
        }
        !sessionMarkedComplete -> {
            nextTrainingAction = language.t("Mark workout complete and close out", "标记训练完成并收尾")
            actionLabel = language.t("Mark complete", "标记完成")
            action = onMarkTrainingComplete
        }
        else -> {
            nextTrainingAction = language.t("Run AI review after food and metrics", "饮食和身体数据就绪后运行 AI 复盘")
            actionLabel = language.t("Run review", "运行复盘")
            action = onRunDailyReview
        }
    }
    SectionCard(
        title = language.t("Training Autopilot", "训练自动教练"),
        subtitle = language.t(
            "One-screen training loop: follow the planned set, log evidence, rest correctly, then close out for AI.",
            "一屏训练闭环：跟随计划组、记录证据、按处方休息，然后收尾交给 AI。"
        )
    ) {
        Text(
            text = language.t("TRAINING AUTOPILOT", "训练自动教练"),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
        MetricGrid(
            metrics = listOf(
                language.t("Loop", "闭环") to language.t("Plan -> Warm up -> Work sets -> Rest -> Closeout", "计划 -> 热身 -> 正式组 -> 休息 -> 收尾"),
                language.t("Current training step", "当前训练步骤") to currentStep,
                language.t("Next set target", "下一组目标") to nextSetTarget,
                language.t("Rest prescription", "休息处方") to restLabel
            )
        )
        LinearProgressIndicator(
            progress = { if (totalSets > 0) completedSets.toFloat() / totalSets.toFloat() else 0f },
            modifier = Modifier.fillMaxWidth()
        )
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
            color = IfbbProGlassStrongSurface
        ) {
            Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(language.t("Next training action", "下一步训练动作"), fontWeight = FontWeight.SemiBold)
                Text(nextTrainingAction, fontWeight = FontWeight.SemiBold)
                Text(
                    text = language.t(
                        "AI updates the next workout only after set logs, rest quality, readiness, pain flags, and closeout evidence are linked.",
                        "AI 只会在组记录、休息质量、准备状态、疼痛信号和收尾证据联动后，更新下一次训练。"
                    ),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Button(onClick = action, enabled = !isLoading, modifier = Modifier.fillMaxWidth()) {
                    Text(actionLabel)
                }
            }
        }
        DataChipGrid(
            items = listOf(
                language.t("Plan", "计划"),
                language.t("Warm up", "热身"),
                language.t("Work sets", "正式组"),
                language.t("Rest", "休息"),
                language.t("Closeout", "收尾")
            )
        )
        MetricGrid(
            metrics = listOf(
                language.t("Sets", "组数") to "$completedSets/$totalSets",
                language.t("Readiness", "准备度") to readinessBuilder.readinessScore.toString(),
                language.t("Quality", "质量") to qualityDashboard.qualityScore.toString(),
                language.t("Closeout", "收尾") to closeoutCoach.closeoutScore.toString()
            )
        )
    }
}

@Composable
private fun LiveSetCoachCard(
    log: DailyLog,
    nextSet: NextSetCoach,
    restPrescription: AiRestPrescription,
    language: AppLanguage,
    isLoading: Boolean,
    onOpenPlan: () -> Unit,
    onUpdateSetEntry: (Int, Int, Int?, Double?, Double?, String) -> Unit,
    onCompleteSet: (Int, Int) -> Unit,
    onPickTrainingPhoto: (PhotoEvidenceType, String) -> Unit
) {
    val exerciseIndex = log.trainingSession.exercises.indexOfFirst { exercise ->
        exercise.trackedSets().any { set -> !set.completed }
    }
    val exercise = log.trainingSession.exercises.getOrNull(exerciseIndex)
    val setIndex = exercise?.trackedSets()?.indexOfFirst { set -> !set.completed } ?: -1
    val set = exercise?.trackedSets()?.getOrNull(setIndex)

    if (exercise == null || set == null || !nextSet.hasActiveSet) {
        SectionCard(
            title = language.t("Live Set Coach", "当前组教练"),
            subtitle = language.t(
                "Load today's workout first, then this card becomes the one-tap set executor.",
                "先载入今天的训练；之后这里会变成一键执行当前组的入口。"
            )
        ) {
            Text(
                text = language.t("LIVE SET COACH", "当前组教练"),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = language.t("No active set loaded.", "当前没有可执行训练组。"),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
            )
            Button(onClick = onOpenPlan, modifier = Modifier.fillMaxWidth()) {
                Text(language.t("Open training plan", "打开训练计划"))
            }
        }
        return
    }

    var reps by remember(exerciseIndex, setIndex, set.completed) {
        mutableStateOf(set.actualReps?.toString().orEmpty())
    }
    var load by remember(exerciseIndex, setIndex, set.completed) {
        mutableStateOf(set.loadKg?.let { formatDecimal(it) }.orEmpty())
    }
    var rir by remember(exerciseIndex, setIndex, set.completed) {
        mutableStateOf(set.rir?.let { formatDecimal(it) }.orEmpty())
    }
    var notes by remember(exerciseIndex, setIndex, set.completed) {
        mutableStateOf(set.notes)
    }
    val plannedLoad = set.loadKg ?: exercise.loadKg
    val plannedRir = set.rir ?: exercise.rir
    val targetLine = listOfNotNull(
        set.targetReps.ifBlank { exercise.reps }.ifBlank { null },
        plannedLoad?.let { "${formatDecimal(it)} kg" },
        plannedRir?.let { "RIR ${formatDecimal(it)}" }
    ).joinToString(" | ").ifBlank { language.t("Use planned target", "使用计划目标") }

    SectionCard(
        title = language.t("Live Set Coach", "当前组教练"),
        subtitle = language.t(
            "Stand at the machine, do this set, then tap once to start the AI matched rest countdown.",
            "站到器械前，完成这一组，然后点一次即可开始 AI 匹配休息倒计时。"
        )
    ) {
        Text(
            text = language.t("LIVE SET COACH", "当前组教练"),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = language.t("Do this set now", "现在完成这一组"),
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "${exercise.name} ${set.setNumber}/${nextSet.totalSets}",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        MetricGrid(
            metrics = listOf(
                language.t("Target", "目标") to targetLine,
                language.t("AI matched rest", "AI 匹配休息") to "${restPrescription.recommendedRestSeconds.coerceAtLeast(set.restSeconds)}s",
                language.t("Visual guide", "动作图例") to nextSet.visualSpec.visualId,
                language.t("Status", "状态") to nextSet.statusLabel
            )
        )
        Text(
            text = nextSet.executionCue,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            NumberField(
                value = reps,
                onChange = {
                    reps = it
                    onUpdateSetEntry(exerciseIndex, setIndex, it.toIntOrNull(), load.toDoubleOrNull(), rir.toDoubleOrNull(), notes)
                },
                label = language.t("Reps", "次数"),
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
        Button(
            onClick = {
                onUpdateSetEntry(exerciseIndex, setIndex, reps.toIntOrNull(), load.toDoubleOrNull(), rir.toDoubleOrNull(), notes)
                onCompleteSet(exerciseIndex, setIndex)
            },
            enabled = !isLoading && !set.completed,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(language.t("Complete this set + start AI rest", "完成本组并开始 AI 休息"))
        }
        DataChipGrid(
            items = listOf(
                language.t("Auto-start rest timer", "自动启动休息倒计时"),
                language.t("AI matched rest", "AI 匹配休息"),
                language.t("Complete set logs", "补全组记录"),
                language.t("After-set logging cue", "组后记录提示")
            )
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            TextButton(
                onClick = {
                    onPickTrainingPhoto(
                        PhotoEvidenceType.TRAINING_FORM,
                        "Live Set Coach form check for ${exercise.name} set ${set.setNumber}, load, reps, RIR, pain, and technique."
                    )
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(language.t("Form photo", "动作照片"))
            }
            TextButton(
                onClick = {
                    onPickTrainingPhoto(
                        PhotoEvidenceType.EQUIPMENT,
                        "Live Set Coach equipment photo for ${exercise.name}, visual guide ${nextSet.visualSpec.visualId}, and setup confirmation."
                    )
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(language.t("Equipment", "器械"))
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
    onCompleteSet: (Int, Int) -> Unit,
    onRunDailyReview: () -> Unit,
    onOpenPlan: () -> Unit,
    onOpenTraining: () -> Unit,
    onOpenNutrition: () -> Unit,
    onOpenMetrics: () -> Unit,
    onOpenAi: () -> Unit,
    onPickTrainingPhoto: (PhotoEvidenceType, String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var muscle by remember { mutableStateOf("") }
    var sets by remember { mutableStateOf("3") }
    var reps by remember { mutableStateOf("8-12") }
    var load by remember { mutableStateOf("") }
    var rir by remember { mutableStateOf("2") }
    var rest by remember { mutableStateOf("120") }
    var notes by remember { mutableStateOf("") }
    var showProfessionalDetails by remember { mutableStateOf(false) }
    val language = state.appLanguage
    val session = state.dailyLog.trainingSession
    val recovery = recoveryGuidance(state.dailyLog, state.recentLogs)
    val readinessBuilder = trainingReadinessBuilder(state.dailyLog, recovery)
    val nextSet = nextSetCoach(state.dailyLog)
    val restPrescription = aiRestPrescription(state.dailyLog, state.recentLogs, nextSet, recovery)
    val rampPlan = warmUpRampPlan(state.dailyLog, readinessBuilder, nextSet)
    val qualityDashboard = sessionQualityDashboard(state.dailyLog)
    val closeoutCoach = trainingCloseoutCoach(
        log = state.dailyLog,
        hasAiReviewToday = state.reviewHistory.any { it.logDate == state.dailyLog.date }
    )

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        LiveSetCoachCard(
            log = state.dailyLog,
            nextSet = nextSet,
            restPrescription = restPrescription,
            language = language,
            isLoading = state.isLoading,
            onOpenPlan = onOpenPlan,
            onUpdateSetEntry = onUpdateSetEntry,
            onCompleteSet = onCompleteSet,
            onPickTrainingPhoto = onPickTrainingPhoto
        )
        TrainingAutopilotCard(
            log = state.dailyLog,
            readinessBuilder = readinessBuilder,
            nextSet = nextSet,
            restPrescription = restPrescription,
            qualityDashboard = qualityDashboard,
            closeoutCoach = closeoutCoach,
            language = language,
            isLoading = state.isLoading,
            onOpenPlan = onOpenPlan,
            onMarkTrainingComplete = onCompletedChange,
            onRunDailyReview = onRunDailyReview,
            onPickTrainingPhoto = onPickTrainingPhoto
        )
        WorkoutFlowCoachCard(
            log = state.dailyLog,
            readinessBuilder = readinessBuilder,
            rampPlan = rampPlan,
            nextSet = nextSet,
            restPrescription = restPrescription,
            qualityDashboard = qualityDashboard,
            closeoutCoach = closeoutCoach,
            language = language,
            isLoading = state.isLoading,
            onOpenPlan = onOpenPlan,
            onMarkTrainingComplete = onCompletedChange,
            onRunDailyReview = onRunDailyReview,
            onOpenNutrition = onOpenNutrition,
            onOpenMetrics = onOpenMetrics,
            onOpenAi = onOpenAi,
            onPickTrainingPhoto = onPickTrainingPhoto
        )
        SectionCard(
            title = language.t("Professional detail layers", "专业细节层"),
            subtitle = language.t(
                "Readiness, ramp plan, next-set cues, quality, and closeout stay available without crowding the first action.",
                "状态、热身递增、下一组提示、质量和收尾仍然保留，但不会挤占首屏动作。"
            )
        ) {
            Text(
                text = language.t(
                    "Open this when you want to understand why the app chose the next action.",
                    "想知道 App 为什么推荐当前下一步时，再展开这里。"
                ),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            TextButton(onClick = { showProfessionalDetails = !showProfessionalDetails }) {
                Text(
                    if (showProfessionalDetails) {
                        language.t("Hide professional details", "收起专业细节")
                    } else {
                        language.t("Show professional details", "展开专业细节")
                    }
                )
            }
        }
        if (showProfessionalDetails) {
            TrainingReadinessBuilderCard(builder = readinessBuilder, language = language)
            WarmUpRampPlanCard(plan = rampPlan, language = language)
            NextSetCoachCard(coach = nextSet, language = language)
            AiRestPrescriptionCard(prescription = restPrescription, language = language)
        }
        SectionCard(
            title = language.t("Training Execution", "训练记录"),
            subtitle = language.t(
                "Log every working set, hard sets, rest time, and effort so AI can compare performance, pain, and recovery.",
                "记录每个正式组的重量、次数、RIR、休息和感受，让 AI 能比较表现、疼痛和恢复。"
            )
        ) {
            MetricGrid(
                metrics = listOf(
                    language.t("Exercises", "动作") to session.exercises.size.toString(),
                    language.t("Completed sets", "已完成组数") to "${state.dailyLog.completedHardSets()}/${state.dailyLog.plannedHardSets()}",
                    language.t("Tonnage", "总训练量") to "${formatDecimal(state.dailyLog.trainingVolumeKg())} kg"
                )
            )
            OutlinedTextField(
                value = session.plannedFocus,
                onValueChange = onFocusChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text(language.t("Planned focus", "今日训练重点")) }
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = session.completed, onCheckedChange = { onCompletedChange() })
                Text(language.t("Training completed", "训练已完成"))
            }
            OutlinedTextField(
                value = session.sessionNotes,
                onValueChange = onNotesChange,
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(
                        language.t(
                            "Session notes: pump, stimulus, pain, technique, energy",
                            "训练备注：充血、目标肌肉感受、疼痛、技术、精力"
                        )
                    )
                },
                minLines = 2
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ElevatedButton(
                    onClick = {
                        onPickTrainingPhoto(
                            PhotoEvidenceType.TRAINING_FORM,
                            "Exercise form frame for technique, target-muscle stimulus, pain flags, and set-log context."
                        )
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(language.t("Form photo", "动作照片"))
                }
                ElevatedButton(
                    onClick = {
                        onPickTrainingPhoto(
                            PhotoEvidenceType.EQUIPMENT,
                            "Equipment photo for visual guide ID mapping and safe setup recognition."
                        )
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(language.t("Equipment photo", "器械照片"))
                }
            }
        }

        if (session.exercises.isEmpty()) {
            EmptyState(language.t("No exercises yet. Add your first movement to start set-level tracking.", "还没有动作。先添加第一个动作，开始按组记录。"))
        } else {
            session.exercises.forEachIndexed { exerciseIndex, exercise ->
                ExerciseExecutionCard(
                    exerciseIndex = exerciseIndex,
                    exercise = exercise,
                    currentLog = state.dailyLog,
                    recentLogs = state.recentLogs,
                    profile = state.athleteProfile,
                    language = language,
                    onRemove = { onRemoveExercise(exerciseIndex) },
                    onUpdateSetEntry = onUpdateSetEntry,
                    onCompleteSet = onCompleteSet
                )
            }
        }

        SessionQualityDashboardCard(dashboard = qualityDashboard, language = language)
        TrainingCloseoutCoachCard(
            coach = closeoutCoach,
            language = language,
            isLoading = state.isLoading,
            onRunDailyReview = onRunDailyReview,
            onOpenPlan = onOpenPlan,
            onOpenTraining = onOpenTraining,
            onOpenNutrition = onOpenNutrition,
            onOpenMetrics = onOpenMetrics,
            onOpenAi = onOpenAi,
            onPickTrainingPhoto = onPickTrainingPhoto
        )

        ExerciseVisualMap(
            title = language.t("Today's Exercise Visual Map", "今日动作图例"),
            subtitle = language.t(
                "A quick equipment/action index for the active workout, so exercise names map to real gym setup.",
                "把今天的动作名映射到真实器械和动作路径，新手先看图再找器械。"
            ),
            emptyText = language.t(
                "Add or apply exercises to see today's equipment/action thumbnails.",
                "添加或应用训练动作后，这里会显示今天的器械/动作缩略图。"
            ),
            items = session.exercises.map { exercise ->
                ExerciseVisualMapItem(
                    name = exercise.name,
                    targetMuscle = exercise.targetMuscle,
                    detail = language.t(
                        "${exercise.sets} x ${exercise.reps} | done ${exercise.completedSetCount()}/${exercise.trackedSets().size}",
                        "${exercise.sets} x ${exercise.reps} | 已完成 ${exercise.completedSetCount()}/${exercise.trackedSets().size}"
                    )
                )
            }
        )

        SectionCard(
            title = language.t("Add Exercise", "添加动作"),
            subtitle = language.t(
                "Create planned set rows first; the app adds a simple equipment/action visual so the movement is easier to recognize.",
                "先创建计划组；App 会同时生成器械/动作图例，让动作更容易识别。"
            )
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(language.t("Exercise", "动作名称")) },
                singleLine = true
            )
            OutlinedTextField(
                value = muscle,
                onValueChange = { muscle = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(language.t("Target muscle", "目标肌肉")) },
                singleLine = true
            )
            ExerciseVisualRecognitionPreview(name = name, targetMuscle = muscle)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                NumberField(value = sets, onChange = { sets = it }, label = language.t("Sets", "组数"), modifier = Modifier.weight(1f))
                OutlinedTextField(
                    value = reps,
                    onValueChange = { reps = it },
                    modifier = Modifier.weight(1f),
                    label = { Text(language.t("Target reps", "目标次数")) },
                    singleLine = true
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                DecimalField(value = load, onChange = { load = it }, label = language.t("Load kg", "重量 kg"), modifier = Modifier.weight(1f))
                DecimalField(value = rir, onChange = { rir = it }, label = "RIR", modifier = Modifier.weight(1f))
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                NumberField(value = rest, onChange = { rest = it }, label = language.t("Rest sec", "休息秒数"), modifier = Modifier.weight(1f))
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
                    Text(language.t("Add", "添加"))
                }
            }
            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(language.t("Technique, stimulus, substitutions, pain", "技术、目标肌肉感受、替代动作、疼痛")) },
                minLines = 2
            )
        }
    }
}

@Composable
private fun NextSetCoachCard(coach: NextSetCoach, language: AppLanguage) {
    SectionCard(
        title = "Next Set Coach",
        subtitle = "Next set target, simple equipment/action diagram, and the decision cues to execute safely."
    ) {
        MetricGrid(
            metrics = listOf(
                "Status" to coach.statusLabel,
                "Current exercise" to coach.currentExerciseName.ifBlank { "--" },
                "Next set target" to if (coach.hasActiveSet) "${coach.setNumber}/${coach.totalSets} | ${coach.targetReps.ifBlank { "--" }}" else "--",
                "Load cue" to (coach.plannedLoadKg?.let { "${formatDecimal(it)} kg" } ?: "bodyweight/plan"),
                "RIR cue" to (coach.plannedRir?.let { formatDecimal(it) } ?: "--"),
                "Rest" to if (coach.restSeconds > 0) "${coach.restSeconds}s" else "--"
            )
        )
        if (coach.currentExerciseName.isNotBlank()) {
            NextSetVisualGuide(spec = coach.visualSpec)
        }
        Text(
            text = coach.primaryAction,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = "Load/Reps/RIR: ${coach.loadCue} ${coach.repsCue} ${coach.rirCue}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = "Rest + execution: ${coach.restCue} ${coach.executionCue}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = "Stop + log: ${coach.stopCue} ${coach.afterSetLoggingCue}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        DataChipGrid(
            items = listOf(
                coach.visualSpec.visualId,
                coach.visualSpec.equipmentZh,
                coach.visualSpec.equipment,
                coach.visualSpec.quickVisualCue,
                coach.visualSpec.findEquipmentCue,
                coach.visualSpec.movementPathCue,
                "Look-for cue: ${coach.visualSpec.lookFor}"
            )
        )
    }
}

@Composable
private fun AiRestPrescriptionCard(prescription: AiRestPrescription, language: AppLanguage) {
    SectionCard(
        title = language.t("AI Rest Prescription", "AI 休息处方"),
        subtitle = language.t(
            "The rest countdown is matched from set performance, exercise type, recovery, sleep, and review evidence.",
            "休息倒计时会根据本组表现、动作类型、恢复、睡眠和复盘证据自动匹配。"
        )
    ) {
        MetricGrid(
            metrics = listOf(
                language.t("Status", "状态") to prescription.statusLabel,
                language.t("Recommended", "推荐") to if (prescription.recommendedRestSeconds > 0) {
                    "${prescription.recommendedRestSeconds}s"
                } else {
                    "--"
                },
                language.t("Base", "基础") to if (prescription.baseRestSeconds > 0) "${prescription.baseRestSeconds}s" else "--",
                language.t("Adjust", "调整") to "${prescription.adjustmentSeconds}s",
                language.t("Range", "范围") to if (prescription.maxRestSeconds > 0) {
                    "${prescription.minRestSeconds}-${prescription.maxRestSeconds}s"
                } else {
                    "--"
                }
            )
        )
        Text(
            text = language.t("Timer cue: ${prescription.timerCue}", "倒计时提示：${prescription.timerCue}"),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = language.t("Next session: ${prescription.nextSessionCarryover}", "下次训练：${prescription.nextSessionCarryover}"),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        DataChipGrid(
            items = listOf(
                language.t("AI Rest Prescription", "AI 休息处方"),
                language.t("restSeconds auto update", "restSeconds 自动更新"),
                language.t("after AI review", "AI 复盘后"),
                language.t("sleep + RIR + HR + soreness", "睡眠 + RIR + 心率 + 酸痛")
            ) + prescription.evidence.take(4)
        )
    }
}

@Composable
private fun WarmUpRampPlanCard(plan: WarmUpRampPlan, language: AppLanguage) {
    SectionCard(
        title = "Warm-up Ramp Plan",
        subtitle = "Turn readiness and the next set target into exact warm-up and ramp-up sets before the first working set."
    ) {
        MetricGrid(
            metrics = listOf(
                "Status" to plan.statusLabel,
                "Exercise" to plan.currentExerciseName.ifBlank { "--" },
                "Target reps" to plan.targetReps.ifBlank { "--" },
                "Work load" to (plan.plannedLoadKg?.let { "${formatDecimal(it)} kg" } ?: "bodyweight/plan"),
                "Work RIR" to (plan.plannedRir?.let { formatDecimal(it) } ?: "--"),
                "Visual guide" to plan.visualSpec.visualId
            )
        )
        if (plan.currentExerciseName.isNotBlank()) {
            NextSetVisualGuide(spec = plan.visualSpec)
        }
        Text(
            text = "Readiness gate: ${plan.readinessGate}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = "Ramp strategy: ${plan.rampStrategy}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        if (plan.rampSets.isEmpty()) {
            EmptyState("Apply a plan day or add an exercise to generate the warm-up ramp set checklist.")
        } else {
            plan.rampSets.forEach { rampSet ->
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                    color = MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(5.dp)) {
                        Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Top) {
                            Text(rampSet.label, fontWeight = FontWeight.SemiBold)
                            Text(
                                text = rampSet.effortCue,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        MetricGrid(
                            metrics = listOf(
                                "Load" to rampSet.loadCue,
                                "Reps" to rampSet.repsCue
                            )
                        )
                        Text(
                            text = rampSet.purpose,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
        Text(
            text = "First working set gate: ${plan.firstWorkingSetGate}",
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = "Stop rule: ${plan.stopRule}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        DataChipGrid(
            items = listOf(
                "Warm-up Ramp Plan",
                "Ramp set checklist",
                "final ramp set quality",
                "first working set gate",
                "planned load percentage",
                plan.visualSpec.visualId,
                plan.visualSpec.equipmentZh
            )
        )
    }
}

@Composable
private fun NextSetVisualGuide(spec: ExerciseVisualSpec) {
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
                    .weight(0.68f)
                    .height(74.dp)
            ) {
                drawExerciseVisual(type = spec.type)
            }
            Column(modifier = Modifier.weight(1.32f), verticalArrangement = Arrangement.spacedBy(5.dp)) {
                Text("${spec.visualId} ${spec.equipment} (${spec.equipmentZh})", fontWeight = FontWeight.SemiBold)
                Text(
                    text = "Unified instance diagram: ${spec.figureTitle}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                ExerciseVisualCueSteps(spec = spec)
                Text(
                    text = "Look-for cue: ${spec.lookFor}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Beginner recognition cue: ${spec.beginnerCue}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun SessionQualityDashboardCard(dashboard: SessionQualityDashboard, language: AppLanguage) {
    SectionCard(
        title = "Session Quality Dashboard",
        subtitle = "Track completion, logging quality, RIR, muscle capacity, and risk before AI changes the plan."
    ) {
        MetricGrid(
            metrics = listOf(
                "Status" to dashboard.statusLabel,
                "Quality" to dashboard.qualityScore.toString(),
                "Completion" to "${dashboard.completedSets}/${dashboard.plannedSets}",
                "Logged" to "${dashboard.loggedSetRatePercent}%",
                "Avg RIR" to (dashboard.averageRir?.let { formatDecimal(it) } ?: "--"),
                "Volume" to "${formatDecimal(dashboard.completedVolumeKg)} kg",
                "Pain flags" to dashboard.painFlagCount.toString(),
                "Technique" to dashboard.techniqueFlagCount.toString()
            )
        )
        Text(
            text = dashboard.qualityCue,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = dashboard.capacityCue,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = dashboard.riskCue,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        if (dashboard.topMuscleVolumes.isNotEmpty()) {
            DataChipGrid(
                items = dashboard.topMuscleVolumes.map {
                    "${it.targetMuscle}: ${it.completedSets} sets, ${formatDecimal(it.volumeKg)} kg"
                }
            )
        }
    }
}

@Composable
private fun TrainingCloseoutCoachCard(
    coach: TrainingCloseoutCoach,
    language: AppLanguage,
    isLoading: Boolean,
    onRunDailyReview: () -> Unit,
    onOpenPlan: () -> Unit,
    onOpenTraining: () -> Unit,
    onOpenNutrition: () -> Unit,
    onOpenMetrics: () -> Unit,
    onOpenAi: () -> Unit,
    onPickTrainingPhoto: (PhotoEvidenceType, String) -> Unit
) {
    SectionCard(
        title = "Training Closeout Coach",
        subtitle = "Final checklist before AI review: sets, logs, photos, food, metrics, and progression confidence."
    ) {
        MetricGrid(
            metrics = listOf(
                "Status" to coach.statusLabel,
                "Closeout" to coach.closeoutScore.toString(),
                "Sets" to "${coach.completedSets}/${coach.plannedSets}",
                "Missing logs" to coach.missingLogCount.toString(),
                "Pain flags" to coach.painFlagCount.toString(),
                "Technique" to coach.techniqueFlagCount.toString()
            )
        )
        Text(
            text = coach.aiReviewGate,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )
        coach.checklist.forEach { task ->
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                color = if (task.complete) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.surfaceVariant
            ) {
                Row(
                    modifier = Modifier.padding(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = if (task.complete) "Done" else "Todo",
                        style = MaterialTheme.typography.labelSmall,
                        color = if (task.complete) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.SemiBold
                    )
                    Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                        Text(task.label, fontWeight = FontWeight.SemiBold)
                        Text(
                            text = task.status,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = task.actionCue,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
        Text(
            text = "Photos: ${coach.photoCue}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = "Nutrition: ${coach.nutritionCue}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = "Metrics: ${coach.metricsCue}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = {
                    when (coach.primaryActionRoute) {
                        DailyExecutionRoute.PLAN -> onOpenPlan()
                        DailyExecutionRoute.NUTRITION -> onOpenNutrition()
                        DailyExecutionRoute.METRICS -> onOpenMetrics()
                        DailyExecutionRoute.AI_REVIEW -> {
                            if (coach.primaryActionLabel == "View review") onOpenAi() else onRunDailyReview()
                        }
                        else -> {
                            if (coach.painFlagCount > 0 || coach.techniqueFlagCount > 0) {
                                onPickTrainingPhoto(
                                    PhotoEvidenceType.TRAINING_FORM,
                                    "Training closeout form frame for pain/technique flags, visual guide ID mapping, and progression confidence."
                                )
                            } else {
                                onOpenTraining()
                            }
                        }
                    }
                },
                enabled = !isLoading,
                modifier = Modifier.weight(1f)
            ) {
                Text(if (isLoading && coach.primaryActionRoute == DailyExecutionRoute.AI_REVIEW) "Reviewing" else coach.primaryActionLabel)
            }
            ElevatedButton(
                onClick = {
                    onPickTrainingPhoto(
                        PhotoEvidenceType.EQUIPMENT,
                        "Training closeout equipment photo for Unified Exercise Visual Atlas mapping and setup recognition."
                    )
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Equipment photo")
            }
        }
        DataChipGrid(
            items = listOf(
                "Training Closeout Coach",
                "closeout score",
                "missing set logs",
                "photo evidence cue",
                "post-workout nutrition cue",
                "metrics sync cue",
                "AI review readiness"
            )
        )
    }
}

@Composable
private fun TrainingReadinessBuilderCard(builder: TrainingReadinessBuilder, language: AppLanguage) {
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
    profile: AthleteProfile,
    language: AppLanguage,
    onRemove: () -> Unit,
    onUpdateSetEntry: (Int, Int, Int?, Double?, Double?, String) -> Unit,
    onCompleteSet: (Int, Int) -> Unit
) {
    Card(shape = RoundedCornerShape(8.dp)) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ExerciseVisualHeader(
                    name = exercise.name,
                    targetMuscle = exercise.targetMuscle,
                    detail = "${exercise.targetMuscle.ifBlank { language.t("Target muscle not set", "未设置目标肌肉") }} | ${exercise.sets} x ${exercise.reps} | ${language.t("rest", "休息")} ${exercise.restSeconds}s",
                    modifier = Modifier.weight(1f)
                )
                TextButton(onClick = onRemove) {
                    Text(language.t("Remove", "移除"))
                }
            }
            if (exercise.notes.isNotBlank()) {
                Text(
                    text = exercise.notes,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            ExerciseVisualGuide(name = exercise.name, targetMuscle = exercise.targetMuscle)
            MetricGrid(
                metrics = listOf(
                    language.t("Done", "完成") to "${exercise.completedSetCount()}/${exercise.trackedSets().size}",
                    language.t("Volume", "容量") to "${formatDecimal(exercise.volumeKg())} kg",
                    language.t("Default RIR", "默认 RIR") to (exercise.rir?.let { formatDecimal(it) } ?: "--")
                )
            )
            ExerciseHistoryCard(summary = exercise.exerciseHistorySummary(currentLog, recentLogs), language = language)
            ProgressionCueCard(cue = exercise.progressionCue(), language = language)
            ExerciseSubstitutionCard(guide = exercise.exerciseSubstitutionGuide(profile), language = language)
            exercise.trackedSets().forEachIndexed { setIndex, set ->
                if (setIndex > 0) HorizontalDivider()
                SetRow(
                    exerciseIndex = exerciseIndex,
                    setIndex = setIndex,
                    set = set,
                    language = language,
                    onUpdateSetEntry = onUpdateSetEntry,
                    onCompleteSet = onCompleteSet
                )
            }
        }
    }
}

@Composable
private fun ExerciseHistoryCard(summary: ExerciseHistorySummary, language: AppLanguage) {
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
private fun ProgressionCueCard(cue: ProgressionCue, language: AppLanguage) {
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

@Composable
private fun ExerciseSubstitutionCard(guide: ExerciseSubstitutionGuide, language: AppLanguage) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Top) {
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                    Text("Exercise Substitution Coach", fontWeight = FontWeight.SemiBold)
                    Text(
                        text = guide.statusLabel,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Text(
                    text = guide.primaryOption.visualSpec.visualId,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(
                text = guide.triggerReason,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            MetricGrid(
                metrics = listOf(
                    "Primary swap" to guide.primaryOption.name,
                    "Same target" to guide.primaryOption.targetMuscle.ifBlank { "--" },
                    "Pattern" to guide.primaryOption.pattern,
                    "Fatigue cost" to guide.primaryOption.fatigueCost
                )
            )
            ExerciseVisualHeader(
                name = guide.primaryOption.name,
                targetMuscle = guide.primaryOption.targetMuscle,
                detail = "${guide.primaryOption.visualSpec.equipmentZh} | ${guide.primaryOption.visualSpec.lookFor}"
            )
            Text(
                text = "Keep intent: ${guide.keepIntentCue}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "Load adjustment: ${guide.loadAdjustmentCue}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            DataChipGrid(
                items = listOf(
                    "same target muscle",
                    "same movement pattern",
                    "preserve rep range",
                    "fatigue cost",
                    "visual guide ID ${guide.primaryOption.visualSpec.visualId}"
                ) + guide.secondaryOptions.map { "${it.name} (${it.visualSpec.visualId})" }
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
    language: AppLanguage,
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
                Text(language.t("Set ${set.setNumber}", "第 ${set.setNumber} 组"), fontWeight = FontWeight.SemiBold)
                Text(
                    text = language.t(
                        "Target ${set.targetReps.ifBlank { "--" }} reps | rest ${set.restSeconds}s",
                        "目标 ${set.targetReps.ifBlank { "--" }} 次 | 休息 ${set.restSeconds} 秒"
                    ),
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
                Text(if (set.completed) language.t("Done", "已完成") else language.t("Complete", "完成"))
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            NumberField(
                value = reps,
                onChange = {
                    reps = it
                    onUpdateSetEntry(exerciseIndex, setIndex, it.toIntOrNull(), load.toDoubleOrNull(), rir.toDoubleOrNull(), notes)
                },
                label = language.t("Reps", "次数"),
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
            label = { Text(language.t("Set notes", "本组备注")) },
            minLines = 1
        )
    }
}

@Composable
private fun MealTemplateLibrary(language: AppLanguage, onAddMealTemplate: (String) -> Unit) {
    SectionCard(
        title = language.t("Meal Templates", "餐食模板"),
        subtitle = language.t(
            "Quick-add reliable meals, then edit notes or use photos when portions are uncertain.",
            "快速添加可靠餐食；份量不确定时再编辑备注或添加照片。"
        )
    ) {
        mealTemplates().forEach { template ->
            MealTemplateCard(template = template, language = language, onAddMealTemplate = onAddMealTemplate)
        }
    }
}

@Composable
private fun MealTemplateCard(template: MealTemplate, language: AppLanguage, onAddMealTemplate: (String) -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.Top) {
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                    Text(template.localizedTitle(language), fontWeight = FontWeight.SemiBold)
                    Text(
                        text = template.localizedSubtitle(language),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                TextButton(onClick = { onAddMealTemplate(template.id) }) {
                    Text(language.t("Add", "添加"))
                }
            }
            MetricGrid(
                metrics = listOf(
                    "Kcal" to template.calories.toString(),
                    language.t("Protein", "蛋白质") to "${formatDecimal(template.protein)} g",
                    language.t("Carbs", "碳水") to "${formatDecimal(template.carbs)} g",
                    language.t("Fat", "脂肪") to "${formatDecimal(template.fat)} g",
                    language.t("Fiber", "纤维") to "${formatDecimal(template.fiber)} g"
                )
            )
            Text(
                text = template.localizedNotes(language),
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
    onConditioningChange: (DailyConditioning) -> Unit,
    onAddMeal: (String, Int, Double, Double, Double, Double, String) -> Unit,
    onAddMealTemplate: (String) -> Unit,
    onRemoveMeal: (Int) -> Unit,
    onPickMealPhoto: (String) -> Unit,
    onOpenAi: () -> Unit
) {
    val language = state.appLanguage
    val targets = state.dailyLog.targets
    var mealName by remember { mutableStateOf("") }
    var calories by remember { mutableStateOf("") }
    var protein by remember { mutableStateOf("") }
    var carbs by remember { mutableStateOf("") }
    var fat by remember { mutableStateOf("") }
    var fiber by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var mealDescription by remember { mutableStateOf("") }
    var showNutritionDetails by remember { mutableStateOf(false) }
    val totals = state.dailyLog.nutritionTotals()
    val prefillMeal: (NextMealBuilder) -> Unit = { builder ->
        mealName = builder.localizedTitle(language)
        calories = builder.estimatedCalories().toString()
        protein = builder.proteinGrams.toString()
        carbs = builder.carbsGrams.toString()
        fat = builder.fatGrams.toString()
        fiber = builder.fiberGrams.toString()
        notes = language.t(
            "Coach prefill: ${builder.localizedPortionCue(language)} ${builder.localizedTimingCue(language)} ${builder.localizedPhotoCue(language)}",
            "教练预填：${builder.localizedPortionCue(language)} ${builder.localizedTimingCue(language)} ${builder.localizedPhotoCue(language)}"
        )
    }
    val useAiMealEstimate: () -> Unit = {
        val builder = state.dailyLog.nextMealBuilder()
        onAddMeal(
            mealDescription.ifBlank { builder.localizedTitle(language) },
            builder.estimatedCalories(),
            builder.proteinGrams.toDouble(),
            builder.carbsGrams.toDouble(),
            builder.fatGrams.toDouble(),
            builder.fiberGrams.toDouble(),
            language.t(
                "AI estimate from live meal coach. Confirm portion, oil, sauce, label, and plate size when possible.",
                "来自当前餐教练的 AI 估算。方便时确认份量、油、酱汁、标签和餐盘大小。"
            )
        )
        mealDescription = ""
    }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        LiveMealCoachCard(
            log = state.dailyLog,
            language = language,
            mealDescription = mealDescription,
            onMealDescriptionChange = { mealDescription = it },
            onUseAiEstimate = useAiMealEstimate,
            onPickMealPhoto = onPickMealPhoto,
            onOpenAi = onOpenAi
        )
        NutritionAutopilotCard(
            log = state.dailyLog,
            language = language,
            onPrefillMeal = prefillMeal,
            onPickMealPhoto = onPickMealPhoto,
            onOpenAi = onOpenAi
        )
        MealFlowCoachCard(
            log = state.dailyLog,
            language = language,
            showDetails = showNutritionDetails,
            onToggleDetails = { showNutritionDetails = !showNutritionDetails },
            onPrefillMeal = prefillMeal,
            onAddMealTemplate = onAddMealTemplate,
            onPickMealPhoto = onPickMealPhoto,
            onOpenAi = onOpenAi
        )
        QuickMealCaptureCard(
            log = state.dailyLog,
            language = language,
            mealDescription = mealDescription,
            onMealDescriptionChange = { mealDescription = it },
            onUseAiEstimate = {
                val builder = state.dailyLog.nextMealBuilder()
                onAddMeal(
                    mealDescription.ifBlank { builder.localizedTitle(language) },
                    builder.estimatedCalories(),
                    builder.proteinGrams.toDouble(),
                    builder.carbsGrams.toDouble(),
                    builder.fatGrams.toDouble(),
                    builder.fiberGrams.toDouble(),
                    language.t(
                        "AI estimate from quick capture. Confirm portion, oil, sauce, label, and plate size when possible.",
                        "快速记录的 AI 估算。方便时确认份量、油、酱汁、标签和餐盘大小。"
                    )
                )
                mealDescription = ""
            },
            onPickMealPhoto = onPickMealPhoto,
            onOpenAi = onOpenAi
        )
        MealEstimateReceiptCard(
            log = state.dailyLog,
            language = language,
            mealDescription = mealDescription,
            onUseAiEstimate = {
                val builder = state.dailyLog.nextMealBuilder()
                onAddMeal(
                    mealDescription.ifBlank { builder.localizedTitle(language) },
                    builder.estimatedCalories(),
                    builder.proteinGrams.toDouble(),
                    builder.carbsGrams.toDouble(),
                    builder.fatGrams.toDouble(),
                    builder.fiberGrams.toDouble(),
                    language.t(
                        "AI estimate receipt. Confirm portions, oil, sauce, labels, and plate size when possible.",
                        "AI 估算回执。方便时确认份量、油、酱汁、标签和餐盘大小。"
                    )
                )
                mealDescription = ""
            },
            onPickMealPhoto = onPickMealPhoto,
            onOpenAi = onOpenAi
        )
        if (showNutritionDetails) {
            NutritionPacingCard(log = state.dailyLog, language = language)
            NextMealBuilderCard(log = state.dailyLog, language = language)
            MealLoggingCoachCard(
                log = state.dailyLog,
                language = language,
                onPrefillMeal = prefillMeal,
                onAddMealTemplate = onAddMealTemplate,
                onPickMealPhoto = onPickMealPhoto,
                onOpenAi = onOpenAi
            )
            MealAssemblyGuideCard(guide = mealAssemblyGuide(state.dailyLog), language = language)
            BodyCompositionCard(
                guidance = bodyCompositionGuidance(state.dailyLog, state.recentLogs, state.athleteProfile),
                subtitle = language.t(
                    "Use body-weight trend, average intake, and phase goal before changing targets.",
                    "调整目标前，先结合体重趋势、平均摄入和当前阶段。"
                ),
                language = language
            )
            ConditioningHydrationCard(
                log = state.dailyLog,
                recentLogs = state.recentLogs,
                profile = state.athleteProfile,
                language = language,
                onConditioningChange = onConditioningChange
            )
            MealTemplateLibrary(language = language, onAddMealTemplate = onAddMealTemplate)
            SectionCard(
                title = language.t("Nutrition Targets", "营养目标"),
                subtitle = language.t(
                    "Use weighed food when possible; use photos for AI estimates when weighing is not practical.",
                    "能称重就称重；无法称重时，用照片帮助 AI 估算。"
                )
            ) {
                MacroLine(language.t("Calories", "热量"), totals.calories.toString(), targets.calories.toString(), "kcal")
                MacroLine(language.t("Protein", "蛋白质"), totals.protein.toString(), targets.protein.toString(), "g")
                MacroLine(language.t("Carbs", "碳水"), totals.carbs.toString(), targets.carbs.toString(), "g")
                MacroLine(language.t("Fat", "脂肪"), totals.fat.toString(), targets.fat.toString(), "g")
                MacroLine(language.t("Fiber", "纤维"), totals.fiber.toString(), targets.fiber.toString(), "g")
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    NumberField(
                        value = targets.calories.toString(),
                        onChange = { onTargetsChange(targets.copy(calories = it.toIntOrNull() ?: targets.calories)) },
                        label = language.t("Target kcal", "目标热量"),
                        modifier = Modifier.weight(1f)
                    )
                    NumberField(
                        value = targets.protein.toString(),
                        onChange = { onTargetsChange(targets.copy(protein = it.toIntOrNull() ?: targets.protein)) },
                        label = language.t("Protein", "蛋白质"),
                        modifier = Modifier.weight(1f)
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    NumberField(
                        value = targets.carbs.toString(),
                        onChange = { onTargetsChange(targets.copy(carbs = it.toIntOrNull() ?: targets.carbs)) },
                        label = language.t("Carbs", "碳水"),
                        modifier = Modifier.weight(1f)
                    )
                    NumberField(
                        value = targets.fat.toString(),
                        onChange = { onTargetsChange(targets.copy(fat = it.toIntOrNull() ?: targets.fat)) },
                        label = language.t("Fat", "脂肪"),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        if (showNutritionDetails) {
            SectionCard(
                title = language.t("Manual macro edit", "手动宏量编辑"),
                subtitle = language.t(
                    "Use this when you weighed the food or want to correct an AI estimate.",
                    "称重后或需要修正 AI 估算时，再使用这里。"
                )
            ) {
                OutlinedTextField(
                    value = mealName,
                    onValueChange = { mealName = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(language.t("Meal", "餐食")) },
                    singleLine = true
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    NumberField(value = calories, onChange = { calories = it }, label = "kcal", modifier = Modifier.weight(1f))
                    DecimalField(value = protein, onChange = { protein = it }, label = language.t("Protein", "蛋白质"), modifier = Modifier.weight(1f))
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    DecimalField(value = carbs, onChange = { carbs = it }, label = language.t("Carbs", "碳水"), modifier = Modifier.weight(1f))
                    DecimalField(value = fat, onChange = { fat = it }, label = language.t("Fat", "脂肪"), modifier = Modifier.weight(1f))
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    DecimalField(value = fiber, onChange = { fiber = it }, label = language.t("Fiber", "纤维"), modifier = Modifier.weight(1f))
                    ElevatedButton(
                        onClick = {
                            onPickMealPhoto(
                                "meal photo for portion estimate. Meal field: ${mealName.ifBlank { "not named" }}. Notes: ${notes.ifBlank { "none" }}"
                            )
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(language.t("Meal photo", "餐食照片"))
                    }
                }
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(language.t("Food source, portion, oils, label, uncertainty", "食物来源、份量、油脂、标签、不确定点")) },
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
                    Text(language.t("Add meal", "添加餐食"))
                }
            }
        }

        if (state.dailyLog.meals.isEmpty()) {
            EmptyState(language.t(
                "No meals logged yet. Add meals or attach photos before running a nutrition review.",
                "今天还没有记录餐食。先添加餐食或照片，再运行营养复盘。"
            ))
        } else {
            SectionCard(
                title = language.t("Meals", "餐食记录"),
                subtitle = language.t("${state.dailyLog.meals.size} logged today", "今天已记录 ${state.dailyLog.meals.size} 餐")
            ) {
                state.dailyLog.meals.forEachIndexed { index, meal ->
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.Top) {
                        Text(
                            text = "${meal.name}: ${meal.calories} kcal, P ${formatDecimal(meal.protein)} g, C ${formatDecimal(meal.carbs)} g, F ${formatDecimal(meal.fat)} g",
                            modifier = Modifier.weight(1f)
                        )
                        TextButton(onClick = { onRemoveMeal(index) }) {
                            Text(language.t("Remove", "移除"))
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

        PhotoEvidenceCard(photoEvidence = state.dailyLog.photoEvidence, language = language, compact = true)
    }
}

@Composable
private fun MetricsFlowCoachCard(
    state: CoachUiState,
    showDetails: Boolean,
    onToggleDetails: () -> Unit,
    onConnectHealthData: () -> Unit,
    onSyncHealthData: () -> Unit,
    onPickPhysiquePhoto: () -> Unit
) {
    val language = state.appLanguage
    val metrics = state.dailyLog.metrics
    val snapshot = state.healthSnapshot
    val recovery = recoveryGuidance(state.dailyLog, state.recentLogs)
    val physique = physiqueMeasurementSummary(state.dailyLog, state.recentLogs)
    val physiquePhotoCount = state.dailyLog.photoEvidence.count { it.type == PhotoEvidenceType.PHYSIQUE }
    val missingKeyBodyData = metrics.bodyWeightKg == null || metrics.waistCm == null
    val primaryTitle: String
    val primaryDetail: String
    val primaryLabel: String
    val primaryEnabled: Boolean
    val primaryAction: () -> Unit
    when {
        state.isHealthSyncing -> {
            primaryTitle = language.t("Refreshing health data", "正在刷新健康数据")
            primaryDetail = language.t(
                "The app is pulling the latest Health Connect body, sleep, steps, heart-rate, and calorie records.",
                "App 正在拉取最新 Health Connect 身体、睡眠、步数、心率和消耗记录。"
            )
            primaryLabel = language.t("Refreshing", "刷新中")
            primaryEnabled = false
            primaryAction = {}
        }
        !snapshot.permissionsGranted -> {
            primaryTitle = language.t("Connect health data", "连接健康数据")
            primaryDetail = language.t(
                "Authorize health data once. After that, the app refreshes available body, sleep, steps, heart-rate, and calorie records automatically on open.",
                "只需要授权一次健康数据。之后 App 打开时会自动刷新可用的身体、睡眠、步数、心率和消耗记录。"
            )
            primaryLabel = language.t("Connect health data", "连接健康数据")
            primaryEnabled = true
            primaryAction = onConnectHealthData
        }
        metrics.healthSyncedAt.isBlank() -> {
            primaryTitle = language.t("Auto health refresh ready", "健康数据自动刷新就绪")
            primaryDetail = language.t(
                "Health data refreshes on app open after permission. Tap refresh only when you want to pull weight, body fat, sleep, steps, resting HR, and calorie burn again before AI review.",
                "授权后，App 打开时会自动刷新健康数据。只有在 AI 复盘前想重新拉取体重、体脂、睡眠、步数、静息心率和消耗热量时，才需要手动刷新。"
            )
            primaryLabel = language.t("Refresh now", "立即刷新")
            primaryEnabled = true
            primaryAction = onSyncHealthData
        }
        missingKeyBodyData -> {
            primaryTitle = language.t("Add body check-in", "补充身体记录")
            primaryDetail = language.t(
                "Add body weight and waist first; AI should not adjust calories from training or scale noise alone.",
                "先补体重和腰围；AI 不应该只凭训练或体重波动调整热量。"
            )
            primaryLabel = language.t("Open fields", "打开字段")
            primaryEnabled = true
            primaryAction = onToggleDetails
        }
        physiquePhotoCount == 0 -> {
            primaryTitle = language.t("Add progress photo", "添加体型照片")
            primaryDetail = language.t(
                "Use consistent lighting, distance, posture, and pump state so photos can be compared with measurements.",
                "保持相同光线、距离、姿势和充血状态，让照片能和围度一起比较。"
            )
            primaryLabel = language.t("Progress photo", "体型照片")
            primaryEnabled = true
            primaryAction = onPickPhysiquePhoto
        }
        else -> {
            primaryTitle = language.t("Metrics ready for AI", "身体数据可用于 AI")
            primaryDetail = language.t(
                "Health sync, measurements, and physique photo context are ready; expand details only when you want to inspect the evidence.",
                "健康同步、围度和体型照片上下文已可用；需要检查证据时再展开细节。"
            )
            primaryLabel = language.t("Review details", "查看细节")
            primaryEnabled = true
            primaryAction = onToggleDetails
        }
    }
    SectionCard(
        title = language.t("Metrics Flow Coach", "身体数据流程教练"),
        subtitle = language.t(
            "Auto-refresh health and sleep data, fill the minimum body check-in, and keep recovery evidence ready for AI.",
            "自动刷新健康与睡眠数据，补齐最小身体记录，并让恢复证据可用于 AI。"
        )
    ) {
        Text(
            text = language.t("NEXT METRICS ACTION", "下一步数据动作"),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = primaryTitle,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = primaryDetail,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Button(onClick = primaryAction, enabled = primaryEnabled, modifier = Modifier.fillMaxWidth()) {
            Text(primaryLabel)
        }
        TextButton(onClick = onToggleDetails, modifier = Modifier.fillMaxWidth()) {
            Text(
                if (showDetails) {
                    language.t("Hide metrics details", "收起数据细节")
                } else {
                    language.t("Show metrics details", "展开数据细节")
                }
            )
        }
        if (showDetails) {
            MetricGrid(
                metrics = listOf(
                    language.t("Health", "健康") to when {
                        state.isHealthSyncing -> language.t("Refreshing", "刷新中")
                        snapshot.permissionsGranted -> language.t("Authorized", "已授权")
                        snapshot.available -> language.t("Needs access", "需要授权")
                        else -> language.t("Manual", "手动")
                    },
                    language.t("Weight", "体重") to formatOptional(metrics.bodyWeightKg ?: snapshot.bodyWeightKg, "kg"),
                    language.t("Body fat", "体脂") to formatOptional(metrics.bodyFatPercent ?: snapshot.bodyFatPercent, "%"),
                    language.t("Sleep", "睡眠") to formatOptional(metrics.sleepHours ?: snapshot.sleepHours, "h"),
                    language.t("Steps", "步数") to ((metrics.steps.takeIf { it > 0 } ?: snapshot.steps)?.toString() ?: "--"),
                    language.t("Photo", "照片") to physiquePhotoCount.toString(),
                    language.t("Recovery", "恢复") to recovery.readinessScore.toString(),
                    language.t("Physique", "体型") to physique.measurementScore.toString()
                )
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                TextButton(onClick = onSyncHealthData, enabled = !state.isHealthSyncing, modifier = Modifier.weight(1f)) {
                    Text(language.t("Refresh", "刷新"))
                }
                TextButton(onClick = onPickPhysiquePhoto, modifier = Modifier.weight(1f)) {
                    Text(language.t("Photo", "照片"))
                }
            }
            DataChipGrid(
                items = listOf(
                    language.t("Health Connect or manual fallback", "Health Connect 或手动补录"),
                    language.t("Auto refresh on app open after permission", "授权后打开 App 自动刷新"),
                    language.t("Weight, waist, sleep, steps, HR", "体重、腰围、睡眠、步数、心率"),
                    language.t("Progress photo linked to measurements", "体型照片联动围度"),
                    language.t("Metrics detail layers open", "数据细节层已展开")
                )
            )
        } else {
            Text(
                text = language.t("Metrics detail layers hidden", "数据细节层已收起"),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun MetricsAutopilotCard(
    state: CoachUiState,
    onConnectHealthData: () -> Unit,
    onSyncHealthData: () -> Unit,
    onPickPhysiquePhoto: () -> Unit,
    onOpenDetails: () -> Unit
) {
    val language = state.appLanguage
    val log = state.dailyLog
    val metrics = log.metrics
    val snapshot = state.healthSnapshot
    val recovery = recoveryGuidance(log, state.recentLogs)
    val physique = physiqueMeasurementSummary(log, state.recentLogs)
    val physiquePhotoCount = log.photoEvidence.count { it.type == PhotoEvidenceType.PHYSIQUE }
    val trendDays = state.recentLogs.count {
        it.metrics.bodyWeightKg != null || it.metrics.waistCm != null || it.metrics.sleepHours != null
    }.coerceAtMost(7)
    val trendConfidence = when {
        trendDays >= 5 && metrics.waistCm != null -> language.t("High: 5+ days + waist", "高：5 天以上 + 腰围")
        trendDays >= 3 -> language.t("Medium: trend forming", "中：趋势正在形成")
        metrics.bodyWeightKg != null || snapshot.bodyWeightKg != null -> language.t("Low: single-day signal", "低：单日信号")
        else -> language.t("Missing: log body data", "缺失：先记录身体数据")
    }
    val healthPermission = when {
        state.isHealthSyncing -> language.t("Refreshing", "刷新中")
        snapshot.permissionsGranted -> language.t("Authorized", "已授权")
        snapshot.available -> language.t("Needs permission", "需要授权")
        else -> language.t("Manual fallback", "手动补录")
    }
    val sleepAndRecovery = "${formatOptional(metrics.sleepHours ?: snapshot.sleepHours, "h")} / ${recovery.readinessScore}"
    val nextMetricsAction: String
    val actionLabel: String
    val actionEnabled: Boolean
    val action: () -> Unit
    when {
        state.isHealthSyncing -> {
            nextMetricsAction = language.t("Wait for health refresh", "等待健康数据刷新")
            actionLabel = language.t("Refreshing", "刷新中")
            actionEnabled = false
            action = {}
        }
        !snapshot.permissionsGranted -> {
            nextMetricsAction = language.t("Grant health permission", "授权健康数据")
            actionLabel = language.t("Connect health data", "连接健康数据")
            actionEnabled = true
            action = onConnectHealthData
        }
        metrics.healthSyncedAt.isBlank() -> {
            nextMetricsAction = language.t("Sync today's metrics", "同步今天的数据")
            actionLabel = language.t("Sync today", "同步今天")
            actionEnabled = true
            action = onSyncHealthData
        }
        metrics.bodyWeightKg == null || metrics.waistCm == null -> {
            nextMetricsAction = language.t("Add weight and waist", "补充体重和腰围")
            actionLabel = language.t("Open fields", "打开字段")
            actionEnabled = true
            action = onOpenDetails
        }
        physiquePhotoCount == 0 -> {
            nextMetricsAction = language.t("Add progress photo", "添加体型照片")
            actionLabel = language.t("Progress photo", "体型照片")
            actionEnabled = true
            action = onPickPhysiquePhoto
        }
        else -> {
            nextMetricsAction = language.t("Review trend before AI changes", "AI 调整前查看趋势")
            actionLabel = language.t("Review details", "查看细节")
            actionEnabled = true
            action = onOpenDetails
        }
    }
    SectionCard(
        title = language.t("Metrics Autopilot", "身体数据自动教练"),
        subtitle = language.t(
            "One screen body-data loop: sync health data, read the trend, attach physique context, then let AI review.",
            "一屏身体数据闭环：同步健康数据、读取趋势、补充体型上下文，再交给 AI 复盘。"
        )
    ) {
        Text(
            text = language.t("METRICS AUTOPILOT", "身体数据自动教练"),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
        MetricGrid(
            metrics = listOf(
                language.t("Loop", "闭环") to language.t("Sync -> Trend -> Photo -> Review", "同步 -> 趋势 -> 照片 -> 复盘"),
                language.t("Health permission", "健康权限") to healthPermission,
                language.t("Trend confidence", "趋势置信度") to trendConfidence,
                language.t("Sleep and recovery", "睡眠与恢复") to sleepAndRecovery
            )
        )
        LinearProgressIndicator(
            progress = { ((trendDays.coerceAtLeast(1) / 7f) + (recovery.readinessScore / 100f)) / 2f },
            modifier = Modifier.fillMaxWidth()
        )
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
            color = IfbbProGlassStrongSurface
        ) {
            Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(language.t("Next metrics action", "下一步身体数据动作"), fontWeight = FontWeight.SemiBold)
                Text(nextMetricsAction, fontWeight = FontWeight.SemiBold)
                Text(
                    text = language.t(
                        "AI should not change training or calories until body trend, sleep/recovery, and photo/measurement evidence are linked.",
                        "身体趋势、睡眠恢复、照片和围度证据联动前，AI 不应轻易改变训练或热量。"
                    ),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Button(onClick = action, enabled = actionEnabled, modifier = Modifier.fillMaxWidth()) {
                    Text(actionLabel)
                }
            }
        }
        DataChipGrid(
            items = listOf(
                language.t("Sync", "同步"),
                language.t("Trend", "趋势"),
                language.t("Photo", "照片"),
                language.t("Review", "复盘")
            )
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            ElevatedButton(onClick = onSyncHealthData, enabled = !state.isHealthSyncing, modifier = Modifier.weight(1f)) {
                Text(language.t("Refresh health", "刷新健康"))
            }
            TextButton(onClick = onPickPhysiquePhoto, modifier = Modifier.weight(1f)) {
                Text(language.t("Progress photo", "体型照片"))
            }
        }
        Text(
            text = language.t(
                "AI uses health permission status, imported sleep, body weight, waist trend, recovery score, and progress photos as one evidence bundle.",
                "AI 会把健康权限状态、导入睡眠、体重、腰围趋势、恢复评分和体型照片作为同一组证据。"
            ),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun HealthSyncReceiptCard(
    state: CoachUiState,
    onConnectHealthData: () -> Unit,
    onSyncHealthData: () -> Unit,
    onOpenDetails: () -> Unit
) {
    val language = state.appLanguage
    val metrics = state.dailyLog.metrics
    val snapshot = state.healthSnapshot
    val source = metrics.healthDataSource.ifBlank { snapshot.source.ifBlank { "Health Connect" } }
    val importedSignals = listOfNotNull(
        (metrics.bodyWeightKg ?: snapshot.bodyWeightKg)?.let { language.t("Weight", "体重") },
        (metrics.bodyFatPercent ?: snapshot.bodyFatPercent)?.let { language.t("Body fat", "体脂") },
        (metrics.leanBodyMassKg ?: snapshot.leanBodyMassKg)?.let { language.t("Lean mass", "瘦体重") },
        (metrics.sleepHours ?: snapshot.sleepHours)?.let { language.t("Sleep", "睡眠") },
        ((metrics.steps.takeIf { it > 0 } ?: snapshot.steps?.toInt()))?.let { language.t("Steps", "步数") },
        (metrics.restingHeartRateBpm ?: snapshot.restingHeartRateBpm)?.let { language.t("Resting HR", "静息心率") },
        (metrics.totalCaloriesBurnedKcal ?: snapshot.totalCaloriesBurnedKcal)?.let { language.t("Calorie burn", "消耗热量") }
    )
    val missingSignals = listOfNotNull(
        if (metrics.bodyWeightKg == null && snapshot.bodyWeightKg == null) language.t("weight", "体重") else null,
        if (metrics.sleepHours == null && snapshot.sleepHours == null) language.t("sleep", "睡眠") else null,
        if (metrics.steps <= 0 && snapshot.steps == null) language.t("steps", "步数") else null,
        if (metrics.restingHeartRateBpm == null && snapshot.restingHeartRateBpm == null) language.t("resting HR", "静息心率") else null,
        if (metrics.totalCaloriesBurnedKcal == null && snapshot.totalCaloriesBurnedKcal == null) language.t("calorie burn", "消耗热量") else null
    )
    val importedLabel = if (importedSignals.isEmpty()) {
        language.t("None yet", "暂未导入")
    } else {
        importedSignals.joinToString(", ")
    }
    val missingLabel = if (missingSignals.isEmpty()) {
        language.t("No key gaps", "关键项无缺口")
    } else {
        missingSignals.joinToString(", ")
    }
    val syncStatus = when {
        state.isHealthSyncing -> language.t("Refreshing", "刷新中")
        metrics.healthSyncedAt.isNotBlank() -> language.t("Synced today", "今天已同步")
        snapshot.permissionsGranted -> language.t("Auto refresh on app open", "打开 App 自动刷新")
        snapshot.available -> language.t("Needs permission", "需要授权")
        else -> language.t("Manual fallback still works", "手动补录仍可用")
    }

    SectionCard(
        title = language.t("Health sync receipt", "健康同步回执"),
        subtitle = language.t(
            "Imported health signals, missing gaps, and how AI will use them before changing training or calories.",
            "已导入健康信号、缺失项，以及 AI 在调整训练或热量前会如何使用它们。"
        )
    ) {
        Text(
            text = language.t("HEALTH SYNC RECEIPT", "健康同步回执"),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
        MetricGrid(
            metrics = listOf(
                language.t("Status", "状态") to syncStatus,
                language.t("Source", "来源") to source,
                language.t("Imported today", "今天已导入") to importedSignals.size.toString(),
                language.t("Missing signals", "缺失信号") to missingSignals.size.toString()
            )
        )
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
            color = IfbbProGlassStrongSurface
        ) {
            Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(language.t("Imported today", "今天已导入"), fontWeight = FontWeight.SemiBold)
                Text(
                    text = importedLabel,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(language.t("Missing signals", "缺失信号"), fontWeight = FontWeight.SemiBold)
                Text(
                    text = missingLabel,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        Text(
            text = language.t(
                "AI uses sleep, weight, steps, resting HR, and calorie burn with training and food before changing training or calories.",
                "AI 会把睡眠、体重、步数、静息心率和消耗热量，与训练和饮食一起看，再决定是否调整训练或热量。"
            ),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = language.t(
                "Manual fallback still works when a phone, scale, watch, or source app does not expose a compatible Health Connect record.",
                "当手机、体脂秤、手表或来源 App 没有暴露兼容的 Health Connect 记录时，手动补录仍可使用。"
            ),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = if (snapshot.permissionsGranted) onSyncHealthData else onConnectHealthData,
                enabled = !state.isHealthSyncing,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    if (snapshot.permissionsGranted) {
                        language.t("Sync today", "同步今天")
                    } else {
                        language.t("Connect health", "连接健康")
                    }
                )
            }
            ElevatedButton(onClick = onOpenDetails, modifier = Modifier.weight(1f)) {
                Text(language.t("Manual fields", "手动字段"))
            }
        }
    }
}

@Composable
private fun MetricsPage(
    state: CoachUiState,
    onMetricsChange: (DailyMetrics) -> Unit,
    onConditioningChange: (DailyConditioning) -> Unit,
    onReflectionChange: (String) -> Unit,
    onPickPhysiquePhoto: () -> Unit,
    onConnectHealthData: () -> Unit,
    onSyncHealthData: () -> Unit
) {
    val metrics = state.dailyLog.metrics
    val language = state.appLanguage
    var showMetricsDetails by remember { mutableStateOf(false) }
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        MetricsAutopilotCard(
            state = state,
            onConnectHealthData = onConnectHealthData,
            onSyncHealthData = onSyncHealthData,
            onPickPhysiquePhoto = onPickPhysiquePhoto,
            onOpenDetails = { showMetricsDetails = true }
        )
        MetricsFlowCoachCard(
            state = state,
            showDetails = showMetricsDetails,
            onToggleDetails = { showMetricsDetails = !showMetricsDetails },
            onConnectHealthData = onConnectHealthData,
            onSyncHealthData = onSyncHealthData,
            onPickPhysiquePhoto = onPickPhysiquePhoto
        )
        HealthSyncReceiptCard(
            state = state,
            onConnectHealthData = onConnectHealthData,
            onSyncHealthData = onSyncHealthData,
            onOpenDetails = { showMetricsDetails = true }
        )
        if (showMetricsDetails) {
            HealthConnectCard(
                state = state,
                onConnectHealthData = onConnectHealthData,
                onSyncHealthData = onSyncHealthData
            )
        RecoveryGuidanceCard(
            guidance = recoveryGuidance(state.dailyLog, state.recentLogs),
            subtitle = language.t(
                "Conservative training-pressure guidance from logged recovery and health signals.",
                "根据已记录的恢复和健康信号，给出保守的训练压力建议。"
            ),
            language = language
        )
        ConditioningHydrationCard(
            log = state.dailyLog,
            recentLogs = state.recentLogs,
            profile = state.athleteProfile,
            language = language,
            onConditioningChange = onConditioningChange
        )
        PhysiqueMeasurementSummaryCard(summary = physiqueMeasurementSummary(state.dailyLog, state.recentLogs), language = language)
        SectionCard(
            title = language.t("Physique Photo Check-in", "体型照片打卡"),
            subtitle = language.t(
                "Attach progress photos under consistent lighting, posture, distance, and pump state.",
                "在一致的光线、姿势、距离和充血状态下添加进度照片。"
            )
        ) {
            DataChipGrid(
                items = listOf(
                    language.t("Front/side/back", "正面/侧面/背面"),
                    language.t("Same lighting", "相同光线"),
                    language.t("Same distance", "相同距离"),
                    language.t("Same pump state", "相同充血状态"),
                    language.t("Compare with measurements", "和围度一起比较")
                )
            )
            ElevatedButton(onClick = onPickPhysiquePhoto, modifier = Modifier.fillMaxWidth()) {
                Text(language.t("Progress photo", "体型照片"))
            }
        }
        PhotoEvidenceCard(photoEvidence = state.dailyLog.photoEvidence, language = language, compact = true)
        TrendOverviewCard(logs = state.recentLogs, language = language)
        SectionCard(
            title = language.t("Metrics", "身体数据"),
            subtitle = language.t(
                "These recovery and physique signals help AI decide whether to push, hold, deload, or adjust food.",
                "这些恢复和体型信号会帮助 AI 判断是推进、保持、减量、deload，还是调整饮食。"
            )
        ) {
            Text(language.t("Body composition", "身体组成"), fontWeight = FontWeight.SemiBold)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                DecimalField(
                    value = metrics.bodyWeightKg?.toString().orEmpty(),
                    onChange = { onMetricsChange(metrics.copy(bodyWeightKg = it.toDoubleOrNull())) },
                    label = language.t("Weight kg", "体重 kg"),
                    modifier = Modifier.weight(1f)
                )
                DecimalField(
                    value = metrics.bodyFatPercent?.toString().orEmpty(),
                    onChange = { onMetricsChange(metrics.copy(bodyFatPercent = it.toDoubleOrNull())) },
                    label = language.t("Body fat %", "体脂 %"),
                    modifier = Modifier.weight(1f)
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                DecimalField(
                    value = metrics.leanBodyMassKg?.toString().orEmpty(),
                    onChange = { onMetricsChange(metrics.copy(leanBodyMassKg = it.toDoubleOrNull())) },
                    label = language.t("Lean mass kg", "瘦体重 kg"),
                    modifier = Modifier.weight(1f)
                )
                DecimalField(
                    value = metrics.waistCm?.toString().orEmpty(),
                    onChange = { onMetricsChange(metrics.copy(waistCm = it.toDoubleOrNull())) },
                    label = language.t("Waist cm", "腰围 cm"),
                    modifier = Modifier.weight(1f)
                )
            }
            Text(language.t("Physique Measurements", "体型围度"), fontWeight = FontWeight.SemiBold)
            Text(
                text = language.t(
                    "Track bodybuilding-relevant dimensions in cm so AI can compare waist control, shoulder/chest growth, arm/thigh symmetry, and physique proportion trends.",
                    "用 cm 记录健美相关围度，让 AI 比较腰围控制、肩胸增长、手臂/大腿对称和体型比例趋势。"
                ),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                DecimalField(
                    value = metrics.chestCm?.toString().orEmpty(),
                    onChange = { onMetricsChange(metrics.copy(chestCm = it.toDoubleOrNull())) },
                    label = language.t("Chest cm", "胸围 cm"),
                    modifier = Modifier.weight(1f)
                )
                DecimalField(
                    value = metrics.shoulderCm?.toString().orEmpty(),
                    onChange = { onMetricsChange(metrics.copy(shoulderCm = it.toDoubleOrNull())) },
                    label = language.t("Shoulder cm", "肩围 cm"),
                    modifier = Modifier.weight(1f)
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                DecimalField(
                    value = metrics.hipCm?.toString().orEmpty(),
                    onChange = { onMetricsChange(metrics.copy(hipCm = it.toDoubleOrNull())) },
                    label = language.t("Hip cm", "臀围 cm"),
                    modifier = Modifier.weight(1f)
                )
                DecimalField(
                    value = metrics.neckCm?.toString().orEmpty(),
                    onChange = { onMetricsChange(metrics.copy(neckCm = it.toDoubleOrNull())) },
                    label = language.t("Neck cm", "颈围 cm"),
                    modifier = Modifier.weight(1f)
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                DecimalField(
                    value = metrics.leftArmCm?.toString().orEmpty(),
                    onChange = { onMetricsChange(metrics.copy(leftArmCm = it.toDoubleOrNull())) },
                    label = language.t("Left arm cm", "左臂 cm"),
                    modifier = Modifier.weight(1f)
                )
                DecimalField(
                    value = metrics.rightArmCm?.toString().orEmpty(),
                    onChange = { onMetricsChange(metrics.copy(rightArmCm = it.toDoubleOrNull())) },
                    label = language.t("Right arm cm", "右臂 cm"),
                    modifier = Modifier.weight(1f)
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                DecimalField(
                    value = metrics.leftThighCm?.toString().orEmpty(),
                    onChange = { onMetricsChange(metrics.copy(leftThighCm = it.toDoubleOrNull())) },
                    label = language.t("Left thigh cm", "左大腿 cm"),
                    modifier = Modifier.weight(1f)
                )
                DecimalField(
                    value = metrics.rightThighCm?.toString().orEmpty(),
                    onChange = { onMetricsChange(metrics.copy(rightThighCm = it.toDoubleOrNull())) },
                    label = language.t("Right thigh cm", "右大腿 cm"),
                    modifier = Modifier.weight(1f)
                )
            }
            DataChipGrid(
                items = listOf(
                    language.t("waist-to-shoulder trend", "肩腰比例趋势"),
                    language.t("arm symmetry", "手臂对称"),
                    language.t("thigh symmetry", "大腿对称"),
                    language.t("chest/shoulder growth", "胸肩增长"),
                    language.t("hip and waist control", "臀围与腰围控制"),
                    language.t("weekly tape-measure check-in", "每周卷尺打卡")
                )
            )
            Text(language.t("Recovery inputs", "恢复输入"), fontWeight = FontWeight.SemiBold)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                DecimalField(
                    value = metrics.sleepHours?.toString().orEmpty(),
                    onChange = { onMetricsChange(metrics.copy(sleepHours = it.toDoubleOrNull())) },
                    label = language.t("Sleep h", "睡眠 h"),
                    modifier = Modifier.weight(1f)
                )
                NumberField(
                    value = metrics.steps.toString(),
                    onChange = { onMetricsChange(metrics.copy(steps = it.toIntOrNull() ?: metrics.steps)) },
                    label = language.t("Steps", "步数"),
                    modifier = Modifier.weight(1f)
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                DecimalField(
                    value = metrics.restingHeartRateBpm?.toString().orEmpty(),
                    onChange = { onMetricsChange(metrics.copy(restingHeartRateBpm = it.toDoubleOrNull())) },
                    label = language.t("Resting HR", "静息心率"),
                    modifier = Modifier.weight(1f)
                )
                DecimalField(
                    value = metrics.totalCaloriesBurnedKcal?.toString().orEmpty(),
                    onChange = { onMetricsChange(metrics.copy(totalCaloriesBurnedKcal = it.toDoubleOrNull())) },
                    label = language.t("Burned kcal", "消耗 kcal"),
                    modifier = Modifier.weight(1f)
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                NumberField(
                    value = metrics.hunger.toString(),
                    onChange = { onMetricsChange(metrics.copy(hunger = it.toIntOrNull() ?: metrics.hunger)) },
                    label = language.t("Hunger 1-5", "饥饿 1-5"),
                    modifier = Modifier.weight(1f)
                )
                NumberField(
                    value = metrics.fatigue.toString(),
                    onChange = { onMetricsChange(metrics.copy(fatigue = it.toIntOrNull() ?: metrics.fatigue)) },
                    label = language.t("Fatigue 1-5", "疲劳 1-5"),
                    modifier = Modifier.weight(1f)
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                NumberField(
                    value = metrics.soreness.toString(),
                    onChange = { onMetricsChange(metrics.copy(soreness = it.toIntOrNull() ?: metrics.soreness)) },
                    label = language.t("Soreness 1-5", "酸痛 1-5"),
                    modifier = Modifier.weight(1f)
                )
                NumberField(
                    value = metrics.stress.toString(),
                    onChange = { onMetricsChange(metrics.copy(stress = it.toIntOrNull() ?: metrics.stress)) },
                    label = language.t("Stress 1-5", "压力 1-5"),
                    modifier = Modifier.weight(1f)
                )
            }
            if (metrics.healthDataSource.isNotBlank()) {
                Text(
                    text = language.t(
                        "Last health sync: ${metrics.healthDataSource} at ${metrics.healthSyncedAt.ifBlank { "--" }}",
                        "最近健康同步：${metrics.healthDataSource}，时间 ${metrics.healthSyncedAt.ifBlank { "--" }}"
                    ),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            OutlinedTextField(
                value = state.dailyLog.reflection,
                onValueChange = onReflectionChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text(language.t("Daily reflection", "每日反思")) },
                minLines = 3
            )
        }
    }
    }
}

private fun HealthSnapshot.localizedMessage(language: AppLanguage): String {
    return when {
        message == "Health Connect not checked yet." -> language.t(
            "Health Connect not checked yet.",
            "尚未检查 Health Connect。"
        )
        message == "Health Connect ready." -> language.t(
            "Health Connect ready.",
            "Health Connect 已就绪。"
        )
        message == "Health Connect needs to be installed or updated before syncing." -> language.t(
            "Health Connect needs to be installed or updated before syncing.",
            "同步前需要安装或更新 Health Connect。"
        )
        message == "Health Connect is not available on this device." -> language.t(
            "Health Connect is not available on this device.",
            "此设备暂不可用 Health Connect。"
        )
        message == "Health Connect permissions granted." -> language.t(
            "Health Connect permissions granted.",
            "Health Connect 权限已授权。"
        )
        message == "Health Connect permissions granted. Sync today's metrics next." -> language.t(
            "Health Connect permissions granted. Sync today's metrics next.",
            "Health Connect 权限已授权。下一步同步今天的身体数据。"
        )
        message == "Health Connect permissions granted. Automatic daily refresh is ready." -> language.t(
            "Health Connect permissions granted. Automatic daily refresh is ready.",
            "Health Connect 权限已授权。每日自动刷新已就绪。"
        )
        message == "Some Health Connect permissions were not granted; manual metrics still work." -> language.t(
            "Some Health Connect permissions were not granted; manual metrics still work.",
            "部分 Health Connect 权限未授权；仍可手动记录身体数据。"
        )
        message.startsWith("Health Connect permissions needed:") -> language.t(
            message,
            "仍需授权 Health Connect 权限：${message.substringAfter(":").trim()}"
        )
        message == "Connect health data before syncing body and recovery metrics." -> language.t(
            "Connect health data before syncing body and recovery metrics.",
            "同步身体与恢复数据前，请先连接健康数据。"
        )
        message.startsWith("Synced Health Connect metrics.") -> language.t(
            message,
            "已同步健康数据。可用记录会自动进入趋势图和 AI 复盘。"
        )
        message == "Connected, but no supported Health Connect records were found for today's sync window." -> language.t(
            "Connected, but no supported Health Connect records were found for today's sync window.",
            "已连接，但今天的同步窗口内没有找到受支持的 Health Connect 记录。"
        )
        message == "Health Connect sync failed." -> language.t(
            "Health Connect sync failed.",
            "Health Connect 同步失败。"
        )
        message.isBlank() -> language.t(
            "Connect Health Connect to import metrics into the AI review.",
            "连接 Health Connect，把身体数据导入 AI 复盘。"
        )
        else -> message
    }
}

@Composable
private fun HealthConnectCard(
    state: CoachUiState,
    onConnectHealthData: () -> Unit,
    onSyncHealthData: () -> Unit
) {
    val snapshot = state.healthSnapshot
    val language = state.appLanguage
    SectionCard(
        title = language.t("Health Connect", "健康数据连接"),
        subtitle = language.t(
            "After permission, refresh user-authorized body, sleep, and recovery records on app open, then feed them into daily AI review.",
            "授权后，打开 App 时刷新用户授权的身体、睡眠与恢复记录，并纳入每日 AI 复盘。"
        )
    ) {
        MetricGrid(
            metrics = listOf(
                language.t("Status", "状态") to when {
                    snapshot.permissionsGranted -> language.t("Authorized", "已授权")
                    snapshot.available -> language.t("Needs access", "需要授权")
                    else -> language.t("Unavailable", "不可用")
                },
                language.t("Weight", "体重") to formatOptional(snapshot.bodyWeightKg, "kg"),
                language.t("Body fat", "体脂") to formatOptional(snapshot.bodyFatPercent, "%"),
                language.t("Steps", "步数") to (snapshot.steps?.toString() ?: "--"),
                language.t("Sleep", "睡眠") to formatOptional(snapshot.sleepHours, "h"),
                language.t("Resting HR", "静息心率") to formatOptional(snapshot.restingHeartRateBpm, "bpm"),
                language.t("Calories", "消耗热量") to formatOptional(snapshot.totalCaloriesBurnedKcal, "kcal")
            )
        )
        DataChipGrid(
            items = listOf(
                language.t("Unified health permission", "统一健康数据授权"),
                language.t("Auto refresh after permission", "授权后自动刷新"),
                language.t("Body, sleep, steps, HR, calories", "身体、睡眠、步数、心率、消耗"),
                language.t("Manual fallback", "手动补录")
            )
        )
        Text(
            text = snapshot.localizedMessage(language),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = language.t(
                "You only grant permission here. Source apps stay behind the scenes; compatible records appear automatically in the trend chart and AI review.",
                "你只需要在这里授权。来源 App 留在后台；可用记录会自动进入趋势图和 AI 复盘。"
            ),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = onConnectHealthData,
                enabled = !state.isHealthSyncing,
                modifier = Modifier.weight(1f)
            ) {
                Text(language.t("Connect health data", "连接健康数据"))
            }
            ElevatedButton(
                onClick = onSyncHealthData,
                enabled = !state.isHealthSyncing,
                modifier = Modifier.weight(1f)
            ) {
                Text(if (state.isHealthSyncing) language.t("Syncing", "同步中") else language.t("Sync today", "同步今天"))
            }
        }
        if (state.isHealthSyncing) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
private fun PhysiqueMeasurementSummaryCard(summary: PhysiqueMeasurementSummary, language: AppLanguage) {
    SectionCard(
        title = language.t("Physique Measurement Summary", "体型围度摘要"),
        subtitle = language.t(
            "Tape-measure trend, proportion, and symmetry signals for bodybuilding-style physique goals.",
            "用卷尺趋势、比例和对称性信号服务健美式体型目标。"
        )
    ) {
        MetricGrid(
            metrics = listOf(
                language.t("Status", "状态") to summary.statusLabel,
                language.t("Score", "评分") to summary.measurementScore.toString(),
                language.t("Shoulder/Waist", "肩腰比") to (summary.shoulderWaistRatio?.let { formatDecimal(it) } ?: "--"),
                language.t("Arm diff", "手臂差") to (summary.armDifferenceCm?.let { "${formatDecimal(it)} cm" } ?: "--"),
                language.t("Thigh diff", "大腿差") to (summary.thighDifferenceCm?.let { "${formatDecimal(it)} cm" } ?: "--"),
                language.t("Waist change", "腰围变化") to (summary.waistChangeCm?.let { "${formatSigned(it)} cm" } ?: "--")
            )
        )
        Text(
            text = language.t("Proportion cue: ${summary.proportionCue}", "比例提示：${summary.proportionCue}"),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = language.t("Symmetry cue: ${summary.symmetryCue}", "对称提示：${summary.symmetryCue}"),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = language.t("Tracking cue: ${summary.trackingCue}", "记录提示：${summary.trackingCue}"),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        DataChipGrid(
            items = listOf(
                language.t("waist change", "腰围变化"),
                language.t("chest change", "胸围变化"),
                language.t("shoulder change", "肩围变化"),
                language.t("hip change", "臀围变化"),
                language.t("left/right arm", "左右手臂"),
                language.t("left/right thigh", "左右大腿"),
                language.t("V-taper direction", "倒三角方向")
            )
        )
    }
}

@Composable
private fun PhotoEvidenceCard(
    photoEvidence: List<PhotoEvidenceEntry>,
    language: AppLanguage = AppLanguage.ENGLISH,
    compact: Boolean = false
) {
    SectionCard(
        title = language.t("Photo Evidence", "照片证据"),
        subtitle = language.t(
            "Classified photos tell AI whether an image is food, form, equipment, label, or physique progress.",
            "已分类照片会告诉 AI：这张图是食物、动作、器械、标签还是体型进度。"
        )
    ) {
        if (photoEvidence.isEmpty()) {
            EmptyState(
                language.t(
                    "No photo evidence saved today. Add meal, form, equipment, label, or progress photos when the data is uncertain.",
                    "今天还没有照片证据。数据不确定时添加餐食、动作、器械、标签或体型照片。"
                )
            )
        } else {
            val counts = PhotoEvidenceType.entries.map { type ->
                "${type.localizedLabel(language)}: ${photoEvidence.count { it.type == type }}"
            }.filterNot { it.endsWith(": 0") }
            DataChipGrid(items = counts)
            if (!compact) {
                photoEvidence.takeLast(8).asReversed().forEach { photo ->
                    Text(
                        text = "${photo.type.localizedLabel(language)} | ${photo.name} | ${
                            photo.createdAt.ifBlank { language.t("time not logged", "未记录时间") }
                        }",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = photo.note.ifBlank { language.t("No note.", "没有备注。") },
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                Text(
                    text = language.t(
                        "${photoEvidence.size} photo evidence item(s) logged today. Latest: ${photoEvidence.last().type.label} (${photoEvidence.last().name}).",
                        "今天已记录 ${photoEvidence.size} 条照片证据。最新：${photoEvidence.last().type.localizedLabel(language)}（${photoEvidence.last().name}）。"
                    ),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
@OptIn(ExperimentalLayoutApi::class)
private fun AiReviewFlowCoachCard(
    state: CoachUiState,
    setup: AiSetupStatus,
    executionPlan: DailyExecutionPlan,
    reviewQueue: AiReviewActionQueue,
    showDetails: Boolean,
    onToggleDetails: () -> Unit,
    onPickImages: () -> Unit,
    onRunAnalysis: () -> Unit,
    onDailyReview: () -> Unit,
    onOpenPlan: () -> Unit,
    onOpenTraining: () -> Unit,
    onOpenNutrition: () -> Unit,
    onOpenMetrics: () -> Unit,
    onOpenAi: () -> Unit
) {
    val language = state.appLanguage
    val log = state.dailyLog
    val plannedSets = log.plannedHardSets()
    val completedSets = log.completedHardSets()
    val hasReviewToday = state.reviewHistory.any { it.logDate == log.date }
    val metricsReady = log.metrics.bodyWeightKg != null ||
        log.metrics.sleepHours != null ||
        log.metrics.steps > 0 ||
        log.metrics.healthSyncedAt.isNotBlank()
    val primaryTitle: String
    val primaryDetail: String
    val primaryLabel: String
    val primaryEnabled: Boolean
    val primaryAction: () -> Unit
    val routeAction: () -> Unit = when (executionPlan.primaryActionRoute) {
        DailyExecutionRoute.PLAN -> onOpenPlan
        DailyExecutionRoute.TRAINING -> onOpenTraining
        DailyExecutionRoute.NUTRITION -> onOpenNutrition
        DailyExecutionRoute.METRICS -> onOpenMetrics
        DailyExecutionRoute.AI_REVIEW -> if (executionPlan.primaryActionLabel == "View review") onOpenAi else onDailyReview
    }
    val queuePrimaryAction: () -> Unit = {
        performAiReviewAction(
            action = reviewQueue.primaryAction,
            onDailyReview = onDailyReview,
            onOpenPlan = onOpenPlan,
            onOpenTraining = onOpenTraining,
            onOpenNutrition = onOpenNutrition,
            onOpenMetrics = onOpenMetrics,
            onOpenAi = onOpenAi
        )
    }
    when {
        state.isLoading -> {
            primaryTitle = language.t("Reviewing today's evidence", "正在复盘今天证据")
            primaryDetail = language.t(
                "AI is reading training, nutrition, body metrics, photos, and recent trends. Keep this screen open until the review is saved.",
                "AI 正在读取训练、饮食、身体数据、照片和近期趋势。保持当前页面，直到复盘保存完成。"
            )
            primaryLabel = language.t("Reviewing", "复盘中")
            primaryEnabled = false
            primaryAction = {}
        }
        !setup.canRunAi -> {
            primaryTitle = language.t("Set API first", "先设置 API")
            primaryDetail = setup.detail
            primaryLabel = language.t("Open AI setup", "打开 AI 设置")
            primaryEnabled = true
            primaryAction = onToggleDetails
        }
        executionPlan.primaryActionRoute != DailyExecutionRoute.AI_REVIEW -> {
            primaryTitle = language.t("Prepare evidence first", "先补齐复盘证据")
            primaryDetail = executionPlan.nextBestAction
            primaryLabel = executionPlan.primaryActionLabel
            primaryEnabled = true
            primaryAction = routeAction
        }
        hasReviewToday -> {
            primaryTitle = language.t("Today's review is saved", "今天复盘已保存")
            primaryDetail = language.t(
                "Use the action queue to turn the saved review into tomorrow's training, food, recovery, tracking, or plan step.",
                "用行动队列把已保存复盘转成明天的训练、饮食、恢复、追踪或计划动作。"
            )
            primaryLabel = reviewQueue.primaryAction.actionLabel
            primaryEnabled = true
            primaryAction = queuePrimaryAction
        }
        else -> {
            primaryTitle = language.t("Run daily AI review", "运行每日 AI 复盘")
            primaryDetail = language.t(
                "Send the athlete profile, plan, set logs, meals, metrics, photos, and trend context to decide tomorrow's priorities.",
                "发送训练者档案、计划、组数记录、餐食、身体数据、照片和趋势上下文，用来决定明天优先级。"
            )
            primaryLabel = language.t("Run daily review", "运行每日复盘")
            primaryEnabled = true
            primaryAction = onDailyReview
        }
    }
    SectionCard(
        title = language.t("AI Review Flow Coach", "AI 复盘流程教练"),
        subtitle = language.t(
            "One-tap review when evidence is ready; setup, photos, prompt, data map, and history stay underneath.",
            "证据就绪后一键复盘；设置、照片、提示词、数据地图和历史记录放在下方。"
        )
    ) {
        Text(
            text = language.t("NEXT AI REVIEW ACTION", "下一步 AI 复盘动作"),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = primaryTitle,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = primaryDetail,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Button(onClick = primaryAction, enabled = primaryEnabled && !state.isLoading, modifier = Modifier.fillMaxWidth()) {
            Text(primaryLabel)
        }
        TextButton(onClick = onToggleDetails, modifier = Modifier.fillMaxWidth()) {
            Text(
                if (showDetails) {
                    language.t("Hide AI details", "收起 AI 细节")
                } else {
                    language.t("Show AI details", "展开 AI 细节")
                }
            )
        }
        if (showDetails) {
            MetricGrid(
                metrics = listOf(
                    language.t("Status", "状态") to if (setup.canRunAi) executionPlan.statusLabel else setup.statusLabel,
                    language.t("Readiness", "状态分") to executionPlan.readinessScore.toString(),
                    language.t("Sets", "组数") to "$completedSets/$plannedSets",
                    language.t("Meals", "餐数") to log.meals.size.toString(),
                    language.t("Metrics", "数据") to if (metricsReady) language.t("Ready", "可用") else language.t("Missing", "缺失"),
                    language.t("Photos", "照片") to "${state.images.size}/${log.photoEvidence.size}",
                    language.t("Review", "复盘") to if (hasReviewToday) language.t("Saved", "已保存") else language.t("Not yet", "未完成"),
                    language.t("Queue", "队列") to reviewQueue.confidenceLabel
                )
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                TextButton(onClick = onPickImages, modifier = Modifier.weight(1f)) {
                    Text(language.t("Add photos", "添加照片"))
                }
                TextButton(
                    onClick = onRunAnalysis,
                    enabled = setup.canRunAi && !state.isLoading,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(language.t("Run mode", "模式分析"))
                }
            }
            DataChipGrid(
                items = listOf(
                    language.t("AI Review Flow Coach", "AI 复盘流程教练"),
                    language.t("Daily review evidence bundle", "每日复盘证据包"),
                    language.t("Training, food, metrics, photos, trends", "训练、饮食、身体数据、照片、趋势"),
                    language.t("Saved review becomes action queue", "已保存复盘转成行动队列"),
                    language.t("AI detail layers open", "AI 细节层已展开")
                )
            )
        } else {
            Text(
                text = language.t("AI detail layers hidden", "AI 细节层已收起"),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        state.reviewHistory.firstOrNull()?.let { review ->
            HorizontalDivider()
            Text(
                text = language.t("Latest saved guidance", "最新保存建议"),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = review.preview(),
                style = MaterialTheme.typography.bodySmall
            )
        }
        if (state.isLoading) {
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
    onPreparePhoto: (PhotoEvidenceType, String) -> Unit,
    onPickImages: () -> Unit,
    onClearImages: () -> Unit,
    onRunAnalysis: () -> Unit,
    onDailyReview: () -> Unit,
    onOpenPlan: () -> Unit,
    onOpenTraining: () -> Unit,
    onOpenNutrition: () -> Unit,
    onOpenMetrics: () -> Unit,
    onOpenAi: () -> Unit
) {
    var photoType by remember { mutableStateOf(state.pendingPhotoType) }
    var photoNote by remember { mutableStateOf(state.pendingPhotoNote) }
    val reviewQueue = aiReviewActionQueue(
        latestReview = state.reviewHistory.firstOrNull(),
        log = state.dailyLog
    )
    val language = state.appLanguage
    val aiSetup = state.aiSetupStatus(language)
    val hasAiReviewToday = state.reviewHistory.any { it.logDate == state.dailyLog.date }
    val executionPlan = dailyExecutionPlan(
        log = state.dailyLog,
        recentLogs = state.recentLogs,
        profile = state.athleteProfile,
        hasWeeklyPlan = state.trainingPlan.days.any { it.exercises.isNotEmpty() },
        hasAiReviewToday = hasAiReviewToday
    )
    val integratedDecision = aiIntegratedDecisionMatrix(
        log = state.dailyLog,
        recentLogs = state.recentLogs,
        profile = state.athleteProfile,
        plan = state.trainingPlan
    )
    val tomorrowBrief = tomorrowCoachBrief(
        log = state.dailyLog,
        recentLogs = state.recentLogs,
        profile = state.athleteProfile,
        plan = state.trainingPlan
    )
    val autopilotTasks = state.dailyCoachTasks(
        language = language,
        onOpenPlan = onOpenPlan,
        onOpenTraining = onOpenTraining,
        onOpenNutrition = onOpenNutrition,
        onOpenMetrics = onOpenMetrics,
        onRunAiReview = onDailyReview,
        onOpenAi = onOpenAi
    )
    var showAiDetails by remember { mutableStateOf(false) }
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        AiReviewFlowCoachCard(
            state = state,
            setup = aiSetup,
            executionPlan = executionPlan,
            reviewQueue = reviewQueue,
            showDetails = showAiDetails,
            onToggleDetails = { showAiDetails = !showAiDetails },
            onPickImages = onPickImages,
            onRunAnalysis = onRunAnalysis,
            onDailyReview = onDailyReview,
            onOpenPlan = onOpenPlan,
            onOpenTraining = onOpenTraining,
            onOpenNutrition = onOpenNutrition,
            onOpenMetrics = onOpenMetrics,
            onOpenAi = onOpenAi
        )
        DailyAutopilotCard(tasks = autopilotTasks, language = language)
        ReviewReadinessChecklistCard(
            state = state,
            setup = aiSetup,
            onOpenTraining = onOpenTraining,
            onOpenNutrition = onOpenNutrition,
            onOpenMetrics = onOpenMetrics,
            onOpenAi = onOpenAi,
            onPickImages = onPickImages,
            onDailyReview = onDailyReview
        )
        AiChangeReceiptCard(
            reviewQueue = reviewQueue,
            decision = integratedDecision,
            tomorrowBrief = tomorrowBrief,
            hasAiReviewToday = hasAiReviewToday,
            language = language,
            onOpenPlan = onOpenPlan,
            onOpenTraining = onOpenTraining,
            onOpenNutrition = onOpenNutrition,
            onOpenMetrics = onOpenMetrics,
            onOpenAi = onOpenAi,
            onDailyReview = onDailyReview
        )
        NextDayHandoffCard(
            brief = tomorrowBrief,
            language = language,
            onOpenPlan = onOpenPlan,
            onOpenTraining = onOpenTraining,
            onOpenNutrition = onOpenNutrition,
            onOpenMetrics = onOpenMetrics
        )
        SettingsCard(settings = state.settings, language = language, onChange = onSettingsChange)
        if (showAiDetails) {
            AiIntegratedDecisionMatrixCard(
                decision = integratedDecision,
                language = language,
                onOpenPlan = onOpenPlan,
                onOpenTraining = onOpenTraining,
                onOpenNutrition = onOpenNutrition,
                onOpenMetrics = onOpenMetrics,
                onDailyReview = onDailyReview
            )
        AiSetupStatusCard(
            status = aiSetup,
            language = language,
            isLoading = state.isLoading,
            primaryLabel = aiSetup.primaryActionLabel,
            primaryEnabled = aiSetup.canRunAi,
            onPrimaryAction = onDailyReview,
            secondaryLabel = language.t("Run mode", "运行模式"),
            onSecondaryAction = if (aiSetup.canRunAi) onRunAnalysis else null
        )
        SectionCard(
            title = language.t("AI Coach Details", "AI 教练细节"),
            subtitle = language.t(
                "Use the bundled skill, daily logs, and optional photos together.",
                "把内置 skill、每日记录和可选照片一起用于分析。"
            )
        ) {
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
                label = {
                    Text(
                        language.t(
                            "Question, goal, or extra AI review instruction",
                            "问题、目标或额外 AI 复盘指令"
                        )
                    )
                },
                minLines = 5
            )
            Text(language.t("Photo purpose", "照片用途"), fontWeight = FontWeight.SemiBold)
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                PhotoEvidenceType.entries.forEach { type ->
                    FilterChip(
                        selected = photoType == type,
                        onClick = {
                            photoType = type
                            onPreparePhoto(type, photoNote)
                        },
                        label = { Text(type.localizedLabel(language)) }
                    )
                }
            }
            OutlinedTextField(
                value = photoNote,
                onValueChange = {
                    photoNote = it
                    onPreparePhoto(photoType, it)
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(
                        language.t(
                            "Photo note: meal, set, angle, lighting, label, uncertainty",
                            "照片备注：餐食、组数、角度、光线、标签、不确定点"
                        )
                    )
                },
                minLines = 2
            )
            ImageCard(images = state.images, language = language, onPick = onPickImages, onClear = onClearImages)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = onDailyReview, enabled = !state.isLoading && aiSetup.canRunAi, modifier = Modifier.weight(1f)) {
                    Text(language.t("Daily review", "每日复盘"))
                }
                ElevatedButton(onClick = onRunAnalysis, enabled = !state.isLoading && aiSetup.canRunAi, modifier = Modifier.weight(1f)) {
                    Text(language.t("Run mode", "模式分析"))
                }
            }
            if (state.isLoading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }
        }
        PhotoEvidenceCard(photoEvidence = state.dailyLog.photoEvidence, language = language)
        AiReviewActionQueueCard(
            queue = reviewQueue,
            isLoading = state.isLoading,
            language = state.appLanguage,
            onDailyReview = onDailyReview,
            onOpenPlan = onOpenPlan,
            onOpenTraining = onOpenTraining,
            onOpenNutrition = onOpenNutrition,
            onOpenMetrics = onOpenMetrics,
            onOpenAi = onOpenAi
        )
        ReviewHistoryCard(reviews = state.reviewHistory, language = language)
        SectionCard(
            title = language.t("AI Data Map", "AI 数据地图"),
            subtitle = language.t(
                "These app records are designed to be linked in the same analysis request.",
                "这些 App 记录会在同一个分析请求中联动使用。"
            )
        ) {
            DataChipGrid(
                items = listOf(
                    "Exercise selection",
                    "Set load",
                    "Actual reps",
                    "RIR/RPE intent",
                    "Rest time",
                    "Hard sets",
                    "Tonnage",
                    "Daily Execution Plan",
                    "AI Integrated Decision Matrix",
                    "all data linked",
                    "training effect",
                    "Split decision",
                    "3-day / 4-day / 5-day split decision",
                    "data confidence",
                    "do not change split until",
                    "Priority focus",
                    "Primary action",
                    "Data quality gate",
                    "AI review gate",
                    "Plan adjustment signal",
                    "Training Readiness Builder",
                    "Warm-up Ramp Plan",
                    "Next Set Coach",
                    "Tomorrow Coach Brief",
                    "Tomorrow training focus",
                    "Tomorrow nutrition target",
                    "Tomorrow recovery gate",
                    "Tomorrow tracking action",
                    "Plan tomorrow",
                    "Readiness gate",
                    "Current exercise",
                    "Next set target",
                    "Load cue",
                    "Reps cue",
                    "RIR cue",
                    "Rest cue",
                    "Stop cue",
                    "After-set logging cue",
                    "Session Quality Dashboard",
                    "Completion rate",
                    "Logged set rate",
                    "Average RIR",
                    "Muscle volume distribution",
                    "Pain flags",
                    "Technique flags",
                    "Warm-up cue",
                    "Ramp-up cue",
                    "Ramp set checklist",
                    "planned load percentage",
                    "final ramp set quality",
                    "first working set gate",
                    "First working set",
                    "Volume adjustment",
                    "Stop rule",
                    "Progression Cue",
                    "Exercise History",
                    "Exercise Substitution Coach",
                    "equipment unavailable",
                    "trigger reason",
                    "primaryOption",
                    "secondaryOptions",
                    "same target muscle",
                    "same movement pattern",
                    "preserve rep range",
                    "fatigue cost",
                    "keepIntentCue",
                    "loadAdjustmentCue",
                    "Exercise visual guide",
                    "Unified Exercise Visual Atlas",
                    "Visual guide ID",
                    "Equipment/action diagrams",
                    "Three-step recognition",
                    "Simplified instance diagram",
                    "Find real equipment",
                    "Movement path matching",
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
                    "Conditioning & Hydration",
                    "step goal",
                    "cardio minutes",
                    "cardio type",
                    "cardio intensity",
                    "water liters",
                    "sodium mg",
                    "caffeine mg",
                    "alcohol servings",
                    "digestion notes",
                    "scale-weight noise",
                    "Next Meal Builder",
                    "Meal Assembly Guide",
                    "Plate structure",
                    "Protein anchor",
                    "Carb anchor",
                    "Fat control",
                    "Fiber/micros",
                    "Shopping cue",
                    "Prep cue",
                    "Photo/logging cue",
                    "Next meal macro targets",
                    "Meal timing cue",
                    "Portion uncertainty cue",
                    "Body Composition Guidance",
                    "Physique Measurement Summary",
                    "waistCm",
                    "chestCm",
                    "shoulderCm",
                    "hipCm",
                    "leftArmCm",
                    "rightArmCm",
                    "leftThighCm",
                    "rightThighCm",
                    "neckCm",
                    "shoulder-to-waist ratio",
                    "arm symmetry",
                    "thigh symmetry",
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
}

@Composable
private fun ReviewHistoryCard(reviews: List<AiReviewEntry>, language: AppLanguage) {
    SectionCard(
        title = language.t("Saved AI Reviews", "已保存 AI 复盘"),
        subtitle = language.t(
            "Recent guidance stays available after you close the app.",
            "关闭 App 后，最近的教练建议仍会保留。"
        )
    ) {
        if (reviews.isEmpty()) {
            EmptyState(language.t("Run a daily review to save the first AI coaching note.", "运行每日复盘后，会保存第一条 AI 教练建议。"))
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
private fun SettingsCard(settings: AiSettings, language: AppLanguage, onChange: (AiSettings) -> Unit) {
    SectionCard(
        title = language.t("API Settings", "API 设置"),
        subtitle = language.t(
            "Bring your own AI provider key; credentials stay in local SharedPreferences.",
            "使用你自己的 AI provider key；凭证只保存在本地 SharedPreferences。"
        )
    ) {
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
private fun ImageCard(
    images: List<ImageAttachment>,
    language: AppLanguage,
    onPick: () -> Unit,
    onClear: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                ElevatedButton(onClick = onPick) {
                    Text(language.t("Add photos", "添加照片"))
                }
                TextButton(onClick = onClear, enabled = images.isNotEmpty()) {
                    Text(language.t("Clear", "清空"))
                }
            }
            if (images.isEmpty()) {
                Text(
                    text = language.t(
                        "Attach exercise frames, equipment photos, food photos, labels, menus, or progress photos.",
                        "可添加动作帧、器械照片、食物照片、标签、菜单或体型进度照片。"
                    ),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                images.forEach { image ->
                    Text(
                        "${image.evidenceType.localizedLabel(language)}: ${image.name} (${image.mimeType})",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.SemiBold
                    )
                    if (image.note.isNotBlank()) {
                        Text(
                            image.note,
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
        colors = CardDefaults.cardColors(containerColor = IfbbProGlassSurface),
        border = BorderStroke(1.dp, IfbbProGlassBorder)
    ) {
        // Apple glass surface: translucent outer material with stronger inner controls for readability.
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
                color = IfbbProGlassStrongSurface
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
                color = IfbbProGlassStrongSurface
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
private fun SafetyNote(language: AppLanguage) {
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = language.t(
            "This app is coaching support, not medical care. Photo form checks and food estimates are approximate.",
            "本 App 提供训练与营养教练辅助，不替代医疗建议。照片动作检查和食物估算都可能存在误差。"
        ),
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

private fun formatRemainingLocalized(value: Int, unit: String, language: AppLanguage): String {
    return if (language == AppLanguage.CHINESE) {
        when {
            value > 0 -> "剩余 $value $unit"
            value < 0 -> "超出 ${kotlin.math.abs(value)} $unit"
            else -> "已达标"
        }
    } else {
        formatRemaining(value, unit)
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

