package com.iwanttobeanifbbpro.app.health

import java.time.Instant

data class HealthSnapshot(
    val available: Boolean = false,
    val permissionsGranted: Boolean = false,
    val source: String = "Health Connect",
    val bodyWeightKg: Double? = null,
    val bodyFatPercent: Double? = null,
    val leanBodyMassKg: Double? = null,
    val steps: Long? = null,
    val sleepHours: Double? = null,
    val restingHeartRateBpm: Double? = null,
    val totalCaloriesBurnedKcal: Double? = null,
    val syncedAt: String = Instant.now().toString(),
    val message: String = ""
) {
    fun hasImportableMetrics(): Boolean {
        return listOf(
            bodyWeightKg,
            bodyFatPercent,
            leanBodyMassKg,
            sleepHours,
            restingHeartRateBpm,
            totalCaloriesBurnedKcal
        ).any { it != null } || steps != null
    }
}
