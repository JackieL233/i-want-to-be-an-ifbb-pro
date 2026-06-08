package com.iwanttobeanifbbpro.app.core

import com.iwanttobeanifbbpro.app.data.DailyLog
import com.iwanttobeanifbbpro.app.data.ExerciseEntry
import java.util.Locale

data class ExerciseHistorySummary(
    val statusLabel: String,
    val previousDate: String?,
    val previousVolumeKg: Double?,
    val currentVolumeKg: Double,
    val previousBestLoadKg: Double?,
    val currentBestLoadKg: Double?,
    val previousBestReps: Int?,
    val currentBestReps: Int?,
    val previousCompletedSets: Int?,
    val currentCompletedSets: Int,
    val previousAverageRir: Double?,
    val currentAverageRir: Double?,
    val guidance: String
)

fun ExerciseEntry.exerciseHistorySummary(currentLog: DailyLog, recentLogs: List<DailyLog>): ExerciseHistorySummary {
    val currentStats = exerciseStats()
    val previous = recentLogs
        .asSequence()
        .filter { it.date != currentLog.date }
        .sortedByDescending { it.date }
        .flatMap { log ->
            log.trainingSession.exercises.asSequence()
                .filter { it.name.normalizedExerciseName() == name.normalizedExerciseName() }
                .map { log.date to it }
        }
        .firstOrNull()
    val previousStats = previous?.second?.exerciseStats()
    val volumeDelta = previousStats?.volumeKg?.let { currentStats.volumeKg - it }
    val statusLabel = when {
        previousStats == null -> "First tracked session"
        currentStats.completedSets == 0 -> "Log sets to compare"
        volumeDelta != null && volumeDelta >= 1.0 -> "Volume up"
        volumeDelta != null && volumeDelta <= -1.0 -> "Volume down"
        currentStats.bestLoadKg != null &&
            previousStats.bestLoadKg != null &&
            currentStats.bestLoadKg > previousStats.bestLoadKg -> "Load PR"
        currentStats.bestReps != null &&
            previousStats.bestReps != null &&
            currentStats.bestReps > previousStats.bestReps -> "Rep PR"
        else -> "Matched last time"
    }
    val guidance = when (statusLabel) {
        "First tracked session" -> "Set a clean baseline today; AI can compare this movement after the next logged session."
        "Log sets to compare" -> "Complete at least one set with load, reps, and RIR to unlock history comparison."
        "Volume up" -> "Progress is moving; confirm recovery before adding more load or sets."
        "Volume down" -> "Check sleep, pain, target muscle stimulus, and whether load/reps should be held."
        "Load PR" -> "Higher load appeared; keep technique and rep quality stable before another jump."
        "Rep PR" -> "More reps appeared; this supports a cautious load increase if RIR stays recoverable."
        else -> "Performance matched last time; chase one cleaner rep, better control, or steadier RIR."
    }
    return ExerciseHistorySummary(
        statusLabel = statusLabel,
        previousDate = previous?.first,
        previousVolumeKg = previousStats?.volumeKg,
        currentVolumeKg = currentStats.volumeKg,
        previousBestLoadKg = previousStats?.bestLoadKg,
        currentBestLoadKg = currentStats.bestLoadKg,
        previousBestReps = previousStats?.bestReps,
        currentBestReps = currentStats.bestReps,
        previousCompletedSets = previousStats?.completedSets,
        currentCompletedSets = currentStats.completedSets,
        previousAverageRir = previousStats?.averageRir,
        currentAverageRir = currentStats.averageRir,
        guidance = guidance
    )
}

private data class ExerciseStats(
    val volumeKg: Double,
    val bestLoadKg: Double?,
    val bestReps: Int?,
    val completedSets: Int,
    val averageRir: Double?
)

private fun ExerciseEntry.exerciseStats(): ExerciseStats {
    val completed = trackedSets().filter { it.completed }
    val rirValues = completed.mapNotNull { it.rir }
    return ExerciseStats(
        volumeKg = volumeKg(),
        bestLoadKg = completed.mapNotNull { it.loadKg }.maxOrNull(),
        bestReps = completed.mapNotNull { it.actualReps }.maxOrNull(),
        completedSets = completed.size,
        averageRir = rirValues.takeIf { it.isNotEmpty() }?.average()
    )
}

private fun String.normalizedExerciseName(): String {
    return lowercase(Locale.US)
        .replace(Regex("[^a-z0-9]+"), " ")
        .trim()
}
