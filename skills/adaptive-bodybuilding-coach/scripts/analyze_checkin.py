#!/usr/bin/env python3
"""Classify simple physique check-in trends.

This is a coaching helper, not a medical tool. It uses conservative deterministic
rules so an agent can explain likely next steps without inventing arithmetic.
"""

from __future__ import annotations

import argparse
import json
import sys
from pathlib import Path


def classify_weight_rate(goal: str, weekly_change_pct: float) -> str:
    if goal == "fat-loss":
        if -1.0 <= weekly_change_pct <= -0.5:
            return "on-target"
        if weekly_change_pct > -0.5:
            return "too-slow"
        return "too-fast"
    if goal == "lean-gain":
        if 0.25 <= weekly_change_pct <= 0.5:
            return "on-target"
        if weekly_change_pct < 0.25:
            return "too-slow"
        return "too-fast"
    if goal == "recomposition":
        if -0.25 <= weekly_change_pct <= 0.25:
            return "scale-stable"
        return "scale-drifting"
    return "monitor"


def recommendation(goal: str, trend: str, adherence: float, recovery: str) -> str:
    if adherence < 0.8:
        return "Improve adherence before changing calories or training."
    if recovery == "poor":
        return "Address recovery and consider reducing training stress before adding more work."
    if goal == "fat-loss":
        if trend == "too-slow":
            return "Consider reducing 100-250 kcal/day or adding steps/cardio."
        if trend == "too-fast":
            return "Consider adding 100-250 kcal/day or reducing cardio to protect performance."
    if goal == "lean-gain":
        if trend == "too-slow":
            return "Consider adding 100-200 kcal/day."
        if trend == "too-fast":
            return "Consider reducing 100-200 kcal/day and monitoring waist gain."
    if trend in {"on-target", "scale-stable"}:
        return "Keep the plan and monitor another week."
    return "Hold plan until more trend data is available."


def main() -> None:
    parser = argparse.ArgumentParser()
    parser.add_argument("--input-json", help="Optional JSON check-in file")
    parser.add_argument("--goal", choices=["fat-loss", "lean-gain", "recomposition", "maintenance"])
    parser.add_argument("--weekly-change-pct", type=float)
    parser.add_argument("--adherence", type=float, default=0.9, help="0.0 to 1.0")
    parser.add_argument("--recovery", choices=["good", "mixed", "poor"], default="good")
    args = parser.parse_args()
    raw_args = sys.argv[1:]

    payload = {}
    if args.input_json:
        payload = json.loads(Path(args.input_json).read_text(encoding="utf-8"))

    goal = args.goal or payload.get("goal")
    weekly_change_pct = args.weekly_change_pct
    if weekly_change_pct is None:
        weekly_change_pct = payload.get("weekly_change_pct")
    adherence = args.adherence if "--adherence" in raw_args else payload.get("adherence", args.adherence)
    recovery = args.recovery if "--recovery" in raw_args else payload.get("recovery", args.recovery)

    if goal is None or weekly_change_pct is None:
        parser.error("provide --goal and --weekly-change-pct, or --input-json with goal and weekly_change_pct")

    trend = classify_weight_rate(goal, weekly_change_pct)
    result = {
        "goal": goal,
        "weekly_change_pct": weekly_change_pct,
        "trend": trend,
        "adherence": adherence,
        "recovery": recovery,
        "recommendation": recommendation(goal, trend, adherence, recovery),
    }
    print(json.dumps(result, indent=2))


if __name__ == "__main__":
    main()
