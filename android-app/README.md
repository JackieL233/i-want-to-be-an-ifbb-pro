# I Want to be an IFBB PRO Android App

This is a native Android companion app for the `I Want to be an IFBB PRO` skill. It packages the skill assets inside the APK and lets you call an AI API for training, nutrition, visual form analysis, food-photo nutrition estimates, and linked check-ins.

## What is included

- Kotlin + Jetpack Compose Android app.
- Bundled skill assets in `app/src/main/assets/skill/`.
- Apple-inspired light UI theme with a quiet neutral background, blue action color, rounded grouped surfaces, and bottom navigation for the main daily tabs.
- A `Today` Command Center with readiness, next action, training, nutrition, recovery, and body-composition cards so beginners know what to do first while advanced users can scan the key numbers.
- Local daily log history with a 7-day trend summary for body weight, calories, protein, sleep, steps, hard sets, and training volume.
- Saved AI review history so the latest coaching guidance remains visible after the app closes.
- Athlete Profile setup for long-term goal, current phase, training experience, target weight/body fat, weekly training days, equipment, weak points, diet preference, and constraints. This profile is included in every daily AI review.
- Coach modes for new plans, check-ins, training visual analysis, food visual analysis, linked training + nutrition analysis, existing plan audit, and evidence explanations.
- Configurable API key, base URL, and model from the app UI.
- OpenAI Responses API request shape with text and photos.
- Photo picker support for exercise frames, equipment photos, food photos, labels, and menus.
- A weekly training plan workflow for phase goals, training days, planned exercises, sets, reps, RIR, rest time, and notes.
- An `Apply today` workflow that turns a planned training day into today's set-level executable workout log.
- A daily training workflow for planned focus, completed sessions, exercises, set-level load, reps, RIR, rest time, hard sets, target muscle, and form/pain notes.
- A unified exercise visual guide on planned and active workout cards, using simple equipment/action diagrams for Smith machine, cable station, dumbbells, barbell, machine, and bodyweight/open-station movements so non-pro users can recognize what the exercise name refers to.
- A workout execution flow where tapping a set as complete starts a rest countdown for the next set.
- A daily nutrition workflow for calorie and macro targets, Nutrition Pacing, meal logging, meal photo support, and food-photo estimate follow-up.
- Nutrition Pacing shows calorie/protein/carb/fat/fiber remaining or over target, a macro adherence score, and a next-meal focus so the user knows what to eat next instead of only seeing a retrospective food log.
- A daily metrics workflow for body weight, waist, sleep, steps, hunger, fatigue, soreness, stress, and reflection.
- Health Connect sync for user-authorized body weight, body fat, lean body mass, steps, sleep, resting heart rate, and total calories burned. Xiaomi, Huawei, scale, watch, and phone data can be imported when the source app writes those records into Health Connect.
- An AI review workflow that summarizes the day and asks the model what to adjust tomorrow.
- AI review includes recent trend rows so recommendations can compare today's execution against recent training, nutrition, recovery, and body-composition direction.
- AI review output is saved locally and shown in `Today` as the latest guidance, with recent reviews available in `AI Coach`.
- An AI Data Map that makes the connected analysis surface explicit: exercise selection, set load, actual reps, RIR, rest time, hard sets, tonnage, technique notes, pain flags, meal macros, Nutrition Pacing, food photos, equipment/form photos, body weight, body fat, lean mass, waist, sleep, steps, resting heart rate, calorie burn, hunger, fatigue, soreness, and stress.

## Daily workflow

Use the app as a daily training and daily nutrition cockpit:

1. Set `Athlete Profile` in `Plan` so the app knows your phase, target, training schedule, equipment, weak points, and constraints.
2. Open `Today` to see the current training, nutrition, metrics, and readiness summary.
   The Command Center shows the next best action and a beginner-friendly daily loop: plan, train, log food, sync health data, then run AI review.
   The 7-day trend card shows whether body weight, food intake, hard sets, sleep, and activity are moving in the right direction.
3. Use `Plan` to build the weekly training plan, select a day, add planned exercises, and tap `Apply today`.
4. Use `Training` to log the applied session, set-by-set weight, reps, RIR, rest time, hard sets, target muscle, and pain/form notes. The exercise visual guide helps identify the equipment/action before logging. Tap `Complete` after a set to start the rest timer.
5. Use `Nutrition` to set calorie/macronutrient targets, follow Nutrition Pacing for remaining calories/macros and next-meal focus, log meals, and attach a meal photo when you want food-photo analysis.
6. Use `Metrics` for body weight, body fat, lean mass, waist, sleep, steps, resting heart rate, calorie burn, hunger, fatigue, soreness, stress, and daily reflection. Tap `Connect health data` and `Sync today` to import supported Health Connect records.
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
