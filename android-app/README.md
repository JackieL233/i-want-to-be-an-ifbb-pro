# I Want to be an IFBB PRO Android App

This is a native Android companion app for the `I Want to be an IFBB PRO` skill. It packages the skill assets inside the APK and lets you call an AI API for training, nutrition, visual form analysis, food-photo nutrition estimates, and linked check-ins.

## What is included

- Kotlin + Jetpack Compose Android app.
- Bundled skill assets in `app/src/main/assets/skill/`.
- Apple-inspired light UI theme with a quiet neutral background, blue action color, rounded grouped surfaces, and bottom navigation for the main daily tabs.
- Saved EN/中文 language switching through `AppLanguage`, `updateLanguage`, `loadLanguage`, and local SharedPreferences so the high-frequency app shell, Today workflow, rest timer, and safety copy can be used in English or Chinese.
- A first-screen `Today Flow Coach` on `Today` with `TodayFlowCoachState` and `todayFlowCoachState`, showing the next best action, daily loop progress, readiness, training sets, nutrition gap, AI gate, and one primary button before any advanced cards.
- Expandable daily detail layers that keep `Start Here`, `DailyStartStep`, `StartHereCoachCard`, `StartHereStepRow`, AI setup, command center, execution plan, checklist, tomorrow brief, weekly check-in, trends, and recovery data available without making the first screen feel like a feature wall.
- A one-tap `Workout Flow Coach` on `Training` that surfaces the next useful action first: load the plan, warm up, complete the next set, start rest, close out, then run AI review.
- A collapsible `Professional detail layers` section on `Training` so readiness, ramp planning, next-set analysis, session quality, and closeout logic stay available without overwhelming beginners on the first screen.
- A one-tap `Meal Flow Coach` on `Nutrition` with `MealFlowCoachCard` and `showNutritionDetails`, showing the next meal target, macro gap, recommended template, food-photo path, AI gate, and `Prefill next meal` before advanced nutrition cards.
- Collapsible `Nutrition detail layers` so Nutrition Pacing, Next Meal Builder, Meal Logging Coach, Meal Assembly Guide, targets, hydration, and body-composition reasoning stay available without crowding the first nutrition screen.
- An `AI Setup & Review Readiness` card with `AiSetupStatus`, `AiSetupStatusCard`, and `AiSettings.isConfigured()` so the app shows API key, base URL, model, photo context, missing setup, and AI review ready state before the user reaches daily review.
- A `Today` Command Center with readiness, next action, training, nutrition, recovery, and body-composition cards so beginners know what to do first while advanced users can scan the key numbers.
- A `Daily Coach Checklist` on `Today` that tracks whether the plan is prepared, training is executed, food is logged, metrics are synced, and the AI review is locked for the day.
- Local daily log history with a 7-day trend summary for body weight, calories, protein, sleep, steps, hard sets, and training volume.
- Saved AI review history so the latest coaching guidance remains visible after the app closes.
- Athlete Profile setup for long-term goal, current phase, training experience, target weight/body fat, weekly training days, equipment, weak points, diet preference, and constraints. This profile is included in every daily AI review.
- Coach modes for new plans, check-ins, training visual analysis, food visual analysis, linked training + nutrition analysis, existing plan audit, and evidence explanations.
- Configurable API key, base URL, and model from the app UI.
- OpenAI Responses API request shape with text and photos.
- Photo picker support for exercise frames, equipment photos, food photos, labels, and menus.
- Photo Evidence tracking for classified photos: `Food or label photo`, `Exercise form frame`, `Equipment photo`, `Physique progress photo`, `Menu or nutrition label`, and other check-in photos. The app saves the photo purpose, file name, note, MIME type, and timestamp in the daily log while sending the recent image attachments to AI with the same context.
- Plan Templates for `Beginner Full Body`, `4-Day Hypertrophy`, and `5-Day Physique Priority`, giving a ready-to-train weekly structure before manual editing.
- A weekly training plan workflow for phase goals, training days, planned exercises, sets, reps, RIR, rest time, and notes.
- An `Apply today` workflow that turns a planned training day into today's set-level executable workout log.
- A `Daily Execution Plan` on `Today` that turns training execution, nutrition gaps, recovery state, data completeness, and AI review timing into one priority focus, primary action, training decision, nutrition decision, recovery decision, data quality gate, AI review gate, and plan adjustment signal.
- An `AI Review Action Queue` on `Today` and `AI Coach` that turns the latest saved review into tappable training action, nutrition action, recovery action, tracking action, and plan action items. It shows sourceLabel, confidenceLabel, primaryAction, actionLabel, evidence cue, and route so saved AI guidance becomes follow-through instead of a loose note.
- A daily training workflow for planned focus, completed sessions, exercises, set-level load, reps, RIR, rest time, hard sets, target muscle, form/pain notes, and Exercise History comparison against previous logged sessions.
- Training photo shortcuts for form frames and equipment photos, so uncertain setup, technique, pain flags, target-muscle stimulus, and visual guide ID mapping can be attached to the same daily review.
- A `Training Readiness Builder` on the training screen that turns Recovery Guidance into a warm-up strategy, ramp-up cue, first working set decision, volume adjustment, and stop rule before the user starts logging sets.
- A `Warm-up Ramp Plan` between readiness and set execution that converts the next exercise, planned load, target reps, RIR, recovery status, and visual guide ID into an exact ramp set checklist with planned load percentages, final ramp set quality, first working set gate, and stop rule.
- A `Next Set Coach` on the training screen that finds the first unfinished working set and shows the current exercise, next set target, load cue, reps cue, RIR cue, rest cue, stop cue, after-set logging cue, and the same equipment/action instance diagram used by the Exercise Visual Library.
- A `Tomorrow Coach Brief` on Today that turns the saved plan, body-composition trend, nutrition status, session quality, and recovery signals into next-day training focus, calorie/protein targets, readiness gate, recovery action, tracking action, and the first action to take tomorrow.
- A `Weekly Check-in` on Today that uses 7-14 day logs for days logged, training completion, average calories/protein, weight change, recovery average, weak-point focus, data quality gate, plan adjustment, nutrition adjustment, and next-week action before AI changes plan-wide volume or calorie targets.
- A `Session Quality Dashboard` on the training screen that summarizes completion rate, logged set rate, average RIR, completed volume, muscle-volume distribution, pain flags, technique flags, quality cue, capacity cue, and risk cue before the AI changes the plan.
- A `Training Closeout Coach` after Session Quality Dashboard that checks completed sets, missing set logs, pain/technique flags, form/equipment photo evidence, post-workout nutrition cue, metrics sync cue, session notes, closeout score, primary action, and AI review readiness before the user asks AI to change tomorrow's plan.
- Exercise History on active exercise cards for previous date, previous/current volume, best load, best reps, completed sets, average RIR, and a short history-based guidance cue.
- Progression Cue on active exercise cards so the user can see whether to add reps, add load, hold, modify, or finish baseline work before waiting for the AI review.
- Exercise Substitution Coach on active exercise cards so the user can keep training when equipment is occupied, unavailable, painful, or technically unstable. It recommends a primaryOption and secondaryOptions that preserve same target muscle, same movement pattern, planned rep range, planned RIR, fatigue cost, keepIntentCue, loadAdjustmentCue, and visual guide ID continuity before the AI changes the plan.
- A unified exercise visual guide on planned and active workout cards, using a `Unified Exercise Visual Atlas` with stable visual guide IDs, simple equipment/action diagrams, Chinese equipment labels, unified instance diagram, three-step recognition, quick visual cue, find-equipment cue, movement path cue, action path cue, beginner recognition cue, equipment markers, instance diagram cues, example movements, common movement examples, and look-for cues for Smith machine, cable station, dumbbells, barbell, machine, adjustable bench, pull-up/dip station, resistance band, leg press or hack squat, and bodyweight/open-station movements so non-pro users can recognize what the exercise name refers to. The preview now reserves an exercise image slot for licensed/open exercise photos while the atlas remains the fallback visual language when a local asset is not available.
- An `Exercise Visual Legend / 统一动作图例` inside Exercise Visual Library, showing compact unified instance diagrams for VG-01 to VG-10 so beginners can memorize the simple icon, Chinese equipment label, real equipment markers, and movement path before reading detailed exercise names.
- A `Today Visual Primer` on `Today` that shows the current workout's unified simple equipment/action instance diagrams, visual-map thumbnails, visual guide IDs, Chinese equipment labels, and beginner recognition cues before the user walks the gym floor.
- `Selected Day Visual Map` in `Plan` and `Today's Exercise Visual Map` in `Training`, plus thumbnail headers on planned and active exercise cards, so the user can scan the day's equipment/action setup before searching the gym floor.
- A live equipment/action preview inside `Add Planned Exercise` and `Add Exercise`, so typing a movement name immediately maps it to the likely visual guide ID and visual category before the user saves it.
- An `Exercise Visual Library` on the plan screen that shows these visual categories together before the user adds a movement, plus a compact atlas overview that teaches the same three-step recognition flow: match the simplified instance diagram, find the real equipment markers, and follow the intended movement path.
- A workout execution flow where tapping a set as complete starts a rest countdown for the next set.
- A daily nutrition workflow for calorie and macro targets, a one-tap Meal Flow Coach, Nutrition Pacing, meal logging, meal photo support, and food-photo estimate follow-up.
- Meal photos are logged as Photo Evidence so food, label, menu, oil, sauce, portion uncertainty, and bowl-depth notes stay linked to the nutrition review.
- Meal Templates for quick-add nutrition logging: `Lean Protein Bowl`, `Pre-Workout Carbs`, `Low-Fat Protein Fix`, `Salmon Potato Plate`, and `Fiber + Micronutrient Add`.
- Nutrition Pacing shows calorie/protein/carb/fat/fiber remaining or over target, a macro adherence score, and a next-meal focus so the user knows what to eat next instead of only seeing a retrospective food log.
- Next Meal Builder turns remaining macros and training demand into the next practical meal target, including protein/carbs/fat/fiber grams, meal timing cue, portion uncertainty cue, and photo/label cue.
- Meal Logging Coach links Nutrition Pacing, Next Meal Builder, recommended meal templates, food/menu/label photo capture, coach prefill, and AI review so the user can quickly prefill a target meal, edit it, add photo evidence, or jump to the AI nutrition review.
- Meal Assembly Guide turns the next-meal macro target into a practical plate structure, protein anchor, carb anchor, fat control, fiber/micros, shopping cue, prep cue, photo/logging cue, and avoid cue so a beginner knows what food to choose and what to photograph for AI estimation.
- Body Composition Guidance compares phase goal, body-weight trend, average calories, average protein, and training output before suggesting whether to hold targets or make a small calorie adjustment.
- Conditioning & Hydration tracks step goal progress, cardio minutes/type/intensity, water liters, sodium, caffeine, alcohol, digestion, and notes so AI can separate true calorie or training needs from NEAT, cardio, water/sodium, stimulant, digestion, or scale-weight noise.
- Physique Measurement Summary tracks bodybuilding-relevant tape measurements including waist, chest, shoulder, hip, left/right arms, left/right thighs, and neck, then summarizes shoulder-to-waist ratio, arm symmetry, thigh symmetry, waist control, and measurement quality before AI changes calories or weak-point training.
- Recovery Guidance compares sleep, fatigue, soreness, stress, resting heart rate, and planned/completed set pressure before suggesting whether to keep the plan, hold training stress, reduce volume, or run a deload check.
- A daily metrics workflow for body weight, body fat, lean mass, waist, chest, shoulder, hip, left/right arms, left/right thighs, neck, sleep, steps, hunger, fatigue, soreness, stress, and reflection.
- A physique photo check-in workflow for progress photos under consistent lighting, distance, posture, and pump state, linked with Physique Measurement Summary before AI changes calories or weak-point training.
- Health Connect sync for user-authorized body weight, body fat, lean body mass, steps, sleep, resting heart rate, and total calories burned. Xiaomi, Huawei, scale, watch, and phone data can be imported when the source app writes those records into Health Connect.
- An AI review workflow that summarizes the day and asks the model what to adjust tomorrow, then feeds the saved text back into AI Review Action Queue for follow-through.
- AI review includes recent trend rows so recommendations can compare today's execution against recent training, nutrition, recovery, and body-composition direction.
- AI review output is saved locally and shown in `Today` as the latest guidance, with recent reviews available in `AI Coach`.
- An AI Data Map that makes the connected analysis surface explicit: exercise selection, set load, actual reps, RIR, rest time, hard sets, tonnage, Daily Execution Plan, priority focus, primary action, data quality gate, AI review gate, plan adjustment signal, AI Review Action Queue, sourceLabel, confidenceLabel, primaryAction, actionLabel, training action, nutrition action, recovery action, tracking action, plan action, Training Readiness Builder, Warm-up Ramp Plan, ramp set checklist, planned load percentage, final ramp set quality, first working set gate, Next Set Coach, current exercise, next set target, load cue, reps cue, RIR cue, rest cue, stop cue, after-set logging cue, Tomorrow Coach Brief, Weekly Check-in, training completion, recovery average, weak-point focus, next-week action, tomorrow training focus, tomorrow nutrition target, tomorrow recovery gate, tomorrow tracking action, readiness gate, Session Quality Dashboard, Training Closeout Coach, closeout score, missing set logs, photo evidence cue, post-workout nutrition cue, metrics sync cue, AI review readiness, completion rate, logged set rate, average RIR, muscle-volume distribution, pain flags, technique flags, warm-up cue, ramp-up cue, first working set, volume adjustment, stop rule, Exercise History, Progression Cue, Exercise Substitution Coach, equipment unavailable, trigger reason, primaryOption, secondaryOptions, same target muscle, same movement pattern, preserve rep range, fatigue cost, keepIntentCue, loadAdjustmentCue, exercise visual guide, Unified Exercise Visual Atlas, visual guide ID, equipment/action diagrams, Exercise Visual Legend, 统一动作图例, three-step recognition, simplified instance diagram, find real equipment, movement path matching, selected-day visual map, today's exercise visual map, exercise thumbnail headers, live equipment/action preview, Chinese equipment labels, unified instance diagram, quick visual cue, find-equipment cue, movement path cue, action path cue, beginner recognition cue, equipment markers, instance diagram cue, common movement examples, look-for cue, equipment photo recognition, Photo Evidence, classified photos, food, form, equipment, label, or physique progress, technique notes, meal macros, Nutrition Pacing, Conditioning & Hydration, step goal, cardio minutes, cardio type, cardio intensity, water liters, sodium mg, caffeine mg, alcohol servings, digestion notes, scale-weight noise, Next Meal Builder, Meal Assembly Guide, plate structure, protein anchor, carb anchor, fat control, fiber/micros, shopping cue, prep cue, photo/logging cue, next meal macro targets, meal timing cue, portion uncertainty cue, Body Composition Guidance, Physique Measurement Summary, waistCm, chestCm, shoulderCm, hipCm, leftArmCm, rightArmCm, leftThighCm, rightThighCm, neckCm, shoulder-to-waist ratio, arm symmetry, thigh symmetry, Recovery Guidance, food photos, equipment/form photos, body weight, body fat, lean mass, waist, sleep, steps, resting heart rate, calorie burn, hunger, fatigue, soreness, and stress.

## Daily workflow

Use the app as a daily training and daily nutrition cockpit:

1. Set `Athlete Profile` in `Plan` so the app knows your phase, target, training schedule, equipment, weak points, and constraints.
2. Open `Today` to see the current training, nutrition, metrics, and readiness summary.
   Use `Today Flow Coach` first: it shows the next best action, daily loop progress, readiness, sets, nutrition gap, AI review gate, and one primary button.
   Expand daily detail layers only when you want the full Start Here checklist, AI setup state, command center, execution plan, action queue, tomorrow brief, weekly check-in, visual primer, snapshot, trend, recovery, and body-composition reasoning.
   The AI Setup & Review Readiness card checks whether API key, base URL, model, and photo context are ready; if setup is missing, the daily loop sends the user to AI Coach settings instead of producing a late API error.
   The Command Center stays below it for the deeper decision layer and latest guidance.
   The Daily Execution Plan turns the current log into the single highest-priority action and explains whether training, food, recovery, or data quality should be handled before trusting the AI review.
   AI Review Action Queue then turns the latest saved review into tappable next steps for Training, Nutrition, Metrics, Plan, or AI Coach.
   Weekly Check-in uses the recent 7-14 day window to decide whether next week should hold, simplify, reduce optional volume, adjust nutrition, or wait for better data before changing bigger targets.
   The Daily Coach Checklist turns that loop into tappable steps: `Plan prepared`, `Training executed`, `Food logged`, `Metrics synced`, and `AI review locked`.
   The 7-day trend card shows whether body weight, food intake, hard sets, sleep, and activity are moving in the right direction.
3. Use `Plan` to pick a Plan Template or build the weekly training plan manually, select a day, add planned exercises, and tap `Apply today`.
4. Use `Training` by following the one-tap `Workout Flow Coach` first: it shows the current exercise, target set, planned load/reps/RIR, and a single primary action to complete the set and start rest. Open `Professional detail layers` only when you want the reasoning behind the recommendation: Training Readiness Builder, Warm-up Ramp Plan, Next Set Coach, Session Quality Dashboard, and Training Closeout Coach. First scan `Today Visual Primer` or Today's Exercise Visual Map so each exercise name maps to a real machine, station, free weight, or bodyweight setup. Warm-up Ramp Plan then turns the next exercise into exact ramp sets before the first working set, including planned load percentages, final ramp set quality, first working set gate, and stop rule. Next Set Coach shows the current exercise, next set target, load/reps/RIR/rest cues, stop cue, after-set logging cue, and matching equipment/action instance diagram before tapping `Complete`. Exercise History compares repeated movements against the previous logged session, while the exercise visual guide, visual map, thumbnail headers, live equipment/action preview, Unified Exercise Visual Atlas, and Exercise Visual Library help identify the equipment/action through visual guide IDs, unified instance diagrams, three-step recognition, Chinese labels, quick visual cues, find-equipment cues, movement path cues, action path cues, beginner recognition cues, equipment markers, common movements, example movements, and look-for cues. Progression Cue tells whether to add reps, add load, hold, modify, or finish baseline work. Exercise Substitution Coach tells how to keep the same target muscle and same movement pattern when the planned station is unavailable, crowded, painful, or unstable; it preserves rep range, RIR intent, fatigue cost, and visual guide ID continuity before the AI review decides whether the plan should change. Tap `Complete` after a set to start the rest timer.
5. Use `Nutrition` by following the one-tap `Meal Flow Coach` first: it shows the next meal target, protein/calorie gap, recommended template, food-photo path, AI gate, and a single `Prefill next meal` primary action. Open `Nutrition detail layers` only when you want the reasoning behind the recommendation: Nutrition Pacing, Next Meal Builder, Meal Logging Coach, Meal Assembly Guide, Nutrition Targets, Body Composition Guidance, Conditioning & Hydration, and Meal Templates. Then log meals manually, attach a meal photo when you want food-photo analysis, and use hydration/cardio/digestion notes so AI can separate real nutrition needs from water, sodium, caffeine, NEAT, and scale-weight noise.
6. Use `Metrics` for body weight, body fat, lean mass, waist, chest, shoulder, hip, left/right arms, left/right thighs, neck, sleep, steps, resting heart rate, calorie burn, hunger, fatigue, soreness, stress, Conditioning & Hydration, and daily reflection. Physique Measurement Summary turns tape measurements into proportion and symmetry cues, while Recovery Guidance and Conditioning & Hydration turn recovery, NEAT, cardio, water/sodium, stimulant, and digestion signals into conservative training-pressure guidance and nutrition guidance. Tap `Connect health data` and `Sync today` to import supported Health Connect records.
7. Use `AI Coach` or the `Run AI review` button to send the athlete profile, weekly training plan, day's log, set-level performance, photos, and your extra question to the model for adjustment guidance.

## Health data sync

The first health-data layer is Android Health Connect. The app requests read-only access to:

- Body weight
- Body fat
- Lean body mass
- Steps
- Sleep sessions
- Resting heart rate
- Total calories burned

This can support Xiaomi, Huawei, smart-scale, watch, and phone health data when those vendor apps sync or expose records through Health Connect and the user grants permission. The app does not bypass vendor privacy controls or read private manufacturer databases directly.

Current implementation status:

- Implemented now: Health Connect read-only sync for compatible records, including data that Xiaomi/Mi Fitness, Huawei, smart-scale, watch, or phone apps write into Health Connect.
- Not guaranteed by the app alone: a vendor app must actually export the metric to Health Connect, and the user must approve the permission.
- Future provider module: Huawei Health Kit can be added later for deeper Huawei ecosystem syncing when Health Connect does not expose enough data.
- Fallback: manual entry remains available when Health Connect is unavailable, permissions are denied, or a vendor app does not export the needed records.

## Build

Use JDK 17 with a normal Android SDK installation that includes Android API 36. The Gradle wrapper is included so the project builds with the tested Gradle version.

1. Open `android-app/` in Android Studio.
2. Let Android Studio sync Gradle.
3. Build and run the `app` configuration on an emulator or phone.

Command-line build:

```bash
cd android-app
./gradlew :app:assembleDebug
```

## API setup

Open the app and fill in:

- `API key`: your provider key. It is stored locally in Android SharedPreferences and is not committed to source.
- `base URL`: default is `https://api.openai.com/v1`.
- `model`: default is `gpt-4.1-mini`; you can change this to any model your API provider supports.

The network client posts to:

```text
{base URL}/responses
```

It sends:

- `instructions`: the bundled skill prompt and selected references.
- `input_text`: your profile, log, or question.
- `input_image`: optional photos encoded as data URLs.

## Skill assets

The app reads the bundled skill assets from:

```text
app/src/main/assets/skill/
```

When you update the upstream skill, copy the updated files into that assets folder before rebuilding the app.

## Safety

This app is coaching support, not medical care. Photo/video form checks and food-photo nutrition estimates are approximate. The app should not be used for diagnosis, physical therapy, clinical dietetics, drug protocols, or official contest rules.
