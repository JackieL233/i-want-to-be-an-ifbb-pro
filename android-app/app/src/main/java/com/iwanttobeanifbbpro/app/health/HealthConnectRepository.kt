package com.iwanttobeanifbbpro.app.health

import android.content.Context
import androidx.activity.result.contract.ActivityResultContract
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.PermissionController
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.BodyFatRecord
import androidx.health.connect.client.records.LeanBodyMassRecord
import androidx.health.connect.client.records.Record
import androidx.health.connect.client.records.RestingHeartRateRecord
import androidx.health.connect.client.records.SleepSessionRecord
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.records.TotalCaloriesBurnedRecord
import androidx.health.connect.client.records.WeightRecord
import androidx.health.connect.client.request.AggregateRequest
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

class HealthConnectRepository(private val context: Context) {
    private val zoneId: ZoneId = ZoneId.systemDefault()

    fun permissions(): Set<String> = READ_PERMISSIONS

    fun availabilityMessage(): String {
        return when (HealthConnectClient.getSdkStatus(context)) {
            HealthConnectClient.SDK_AVAILABLE -> "Health Connect ready."
            HealthConnectClient.SDK_UNAVAILABLE_PROVIDER_UPDATE_REQUIRED ->
                "Health Connect needs to be installed or updated before syncing."
            else -> "Health Connect is not available on this device."
        }
    }

    suspend fun permissionSnapshot(): HealthSnapshot {
        if (!isAvailable()) {
            return HealthSnapshot(message = availabilityMessage())
        }
        val granted = client().permissionController.getGrantedPermissions()
        val missing = READ_PERMISSIONS - granted
        return HealthSnapshot(
            available = true,
            permissionsGranted = missing.isEmpty(),
            message = if (missing.isEmpty()) {
                "Health Connect permissions granted."
            } else {
                "Health Connect permissions needed: ${missing.size} remaining."
            }
        )
    }

    suspend fun readTodaySnapshot(): HealthSnapshot {
        if (!isAvailable()) {
            return HealthSnapshot(message = availabilityMessage())
        }

        val client = client()
        val granted = client.permissionController.getGrantedPermissions()
        if (READ_PERMISSIONS.none { it in granted }) {
            return HealthSnapshot(
                available = true,
                permissionsGranted = false,
                message = "Connect health data before syncing body and recovery metrics."
            )
        }

        val now = Instant.now()
        val startOfDay = LocalDate.now(zoneId).atStartOfDay(zoneId).toInstant()
        val bodyLookbackStart = now.minus(Duration.ofDays(30))
        val sleepLookbackStart = startOfDay.minus(Duration.ofHours(12))

        return runCatching {
            val weight = readLatest(
                client = client,
                permission = HealthPermission.getReadPermission(WeightRecord::class),
                granted = granted,
                startTime = bodyLookbackStart,
                endTime = now,
                timeOf = { it.time }
            )?.weight?.inKilograms

            val bodyFat = readLatest(
                client = client,
                permission = HealthPermission.getReadPermission(BodyFatRecord::class),
                granted = granted,
                startTime = bodyLookbackStart,
                endTime = now,
                timeOf = { it.time }
            )?.percentage?.value

            val leanMass = readLatest(
                client = client,
                permission = HealthPermission.getReadPermission(LeanBodyMassRecord::class),
                granted = granted,
                startTime = bodyLookbackStart,
                endTime = now,
                timeOf = { it.time }
            )?.mass?.inKilograms

            val restingHeartRate = readLatest(
                client = client,
                permission = HealthPermission.getReadPermission(RestingHeartRateRecord::class),
                granted = granted,
                startTime = bodyLookbackStart,
                endTime = now,
                timeOf = { it.time }
            )?.beatsPerMinute?.toDouble()

            val sleepHours = readRecordsIfGranted<SleepSessionRecord>(
                client = client,
                permission = HealthPermission.getReadPermission(SleepSessionRecord::class),
                granted = granted,
                startTime = sleepLookbackStart,
                endTime = now
            ).sumOf { record ->
                Duration.between(record.startTime, record.endTime).toMinutes().coerceAtLeast(0)
            }.takeIf { it > 0 }?.let { minutes -> minutes / 60.0 }

            val steps = readSteps(client, granted, startOfDay, now)
            val calories = readTotalCalories(client, granted, startOfDay, now)
            val message = if (listOf(weight, bodyFat, leanMass, sleepHours, restingHeartRate, calories).any { it != null } || steps != null) {
                "Synced Health Connect metrics. Xiaomi, Huawei, scale, watch, and phone data can appear here when the source app writes compatible records into Health Connect."
            } else {
                "Connected, but no supported Health Connect records were found for today's sync window."
            }

            HealthSnapshot(
                available = true,
                permissionsGranted = READ_PERMISSIONS.all { it in granted },
                bodyWeightKg = weight,
                bodyFatPercent = bodyFat,
                leanBodyMassKg = leanMass,
                steps = steps,
                sleepHours = sleepHours,
                restingHeartRateBpm = restingHeartRate,
                totalCaloriesBurnedKcal = calories,
                syncedAt = now.toString(),
                message = message
            )
        }.getOrElse { error ->
            HealthSnapshot(
                available = true,
                permissionsGranted = READ_PERMISSIONS.all { it in granted },
                message = error.message ?: "Health Connect sync failed."
            )
        }
    }

    private fun isAvailable(): Boolean {
        return HealthConnectClient.getSdkStatus(context) == HealthConnectClient.SDK_AVAILABLE
    }

    private fun client(): HealthConnectClient = HealthConnectClient.getOrCreate(context)

    private suspend fun readSteps(
        client: HealthConnectClient,
        granted: Set<String>,
        startTime: Instant,
        endTime: Instant
    ): Long? {
        val permission = HealthPermission.getReadPermission(StepsRecord::class)
        if (permission !in granted) return null
        return client.aggregate(
            AggregateRequest(
                metrics = setOf(StepsRecord.COUNT_TOTAL),
                timeRangeFilter = TimeRangeFilter.between(startTime, endTime)
            )
        )[StepsRecord.COUNT_TOTAL]
    }

    private suspend fun readTotalCalories(
        client: HealthConnectClient,
        granted: Set<String>,
        startTime: Instant,
        endTime: Instant
    ): Double? {
        val permission = HealthPermission.getReadPermission(TotalCaloriesBurnedRecord::class)
        if (permission !in granted) return null
        return client.aggregate(
            AggregateRequest(
                metrics = setOf(TotalCaloriesBurnedRecord.ENERGY_TOTAL),
                timeRangeFilter = TimeRangeFilter.between(startTime, endTime)
            )
        )[TotalCaloriesBurnedRecord.ENERGY_TOTAL]?.inKilocalories
    }

    private suspend inline fun <reified T : Record> readLatest(
        client: HealthConnectClient,
        permission: String,
        granted: Set<String>,
        startTime: Instant,
        endTime: Instant,
        crossinline timeOf: (T) -> Instant
    ): T? {
        return readRecordsIfGranted<T>(client, permission, granted, startTime, endTime).maxByOrNull { timeOf(it) }
    }

    private suspend inline fun <reified T : Record> readRecordsIfGranted(
        client: HealthConnectClient,
        permission: String,
        granted: Set<String>,
        startTime: Instant,
        endTime: Instant
    ): List<T> {
        if (permission !in granted) return emptyList()
        val records = mutableListOf<T>()
        var pageToken: String? = null
        do {
            val response = client.readRecords(
                ReadRecordsRequest(
                    recordType = T::class,
                    timeRangeFilter = TimeRangeFilter.between(startTime, endTime),
                    pageToken = pageToken
                )
            )
            records += response.records
            pageToken = response.pageToken
        } while (pageToken != null)
        return records
    }

    companion object {
        val READ_PERMISSIONS: Set<String> = setOf(
            HealthPermission.getReadPermission(WeightRecord::class),
            HealthPermission.getReadPermission(BodyFatRecord::class),
            HealthPermission.getReadPermission(LeanBodyMassRecord::class),
            HealthPermission.getReadPermission(StepsRecord::class),
            HealthPermission.getReadPermission(SleepSessionRecord::class),
            HealthPermission.getReadPermission(RestingHeartRateRecord::class),
            HealthPermission.getReadPermission(TotalCaloriesBurnedRecord::class)
        )

        fun permissionContract(): ActivityResultContract<Set<String>, Set<String>> {
            return PermissionController.createRequestPermissionResultContract()
        }
    }
}
