# I Want to be an IFBB PRO Skill

[中文说明](README.zh-CN.md)

An open-source AI skill for professional-level personalized physique coaching. The name "I Want to be an IFBB PRO" is an aspirational image: it signals elite bodybuilding-style rigor, not a promise or default plan to earn an actual pro card. The skill supports training plans, existing-plan audits, improvement seasons, fat-loss phases, recomposition, contest-prep-inspired conditioning, posing, nutrition, recovery, injury-risk controls, workout logs, multimodal photo/video analysis, and data-driven check-ins.

This project is not affiliated with, endorsed by, or an official resource of the IFBB Pro League, NPC, or NPC Worldwide. Always verify current qualification paths, divisions, rules, event details, and contest availability with official organizers when real competition planning is requested.

The canonical skill lives in:

`skills/i-want-to-be-an-ifbb-pro/`

## What It Does

- Builds individualized bodybuilding and fitness plans from goals, body data, equipment, schedule, training age, aesthetic target, and constraints.
- Audits and optimizes existing plans instead of blindly replacing them.
- Supports hypertrophy, improvement season, fat loss, recomposition, maintenance, deloads, contest prep, and return-to-training.
- Helps map a long-term IFBB PRO-inspired physique aspiration into seasons, weak-point priorities, readiness criteria, and official-rule checkpoints when competition is relevant.
- Creates periodized training plans with exercises, sets, reps, RIR/RPE, progression, deloads, cardio, and substitutions.
- Includes app-ready Plan Templates so a user can start from Beginner Full Body, 4-Day Hypertrophy, or 5-Day Physique Priority before editing exercises, sets, RIR, rest, and weak-point focus.
- Adds a bilingual Android UI shell with saved EN/中文 switching and a first-screen `Today Flow Coach` so users see one next action before entering advanced training, nutrition, metrics, and AI review modules.
- Keeps the older `Start Here` loop as part of expandable daily detail layers, preserving the full plan -> train -> eat -> sync -> review checklist without making it the first thing a beginner has to parse.
- Adds a one-tap `Plan Flow Coach` for the plan screen, with `Use recommended template` or `Apply today` as the primary path and plan detail layers collapsed underneath for athlete profile, templates, weekly plan editing, selected-day visual map, and exercise editing.
- Adds a one-tap `Workout Flow Coach` for the training screen, with `Complete set + start rest` as the primary path and `Professional detail layers` collapsed underneath for readiness, ramp planning, next-set logic, quality, and closeout.
- Adds a one-tap `Meal Flow Coach` for the nutrition screen, with `Prefill next meal` as the primary path and nutrition detail layers collapsed underneath for macro pacing, plate assembly, targets, hydration, and body-composition reasoning.
- Adds a one-tap `Metrics Flow Coach` for the body-data screen, with health sync, minimum body check-in, progress photo, and recovery evidence summarized before metrics detail layers.
- Adds a one-tap `AI Review Flow Coach` for the AI Coach screen, with `Run daily review` as the primary path and AI detail layers collapsed underneath for API setup, photos, prompts, data map, action queue, and saved review history.
- Adds an `AI Setup & Review Readiness` state so the Android app checks API key, base URL, model, and photo context before daily review instead of letting the daily flow end in a late API setup error.
- Tracks each workout at exercise/set level, including hard sets, tonnage, RIR/RPE, pain, technique quality, and target-muscle stimulus.
- Turns readiness and the first unfinished exercise into a `Warm-up Ramp Plan`, with ramp set checklist, planned load percentages, final ramp set quality, first working set gate, and stop rule before the user enters the first working set.
- Adds an app-ready `Training Closeout Coach` after the session quality review, checking completed sets, missing set logs, pain/technique flags, form/equipment photo evidence, post-workout nutrition cue, metrics sync cue, closeout score, and AI review readiness before tomorrow's plan is changed.
- Turns saved AI review text into an app-ready `AI Review Action Queue` with sourceLabel, confidenceLabel, primaryAction, actionLabel, evidence cues, training action, nutrition action, recovery action, tracking action, and plan action routes.
- Keeps training moving when equipment is occupied, unavailable, painful, or technically unstable through an app-ready Exercise Substitution Coach that recommends same-target, same-pattern alternatives while preserving rep range, RIR intent, fatigue cost, and visual guide ID continuity.
- Provides beginner-friendly equipment/action context for exercise names through a `Unified Exercise Visual Atlas`: stable VG-01 to VG-10 visual guide IDs, Chinese equipment labels, simple unified instance diagrams, three-step recognition, quick visual cues, find-equipment cues, movement path cues, action path cues, beginner recognition cues, equipment markers, example movements, common movement examples, and look-for cues for Smith machine, cable station, dumbbells, barbell, machine, adjustable bench, pull-up/dip station, resistance band, leg press or hack squat, and bodyweight/open-station work.
- Adds an `Exercise Visual Legend / 统一动作图例` in the app's Exercise Visual Library so a beginner can scan compact unified instance diagrams for VG-01 to VG-10 before reading unfamiliar exercise names.
- Shows app-ready visual maps and thumbnail headers for planned and active workouts, so a non-pro user can scan the day and immediately know which machine, station, free weight, or movement setup each exercise name points to.
- Uses multimodal inputs for equipment identification, exercise form analysis, food photo nutrition estimates, portion-size uncertainty, and linked training/nutrition check-ins.
- Guides calories/macros, meal timing, hydration, supplements, and adjustment rules within conservative safety boundaries.
- Includes app-ready Meal Templates, Next Meal Builder, Meal Logging Coach, and Meal Assembly Guide for fast high-protein logging, practical next-meal macro targets, coach prefill, recommended template selection, plate structure, shopping/prep cues, meal timing cues, AI review linkage, and portion/photo uncertainty handling.
- Tracks physique measurements such as waist, chest, shoulder, hip, left/right arms, left/right thighs, and neck so AI can compare body-weight changes against bodybuilding proportion, shoulder-to-waist direction, symmetry, weak-point response, and waist control.
- Covers posing practice, stage conditioning, show logistics, peak-week caution, and post-show recovery.
- Uses safety screening and referral boundaries instead of pretending to be a doctor.

## Repository Structure

```text
skills/i-want-to-be-an-ifbb-pro/
  SKILL.md
  agents/openai.yaml
  references/
    safety-screening.md
    intake-assessment.md
    anatomy-and-movement.md
    goal-decision-system.md
    evidence-map.md
    pro-level-physique-roadmap.md
    plan-optimization.md
    training-programming.md
    exercise-library.md
    phase-templates.md
    session-execution-and-volume.md
    visual-analysis-and-food-estimation.md
    contest-prep-and-posing.md
    nutrition-body-composition.md
    recovery-injury-risk.md
    data-tracking-adjustment.md
    adaptation-playbook.md
    model-adaptation.md
    sources.md
  assets/templates/
    intake-form.md
    check-in-form.md
    plan-template.md
    session-log.csv
    tracking-log.csv
  scripts/estimate_targets.py
  scripts/analyze_checkin.py
  scripts/analyze_training_volume.py
```

## Install for Codex

Copy or symlink the skill folder into your Codex skills directory:

```bash
cp -R skills/i-want-to-be-an-ifbb-pro ~/.codex/skills/
```

Then invoke it with:

```text
Use $i-want-to-be-an-ifbb-pro to create a long-term bodybuilding roadmap toward an IFBB PRO-inspired physique goal.
```

## Use With Other Models

Use `SKILL.md` as the main system/developer instruction. Keep the `references/` files available for retrieval, tool loading, or manual context injection when the model needs detail.

Recommended runtime behavior:

1. Load `SKILL.md` first.
2. Load only the relevant reference files for the user's request.
3. Keep the plan grounded in user data, official-rule verification, and safety boundaries.
4. Iterate from tracked trends rather than one-off measurements.
5. When images are available, link visual training evidence and food-photo nutrition estimates before changing the plan.

## Android App

A native Android companion app lives in [`android-app/`](android-app/). It packages the skill into Android assets and provides daily training, daily nutrition, metrics, photo analysis, and AI review workflows with a configurable AI API client.

The app UI is moving toward a simple Apple-inspired daily cockpit: a quiet light theme, bottom navigation, saved EN/中文 language switching, a first-screen `Today Flow Coach`, expandable daily detail layers, a one-tap `Plan Flow Coach`, collapsed plan detail layers, a one-tap `Workout Flow Coach`, collapsed `Professional detail layers`, a one-tap `Meal Flow Coach`, collapsed nutrition detail layers, a one-tap `Metrics Flow Coach`, collapsed metrics detail layers, a one-tap `AI Review Flow Coach`, collapsed AI detail layers, a `Today` Command Center, Daily Execution Plan, Daily Coach Checklist, readiness score, next action guidance, and beginner-friendly steps that still expose professional set-level training, nutrition, health, and AI review data.

The app also saves local daily log history and shows a 7-day trend card. AI review now receives recent trend rows, so it can compare today's training, calories, protein, body weight, sleep, steps, fatigue, and hard sets against recent direction instead of reacting to one isolated day.

AI review output is saved locally as recent coaching guidance. The latest review appears on `Today`, and recent reviews remain available in `AI Coach` so the app can act more like a persistent daily coach instead of a one-off chat response.

The app now supports Athlete Profile setup, Plan Templates, weekly training plans, set-level workout execution, Daily Execution Plan, AI Review Action Queue, Training Readiness Builder, Warm-up Ramp Plan, Next Set Coach, Tomorrow Coach Brief, Weekly Check-in, Session Quality Dashboard, Training Closeout Coach, Exercise History, Progression Cue, Exercise Substitution Coach, Meal Templates, Nutrition Pacing, Next Meal Builder, Meal Logging Coach, Meal Assembly Guide, Body Composition Guidance, Physique Measurement Summary, Recovery Guidance, a unified exercise visual guide, `Unified Exercise Visual Atlas`, `Exercise Visual Legend / 统一动作图例`, Selected Day Visual Map, Today's Exercise Visual Map, Daily Coach Checklist, and Health Connect metric sync: define the long-term physique goal, phase, training experience, target weight/body fat, weekly training days, equipment, weak points, and constraints; start from Beginner Full Body, 4-Day Hypertrophy, or 5-Day Physique Priority when you want a ready-to-train structure; build or edit a weekly plan; apply a planned day to today's workout; use Today to check the Daily Execution Plan's priority focus, primary action, training decision, nutrition decision, recovery decision, data quality gate, AI review gate, and plan adjustment signal; use AI Review Action Queue to turn saved guidance into sourceLabel, confidenceLabel, primaryAction, actionLabel, training action, nutrition action, recovery action, tracking action, and plan action routes; use Tomorrow Coach Brief to lock the next-day plan day, training focus, calorie/protein target, readiness gate, recovery action, tracking action, and first action tomorrow; use Weekly Check-in to compare 7-14 day training completion, average calories/protein, weight trend, recovery average, weak-point focus, data quality, and next-week action before changing plan-wide volume or calorie targets; then follow `Plan prepared`, `Training executed`, `Food logged`, `Metrics synced`, and `AI review locked`; recognize the intended equipment/action from visual guide IDs, Chinese equipment labels, unified instance diagrams, Exercise Visual Legend, three-step recognition, quick visual cues, find-equipment cues, movement path cues, action path cues, beginner recognition cues, equipment markers, instance diagram cues, example movements, common movement examples, and look-for cues; use the live equipment/action preview while adding a movement; scan the selected plan day or today's workout with visual-map thumbnails and action-card thumbnail headers before looking for equipment in the gym; follow Training Readiness Builder for warm-up strategy, ramp-up cue, first working set decision, volume adjustment, and stop rule; follow Warm-up Ramp Plan for the ramp set checklist, planned load percentages, final ramp set quality, first working set gate, and stop rule before the first working set; use Next Set Coach to see the current exercise, next set target, load cue, reps cue, RIR cue, rest cue, stop cue, after-set logging cue, and matching equipment/action instance diagram before completing a set; use Session Quality Dashboard for completion rate, logged set rate, average RIR, muscle-volume distribution, pain flags, technique flags, quality cue, capacity cue, and risk cue; use Training Closeout Coach for closeout score, completed sets, missing set logs, pain/technique flags, form/equipment photo evidence, post-workout nutrition cue, metrics sync cue, session notes, primary action, and AI review readiness; record each set's weight, reps, RIR, completion state, rest time, and notes; compare repeated exercises against the previous logged session for volume, best load, best reps, completed sets, and average RIR; get an immediate cue on whether to add reps, add load, hold, modify, or finish baseline work; use Exercise Substitution Coach when equipment is occupied, unavailable, painful, or unstable to choose a primaryOption and secondaryOptions that keep the same target muscle, same movement pattern, preserve rep range, planned RIR, fatigue cost, keepIntentCue, loadAdjustmentCue, and visual guide ID continuity; tap a completed set to start a rest countdown; follow remaining calories/macros and next-meal focus from Nutrition Pacing; use Next Meal Builder for protein/carbs/fat/fiber targets, meal timing cue, portion uncertainty cue, and photo/label cue before the next meal; use Meal Logging Coach to prefill the suggested meal, choose the recommended meal template, capture food/menu/label photo evidence, and open AI review; use Meal Assembly Guide to translate that macro target into plate structure, protein anchor, carb anchor, fat control, fiber/micros, shopping cue, prep cue, photo/logging cue, and avoid cue; quick-add common high-protein meals from Meal Templates and refine them with labels, photos, oil, sauce, and portion details; use Body Composition Guidance and Physique Measurement Summary to compare phase goal, body-weight trend, waistCm, chestCm, shoulderCm, hipCm, leftArmCm, rightArmCm, leftThighCm, rightThighCm, neckCm, shoulder-to-waist ratio, arm symmetry, thigh symmetry, average calories/protein, and training output before holding or changing calorie targets; use Recovery Guidance to compare sleep, soreness, fatigue, stress, resting heart rate, and set pressure before pushing, holding, reducing volume, or checking for deload; sync user-authorized body weight, body fat, lean mass, steps, sleep, resting heart rate, and calorie burn from Health Connect when Xiaomi/Mi Fitness, Huawei, scale, watch, or phone apps expose those records; then send the athlete profile, weekly plan, actual session log, Daily Execution Plan, AI Review Action Queue, Training Readiness Builder, Warm-up Ramp Plan, ramp set checklist, planned load percentage, final ramp set quality, first working set gate, Next Set Coach, Tomorrow Coach Brief, Weekly Check-in, Session Quality Dashboard, Training Closeout Coach, exercise history, progression cues, Exercise Substitution Coach, exercise visual guide lines, Unified Exercise Visual Atlas, meals, meal template context, nutrition pacing, Next Meal Builder, Meal Logging Coach, Meal Assembly Guide, body composition guidance, physique measurements, recovery guidance, photos, body metrics, sleep, steps, hunger, fatigue, soreness, and stress into the AI review flow.

Health Connect is the first Android health-data integration layer. It can read compatible Xiaomi/Mi Fitness, Huawei, phone, scale, and watch data only when the source app writes compatible records to Health Connect and the user grants permissions. Huawei Health Kit is documented as a future optional dedicated provider for deeper Huawei-specific syncing; manual entry remains the fallback.

The app keeps API credentials out of source code. Users enter an API key, base URL, and model in the app settings screen. See [`android-app/README.md`](android-app/README.md) for Android Studio, JDK 17, build, and API setup notes.

## Example Prompts

```text
Use $i-want-to-be-an-ifbb-pro to build a 2-year roadmap from intermediate lifter to a professional-level physique standard, with weak-point priorities and check-in metrics.
```

```text
Use $i-want-to-be-an-ifbb-pro to audit my current push/pull/legs plan and tell me how to adjust weekly hard sets, exercise order, and progression for Classic Physique.
```

```text
Use $i-want-to-be-an-ifbb-pro to analyze this session log and decide which exercises should add reps, hold, deload, or be swapped.
```

```text
Use $i-want-to-be-an-ifbb-pro to review my squat video frames and today's food photos together, estimate nutrition, identify form issues, and tell me whether training execution, calories, carbs, or recovery should change.
```

```text
Use $i-want-to-be-an-ifbb-pro to plan a conservative contest-prep outline, posing schedule, and safety checkpoints for a show I am considering.
```

See [examples/pressure-scenarios.md](examples/pressure-scenarios.md) for behavior tests and realistic usage scenarios.

## Safety

This skill is for coaching support and education. It is not medical care, physical therapy, dietetic treatment, drug guidance, or an official contest-rules source. Photo/video form checks and food-photo nutrition estimates are approximate, not diagnoses or precise weighing. Users with symptoms, injuries, medical conditions, pregnancy/postpartum needs, eating-disorder risk, extreme body-composition goals, or drug/diuretic questions should work with qualified professionals and official organizers.

## Evidence-Informed Sources

The skill includes an evidence map at [references/evidence-map.md](skills/i-want-to-be-an-ifbb-pro/references/evidence-map.md), which explains which papers, experiments, position stands, systematic reviews, meta-analyses, and validation studies support each skill capability, and where the evidence stops.

The references are summarized from widely used public guidance and official/public anchors, including:

- WHO 2020 guidelines on physical activity and sedentary behavior.
- ACSM 2026 position stand on resistance-training prescription for healthy adults.
- ACSM exercise screening and sports nutrition position statements.
- International Society of Sports Nutrition position stands on protein, diets/body composition, and creatine.
- Resistance-training hypertrophy systematic reviews and meta-analyses, including Schoenfeld and colleagues on volume, frequency, and load.
- Protein meta-analysis/meta-regression work, including Morton and colleagues.
- Natural bodybuilding contest-prep evidence reviews, including Helms, Aragon, and Fitschen.
- Image-assisted dietary assessment and food-recognition validation research, including Boushey/mobile food record work and systematic reviews.
- Official IFBB Pro League / NPC / NPC Worldwide public resources for rule-verification prompts and pathway awareness.

See [references/sources.md](skills/i-want-to-be-an-ifbb-pro/references/sources.md) for source links.

## License

MIT
