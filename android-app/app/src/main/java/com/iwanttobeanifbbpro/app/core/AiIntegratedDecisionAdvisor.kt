package com.iwanttobeanifbbpro.app.core

import com.iwanttobeanifbbpro.app.data.AthleteProfile
import com.iwanttobeanifbbpro.app.data.DailyLog
import com.iwanttobeanifbbpro.app.data.WeeklyTrainingPlan

data class AiIntegratedDecisionMatrix(
    val statusLabel: String,
    val trainingEffectVerdict: String,
    val splitDecision: String,
    val currentSplit: String,
    val recommendedSplit: String,
    val nutritionLever: String,
    val recoveryLever: String,
    val dataConfidencePercent: Int,
    val dataConfidenceLabel: String,
    val allowedChangesNow: String,
    val holdUntil: String,
    val linkedEvidence: List<String>,
    val nextAction: String
) {
    fun promptLine(): String {
        return "AI Integrated Decision Matrix: $statusLabel | training effect: $trainingEffectVerdict | Split decision: $splitDecision | 3-day / 4-day / 5-day split decision | current split $currentSplit | recommended split $recommendedSplit | Nutrition lever: $nutritionLever | Recovery lever: $recoveryLever | data confidence $dataConfidencePercent% ($dataConfidenceLabel) | allowed changes now: $allowedChangesNow | do not change split until: $holdUntil | all data linked evidence: ${linkedEvidence.joinToString("; ")} | next action: $nextAction"
    }
}

fun aiIntegratedDecisionMatrix(
    log: DailyLog,
    recentLogs: List<DailyLog>,
    profile: AthleteProfile,
    plan: WeeklyTrainingPlan
): AiIntegratedDecisionMatrix {
    val window = recentLogs.ifEmpty { listOf(log) }.sortedBy { it.date }.takeLast(14)
    val sessionQuality = sessionQualityDashboard(log)
    val recovery = recoveryGuidance(log, window)
    val body = bodyCompositionGuidance(log, window, profile)
    val weekly = weeklyCheckInSummary(log, window, profile, plan)
    val execution = dailyExecutionPlan(
        log = log,
        recentLogs = window,
        profile = profile,
        hasWeeklyPlan = plan.days.any { it.exercises.isNotEmpty() }
    )
    val currentSplitDays = plan.days.count { it.exercises.isNotEmpty() }
    val currentSplit = when (currentSplitDays) {
        0 -> "No loaded weekly split"
        1, 2 -> "$currentSplitDays-day minimal split"
        3 -> "3-day full-body or rotating split"
        4 -> "4-day upper/lower hypertrophy split"
        else -> "5-day physique priority split"
    }
    val desiredDays = profile.weeklyTrainingDays.coerceIn(3, 5)
    val recommendedSplit = when (desiredDays) {
        3 -> "3-day full-body foundation"
        4 -> "4-day upper/lower hypertrophy"
        else -> "5-day physique priority"
    }
    val enoughData = weekly.daysLogged >= 4 &&
        weekly.averageCalories != null &&
        weekly.weightChangeKg != null &&
        sessionQuality.completedSets > 0
    val confidence = buildConfidence(
        weekly = weekly,
        sessionQuality = sessionQuality,
        hasSleep = window.any { it.metrics.sleepHours != null },
        hasPhotos = window.any { it.photoEvidence.isNotEmpty() }
    )
    val confidenceLabel = when {
        confidence >= 82 -> "High confidence"
        confidence >= 62 -> "Medium confidence"
        else -> "Low confidence"
    }
    val trainingEffectVerdict = when {
        sessionQuality.plannedSets == 0 -> "Training effect cannot be judged because no executable workout is loaded."
        sessionQuality.painFlagCount > 0 -> "Training effect is limited by pain risk; protect movement quality before chasing volume."
        sessionQuality.completionRatePercent < 70 -> "Training stimulus is inconsistent; the plan is probably too complex or poorly timed."
        sessionQuality.loggedSetRatePercent < 70 -> "Training happened, but progression confidence is low because set data is under-logged."
        recovery.statusLabel == "Deload check" -> "Training stress is exceeding recovery; adaptation is less likely until readiness improves."
        sessionQuality.qualityScore >= 86 -> "Training effect is productive; use exercise-specific progression rather than a full split change."
        else -> "Training effect is usable; keep changes conservative and tied to Exercise History."
    }
    val splitDecision = when {
        currentSplitDays == 0 -> "Create the $recommendedSplit before judging training effect."
        !enoughData -> "Hold the current split. Do not change split until at least 4 logged days include sets, food, body weight, and sleep or recovery data."
        recovery.statusLabel == "Deload check" || recovery.statusLabel == "Reduce volume" -> {
            if (currentSplitDays > 3) {
                "Switch down temporarily to a 3-day controlled split or reduce optional sets until recovery normalizes."
            } else {
                "Keep the 3-day split but reduce optional hard sets and avoid failure work."
            }
        }
        weekly.trainingCompletionPercent < 70 -> {
            if (currentSplitDays > 3) {
                "Switch down to a simpler 3-day split until completion is consistently above 80%."
            } else {
                "Keep 3-day frequency and simplify exercise count before adding days."
            }
        }
        currentSplitDays < desiredDays && weekly.trainingCompletionPercent >= 88 && (weekly.recoveryScoreAverage ?: 0) >= 72 -> {
            "Progress toward the $recommendedSplit because execution and recovery support more weekly structure."
        }
        currentSplitDays > desiredDays -> "Move toward the $recommendedSplit because the user's available days do not support the current density."
        else -> "Hold the current split; change exercise progression, substitutions, nutrition, or recovery before changing weekly structure."
    }
    val nutritionLever = when {
        weekly.averageProtein != null && weekly.averageProtein < log.targets.protein * 0.85 -> "Fix protein consistency before changing calories or adding training days."
        body.calorieAdjustmentKcal > 0 -> "Increase calories slightly, mostly carbs around training, if food logs are reliable."
        body.calorieAdjustmentKcal < 0 -> "Decrease calories slightly from low-satiety extras before reducing training fuel."
        log.meals.isEmpty() -> "Add meal/photo logging so food intake can be compared with training demand."
        else -> "Hold calories and keep meal timing, protein, fiber, and photo logging consistent."
    }
    val recoveryLever = when {
        recovery.statusLabel == "Controlled push" -> "Recovery supports normal training; progress only clean exercises."
        recovery.statusLabel == "Hold training stress" -> "Keep load and sets stable; avoid bonus volume."
        recovery.statusLabel == "Reduce volume" -> "Trim optional hard sets and prioritize sleep, hydration, and lower-risk machines."
        else -> "Use a deload-style session, rest day, or technique-only work until sleep, soreness, stress, and HR improve."
    }
    val allowedChanges = when {
        confidence < 55 -> "Only load today's plan, log missing sets/meals/metrics, and use safer exercise substitutions."
        recovery.statusLabel == "Reduce volume" || recovery.statusLabel == "Deload check" -> "Reduce optional sets, swap risky lifts, and protect recovery."
        body.calorieAdjustmentKcal != 0 -> "Adjust calories by ${body.calorieAdjustmentKcal} kcal and keep split stable."
        weekly.trainingCompletionPercent >= 88 -> "Use exercise-specific progression and small weak-point emphasis; keep split stable unless the split decision says otherwise."
        else -> "Keep split stable; adjust order, rest, substitutions, and next-meal targets."
    }
    val holdUntil = when {
        weekly.daysLogged < 4 -> "4 complete logged days are available."
        weekly.averageCalories == null -> "food logs or food photos cover at least four days."
        weekly.weightChangeKg == null -> "body weight is synced or entered across the trend window."
        window.none { it.metrics.sleepHours != null } -> "sleep or recovery data is available from Health Connect or manual entry."
        sessionQuality.loggedSetRatePercent < 70 -> "completed sets include load, reps, and RIR."
        else -> "weekly completion, recovery average, and body trend support the change."
    }
    val linkedEvidence = listOf(
        "Training execution ${sessionQuality.completedSets}/${sessionQuality.plannedSets} sets, quality ${sessionQuality.qualityScore}",
        "Weekly completion ${weekly.trainingCompletionPercent}% across ${weekly.daysLogged} logged days",
        "Nutrition avg ${weekly.averageCalories?.roundForMatrix() ?: "not enough data"} kcal, protein ${weekly.averageProtein?.roundForMatrix() ?: "not enough data"} g",
        "Body trend ${weekly.weightChangeKg?.roundForMatrix() ?: "not enough data"} kg",
        "Recovery ${recovery.statusLabel}, score ${recovery.readinessScore}, sleep ${recovery.sleepSignal}",
        "Execution gate ${execution.dataQualityGate}",
        "Photos ${window.sumOf { it.photoEvidence.size }} linked evidence item(s)"
    )
    val statusLabel = when {
        confidence < 55 -> "Collect evidence first"
        splitDecision.startsWith("Switch", ignoreCase = true) || splitDecision.startsWith("Move", ignoreCase = true) -> "Split review needed"
        recovery.statusLabel == "Reduce volume" || recovery.statusLabel == "Deload check" -> "Recovery-limited"
        else -> "Hold structure"
    }
    val nextAction = when {
        currentSplitDays == 0 -> "Open Training, choose the $recommendedSplit template, then log the first workout."
        confidence < 55 -> "Complete the missing training, food, body, sleep, and photo evidence before asking AI to rewrite the plan."
        splitDecision.startsWith("Switch", ignoreCase = true) || splitDecision.startsWith("Move", ignoreCase = true) ->
            "Open Training plan and let AI draft the split change with the evidence above."
        else -> "Run daily AI review, keep the split stable, and apply the smallest useful training, nutrition, or recovery lever."
    }
    return AiIntegratedDecisionMatrix(
        statusLabel = statusLabel,
        trainingEffectVerdict = trainingEffectVerdict,
        splitDecision = splitDecision,
        currentSplit = currentSplit,
        recommendedSplit = recommendedSplit,
        nutritionLever = nutritionLever,
        recoveryLever = recoveryLever,
        dataConfidencePercent = confidence,
        dataConfidenceLabel = confidenceLabel,
        allowedChangesNow = allowedChanges,
        holdUntil = holdUntil,
        linkedEvidence = linkedEvidence,
        nextAction = nextAction
    )
}

private fun buildConfidence(
    weekly: WeeklyCheckInSummary,
    sessionQuality: SessionQualityDashboard,
    hasSleep: Boolean,
    hasPhotos: Boolean
): Int {
    var score = 24
    score += (weekly.daysLogged * 6).coerceAtMost(24)
    if (weekly.averageCalories != null) score += 12
    if (weekly.weightChangeKg != null) score += 12
    if (weekly.recoveryScoreAverage != null || hasSleep) score += 12
    if (sessionQuality.completedSets > 0) score += 8
    if (sessionQuality.loggedSetRatePercent >= 70) score += 8
    if (hasPhotos) score += 4
    return score.coerceIn(20, 96)
}

private fun Double.roundForMatrix(): String {
    return if (this % 1.0 == 0.0) {
        toInt().toString()
    } else {
        "%.1f".format(java.util.Locale.US, this)
    }
}
