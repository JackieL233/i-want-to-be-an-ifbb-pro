# Contributing

Contributions are welcome, especially improvements that make the skill safer, clearer, more evidence-informed, or easier to adapt across LLM runtimes.

## Good Contributions

- Clearer coaching workflows.
- Better safety-screening language.
- More precise exercise substitutions or technique cues.
- Evidence-informed updates with reputable citations.
- Templates for check-ins, weekly plans, or progress reports.
- Tests or validation scripts for skill metadata.

## Standards

- Keep `SKILL.md` concise and procedural.
- Put detailed domain material in `references/`.
- Do not add medical diagnosis or treatment claims.
- Avoid extreme dieting, dehydration, or drug-use advice.
- Prefer practical decision rules over vague motivation.

## Validation

Run:

```bash
python scripts/validate_skill.py skills/i-want-to-be-an-ifbb-pro
python -m unittest tests.test_skill_completeness
python skills/i-want-to-be-an-ifbb-pro/scripts/estimate_targets.py --sex male --age 30 --height-cm 178 --weight-kg 80 --activity moderate --goal fat-loss
python skills/i-want-to-be-an-ifbb-pro/scripts/analyze_checkin.py --goal fat-loss --weekly-change-pct -0.7 --adherence 0.9 --recovery good
python skills/i-want-to-be-an-ifbb-pro/scripts/analyze_training_volume.py --session-csv examples/sample-session-log.csv
```
