# Model Adaptation

Use this to port the skill across LLM systems.

## Core Packaging

- Main instruction: `SKILL.md`.
- Retrieval corpus: all files in `references/`.
- User-facing templates: files in `assets/templates/`.
- Deterministic helpers: files in `scripts/`.
- UI metadata: `agents/openai.yaml`.

## Runtime Pattern

1. Load `SKILL.md`.
2. Detect request type: pro-level physique roadmap, new plan, existing-plan audit, contest prep, posing, check-in, nutrition, exercise substitution, multimodal image/video analysis, safety, education, or adaptation.
3. Retrieve only the relevant reference files named in `SKILL.md`.
4. Ask for missing safety-critical data before prescribing high-intensity work.
5. Produce a concise plan with assumptions and next check-in.
6. For visual inputs, preserve uncertainty and link training visuals with food-photo nutrition estimates before recommending adjustments.
7. Store or request follow-up data for iteration.

## Prompt Wrapper for Other Models

Use:

```text
You are using the i-want-to-be-an-ifbb-pro skill. Follow SKILL.md as the controlling workflow. Load only the reference files needed for the user's request. Do not diagnose or treat medical conditions. Build safe, individualized, evidence-informed plans and iterate from tracked data.
```

## Retrieval Hints

- Safety, pain, medical, pregnancy, eating concerns, drug questions -> `safety-screening.md`.
- Missing profile or first consultation -> `intake-assessment.md`.
- IFBB Pro aspiration, pro-level physique roadmap, division choice, official rules -> `pro-level-physique-roadmap.md`.
- Existing plan review or optimization -> `plan-optimization.md`.
- Exercise choice by muscle or joint -> `anatomy-and-movement.md`, `exercise-library.md`.
- Split or mesocycle -> `training-programming.md`, `phase-templates.md`.
- Session log, hard sets, tonnage, training quality -> `session-execution-and-volume.md`.
- Gym equipment photos, exercise form images/video frames, food photos, labels, menus, portion estimates, or linked training/nutrition visual check-ins -> `visual-analysis-and-food-estimation.md`.
- Contest prep, posing, stage conditioning, peak week -> `contest-prep-and-posing.md`.
- Calories/macros/supplements -> `nutrition-body-composition.md`.
- Check-in or plateau -> `data-tracking-adjustment.md`, `adaptation-playbook.md`.

## App Workflow

For an app or agent:

- Intake form -> pro-level physique roadmap or plan generator -> weekly check-in -> trend analyzer -> coach response.
- Visual workflow: upload exercise image/video frame and food photo -> retrieve `visual-analysis-and-food-estimation.md` -> identify equipment/form and estimate meal nutrition -> compare with session log and macro target -> linked check-in adjustment.
- Keep safety screening before plan generation.
- Keep user-editable assumptions.
- Store data as trends, not isolated values.
- Require clinician referral language for red-flag flows.

## Multi-Language Use

The skill can respond in the user's language. Preserve technical terms like RIR/RPE when useful, and define them once.
