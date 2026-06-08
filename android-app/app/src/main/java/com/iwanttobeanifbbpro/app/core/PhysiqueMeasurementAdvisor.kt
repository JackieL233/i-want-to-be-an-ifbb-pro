package com.iwanttobeanifbbpro.app.core

import com.iwanttobeanifbbpro.app.data.DailyLog
import com.iwanttobeanifbbpro.app.data.DailyMetrics
import kotlin.math.abs

data class PhysiqueMeasurementSummary(
    val statusLabel: String,
    val measurementScore: Int,
    val shoulderWaistRatio: Double?,
    val armDifferenceCm: Double?,
    val thighDifferenceCm: Double?,
    val waistChangeCm: Double?,
    val chestChangeCm: Double?,
    val shoulderChangeCm: Double?,
    val hipChangeCm: Double?,
    val proportionCue: String,
    val symmetryCue: String,
    val trackingCue: String,
    val aiReviewFocus: String
) {
    fun promptLine(): String {
        return "Physique Measurement Summary: $statusLabel, score $measurementScore | waist change ${waistChangeCm ?: "not enough data"} cm, chest change ${chestChangeCm ?: "not enough data"} cm, shoulder change ${shoulderChangeCm ?: "not enough data"} cm, hip change ${hipChangeCm ?: "not enough data"} cm, shoulder-to-waist ratio ${shoulderWaistRatio ?: "not logged"}, arm difference ${armDifferenceCm ?: "not logged"} cm, thigh difference ${thighDifferenceCm ?: "not logged"} cm | Proportion cue: $proportionCue | Symmetry cue: $symmetryCue | Tracking cue: $trackingCue | AI review focus: $aiReviewFocus"
    }
}

fun physiqueMeasurementSummary(log: DailyLog, recentLogs: List<DailyLog> = emptyList()): PhysiqueMeasurementSummary {
    val window = (recentLogs + log).distinctBy { it.date }.sortedBy { it.date }.takeLast(14)
    val metrics = log.metrics
    val loggedCount = metrics.loggedPhysiqueMeasurementCount()
    val waistChange = window.changeOf { it.metrics.waistCm }
    val chestChange = window.changeOf { it.metrics.chestCm }
    val shoulderChange = window.changeOf { it.metrics.shoulderCm }
    val hipChange = window.changeOf { it.metrics.hipCm }
    val shoulderWaistRatio = if (metrics.shoulderCm != null && metrics.waistCm != null && metrics.waistCm > 0.0) {
        metrics.shoulderCm / metrics.waistCm
    } else {
        null
    }
    val armDifference = sideDifference(metrics.leftArmCm, metrics.rightArmCm)
    val thighDifference = sideDifference(metrics.leftThighCm, metrics.rightThighCm)
    val measurementScore = (loggedCount * 11 +
        if (waistChange != null) 12 else 0 +
        if (shoulderWaistRatio != null) 10 else 0 +
        if (armDifference != null) 8 else 0 +
        if (thighDifference != null) 8 else 0).coerceIn(12, 96)
    val statusLabel = when {
        loggedCount == 0 -> "Tape data missing"
        loggedCount < 3 -> "Partial measurements"
        waistChange != null && waistChange > 1.5 && (chestChange ?: 0.0) <= 0.5 && (shoulderChange ?: 0.0) <= 0.5 -> "Waist rising faster"
        armDifference != null && armDifference > 1.0 -> "Arm asymmetry watch"
        thighDifference != null && thighDifference > 1.5 -> "Thigh asymmetry watch"
        measurementScore >= 70 -> "Physique trend ready"
        else -> "Build measurement baseline"
    }
    val proportionCue = when {
        shoulderWaistRatio == null -> "Log shoulder and waist on the same check-in to judge V-taper direction."
        waistChange != null && waistChange > 1.5 && (shoulderChange ?: 0.0) <= 0.5 -> "Waist is moving faster than shoulder width; review calories, steps, and photos before pushing surplus."
        shoulderChange != null && shoulderChange > 0.5 && (waistChange == null || waistChange <= 0.5) -> "Shoulder width is improving while waist is controlled; this supports the physique goal."
        else -> "Use waist, shoulder, chest, hip, and photos together before changing calories or weak-point volume."
    }
    val symmetryCue = when {
        armDifference != null && armDifference > 1.0 -> "Arm side-to-side gap is meaningful; keep unilateral work honest and log left/right stimulus."
        thighDifference != null && thighDifference > 1.5 -> "Thigh side-to-side gap is meaningful; check stance, unilateral work, and injury history."
        armDifference != null || thighDifference != null -> "Left/right symmetry looks trackable; repeat measurements under the same conditions."
        else -> "Log left/right arm and thigh measurements to detect symmetry changes."
    }
    val trackingCue = when {
        loggedCount == 0 -> "Take relaxed morning tape measurements weekly: waist, chest, shoulder, hip, arms, thighs, and neck."
        loggedCount < 5 -> "Add the missing tape points before using measurements for plan-wide changes."
        else -> "Use the same tape position, posture, pump state, and time of day so trends are comparable."
    }
    val aiReviewFocus = "Compare physique measurements with body weight, body fat estimate, photos, calories, training volume, weak points, and recovery before deciding whether to add calories, cut calories, bias weak-point volume, or hold the plan."

    return PhysiqueMeasurementSummary(
        statusLabel = statusLabel,
        measurementScore = measurementScore,
        shoulderWaistRatio = shoulderWaistRatio?.roundOneDecimal(),
        armDifferenceCm = armDifference?.roundOneDecimal(),
        thighDifferenceCm = thighDifference?.roundOneDecimal(),
        waistChangeCm = waistChange?.roundOneDecimal(),
        chestChangeCm = chestChange?.roundOneDecimal(),
        shoulderChangeCm = shoulderChange?.roundOneDecimal(),
        hipChangeCm = hipChange?.roundOneDecimal(),
        proportionCue = proportionCue,
        symmetryCue = symmetryCue,
        trackingCue = trackingCue,
        aiReviewFocus = aiReviewFocus
    )
}

private fun DailyMetrics.loggedPhysiqueMeasurementCount(): Int {
    return listOf(
        waistCm,
        chestCm,
        shoulderCm,
        hipCm,
        leftArmCm,
        rightArmCm,
        leftThighCm,
        rightThighCm,
        neckCm
    ).count { it != null }
}

private fun sideDifference(left: Double?, right: Double?): Double? {
    if (left == null || right == null) return null
    return abs(left - right)
}

private fun List<DailyLog>.changeOf(selector: (DailyLog) -> Double?): Double? {
    val first = firstNotNullOfOrNull(selector)
    val last = asReversed().firstNotNullOfOrNull(selector)
    if (first == null || last == null) return null
    return last - first
}

private fun Double.roundOneDecimal(): Double {
    return kotlin.math.round(this * 10.0) / 10.0
}
