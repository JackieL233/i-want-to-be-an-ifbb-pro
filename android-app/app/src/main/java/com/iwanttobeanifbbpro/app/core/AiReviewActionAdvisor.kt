package com.iwanttobeanifbbpro.app.core

import com.iwanttobeanifbbpro.app.data.AiReviewEntry
import com.iwanttobeanifbbpro.app.data.DailyLog
import java.util.Locale

enum class AiReviewActionCategory(val label: String) {
    TRAINING("Training"),
    NUTRITION("Nutrition"),
    RECOVERY("Recovery"),
    TRACKING("Tracking"),
    PLAN("Plan"),
    REVIEW("Review")
}

data class AiReviewActionItem(
    val category: AiReviewActionCategory,
    val title: String,
    val detail: String,
    val actionLabel: String,
    val route: DailyExecutionRoute,
    val evidenceCue: String,
    val priority: Int
)

data class AiReviewActionQueue(
    val statusLabel: String,
    val sourceLabel: String,
    val confidenceLabel: String,
    val primaryAction: AiReviewActionItem,
    val actions: List<AiReviewActionItem>,
    val aiReviewFocus: String
) {
    fun promptLine(): String {
        val actionLine = actions.joinToString("; ") {
            "${it.category.label}: ${it.title}, priority ${it.priority}, route ${it.route}, evidence ${it.evidenceCue}"
        }
        return "AI Review Action Queue: $statusLabel | Source: $sourceLabel | Confidence: $confidenceLabel | Primary action: ${primaryAction.title} via ${primaryAction.route} | Actions: $actionLine | AI review focus: $aiReviewFocus"
    }
}

fun aiReviewActionQueue(
    latestReview: AiReviewEntry?,
    log: DailyLog
): AiReviewActionQueue {
    val text = latestReview?.body.orEmpty()
    val normalized = text.lowercase(Locale.US)
    val hasReview = latestReview != null && text.isNotBlank()
    val reviewForToday = latestReview?.logDate == log.date
    val closeout = trainingCloseoutCoach(log, hasAiReviewToday = reviewForToday)
    val actions = when {
        !hasReview -> emptyList()
        else -> buildList {
            add(trainingActionFromReview(normalized, log, closeout))
            add(nutritionActionFromReview(normalized, log))
            add(recoveryActionFromReview(normalized, log))
            add(trackingActionFromReview(normalized, log, closeout))
            if (needsPlanAction(normalized)) {
                add(planActionFromReview(normalized))
            }
        }
            .distinctBy { it.category }
            .sortedWith(compareByDescending<AiReviewActionItem> { it.priority }.thenBy { it.category.ordinal })
    }
    val fallback = AiReviewActionItem(
        category = AiReviewActionCategory.REVIEW,
        title = if (hasReview) "Review saved guidance" else "Run daily review",
        detail = if (hasReview) {
            "Open the latest review, then turn its training, food, recovery, and tracking advice into tomorrow's log."
        } else {
            "Finish the daily closeout checklist, then run AI review to create the first action queue."
        },
        actionLabel = if (hasReview) "Open AI" else "Run review",
        route = DailyExecutionRoute.AI_REVIEW,
        evidenceCue = if (hasReview) "Uses saved review text and today's log." else "No saved review is available yet.",
        priority = if (hasReview) 3 else 9
    )
    val primary = actions.firstOrNull() ?: fallback
    val sourceLabel = when {
        latestReview == null -> "No saved review"
        reviewForToday -> "Today ${latestReview.modeTitle}"
        else -> "Latest saved ${latestReview.logDate}"
    }
    val confidenceLabel = when {
        !hasReview -> "Needs review"
        !reviewForToday -> "Older review"
        closeout.primaryActionRoute != DailyExecutionRoute.AI_REVIEW -> "Closeout gaps"
        else -> "Ready to execute"
    }
    val statusLabel = when {
        !hasReview -> "No review actions"
        !reviewForToday -> "Carryover actions"
        closeout.primaryActionRoute != DailyExecutionRoute.AI_REVIEW -> "Review with caveats"
        else -> "Action queue ready"
    }

    return AiReviewActionQueue(
        statusLabel = statusLabel,
        sourceLabel = sourceLabel,
        confidenceLabel = confidenceLabel,
        primaryAction = primary,
        actions = if (actions.isEmpty()) listOf(fallback) else actions.take(5),
        aiReviewFocus = "Convert saved AI review text into a practical queue for training action, nutrition action, recovery action, tracking action, and plan action routes; use Training Closeout Coach before trusting plan changes."
    )
}

private fun trainingActionFromReview(
    normalized: String,
    log: DailyLog,
    closeout: TrainingCloseoutCoach
): AiReviewActionItem {
    val priority = when {
        closeout.primaryActionRoute == DailyExecutionRoute.TRAINING -> 9
        anyOf(normalized, "deload", "reduce volume", "hold load", "swap", "substitution", "technique", "pain") -> 8
        anyOf(normalized, "add load", "add weight", "add reps", "progress") -> 7
        else -> 5
    }
    val title = when {
        closeout.primaryActionRoute == DailyExecutionRoute.TRAINING -> closeout.primaryActionLabel
        anyOf(normalized, "deload", "reduce volume") -> "Apply conservative training change"
        anyOf(normalized, "swap", "substitution", "pain") -> "Review exercise swaps"
        anyOf(normalized, "add load", "add weight") -> "Confirm load progression"
        anyOf(normalized, "add reps") -> "Chase clean reps"
        else -> "Set tomorrow's training intent"
    }
    val detail = when (title) {
        "Apply conservative training change" -> "AI review mentions lower stress; keep hard sets controlled and avoid surprise failure work."
        "Review exercise swaps" -> "Use Exercise Substitution Coach and visual guide IDs before replacing a movement."
        "Confirm load progression" -> "Check Exercise History, RIR, pain flags, and closeout score before adding load."
        "Chase clean reps" -> "Keep load stable and add reps only when technique and target-muscle stimulus stay clean."
        "Set tomorrow's training intent" -> "Use Tomorrow Coach Brief, Weekly Check-in, and the latest review before starting the next session."
        else -> closeout.aiReviewGate
    }
    return AiReviewActionItem(
        category = AiReviewActionCategory.TRAINING,
        title = title,
        detail = detail,
        actionLabel = "Open Training",
        route = DailyExecutionRoute.TRAINING,
        evidenceCue = "Training closeout ${closeout.statusLabel}, sets ${closeout.completedSets}/${closeout.plannedSets}, missing logs ${closeout.missingLogCount}.",
        priority = priority
    )
}

private fun nutritionActionFromReview(normalized: String, log: DailyLog): AiReviewActionItem {
    val totals = log.nutritionTotals()
    val proteinGap = log.targets.protein - totals.protein
    val calorieGap = log.targets.calories - totals.calories
    val priority = when {
        anyOf(normalized, "calorie", "protein", "carb", "fat", "meal", "nutrition") -> 8
        log.meals.isEmpty() || proteinGap > 30 || kotlin.math.abs(calorieGap) > 250 -> 7
        else -> 4
    }
    val title = when {
        proteinGap > 30 -> "Close protein gap"
        calorieGap < -200 -> "Keep next meal lean"
        calorieGap > 400 -> "Place remaining calories"
        anyOf(normalized, "carb", "pre-workout", "post-workout") -> "Time carbs around training"
        else -> "Review food targets"
    }
    val detail = when (title) {
        "Close protein gap" -> "Protein is still $proteinGap g behind; choose a lean protein anchor before adding fats."
        "Keep next meal lean" -> "Calories are ${kotlin.math.abs(calorieGap)} kcal over; keep the next meal mostly lean protein and vegetables."
        "Place remaining calories" -> "$calorieGap kcal remain; use Nutrition Pacing and Next Meal Builder instead of guessing."
        "Time carbs around training" -> "Use carbs near the next hard session or post-workout recovery meal if digestion is comfortable."
        else -> "Compare the saved review against Nutrition Pacing, Meal Assembly Guide, and food-photo uncertainty."
    }
    return AiReviewActionItem(
        category = AiReviewActionCategory.NUTRITION,
        title = title,
        detail = detail,
        actionLabel = "Open Nutrition",
        route = DailyExecutionRoute.NUTRITION,
        evidenceCue = "Today ${totals.calories}/${log.targets.calories} kcal, protein ${totals.protein}/${log.targets.protein} g.",
        priority = priority
    )
}

private fun recoveryActionFromReview(normalized: String, log: DailyLog): AiReviewActionItem {
    val sleepLabel = log.metrics.sleepHours?.toString() ?: "not logged"
    val priority = when {
        anyOf(normalized, "sleep", "fatigue", "soreness", "stress", "rest", "recovery", "hr", "deload") -> 8
        log.metrics.sleepHours == null || log.metrics.fatigue >= 4 || log.metrics.soreness >= 4 -> 7
        else -> 4
    }
    val title = when {
        anyOf(normalized, "deload") -> "Respect deload signal"
        log.metrics.sleepHours == null -> "Log sleep before judging recovery"
        log.metrics.fatigue >= 4 || log.metrics.soreness >= 4 -> "Reduce recovery pressure"
        anyOf(normalized, "steps", "cardio", "hydration") -> "Clean up recovery variables"
        else -> "Check recovery gate"
    }
    val detail = when (title) {
        "Respect deload signal" -> "Treat deload language as a cap on volume and failure work until performance rebounds."
        "Log sleep before judging recovery" -> "Sleep is missing; sync or enter recovery data before changing volume aggressively."
        "Reduce recovery pressure" -> "Fatigue ${log.metrics.fatigue}/5 and soreness ${log.metrics.soreness}/5 suggest conservative execution."
        "Clean up recovery variables" -> "Use Conditioning & Hydration before interpreting scale, pump, appetite, or training output."
        else -> "Check Recovery Guidance and Health Connect signals before pushing tomorrow's session."
    }
    return AiReviewActionItem(
        category = AiReviewActionCategory.RECOVERY,
        title = title,
        detail = detail,
        actionLabel = "Open Metrics",
        route = DailyExecutionRoute.METRICS,
        evidenceCue = "Sleep $sleepLabel h, fatigue ${log.metrics.fatigue}/5, soreness ${log.metrics.soreness}/5.",
        priority = priority
    )
}

private fun trackingActionFromReview(
    normalized: String,
    log: DailyLog,
    closeout: TrainingCloseoutCoach
): AiReviewActionItem {
    val needsPhotos = anyOf(normalized, "photo", "form", "equipment", "portion", "label", "progress") ||
        closeout.photoCue.contains("Attach", ignoreCase = true)
    val metricsMissing = log.metrics.healthSyncedAt.isBlank() &&
        log.metrics.bodyWeightKg == null &&
        log.metrics.sleepHours == null
    val priority = when {
        closeout.missingLogCount > 0 || needsPhotos || metricsMissing -> 8
        else -> 5
    }
    val title = when {
        closeout.missingLogCount > 0 -> "Fill missing set logs"
        needsPhotos -> "Attach evidence photos"
        metricsMissing -> "Sync or enter health metrics"
        else -> "Keep tomorrow's evidence clean"
    }
    val detail = when (title) {
        "Fill missing set logs" -> "${closeout.missingLogCount} completed set(s) need reps, kg, or RIR before progression is reliable."
        "Attach evidence photos" -> "Add form, equipment, food label, or physique photos when the review depends on visual uncertainty."
        "Sync or enter health metrics" -> "Health Connect or manual recovery metrics make the next review more useful."
        else -> "Before the next check-in, keep set logs, food notes, photos, measurements, and Health Connect sync aligned."
    }
    return AiReviewActionItem(
        category = AiReviewActionCategory.TRACKING,
        title = title,
        detail = detail,
        actionLabel = if (metricsMissing) "Open Metrics" else "Open AI",
        route = if (metricsMissing) DailyExecutionRoute.METRICS else DailyExecutionRoute.AI_REVIEW,
        evidenceCue = "Closeout score ${closeout.closeoutScore}; photo cue: ${closeout.photoCue}",
        priority = priority
    )
}

private fun planActionFromReview(normalized: String): AiReviewActionItem {
    val title = when {
        anyOf(normalized, "weekly", "volume", "sets") -> "Review weekly volume"
        anyOf(normalized, "phase", "cut", "bulk", "recomp") -> "Check phase targets"
        else -> "Update plan notes"
    }
    return AiReviewActionItem(
        category = AiReviewActionCategory.PLAN,
        title = title,
        detail = "Only edit plan-wide structure after Weekly Check-in and trend data support the change.",
        actionLabel = "Open Plan",
        route = DailyExecutionRoute.PLAN,
        evidenceCue = "Detected plan-level language in the saved AI review.",
        priority = 6
    )
}

private fun needsPlanAction(normalized: String): Boolean {
    return anyOf(normalized, "weekly", "program", "plan", "volume", "phase", "deload", "block", "mesocycle")
}

private fun anyOf(text: String, vararg terms: String): Boolean {
    return terms.any { text.contains(it) }
}
