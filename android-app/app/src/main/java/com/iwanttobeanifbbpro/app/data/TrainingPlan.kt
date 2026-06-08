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

data class TrainingPlanTemplate(
    val id: String,
    val title: String,
    val subtitle: String,
    val bestFor: String,
    val weeklyDays: Int,
    val plan: WeeklyTrainingPlan
)

fun trainingPlanTemplates(): List<TrainingPlanTemplate> {
    return listOf(
        TrainingPlanTemplate(
            id = "beginner-full-body",
            title = "Beginner Full Body",
            subtitle = "3 days/wk, stable machines and free weights",
            bestFor = "New lifters, return-to-training, or anyone who wants a simple start",
            weeklyDays = 3,
            plan = WeeklyTrainingPlan(
                name = "Beginner Full Body Foundation",
                phaseGoal = "Build technique, consistency, and recoverable full-body hypertrophy",
                days = fillWeek(
                    TrainingDay(
                        dayName = "Mon",
                        focus = "Full Body A",
                        notes = "Start conservative. Leave 2-3 reps in reserve and learn the movement path.",
                        exercises = listOf(
                            planned("Leg Press", "Quads", 3, "10-12", 2.0, 120, "Controlled depth; keep knees tracking with toes."),
                            planned("Machine Chest Press", "Chest", 3, "8-12", 2.0, 120, "Set seat so handles start near mid-chest."),
                            planned("Lat Pulldown", "Back", 3, "10-12", 2.0, 120, "Drive elbows down; avoid swinging."),
                            planned("Dumbbell Lateral Raise", "Side delts", 2, "12-15", 2.0, 75, "Lead with elbows and stop before traps take over."),
                            planned("Cable Triceps Pushdown", "Triceps", 2, "10-15", 2.0, 75, "Keep elbows pinned and control the stretch.")
                        )
                    ),
                    TrainingDay(
                        dayName = "Wed",
                        focus = "Full Body B",
                        notes = "Use the same warm-up pattern and log every working set.",
                        exercises = listOf(
                            planned("Goblet Squat", "Quads", 3, "10-12", 2.0, 120, "Brace and keep pressure through midfoot."),
                            planned("Incline Dumbbell Press", "Upper chest", 3, "8-12", 2.0, 120, "Use a low incline; keep shoulder blades set."),
                            planned("Seated Cable Row", "Back", 3, "10-12", 2.0, 120, "Pause with elbows behind torso."),
                            planned("Dumbbell Curl", "Biceps", 2, "10-15", 2.0, 75, "Match both sides and avoid swinging."),
                            planned("Standing Calf Raise", "Calves", 2, "10-15", 2.0, 75, "Full stretch, controlled top squeeze.")
                        )
                    ),
                    TrainingDay(
                        dayName = "Fri",
                        focus = "Full Body C",
                        notes = "Repeat clean technique; add reps before load.",
                        exercises = listOf(
                            planned("Hack Squat", "Quads", 3, "8-12", 2.0, 150, "Use safety stops and controlled range."),
                            planned("Push-up", "Chest", 3, "8-15", 2.0, 90, "Use incline push-ups if full reps break down."),
                            planned("Machine Row", "Back", 3, "8-12", 2.0, 120, "Chest supported if available."),
                            planned("Cable Face Pull", "Rear delts", 2, "12-20", 2.0, 75, "Pull toward eye level; keep ribs down."),
                            planned("Cable Crunch", "Abs", 2, "10-15", 2.0, 75, "Curl ribs toward pelvis.")
                        )
                    )
                )
            )
        ),
        TrainingPlanTemplate(
            id = "four-day-hypertrophy",
            title = "4-Day Hypertrophy",
            subtitle = "Upper/lower split with clear progression",
            bestFor = "Most intermediate users building muscle with recoverable volume",
            weeklyDays = 4,
            plan = WeeklyTrainingPlan(
                name = "4-Day Hypertrophy Builder",
                phaseGoal = "Build muscle with balanced weekly volume and repeatable progression",
                days = fillWeek(
                    TrainingDay(
                        dayName = "Mon",
                        focus = "Upper A",
                        notes = "Progress presses and rows by reps before load.",
                        exercises = listOf(
                            planned("Incline Smith Press", "Upper chest", 4, "6-10", 2.0, 150, "Align bench under fixed bar path."),
                            planned("Chest-Supported Dumbbell Row", "Back", 4, "8-12", 2.0, 120, "Drive elbows; keep torso stable."),
                            planned("Machine Shoulder Press", "Delts", 3, "8-12", 2.0, 120, "Avoid excessive low-back arch."),
                            planned("Cable Lateral Raise", "Side delts", 3, "12-20", 1.0, 75, "Keep cable behind body slightly."),
                            planned("Rope Triceps Pushdown", "Triceps", 3, "10-15", 1.0, 75, "Control the top stretch.")
                        )
                    ),
                    TrainingDay(
                        dayName = "Tue",
                        focus = "Lower A",
                        notes = "Quads first, posterior chain second, calves last.",
                        exercises = listOf(
                            planned("Barbell Squat", "Quads", 3, "5-8", 2.0, 180, "Use safety pins and keep bracing consistent."),
                            planned("Romanian Deadlift", "Hamstrings", 3, "8-10", 2.0, 150, "Stop before spinal rounding."),
                            planned("Leg Press", "Quads", 3, "10-15", 2.0, 150, "Controlled depth and steady foot pressure."),
                            planned("Seated Leg Curl", "Hamstrings", 3, "10-15", 1.0, 90, "Pause in the shortened position."),
                            planned("Standing Calf Raise", "Calves", 4, "8-12", 1.0, 75, "Deep stretch and hard top squeeze.")
                        )
                    ),
                    TrainingDay(dayName = "Wed", focus = "Recovery", notes = "Steps, mobility, and meal consistency.", exercises = emptyList()),
                    TrainingDay(
                        dayName = "Thu",
                        focus = "Upper B",
                        notes = "Bias back width, side delts, and arm execution.",
                        exercises = listOf(
                            planned("Pull-up", "Lats", 3, "6-10", 2.0, 150, "Use assistance if reps fall below target."),
                            planned("Machine Chest Press", "Chest", 3, "8-12", 2.0, 120, "Match handle height to chest line."),
                            planned("Seated Cable Row", "Mid back", 3, "8-12", 2.0, 120, "Pause without leaning back."),
                            planned("Cable Lateral Raise", "Side delts", 4, "12-20", 1.0, 75, "Keep tension in the target delt."),
                            planned("Incline Dumbbell Curl", "Biceps", 3, "10-15", 1.0, 75, "Use full stretch and no swing.")
                        )
                    ),
                    TrainingDay(
                        dayName = "Fri",
                        focus = "Lower B",
                        notes = "Stable machines, controlled range, no sloppy failure.",
                        exercises = listOf(
                            planned("Hack Squat", "Quads", 4, "8-12", 2.0, 150, "Use a stance that keeps knee tracking clean."),
                            planned("Hip Thrust", "Glutes", 3, "8-12", 2.0, 120, "Lock ribs down and pause at top."),
                            planned("Leg Extension", "Quads", 3, "12-15", 1.0, 90, "Control the bottom and squeeze top."),
                            planned("Lying Leg Curl", "Hamstrings", 3, "10-15", 1.0, 90, "Keep hips down."),
                            planned("Cable Crunch", "Abs", 3, "10-15", 1.0, 75, "Use controlled spinal flexion.")
                        )
                    )
                )
            )
        ),
        TrainingPlanTemplate(
            id = "physique-priority",
            title = "5-Day Physique Priority",
            subtitle = "Upper chest, delts, back width, legs, arms",
            bestFor = "Bodybuilding-style weak-point focus with professional data tracking",
            weeklyDays = 5,
            plan = WeeklyTrainingPlan(
                name = "5-Day Physique Priority",
                phaseGoal = "Build an IFBB PRO-inspired physique emphasis while managing fatigue",
                days = fillWeek(
                    TrainingDay(
                        dayName = "Mon",
                        focus = "Chest + Side Delts",
                        notes = "Upper chest first; stop pressing if shoulder pain changes mechanics.",
                        exercises = listOf(
                            planned("Incline Smith Press", "Upper chest", 4, "6-10", 2.0, 150, "Fixed bar path; progress by reps."),
                            planned("Incline Dumbbell Press", "Upper chest", 3, "8-12", 2.0, 120, "Low incline and controlled stretch."),
                            planned("Cable Fly", "Chest", 3, "12-15", 1.0, 90, "Line cable path with fibers."),
                            planned("Cable Lateral Raise", "Side delts", 4, "12-20", 1.0, 75, "Use strict reps; keep traps quiet.")
                        )
                    ),
                    TrainingDay(
                        dayName = "Tue",
                        focus = "Back Width + Rear Delts",
                        notes = "Prioritize lats without turning pulls into biceps work.",
                        exercises = listOf(
                            planned("Pull-up", "Lats", 4, "6-10", 2.0, 150, "Assisted is fine if clean reps are limited."),
                            planned("Single-Arm Cable Pulldown", "Lats", 3, "10-12", 1.0, 90, "Drive elbow to hip."),
                            planned("Chest-Supported Row", "Mid back", 3, "8-12", 2.0, 120, "Pause without shrugging."),
                            planned("Cable Face Pull", "Rear delts", 3, "12-20", 1.0, 75, "Pull to eye line.")
                        )
                    ),
                    TrainingDay(
                        dayName = "Wed",
                        focus = "Legs",
                        notes = "High output day; Recovery Guidance should decide whether to push or hold.",
                        exercises = listOf(
                            planned("Hack Squat", "Quads", 4, "8-12", 2.0, 150, "Controlled depth and no bouncing."),
                            planned("Romanian Deadlift", "Hamstrings", 3, "8-10", 2.0, 150, "Hamstring stretch without back rounding."),
                            planned("Leg Press", "Quads", 3, "10-15", 2.0, 150, "Stable platform path."),
                            planned("Seated Leg Curl", "Hamstrings", 3, "10-15", 1.0, 90, "Pause at squeeze."),
                            planned("Standing Calf Raise", "Calves", 4, "8-12", 1.0, 75, "Full ROM.")
                        )
                    ),
                    TrainingDay(
                        dayName = "Fri",
                        focus = "Delts + Arms",
                        notes = "Pump and execution day; avoid joint irritation.",
                        exercises = listOf(
                            planned("Machine Shoulder Press", "Delts", 3, "8-12", 2.0, 120, "Stable seat and controlled reps."),
                            planned("Dumbbell Lateral Raise", "Side delts", 4, "12-20", 1.0, 75, "Add reps before load."),
                            planned("Cable Curl", "Biceps", 3, "10-15", 1.0, 75, "Elbows stable."),
                            planned("Overhead Cable Extension", "Triceps", 3, "10-15", 1.0, 75, "Long-head stretch."),
                            planned("Rope Triceps Pushdown", "Triceps", 2, "12-20", 1.0, 60, "Clean lockout.")
                        )
                    ),
                    TrainingDay(
                        dayName = "Sat",
                        focus = "Upper Pump + Weak Points",
                        notes = "Low joint cost. Use photos and notes to decide next week's weak-point bias.",
                        exercises = listOf(
                            planned("Machine Chest Press", "Chest", 3, "10-12", 2.0, 120, "Stable pressing volume."),
                            planned("Seated Cable Row", "Back", 3, "10-12", 2.0, 120, "Mid-back control."),
                            planned("Cable Lateral Raise", "Side delts", 3, "15-20", 1.0, 60, "Strict pump work."),
                            planned("Band Face Pull", "Rear delts", 2, "15-25", 2.0, 60, "Low fatigue shoulder balance."),
                            planned("Cable Crunch", "Abs", 3, "10-15", 1.0, 75, "Waist control and bracing.")
                        )
                    )
                )
            )
        )
    )
}

private fun defaultTrainingDays(): List<TrainingDay> {
    return listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun").map { dayName ->
        TrainingDay(dayName = dayName)
    }
}

private fun fillWeek(vararg trainingDays: TrainingDay): List<TrainingDay> {
    val keyed = trainingDays.associateBy { it.dayName }
    return listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun").map { dayName ->
        keyed[dayName] ?: TrainingDay(dayName = dayName, focus = "Recovery", notes = "Rest, steps, mobility, and meal consistency.")
    }
}

private fun planned(
    name: String,
    targetMuscle: String,
    sets: Int,
    reps: String,
    rir: Double,
    restSeconds: Int,
    notes: String
): PlannedExercise {
    return PlannedExercise(
        name = name,
        targetMuscle = targetMuscle,
        sets = sets,
        reps = reps,
        loadKg = null,
        rir = rir,
        restSeconds = restSeconds,
        notes = notes
    )
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
