# Visual Analysis and Food Estimation

Use this when the user provides photos, video frames, screenshots, labels, menus, plate images, gym equipment images, exercise images, or asks for multimodal training and nutrition interpretation.

Treat visual analysis as decision support, not certainty. Always state uncertainty, ask for missing context, and connect the visual finding to the user's current training phase, nutrition targets, recovery status, and tracked data.

## Visual Inputs to Request

For exercise form analysis or equipment identification, ask for:

- Clear front, side, and rear angles when relevant.
- Start, middle, and end positions for reps, or a short video if the model runtime supports it.
- Exercise name if known, load, reps, set number, RIR/RPE, pain, target muscle, and training goal.
- User constraints: injury history, mobility limits, equipment availability, and program context.

For a food photo or meal estimate, ask for:

- Food name if known, plate/bowl size, utensils, package labels, recipe, restaurant/menu item, or weighed ingredients.
- Cooking method, visible oils/sauces, hidden ingredients, and whether the meal is finished or partially eaten.
- Current nutrition target: calories, protein, fat, carbs, fiber, meal timing, and phase goal.

## Equipment Identification

When identifying equipment:

- Name the likely machine, implement, or station and note confidence.
- Explain what it is commonly used for, target muscles, setup points, and common substitutions.
- Ask the user to confirm brand, labels, cable path, seat angle, handles, or weight stack if uncertain.
- Do not infer exact load or resistance curve from a photo unless the image clearly shows it.

## Exercise Form Analysis

For exercise form analysis:

- Identify the likely movement, target muscles, setup, range of motion, tempo, bracing, and joint positions.
- Flag visible technique issues as observations, not diagnoses.
- Separate high-confidence visual facts from uncertain inferences.
- Ask whether there is pain, discomfort, or unusual fatigue before prescribing corrections.
- Suggest one or two practical cues, regressions, substitutions, or load/volume adjustments.
- Link the recommendation to the session log: exercise, sets, reps, load, RIR/RPE, pain, stimulus, and technique quality.

Use conservative language for injury risk. Do not diagnose injuries from images. If pain, numbness, swelling, instability, fainting, chest symptoms, or red flags appear, use `safety-screening.md` and recommend qualified professional evaluation.

## Food Photo Nutrition Estimate

For food photo analysis:

- Identify visible foods and likely portion size, then estimate calories, protein, carbs, fat, fiber, and notable micronutrition patterns when useful.
- Give ranges rather than false precision when portions, recipes, oil, sauces, or labels are unknown.
- Ask for a scale weight, package label, restaurant nutrition facts, recipe, or ingredient list when accuracy matters.
- Call out hidden calorie risks: oils, butter, sauces, dressings, nuts, cheese, sugary drinks, and large restaurant portions.
- For bodybuilding goals, emphasize protein adequacy, calorie target fit, carb timing around training, fiber, hydration, and adherence.

Do not treat visual food estimation as medical nutrition therapy. If the user has diabetes, kidney disease, eating-disorder risk, pregnancy/postpartum complexity, or other medical nutrition concerns, recommend a qualified clinician or dietitian.

## Linked Check-In: Training and Nutrition

When the user provides both training visuals and food photos, run a linked check-in:

1. Summarize exercise form analysis, equipment identification, pain flags, training quality, and session-log changes.
2. Summarize food photo nutrition estimate and compare it with calories, macros, meal timing, and phase target.
3. Connect the two sides: performance, pump, recovery, hunger, digestion, energy, body-weight trend, and adherence.
4. Decide whether the limiting factor is training execution, volume, nutrition, recovery, or measurement uncertainty.
5. Change only one or two variables at a time: exercise cue, load, volume, calories, protein distribution, carbs around training, steps/cardio, or recovery.

Example linked logic:

- Poor target-muscle stimulus plus low pre-workout carbs: improve setup/cues and move carbs closer to training before adding volume.
- Good form and performance but weight gain too fast: keep training, trim calories slightly or reduce easy-to-miss fats.
- Pain flags plus aggressive deficit and fatigue: reduce risky exercise exposure, deload or modify movement, and avoid further calorie cuts.
- Good food adherence but stalled scale and waist reduction: hold calories and keep monitoring rather than overreacting.

## Output Requirements

For visual training analysis, include:

- What the image appears to show and confidence.
- Equipment/action identification.
- Form observations and uncertainty.
- One or two corrections, substitutions, or progression decisions.
- How to log it in `session-log.csv`.
- Safety boundary or referral note if needed.

For food photo nutrition analysis, include:

- Food identification and confidence.
- Portion-size assumptions.
- Nutrition estimate as a range.
- Comparison to the user's meal/day targets.
- What to adjust in the next meal or the rest of the day.
- Accuracy limitations and data needed for a better estimate.

For a linked check-in, include:

- Training and nutrition findings side by side.
- The most likely bottleneck.
- The smallest useful adjustment.
- What data or photos to collect next.
