package com.iwanttobeanifbbpro.app.core

import com.iwanttobeanifbbpro.app.data.AthleteProfile
import com.iwanttobeanifbbpro.app.data.DailyLog
import com.iwanttobeanifbbpro.app.data.TrainingDay
import com.iwanttobeanifbbpro.app.data.WeeklyTrainingPlan
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeParseException

data class TomorrowCoachBrief(
    val statusLabel: String,
    val tomorrowDate: String,
    val planDayName: String,
    val planFocus: String,
    val trainingAction: String,
    val nutritionAction: String,
    val recoveryAction: String,
    val trackingAction: String,
    val targetCalories: Int,
    val targetProtein: Int,
    val readinessGate: String,
    val aiReviewFocus: String,
    val primaryAction: String
) {
    fun promptLine(): String {
        return "Tomorrow Coach Brief: $statusLabel, date $tomorrowDate, plan day $planDayName | tomorrow training focus: $planFocus | Primary action: $primaryAction | Training action: $trainingAction | tomorrow nutrition target: $targetCalories kcal and $targetProtein g protein | Nutrition action: $nutritionAction | tomorrow recovery gate: $readinessGate | Recovery action: $recoveryAction | tomorrow tracking action: $trackingAction | readiness gate: $readinessGate | AI review focus: $aiReviewFocus"
    }
}

fun tomorrowCoachBrief(
    log: DailyLog,
    recentLogs: List<DailyLog>,
    profile: AthleteProfile,
    plan: WeeklyTrainingPlan
): TomorrowCoachBrief {
    val tomorrow = log.date.toLocalDateOrToday().plusDays(1)
    val planDay = plan.dayForDate(tomorrow)
    val recovery = recoveryGuidance(log, recentLogs)
    val body = bodyCompositionGuidance(log, recentLogs, profile)
    val sessionQuality = sessionQualityDashboard(log)
    val plannedFocus = planDay.focus.ifBlank {
        if (planDay.exercises.isEmpty()) "Recovery" else planDay.dayName
    }
    val plannedSets = planDay.exercises.sumOf { it.sets }
    val trainingAction = when {
        recovery.statusLabel == "Deload check" -> "Use a rest day, technique-only work, or deload-style session before chasing progression."
        recovery.statusLabel == "Reduce volume" && plannedSets > 0 -> "Run ${planDay.dayName} as reduced volume: trim 20-30% of hard sets and favor stable machines or cables."
        recovery.statusLabel == "Hold training stress" && plannedSets > 0 -> "Run ${planDay.dayName} without bonus sets or failure work; hold load unless Exercise History clearly supports progression."
        plannedSets > 0 -> "Prepare ${planDay.dayName}: $plannedFocus with $plannedSets planned hard sets; progress only clean exercises."
        else -> "Use ${planDay.dayName} as recovery: steps, mobility, easy cardio, and meal consistency."
    }
    val nutritionAction = when {
        body.calorieAdjustmentKcal > 0 -> "Set tomorrow to ${body.targetCalories} kcal, keep protein ${body.targetProtein} g, and place most added calories around training."
        body.calorieAdjustmentKcal < 0 -> "Set tomorrow to ${body.targetCalories} kcal, keep protein ${body.targetProtein} g, and remove calories from low-satiety extras first."
        log.nutritionTotals().protein < log.targets.protein -> "Hold ${body.targetCalories} kcal and prioritize protein early; do not leave the protein gap for the last meal."
        else -> "Hold ${body.targetCalories} kcal and ${body.targetProtein} g protein; keep portions measurable and photo uncertain meals."
    }
    val recoveryAction = when (recovery.statusLabel) {
        "Controlled push" -> "Sleep target stays 7+ hours; if warm-up is clean, follow the plan normally."
        "Hold training stress" -> "Protect sleep, hydration, and warm-up quality; stop if RIR is more than 1 harder than planned."
        "Reduce volume" -> "Prioritize sleep and lower-risk exercise choices; cut optional finishers."
        else -> "Treat tomorrow as a recovery checkpoint and avoid heavy progression attempts."
    }
    val trackingAction = when {
        log.metrics.bodyWeightKg == null -> "Weigh in tomorrow morning and sync Health Connect before AI review."
        log.meals.isEmpty() -> "Log at least the first meal or attach a food photo so nutrition pacing starts early."
        sessionQuality.loggedSetRatePercent < 80 && log.completedHardSets() > 0 -> "Make every completed set include kg, reps, RIR, and notes before trusting progression."
        else -> "Start with body weight, sleep, soreness, first meal, and the first working set log."
    }
    val statusLabel = when {
        recovery.statusLabel == "Deload check" -> "Recovery-first tomorrow"
        recovery.statusLabel == "Reduce volume" -> "Reduced-volume tomorrow"
        plannedSets > 0 -> "Plan-ready tomorrow"
        else -> "Recovery day tomorrow"
    }
    val readinessGate = when {
        plannedSets == 0 -> "No hard training is scheduled; keep recovery habits measurable."
        recovery.statusLabel == "Controlled push" -> "Green light only if warm-up is pain-free and target muscles respond."
        recovery.statusLabel == "Hold training stress" -> "No bonus sets; hold load if first working set is harder than planned."
        recovery.statusLabel == "Reduce volume" -> "Trim sets before load; avoid high-fatigue lifts if soreness or HR is elevated."
        else -> "Do not push intensity until sleep, soreness, stress, and HR signals improve."
    }
    val primaryAction = if (plannedSets > 0) {
        "Tomorrow: open Plan/Training, apply ${planDay.dayName}, then follow Training Readiness Builder before set 1."
    } else {
        "Tomorrow: use recovery day structure, hit nutrition targets, sync metrics, and let AI adjust the next training day."
    }
    val aiReviewFocus = "Use Tomorrow Coach Brief to lock next-day training focus, calories, protein, readiness gate, tracking requirements, and whether progression should wait."

    return TomorrowCoachBrief(
        statusLabel = statusLabel,
        tomorrowDate = tomorrow.toString(),
        planDayName = planDay.dayName,
        planFocus = plannedFocus,
        trainingAction = trainingAction,
        nutritionAction = nutritionAction,
        recoveryAction = recoveryAction,
        trackingAction = trackingAction,
        targetCalories = body.targetCalories,
        targetProtein = body.targetProtein,
        readinessGate = readinessGate,
        aiReviewFocus = aiReviewFocus,
        primaryAction = primaryAction
    )
}

private fun WeeklyTrainingPlan.dayForDate(date: LocalDate): TrainingDay {
    val expected = date.dayOfWeek.shortLabel()
    return days.firstOrNull { it.dayName.equals(expected, ignoreCase = true) }
        ?: days.firstOrNull { it.exercises.isNotEmpty() }
        ?: TrainingDay(dayName = expected, focus = "Recovery", notes = "Rest, steps, mobility, and meal consistency.")
}

private fun DayOfWeek.shortLabel(): String {
    return when (this) {
        DayOfWeek.MONDAY -> "Mon"
        DayOfWeek.TUESDAY -> "Tue"
        DayOfWeek.WEDNESDAY -> "Wed"
        DayOfWeek.THURSDAY -> "Thu"
        DayOfWeek.FRIDAY -> "Fri"
        DayOfWeek.SATURDAY -> "Sat"
        DayOfWeek.SUNDAY -> "Sun"
    }
}

private fun String.toLocalDateOrToday(): LocalDate {
    return try {
        LocalDate.parse(this)
    } catch (_: DateTimeParseException) {
        LocalDate.now()
    }
}
