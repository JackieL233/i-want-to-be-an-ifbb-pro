import json
import subprocess
import sys
import unittest
from pathlib import Path


ROOT = Path(__file__).resolve().parents[1]


class ScriptSmokeTest(unittest.TestCase):
    def run_json_script(self, *args: str) -> dict:
        result = subprocess.run(
            [sys.executable, *args],
            cwd=ROOT,
            check=True,
            text=True,
            capture_output=True,
        )
        return json.loads(result.stdout)

    def test_estimate_targets_outputs_expected_keys(self) -> None:
        data = self.run_json_script(
            "skills/i-want-to-be-an-ifbb-pro/scripts/estimate_targets.py",
            "--sex",
            "male",
            "--age",
            "30",
            "--height-cm",
            "178",
            "--weight-kg",
            "80",
            "--activity",
            "moderate",
            "--goal",
            "fat-loss",
        )
        self.assertEqual(data["goal"], "fat-loss")
        self.assertGreater(data["starting_calories_kcal"], 0)
        self.assertGreater(data["protein_g"], 0)

    def test_analyze_checkin_outputs_recommendation(self) -> None:
        data = self.run_json_script(
            "skills/i-want-to-be-an-ifbb-pro/scripts/analyze_checkin.py",
            "--goal",
            "fat-loss",
            "--weekly-change-pct",
            "-0.7",
            "--adherence",
            "0.9",
            "--recovery",
            "good",
        )
        self.assertEqual(data["trend"], "on-target")
        self.assertIn("recommendation", data)

    def test_analyze_checkin_accepts_json_input_file(self) -> None:
        data = self.run_json_script(
            "skills/i-want-to-be-an-ifbb-pro/scripts/analyze_checkin.py",
            "--input-json",
            "examples/sample-checkin.json",
        )
        self.assertEqual(data["goal"], "fat-loss")
        self.assertEqual(data["trend"], "on-target")

    def test_analyze_training_volume_summarizes_session_log(self) -> None:
        data = self.run_json_script(
            "skills/i-want-to-be-an-ifbb-pro/scripts/analyze_training_volume.py",
            "--session-csv",
            "examples/sample-session-log.csv",
        )
        self.assertIn("muscle_summary", data)
        self.assertIn("chest", data["muscle_summary"])
        self.assertGreater(data["muscle_summary"]["chest"]["hard_sets"], 0)


if __name__ == "__main__":
    unittest.main()
