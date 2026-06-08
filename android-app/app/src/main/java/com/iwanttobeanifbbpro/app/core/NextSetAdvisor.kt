package com.iwanttobeanifbbpro.app.core

import com.iwanttobeanifbbpro.app.data.DailyLog
import com.iwanttobeanifbbpro.app.data.ExerciseEntry
import com.iwanttobeanifbbpro.app.data.SetEntry
import java.util.Locale

data class NextSetCoach(
    val statusLabel: String,
    val currentExerciseName: String,
    val targetMuscle: String,
    val visualSpec: ExerciseVisualSpec,
    val setNumber: Int,
    val totalSets: Int,
    val targetReps: String,
    val plannedLoadKg: Double?,
    val plannedRir: Double?,
    val restSeconds: Int,
    val loadCue: String,
    val repsCue: String,
    val rirCue: String,
    val restCue: String,
    val executionCue: String,
    val stopCue: String,
    val afterSetLoggingCue: String,
    val primaryAction: String,
    val aiReviewFocus: String
) {
    val hasActiveSet: Boolean
        get() = setNumber > 0 && currentExerciseName.isNotBlank()

    fun promptLine(): String {
        val setLabel = if (hasActiveSet) {
            "set $setNumber/$totalSets, target reps ${targetReps.ifBlank { "not specified" }}, planned load ${plannedLoadKg?.formatPromptDecimal() ?: "bodyweight or not specified"} kg, planned RIR ${plannedRir?.formatPromptDecimal() ?: "not specified"}, rest ${restSeconds}s"
        } else {
            "no active set"
        }
        return "Next Set Coach: $statusLabel | Current exercise: ${currentExerciseName.ifBlank { "none" }} | Target muscle: ${targetMuscle.ifBlank { "not specified" }} | Next set target: $setLabel | Visual guide: ${visualSpec.visualPromptLine()} | Load cue: $loadCue | Reps cue: $repsCue | RIR cue: $rirCue | Rest cue: $restCue | Execution cue: $executionCue | Stop cue: $stopCue | After-set logging cue: $afterSetLoggingCue | Primary action: $primaryAction | AI review focus: $aiReviewFocus"
    }
}

fun nextSetCoach(log: DailyLog): NextSetCoach {
    val exercises = log.trainingSession.exercises
    if (exercises.isEmpty() || log.plannedHardSets() == 0) {
        return inactiveNextSetCoach(
            statusLabel = "No loaded session",
            primaryAction = "Apply a planned day or add exercises before starting the set-by-set workout.",
            executionCue = "Use the Plan tab first so the app can create exercise rows, target reps, load, RIR, and rest timers.",
            stopCue = "Do not improvise heavy work without a planned target, warm-up, and safe setup."
        )
    }

    exercises.forEach { exercise ->
        val sets = exercise.trackedSets()
        val setIndex = sets.indexOfFirst { !it.completed }
        if (setIndex >= 0) {
            return buildActiveNextSetCoach(
                exercise = exercise,
                sets = sets,
                setIndex = setIndex
            )
        }
    }

    val lastExercise = exercises.lastOrNull()
    return inactiveNextSetCoach(
        statusLabel = "All sets complete",
        currentExerciseName = lastExercise?.name.orEmpty(),
        targetMuscle = lastExercise?.targetMuscle.orEmpty(),
        primaryAction = "Mark training completed, review session notes, then run AI review.",
        executionCue = "The next training decision should come from Session Quality Dashboard, Exercise History, Progression Cue, and recovery signals.",
        stopCue = "Do not add unplanned bonus sets unless the program explicitly calls for them and recovery is excellent."
    )
}

private fun buildActiveNextSetCoach(
    exercise: ExerciseEntry,
    sets: List<SetEntry>,
    setIndex: Int
): NextSetCoach {
    val set = sets[setIndex]
    val previousSets = sets.take(setIndex).filter { it.completed }
    val previousSet = previousSets.lastOrNull()
    val plannedLoad = set.loadKg ?: exercise.loadKg
    val plannedRir = set.rir ?: exercise.rir
    val targetReps = set.targetReps.ifBlank { exercise.reps }
    val visual = exerciseVisualSpec(exercise.name, exercise.targetMuscle)
    val notesText = "${exercise.notes} ${previousSets.joinToString(" ") { it.notes }}".lowercase(Locale.US)
    val hasPain = notesText.hasNextSetPainSignal()
    val hasTechnique = notesText.hasNextSetTechniqueFlag()
    val targetLow = targetReps.extractLowestTargetRep()
    val targetHigh = targetReps.extractHighestTargetRep()
    val missedLowEnd = targetLow != null && previousSet?.actualReps != null && previousSet.actualReps < targetLow
    val reachedTop = targetHigh != null && previousSet?.actualReps != null && previousSet.actualReps >= targetHigh
    val previousRir = previousSet?.rir

    val status = when {
        hasPain -> "Pain check"
        hasTechnique -> "Technique check"
        previousSet == null -> "Ready for first set"
        previousRir != null && previousRir <= 0.5 -> "Manage fatigue"
        missedLowEnd -> "Hold or reduce"
        else -> "Continue next set"
    }
    val loadCue = when {
        hasPain -> "Hold or reduce the load; do not progress while pain is logged."
        hasTechnique -> "Hold the same load until the movement path is clean and repeatable."
        previousSet == null && plannedLoad != null -> "Open at ${plannedLoad.formatPromptDecimal()} kg only if warm-up and setup feel stable."
        previousSet == null -> "Use the planned bodyweight or machine setting and make the first work set controlled."
        previousRir != null && previousRir <= 0.5 -> "Repeat or reduce ${plannedLoad?.formatPromptDecimal() ?: "the"} load because the last set was too close to failure."
        missedLowEnd -> "Keep load stable or reduce slightly until the low end of the rep target is reliable."
        reachedTop && previousRir != null && previousRir >= 2.0 -> "Keep the load for today's remaining sets; consider adding load next session only if all sets stay clean."
        previousRir != null && previousRir >= 3.0 -> "Keep load stable and chase controlled reps before adding weight."
        plannedLoad != null -> "Use ${plannedLoad.formatPromptDecimal()} kg and judge the set by reps plus honest RIR."
        else -> "Use the planned setup; log the exact machine stack, band, or bodyweight variation in notes."
    }
    val repsCue = when {
        targetReps.isBlank() -> "Use the planned rep target from the program and log actual reps before completing the set."
        missedLowEnd -> "Aim for the low end of $targetReps with cleaner control before chasing more reps."
        reachedTop -> "Try to repeat the top-end reps with the same form standard."
        else -> "Aim for $targetReps clean reps with the target muscle doing the work."
    }
    val rirCue = when {
        plannedRir != null -> "Stop around RIR ${plannedRir.formatPromptDecimal()}; if it feels more than 1 RIR harder than planned, hold or reduce the next set."
        else -> "Log honest RIR after the set so AI can compare effort and recovery."
    }
    val restCue = "After tapping Complete, rest ${set.restSeconds}s before the next working set."
    val executionCue = if (previousSet == null) {
        "Find ${visual.visualId} ${visual.equipment} (${visual.equipmentZh}), match the setup cue, then start only after warm-up confirms pain-free range."
    } else {
        "Repeat the same setup and ${visual.actionPathCue.lowercase(Locale.US)}; compare speed, control, and target-muscle feel against set ${previousSet.setNumber}."
    }
    val stopCue = when {
        hasPain -> "Stop or swap the exercise if pain changes range, bracing, or target-muscle control."
        hasTechnique -> "Stop adding load if compensation, unstable range, or form breakdown continues."
        else -> "Stop or modify if sharp pain appears, reps miss the target by more than 2, or RIR lands more than 1 harder than planned."
    }
    val loggingCue = "Before tapping Complete, log kg, reps, RIR, and notes; attach an equipment/form photo in AI Coach if the setup is uncertain."
    val primaryAction = "Do set ${set.setNumber}/${sets.size}: ${exercise.name}, $targetReps reps, ${plannedLoad?.formatPromptDecimal() ?: "bodyweight or planned"} kg, RIR ${plannedRir?.formatPromptDecimal() ?: "--"}."
    val aiFocus = "Compare this next-set target, visual guide ID, actual load, reps, RIR, rest time, pain/technique notes, and Exercise History before changing progression."

    return NextSetCoach(
        statusLabel = status,
        currentExerciseName = exercise.name,
        targetMuscle = exercise.targetMuscle,
        visualSpec = visual,
        setNumber = set.setNumber,
        totalSets = sets.size,
        targetReps = targetReps,
        plannedLoadKg = plannedLoad,
        plannedRir = plannedRir,
        restSeconds = set.restSeconds,
        loadCue = loadCue,
        repsCue = repsCue,
        rirCue = rirCue,
        restCue = restCue,
        executionCue = executionCue,
        stopCue = stopCue,
        afterSetLoggingCue = loggingCue,
        primaryAction = primaryAction,
        aiReviewFocus = aiFocus
    )
}

private fun inactiveNextSetCoach(
    statusLabel: String,
    currentExerciseName: String = "",
    targetMuscle: String = "",
    primaryAction: String,
    executionCue: String,
    stopCue: String
): NextSetCoach {
    val visual = exerciseVisualSpec(currentExerciseName, targetMuscle)
    return NextSetCoach(
        statusLabel = statusLabel,
        currentExerciseName = currentExerciseName,
        targetMuscle = targetMuscle,
        visualSpec = visual,
        setNumber = 0,
        totalSets = 0,
        targetReps = "",
        plannedLoadKg = null,
        plannedRir = null,
        restSeconds = 0,
        loadCue = "No next-set load is available yet.",
        repsCue = "No next-set rep target is available yet.",
        rirCue = "No next-set RIR target is available yet.",
        restCue = "No rest timer is active until a set is completed.",
        executionCue = executionCue,
        stopCue = stopCue,
        afterSetLoggingCue = "Keep set logs complete so AI can audit the session later.",
        primaryAction = primaryAction,
        aiReviewFocus = "Check whether the user has a loaded session, completed set logs, and enough data before recommending progression."
    )
}

private fun String.extractLowestTargetRep(): Int? {
    return Regex("\\d+").findAll(this).mapNotNull { it.value.toIntOrNull() }.minOrNull()
}

private fun String.extractHighestTargetRep(): Int? {
    return Regex("\\d+").findAll(this).mapNotNull { it.value.toIntOrNull() }.maxOrNull()
}

private fun String.hasNextSetPainSignal(): Boolean {
    val clean = replace("pain-free", "painfree")
        .replace("pain free", "painfree")
        .replace("no pain", "nopain")
        .replace("without pain", "withoutpain")
        .replace("无痛", "nopain")
        .replace("没有痛", "nopain")
        .replace("不痛", "nopain")
        .replace("不疼", "nopain")
    return clean.contains("pain") ||
        clean.contains("ache") ||
        clean.contains("疼") ||
        clean.contains("痛")
}

private fun String.hasNextSetTechniqueFlag(): Boolean {
    return listOf(
        "form break",
        "technique",
        "unstable",
        "compensation",
        "cheat",
        "grind",
        "failed",
        "missed",
        "range short",
        "失控",
        "代偿",
        "动作变形"
    ).any { contains(it) }
}

private fun Double.formatPromptDecimal(): String {
    return if (this % 1.0 == 0.0) {
        toInt().toString()
    } else {
        "%.1f".format(Locale.US, this)
    }
}
