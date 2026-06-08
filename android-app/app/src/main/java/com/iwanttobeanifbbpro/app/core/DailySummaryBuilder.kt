package com.iwanttobeanifbbpro.app.core

import com.iwanttobeanifbbpro.app.data.DailyLog
import com.iwanttobeanifbbpro.app.data.AthleteProfile
import com.iwanttobeanifbbpro.app.data.WeeklyTrainingPlan

class DailySummaryBuilder {
    fun buildAiReviewContext(
        log: DailyLog,
        recentLogs: List<DailyLog> = emptyList(),
        profile: AthleteProfile = AthleteProfile(),
        plan: WeeklyTrainingPlan? = null,
        extraRequest: String = ""
    ): String {
        val totals = log.nutritionTotals()
        val nutritionPacing = log.nutritionPacingSummary()
        val trend = buildTrendSummary(recentLogs.ifEmpty { listOf(log) })
        val weeklyPlan = plan?.days?.joinToString("\n") { day ->
            val plannedExercises = day.exercises.joinToString("\n") { exercise ->
                "  - ${exercise.name}: ${exercise.sets} x ${exercise.reps}, load ${exercise.loadKg ?: "not specified"} kg, RIR ${exercise.rir ?: "not specified"}, rest ${exercise.restSeconds}s, target ${exercise.targetMuscle.ifBlank { "not specified" }}, notes: ${exercise.notes.ifBlank { "none" }}"
            }.ifBlank { "  - No planned exercises." }
            "- ${day.dayName}: focus ${day.focus.ifBlank { "not specified" }}, notes: ${day.notes.ifBlank { "none" }}\n$plannedExercises"
        } ?: "- No weekly plan loaded."
        val exercises = log.trainingSession.exercises.joinToString("\n") { exercise ->
            val cue = exercise.progressionCue()
            val history = exercise.exerciseHistorySummary(log, recentLogs)
            val setLog = exercise.trackedSets().joinToString("\n") { set ->
                "  - Set ${set.setNumber}: target ${set.targetReps}, actual reps ${set.actualReps ?: "not logged"}, load ${set.loadKg ?: "not logged"} kg, RIR ${set.rir ?: "not logged"}, completed ${set.completed}, rest ${set.restSeconds}s, notes: ${set.notes.ifBlank { "none" }}"
            }
            """
            - ${exercise.name}: target ${exercise.targetMuscle.ifBlank { "not logged" }}, plan ${exercise.sets} sets x ${exercise.reps}, default load ${exercise.loadKg ?: "bodyweight"} kg, default RIR ${exercise.rir ?: "unknown"}, completed sets ${exercise.completedSetCount()}/${exercise.trackedSets().size}, volume ${exercise.volumeKg()} kg, default rest ${exercise.restSeconds}s, notes: ${exercise.notes.ifBlank { "none" }}
              Progression cue: ${cue.label}. ${cue.reason} ${cue.nextAction}
              Exercise history: ${history.statusLabel}. Previous date ${history.previousDate ?: "none"}, previous volume ${history.previousVolumeKg ?: "not available"} kg, current volume ${history.currentVolumeKg} kg, previous best load ${history.previousBestLoadKg ?: "not available"} kg, current best load ${history.currentBestLoadKg ?: "not logged"} kg, previous best reps ${history.previousBestReps ?: "not available"}, current best reps ${history.currentBestReps ?: "not logged"}, previous average RIR ${history.previousAverageRir ?: "not available"}, current average RIR ${history.currentAverageRir ?: "not logged"}. ${history.guidance}
            $setLog
            """.trimIndent()
        }.ifBlank { "- No exercises logged yet." }
        val meals = log.meals.joinToString("\n") { meal ->
            "- ${meal.name}: ${meal.calories} kcal, P ${meal.protein}g, C ${meal.carbs}g, F ${meal.fat}g, fiber ${meal.fiber}g, notes: ${meal.notes}"
        }.ifBlank { "- No meals logged yet." }

        return """
            AI review context for daily training and daily nutrition.

            Date: ${log.date}

            Athlete profile and goal:
            - Name: ${profile.displayName.ifBlank { "not set" }}
            - Primary goal: ${profile.primaryGoal}
            - Current phase: ${profile.currentPhase}
            - Training experience: ${profile.trainingExperience}
            - Sex: ${profile.sex.ifBlank { "not set" }}
            - Age: ${profile.age ?: "not set"}
            - HeightCm: ${profile.heightCm ?: "not set"}
            - Start weightKg: ${profile.startWeightKg ?: "not set"}
            - Target weightKg: ${profile.targetWeightKg ?: "not set"}
            - Target bodyFatPercent: ${profile.targetBodyFatPercent ?: "not set"}
            - Weekly training days: ${profile.weeklyTrainingDays}
            - Available equipment: ${profile.availableEquipment}
            - Dietary preference: ${profile.dietaryPreference.ifBlank { "not set" }}
            - Constraints: ${profile.constraints.ifBlank { "none logged" }}
            - Weak points: ${profile.weakPoints.ifBlank { "not set" }}

            Daily targets:
            - Calories ${log.targets.calories}, protein ${log.targets.protein}g, carbs ${log.targets.carbs}g, fat ${log.targets.fat}g, fiber ${log.targets.fiber}g.

            Current weekly training plan:
            - Plan name: ${plan?.name ?: "not loaded"}
            - Phase goal: ${plan?.phaseGoal ?: "not loaded"}
            $weeklyPlan

            Recent trend window:
            $trend

            Daily nutrition logged:
            - Calories ${totals.calories}/${log.targets.calories}
            - Protein ${totals.protein}/${log.targets.protein}g
            - Carbs ${totals.carbs}/${log.targets.carbs}g
            - Fat ${totals.fat}/${log.targets.fat}g
            - Fiber ${totals.fiber}/${log.targets.fiber}g
            - Nutrition pacing: ${nutritionPacing.statusLabel}, adherence ${nutritionPacing.adherenceScore}%, calories ${nutritionPacing.caloriesRemaining} kcal remaining, protein ${nutritionPacing.proteinRemaining}g remaining, carbs ${nutritionPacing.carbsRemaining}g remaining, fat ${nutritionPacing.fatRemaining}g remaining, fiber ${nutritionPacing.fiberRemaining}g remaining.
            - Next meal focus: ${nutritionPacing.nextMealFocus}
            Meals:
            $meals

            Daily training:
            - Planned focus: ${log.trainingSession.plannedFocus}
            - Completed: ${log.trainingSession.completed}
            - Planned hard sets: ${log.plannedHardSets()}
            - Completed hard sets: ${log.completedHardSets()}
            - Completed training volume: ${log.trainingVolumeKg()} kg
            - Session notes: ${log.trainingSession.sessionNotes}
            - Exercise log:
            $exercises

            Daily metrics:
            - bodyWeightKg: ${log.metrics.bodyWeightKg ?: "not logged"}
            - bodyFatPercent: ${log.metrics.bodyFatPercent ?: "not logged"}
            - leanBodyMassKg: ${log.metrics.leanBodyMassKg ?: "not logged"}
            - waistCm: ${log.metrics.waistCm ?: "not logged"}
            - sleepHours: ${log.metrics.sleepHours ?: "not logged"}
            - steps: ${log.metrics.steps}
            - restingHeartRateBpm: ${log.metrics.restingHeartRateBpm ?: "not logged"}
            - totalCaloriesBurnedKcal: ${log.metrics.totalCaloriesBurnedKcal ?: "not logged"}
            - healthDataSource: ${log.metrics.healthDataSource.ifBlank { "manual or not logged" }}
            - healthSyncedAt: ${log.metrics.healthSyncedAt.ifBlank { "not synced" }}
            - hunger: ${log.metrics.hunger}/5
            - fatigue: ${log.metrics.fatigue}/5
            - soreness: ${log.metrics.soreness}/5
            - stress: ${log.metrics.stress}/5

            User reflection:
            ${log.reflection.ifBlank { "No reflection logged." }}

            Extra request:
            ${extraRequest.ifBlank { "Perform an AI review and update recommendations for tomorrow." }}

            Please perform an AI review:
            1. Interpret today's data through the athlete profile: goal, phase, experience, equipment, schedule, weak points, and constraints.
            2. Identify the limiting factor across training execution, nutrition adherence, sleep/recovery, and body-composition trend signals using the recent trend window before reacting to today's values.
            3. Compare today's execution against the current weekly training plan and decide whether later training days should stay unchanged or be adjusted.
            4. Review set-level performance: load, reps, RIR, rest time, completed sets, technique notes, pain flags, target-muscle stimulus, and whether progression is justified.
            5. Compare Exercise History for repeated movements: previous date, previous volume, current volume, best load, best reps, completed sets, and average RIR.
            6. Use each Progression Cue as a deterministic starting point, then decide which exercises should add reps, add load, hold, reduce volume, swap, or deload next time.
            7. Use Health Connect-derived data, if present, as approximate user-authorized signals from phone, scale, watch, Xiaomi, Huawei, or other source apps; do not overreact to one-day body-fat or calorie-burn estimates.
            8. Compare food intake and Nutrition Pacing with training demand; recommend the smallest useful calorie, protein, carb, fat, fiber, hydration, or meal-timing adjustment.
            9. Use attached photos, if provided, as approximate evidence for exercise form, equipment identification, food portions, nutrition labels, menus, and progress comparison.
            10. Specify tomorrow's training, nutrition, recovery, and tracking priorities.
        """.trimIndent()
    }

    private fun buildTrendSummary(logs: List<DailyLog>): String {
        val ordered = logs.sortedBy { it.date }.takeLast(14)
        if (ordered.isEmpty()) return "- No historical logs saved yet."
        val firstWeight = ordered.firstNotNullOfOrNull { it.metrics.bodyWeightKg }
        val lastWeight = ordered.asReversed().firstNotNullOfOrNull { it.metrics.bodyWeightKg }
        val weightChange = if (firstWeight != null && lastWeight != null) {
            "${lastWeight - firstWeight} kg"
        } else {
            "not enough data"
        }
        val nutrition = ordered.map { it.nutritionTotals() }
        val avgCalories = nutrition.map { it.calories }.averageIntOrNull()
        val avgProtein = nutrition.map { it.protein }.averageIntOrNull()
        val avgSleep = ordered.mapNotNull { it.metrics.sleepHours }.averageDoubleOrNull()
        val avgSteps = ordered.map { it.metrics.steps }.averageIntOrNull()
        val avgFatigue = ordered.map { it.metrics.fatigue }.averageIntOrNull()
        val totalCompletedSets = ordered.sumOf { it.completedHardSets() }
        val totalPlannedSets = ordered.sumOf { it.plannedHardSets() }
        val totalVolume = ordered.sumOf { it.trainingVolumeKg() }
        val days = ordered.joinToString("\n") { day ->
            val totals = day.nutritionTotals()
            "- ${day.date}: weight ${day.metrics.bodyWeightKg ?: "not logged"} kg, bodyFat ${day.metrics.bodyFatPercent ?: "not logged"}%, sets ${day.completedHardSets()}/${day.plannedHardSets()}, volume ${day.trainingVolumeKg()} kg, calories ${totals.calories}, protein ${totals.protein}g, sleep ${day.metrics.sleepHours ?: "not logged"}h, steps ${day.metrics.steps}, fatigue ${day.metrics.fatigue}/5"
        }
        return """
            - Days available: ${ordered.size}
            - Weight change in window: $weightChange
            - Average calories: ${avgCalories?.roundForPrompt() ?: "not enough data"}
            - Average protein: ${avgProtein?.roundForPrompt() ?: "not enough data"} g
            - Average sleep: ${avgSleep?.roundForPrompt() ?: "not enough data"} h
            - Average steps: ${avgSteps?.roundForPrompt() ?: "not enough data"}
            - Average fatigue: ${avgFatigue?.roundForPrompt() ?: "not enough data"}/5
            - Completed hard sets: $totalCompletedSets/$totalPlannedSets
            - Total completed training volume: $totalVolume kg
            Daily trend rows:
            $days
        """.trimIndent()
    }
}

private data class NutritionPacingSummary(
    val caloriesRemaining: Int,
    val proteinRemaining: Int,
    val carbsRemaining: Int,
    val fatRemaining: Int,
    val fiberRemaining: Int,
    val adherenceScore: Int,
    val statusLabel: String,
    val nextMealFocus: String
)

private fun DailyLog.nutritionPacingSummary(): NutritionPacingSummary {
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
    return NutritionPacingSummary(
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

private fun List<Int>.averageIntOrNull(): Double? = takeIf { it.isNotEmpty() }?.map { it.toDouble() }?.average()

private fun List<Double>.averageDoubleOrNull(): Double? = takeIf { it.isNotEmpty() }?.average()

private fun Double.roundForPrompt(): String {
    return if (this % 1.0 == 0.0) {
        toInt().toString()
    } else {
        "%.1f".format(java.util.Locale.US, this)
    }
}
