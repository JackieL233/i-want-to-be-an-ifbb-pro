#!/usr/bin/env python3
"""Summarize exercise-level session logs into muscle-level training signals."""

from __future__ import annotations

import argparse
import csv
import json
from collections import defaultdict
from pathlib import Path
from typing import Any


def as_float(value: str, default: float = 0.0) -> float:
    try:
        return float(value)
    except (TypeError, ValueError):
        return default


def is_hard_set(row: dict[str, str]) -> bool:
    rir = as_float(row.get("rir", ""), 99)
    technique = as_float(row.get("technique_quality_1_5", ""), 0)
    pain = as_float(row.get("pain_0_10", ""), 0)
    completed = row.get("completed", "yes").strip().lower()
    return completed in {"yes", "true", "1"} and 0 <= rir <= 4 and technique >= 3 and pain < 5


def effective_reps(row: dict[str, str]) -> float:
    reps = as_float(row.get("reps", ""))
    rir = as_float(row.get("rir", ""), 99)
    if rir > 5:
        return 0
    return max(min(reps, 5 - rir), 0)


def summarize(rows: list[dict[str, str]]) -> dict[str, Any]:
    muscles: dict[str, dict[str, float]] = defaultdict(
        lambda: {
            "sets": 0,
            "hard_sets": 0,
            "tonnage": 0.0,
            "effective_reps": 0.0,
            "pain_flags": 0,
            "technique_total": 0.0,
            "stimulus_total": 0.0,
        }
    )
    exercises: dict[str, dict[str, float]] = defaultdict(
        lambda: {"sets": 0, "hard_sets": 0, "tonnage": 0.0, "best_reps": 0}
    )

    for row in rows:
        muscle = row.get("target_muscle", "unknown").strip().lower() or "unknown"
        exercise = row.get("exercise", "unknown").strip()
        load = as_float(row.get("load", ""))
        reps = as_float(row.get("reps", ""))
        tonnage = load * reps
        hard = is_hard_set(row)
        pain = as_float(row.get("pain_0_10", ""))

        muscles[muscle]["sets"] += 1
        muscles[muscle]["hard_sets"] += 1 if hard else 0
        muscles[muscle]["tonnage"] += tonnage
        muscles[muscle]["effective_reps"] += effective_reps(row)
        muscles[muscle]["pain_flags"] += 1 if pain >= 3 else 0
        muscles[muscle]["technique_total"] += as_float(row.get("technique_quality_1_5", ""))
        muscles[muscle]["stimulus_total"] += as_float(row.get("target_stimulus_1_5", ""))

        exercises[exercise]["sets"] += 1
        exercises[exercise]["hard_sets"] += 1 if hard else 0
        exercises[exercise]["tonnage"] += tonnage
        exercises[exercise]["best_reps"] = max(exercises[exercise]["best_reps"], reps)

    muscle_summary = {}
    for muscle, values in muscles.items():
        sets = max(values["sets"], 1)
        muscle_summary[muscle] = {
            "sets": int(values["sets"]),
            "hard_sets": int(values["hard_sets"]),
            "tonnage": round(values["tonnage"], 1),
            "effective_reps": round(values["effective_reps"], 1),
            "avg_technique_quality": round(values["technique_total"] / sets, 2),
            "avg_target_stimulus": round(values["stimulus_total"] / sets, 2),
            "pain_flags": int(values["pain_flags"]),
        }

    exercise_summary = {
        exercise: {
            "sets": int(values["sets"]),
            "hard_sets": int(values["hard_sets"]),
            "tonnage": round(values["tonnage"], 1),
            "best_reps": int(values["best_reps"]),
        }
        for exercise, values in exercises.items()
    }

    return {
        "session_rows": len(rows),
        "muscle_summary": muscle_summary,
        "exercise_summary": exercise_summary,
        "coaching_note": "Use hard sets, stimulus, technique, pain, and performance trend together before changing volume.",
    }


def main() -> None:
    parser = argparse.ArgumentParser()
    parser.add_argument("--session-csv", required=True)
    args = parser.parse_args()

    with Path(args.session_csv).open(newline="", encoding="utf-8") as handle:
        rows = list(csv.DictReader(handle))
    print(json.dumps(summarize(rows), indent=2))


if __name__ == "__main__":
    main()
