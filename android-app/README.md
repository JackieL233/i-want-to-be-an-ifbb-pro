# I Want to be an IFBB PRO Android App

This is a native Android companion app for the `I Want to be an IFBB PRO` skill. It packages the skill assets inside the APK and lets you call an AI API for training, nutrition, visual form analysis, food-photo nutrition estimates, and linked check-ins.

## What is included

- Kotlin + Jetpack Compose Android app.
- Bundled skill assets in `app/src/main/assets/skill/`.
- Coach modes for new plans, check-ins, training visual analysis, food visual analysis, linked training + nutrition analysis, existing plan audit, and evidence explanations.
- Configurable API key, base URL, and model from the app UI.
- OpenAI Responses API request shape with text and photos.
- Photo picker support for exercise frames, equipment photos, food photos, labels, and menus.
- A daily training workflow for planned focus, completed sessions, exercises, set-level load, reps, RIR, rest time, hard sets, target muscle, and form/pain notes.
- A workout execution flow where tapping a set as complete starts a rest countdown for the next set.
- A daily nutrition workflow for calorie and macro targets, meal logging, meal photo support, and food-photo estimate follow-up.
- A daily metrics workflow for body weight, waist, sleep, steps, hunger, fatigue, soreness, stress, and reflection.
- An AI review workflow that summarizes the day and asks the model what to adjust tomorrow.
- An AI Data Map that makes the connected analysis surface explicit: exercise selection, set load, actual reps, RIR, rest time, hard sets, tonnage, technique notes, pain flags, meal macros, food photos, equipment/form photos, body weight, waist, sleep, steps, hunger, fatigue, soreness, and stress.

## Daily workflow

Use the app as a daily training and daily nutrition cockpit:

1. Open `Today` to see the current training, nutrition, metrics, and readiness summary.
2. Use `Training` to log the session plan, exercises, set-by-set weight, reps, RIR, rest time, hard sets, target muscle, and pain/form notes. Tap `Complete` after a set to start the rest timer.
3. Use `Nutrition` to set calorie/macronutrient targets, log meals, and attach a meal photo when you want food-photo analysis.
4. Use `Metrics` for body weight, waist, sleep, steps, hunger, fatigue, soreness, stress, and daily reflection.
5. Use `AI Coach` or the `Run AI review` button to send the day's log, set-level performance, photos, and your extra question to the model for adjustment guidance.

## Build

Use Android Studio with JDK 17 and a normal Android SDK installation.

1. Open `android-app/` in Android Studio.
2. Let Android Studio sync Gradle.
3. Build and run the `app` configuration on an emulator or phone.

This workspace currently does not include a local JDK/Android SDK, so APK compilation is expected to happen in Android Studio or another Android build machine.

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
