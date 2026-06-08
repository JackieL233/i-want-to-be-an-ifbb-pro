package com.iwanttobeanifbbpro.app.data

import org.json.JSONArray
import org.json.JSONObject

data class PlannedExercise(
    val name: String,
    val targetMuscle: String,
    val sets: Int,
    val reps: String,
    val loadKg: Double?,
    val rir: Double?,
    val restSeconds: Int,
    val notes: String = ""
) {
    fun toExerciseEntry(): ExerciseEntry {
        val plannedSets = sets.coerceAtLeast(0)
        return ExerciseEntry(
            name = name,
            targetMuscle = targetMuscle,
            sets = plannedSets,
            reps = reps,
            loadKg = loadKg,
            rir = rir,
            notes = notes,
            restSeconds = restSeconds.coerceIn(30, 600),
            setEntries = (1..plannedSets).map { setNumber ->
                SetEntry(
                    setNumber = setNumber,
                    targetReps = reps,
                    actualReps = null,
                    loadKg = loadKg,
                    rir = rir,
                    restSeconds = restSeconds.coerceIn(30, 600)
                )
            }
        )
    }

    companion object
}

data class TrainingDay(
    val dayName: String,
    val focus: String = "",
    val notes: String = "",
    val exercises: List<PlannedExercise> = emptyList()
) {
    companion object
}

data class WeeklyTrainingPlan(
    val name: String = "Current Hypertrophy Plan",
    val phaseGoal: String = "Build muscle with recoverable progression",
    val days: List<TrainingDay> = defaultTrainingDays()
) {
    fun toJson(): JSONObject {
        return JSONObject()
            .put("name", name)
            .put("phaseGoal", phaseGoal)
            .put("days", JSONArray().also { array -> days.forEach { array.put(it.toJson()) } })
    }

    companion object {
        fun defaultDays(): List<TrainingDay> {
            return defaultTrainingDays()
        }

        fun fromJson(json: JSONObject): WeeklyTrainingPlan {
            val days = json.optJSONArray("days") ?: JSONArray()
            val parsedDays = (0 until days.length()).mapNotNull { index ->
                days.optJSONObject(index)?.let { TrainingDay.fromJson(it) }
            }
            return WeeklyTrainingPlan(
                name = json.safeString("name", "Current Hypertrophy Plan"),
                phaseGoal = json.safeString("phaseGoal", "Build muscle with recoverable progression"),
                days = parsedDays.ifEmpty { defaultDays() }
            )
        }
    }
}

private fun defaultTrainingDays(): List<TrainingDay> {
    return listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun").map { dayName ->
        TrainingDay(dayName = dayName)
    }
}

fun TrainingDay.toJson(): JSONObject {
    return JSONObject()
        .put("dayName", dayName)
        .put("focus", focus)
        .put("notes", notes)
        .put("exercises", JSONArray().also { array -> exercises.forEach { array.put(it.toJson()) } })
}

fun PlannedExercise.toJson(): JSONObject {
    return JSONObject()
        .put("name", name)
        .put("targetMuscle", targetMuscle)
        .put("sets", sets)
        .put("reps", reps)
        .put("loadKg", loadKg)
        .put("rir", rir)
        .put("restSeconds", restSeconds)
        .put("notes", notes)
}

fun TrainingDay.Companion.fromJson(json: JSONObject): TrainingDay {
    val exercises = json.optJSONArray("exercises") ?: JSONArray()
    return TrainingDay(
        dayName = json.safeString("dayName", "Day"),
        focus = json.optString("focus", ""),
        notes = json.optString("notes", ""),
        exercises = (0 until exercises.length()).mapNotNull { index ->
            exercises.optJSONObject(index)?.let { PlannedExercise.fromJson(it) }
        }
    )
}

fun PlannedExercise.Companion.fromJson(json: JSONObject): PlannedExercise {
    return PlannedExercise(
        name = json.safeString("name", "Exercise"),
        targetMuscle = json.optString("targetMuscle", ""),
        sets = json.safeInt("sets", 3),
        reps = json.safeString("reps", "8-12"),
        loadKg = json.nullableDouble("loadKg"),
        rir = json.nullableDouble("rir"),
        restSeconds = json.safeInt("restSeconds", 120),
        notes = json.optString("notes", "")
    )
}

private fun JSONObject.nullableDouble(name: String): Double? {
    return if (has(name) && !isNull(name)) optDouble(name) else null
}

private fun JSONObject.safeInt(name: String, fallback: Int): Int {
    return if (has(name) && !isNull(name)) optInt(name, fallback) else fallback
}

private fun JSONObject.safeString(name: String, fallback: String): String {
    val value = if (has(name) && !isNull(name)) optString(name) else ""
    return value.takeIf { it.isNotBlank() } ?: fallback
}
