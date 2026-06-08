package com.iwanttobeanifbbpro.app.core

import java.util.Locale

enum class ExerciseVisualType {
    SMITH_MACHINE,
    CABLE,
    DUMBBELL,
    BARBELL,
    MACHINE,
    BENCH,
    PULL_UP_STATION,
    BAND,
    LEG_PRESS,
    BODYWEIGHT
}

data class ExerciseVisualSpec(
    val type: ExerciseVisualType,
    val equipment: String,
    val equipmentZh: String,
    val pattern: String,
    val primaryMuscle: String,
    val setupCue: String,
    val example: String,
    val lookFor: String,
    val instanceCue: String,
    val commonMovements: List<String>
) {
    fun visualPromptLine(): String {
        return "Exercise visual guide: $equipment ($equipmentZh) | $pattern | instance diagram cue: $instanceCue | target $primaryMuscle | setup cue: $setupCue | example movement: $example | look-for cue: $lookFor | common movements: ${commonMovements.joinToString(", ")}"
    }
}

fun exerciseVisualLibrarySpecs(): List<ExerciseVisualSpec> {
    return listOf(
        exerciseVisualSpec("Smith incline press", "Upper chest"),
        exerciseVisualSpec("Cable lateral raise", "Side delt"),
        exerciseVisualSpec("Dumbbell row", "Back"),
        exerciseVisualSpec("Barbell squat", "Quads"),
        exerciseVisualSpec("Machine chest press", "Chest"),
        exerciseVisualSpec("Incline dumbbell press bench setup", "Upper chest"),
        exerciseVisualSpec("Pull-up", "Lats"),
        exerciseVisualSpec("Band face pull", "Rear delts"),
        exerciseVisualSpec("Leg press", "Quads"),
        exerciseVisualSpec("Push-up", "Chest")
    )
}

fun exerciseVisualSpec(name: String, targetMuscle: String): ExerciseVisualSpec {
    val text = "$name $targetMuscle".lowercase(Locale.US)
    val primaryMuscle = targetMuscle.ifBlank { inferPrimaryMuscle(text) }
    return when {
        text.contains("smith") -> ExerciseVisualSpec(
            type = ExerciseVisualType.SMITH_MACHINE,
            equipment = "Smith machine",
            equipmentZh = "史密斯机",
            pattern = "Guided fixed-bar path",
            primaryMuscle = primaryMuscle,
            setupCue = "Align bench or stance under the fixed bar path",
            example = "Incline Smith Press",
            lookFor = "Look for two rails and a fixed bar",
            instanceCue = "Two vertical rails with a bar that moves on a fixed track",
            commonMovements = listOf("Incline Smith Press", "Smith Squat", "Smith Row")
        )

        text.contains("cable") ||
            text.contains("pulley") ||
            text.contains("rope") ||
            text.contains("pushdown") ||
            text.contains("face pull") ||
            text.contains("pulldown") -> ExerciseVisualSpec(
            type = ExerciseVisualType.CABLE,
            equipment = "Cable station",
            equipmentZh = "绳索龙门架/滑轮器械",
            pattern = "Pulley resistance",
            primaryMuscle = primaryMuscle,
            setupCue = "Set pulley height before choosing handle",
            example = "Cable Lateral Raise",
            lookFor = "Look for a cable, pulley, and handle",
            instanceCue = "A cable runs through a pulley to a handle, rope, bar, or ankle strap",
            commonMovements = listOf("Cable Lateral Raise", "Lat Pulldown", "Rope Pushdown")
        )

        text.contains("leg press") ||
            text.contains("hack squat") ||
            text.contains("pendulum") -> ExerciseVisualSpec(
            type = ExerciseVisualType.LEG_PRESS,
            equipment = "Leg press or hack squat",
            equipmentZh = "腿举/哈克深蹲器械",
            pattern = "Guided lower-body sled path",
            primaryMuscle = primaryMuscle,
            setupCue = "Set seat, foot position, safety stops, and controlled depth",
            example = "Leg Press",
            lookFor = "Look for a sled platform, seat, and safety handles",
            instanceCue = "A seat or shoulder pad setup pushes against a sled or fixed foot platform",
            commonMovements = listOf("Leg Press", "Hack Squat", "Pendulum Squat")
        )

        text.contains("pull-up") ||
            text.contains("pullup") ||
            text.contains("chin-up") ||
            text.contains("chinup") ||
            text.contains("dip") -> ExerciseVisualSpec(
            type = ExerciseVisualType.PULL_UP_STATION,
            equipment = "Pull-up/Dip station",
            equipmentZh = "引体向上/双杠训练站",
            pattern = "Bodyweight vertical support",
            primaryMuscle = primaryMuscle,
            setupCue = "Choose grip, brace, and use assistance if reps break down",
            example = "Pull-up",
            lookFor = "Look for an overhead bar, dip handles, or assist platform",
            instanceCue = "A tall frame with overhead grips, dip handles, or an assisted platform",
            commonMovements = listOf("Pull-up", "Chin-up", "Dip")
        )

        text.contains("band") -> ExerciseVisualSpec(
            type = ExerciseVisualType.BAND,
            equipment = "Resistance band",
            equipmentZh = "弹力带",
            pattern = "Elastic resistance",
            primaryMuscle = primaryMuscle,
            setupCue = "Anchor the band securely and control the end range",
            example = "Band Face Pull",
            lookFor = "Look for a band anchored to a rack, door, or post",
            instanceCue = "A loop or tube band stretches from an anchor point or under the feet",
            commonMovements = listOf("Band Face Pull", "Band Pull-apart", "Banded Push-up")
        )

        text.contains("dumbbell") ||
            text.contains("db ") ||
            text.endsWith(" db") -> ExerciseVisualSpec(
            type = ExerciseVisualType.DUMBBELL,
            equipment = "Dumbbells",
            equipmentZh = "哑铃",
            pattern = "Free-weight unilateral control",
            primaryMuscle = primaryMuscle,
            setupCue = "Match both sides and control the path",
            example = "Dumbbell Row",
            lookFor = "Look for one or two handheld weights",
            instanceCue = "One or two short handheld weights move independently",
            commonMovements = listOf("Dumbbell Row", "Dumbbell Press", "Dumbbell Lateral Raise")
        )

        text.contains("barbell") ||
            text.contains("bench press") ||
            text.contains("deadlift") ||
            text.contains("squat") -> ExerciseVisualSpec(
            type = ExerciseVisualType.BARBELL,
            equipment = "Barbell",
            equipmentZh = "杠铃",
            pattern = "Free-weight compound lift",
            primaryMuscle = primaryMuscle,
            setupCue = "Set rack height, grip, stance, and safety pins",
            example = "Barbell Squat",
            lookFor = "Look for a straight bar, plates, and rack",
            instanceCue = "A long straight bar with plates, usually near a rack or platform",
            commonMovements = listOf("Barbell Squat", "Bench Press", "Deadlift")
        )

        text.contains("bench") ||
            text.contains("hip thrust") ||
            text.contains("step-up") ||
            text.contains("step up") ||
            text.contains("bulgarian") -> ExerciseVisualSpec(
            type = ExerciseVisualType.BENCH,
            equipment = "Adjustable bench",
            equipmentZh = "可调训练凳",
            pattern = "Bench-supported movement",
            primaryMuscle = primaryMuscle,
            setupCue = "Set bench angle and body contact before loading",
            example = "Incline Dumbbell Press",
            lookFor = "Look for a flat or adjustable bench",
            instanceCue = "A flat or angled bench supports the torso, back foot, hips, or chest",
            commonMovements = listOf("Incline Dumbbell Press", "Chest-supported Row", "Bulgarian Split Squat")
        )

        text.contains("machine") ||
            text.contains("press") ||
            text.contains("extension") ||
            text.contains("curl") ||
            text.contains("pec deck") ||
            text.contains("leg") -> ExerciseVisualSpec(
            type = ExerciseVisualType.MACHINE,
            equipment = "Machine",
            equipmentZh = "固定轨迹器械",
            pattern = "Guided resistance",
            primaryMuscle = primaryMuscle,
            setupCue = "Adjust seat and pads to match joint axis",
            example = "Machine Chest Press",
            lookFor = "Look for a seat, pads, handles, or lever arms",
            instanceCue = "A seated or padded machine guides the movement through handles or lever arms",
            commonMovements = listOf("Machine Chest Press", "Leg Extension", "Seated Row")
        )

        else -> ExerciseVisualSpec(
            type = ExerciseVisualType.BODYWEIGHT,
            equipment = "Bodyweight or open station",
            equipmentZh = "自重/开放训练空间",
            pattern = "Movement pattern demo",
            primaryMuscle = primaryMuscle,
            setupCue = "Use notes or photos when the exact setup is unclear",
            example = "Push-up",
            lookFor = "Look for floor, bench, bar, bands, or open space",
            instanceCue = "The body moves on the floor, open space, a bench, bar, or simple support",
            commonMovements = listOf("Push-up", "Plank", "Inverted Row")
        )
    }
}

private fun inferPrimaryMuscle(text: String): String {
    return when {
        text.contains("lateral") || text.contains("delt") || text.contains("shoulder") -> "Delts"
        text.contains("incline") || text.contains("chest") || text.contains("press") || text.contains("pec") -> "Chest"
        text.contains("row") || text.contains("pulldown") || text.contains("lat") || text.contains("back") -> "Back"
        text.contains("squat") || text.contains("leg press") || text.contains("quad") -> "Quads"
        text.contains("deadlift") || text.contains("rdl") || text.contains("hamstring") || text.contains("glute") -> "Posterior chain"
        text.contains("curl") || text.contains("biceps") -> "Biceps"
        text.contains("extension") || text.contains("pushdown") || text.contains("triceps") -> "Triceps"
        text.contains("calf") -> "Calves"
        else -> "Target muscle"
    }
}
