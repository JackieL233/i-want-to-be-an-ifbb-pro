package com.iwanttobeanifbbpro.app.core

import com.iwanttobeanifbbpro.app.data.DailyLog

data class TrainingReadinessBuilder(
    val statusLabel: String,
    val readinessScore: Int,
    val recoveryGate: String,
    val warmUpCue: String,
    val rampUpCue: String,
    val firstWorkingSetCue: String,
    val volumeAdjustmentCue: String,
    val stopRule: String,
    val aiReviewFocus: String
) {
    fun promptLine(): String {
        return "Training Readiness Builder: $statusLabel, readiness score $readinessScore | Recovery gate: $recoveryGate | Warm-up cue: $warmUpCue | Ramp-up cue: $rampUpCue | First working set: $firstWorkingSetCue | Volume adjustment: $volumeAdjustmentCue | Stop rule: $stopRule | AI review focus: $aiReviewFocus"
    }
}

fun trainingReadinessBuilder(log: DailyLog, guidance: RecoveryGuidance): TrainingReadinessBuilder {
    val plannedSets = log.plannedHardSets()
    val completedSets = log.completedHardSets()
    val hasPlannedSession = plannedSets > 0
    val baseFocus = log.trainingSession.plannedFocus.ifBlank { "today's planned session" }
    val progressFocus = "Compare warm-up quality, first working set, completed sets, RIR, pain notes, and Exercise History before changing load or volume."

    return when (guidance.statusLabel) {
        "Controlled push" -> TrainingReadinessBuilder(
            statusLabel = "Ready to execute",
            readinessScore = guidance.readinessScore,
            recoveryGate = if (hasPlannedSession) {
                "Start $baseFocus if joints feel normal and target muscles respond during warm-up."
            } else {
                "Build or apply a plan before pushing; no planned hard sets are loaded yet."
            },
            warmUpCue = "Use 5-8 minutes easy cardio or movement prep, then rehearse the first pattern through full pain-free range.",
            rampUpCue = "For the first main lift, use 2-4 ramp-up sets: light technique set, moderate set, near-work set, then working load.",
            firstWorkingSetCue = "Open at the planned load and stop near planned RIR; progress only if bar speed, control, and target-muscle feel are clean.",
            volumeAdjustmentCue = "Keep planned volume. Add reps or load only where Progression Cue and Exercise History support it.",
            stopRule = "Stop or modify the exercise if sharp pain, unstable range, or compensation appears.",
            aiReviewFocus = progressFocus
        )

        "Hold training stress" -> TrainingReadinessBuilder(
            statusLabel = "Hold before pushing",
            readinessScore = guidance.readinessScore,
            recoveryGate = "Train, but treat today's readiness as a cap: no bonus sets, no surprise failure work.",
            warmUpCue = "Use 8-10 minutes warm-up with extra joint-specific prep for sore areas before loading.",
            rampUpCue = "Add one extra ramp-up set and check whether reps feel smoother before reaching working load.",
            firstWorkingSetCue = "Use planned load only if the near-work warm-up feels stable; otherwise start 2.5-5% lighter.",
            volumeAdjustmentCue = "Keep sets stable or trim isolation work if RIR drops early, technique slows, or soreness climbs.",
            stopRule = "Stop adding load if the first working set misses target reps or lands more than 1 RIR harder than planned.",
            aiReviewFocus = progressFocus
        )

        "Reduce volume" -> TrainingReadinessBuilder(
            statusLabel = "Reduce session stress",
            readinessScore = guidance.readinessScore,
            recoveryGate = "Use the workout for quality practice, not a progression attempt.",
            warmUpCue = "Use 10 minutes gradual warm-up, mobility for restricted joints, and lighter pattern rehearsal.",
            rampUpCue = "Ramp in smaller jumps and repeat the last warm-up if movement quality is uncertain.",
            firstWorkingSetCue = "Start 5-10% lighter than planned or use the low end of the rep range with 2-4 RIR.",
            volumeAdjustmentCue = "Trim 20-30% of hard sets, favor stable machines or cables, and skip optional finishers.",
            stopRule = "Stop the movement if pain changes mechanics, range shortens suddenly, or bracing breaks down.",
            aiReviewFocus = progressFocus
        )

        else -> TrainingReadinessBuilder(
            statusLabel = "Deload check",
            readinessScore = guidance.readinessScore,
            recoveryGate = "Consider rest, technique-only work, or a deload-style session until signals improve.",
            warmUpCue = "Use a gentle 10-12 minute warm-up and only continue if symptoms improve, not worsen.",
            rampUpCue = "Keep ramp-up loads light and stop before any set that feels like a true working set.",
            firstWorkingSetCue = "If you train, use a conservative technique set with 4+ RIR instead of a normal working set.",
            volumeAdjustmentCue = "Cut hard sets by at least 40-50% or replace the session with recovery work.",
            stopRule = "Stop training and seek appropriate help for unusual pain, dizziness, chest symptoms, fainting, or severe fatigue.",
            aiReviewFocus = "$progressFocus Treat red-flag symptoms as outside normal coaching."
        )
    }.let { builder ->
        if (!hasPlannedSession && completedSets == 0) {
            builder.copy(
                volumeAdjustmentCue = "No planned hard sets are loaded; apply a plan day before judging volume adjustment.",
                aiReviewFocus = "${builder.aiReviewFocus} Also check whether the user needs a plan day applied before training."
            )
        } else {
            builder
        }
    }
}
