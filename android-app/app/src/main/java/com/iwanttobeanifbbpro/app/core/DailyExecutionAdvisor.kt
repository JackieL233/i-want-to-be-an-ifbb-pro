package com.iwanttobeanifbbpro.app.core

import com.iwanttobeanifbbpro.app.data.AthleteProfile
import com.iwanttobeanifbbpro.app.data.DailyLog

enum class DailyExecutionRoute {
    PLAN,
    TRAINING,
    NUTRITION,
    METRICS,
    AI_REVIEW
}

data class DailyExecutionPlan(
    val statusLabel: String,
    val readinessScore: Int,
    val priorityFocus: String,
    val primaryActionLabel: String,
    val primaryActionRoute: DailyExecutionRoute,
    val trainingDecision: String,
    val nutritionDecision: String,
    val recoveryDecision: String,
    val dataQualityGate: String,
    val aiReviewGate: String,
    val planAdjustmentSignal: String,
    val nextBestAction: String
) {
    fun promptLine(): String {
        return "Daily Execution Plan: $statusLabel, priority $priorityFocus, readiness score $readinessScore | Primary action: $primaryActionLabel | Training decision: $trainingDecision | Nutrition decision: $nutritionDecision | Recovery decision: $recoveryDecision | Data quality gate: $dataQualityGate | AI review gate: $aiReviewGate | Plan adjustment signal: $planAdjustmentSignal | Next best action: $nextBestAction"
    }
}

fun dailyExecutionPlan(
    log: DailyLog,
    recentLogs: List<DailyLog>,
    profile: AthleteProfile,
    hasWeeklyPlan: Boolean,
    hasAiReviewToday: Boolean = false
): DailyExecutionPlan {
    val totals = log.nutritionTotals()
    val plannedSets = log.plannedHardSets()
    val completedSets = log.completedHardSets()
    val remainingSets = (plannedSets - completedSets).coerceAtLeast(0)
    val sessionReady = log.trainingSession.exercises.isNotEmpty()
    val trainingDone = plannedSets > 0 && completedSets >= plannedSets
    val metricsReady = log.metrics.bodyWeightKg != null ||
        log.metrics.sleepHours != null ||
        log.metrics.steps > 0 ||
        log.metrics.restingHeartRateBpm != null ||
        log.metrics.healthSyncedAt.isNotBlank()
    val mealsLogged = log.meals.isNotEmpty()
    val caloriesRemaining = log.targets.calories - totals.calories
    val proteinRemaining = log.targets.protein - totals.protein
    val carbsRemaining = log.targets.carbs - totals.carbs
    val fiberRemaining = log.targets.fiber - totals.fiber
    val targetSum = listOf(log.targets.calories, log.targets.protein, log.targets.carbs, log.targets.fat, log.targets.fiber)
        .sumOf { it.coerceAtLeast(1) }
        .toDouble()
    val missSum = listOf(caloriesRemaining, proteinRemaining, carbsRemaining, log.targets.fat - totals.fat, fiberRemaining)
        .sumOf { kotlin.math.abs(it).coerceAtMost(400) }
        .toDouble()
    val macroAdherence = (100 - (missSum / targetSum * 100)).toInt().coerceIn(0, 100)
    val recovery = recoveryGuidance(log, recentLogs)
    val readiness = trainingReadinessBuilder(log, recovery)
    val body = bodyCompositionGuidance(log, recentLogs, profile)
    val conditioning = conditioningHydrationGuidance(log, recentLogs, profile)
    val trendReady = recentLogs.ifEmpty { listOf(log) }.size >= 4

    val primaryActionRoute = when {
        !sessionReady -> if (hasWeeklyPlan) DailyExecutionRoute.PLAN else DailyExecutionRoute.PLAN
        !trainingDone -> DailyExecutionRoute.TRAINING
        !mealsLogged || macroAdherence < 72 -> DailyExecutionRoute.NUTRITION
        !metricsReady -> DailyExecutionRoute.METRICS
        else -> DailyExecutionRoute.AI_REVIEW
    }
    val primaryActionLabel = when (primaryActionRoute) {
        DailyExecutionRoute.PLAN -> if (hasWeeklyPlan) "Apply plan day" else "Build plan"
        DailyExecutionRoute.TRAINING -> "Log sets"
        DailyExecutionRoute.NUTRITION -> "Fix food"
        DailyExecutionRoute.METRICS -> "Sync metrics"
        DailyExecutionRoute.AI_REVIEW -> if (hasAiReviewToday) "View review" else "Run AI review"
    }
    val statusLabel = when {
        !sessionReady -> "Plan needed"
        !trainingDone -> "Execute workout"
        !mealsLogged || macroAdherence < 72 -> "Nutrition gap"
        !metricsReady -> "Metric gap"
        hasAiReviewToday -> "Day locked"
        else -> "Ready for review"
    }
    val priorityFocus = when (primaryActionRoute) {
        DailyExecutionRoute.PLAN -> "Plan setup"
        DailyExecutionRoute.TRAINING -> "Training execution"
        DailyExecutionRoute.NUTRITION -> "Nutrition pacing"
        DailyExecutionRoute.METRICS -> "Health metrics"
        DailyExecutionRoute.AI_REVIEW -> "AI adjustment"
    }

    val trainingDecision = when {
        !sessionReady && hasWeeklyPlan -> "Apply the correct weekly plan day before starting; AI review should not judge volume without a loaded session."
        !sessionReady -> "Create or choose a plan template, then load today's first executable workout."
        !trainingDone -> "Finish $remainingSets remaining working sets using ${readiness.statusLabel.lowercase()} rules: ${readiness.volumeAdjustmentCue}"
        else -> "Training is complete; use Exercise History and Progression Cue before adding load next time."
    }
    val nutritionDecision = when {
        !mealsLogged -> "Log the first meal or attach a food photo so calories and protein can be compared with training demand."
        proteinRemaining > 35 -> "Protein is still $proteinRemaining g behind; make the next meal protein-first before adding extra fats."
        caloriesRemaining < -150 -> "Calories are ${kotlin.math.abs(caloriesRemaining)} kcal over; keep the next meal lean and do not cut training carbs retroactively."
        carbsRemaining > 90 && !trainingDone -> "Carbs are available; place them near the remaining workout or post-workout recovery meal."
        fiberRemaining > 10 -> "Fiber is still $fiberRemaining g behind; add fruit, vegetables, oats, beans, or potatoes."
        else -> "Nutrition is usable for review; keep portions measurable and note oil, sauces, labels, or food photos where uncertain."
    }
    val recoveryDecision = "${recovery.statusLabel}: ${recovery.recommendedTrainingAction} Conditioning: ${conditioning.statusLabel}; ${conditioning.nextAction}"
    val dataQualityGate = when {
        !sessionReady -> "Missing loaded workout."
        !trainingDone -> "AI review can draft guidance, but final progression should wait until all working sets are logged."
        !mealsLogged -> "Missing food log or food photo."
        !metricsReady -> "Missing body/recovery metrics or Health Connect sync."
        conditioning.statusLabel == "NEAT behind" -> "Training and food are usable, but step/conditioning gap lowers fat-loss confidence."
        conditioning.statusLabel == "Hydration gap" -> "Hydration is not clean enough to trust pump, appetite, or scale changes."
        !trendReady -> "Daily data is usable, but trend confidence improves after at least four logged days."
        else -> "Training, food, metrics, and trend signals are ready for a high-confidence AI review."
    }
    val aiReviewGate = when {
        hasAiReviewToday -> "Today's AI review is already saved; use it as the current instruction unless new data was added."
        !trainingDone -> "Run after finishing the session for the cleanest progression decision."
        !mealsLogged -> "Run after at least one meal or food photo is logged."
        !metricsReady -> "Run after Health Connect sync or manual recovery metrics."
        else -> "Ready to ask AI for tomorrow's training, nutrition, and recovery priorities."
    }
    val planAdjustmentSignal = when {
        recovery.statusLabel == "Deload check" -> "Do not chase progression; consider deload, rest, or technique-only work."
        recovery.statusLabel == "Reduce volume" -> "Reduce hard sets or choose stable machine/cable alternatives today."
        conditioning.statusLabel == "NEAT behind" -> "Adjust steps or easy cardio before cutting calories again."
        conditioning.statusLabel == "Hydration gap" -> "Hold calorie changes until water/sodium logging is cleaner."
        conditioning.statusLabel == "Stimulant pressure" -> "Protect sleep before adding training or cardio pressure."
        body.statusLabel == "Small calorie increase" -> "Food target may need a small increase, mostly around training."
        body.statusLabel == "Small calorie decrease" -> "Food target may need a small decrease from low-satiety extras."
        body.statusLabel == "Need trend data" -> "Hold plan targets until body weight and food logs have enough trend evidence."
        else -> "Hold plan structure and adjust only exercise-specific progression."
    }
    val nextBestAction = when (primaryActionRoute) {
        DailyExecutionRoute.PLAN -> if (hasWeeklyPlan) {
            "Open Plan and apply the correct day, then follow Training Readiness Builder before the first set."
        } else {
            "Open Plan, choose a template, and load today's workout before training."
        }
        DailyExecutionRoute.TRAINING -> "Open Training, complete the remaining sets, and respect rest timers and RIR."
        DailyExecutionRoute.NUTRITION -> "Open Nutrition, add the next meal or food photo, and close the biggest macro gap."
        DailyExecutionRoute.METRICS -> "Open Metrics, sync Health Connect or enter body weight, sleep, HR, soreness, fatigue, and stress."
        DailyExecutionRoute.AI_REVIEW -> if (hasAiReviewToday) {
            "Review the saved AI note and use it to set tomorrow's plan."
        } else {
            "Run AI review and lock tomorrow's training, nutrition, and recovery priorities."
        }
    }

    return DailyExecutionPlan(
        statusLabel = statusLabel,
        readinessScore = recovery.readinessScore,
        priorityFocus = priorityFocus,
        primaryActionLabel = primaryActionLabel,
        primaryActionRoute = primaryActionRoute,
        trainingDecision = trainingDecision,
        nutritionDecision = nutritionDecision,
        recoveryDecision = recoveryDecision,
        dataQualityGate = dataQualityGate,
        aiReviewGate = aiReviewGate,
        planAdjustmentSignal = planAdjustmentSignal,
        nextBestAction = nextBestAction
    )
}
