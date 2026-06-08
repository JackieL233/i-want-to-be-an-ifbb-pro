import unittest
from pathlib import Path


ROOT = Path(__file__).resolve().parents[1]
APP = ROOT / "android-app"


class AndroidAppStructureTest(unittest.TestCase):
    def test_android_project_files_exist(self) -> None:
        required = [
            "README.md",
            "settings.gradle.kts",
            "build.gradle.kts",
            "gradle.properties",
            "app/build.gradle.kts",
            "app/src/main/AndroidManifest.xml",
            "app/src/main/java/com/iwanttobeanifbbpro/app/MainActivity.kt",
            "app/src/main/java/com/iwanttobeanifbbpro/app/core/SkillPromptBuilder.kt",
            "app/src/main/java/com/iwanttobeanifbbpro/app/core/SkillAssetRepository.kt",
            "app/src/main/java/com/iwanttobeanifbbpro/app/network/OpenAiResponsesClient.kt",
            "app/src/main/java/com/iwanttobeanifbbpro/app/data/DailyLog.kt",
            "app/src/main/java/com/iwanttobeanifbbpro/app/data/DailyLogStore.kt",
            "app/src/main/java/com/iwanttobeanifbbpro/app/core/DailySummaryBuilder.kt",
            "app/src/main/java/com/iwanttobeanifbbpro/app/ui/CoachViewModel.kt",
            "app/src/main/java/com/iwanttobeanifbbpro/app/ui/IfbbProCoachApp.kt",
        ]
        for relative in required:
            with self.subTest(relative=relative):
                self.assertTrue((APP / relative).exists(), relative)

    def test_app_embeds_skill_assets(self) -> None:
        asset_root = APP / "app/src/main/assets/skill"
        required_assets = [
            "SKILL.md",
            "references/evidence-map.md",
            "references/visual-analysis-and-food-estimation.md",
            "references/training-programming.md",
            "references/nutrition-body-composition.md",
            "references/session-execution-and-volume.md",
            "references/model-adaptation.md",
        ]
        for relative in required_assets:
            with self.subTest(relative=relative):
                self.assertTrue((asset_root / relative).exists(), relative)

    def test_api_client_supports_configurable_multimodal_responses_api(self) -> None:
        client = (APP / "app/src/main/java/com/iwanttobeanifbbpro/app/network/OpenAiResponsesClient.kt").read_text(
            encoding="utf-8"
        )
        expected_terms = [
            "/responses",
            "instructions",
            "input_image",
            "image_url",
            "data:",
            "output_text",
            "baseUrl",
            "apiKey",
            "model",
        ]
        for term in expected_terms:
            with self.subTest(term=term):
                self.assertIn(term, client)
        self.assertNotIn("sk-", client)

    def test_prompt_builder_exposes_skill_modes_and_linked_analysis(self) -> None:
        prompt = (APP / "app/src/main/java/com/iwanttobeanifbbpro/app/core/SkillPromptBuilder.kt").read_text(
            encoding="utf-8"
        )
        expected_terms = [
            "NEW_PLAN",
            "CHECK_IN",
            "TRAINING_VISUAL",
            "FOOD_VISUAL",
            "LINKED_TRAINING_NUTRITION",
            "evidence-map.md",
            "visual-analysis-and-food-estimation.md",
            "session-execution-and-volume.md",
            "nutrition-body-composition.md",
        ]
        for term in expected_terms:
            with self.subTest(term=term):
                self.assertIn(term, prompt)

    def test_daily_app_model_supports_training_nutrition_metrics_and_ai_review(self) -> None:
        model = (APP / "app/src/main/java/com/iwanttobeanifbbpro/app/data/DailyLog.kt").read_text(
            encoding="utf-8"
        )
        store = (APP / "app/src/main/java/com/iwanttobeanifbbpro/app/data/DailyLogStore.kt").read_text(
            encoding="utf-8"
        )
        summary = (APP / "app/src/main/java/com/iwanttobeanifbbpro/app/core/DailySummaryBuilder.kt").read_text(
            encoding="utf-8"
        )
        expected_terms = [
            "TrainingSession",
            "ExerciseEntry",
            "SetEntry",
            "MealEntry",
            "DailyMetrics",
            "DailyTargets",
            "setEntries",
            "completedHardSets",
            "trainingVolumeKg",
            "restSeconds",
            "calories",
            "protein",
            "carbs",
            "fat",
            "bodyWeightKg",
            "waistCm",
            "sleepHours",
            "readLog",
            "saveLog",
            "buildAiReviewContext",
            "daily training",
            "daily nutrition",
            "AI review",
            "set-level performance",
            "rest time",
        ]
        combined = f"{model}\n{store}\n{summary}"
        for term in expected_terms:
            with self.subTest(term=term):
                self.assertIn(term, combined)

    def test_ui_exposes_daily_workflows(self) -> None:
        ui = (APP / "app/src/main/java/com/iwanttobeanifbbpro/app/ui/IfbbProCoachApp.kt").read_text(
            encoding="utf-8"
        )
        view_model = (APP / "app/src/main/java/com/iwanttobeanifbbpro/app/ui/CoachViewModel.kt").read_text(
            encoding="utf-8"
        )
        expected_terms = [
            "Today",
            "Training",
            "Nutrition",
            "Metrics",
            "AI Coach",
            "addExercise",
            "addMeal",
            "updateMetrics",
            "updateTargets",
            "runDailyReview",
            "hard sets",
            "meal photo",
            "Rest timer",
            "Complete",
            "AI Data Map",
            "Set load",
            "Actual reps",
        ]
        combined = f"{ui}\n{view_model}"
        for term in expected_terms:
            with self.subTest(term=term):
                self.assertIn(term, combined)

    def test_readme_documents_build_and_api_setup(self) -> None:
        readme = (APP / "README.md").read_text(encoding="utf-8")
        expected_terms = [
            "Android Studio",
            "JDK 17",
            "API key",
            "base URL",
            "model",
            "OpenAI Responses API",
            "photos",
            "skill assets",
            "daily training",
            "daily nutrition",
            "AI review",
            "rest countdown",
            "set-level",
            "AI Data Map",
        ]
        for term in expected_terms:
            with self.subTest(term=term):
                self.assertIn(term, readme)


if __name__ == "__main__":
    unittest.main()
