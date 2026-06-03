#!/usr/bin/env python3
"""Estimate starting calories and macros for physique planning.

This helper is intentionally simple. Use trend data and coaching judgment to
adjust the output.
"""

from __future__ import annotations

import argparse
import json


ACTIVITY = {
    "sedentary": 1.2,
    "light": 1.375,
    "moderate": 1.55,
    "high": 1.725,
    "very-high": 1.9,
}


def mifflin_st_jeor(weight_kg: float, height_cm: float, age: int, sex: str) -> float:
    sex_offset = 5 if sex == "male" else -161
    return 10 * weight_kg + 6.25 * height_cm - 5 * age + sex_offset


def goal_calories(maintenance: float, goal: str) -> float:
    if goal == "fat-loss":
        return maintenance * 0.85
    if goal == "lean-gain":
        return maintenance * 1.07
    return maintenance


def estimate(args: argparse.Namespace) -> dict[str, float | str]:
    bmr = mifflin_st_jeor(args.weight_kg, args.height_cm, args.age, args.sex)
    maintenance = bmr * ACTIVITY[args.activity]
    calories = goal_calories(maintenance, args.goal)

    protein_g = args.weight_kg * (2.0 if args.goal == "fat-loss" else 1.8)
    fat_g = max(args.weight_kg * 0.7, calories * 0.2 / 9)
    carbs_g = max((calories - protein_g * 4 - fat_g * 9) / 4, 0)

    return {
        "goal": args.goal,
        "bmr_kcal": round(bmr),
        "maintenance_kcal": round(maintenance),
        "starting_calories_kcal": round(calories),
        "protein_g": round(protein_g),
        "fat_g": round(fat_g),
        "carbs_g": round(carbs_g),
        "note": "Starting estimate only; adjust using 2-4 weeks of trend data.",
    }


def main() -> None:
    parser = argparse.ArgumentParser()
    parser.add_argument("--sex", choices=["male", "female"], required=True)
    parser.add_argument("--age", type=int, required=True)
    parser.add_argument("--height-cm", type=float, required=True)
    parser.add_argument("--weight-kg", type=float, required=True)
    parser.add_argument("--activity", choices=sorted(ACTIVITY), default="moderate")
    parser.add_argument(
        "--goal",
        choices=["fat-loss", "lean-gain", "recomposition", "maintenance"],
        default="recomposition",
    )
    args = parser.parse_args()
    print(json.dumps(estimate(args), indent=2))


if __name__ == "__main__":
    main()
