package com.iwanttobeanifbbpro.app.core

import com.iwanttobeanifbbpro.app.data.DailyLog
import com.iwanttobeanifbbpro.app.data.ExerciseEntry
import com.iwanttobeanifbbpro.app.data.PlannedExercise
import com.iwanttobeanifbbpro.app.data.SetEntry
import com.iwanttobeanifbbpro.app.data.WeeklyTrainingPlan
import java.util.Locale

data class AiRestPrescription(
    val statusLabel: String,
    val recommendedRestSeconds: Int,
    val minRestSeconds: Int,
    val maxRestSeconds: Int,
    val baseRestSeconds: Int,
    val adjustmentSeconds: Int,
    val timerCue: String,
    val autoAdjustRule: String,
    val nextSessionCarryover: String,
    val evidence: List<String>
) {
    fun promptLine(): String {
        return "AI Rest Prescription: $statusLabel | recommended rest ${recommendedRestSeconds}s | base ${baseRestSeconds}s | range ${minRestSeconds}-${maxRestSeconds}s | adjustment ${adjustmentSeconds}s | restSeconds auto update after AI review | timer cue: $timerCue | auto-adjust rule: $autoAdjustRule | next-session carryover: $nextSessionCarryover | evidence: ${evidence.joinToString("; ")}"
    }
}

fun aiRestPrescription(
    log: DailyLog,
    recentLogs: List<DailyLog>,
    nextSet: NextSetCoach = nextSetCoach(log),
    recovery: RecoveryGuidance = recoveryGuidance(log, recentLogs)
): AiRestPrescription {
    val activeExercise = log.trainingSession.exercises.firstOrNull { exercise ->
        exercise.name == nextSet.currentExerciseName
    } ?: log.trainingSession.exercises.firstOrNull()
    val activeSet = activeExercise
        ?.trackedSets()
        ?.firstOrNull { it.setNumber == nextSet.setNumber }
        ?: activeExercise?.trackedSets()?.firstOrNull { !it.completed }

    if (activeExercise == null || activeSet == null || !nextSet.hasActiveSet) {
        return inactiveRestPrescription()
    }
    return aiRestPrescriptionForSet(
        log = log,
        recentLogs = recentLogs,
        exercise = activeExercise,
        set = activeSet,
        recovery = recovery
    )
}

fun aiRestPrescriptionForSet(
    log: DailyLog,
    recentLogs: List<DailyLog>,
    exercise: ExerciseEntry,
    set: SetEntry,
    recovery: RecoveryGuidance = recoveryGuidance(log, recentLogs)
): AiRestPrescription {
    return buildRestPrescription(
        log = log,
        recentLogs = recentLogs,
        exerciseName = exercise.name,
        targetMuscle = exercise.targetMuscle,
        targetReps = set.targetReps.ifBlank { exercise.reps },
        plannedRir = set.rir ?: exercise.rir,
        actualReps = set.actualReps,
        actualRir = set.rir,
        baseRestSeconds = set.restSeconds.takeIf { it > 0 } ?: exercise.restSeconds,
        notes = "${exercise.notes} ${set.notes}",
        recovery = recovery
    )
}

fun aiRestPrescriptionForPlannedExercise(
    log: DailyLog,
    recentLogs: List<DailyLog>,
    exercise: PlannedExercise,
    recovery: RecoveryGuidance = recoveryGuidance(log, recentLogs)
): AiRestPrescription {
    return buildRestPrescription(
        log = log,
        recentLogs = recentLogs,
        exerciseName = exercise.name,
        targetMuscle = exercise.targetMuscle,
        targetReps = exercise.reps,
        plannedRir = exercise.rir,
        actualReps = null,
        actualRir = exercise.rir,
        baseRestSeconds = exercise.restSeconds,
        notes = exercise.notes,
        recovery = recovery
    )
}

fun DailyLog.withAiMatchedRestTargets(recentLogs: List<DailyLog>): DailyLog {
    val recovery = recoveryGuidance(this, recentLogs)
    val updatedExercises = trainingSession.exercises.map { exercise ->
        val updatedSets = exercise.trackedSets().map { set ->
            if (set.completed) {
                set
            } else {
                val prescription = aiRestPrescriptionForSet(
                    log = this,
                    recentLogs = recentLogs,
                    exercise = exercise,
                    set = set,
                    recovery = recovery
                )
                set.copy(restSeconds = prescription.recommendedRestSeconds)
            }
        }
        val nextRest = updatedSets.firstOrNull { !it.completed }?.restSeconds ?: exercise.restSeconds
        exercise.copy(restSeconds = nextRest, setEntries = updatedSets)
    }
    return copy(trainingSession = trainingSession.copy(exercises = updatedExercises))
}

fun WeeklyTrainingPlan.withAiMatchedRestTargets(
    log: DailyLog,
    recentLogs: List<DailyLog>
): WeeklyTrainingPlan {
    val recovery = recoveryGuidance(log, recentLogs)
    return copy(
        days = days.map { day ->
            day.copy(
                exercises = day.exercises.map { exercise ->
                    val prescription = aiRestPrescriptionForPlannedExercise(
                        log = log,
                        recentLogs = recentLogs,
                        exercise = exercise,
                        recovery = recovery
                    )
                    exercise.copy(restSeconds = prescription.recommendedRestSeconds)
                }
            )
        }
    )
}

private fun buildRestPrescription(
    log: DailyLog,
    recentLogs: List<DailyLog>,
    exerciseName: String,
    targetMuscle: String,
    targetReps: String,
    plannedRir: Double?,
    actualReps: Int?,
    actualRir: Double?,
    baseRestSeconds: Int,
    notes: String,
    recovery: RecoveryGuidance
): AiRestPrescription {
    val cleanName = exerciseName.lowercase(Locale.US)
    val cleanNotes = notes.lowercase(Locale.US)
    val lowRep = targetReps.lowestRepTarget()
    val highRep = targetReps.highestRepTarget()
    val heavyOrCompound = isHeavyOrCompound(cleanName, targetMuscle, lowRep)
    val pumpOrIsolation = isPumpOrIsolation(cleanName, targetMuscle, highRep)
    val base = baseRestSeconds.coerceIn(30, 600)
    var recommended = base
    val reasons = mutableListOf<String>()

    if (heavyOrCompound) {
        recommended += 30
        reasons += "heavy compound or low-rep work needs fuller ATP-PC recovery"
    }
    if (pumpOrIsolation) {
        recommended -= 15
        reasons += "isolation or high-rep pump work can use shorter pacing if form stays clean"
    }
    if (actualRir != null) {
        when {
            actualRir <= 0.5 -> {
                recommended += 45
                reasons += "last set was at or near failure"
            }
            actualRir <= 1.0 -> {
                recommended += 30
                reasons += "last set was harder than a normal hypertrophy target"
            }
            actualRir >= 4.0 && pumpOrIsolation -> {
                recommended -= 15
                reasons += "effort was easy enough to keep isolation pacing tighter"
            }
        }
    }
    if (actualReps != null && lowRep != null && actualReps < lowRep) {
        recommended += 30
        reasons += "actual reps missed the low end of the target"
    }
    if (cleanNotes.hasRestPainOrTechniqueSignal()) {
        recommended += 45
        reasons += "pain or technique notes require a longer reset and possible substitution"
    }
    when (recovery.statusLabel) {
        "Controlled push" -> reasons += "recovery supports normal pacing"
        "Hold training stress" -> {
            recommended += 15
            reasons += "recovery says hold stress, so rest is slightly extended"
        }
        "Reduce volume" -> {
            recommended += 30
            reasons += "recovery says reduce volume, so fatigue must clear before the next set"
        }
        else -> {
            recommended += 45
            reasons += "deload-style recovery signal requires conservative rest"
        }
    }
    val sleepHours = log.metrics.sleepHours
    if (sleepHours != null && sleepHours < 6.5) {
        recommended += 30
        reasons += "short sleep reduces readiness"
    }
    if (log.metrics.soreness >= 4 || log.metrics.fatigue >= 4 || log.metrics.stress >= 4) {
        recommended += 15
        reasons += "subjective fatigue, stress, or soreness is elevated"
    }
    val recentAverageRest = recentLogs
        .flatMap { it.trainingSession.exercises }
        .flatMap { it.trackedSets() }
        .filter { it.completed && it.restSeconds > 0 }
        .takeIf { it.size >= 4 }
        ?.map { it.restSeconds }
        ?.average()
    if (recentAverageRest != null) {
        reasons += "recent completed rest average ${recentAverageRest.roundSeconds()}s is available"
    }

    val minRest = when {
        heavyOrCompound -> 120
        pumpOrIsolation -> 45
        else -> 75
    }
    val maxRest = when {
        recovery.statusLabel == "Reduce volume" || recovery.statusLabel == "Deload check" -> 300
        heavyOrCompound -> 240
        else -> 180
    }
    val finalRest = recommended.roundToNearest15().coerceIn(minRest, maxRest)
    val adjustment = finalRest - base
    val statusLabel = when {
        adjustment >= 45 -> "Extend rest"
        adjustment >= 15 -> "Slightly extend"
        adjustment <= -15 -> "Tighter pacing"
        else -> "Keep rest"
    }
    val timerCue = when (statusLabel) {
        "Extend rest" -> "Start ${finalRest}s after Complete; only begin early if breathing, setup, and target-muscle control are fully back."
        "Slightly extend" -> "Start ${finalRest}s after Complete; use the extra time to repeat setup and brace before the next set."
        "Tighter pacing" -> "Start ${finalRest}s after Complete; keep the pump but do not trade form for speed."
        else -> "Start ${finalRest}s after Complete and keep the planned hypertrophy rhythm."
    }
    val autoAdjustRule = "After every completed set, compare actual reps, RIR, pain/technique notes, sleep, soreness, fatigue, stress, resting HR, and session completion before setting the next countdown."
    val carryover = "After AI review, write ${finalRest}s into matching upcoming plan sets unless recovery or exercise history changes the prescription."
    val evidence = listOf(
        "exercise $exerciseName, target muscle ${targetMuscle.ifBlank { "not logged" }}",
        "target reps ${targetReps.ifBlank { "not logged" }}, planned RIR ${plannedRir?.formatRestDecimal() ?: "not logged"}",
        "actual reps ${actualReps ?: "not logged"}, actual RIR ${actualRir?.formatRestDecimal() ?: "not logged"}",
        "recovery ${recovery.statusLabel}, score ${recovery.readinessScore}, sleep ${sleepHours?.formatRestDecimal() ?: "not logged"}h",
        "fatigue ${log.metrics.fatigue}/5, soreness ${log.metrics.soreness}/5, stress ${log.metrics.stress}/5",
        "resting HR ${log.metrics.restingHeartRateBpm?.formatRestDecimal() ?: "not logged"} bpm"
    ) + reasons.take(5)

    return AiRestPrescription(
        statusLabel = statusLabel,
        recommendedRestSeconds = finalRest,
        minRestSeconds = minRest,
        maxRestSeconds = maxRest,
        baseRestSeconds = base,
        adjustmentSeconds = adjustment,
        timerCue = timerCue,
        autoAdjustRule = autoAdjustRule,
        nextSessionCarryover = carryover,
        evidence = evidence
    )
}

private fun inactiveRestPrescription(): AiRestPrescription {
    return AiRestPrescription(
        statusLabel = "No active rest target",
        recommendedRestSeconds = 0,
        minRestSeconds = 0,
        maxRestSeconds = 0,
        baseRestSeconds = 0,
        adjustmentSeconds = 0,
        timerCue = "Complete a planned set before the AI rest countdown starts.",
        autoAdjustRule = "Load a workout with target reps, RIR, and recovery data before matching rest time.",
        nextSessionCarryover = "No next-session rest update is available until a training plan exists.",
        evidence = listOf("no active set")
    )
}

private fun isHeavyOrCompound(name: String, targetMuscle: String, lowRep: Int?): Boolean {
    val target = targetMuscle.lowercase(Locale.US)
    val compoundNames = listOf(
        "squat",
        "deadlift",
        "press",
        "row",
        "pull-up",
        "pulldown",
        "leg press",
        "hack squat",
        "rdl",
        "romanian"
    )
    return lowRep != null && lowRep <= 8 ||
        compoundNames.any { name.contains(it) } ||
        listOf("quads", "hamstrings", "back", "chest", "glutes").any { target.contains(it) }
}

private fun isPumpOrIsolation(name: String, targetMuscle: String, highRep: Int?): Boolean {
    val target = targetMuscle.lowercase(Locale.US)
    val isolationNames = listOf(
        "curl",
        "raise",
        "fly",
        "extension",
        "pushdown",
        "face pull",
        "calf",
        "crunch"
    )
    return highRep != null && highRep >= 15 ||
        isolationNames.any { name.contains(it) } ||
        listOf("biceps", "triceps", "side delt", "rear delt", "calves", "abs").any { target.contains(it) }
}

private fun String.lowestRepTarget(): Int? {
    return Regex("\\d+").findAll(this).mapNotNull { it.value.toIntOrNull() }.minOrNull()
}

private fun String.highestRepTarget(): Int? {
    return Regex("\\d+").findAll(this).mapNotNull { it.value.toIntOrNull() }.maxOrNull()
}

private fun String.hasRestPainOrTechniqueSignal(): Boolean {
    val clean = replace("pain-free", "painfree")
        .replace("pain free", "painfree")
        .replace("no pain", "nopain")
    return listOf(
        "pain",
        "ache",
        "sharp",
        "unstable",
        "form break",
        "technique",
        "compensation",
        "grind",
        "missed",
        "failed"
    ).any { clean.contains(it) }
}

private fun Int.roundToNearest15(): Int {
    return ((this + 7) / 15) * 15
}

private fun Double.roundSeconds(): String {
    return "%.0f".format(Locale.US, this)
}

private fun Double.formatRestDecimal(): String {
    return if (this % 1.0 == 0.0) {
        toInt().toString()
    } else {
        "%.1f".format(Locale.US, this)
    }
}
