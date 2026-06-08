package com.iwanttobeanifbbpro.app.data

import org.json.JSONArray
import org.json.JSONObject
import java.time.LocalDate

data class DailyTargets(
    val calories: Int = 2600,
    val protein: Int = 180,
    val carbs: Int = 300,
    val fat: Int = 70,
    val fiber: Int = 30
) {
    companion object
}

data class SetEntry(
    val setNumber: Int,
    val targetReps: String,
    val actualReps: Int?,
    val loadKg: Double?,
    val rir: Double?,
    val completed: Boolean = false,
    val restSeconds: Int = 120,
    val notes: String = ""
) {
    companion object
}

data class ExerciseEntry(
    val name: String,
    val targetMuscle: String,
    val sets: Int,
    val reps: String,
    val loadKg: Double?,
    val rir: Double?,
    val notes: String,
    val setEntries: List<SetEntry> = emptyList(),
    val restSeconds: Int = 120
) {
    fun trackedSets(): List<SetEntry> {
        if (setEntries.isNotEmpty()) return setEntries
        return (1..sets.coerceAtLeast(0)).map { setNumber ->
            SetEntry(
                setNumber = setNumber,
                targetReps = reps,
                actualReps = null,
                loadKg = loadKg,
                rir = rir,
                completed = false,
                restSeconds = restSeconds
            )
        }
    }

    fun completedSetCount(): Int = trackedSets().count { it.completed }

    fun volumeKg(): Double {
        return trackedSets()
            .filter { it.completed }
            .sumOf { set -> (set.loadKg ?: 0.0) * (set.actualReps ?: 0) }
    }

    companion object
}

data class TrainingSession(
    val plannedFocus: String = "Hypertrophy session",
    val completed: Boolean = false,
    val exercises: List<ExerciseEntry> = emptyList(),
    val sessionNotes: String = ""
) {
    companion object
}

data class MealEntry(
    val name: String,
    val calories: Int,
    val protein: Double,
    val carbs: Double,
    val fat: Double,
    val fiber: Double,
    val notes: String
) {
    companion object
}

enum class PhotoEvidenceType(val label: String) {
    MEAL("Food or label photo"),
    TRAINING_FORM("Exercise form frame"),
    EQUIPMENT("Equipment photo"),
    PHYSIQUE("Physique progress photo"),
    MENU_LABEL("Menu or nutrition label"),
    OTHER("Other check-in photo");

    companion object {
        fun fromRaw(value: String): PhotoEvidenceType {
            return entries.firstOrNull { it.name == value } ?: OTHER
        }
    }
}

data class PhotoEvidenceEntry(
    val type: PhotoEvidenceType,
    val name: String,
    val mimeType: String,
    val note: String = "",
    val createdAt: String = ""
) {
    companion object
}

data class DailyConditioning(
    val stepGoal: Int = 9000,
    val cardioMinutes: Int = 0,
    val cardioType: String = "",
    val cardioIntensity: String = "Zone 2",
    val waterLiters: Double? = null,
    val sodiumMg: Int? = null,
    val caffeineMg: Int? = null,
    val alcoholServings: Double? = null,
    val digestion: String = "",
    val notes: String = ""
) {
    companion object
}

data class MealTemplate(
    val id: String,
    val title: String,
    val subtitle: String,
    val calories: Int,
    val protein: Double,
    val carbs: Double,
    val fat: Double,
    val fiber: Double,
    val notes: String
) {
    fun toMealEntry(): MealEntry {
        return MealEntry(
            name = title,
            calories = calories,
            protein = protein,
            carbs = carbs,
            fat = fat,
            fiber = fiber,
            notes = notes
        )
    }
}

fun mealTemplates(): List<MealTemplate> {
    return listOf(
        MealTemplate(
            id = "lean-protein-bowl",
            title = "Lean Protein Bowl",
            subtitle = "Chicken, rice, vegetables; reliable bodybuilding staple",
            calories = 620,
            protein = 52.0,
            carbs = 72.0,
            fat = 12.0,
            fiber = 8.0,
            notes = "Template estimate: adjust rice, oil, sauce, and meat weight when known."
        ),
        MealTemplate(
            id = "pre-workout-carbs",
            title = "Pre-Workout Carbs",
            subtitle = "Oats, whey, banana; useful before hard training",
            calories = 520,
            protein = 38.0,
            carbs = 76.0,
            fat = 8.0,
            fiber = 9.0,
            notes = "Template estimate: place 60-120 minutes before training if digestion is comfortable."
        ),
        MealTemplate(
            id = "low-fat-protein",
            title = "Low-Fat Protein Fix",
            subtitle = "Greek yogurt or lean protein when protein is behind",
            calories = 320,
            protein = 45.0,
            carbs = 22.0,
            fat = 4.0,
            fiber = 2.0,
            notes = "Template estimate: use when protein is behind and fats need to stay low."
        ),
        MealTemplate(
            id = "salmon-potato",
            title = "Salmon Potato Plate",
            subtitle = "Higher-fat whole-food meal for satiety",
            calories = 680,
            protein = 46.0,
            carbs = 58.0,
            fat = 28.0,
            fiber = 7.0,
            notes = "Template estimate: fats vary with salmon size and added oil."
        ),
        MealTemplate(
            id = "fiber-micronutrient",
            title = "Fiber + Micronutrient Add",
            subtitle = "Fruit, vegetables, beans, or oats to close fiber gap",
            calories = 260,
            protein = 12.0,
            carbs = 46.0,
            fat = 3.0,
            fiber = 12.0,
            notes = "Template estimate: add when fiber or food quality is behind."
        )
    )
}

data class DailyMetrics(
    val bodyWeightKg: Double? = null,
    val bodyFatPercent: Double? = null,
    val leanBodyMassKg: Double? = null,
    val waistCm: Double? = null,
    val chestCm: Double? = null,
    val shoulderCm: Double? = null,
    val hipCm: Double? = null,
    val leftArmCm: Double? = null,
    val rightArmCm: Double? = null,
    val leftThighCm: Double? = null,
    val rightThighCm: Double? = null,
    val neckCm: Double? = null,
    val sleepHours: Double? = null,
    val steps: Int = 0,
    val restingHeartRateBpm: Double? = null,
    val totalCaloriesBurnedKcal: Double? = null,
    val healthDataSource: String = "",
    val healthSyncedAt: String = "",
    val hunger: Int = 3,
    val fatigue: Int = 3,
    val soreness: Int = 3,
    val stress: Int = 3
) {
    companion object
}

data class DailyLog(
    val date: String = LocalDate.now().toString(),
    val targets: DailyTargets = DailyTargets(),
    val trainingSession: TrainingSession = TrainingSession(),
    val meals: List<MealEntry> = emptyList(),
    val photoEvidence: List<PhotoEvidenceEntry> = emptyList(),
    val conditioning: DailyConditioning = DailyConditioning(),
    val metrics: DailyMetrics = DailyMetrics(),
    val reflection: String = ""
) {
    fun nutritionTotals(): DailyTargets {
        return DailyTargets(
            calories = meals.sumOf { it.calories },
            protein = meals.sumOf { it.protein }.toInt(),
            carbs = meals.sumOf { it.carbs }.toInt(),
            fat = meals.sumOf { it.fat }.toInt(),
            fiber = meals.sumOf { it.fiber }.toInt()
        )
    }

    fun completedHardSets(): Int = trainingSession.exercises.sumOf { it.completedSetCount() }

    fun plannedHardSets(): Int = trainingSession.exercises.sumOf { it.trackedSets().size }

    fun trainingVolumeKg(): Double = trainingSession.exercises.sumOf { it.volumeKg() }

    fun toJson(): JSONObject {
        return JSONObject()
            .put("date", date)
            .put("targets", targets.toJson())
            .put("trainingSession", trainingSession.toJson())
            .put("meals", JSONArray().also { array -> meals.forEach { array.put(it.toJson()) } })
            .put("photoEvidence", JSONArray().also { array -> photoEvidence.forEach { array.put(it.toJson()) } })
            .put("conditioning", conditioning.toJson())
            .put("metrics", metrics.toJson())
            .put("reflection", reflection)
    }

    companion object {
        fun fromJson(json: JSONObject): DailyLog {
            val meals = json.optJSONArray("meals") ?: JSONArray()
            val photoEvidence = json.optJSONArray("photoEvidence") ?: JSONArray()
            return DailyLog(
                date = json.optString("date", LocalDate.now().toString()),
                targets = DailyTargets.fromJson(json.optJSONObject("targets") ?: JSONObject()),
                trainingSession = TrainingSession.fromJson(json.optJSONObject("trainingSession") ?: JSONObject()),
                meals = (0 until meals.length()).mapNotNull { index ->
                    meals.optJSONObject(index)?.let { MealEntry.fromJson(it) }
                },
                photoEvidence = (0 until photoEvidence.length()).mapNotNull { index ->
                    photoEvidence.optJSONObject(index)?.let { PhotoEvidenceEntry.fromJson(it) }
                },
                conditioning = DailyConditioning.fromJson(json.optJSONObject("conditioning") ?: JSONObject()),
                metrics = DailyMetrics.fromJson(json.optJSONObject("metrics") ?: JSONObject()),
                reflection = json.optString("reflection", "")
            )
        }
    }
}

private fun DailyTargets.toJson(): JSONObject {
    return JSONObject()
        .put("calories", calories)
        .put("protein", protein)
        .put("carbs", carbs)
        .put("fat", fat)
        .put("fiber", fiber)
}

private fun TrainingSession.toJson(): JSONObject {
    return JSONObject()
        .put("plannedFocus", plannedFocus)
        .put("completed", completed)
        .put("exercises", JSONArray().also { array -> exercises.forEach { array.put(it.toJson()) } })
        .put("sessionNotes", sessionNotes)
}

private fun ExerciseEntry.toJson(): JSONObject {
    return JSONObject()
        .put("name", name)
        .put("targetMuscle", targetMuscle)
        .put("sets", sets)
        .put("reps", reps)
        .put("loadKg", loadKg)
        .put("rir", rir)
        .put("notes", notes)
        .put("restSeconds", restSeconds)
        .put("setEntries", JSONArray().also { array -> trackedSets().forEach { array.put(it.toJson()) } })
}

private fun SetEntry.toJson(): JSONObject {
    return JSONObject()
        .put("setNumber", setNumber)
        .put("targetReps", targetReps)
        .put("actualReps", actualReps)
        .put("loadKg", loadKg)
        .put("rir", rir)
        .put("completed", completed)
        .put("restSeconds", restSeconds)
        .put("notes", notes)
}

private fun MealEntry.toJson(): JSONObject {
    return JSONObject()
        .put("name", name)
        .put("calories", calories)
        .put("protein", protein)
        .put("carbs", carbs)
        .put("fat", fat)
        .put("fiber", fiber)
        .put("notes", notes)
}

private fun PhotoEvidenceEntry.toJson(): JSONObject {
    return JSONObject()
        .put("type", type.name)
        .put("name", name)
        .put("mimeType", mimeType)
        .put("note", note)
        .put("createdAt", createdAt)
}

private fun DailyConditioning.toJson(): JSONObject {
    return JSONObject()
        .put("stepGoal", stepGoal)
        .put("cardioMinutes", cardioMinutes)
        .put("cardioType", cardioType)
        .put("cardioIntensity", cardioIntensity)
        .put("waterLiters", waterLiters)
        .put("sodiumMg", sodiumMg)
        .put("caffeineMg", caffeineMg)
        .put("alcoholServings", alcoholServings)
        .put("digestion", digestion)
        .put("notes", notes)
}

private fun DailyMetrics.toJson(): JSONObject {
    return JSONObject()
        .put("bodyWeightKg", bodyWeightKg)
        .put("bodyFatPercent", bodyFatPercent)
        .put("leanBodyMassKg", leanBodyMassKg)
        .put("waistCm", waistCm)
        .put("chestCm", chestCm)
        .put("shoulderCm", shoulderCm)
        .put("hipCm", hipCm)
        .put("leftArmCm", leftArmCm)
        .put("rightArmCm", rightArmCm)
        .put("leftThighCm", leftThighCm)
        .put("rightThighCm", rightThighCm)
        .put("neckCm", neckCm)
        .put("sleepHours", sleepHours)
        .put("steps", steps)
        .put("restingHeartRateBpm", restingHeartRateBpm)
        .put("totalCaloriesBurnedKcal", totalCaloriesBurnedKcal)
        .put("healthDataSource", healthDataSource)
        .put("healthSyncedAt", healthSyncedAt)
        .put("hunger", hunger)
        .put("fatigue", fatigue)
        .put("soreness", soreness)
        .put("stress", stress)
}

private fun JSONObject.nullableDouble(name: String): Double? {
    return if (has(name) && !isNull(name)) optDouble(name) else null
}

private fun JSONObject.nullableString(name: String): String? {
    return if (has(name) && !isNull(name)) optString(name) else null
}

private fun JSONObject.safeInt(name: String, fallback: Int): Int {
    return if (has(name) && !isNull(name)) optInt(name, fallback) else fallback
}

private fun JSONObject.safeDouble(name: String, fallback: Double): Double {
    return if (has(name) && !isNull(name)) optDouble(name, fallback) else fallback
}

private fun JSONObject.safeString(name: String, fallback: String): String {
    return nullableString(name)?.takeIf { it.isNotBlank() } ?: fallback
}

fun DailyTargets.Companion.fromJson(json: JSONObject): DailyTargets {
    return DailyTargets(
        calories = json.safeInt("calories", 2600),
        protein = json.safeInt("protein", 180),
        carbs = json.safeInt("carbs", 300),
        fat = json.safeInt("fat", 70),
        fiber = json.safeInt("fiber", 30)
    )
}

fun TrainingSession.Companion.fromJson(json: JSONObject): TrainingSession {
    val exercises = json.optJSONArray("exercises") ?: JSONArray()
    return TrainingSession(
        plannedFocus = json.safeString("plannedFocus", "Hypertrophy session"),
        completed = json.optBoolean("completed", false),
        exercises = (0 until exercises.length()).mapNotNull { index ->
            exercises.optJSONObject(index)?.let { ExerciseEntry.fromJson(it) }
        },
        sessionNotes = json.optString("sessionNotes", "")
    )
}

fun ExerciseEntry.Companion.fromJson(json: JSONObject): ExerciseEntry {
    val setEntries = json.optJSONArray("setEntries") ?: JSONArray()
    return ExerciseEntry(
        name = json.safeString("name", "Exercise"),
        targetMuscle = json.optString("targetMuscle", ""),
        sets = json.safeInt("sets", 0),
        reps = json.optString("reps", ""),
        loadKg = json.nullableDouble("loadKg"),
        rir = json.nullableDouble("rir"),
        notes = json.optString("notes", ""),
        setEntries = (0 until setEntries.length()).mapNotNull { index ->
            setEntries.optJSONObject(index)?.let { SetEntry.fromJson(it) }
        },
        restSeconds = json.safeInt("restSeconds", 120)
    )
}

fun SetEntry.Companion.fromJson(json: JSONObject): SetEntry {
    return SetEntry(
        setNumber = json.safeInt("setNumber", 1),
        targetReps = json.safeString("targetReps", ""),
        actualReps = if (json.has("actualReps") && !json.isNull("actualReps")) json.optInt("actualReps") else null,
        loadKg = json.nullableDouble("loadKg"),
        rir = json.nullableDouble("rir"),
        completed = json.optBoolean("completed", false),
        restSeconds = json.safeInt("restSeconds", 120),
        notes = json.optString("notes", "")
    )
}

fun MealEntry.Companion.fromJson(json: JSONObject): MealEntry {
    return MealEntry(
        name = json.safeString("name", "Meal"),
        calories = json.safeInt("calories", 0),
        protein = json.safeDouble("protein", 0.0),
        carbs = json.safeDouble("carbs", 0.0),
        fat = json.safeDouble("fat", 0.0),
        fiber = json.safeDouble("fiber", 0.0),
        notes = json.optString("notes", "")
    )
}

fun PhotoEvidenceEntry.Companion.fromJson(json: JSONObject): PhotoEvidenceEntry {
    return PhotoEvidenceEntry(
        type = PhotoEvidenceType.fromRaw(json.safeString("type", PhotoEvidenceType.OTHER.name)),
        name = json.safeString("name", "photo"),
        mimeType = json.safeString("mimeType", "image/jpeg"),
        note = json.optString("note", ""),
        createdAt = json.optString("createdAt", "")
    )
}

fun DailyConditioning.Companion.fromJson(json: JSONObject): DailyConditioning {
    return DailyConditioning(
        stepGoal = json.safeInt("stepGoal", 9000).coerceAtLeast(0),
        cardioMinutes = json.safeInt("cardioMinutes", 0).coerceAtLeast(0),
        cardioType = json.optString("cardioType", ""),
        cardioIntensity = json.safeString("cardioIntensity", "Zone 2"),
        waterLiters = json.nullableDouble("waterLiters"),
        sodiumMg = if (json.has("sodiumMg") && !json.isNull("sodiumMg")) json.optInt("sodiumMg") else null,
        caffeineMg = if (json.has("caffeineMg") && !json.isNull("caffeineMg")) json.optInt("caffeineMg") else null,
        alcoholServings = json.nullableDouble("alcoholServings"),
        digestion = json.optString("digestion", ""),
        notes = json.optString("notes", "")
    )
}

fun DailyMetrics.Companion.fromJson(json: JSONObject): DailyMetrics {
    return DailyMetrics(
        bodyWeightKg = json.nullableDouble("bodyWeightKg"),
        bodyFatPercent = json.nullableDouble("bodyFatPercent"),
        leanBodyMassKg = json.nullableDouble("leanBodyMassKg"),
        waistCm = json.nullableDouble("waistCm"),
        chestCm = json.nullableDouble("chestCm"),
        shoulderCm = json.nullableDouble("shoulderCm"),
        hipCm = json.nullableDouble("hipCm"),
        leftArmCm = json.nullableDouble("leftArmCm"),
        rightArmCm = json.nullableDouble("rightArmCm"),
        leftThighCm = json.nullableDouble("leftThighCm"),
        rightThighCm = json.nullableDouble("rightThighCm"),
        neckCm = json.nullableDouble("neckCm"),
        sleepHours = json.nullableDouble("sleepHours"),
        steps = json.safeInt("steps", 0),
        restingHeartRateBpm = json.nullableDouble("restingHeartRateBpm"),
        totalCaloriesBurnedKcal = json.nullableDouble("totalCaloriesBurnedKcal"),
        healthDataSource = json.optString("healthDataSource", ""),
        healthSyncedAt = json.optString("healthSyncedAt", ""),
        hunger = json.safeInt("hunger", 3),
        fatigue = json.safeInt("fatigue", 3),
        soreness = json.safeInt("soreness", 3),
        stress = json.safeInt("stress", 3)
    )
}
