package com.iwanttobeanifbbpro.app.core

import com.iwanttobeanifbbpro.app.data.DailyLog
import java.util.Locale
import kotlin.math.roundToInt

data class WarmUpRampSet(
    val label: String,
    val loadCue: String,
    val repsCue: String,
    val effortCue: String,
    val purpose: String
) {
    fun promptLine(): String {
        return "$label: load $loadCue, reps $repsCue, effort $effortCue, purpose $purpose"
    }
}

data class WarmUpRampPlan(
    val statusLabel: String,
    val currentExerciseName: String,
    val targetMuscle: String,
    val visualSpec: ExerciseVisualSpec,
    val targetReps: String,
    val plannedLoadKg: Double?,
    val plannedRir: Double?,
    val readinessGate: String,
    val rampStrategy: String,
    val rampSets: List<WarmUpRampSet>,
    val firstWorkingSetGate: String,
    val stopRule: String,
    val aiReviewFocus: String
) {
    val hasActiveRamp: Boolean
        get() = currentExerciseName.isNotBlank() && rampSets.isNotEmpty()

    fun promptLine(): String {
        val setPlan = if (rampSets.isEmpty()) {
            "no ramp set checklist loaded"
        } else {
            rampSets.joinToString(" || ") { it.promptLine() }
        }
        val loadLabel = plannedLoadKg?.formatRampDecimal()?.let { "$it kg" } ?: "bodyweight or not specified"
        val rirLabel = plannedRir?.formatRampDecimal() ?: "not specified"
        return "Warm-up Ramp Plan: $statusLabel | Current exercise: ${currentExerciseName.ifBlank { "none" }} | Target muscle: ${targetMuscle.ifBlank { "not specified" }} | Target reps: ${targetReps.ifBlank { "not specified" }} | Planned load: $loadLabel | Planned RIR: $rirLabel | Visual guide: ${visualSpec.visualPromptLine()} | Readiness gate: $readinessGate | Ramp strategy: $rampStrategy | Ramp set checklist: $setPlan | First working set gate: $firstWorkingSetGate | Stop rule: $stopRule | AI review focus: $aiReviewFocus"
    }
}

fun warmUpRampPlan(
    log: DailyLog,
    builder: TrainingReadinessBuilder,
    nextSet: NextSetCoach = nextSetCoach(log)
): WarmUpRampPlan {
    if (!nextSet.hasActiveSet) {
        return WarmUpRampPlan(
            statusLabel = "No active ramp",
            currentExerciseName = nextSet.currentExerciseName,
            targetMuscle = nextSet.targetMuscle,
            visualSpec = nextSet.visualSpec,
            targetReps = nextSet.targetReps,
            plannedLoadKg = nextSet.plannedLoadKg,
            plannedRir = nextSet.plannedRir,
            readinessGate = builder.recoveryGate,
            rampStrategy = "Apply a plan day or add an exercise before the app can calculate a warm-up ramp set checklist.",
            rampSets = emptyList(),
            firstWorkingSetGate = builder.firstWorkingSetCue,
            stopRule = builder.stopRule,
            aiReviewFocus = "Check whether a planned exercise, next-set target, visual guide ID, load, reps, and rest timer exist before judging warm-up quality."
        )
    }

    val painLimited = nextSet.statusLabel.contains("Pain", ignoreCase = true)
    val techniqueLimited = nextSet.statusLabel.contains("Technique", ignoreCase = true)
    val statusLabel = when {
        painLimited -> "Pain-limited ramp"
        techniqueLimited -> "Technique-check ramp"
        builder.statusLabel == "Ready to execute" -> "Working-load ramp"
        builder.statusLabel == "Hold before pushing" -> "Conservative ramp"
        builder.statusLabel == "Reduce session stress" -> "Reduced-stress ramp"
        else -> "Deload-style ramp"
    }
    val rampSets = if (nextSet.plannedLoadKg != null && nextSet.plannedLoadKg > 0.0) {
        loadedRampSets(
            plannedLoadKg = nextSet.plannedLoadKg,
            warmUpCue = builder.warmUpCue,
            statusLabel = statusLabel
        )
    } else {
        bodyweightRampSets(
            warmUpCue = builder.warmUpCue,
            statusLabel = statusLabel
        )
    }
    val rampStrategy = when (statusLabel) {
        "Pain-limited ramp" -> "Use the visual guide ID ${nextSet.visualSpec.visualId}, then treat every ramp set as a pain and range check; do not chase normal working load if pain changes mechanics."
        "Technique-check ramp" -> "Use smaller jumps, repeat the final ramp set if control is uncertain, and only start work sets when the movement path matches the visual guide."
        "Working-load ramp" -> "Move from easy pattern prep to light, moderate, and near-work ramp sets before the first planned working set."
        "Conservative ramp" -> "Add one extra near-work checkpoint so today's readiness caps the first working set instead of forcing progression."
        "Reduced-stress ramp" -> "Use the ramp to decide whether to start lighter, trim volume, or swap to a lower-joint-cost option."
        else -> "Keep all ramp sets easy; the goal is technique practice and recovery feedback, not a normal progression attempt."
    }
    val firstWorkingSetGate = when (statusLabel) {
        "Pain-limited ramp" -> "Start no normal working set unless the final ramp is pain-free, full-range, and stable; otherwise open Exercise Substitution Coach."
        "Technique-check ramp" -> "Start the first working set only if the final ramp matches the intended path and target muscle; otherwise repeat lighter or swap."
        "Working-load ramp" -> builder.firstWorkingSetCue
        "Conservative ramp" -> "Enter the first working set only if the final ramp feels no harder than planned; otherwise begin 2.5-5% lighter."
        "Reduced-stress ramp" -> "Begin 5-10% lighter or at the low end of the rep range if the final ramp is slow, shaky, or poorly targeted."
        else -> "If you train, keep the first set technique-only with 4+ RIR and do not use it to justify progression."
    }
    val stopRule = "${builder.stopRule} ${nextSet.stopCue}"
    val aiReviewFocus = "Compare whether the user completed the warm-up ramp set checklist, final ramp set quality, visual guide ID, planned load percentage, first working set gate, actual first set load/reps/RIR, pain notes, and technique notes before changing progression."

    return WarmUpRampPlan(
        statusLabel = statusLabel,
        currentExerciseName = nextSet.currentExerciseName,
        targetMuscle = nextSet.targetMuscle,
        visualSpec = nextSet.visualSpec,
        targetReps = nextSet.targetReps,
        plannedLoadKg = nextSet.plannedLoadKg,
        plannedRir = nextSet.plannedRir,
        readinessGate = builder.recoveryGate,
        rampStrategy = rampStrategy,
        rampSets = rampSets,
        firstWorkingSetGate = firstWorkingSetGate,
        stopRule = stopRule,
        aiReviewFocus = aiReviewFocus
    )
}

private fun loadedRampSets(
    plannedLoadKg: Double,
    warmUpCue: String,
    statusLabel: String
): List<WarmUpRampSet> {
    val percentages = when (statusLabel) {
        "Pain-limited ramp" -> listOf(0.25, 0.40, 0.55)
        "Technique-check ramp" -> listOf(0.30, 0.50, 0.65, 0.75)
        "Conservative ramp" -> listOf(0.35, 0.55, 0.70, 0.85)
        "Reduced-stress ramp" -> listOf(0.30, 0.50, 0.65, 0.80)
        "Deload-style ramp" -> listOf(0.25, 0.40, 0.55)
        else -> listOf(0.40, 0.60, 0.80)
    }
    val prepSet = WarmUpRampSet(
        label = "General prep",
        loadCue = "no working load",
        repsCue = "5-10 minutes",
        effortCue = "easy breathing, joints warming",
        purpose = warmUpCue
    )
    val rampSetRows = percentages.mapIndexed { index, percent ->
        WarmUpRampSet(
            label = "Ramp set ${index + 1}",
            loadCue = "${(percent * 100).roundToInt()}% = ${plannedLoadKg.rampLoad(percent)} kg",
            repsCue = rampRepsFor(index, percentages.lastIndex, statusLabel),
            effortCue = rampEffortFor(index, percentages.lastIndex, statusLabel),
            purpose = rampPurposeFor(index, percentages.lastIndex, statusLabel)
        )
    }
    return listOf(prepSet) + rampSetRows
}

private fun bodyweightRampSets(
    warmUpCue: String,
    statusLabel: String
): List<WarmUpRampSet> {
    val prepSet = WarmUpRampSet(
        label = "General prep",
        loadCue = "no working load",
        repsCue = "5-10 minutes",
        effortCue = "easy breathing, joints warming",
        purpose = warmUpCue
    )
    val rows = when (statusLabel) {
        "Pain-limited ramp", "Deload-style ramp" -> listOf(
            WarmUpRampSet("Ramp set 1", "easier variation or assistance", "8-10 reps", "RIR 5+", "Confirm pain-free range and setup."),
            WarmUpRampSet("Ramp set 2", "planned setup, partial effort", "4-6 reps", "RIR 4+", "Check bracing, tempo, and target-muscle feel.")
        )
        "Conservative ramp", "Reduced-stress ramp", "Technique-check ramp" -> listOf(
            WarmUpRampSet("Ramp set 1", "easier variation or assistance", "8-10 reps", "RIR 5+", "Learn the path without fatigue."),
            WarmUpRampSet("Ramp set 2", "planned setup", "5-6 reps", "RIR 4+", "Confirm full range and stable control."),
            WarmUpRampSet("Ramp set 3", "planned setup", "2-3 reps", "RIR 3+", "Final ramp set: decide whether the first working set is safe.")
        )
        else -> listOf(
            WarmUpRampSet("Ramp set 1", "easier variation or assistance", "8-10 reps", "RIR 5+", "Rehearse the pattern and breathing."),
            WarmUpRampSet("Ramp set 2", "planned setup", "3-5 reps", "RIR 3-4", "Final ramp set: confirm control before the first working set.")
        )
    }
    return listOf(prepSet) + rows
}

private fun rampRepsFor(index: Int, lastIndex: Int, statusLabel: String): String {
    if (statusLabel == "Pain-limited ramp" || statusLabel == "Deload-style ramp") {
        return when (index) {
            0 -> "6-8 reps"
            1 -> "4-5 reps"
            else -> "2-3 reps"
        }
    }
    return when {
        index == 0 -> "8-10 reps"
        index == lastIndex -> "1-3 reps"
        index == lastIndex - 1 -> "3-5 reps"
        else -> "5-6 reps"
    }
}

private fun rampEffortFor(index: Int, lastIndex: Int, statusLabel: String): String {
    if (statusLabel == "Pain-limited ramp" || statusLabel == "Deload-style ramp") {
        return if (index == lastIndex) "RIR 5+, stop before strain" else "RIR 6+, easy"
    }
    return when {
        index == lastIndex -> "RIR 3-5, never a grinder"
        index == lastIndex - 1 -> "RIR 4-5, crisp speed"
        else -> "RIR 5+, easy"
    }
}

private fun rampPurposeFor(index: Int, lastIndex: Int, statusLabel: String): String {
    return when {
        statusLabel == "Pain-limited ramp" -> "Pain and range check before deciding whether to swap."
        statusLabel == "Deload-style ramp" -> "Technique practice only; do not use this to justify progression."
        index == lastIndex -> "Final ramp set: working-load gate for speed, control, target-muscle feel, and pain-free range."
        index == 0 -> "Groove the equipment setup and the movement path."
        else -> "Bridge toward the planned load while preserving clean technique."
    }
}

private fun Double.rampLoad(percent: Double): String {
    val raw = this * percent
    val rounded = when {
        this >= 60.0 -> (raw / 2.5).roundToInt() * 2.5
        this >= 20.0 -> raw.roundToInt().toDouble()
        else -> (raw * 2.0).roundToInt() / 2.0
    }.coerceAtLeast(0.5)
    return rounded.formatRampDecimal()
}

private fun Double.formatRampDecimal(): String {
    return if (this % 1.0 == 0.0) {
        toInt().toString()
    } else {
        "%.1f".format(Locale.US, this)
    }
}
