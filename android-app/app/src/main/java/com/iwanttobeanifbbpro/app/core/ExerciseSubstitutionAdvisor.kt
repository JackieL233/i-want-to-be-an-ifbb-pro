package com.iwanttobeanifbbpro.app.core

import com.iwanttobeanifbbpro.app.data.AthleteProfile
import com.iwanttobeanifbbpro.app.data.ExerciseEntry
import java.util.Locale

data class ExerciseSubstitutionOption(
    val name: String,
    val targetMuscle: String,
    val pattern: String,
    val equipment: String,
    val reason: String,
    val setupCue: String,
    val fatigueCost: String,
    val visualSpec: ExerciseVisualSpec
) {
    fun promptLine(): String {
        return "$name | same target muscle: $targetMuscle | same movement pattern: $pattern | equipment: $equipment | reason: $reason | setup cue: $setupCue | fatigue cost: $fatigueCost | preserve rep range unless pain/technique changes require a smaller range | visual guide ID: ${visualSpec.visualId} ${visualSpec.equipment} (${visualSpec.equipmentZh})"
    }
}

data class ExerciseSubstitutionGuide(
    val statusLabel: String,
    val triggerReason: String,
    val primaryOption: ExerciseSubstitutionOption,
    val secondaryOptions: List<ExerciseSubstitutionOption>,
    val keepIntentCue: String,
    val loadAdjustmentCue: String,
    val aiReviewFocus: String
) {
    fun promptLine(): String {
        val secondaryLine = secondaryOptions.joinToString("; ") { it.promptLine() }.ifBlank { "none" }
        return "Exercise Substitution Coach: $statusLabel | equipment unavailable check included | trigger reason: $triggerReason | primaryOption: ${primaryOption.promptLine()} | secondaryOptions: $secondaryLine | keepIntentCue: $keepIntentCue | loadAdjustmentCue: $loadAdjustmentCue | aiReviewFocus: $aiReviewFocus"
    }
}

fun ExerciseEntry.exerciseSubstitutionGuide(profile: AthleteProfile = AthleteProfile()): ExerciseSubstitutionGuide {
    return exerciseSubstitutionGuide(
        name = name,
        targetMuscle = targetMuscle,
        notes = notes,
        profile = profile,
        setNotes = trackedSets().map { it.notes }
    )
}

fun exerciseSubstitutionGuide(
    name: String,
    targetMuscle: String,
    notes: String = "",
    profile: AthleteProfile = AthleteProfile(),
    setNotes: List<String> = emptyList()
): ExerciseSubstitutionGuide {
    val visual = exerciseVisualSpec(name, targetMuscle)
    val text = listOf(name, targetMuscle, notes, setNotes.joinToString(" "), profile.constraints)
        .joinToString(" ")
        .lowercase(Locale.US)
    val equipmentUnavailable = text.hasEquipmentUnavailableSignal()
    val painSignal = text.hasSubstitutionPainSignal()
    val techniqueSignal = text.hasSubstitutionTechniqueSignal()
    val equipmentMismatch = !visual.isAvailableIn(profile.availableEquipment)
    val triggerReason = when {
        painSignal -> "Pain or discomfort signal logged; protect joints before adding volume."
        equipmentUnavailable -> "Equipment unavailable or occupied signal logged."
        equipmentMismatch -> "Profile equipment may not include ${visual.equipment}; use an available same-intent option."
        techniqueSignal -> "Technique quality signal logged; choose a more stable setup if form breaks down."
        else -> "Plan A available; keep a same-intent backup ready if the gym floor changes."
    }
    val statusLabel = when {
        painSignal -> "Pain-aware swap"
        equipmentUnavailable -> "Equipment unavailable"
        equipmentMismatch -> "Equipment mismatch"
        techniqueSignal -> "Technique-stability swap"
        else -> "Plan A available"
    }
    val preferredBias = when {
        painSignal || techniqueSignal -> OptionBias.STABLE_LOW_JOINT_COST
        equipmentUnavailable || equipmentMismatch -> OptionBias.AVAILABLE_EQUIPMENT
        else -> OptionBias.BACKUP
    }
    val options = substitutionOptionsFor(
        name = name,
        targetMuscle = targetMuscle.ifBlank { visual.primaryMuscle },
        preferredBias = preferredBias,
        profile = profile
    )
    val primary = options.firstOrNull()
        ?: substitutionOption(
            name = name,
            targetMuscle = targetMuscle.ifBlank { visual.primaryMuscle },
            pattern = inferMovementPattern(name, targetMuscle),
            reason = "Keep the planned movement because no better same-intent substitute was identified.",
            setupCue = visual.setupCue,
            fatigueCost = "Same fatigue cost"
        )
    val keepIntentCue = "Keep the same target muscle, same movement pattern, planned set count, planned rep range, and planned RIR unless pain or technique quality requires reducing range, load, or volume."
    val loadAdjustmentCue = when {
        painSignal -> "Start 10-20% lighter or choose a load that feels at least 2 RIR easier; stop if pain changes mechanics."
        techniqueSignal -> "Start 5-15% lighter and keep the movement stable before matching planned effort."
        equipmentUnavailable || equipmentMismatch -> "Use the first set to find the load that matches the planned RIR; preserve rep range before chasing the original load."
        else -> "If you use the backup, treat set 1 as a calibration set and preserve rep range and RIR."
    }
    val aiReviewFocus = "Compare whether the substitution was actually performed, why it was needed, target muscle stimulus, same movement pattern, preserved rep range, fatigue cost, visual guide ID, load, reps, RIR, pain notes, and Exercise History before changing the plan."

    return ExerciseSubstitutionGuide(
        statusLabel = statusLabel,
        triggerReason = triggerReason,
        primaryOption = primary,
        secondaryOptions = options.drop(1).take(3),
        keepIntentCue = keepIntentCue,
        loadAdjustmentCue = loadAdjustmentCue,
        aiReviewFocus = aiReviewFocus
    )
}

private enum class OptionBias {
    BACKUP,
    AVAILABLE_EQUIPMENT,
    STABLE_LOW_JOINT_COST
}

private fun substitutionOptionsFor(
    name: String,
    targetMuscle: String,
    preferredBias: OptionBias,
    profile: AthleteProfile
): List<ExerciseSubstitutionOption> {
    val text = "$name $targetMuscle".lowercase(Locale.US)
    val pattern = inferMovementPattern(name, targetMuscle)
    val rawOptions = when {
        text.contains("lateral raise") || text.contains("side delt") -> listOf(
            option("Dumbbell Lateral Raise", targetMuscle, "Shoulder abduction", "same target muscle and same movement pattern with independent dumbbells", "Lead with elbows; stop before traps take over.", "Similar local fatigue cost"),
            option("Machine Lateral Raise", targetMuscle, "Shoulder abduction", "same target muscle and same movement pattern with a more guided path", "Adjust seat so pads meet mid-upper arm.", "Lower stability cost"),
            option("Band Lateral Raise", targetMuscle, "Shoulder abduction", "same target muscle and same movement pattern when cables are busy", "Stand on or anchor the band securely.", "Lower load ceiling")
        )

        text.contains("smith") && (text.contains("press") || targetMuscle.contains("chest", ignoreCase = true)) -> listOf(
            option("Incline Dumbbell Press", targetMuscle, "Horizontal/incline push", "same target muscle and same movement pattern without a fixed Smith path", "Use a low incline and keep shoulder blades set.", "Similar fatigue cost"),
            option("Machine Chest Press", targetMuscle, "Horizontal push", "same target muscle and same movement pattern with a more stable machine", "Set handles near mid-chest and press without shrugging.", "Lower joint-stability cost"),
            option("Push-up", targetMuscle, "Horizontal push", "same target muscle and same movement pattern when benches or machines are unavailable", "Elevate hands or add load to match the planned RIR.", "Lower external load")
        )

        text.contains("bench press") || (text.contains("press") && targetMuscle.contains("chest", ignoreCase = true)) -> listOf(
            option("Dumbbell Press", targetMuscle, "Horizontal push", "same target muscle and same movement pattern with independent sides", "Match both sides and keep forearms near vertical.", "Similar fatigue cost"),
            option("Machine Chest Press", targetMuscle, "Horizontal push", "same target muscle and same movement pattern with a stable guided path", "Adjust seat so handles start near chest line.", "Lower stability cost"),
            option("Push-up", targetMuscle, "Horizontal push", "same target muscle and same movement pattern with simple setup", "Use incline, flat, or weighted push-up to match effort.", "Lower external load")
        )

        text.contains("shoulder press") || text.contains("overhead press") -> listOf(
            option("Machine Shoulder Press", targetMuscle, "Vertical push", "same target muscle and same movement pattern with a stable seat", "Set seat so handles start around chin or shoulder height.", "Lower stability cost"),
            option("Dumbbell Shoulder Press", targetMuscle, "Vertical push", "same target muscle and same movement pattern with free weights", "Brace ribs down and press in a pain-free path.", "Similar fatigue cost"),
            option("Landmine Press", targetMuscle, "Angled vertical push", "similar target with a joint-friendlier pressing arc", "Press up and forward without low-back arch.", "Lower shoulder-irritation cost")
        )

        text.contains("pull-up") || text.contains("pullup") || text.contains("chin-up") || text.contains("chinup") -> listOf(
            option("Lat Pulldown", targetMuscle, "Vertical pull", "same target muscle and same movement pattern with adjustable load", "Drive elbows down and avoid swinging.", "Similar local fatigue cost"),
            option("Assisted Pull-up", targetMuscle, "Vertical pull", "same target muscle and same movement pattern with assistance", "Choose assistance that keeps reps in range.", "Similar fatigue cost"),
            option("Band-Assisted Pull-up", targetMuscle, "Vertical pull", "same target muscle and same movement pattern when the assisted machine is busy", "Anchor band safely and keep the descent controlled.", "Lower load precision")
        )

        text.contains("pulldown") -> listOf(
            option("Assisted Pull-up", targetMuscle, "Vertical pull", "same target muscle and same movement pattern if the pulldown station is busy", "Use assistance that lets the lats drive the set.", "Similar fatigue cost"),
            option("Single-Arm Cable Pulldown", targetMuscle, "Vertical pull", "same target muscle and similar movement pattern with unilateral cable setup", "Drive elbow toward hip without twisting.", "Similar local fatigue cost"),
            option("Straight-Arm Pulldown", targetMuscle, "Lat shoulder-extension pull", "same target muscle bias with lower elbow-flexor demand", "Keep arms long and move from the shoulders.", "Lower systemic fatigue")
        )

        text.contains("row") -> listOf(
            option("Chest-Supported Row", targetMuscle, "Horizontal pull", "same target muscle and same movement pattern with less low-back fatigue", "Keep chest supported and pause briefly at the top.", "Lower systemic fatigue"),
            option("Seated Cable Row", targetMuscle, "Horizontal pull", "same target muscle and same movement pattern with a cable station", "Pull elbows back without leaning into the rep.", "Similar fatigue cost"),
            option("Machine Row", targetMuscle, "Horizontal pull", "same target muscle and same movement pattern with guided handles", "Adjust chest pad so the shoulder can protract and retract.", "Lower setup cost"),
            option("One-Arm Dumbbell Row", targetMuscle, "Horizontal pull", "same target muscle and same movement pattern with one dumbbell", "Brace on a bench and keep hips square.", "Similar local fatigue cost")
        )

        text.contains("squat") || text.contains("leg press") || text.contains("quad") -> listOf(
            option("Hack Squat", targetMuscle, "Squat/knee-dominant", "same target muscle and same movement pattern with a guided sled", "Set stance for knee tracking and use safety stops.", "Similar fatigue cost"),
            option("Leg Press", targetMuscle, "Squat/knee-dominant", "same target muscle and similar knee-dominant pattern with more support", "Choose foot position and depth that keep pelvis stable.", "Similar local fatigue cost"),
            option("Goblet Squat", targetMuscle, "Squat/knee-dominant", "same target muscle and same movement pattern with simple equipment", "Hold one dumbbell high and keep pressure through midfoot.", "Lower load ceiling"),
            option("Bulgarian Split Squat", targetMuscle, "Single-leg squat/knee-dominant", "same target muscle with unilateral squat pattern when racks are busy", "Use a bench only as rear-foot support.", "Higher local fatigue")
        )

        text.contains("deadlift") || text.contains("rdl") || text.contains("hinge") || text.contains("hamstring") -> listOf(
            option("Romanian Deadlift", targetMuscle, "Hip hinge", "same target muscle and same movement pattern with controlled range", "Push hips back and stop before spinal rounding.", "Similar fatigue cost"),
            option("Dumbbell Romanian Deadlift", targetMuscle, "Hip hinge", "same target muscle and same movement pattern without a barbell", "Keep dumbbells close and load the hamstring stretch.", "Lower load ceiling"),
            option("Hip Thrust", targetMuscle, "Hip extension", "same posterior-chain intent with less spinal loading", "Lock ribs down and pause at the top.", "Lower spinal fatigue"),
            option("Back Extension", targetMuscle, "Hip extension", "same posterior-chain intent with controlled bodyweight loading", "Move through hips and avoid overextending the low back.", "Lower systemic fatigue")
        )

        text.contains("curl") || text.contains("biceps") -> listOf(
            option("Dumbbell Curl", targetMuscle, "Elbow flexion", "same target muscle and same movement pattern with dumbbells", "Keep elbows stable and match both sides.", "Similar fatigue cost"),
            option("Cable Curl", targetMuscle, "Elbow flexion", "same target muscle and same movement pattern with constant cable tension", "Set pulley low and keep upper arms still.", "Similar local fatigue cost"),
            option("Band Curl", targetMuscle, "Elbow flexion", "same target muscle and same movement pattern when equipment is limited", "Stand on the band and slow the top range.", "Lower load precision")
        )

        text.contains("triceps") || text.contains("pushdown") || text.contains("extension") || text.contains("skull") -> listOf(
            option("Rope Triceps Pushdown", targetMuscle, "Elbow extension", "same target muscle and same movement pattern with cable tension", "Pin elbows and control the top stretch.", "Similar local fatigue cost"),
            option("Overhead Cable Extension", targetMuscle, "Elbow extension", "same target muscle with more long-head stretch", "Keep ribs down and elbows mostly fixed.", "Similar local fatigue cost"),
            option("Dumbbell Skull Crusher", targetMuscle, "Elbow extension", "same target muscle and same movement pattern without cable", "Use a pain-free elbow path and stop before irritation.", "Similar fatigue cost"),
            option("Band Pushdown", targetMuscle, "Elbow extension", "same target muscle and same movement pattern with a band", "Anchor band high and control lockout.", "Lower load precision")
        )

        text.contains("calf") -> listOf(
            option("Standing Calf Raise", targetMuscle, "Ankle plantar flexion", "same target muscle and same movement pattern standing", "Use a full stretch and hard top squeeze.", "Similar local fatigue cost"),
            option("Seated Calf Raise", targetMuscle, "Ankle plantar flexion", "same target muscle with more bent-knee calf bias", "Pause in the stretched position before each rep.", "Similar local fatigue cost"),
            option("Leg Press Calf Raise", targetMuscle, "Ankle plantar flexion", "same target muscle and same movement pattern on a leg press", "Keep knees softly locked and use controlled range.", "Similar fatigue cost"),
            option("Single-Leg Bodyweight Calf Raise", targetMuscle, "Ankle plantar flexion", "same target muscle when machines are unavailable", "Use a step and slow tempo to match effort.", "Lower load ceiling")
        )

        text.contains("crunch") || text.contains("abs") || text.contains("plank") -> listOf(
            option("Cable Crunch", targetMuscle, "Spinal flexion/core", "same target muscle and same movement pattern with cable loading", "Curl ribs toward pelvis without pulling with arms.", "Similar local fatigue cost"),
            option("Machine Crunch", targetMuscle, "Spinal flexion/core", "same target muscle and same movement pattern on a guided machine", "Adjust pad and move through a controlled range.", "Lower setup cost"),
            option("Reverse Crunch", targetMuscle, "Spinal flexion/core", "same target muscle when equipment is unavailable", "Curl pelvis up slowly and avoid swinging.", "Lower external load"),
            option("Plank", targetMuscle, "Anti-extension core", "same core intent with a static lower-skill option", "Brace ribs down and breathe through the hold.", "Lower fatigue cost")
        )

        else -> listOf(
            option(name, targetMuscle, pattern, "planned movement remains the closest match", "Use the current visual guide and log notes if the setup is unclear.", "Same fatigue cost"),
            option("Machine ${targetMuscle.ifBlank { "Target" }} Press/Row", targetMuscle, pattern, "more stable machine option that can preserve the target when available", "Adjust seat/pads before loading.", "Lower stability cost"),
            option("Dumbbell ${targetMuscle.ifBlank { "Target" }} Variation", targetMuscle, pattern, "free-weight option when the planned station is busy", "Start conservative and match planned RIR.", "Similar fatigue cost")
        )
    }
    val sorted = when (preferredBias) {
        OptionBias.STABLE_LOW_JOINT_COST -> rawOptions.sortedBy { option ->
            when {
                option.fatigueCost.contains("Lower", ignoreCase = true) -> 0
                option.equipment.contains("Machine", ignoreCase = true) -> 1
                else -> 2
            }
        }
        OptionBias.AVAILABLE_EQUIPMENT -> {
            val available = rawOptions.filter { it.visualSpec.isAvailableIn(profile.availableEquipment) }
            (available + rawOptions.filterNot { option -> available.any { it.name == option.name } })
        }
        OptionBias.BACKUP -> rawOptions
    }
    return sorted.distinctBy { it.name.lowercase(Locale.US) }.take(4)
}

private fun option(
    name: String,
    targetMuscle: String,
    pattern: String,
    reason: String,
    setupCue: String,
    fatigueCost: String
): ExerciseSubstitutionOption = substitutionOption(name, targetMuscle, pattern, reason, setupCue, fatigueCost)

private fun substitutionOption(
    name: String,
    targetMuscle: String,
    pattern: String,
    reason: String,
    setupCue: String,
    fatigueCost: String
): ExerciseSubstitutionOption {
    val visual = exerciseVisualSpec(name, targetMuscle)
    return ExerciseSubstitutionOption(
        name = name,
        targetMuscle = targetMuscle.ifBlank { visual.primaryMuscle },
        pattern = pattern,
        equipment = visual.equipment,
        reason = reason,
        setupCue = setupCue,
        fatigueCost = fatigueCost,
        visualSpec = visual
    )
}

private fun inferMovementPattern(name: String, targetMuscle: String): String {
    val text = "$name $targetMuscle".lowercase(Locale.US)
    return when {
        text.contains("lateral raise") || text.contains("side delt") -> "Shoulder abduction"
        text.contains("press") && (text.contains("chest") || text.contains("bench") || text.contains("incline")) -> "Horizontal/incline push"
        text.contains("shoulder press") || text.contains("overhead press") -> "Vertical push"
        text.contains("row") -> "Horizontal pull"
        text.contains("pull-up") || text.contains("pullup") || text.contains("chin") || text.contains("pulldown") -> "Vertical pull"
        text.contains("squat") || text.contains("leg press") || text.contains("quad") -> "Squat/knee-dominant"
        text.contains("deadlift") || text.contains("rdl") || text.contains("hamstring") || text.contains("glute") -> "Hip hinge/posterior chain"
        text.contains("curl") || text.contains("biceps") -> "Elbow flexion"
        text.contains("triceps") || text.contains("pushdown") || text.contains("extension") -> "Elbow extension"
        text.contains("calf") -> "Ankle plantar flexion"
        text.contains("crunch") || text.contains("abs") || text.contains("plank") -> "Core"
        else -> "Same movement pattern"
    }
}

private fun String.hasEquipmentUnavailableSignal(): Boolean {
    return listOf(
        "occupied",
        "busy",
        "taken",
        "unavailable",
        "not available",
        "no machine",
        "no cable",
        "no dumbbell",
        "no barbell",
        "no bench",
        "broken",
        "waiting",
        "crowded",
        "器械被占",
        "被占",
        "占用",
        "没有器械",
        "没有绳索",
        "没有哑铃",
        "没有杠铃",
        "排队",
        "坏了"
    ).any { contains(it) }
}

private fun String.hasSubstitutionPainSignal(): Boolean {
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
        clean.contains("pinch") ||
        clean.contains("irritation") ||
        clean.contains("疼") ||
        clean.contains("痛") ||
        clean.contains("不舒服")
}

private fun String.hasSubstitutionTechniqueSignal(): Boolean {
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
        "动作变形",
        "不稳"
    ).any { contains(it) }
}

private fun ExerciseVisualSpec.isAvailableIn(availableEquipment: String): Boolean {
    val available = availableEquipment.lowercase(Locale.US)
    if (available.isBlank()) return true
    if (listOf("full gym", "commercial gym", "gym", "all", "complete").any { available.contains(it) }) return true
    val synonyms = when (type) {
        ExerciseVisualType.SMITH_MACHINE -> listOf("smith", "史密斯")
        ExerciseVisualType.CABLE -> listOf("cable", "pulley", "lat pulldown", "龙门", "绳索", "滑轮")
        ExerciseVisualType.DUMBBELL -> listOf("dumbbell", "db", "home", "哑铃")
        ExerciseVisualType.BARBELL -> listOf("barbell", "rack", "platform", "杠铃", "深蹲架")
        ExerciseVisualType.MACHINE -> listOf("machine", "机器", "固定器械")
        ExerciseVisualType.BENCH -> listOf("bench", "home", "凳")
        ExerciseVisualType.PULL_UP_STATION -> listOf("pull-up", "pullup", "chin", "bar", "home", "引体", "单杠")
        ExerciseVisualType.BAND -> listOf("band", "home", "弹力带")
        ExerciseVisualType.LEG_PRESS -> listOf("leg press", "hack", "pendulum", "腿举", "哈克")
        ExerciseVisualType.BODYWEIGHT -> listOf("bodyweight", "home", "floor", "open", "自重")
    }
    if (type == ExerciseVisualType.BODYWEIGHT) return true
    return synonyms.any { available.contains(it) }
}
