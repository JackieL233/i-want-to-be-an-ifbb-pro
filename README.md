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
- Tracks each workout at exercise/set level, including hard sets, tonnage, RIR/RPE, pain, technique quality, and target-muscle stimulus.
- Provides beginner-friendly equipment/action context for exercise names, including stable visual guide IDs, Chinese equipment labels, simple unified instance diagrams, action path cues, beginner recognition cues, equipment markers, example movements, common movement examples, and look-for cues for Smith machine, cable station, dumbbells, barbell, machine, adjustable bench, pull-up/dip station, resistance band, leg press or hack squat, and bodyweight/open-station work.
- Uses multimodal inputs for equipment identification, exercise form analysis, food photo nutrition estimates, portion-size uncertainty, and linked training/nutrition check-ins.
- Guides calories/macros, meal timing, hydration, supplements, and adjustment rules within conservative safety boundaries.
- Includes app-ready Meal Templates and Next Meal Builder for fast high-protein logging, practical next-meal macro targets, meal timing cues, and portion/photo uncertainty handling.
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

The app UI is moving toward a simple Apple-inspired daily cockpit: a quiet light theme, bottom navigation, a `Today` Command Center, Daily Execution Plan, Daily Coach Checklist, readiness score, next action guidance, and beginner-friendly steps that still expose professional set-level training, nutrition, health, and AI review data.

The app also saves local daily log history and shows a 7-day trend card. AI review now receives recent trend rows, so it can compare today's training, calories, protein, body weight, sleep, steps, fatigue, and hard sets against recent direction instead of reacting to one isolated day.

AI review output is saved locally as recent coaching guidance. The latest review appears on `Today`, and recent reviews remain available in `AI Coach` so the app can act more like a persistent daily coach instead of a one-off chat response.

The app now supports Athlete Profile setup, Plan Templates, weekly training plans, set-level workout execution, Daily Execution Plan, Training Readiness Builder, Exercise History, Progression Cue, Meal Templates, Nutrition Pacing, Next Meal Builder, Body Composition Guidance, Recovery Guidance, a unified exercise visual guide, Daily Coach Checklist, and Health Connect metric sync: define the long-term physique goal, phase, training experience, target weight/body fat, weekly training days, equipment, weak points, and constraints; start from Beginner Full Body, 4-Day Hypertrophy, or 5-Day Physique Priority when you want a ready-to-train structure; build or edit a weekly plan; apply a planned day to today's workout; use Today to check the Daily Execution Plan's priority focus, primary action, training decision, nutrition decision, recovery decision, data quality gate, AI review gate, and plan adjustment signal; then follow `Plan prepared`, `Training executed`, `Food logged`, `Metrics synced`, and `AI review locked`; recognize the intended equipment/action from visual guide IDs, Chinese equipment labels, unified instance diagrams, action path cues, beginner recognition cues, equipment markers, instance diagram cues, example movements, common movement examples, and look-for cues; use the live equipment/action preview while adding a movement; follow Training Readiness Builder for warm-up strategy, ramp-up cue, first working set decision, volume adjustment, and stop rule; record each set's weight, reps, RIR, completion state, rest time, and notes; compare repeated exercises against the previous logged session for volume, best load, best reps, completed sets, and average RIR; get an immediate cue on whether to add reps, add load, hold, modify, or finish baseline work; tap a completed set to start a rest countdown; follow remaining calories/macros and next-meal focus from Nutrition Pacing; use Next Meal Builder for protein/carbs/fat/fiber targets, meal timing cue, portion uncertainty cue, and photo/label cue before the next meal; quick-add common high-protein meals from Meal Templates and refine them with labels, photos, oil, sauce, and portion details; use Body Composition Guidance to compare phase goal, body-weight trend, average calories/protein, and training output before holding or changing calorie targets; use Recovery Guidance to compare sleep, soreness, fatigue, stress, resting heart rate, and set pressure before pushing, holding, reducing volume, or checking for deload; sync user-authorized body weight, body fat, lean mass, steps, sleep, resting heart rate, and calorie burn from Health Connect when Xiaomi/Mi Fitness, Huawei, scale, watch, or phone apps expose those records; then send the athlete profile, weekly plan, actual session log, Daily Execution Plan, Training Readiness Builder, exercise history, progression cues, exercise visual guide lines, meals, meal template context, nutrition pacing, Next Meal Builder, body composition guidance, recovery guidance, photos, body metrics, sleep, steps, hunger, fatigue, soreness, and stress into the AI review flow.

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
