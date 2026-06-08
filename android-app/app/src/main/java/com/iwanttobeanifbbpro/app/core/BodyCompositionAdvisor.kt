package com.iwanttobeanifbbpro.app.core

import com.iwanttobeanifbbpro.app.data.AthleteProfile
import com.iwanttobeanifbbpro.app.data.DailyLog
import java.util.Locale

data class BodyCompositionGuidance(
    val statusLabel: String,
    val phaseGoal: String,
    val weightChangeKg: Double?,
    val averageCalories: Double?,
    val averageProtein: Double?,
    val averageCompletedSets: Double?,
    val calorieAdjustmentKcal: Int,
    val targetCalories: Int,
    val targetProtein: Int,
    val rationale: String,
    val nextAction: String
)

fun bodyCompositionGuidance(
    log: DailyLog,
    recentLogs: List<DailyLog>,
    profile: AthleteProfile
): BodyCompositionGuidance {
    val window = recentLogs.ifEmpty { listOf(log) }.sortedBy { it.date }.takeLast(14)
    val firstWeight = window.firstNotNullOfOrNull { it.metrics.bodyWeightKg }
    val lastWeight = window.asReversed().firstNotNullOfOrNull { it.metrics.bodyWeightKg }
    val weightChange = if (firstWeight != null && lastWeight != null) lastWeight - firstWeight else null
    val nutrition = window.map { it.nutritionTotals() }
    val avgCalories = nutrition.map { it.calories }.takeIf { it.isNotEmpty() }?.map { it.toDouble() }?.average()
    val avgProtein = nutrition.map { it.protein }.takeIf { it.isNotEmpty() }?.map { it.toDouble() }?.average()
    val avgCompletedSets = window.map { it.completedHardSets() }.takeIf { it.isNotEmpty() }?.map { it.toDouble() }?.average()
    val phase = profile.currentPhase.ifBlank { profile.primaryGoal }.lowercase(Locale.US)
    val phaseGoal = when {
        phase.contains("cut") || phase.contains("fat") || phase.contains("loss") || phase.contains("减脂") -> "Fat loss"
        phase.contains("bulk") || phase.contains("gain") || phase.contains("hypertrophy") || phase.contains("增肌") -> "Lean gain"
        phase.contains("maint") || phase.contains("recomp") || phase.contains("维持") || phase.contains("重组") -> "Maintain/Recomp"
        else -> "Physique improvement"
    }
    val enoughTrend = window.size >= 4 && weightChange != null && avgCalories != null
    val adjustment = when {
        !enoughTrend -> 0
        phaseGoal == "Fat loss" && weightChange >= -0.1 -> -150
        phaseGoal == "Fat loss" && weightChange <= -1.0 -> 100
        phaseGoal == "Lean gain" && weightChange <= 0.1 -> 150
        phaseGoal == "Lean gain" && weightChange >= 1.0 -> -100
        phaseGoal == "Maintain/Recomp" && kotlin.math.abs(weightChange) >= 0.8 -> if (weightChange > 0) -100 else 100
        else -> 0
    }
    val statusLabel = when {
        !enoughTrend -> "Need trend data"
        adjustment > 0 -> "Small calorie increase"
        adjustment < 0 -> "Small calorie decrease"
        else -> "Hold targets"
    }
    val targetCalories = (log.targets.calories + adjustment).coerceAtLeast(1200)
    val targetProtein = log.targets.protein
    val rationale = when {
        !enoughTrend -> "Log body weight and food for at least four days before changing targets."
        adjustment > 0 -> "Recent weight trend is below the phase target, so a small increase is safer than a large jump."
        adjustment < 0 -> "Recent weight trend is above the phase target, so a small decrease is enough before reassessing."
        else -> "Recent weight, calories, protein, and training trend do not justify changing targets yet."
    }
    val nextAction = when {
        !enoughTrend -> "Keep current targets, weigh in consistently, and complete meal logs."
        avgProtein != null && avgProtein < log.targets.protein * 0.85 -> "Fix protein consistency before changing calories aggressively."
        avgCompletedSets != null && avgCompletedSets < 8 -> "Confirm training output before using scale trend alone."
        adjustment == 0 -> "Hold calories for another check-in and reassess with the next trend window."
        adjustment > 0 -> "Add mostly carbs around training or lean protein if protein is behind."
        else -> "Remove calories from low-satiety fats/snacks before cutting training carbs."
    }
    return BodyCompositionGuidance(
        statusLabel = statusLabel,
        phaseGoal = phaseGoal,
        weightChangeKg = weightChange,
        averageCalories = avgCalories,
        averageProtein = avgProtein,
        averageCompletedSets = avgCompletedSets,
        calorieAdjustmentKcal = adjustment,
        targetCalories = targetCalories,
        targetProtein = targetProtein,
        rationale = rationale,
        nextAction = nextAction
    )
}
