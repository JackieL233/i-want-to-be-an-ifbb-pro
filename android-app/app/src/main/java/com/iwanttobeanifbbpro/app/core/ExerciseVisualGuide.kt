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
    val visualId: String,
    val type: ExerciseVisualType,
    val equipment: String,
    val equipmentZh: String,
    val figureTitle: String,
    val pattern: String,
    val primaryMuscle: String,
    val setupCue: String,
    val example: String,
    val lookFor: String,
    val quickVisualCue: String,
    val findEquipmentCue: String,
    val movementPathCue: String,
    val instanceCue: String,
    val actionPathCue: String,
    val beginnerCue: String,
    val equipmentMarkers: List<String>,
    val commonMovements: List<String>
) {
    fun recognitionSteps(): List<String> {
        return listOf(
            "1. See: $figureTitle.",
            "2. Find: $findEquipmentCue.",
            "3. Move: $movementPathCue."
        )
    }

    fun atlasLine(): String {
        return "$visualId $equipment ($equipmentZh): $figureTitle | $quickVisualCue | $findEquipmentCue | $movementPathCue | beginner cue: $beginnerCue | common movements: ${commonMovements.joinToString(", ")}"
    }

    fun visualPromptLine(): String {
        return "Exercise visual guide: $visualId $equipment ($equipmentZh) | unified instance diagram: $figureTitle | $pattern | quick visual cue: $quickVisualCue | find-equipment cue: $findEquipmentCue | movement path cue: $movementPathCue | three-step recognition: ${recognitionSteps().joinToString(" ")} | action path cue: $actionPathCue | instance diagram cue: $instanceCue | beginner recognition cue: $beginnerCue | equipment markers: ${equipmentMarkers.joinToString(", ")} | target $primaryMuscle | setup cue: $setupCue | example movement: $example | look-for cue: $lookFor | common movements: ${commonMovements.joinToString(", ")}"
    }
}

data class ExerciseVisualAtlas(
    val title: String,
    val summary: String,
    val recognitionFlow: List<String>,
    val specs: List<ExerciseVisualSpec>
) {
    fun promptLine(): String {
        val atlas = specs.joinToString(" || ") { it.atlasLine() }
        return "$title: $summary | three-step recognition flow: ${recognitionFlow.joinToString(" -> ")} | atlas categories: $atlas"
    }
}

fun exerciseVisualAtlas(): ExerciseVisualAtlas {
    return ExerciseVisualAtlas(
        title = "Unified Exercise Visual Atlas",
        summary = "Exercise Visual Legend / 统一动作图例: a stable VG-01 to VG-10 equipment/action diagram system that maps non-pro exercise names to the gym station, visible markers, and intended movement path before the user trains.",
        recognitionFlow = listOf(
            "Match the simplified instance diagram",
            "Find the real equipment markers",
            "Follow the intended movement path"
        ),
        specs = exerciseVisualLibrarySpecs()
    )
}

fun exerciseVisualLibrarySpecs(): List<ExerciseVisualSpec> {
    return listOf(
        exerciseVisualSpec("Smith incline press", "Upper chest"),
        exerciseVisualSpec("Cable lateral raise", "Side delt"),
        exerciseVisualSpec("Dumbbell row", "Back"),
        exerciseVisualSpec("Barbell squat", "Quads"),
        exerciseVisualSpec("Machine chest press", "Chest"),
        exerciseVisualSpec("Step-up bench", "Glutes"),
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
            visualId = "VG-01",
            type = ExerciseVisualType.SMITH_MACHINE,
            equipment = "Smith machine",
            equipmentZh = "史密斯机",
            figureTitle = "Fixed bar between two vertical rails",
            pattern = "Guided fixed-bar path",
            primaryMuscle = primaryMuscle,
            setupCue = "Align bench or stance under the fixed bar path",
            example = "Incline Smith Press",
            lookFor = "Look for two rails and a fixed bar",
            quickVisualCue = "图例: 两根竖轨 + 一根固定杠",
            findEquipmentCue = "找器械: 史密斯架、固定杠、安全挂钩",
            movementPathCue = "动作路径: 杠铃沿竖轨上下移动",
            instanceCue = "Two vertical rails with a bar that moves on a fixed track",
            actionPathCue = "The bar travels up and down on rails while the bench or stance stays under the track",
            beginnerCue = "先找两根竖轨和一根固定杠；上斜推、深蹲、划船都沿这条轨道完成。",
            equipmentMarkers = listOf("two vertical rails", "fixed guided bar", "safety hooks"),
            commonMovements = listOf("Incline Smith Press", "Smith Squat", "Smith Row")
        )

        text.contains("cable") ||
            text.contains("pulley") ||
            text.contains("rope") ||
            text.contains("pushdown") ||
            text.contains("face pull") ||
            text.contains("pulldown") -> ExerciseVisualSpec(
            visualId = "VG-02",
            type = ExerciseVisualType.CABLE,
            equipment = "Cable station",
            equipmentZh = "绳索龙门架/滑轮器械",
            figureTitle = "Pulley tower with cable and handle",
            pattern = "Pulley resistance",
            primaryMuscle = primaryMuscle,
            setupCue = "Set pulley height before choosing handle",
            example = "Cable Lateral Raise",
            lookFor = "Look for a cable, pulley, and handle",
            quickVisualCue = "图例: 立柱滑轮 + 钢索 + 把手",
            findEquipmentCue = "找器械: 龙门架、下拉机、绳索把手或绳子",
            movementPathCue = "动作路径: 把手沿钢索方向受力",
            instanceCue = "A cable runs through a pulley to a handle, rope, bar, or ankle strap",
            actionPathCue = "The handle follows the cable line from a high, middle, or low pulley",
            beginnerCue = "先找滑轮、钢索和把手/绳子；动作方向通常跟钢索拉力方向相反。",
            equipmentMarkers = listOf("pulley", "visible cable", "handle or rope"),
            commonMovements = listOf("Cable Lateral Raise", "Lat Pulldown", "Rope Pushdown")
        )

        text.contains("leg press") ||
            text.contains("hack squat") ||
            text.contains("pendulum") -> ExerciseVisualSpec(
            visualId = "VG-09",
            type = ExerciseVisualType.LEG_PRESS,
            equipment = "Leg press or hack squat",
            equipmentZh = "腿举/哈克深蹲器械",
            figureTitle = "Sled or foot platform on a guided lower-body path",
            pattern = "Guided lower-body sled path",
            primaryMuscle = primaryMuscle,
            setupCue = "Set seat, foot position, safety stops, and controlled depth",
            example = "Leg Press",
            lookFor = "Look for a sled platform, seat, and safety handles",
            quickVisualCue = "图例: 大脚踏板 + 座椅/肩垫 + 斜轨",
            findEquipmentCue = "找器械: 腿举机、哈克深蹲机或摆式深蹲机",
            movementPathCue = "动作路径: 脚推平台或身体沿轨道蹲起",
            instanceCue = "A seat or shoulder pad setup pushes against a sled or fixed foot platform",
            actionPathCue = "The feet push a sled or fixed platform while the body stays supported",
            beginnerCue = "先找大脚踏板、座椅或肩垫、安全把手；腿部沿固定轨迹推开或蹲起。",
            equipmentMarkers = listOf("large foot plate", "seat or shoulder pads", "safety handles"),
            commonMovements = listOf("Leg Press", "Hack Squat", "Pendulum Squat")
        )

        text.contains("pull-up") ||
            text.contains("pullup") ||
            text.contains("chin-up") ||
            text.contains("chinup") ||
            text.contains("dip") -> ExerciseVisualSpec(
            visualId = "VG-07",
            type = ExerciseVisualType.PULL_UP_STATION,
            equipment = "Pull-up/Dip station",
            equipmentZh = "引体向上/双杠训练站",
            figureTitle = "Tall frame with overhead bar and dip handles",
            pattern = "Bodyweight vertical support",
            primaryMuscle = primaryMuscle,
            setupCue = "Choose grip, brace, and use assistance if reps break down",
            example = "Pull-up",
            lookFor = "Look for an overhead bar, dip handles, or assist platform",
            quickVisualCue = "图例: 高架 + 头顶横杆 + 双杠把手",
            findEquipmentCue = "找器械: 引体架、双杠站或辅助引体机",
            movementPathCue = "动作路径: 身体垂直上拉或撑起",
            instanceCue = "A tall frame with overhead grips, dip handles, or an assisted platform",
            actionPathCue = "The body moves vertically while hanging from grips or supporting on handles",
            beginnerCue = "先找头顶横杆、双杠把手或辅助踏板；身体重量就是主要阻力。",
            equipmentMarkers = listOf("overhead grip bar", "dip handles", "assist platform"),
            commonMovements = listOf("Pull-up", "Chin-up", "Dip")
        )

        text.contains("band") -> ExerciseVisualSpec(
            visualId = "VG-08",
            type = ExerciseVisualType.BAND,
            equipment = "Resistance band",
            equipmentZh = "弹力带",
            figureTitle = "Elastic band anchored to a fixed point or feet",
            pattern = "Elastic resistance",
            primaryMuscle = primaryMuscle,
            setupCue = "Anchor the band securely and control the end range",
            example = "Band Face Pull",
            lookFor = "Look for a band anchored to a rack, door, or post",
            quickVisualCue = "图例: 弹力带 + 固定点 + 拉伸线",
            findEquipmentCue = "找器械: 弹力带和可靠固定点",
            movementPathCue = "动作路径: 离固定点越远阻力越大",
            instanceCue = "A loop or tube band stretches from an anchor point or under the feet",
            actionPathCue = "The band stretches as the hands or body move away from the anchor",
            beginnerCue = "先确认弹力带固定点可靠；越拉远阻力越大，末端要更慢控制。",
            equipmentMarkers = listOf("elastic loop or tube", "secure anchor", "visible stretch"),
            commonMovements = listOf("Band Face Pull", "Band Pull-apart", "Banded Push-up")
        )

        text.contains("dumbbell") ||
            text.contains("db ") ||
            text.endsWith(" db") -> ExerciseVisualSpec(
            visualId = "VG-03",
            type = ExerciseVisualType.DUMBBELL,
            equipment = "Dumbbells",
            equipmentZh = "哑铃",
            figureTitle = "One or two short handheld weights",
            pattern = "Free-weight unilateral control",
            primaryMuscle = primaryMuscle,
            setupCue = "Match both sides and control the path",
            example = "Dumbbell Row",
            lookFor = "Look for one or two handheld weights",
            quickVisualCue = "图例: 一只或一对短柄重量",
            findEquipmentCue = "找器械: 哑铃架上的成对重量",
            movementPathCue = "动作路径: 左右手各自独立控制轨迹",
            instanceCue = "One or two short handheld weights move independently",
            actionPathCue = "Each hand controls its own weight path instead of sharing one fixed bar",
            beginnerCue = "先找一对短柄重量；左右手可以独立移动，所以更需要控制稳定。",
            equipmentMarkers = listOf("short handles", "paired weights", "independent sides"),
            commonMovements = listOf("Dumbbell Row", "Dumbbell Press", "Dumbbell Lateral Raise")
        )

        text.contains("barbell") ||
            text.contains("bench press") ||
            text.contains("deadlift") ||
            text.contains("squat") -> ExerciseVisualSpec(
            visualId = "VG-04",
            type = ExerciseVisualType.BARBELL,
            equipment = "Barbell",
            equipmentZh = "杠铃",
            figureTitle = "Long straight bar with plates near a rack or platform",
            pattern = "Free-weight compound lift",
            primaryMuscle = primaryMuscle,
            setupCue = "Set rack height, grip, stance, and safety pins",
            example = "Barbell Squat",
            lookFor = "Look for a straight bar, plates, and rack",
            quickVisualCue = "图例: 长直杠 + 杠铃片 + 架子",
            findEquipmentCue = "找器械: 深蹲架、卧推架、平台和杠铃片",
            movementPathCue = "动作路径: 双手共享同一根长杠轨迹",
            instanceCue = "A long straight bar with plates, usually near a rack or platform",
            actionPathCue = "Both hands share one long bar path through a squat, press, row, or hinge",
            beginnerCue = "先找长直杠和杠铃片；深蹲/卧推通常还需要架子和安全杆。",
            equipmentMarkers = listOf("long straight bar", "weight plates", "rack or platform"),
            commonMovements = listOf("Barbell Squat", "Bench Press", "Deadlift")
        )

        text.contains("bench") ||
            text.contains("hip thrust") ||
            text.contains("step-up") ||
            text.contains("step up") ||
            text.contains("bulgarian") -> ExerciseVisualSpec(
            visualId = "VG-06",
            type = ExerciseVisualType.BENCH,
            equipment = "Adjustable bench",
            equipmentZh = "可调训练凳",
            figureTitle = "Flat or angled bench supporting the body",
            pattern = "Bench-supported movement",
            primaryMuscle = primaryMuscle,
            setupCue = "Set bench angle and body contact before loading",
            example = "Incline Dumbbell Press",
            lookFor = "Look for a flat or adjustable bench",
            quickVisualCue = "图例: 平凳/斜凳支撑身体",
            findEquipmentCue = "找器械: 可调训练凳或平凳",
            movementPathCue = "动作路径: 凳子固定身体角度，四肢完成动作",
            instanceCue = "A flat or angled bench supports the torso, back foot, hips, or chest",
            actionPathCue = "The bench fixes body angle or support while the limbs perform the loaded movement",
            beginnerCue = "先找平凳或可调斜凳；它可能支撑背、胸、臀或后脚，而不是主要阻力。",
            equipmentMarkers = listOf("flat or angled pad", "bench legs", "body support point"),
            commonMovements = listOf("Incline Dumbbell Press", "Chest-supported Row", "Bulgarian Split Squat")
        )

        text.contains("machine") ||
            text.contains("press") ||
            text.contains("extension") ||
            text.contains("curl") ||
            text.contains("pec deck") ||
            text.contains("leg") -> ExerciseVisualSpec(
            visualId = "VG-05",
            type = ExerciseVisualType.MACHINE,
            equipment = "Machine",
            equipmentZh = "固定轨迹器械",
            figureTitle = "Seat, pads, handles, and lever arms",
            pattern = "Guided resistance",
            primaryMuscle = primaryMuscle,
            setupCue = "Adjust seat and pads to match joint axis",
            example = "Machine Chest Press",
            lookFor = "Look for a seat, pads, handles, or lever arms",
            quickVisualCue = "图例: 座椅 + 靠垫 + 把手/摆臂",
            findEquipmentCue = "找器械: 有座椅、插销、把手或靠垫的固定器械",
            movementPathCue = "动作路径: 把手或垫片沿机器轨迹移动",
            instanceCue = "A seated or padded machine guides the movement through handles or lever arms",
            actionPathCue = "Handles or pads move on a guided arc or fixed track after the seat is adjusted",
            beginnerCue = "先找座椅、靠垫、把手和调节插销；坐好后沿器械轨迹发力。",
            equipmentMarkers = listOf("seat", "pads", "handles or lever arms"),
            commonMovements = listOf("Machine Chest Press", "Leg Extension", "Seated Row")
        )

        else -> ExerciseVisualSpec(
            visualId = "VG-10",
            type = ExerciseVisualType.BODYWEIGHT,
            equipment = "Bodyweight or open station",
            equipmentZh = "自重/开放训练空间",
            figureTitle = "Body moving on floor, bar, bench, or open space",
            pattern = "Movement pattern demo",
            primaryMuscle = primaryMuscle,
            setupCue = "Use notes or photos when the exact setup is unclear",
            example = "Push-up",
            lookFor = "Look for floor, bench, bar, bands, or open space",
            quickVisualCue = "图例: 身体 + 地面/支撑物",
            findEquipmentCue = "找器械: 地面、开放空间、横杆、凳子或简单支撑",
            movementPathCue = "动作路径: 身体按推、拉、支撑或核心模式移动",
            instanceCue = "The body moves on the floor, open space, a bench, bar, or simple support",
            actionPathCue = "The body moves through the pattern while simple supports define the setup",
            beginnerCue = "如果没有明显器械，先看身体在地面、凳子、横杆或开放空间里的动作路径。",
            equipmentMarkers = listOf("floor or open space", "simple support", "bodyweight movement"),
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
