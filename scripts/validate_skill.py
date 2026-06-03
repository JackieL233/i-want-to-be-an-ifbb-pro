#!/usr/bin/env python3
"""Small repository-local validator for portable skill projects."""

from __future__ import annotations

import re
import sys
from pathlib import Path


NAME_RE = re.compile(r"^[a-z0-9-]{1,63}$")


def fail(message: str) -> None:
    print(f"ERROR: {message}", file=sys.stderr)
    raise SystemExit(1)


def parse_frontmatter(text: str) -> dict[str, str]:
    if not text.startswith("---\n"):
        fail("SKILL.md must start with YAML frontmatter")
    try:
        _, raw, _ = text.split("---", 2)
    except ValueError:
        fail("SKILL.md frontmatter must be closed with ---")

    values: dict[str, str] = {}
    for line in raw.strip().splitlines():
        if not line.strip():
            continue
        if ":" not in line:
            fail(f"Invalid frontmatter line: {line}")
        key, value = line.split(":", 1)
        values[key.strip()] = value.strip()
    return values


def main() -> None:
    if len(sys.argv) != 2:
        fail("Usage: validate_skill.py <skill-directory>")

    skill_dir = Path(sys.argv[1])
    skill_md = skill_dir / "SKILL.md"
    if not skill_md.exists():
        fail(f"Missing {skill_md}")

    text = skill_md.read_text(encoding="utf-8")
    frontmatter = parse_frontmatter(text)
    name = frontmatter.get("name", "")
    description = frontmatter.get("description", "")

    if not NAME_RE.match(name):
        fail("frontmatter name must use lowercase letters, digits, and hyphens")
    if skill_dir.name != name:
        fail(f"skill directory name must match frontmatter name: {name}")
    if len(description) < 80:
        fail("description should be detailed enough to trigger the skill")
    if "[TODO" in text or "TODO:" in text:
        fail("remove TODO placeholders before release")

    required = [
        "references/safety-screening.md",
        "references/intake-assessment.md",
        "references/anatomy-and-movement.md",
        "references/goal-decision-system.md",
        "references/pro-level-physique-roadmap.md",
        "references/plan-optimization.md",
        "references/training-programming.md",
        "references/exercise-library.md",
        "references/phase-templates.md",
        "references/session-execution-and-volume.md",
        "references/visual-analysis-and-food-estimation.md",
        "references/contest-prep-and-posing.md",
        "references/nutrition-body-composition.md",
        "references/recovery-injury-risk.md",
        "references/data-tracking-adjustment.md",
        "references/adaptation-playbook.md",
        "references/model-adaptation.md",
        "references/sources.md",
        "assets/templates/intake-form.md",
        "assets/templates/check-in-form.md",
        "assets/templates/plan-template.md",
        "assets/templates/session-log.csv",
        "assets/templates/tracking-log.csv",
        "scripts/estimate_targets.py",
        "scripts/analyze_checkin.py",
        "scripts/analyze_training_volume.py",
        "agents/openai.yaml",
    ]
    for relative in required:
        if not (skill_dir / relative).exists():
            fail(f"Missing required file: {relative}")

    print(f"OK: {skill_dir}")


if __name__ == "__main__":
    main()
