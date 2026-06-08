package com.iwanttobeanifbbpro.app.core

import com.iwanttobeanifbbpro.app.data.DailyLog

data class MealAssemblyGuide(
    val title: String,
    val plateStructure: String,
    val proteinAnchor: String,
    val carbAnchor: String,
    val fatControl: String,
    val fiberMicros: String,
    val shoppingCue: String,
    val prepCue: String,
    val photoLoggingCue: String,
    val avoidCue: String
) {
    fun promptLine(): String {
        return "Meal Assembly Guide: $title | Plate structure: $plateStructure | Protein anchor: $proteinAnchor | Carb anchor: $carbAnchor | Fat control: $fatControl | Fiber/micros: $fiberMicros | Shopping cue: $shoppingCue | Prep cue: $prepCue | Photo/logging cue: $photoLoggingCue | Avoid cue: $avoidCue"
    }
}

fun mealAssemblyGuide(log: DailyLog): MealAssemblyGuide {
    val totals = log.nutritionTotals()
    val caloriesRemaining = log.targets.calories - totals.calories
    val proteinRemaining = log.targets.protein - totals.protein
    val carbsRemaining = log.targets.carbs - totals.carbs
    val fatRemaining = log.targets.fat - totals.fat
    val fiberRemaining = log.targets.fiber - totals.fiber
    val trainingPending = log.plannedHardSets() > 0 && log.completedHardSets() < log.plannedHardSets()
    val trainingDone = log.trainingSession.completed || (log.plannedHardSets() > 0 && log.completedHardSets() >= log.plannedHardSets())

    val title = when {
        caloriesRemaining < -150 -> "Lean correction plate"
        proteinRemaining > 35 -> "Protein-first bodybuilding plate"
        trainingPending && carbsRemaining > 60 -> "Training fuel plate"
        trainingDone && carbsRemaining > 45 -> "Post-workout recovery plate"
        fiberRemaining > 10 -> "Fiber and micronutrient plate"
        else -> "Balanced physique plate"
    }
    val plateStructure = when (title) {
        "Lean correction plate" -> "One palm-plus lean protein, two fists vegetables, very small starch, sauce measured separately."
        "Protein-first bodybuilding plate" -> "Two palms lean protein, one fist carb if calories allow, one to two fists vegetables."
        "Training fuel plate" -> "One to two palms lean protein, one to two fists easy carbs, low fat, low-to-moderate fiber."
        "Post-workout recovery plate" -> "One to two palms protein, one to two fists carbs, vegetables or fruit, fats kept moderate."
        "Fiber and micronutrient plate" -> "One palm protein, one fist high-fiber carb, two fists colorful vegetables or fruit."
        else -> "One palm protein, one fist carb, one thumb fat, one to two fists vegetables."
    }
    val proteinAnchor = when {
        proteinRemaining > 55 -> "Use chicken breast, turkey, white fish, lean beef, egg whites, whey, tofu, or Greek yogurt for about 45-65 g protein."
        proteinRemaining > 30 -> "Use one large lean protein serving for about 30-45 g protein."
        else -> "Keep a normal protein serving so the day stays anchored without forcing extra calories."
    }
    val carbAnchor = when {
        caloriesRemaining < -150 -> "Skip dense carbs unless training is still pending; use vegetables or berries."
        trainingPending && carbsRemaining > 60 -> "Use rice, oats, potatoes, pasta, bread, bananas, or cereal 60-120 minutes around training."
        trainingDone && carbsRemaining > 45 -> "Use rice, potatoes, oats, fruit, or noodles for recovery without pushing fats high."
        carbsRemaining > 70 -> "Use a measured carb serving and keep it easy to log."
        else -> "Keep carbs modest and spend remaining calories on protein and vegetables."
    }
    val fatControl = when {
        fatRemaining < 0 -> "Avoid added oil, nuts, cheese, fatty sauces, and fried food for the rest of the day."
        fatRemaining < 10 -> "Keep fats very low: grill, steam, air fry, and measure sauces."
        else -> "Use one measured thumb of fats such as olive oil, avocado, nuts, whole eggs, or salmon if it fits the target."
    }
    val fiberMicros = when {
        fiberRemaining > 12 -> "Add a high-fiber side: vegetables, berries, oats, beans, lentils, potatoes with skin, or whole grains."
        fiberRemaining > 6 -> "Add at least one vegetable or fruit serving."
        else -> "Keep vegetables present, but do not force extra fiber if digestion or training timing would suffer."
    }
    val shoppingCue = when (title) {
        "Protein-first bodybuilding plate" -> "Keep lean protein, microwave rice or potatoes, Greek yogurt, and frozen vegetables stocked."
        "Training fuel plate" -> "Keep fast carbs ready: rice packs, oats, bananas, cereal, bagels, potatoes, or sports drink if needed."
        "Lean correction plate" -> "Choose lean protein and vegetables; skip restaurant meals with hidden oil when possible."
        else -> "Use simple repeatable staples so the meal can be logged without guessing."
    }
    val prepCue = when {
        trainingPending -> "Prep this as a low-fat, easy-digesting meal so training performance is not limited by digestion."
        trainingDone -> "Prep this as a recovery meal and log the cooked carb amount, protein weight, oil, and sauce."
        else -> "Batch protein and carbs separately so portions can be adjusted meal by meal."
    }
    val photoLoggingCue = if (log.meals.isEmpty()) {
        "If this is the first meal, photograph the full plate and include any labels or package weights."
    } else {
        "Photograph uncertain restaurant portions, oils, sauces, labels, and bowl depth before AI estimation."
    }
    val avoidCue = when {
        caloriesRemaining < -150 -> "Do not compensate by cutting tomorrow's protein or skipping recovery nutrition."
        fatRemaining < 0 -> "Do not add fats because the meal looks small; use vegetables and lean protein for volume."
        proteinRemaining > 35 -> "Do not spend remaining calories on snacks before protein is handled."
        else -> "Do not chase perfect numbers; get close, log honestly, and let the trend guide adjustments."
    }

    return MealAssemblyGuide(
        title = title,
        plateStructure = plateStructure,
        proteinAnchor = proteinAnchor,
        carbAnchor = carbAnchor,
        fatControl = fatControl,
        fiberMicros = fiberMicros,
        shoppingCue = shoppingCue,
        prepCue = prepCue,
        photoLoggingCue = photoLoggingCue,
        avoidCue = avoidCue
    )
}
