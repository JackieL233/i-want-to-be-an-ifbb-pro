package com.iwanttobeanifbbpro.app.core

import com.iwanttobeanifbbpro.app.data.AthleteProfile
import com.iwanttobeanifbbpro.app.data.DailyLog
import java.util.Locale

data class ConditioningHydrationGuidance(
    val statusLabel: String,
    val conditioningScore: Int,
    val stepProgressPercent: Int,
    val cardioStatus: String,
    val hydrationStatus: String,
    val sodiumCue: String,
    val caffeineCue: String,
    val weightFluctuationCue: String,
    val nextAction: String,
    val aiReviewFocus: String
) {
    fun promptLine(): String {
        return "Conditioning & Hydration: $statusLabel, score $conditioningScore | step goal progress $stepProgressPercent% | cardio status: $cardioStatus | hydration status: $hydrationStatus | sodium cue: $sodiumCue | caffeine cue: $caffeineCue | weight fluctuation cue: $weightFluctuationCue | Next action: $nextAction | AI review focus: $aiReviewFocus"
    }
}

fun conditioningHydrationGuidance(
    log: DailyLog,
    recentLogs: List<DailyLog> = emptyList(),
    profile: AthleteProfile = AthleteProfile()
): ConditioningHydrationGuidance {
    val conditioning = log.conditioning
    val stepGoal = conditioning.stepGoal.coerceAtLeast(1)
    val stepProgress = (log.metrics.steps * 100 / stepGoal).coerceIn(0, 180)
    val cardioMinutes = conditioning.cardioMinutes.coerceAtLeast(0)
    val waterLiters = conditioning.waterLiters
    val sodiumMg = conditioning.sodiumMg
    val caffeineMg = conditioning.caffeineMg
    val alcoholServings = conditioning.alcoholServings

    val cardioTarget = when {
        profile.currentPhase.contains("fat", ignoreCase = true) ||
            profile.primaryGoal.contains("cut", ignoreCase = true) ||
            profile.primaryGoal.contains("减脂") -> 25
        profile.currentPhase.contains("maintenance", ignoreCase = true) -> 15
        else -> 10
    }

    val cardioStatus = when {
        cardioMinutes <= 0 && cardioTarget >= 20 -> "No cardio logged; use steps first, then low-intensity cardio if fat-loss pace needs help."
        cardioMinutes < cardioTarget -> "Cardio below today's phase target; keep it easy enough not to steal leg recovery."
        cardioMinutes > 60 -> "High cardio load; watch leg performance, hunger, and sleep before adding more."
        else -> "Cardio is in a useful range for this phase."
    }
    val hydrationStatus = when {
        waterLiters == null -> "Water not logged; AI should treat scale weight and pump notes with lower confidence."
        waterLiters < 2.0 -> "Water is low; improve hydration before judging pump, appetite, or weight spikes."
        waterLiters > 5.5 -> "Very high water intake; check sodium balance and avoid forcing fluids."
        else -> "Water intake is logged and usable for recovery and weight interpretation."
    }
    val sodiumCue = when {
        sodiumMg == null -> "Sodium not logged; note salty restaurant meals or unusual cramping/pump changes."
        sodiumMg < 1800 -> "Sodium may be low for hard training; watch pump, dizziness, and cramping."
        sodiumMg > 5000 -> "High sodium can explain next-day scale weight and thirst without implying fat gain."
        else -> "Sodium looks plausible; compare with water, sweat, and scale trend."
    }
    val caffeineCue = when {
        caffeineMg == null -> "Caffeine not logged; note late stimulants when sleep or resting HR worsens."
        caffeineMg > 450 -> "High caffeine; protect sleep and avoid masking fatigue."
        caffeineMg > 250 -> "Moderate-high caffeine; keep timing early if sleep quality matters."
        else -> "Caffeine is unlikely to be the main recovery limiter."
    }
    val alcoholCue = when {
        alcoholServings == null || alcoholServings <= 0.0 -> ""
        alcoholServings >= 2.0 -> "Alcohol logged; treat recovery, sleep, weight, and hunger as lower-confidence signals."
        else -> "Small alcohol exposure logged; note it if sleep, appetite, or weight changes."
    }
    val latestWeight = log.metrics.bodyWeightKg
    val priorWeight = recentLogs
        .filter { it.date != log.date }
        .sortedBy { it.date }
        .asReversed()
        .firstNotNullOfOrNull { it.metrics.bodyWeightKg }
    val weightDelta = if (latestWeight != null && priorWeight != null) latestWeight - priorWeight else null
    val fluctuationReasons = listOfNotNull(
        if ((sodiumMg ?: 0) > 4500) "high sodium" else null,
        if ((waterLiters ?: 0.0) < 2.0) "low water" else null,
        if ((alcoholServings ?: 0.0) > 0.0) "alcohol exposure" else null,
        if (conditioning.digestion.isNotBlank()) "digestion: ${conditioning.digestion}" else null,
        if (cardioMinutes > 60) "high cardio load" else null
    )
    val weightFluctuationCue = when {
        weightDelta == null -> "Not enough weight trend data; do not change calories from one scale point."
        kotlin.math.abs(weightDelta) < 0.4 -> "Scale is stable day to day; use 7-14 day trend before changing calories."
        fluctuationReasons.isNotEmpty() -> "Scale changed ${weightDelta.formatConditioningDecimal()} kg; possible confounders: ${fluctuationReasons.joinToString(", ")}."
        else -> "Scale changed ${weightDelta.formatConditioningDecimal()} kg without an obvious water/sodium/cardio note; keep watching trend."
    }

    val score = (
        48 +
            stepProgress.coerceAtMost(100) / 4 +
            if (cardioMinutes >= cardioTarget) 10 else 0 +
            when {
                waterLiters == null -> 0
                waterLiters in 2.0..5.5 -> 10
                else -> -4
            } +
            when {
                caffeineMg == null -> 0
                caffeineMg > 450 -> -8
                else -> 4
            } +
            when {
                alcoholServings == null -> 0
                alcoholServings > 0.0 -> -8
                else -> 4
            }
        ).coerceIn(0, 100)
    val statusLabel = when {
        score >= 84 -> "Cut/recomp support ready"
        stepProgress < 70 -> "NEAT behind"
        waterLiters == null || waterLiters < 2.0 -> "Hydration gap"
        caffeineMg != null && caffeineMg > 450 -> "Stimulant pressure"
        alcoholServings != null && alcoholServings > 0.0 -> "Recovery confounder"
        cardioMinutes < cardioTarget -> "Cardio optional"
        else -> "Support variables logged"
    }
    val nextAction = when (statusLabel) {
        "NEAT behind" -> "Walk enough to reach at least 80% of step goal before adding more calorie cuts."
        "Hydration gap" -> "Log water and bring hydration into range before judging pump or scale changes."
        "Stimulant pressure" -> "Cap caffeine earlier tomorrow and compare sleep/resting HR before pushing volume."
        "Recovery confounder" -> "Treat today's recovery and weight as confounded; hold plan changes unless trend agrees."
        "Cardio optional" -> "Add short easy cardio only if fat-loss pace or appetite control needs it."
        else -> "Keep steps, cardio, water, sodium, caffeine, and digestion notes consistent for cleaner AI review."
    }
    val aiReviewFocus = "Use Conditioning & Hydration to separate true calorie/training needs from NEAT, cardio, water, sodium, caffeine, alcohol, digestion, and scale-weight noise."

    return ConditioningHydrationGuidance(
        statusLabel = statusLabel,
        conditioningScore = score,
        stepProgressPercent = stepProgress,
        cardioStatus = cardioStatus,
        hydrationStatus = hydrationStatus,
        sodiumCue = sodiumCue,
        caffeineCue = listOf(caffeineCue, alcoholCue).filter { it.isNotBlank() }.joinToString(" "),
        weightFluctuationCue = weightFluctuationCue,
        nextAction = nextAction,
        aiReviewFocus = aiReviewFocus
    )
}

private fun Double.formatConditioningDecimal(): String {
    return if (this % 1.0 == 0.0) {
        toInt().toString()
    } else {
        "%.1f".format(Locale.US, this)
    }
}
