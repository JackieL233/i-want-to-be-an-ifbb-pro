package com.iwanttobeanifbbpro.app.core

import com.iwanttobeanifbbpro.app.data.AthleteProfile
import com.iwanttobeanifbbpro.app.data.DailyLog
import com.iwanttobeanifbbpro.app.data.WeeklyTrainingPlan
import java.util.Locale

data class WeeklyCheckInSummary(
    val statusLabel: String,
    val daysLogged: Int,
    val trainingCompletionPercent: Int,
    val averageCalories: Double?,
    val averageProtein: Double?,
    val weightChangeKg: Double?,
    val recoveryScoreAverage: Int?,
    val planAdjustment: String,
    val nutritionAdjustment: String,
    val weakPointFocus: String,
    val dataQualityGate: String,
    val nextWeekAction: String,
    val aiReviewFocus: String
) {
    fun promptLine(): String {
        return "Weekly Check-in: $statusLabel | days logged $daysLogged | training completion $trainingCompletionPercent% | average calories ${averageCalories?.roundForWeeklyPrompt() ?: "not enough data"} | average protein ${averageProtein?.roundForWeeklyPrompt() ?: "not enough data"} g | weight change ${weightChangeKg?.roundForWeeklyPrompt() ?: "not enough data"} kg | recovery score average ${recoveryScoreAverage ?: "not enough data"} | Plan adjustment: $planAdjustment | Nutrition adjustment: $nutritionAdjustment | Weak-point focus: $weakPointFocus | Data quality gate: $dataQualityGate | Next week action: $nextWeekAction | AI review focus: $aiReviewFocus"
    }
}

fun weeklyCheckInSummary(
    log: DailyLog,
    recentLogs: List<DailyLog>,
    profile: AthleteProfile,
    plan: WeeklyTrainingPlan
): WeeklyCheckInSummary {
    val window = recentLogs.ifEmpty { listOf(log) }.sortedBy { it.date }.takeLast(14)
    val daysLogged = window.size
    val plannedSets = window.sumOf { it.plannedHardSets() }
    val completedSets = window.sumOf { it.completedHardSets() }
    val completionPercent = if (plannedSets > 0) {
        ((completedSets.toDouble() / plannedSets.toDouble()) * 100).toInt().coerceIn(0, 100)
    } else {
        0
    }
    val nutrition = window.map { it.nutritionTotals() }
    val averageCalories = nutrition.map { it.calories }.takeIf { it.isNotEmpty() }?.map { it.toDouble() }?.average()
    val averageProtein = nutrition.map { it.protein }.takeIf { it.isNotEmpty() }?.map { it.toDouble() }?.average()
    val firstWeight = window.firstNotNullOfOrNull { it.metrics.bodyWeightKg }
    val lastWeight = window.asReversed().firstNotNullOfOrNull { it.metrics.bodyWeightKg }
    val weightChange = if (firstWeight != null && lastWeight != null) lastWeight - firstWeight else null
    val recoveryScores = window.map { recoveryGuidance(it, window).readinessScore }
    val recoveryAverage = recoveryScores.takeIf { it.isNotEmpty() }?.average()?.toInt()
    val hasPlan = plan.days.any { it.exercises.isNotEmpty() }
    val phase = profile.currentPhase.ifBlank { profile.primaryGoal }.lowercase(Locale.US)
    val phaseGoal = when {
        phase.contains("cut") || phase.contains("fat") || phase.contains("loss") || phase.contains("减脂") -> "Fat loss"
        phase.contains("bulk") || phase.contains("gain") || phase.contains("hypertrophy") || phase.contains("增肌") -> "Lean gain"
        phase.contains("maint") || phase.contains("recomp") || phase.contains("维持") || phase.contains("重组") -> "Maintain/Recomp"
        else -> "Physique improvement"
    }
    val dataQualityGate = when {
        daysLogged < 4 -> "Need at least 4 logged days before changing weekly volume or calories."
        plannedSets == 0 && !hasPlan -> "Build or apply a weekly plan before judging training completion."
        averageCalories == null || window.count { it.meals.isNotEmpty() } < 4 -> "Food logging is too thin; fix meal logs before changing targets."
        weightChange == null -> "Body-weight trend is missing; sync Health Connect or enter morning weigh-ins."
        else -> "Enough weekly data for conservative plan and nutrition review."
    }
    val statusLabel = when {
        daysLogged < 4 -> "Build weekly signal"
        recoveryAverage != null && recoveryAverage < 58 -> "Recovery-limited week"
        completionPercent < 70 && plannedSets > 0 -> "Execution gap"
        averageProtein != null && averageProtein < log.targets.protein * 0.85 -> "Protein consistency gap"
        else -> "Ready for weekly adjustment"
    }
    val planAdjustment = when {
        !hasPlan -> "Create a weekly plan before using AI to adjust volume."
        daysLogged < 4 -> "Hold weekly volume; collect more training, food, and metric data."
        recoveryAverage != null && recoveryAverage < 58 -> "Reduce optional hard sets 10-20% next week and avoid failure work until readiness improves."
        completionPercent < 70 && plannedSets > 0 -> "Do not add volume; simplify exercise count or shift sets to days the user can actually finish."
        completionPercent >= 90 && recoveryAverage != null && recoveryAverage >= 72 -> "Keep plan-wide volume stable; allow exercise-specific progression only where Exercise History supports it."
        else -> "Hold weekly hard sets and adjust only exercise order, substitutions, or rest where adherence is weak."
    }
    val nutritionAdjustment = when {
        daysLogged < 4 -> "Hold calories until at least four logged days exist."
        averageProtein != null && averageProtein < log.targets.protein * 0.85 -> "Do not change calories first; make protein easier with templates, earlier meals, or lean anchors."
        phaseGoal == "Fat loss" && weightChange != null && weightChange >= -0.1 -> "Consider a small 100-150 kcal decrease only if food logging quality is solid."
        phaseGoal == "Lean gain" && weightChange != null && weightChange <= 0.1 -> "Consider a small 100-150 kcal increase, mostly carbs around training."
        else -> "Hold calorie target and keep meal timing, protein, fiber, and photo logging consistent."
    }
    val weakPointFocus = profile.weakPoints.ifBlank {
        plan.days
            .flatMap { it.exercises }
            .groupBy { it.targetMuscle.ifBlank { "General hypertrophy" } }
            .maxByOrNull { (_, exercises) -> exercises.sumOf { it.sets } }
            ?.key
            ?: "Set one visible physique priority in Athlete Profile."
    }
    val nextWeekAction = when (statusLabel) {
        "Build weekly signal" -> "Log four complete days: plan applied, sets completed, meals, morning weight, sleep, soreness, and fatigue."
        "Recovery-limited week" -> "Run next week as a controlled week: stable loads, fewer optional sets, better sleep and hydration tracking."
        "Execution gap" -> "Reduce complexity before adding ambition: keep priority lifts, remove low-value extras, and schedule realistic training days."
        "Protein consistency gap" -> "Add a default protein-first breakfast or post-workout meal template before changing calories."
        else -> "Run AI weekly review with this check-in, then change only one lever: volume, exercise progression, calories, or recovery."
    }
    val aiReviewFocus = "Use Weekly Check-in before changing plan-wide volume or calorie targets; prefer one conservative lever unless data quality is strong."

    return WeeklyCheckInSummary(
        statusLabel = statusLabel,
        daysLogged = daysLogged,
        trainingCompletionPercent = completionPercent,
        averageCalories = averageCalories,
        averageProtein = averageProtein,
        weightChangeKg = weightChange,
        recoveryScoreAverage = recoveryAverage,
        planAdjustment = planAdjustment,
        nutritionAdjustment = nutritionAdjustment,
        weakPointFocus = weakPointFocus,
        dataQualityGate = dataQualityGate,
        nextWeekAction = nextWeekAction,
        aiReviewFocus = aiReviewFocus
    )
}

private fun Double.roundForWeeklyPrompt(): String {
    return if (this % 1.0 == 0.0) {
        toInt().toString()
    } else {
        "%.1f".format(Locale.US, this)
    }
}
