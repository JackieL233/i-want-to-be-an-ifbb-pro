package com.iwanttobeanifbbpro.app.core

import com.iwanttobeanifbbpro.app.data.ExerciseEntry
import java.util.Locale

data class ProgressionCue(
    val label: String,
    val reason: String,
    val nextAction: String
)

fun ExerciseEntry.progressionCue(): ProgressionCue {
    val sets = trackedSets()
    val completedSets = sets.filter { it.completed }
    if (sets.isEmpty()) {
        return ProgressionCue(
            label = "Plan first",
            reason = "No working sets are available for this movement.",
            nextAction = "Add set targets before judging progression."
        )
    }
    if (completedSets.isEmpty()) {
        return ProgressionCue(
            label = "Not enough data",
            reason = "No completed working sets yet.",
            nextAction = "Complete at least one set with reps, load, and RIR."
        )
    }

    val notesText = (notes + " " + completedSets.joinToString(" ") { it.notes }).lowercase(Locale.US)
    if (notesText.hasPainSignal()) {
        return ProgressionCue(
            label = "Modify or hold",
            reason = "Pain or discomfort was logged for this movement.",
            nextAction = "Hold load, reduce range, or swap the exercise before adding volume."
        )
    }

    val completionRate = completedSets.size.toDouble() / sets.size.coerceAtLeast(1)
    val avgRir = completedSets.mapNotNull { it.rir }.takeIf { it.isNotEmpty() }?.average()
    val repsLogged = completedSets.mapNotNull { it.actualReps }
    val targetHigh = reps.extractHighestTargetRep()
    val allAtTopReps = targetHigh != null &&
        repsLogged.size == completedSets.size &&
        repsLogged.all { it >= targetHigh }

    return when {
        completionRate < 0.75 -> ProgressionCue(
            label = "Finish baseline",
            reason = "Less than 75% of planned sets are completed.",
            nextAction = "Finish planned sets before changing load."
        )

        allAtTopReps && (avgRir == null || avgRir >= 1.0) -> ProgressionCue(
            label = "Add load next time",
            reason = "Completed sets reached the top of the target rep range with recoverable effort.",
            nextAction = "Increase load slightly next time and keep the same rep target."
        )

        avgRir != null && avgRir >= 2.0 -> ProgressionCue(
            label = "Add reps first",
            reason = "Effort is still conservative based on logged RIR.",
            nextAction = "Add 1-2 reps across sets before increasing load."
        )

        avgRir != null && avgRir <= 0.5 -> ProgressionCue(
            label = "Hold load",
            reason = "Sets were very close to failure.",
            nextAction = "Repeat load until reps stabilize or recovery improves."
        )

        else -> ProgressionCue(
            label = "Repeat and refine",
            reason = "Performance is usable but not clearly ready for load progression.",
            nextAction = "Keep load stable, improve technique, and chase one more clean rep."
        )
    }
}

private fun String.extractHighestTargetRep(): Int? {
    return Regex("\\d+").findAll(this).mapNotNull { it.value.toIntOrNull() }.maxOrNull()
}

private fun String.hasPainSignal(): Boolean {
    val clean = replace("pain-free", "painfree")
        .replace("pain free", "painfree")
        .replace("no pain", "nopain")
        .replace("without pain", "withoutpain")
        .replace("\u75bc\u75db\u306a\u3057", "nopain")
        .replace("\u65e0\u75db", "nopain")
        .replace("\u6c92\u6709\u75db", "nopain")
        .replace("\u6ca1\u6709\u75db", "nopain")
        .replace("\u4e0d\u75db", "nopain")
        .replace("\u4e0d\u75bc", "nopain")
    return clean.contains("pain") || clean.contains("\u75bc") || clean.contains("\u75db")
}
