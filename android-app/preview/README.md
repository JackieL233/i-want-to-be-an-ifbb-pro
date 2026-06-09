# Android App UI Preview

This folder contains a local, static preview of the `I Want to be an IFBB PRO` Android app UI.

Open `index.html` in a browser to review the Apple-inspired product feel without requiring Android Studio, an emulator, a JDK, or an APK build.

The preview mirrors the current Android app structure:

- Today command center
- EN/中文 language switch preview for the app shell
- Start Here / 从这里开始 first-screen loop with plan, training, food, metrics, and AI review steps
- Daily Execution Plan for priority focus, primary action, data quality gate, AI review gate, and plan adjustment signal
- AI Review Action Queue with sourceLabel, confidenceLabel, primaryAction, actionLabel, training action, nutrition action, recovery action, tracking action, and plan action routing
- Tomorrow Coach Brief for next-day training focus, calorie/protein targets, readiness gate, recovery action, and tracking action
- Weekly Check-in for 7-14 day training completion, average calories/protein, weight trend, recovery average, weak-point focus, data quality, and next-week action
- Daily Coach Checklist for plan, training, food, metrics, and AI review completion
- Athlete profile and weekly plan
- Plan Templates for Beginner Full Body, 4-Day Hypertrophy, and 5-Day Physique Priority
- Unified exercise visual guide for equipment/action recognition, including stable visual guide IDs
- Exercise Visual Legend / 统一动作图例 with compact VG-01 to VG-10 diagrams
- Unified Exercise Visual Atlas with the same three-step recognition flow used by Android and AI review: match the simplified instance diagram, find the real equipment markers, and follow the intended movement path
- Today Visual Primer for training-before-you-start equipment/action scanning with unified simple equipment/action instance diagrams, visual-map thumbnails, visual guide IDs, Chinese equipment labels, and beginner recognition cues
- Selected Day Visual Map, Today's Exercise Visual Map, visual-map thumbnails, and exercise thumbnail headers for quick equipment/action scanning
- Live equipment/action preview while adding planned or active exercises
- Exercise Visual Library with Chinese equipment labels, simple unified instance diagrams, three-step recognition, quick visual cues, find-equipment cues, movement path cues, action path cues, beginner recognition cues, equipment markers, instance diagram cues, example movements, common movement examples, and look-for cues for Smith machine, cable station, dumbbells, barbell, machine, adjustable bench, pull-up/dip station, resistance band, leg press or hack squat, and bodyweight/open-station work
- Set-level training execution
- Training Readiness Builder for warm-up strategy, ramp-up cue, first working set, volume adjustment, and stop rule
- Warm-up Ramp Plan for exact ramp set checklist, planned load percentages, final ramp set quality, first working set gate, and stop rule before the first working set
- Next Set Coach for the current exercise, next set target, load cue, reps cue, RIR cue, rest cue, stop cue, After-set logging cue, and the matching equipment/action instance diagram
- Session Quality Dashboard for completion rate, logged set rate, average RIR, muscle-volume distribution, pain flags, and technique flags
- Training Closeout Coach for closeout score, missing set logs, photo evidence cue, post-workout nutrition cue, metrics sync cue, and AI review readiness before changing tomorrow's plan
- Exercise History comparison for previous/current volume, best load, best reps, sets, and average RIR
- Exercise Substitution Coach for equipment unavailable, trigger reason, primaryOption, secondaryOptions, same target muscle, same movement pattern, preserve rep range, fatigue cost, keepIntentCue, loadAdjustmentCue, and visual guide ID continuity
- Rest countdown after completing a set
- Photo Evidence for classified food, form, equipment, label/menu, and physique progress photos
- Daily nutrition tracking
- Meal Templates for quick-add high-protein meals
- Next Meal Builder for practical next-meal macro targets, timing, portion uncertainty, and photo/label cues
- Meal Assembly Guide for plate structure, protein/carb anchors, fat control, shopping/prep cues, and photo logging cues
- Body Composition Guidance for trend-based calorie target checks
- Conditioning & Hydration for step goal progress, cardio minutes/type/intensity, water liters, sodium, caffeine, alcohol, digestion notes, and scale-weight noise before calorie or volume changes
- Physique Measurement Summary for waist, chest, shoulder, hip, left/right arms, left/right thighs, neck, shoulder-to-waist ratio, arm symmetry, thigh symmetry, and weekly tape-measure check-ins
- Recovery Guidance for sleep, soreness, stress, resting HR, and training-pressure decisions
- Health Connect and vendor-data boundary
- AI Coach and AI Data Map

This is not a replacement for real Android testing. Use it for fast visual iteration; use Android Studio or an Android test plugin for APK, emulator, and device validation.
