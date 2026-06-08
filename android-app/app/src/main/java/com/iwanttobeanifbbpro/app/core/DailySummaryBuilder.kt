package com.iwanttobeanifbbpro.app.core

import com.iwanttobeanifbbpro.app.data.DailyLog
import com.iwanttobeanifbbpro.app.data.WeeklyTrainingPlan

class DailySummaryBuilder {
    fun buildAiReviewContext(log: DailyLog, plan: WeeklyTrainingPlan? = null, extraRequest: String = ""): String {
        val totals = log.nutritionTotals()
        val weeklyPlan = plan?.days?.joinToString("\n") { day ->
            val plannedExercises = day.exercises.joinToString("\n") { exercise ->
                "  - ${exercise.name}: ${exercise.sets} x ${exercise.reps}, load ${exercise.loadKg ?: "not specified"} kg, RIR ${exercise.rir ?: "not specified"}, rest ${exercise.restSeconds}s, target ${exercise.targetMuscle.ifBlank { "not specified" }}, notes: ${exercise.notes.ifBlank { "none" }}"
            }.ifBlank { "  - No planned exercises." }
            "- ${day.dayName}: focus ${day.focus.ifBlank { "not specified" }}, notes: ${day.notes.ifBlank { "none" }}\n$plannedExercises"
        } ?: "- No weekly plan loaded."
        val exercises = log.trainingSession.exercises.joinToString("\n") { exercise ->
            val setLog = exercise.trackedSets().joinToString("\n") { set ->
                "  - Set ${set.setNumber}: target ${set.targetReps}, actual reps ${set.actualReps ?: "not logged"}, load ${set.loadKg ?: "not logged"} kg, RIR ${set.rir ?: "not logged"}, completed ${set.completed}, rest ${set.restSeconds}s, notes: ${set.notes.ifBlank { "none" }}"
            }
            """
            - ${exercise.name}: target ${exercise.targetMuscle.ifBlank { "not logged" }}, plan ${exercise.sets} sets x ${exercise.reps}, default load ${exercise.loadKg ?: "bodyweight"} kg, default RIR ${exercise.rir ?: "unknown"}, completed sets ${exercise.completedSetCount()}/${exercise.trackedSets().size}, volume ${exercise.volumeKg()} kg, default rest ${exercise.restSeconds}s, notes: ${exercise.notes.ifBlank { "none" }}
            $setLog
            """.trimIndent()
        }.ifBlank { "- No exercises logged yet." }
        val meals = log.meals.joinToString("\n") { meal ->
            "- ${meal.name}: ${meal.calories} kcal, P ${meal.protein}g, C ${meal.carbs}g, F ${meal.fat}g, fiber ${meal.fiber}g, notes: ${meal.notes}"
        }.ifBlank { "- No meals logged yet." }

        return """
            AI review context for daily training and daily nutrition.

            Date: ${log.date}

            Daily targets:
            - Calories ${log.targets.calories}, protein ${log.targets.protein}g, carbs ${log.targets.carbs}g, fat ${log.targets.fat}g, fiber ${log.targets.fiber}g.

            Current weekly training plan:
            - Plan name: ${plan?.name ?: "not loaded"}
            - Phase goal: ${plan?.phaseGoal ?: "not loaded"}
            $weeklyPlan

            Daily nutrition logged:
            - Calories ${totals.calories}/${log.targets.calories}
            - Protein ${totals.protein}/${log.targets.protein}g
            - Carbs ${totals.carbs}/${log.targets.carbs}g
            - Fat ${totals.fat}/${log.targets.fat}g
            - Fiber ${totals.fiber}/${log.targets.fiber}g
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
            1. Identify the limiting factor across training execution, nutrition adherence, sleep/recovery, and body-composition trend signals.
            2. Compare today's execution against the current weekly training plan and decide whether later training days should stay unchanged or be adjusted.
            3. Review set-level performance: load, reps, RIR, rest time, completed sets, technique notes, pain flags, target-muscle stimulus, and whether progression is justified.
            4. Decide which exercises should add reps, add load, hold, reduce volume, swap, or deload next time.
            5. Use Health Connect-derived data, if present, as approximate user-authorized signals from phone, scale, watch, Xiaomi, Huawei, or other source apps; do not overreact to one-day body-fat or calorie-burn estimates.
            6. Compare food intake with training demand and recommend the smallest useful calorie, protein, carb, fat, fiber, hydration, or meal-timing adjustment.
            7. Use attached photos, if provided, as approximate evidence for exercise form, equipment identification, food portions, nutrition labels, menus, and progress comparison.
            8. Specify tomorrow's training, nutrition, recovery, and tracking priorities.
        """.trimIndent()
    }
}
