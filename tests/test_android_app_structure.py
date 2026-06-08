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
            "app/src/main/java/com/iwanttobeanifbbpro/app/core/ProgressionAdvisor.kt",
            "app/src/main/java/com/iwanttobeanifbbpro/app/core/ExerciseHistoryAdvisor.kt",
            "app/src/main/java/com/iwanttobeanifbbpro/app/core/BodyCompositionAdvisor.kt",
            "app/src/main/java/com/iwanttobeanifbbpro/app/core/ExerciseVisualGuide.kt",
            "app/src/main/java/com/iwanttobeanifbbpro/app/core/RecoveryAdvisor.kt",
            "app/src/main/java/com/iwanttobeanifbbpro/app/core/TrainingReadinessAdvisor.kt",
            "app/src/main/java/com/iwanttobeanifbbpro/app/network/OpenAiResponsesClient.kt",
            "app/src/main/java/com/iwanttobeanifbbpro/app/data/DailyLog.kt",
            "app/src/main/java/com/iwanttobeanifbbpro/app/data/DailyLogStore.kt",
            "app/src/main/java/com/iwanttobeanifbbpro/app/data/AiReviewStore.kt",
            "app/src/main/java/com/iwanttobeanifbbpro/app/data/AthleteProfile.kt",
            "app/src/main/java/com/iwanttobeanifbbpro/app/data/TrainingPlan.kt",
            "app/src/main/java/com/iwanttobeanifbbpro/app/data/TrainingPlanStore.kt",
            "app/src/main/java/com/iwanttobeanifbbpro/app/core/DailySummaryBuilder.kt",
            "app/src/main/java/com/iwanttobeanifbbpro/app/health/HealthSnapshot.kt",
            "app/src/main/java/com/iwanttobeanifbbpro/app/health/HealthConnectRepository.kt",
            "app/src/main/java/com/iwanttobeanifbbpro/app/ui/AppTheme.kt",
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
        plan_model = (APP / "app/src/main/java/com/iwanttobeanifbbpro/app/data/TrainingPlan.kt").read_text(
            encoding="utf-8"
        )
        log_model = (APP / "app/src/main/java/com/iwanttobeanifbbpro/app/data/DailyLog.kt").read_text(
            encoding="utf-8"
        )
        plan_store = (APP / "app/src/main/java/com/iwanttobeanifbbpro/app/data/TrainingPlanStore.kt").read_text(
            encoding="utf-8"
        )
        summary = (APP / "app/src/main/java/com/iwanttobeanifbbpro/app/core/DailySummaryBuilder.kt").read_text(
            encoding="utf-8"
        )
        ai_review_store = (APP / "app/src/main/java/com/iwanttobeanifbbpro/app/data/AiReviewStore.kt").read_text(
            encoding="utf-8"
        )
        athlete_profile = (APP / "app/src/main/java/com/iwanttobeanifbbpro/app/data/AthleteProfile.kt").read_text(
            encoding="utf-8"
        )
        expected_terms = [
            "AthleteProfile",
            "AthleteProfileStore",
            "athlete_profile",
            "primaryGoal",
            "currentPhase",
            "trainingExperience",
            "targetBodyFatPercent",
            "weeklyTrainingDays",
            "weakPoints",
            "constraints",
            "TrainingSession",
            "ExerciseEntry",
            "SetEntry",
            "WeeklyTrainingPlan",
            "TrainingDay",
            "PlannedExercise",
            "TrainingPlanTemplate",
            "trainingPlanTemplates",
            "Beginner Full Body",
            "4-Day Hypertrophy",
            "5-Day Physique Priority",
            "Beginner Full Body Foundation",
            "4-Day Hypertrophy Builder",
            "5-Day Physique Priority",
            "fillWeek",
            "planned(",
            "TrainingPlanStore",
            "readPlan",
            "savePlan",
            "toExerciseEntry",
            "MealEntry",
            "MealTemplate",
            "mealTemplates",
            "toMealEntry",
            "Lean Protein Bowl",
            "Pre-Workout Carbs",
            "Low-Fat Protein Fix",
            "Salmon Potato Plate",
            "Fiber + Micronutrient Add",
            "DailyMetrics",
            "DailyTargets",
            "AiReviewEntry",
            "AiReviewStore",
            "readReviews",
            "saveReview",
            "ai_review_history",
            "setEntries",
            "completedHardSets",
            "trainingVolumeKg",
            "restSeconds",
            "calories",
            "protein",
            "carbs",
            "fat",
            "bodyWeightKg",
            "bodyFatPercent",
            "leanBodyMassKg",
            "waistCm",
            "sleepHours",
            "restingHeartRateBpm",
            "totalCaloriesBurnedKcal",
            "healthDataSource",
            "healthSyncedAt",
            "readLog",
            "saveLog",
            "readRecentLogs",
            "log_date_index",
            "log_",
            "buildAiReviewContext",
            "daily training",
            "daily nutrition",
            "AI review",
            "Athlete profile and goal",
            "Interpret today's data through the athlete profile",
            "set-level performance",
            "rest time",
            "Current weekly training plan",
            "Template context",
            "Health Connect-derived data",
            "Recent trend window",
            "Weight change in window",
            "Daily trend rows",
            "Nutrition pacing",
            "Meal template context",
            "adherence",
            "Next meal focus",
            "Next Meal Builder",
            "NextMealBuilderSummary",
            "nextMealBuilderSummary",
            "Macro target",
            "Timing cue",
            "Portion cue",
            "Photo/label cue",
            "Body composition guidance",
            "BodyCompositionGuidance",
            "bodyCompositionGuidance",
            "calorie adjustment",
            "target calories",
            "phaseGoal",
            "Recovery guidance",
            "RecoveryGuidance",
            "recoveryGuidance",
            "recommended training action",
            "recommendedTrainingAction",
            "readiness score",
            "readinessScore",
            "training pressure",
            "trainingPressure",
            "Controlled push",
            "Hold training stress",
            "Reduce volume",
            "Deload check",
            "Training Readiness Builder",
            "TrainingReadinessBuilder",
            "trainingReadinessBuilder",
            "promptLine",
            "Warm-up cue",
            "warmUpCue",
            "Ramp-up cue",
            "rampUpCue",
            "First working set",
            "firstWorkingSetCue",
            "Volume adjustment",
            "volumeAdjustmentCue",
            "Stop rule",
            "stopRule",
            "AI review focus",
            "aiReviewFocus",
            "Exercise visual guide",
            "visualPromptLine",
            "visual IDs",
            "equipment/action categories",
            "Chinese equipment labels",
            "unified instance diagrams",
            "action path cues",
            "beginner recognition cues",
            "equipment markers",
            "instance diagram cues",
            "common movements",
            "ExerciseVisualSpec",
            "ExerciseVisualType",
            "exerciseVisualLibrarySpecs",
            "visualId",
            "figureTitle",
            "actionPathCue",
            "beginnerCue",
            "equipmentMarkers",
            "VG-01",
            "VG-10",
            "equipmentZh",
            "instanceCue",
            "commonMovements",
            "Adjustable bench",
            "Pull-up/Dip station",
            "Resistance band",
            "Leg press or hack squat",
            "Progression cue",
            "Use each Progression Cue",
            "progressionCue",
            "Exercise history",
            "Compare Exercise History",
            "exerciseHistorySummary",
            "previous volume",
            "current volume",
            "best load",
            "best reps",
            "average RIR",
        ]
        progression = (APP / "app/src/main/java/com/iwanttobeanifbbpro/app/core/ProgressionAdvisor.kt").read_text(
            encoding="utf-8"
        )
        history = (APP / "app/src/main/java/com/iwanttobeanifbbpro/app/core/ExerciseHistoryAdvisor.kt").read_text(
            encoding="utf-8"
        )
        body_composition = (
            APP / "app/src/main/java/com/iwanttobeanifbbpro/app/core/BodyCompositionAdvisor.kt"
        ).read_text(encoding="utf-8")
        recovery = (APP / "app/src/main/java/com/iwanttobeanifbbpro/app/core/RecoveryAdvisor.kt").read_text(
            encoding="utf-8"
        )
        training_readiness = (
            APP / "app/src/main/java/com/iwanttobeanifbbpro/app/core/TrainingReadinessAdvisor.kt"
        ).read_text(encoding="utf-8")
        visual = (APP / "app/src/main/java/com/iwanttobeanifbbpro/app/core/ExerciseVisualGuide.kt").read_text(
            encoding="utf-8"
        )
        combined = f"{model}\n{store}\n{ai_review_store}\n{athlete_profile}\n{plan_model}\n{plan_store}\n{summary}\n{progression}\n{history}\n{body_composition}\n{recovery}\n{training_readiness}\n{visual}"
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
        plan_model = (APP / "app/src/main/java/com/iwanttobeanifbbpro/app/data/TrainingPlan.kt").read_text(
            encoding="utf-8"
        )
        log_model = (APP / "app/src/main/java/com/iwanttobeanifbbpro/app/data/DailyLog.kt").read_text(
            encoding="utf-8"
        )
        expected_terms = [
            "Today",
            "Plan",
            "Training",
            "Nutrition",
            "Metrics",
            "AI Coach",
            "addExercise",
            "addMeal",
            "addMealTemplate",
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
            "Weekly Plan",
            "Plan Templates",
            "PlanTemplateLibrary",
            "PlanTemplateCard",
            "applyTrainingPlanTemplate",
            "trainingPlanTemplates",
            "Beginner Full Body",
            "4-Day Hypertrophy",
            "5-Day Physique Priority",
            "Use",
            "Current",
            "Athlete Profile",
            "Primary physique goal",
            "Weak points or physique priorities",
            "updateAthleteProfile",
            "athleteProfile",
            "Apply today",
            "addPlannedExercise",
            "applyPlanDayToToday",
            "Health Connect",
            "Connect health data",
            "Sync today",
            "Body fat",
            "Resting HR",
            "healthPermissions",
            "syncHealthData",
            "IfbbProTheme",
            "AppleInspiredLightColors",
            "Scaffold",
            "NavigationBar",
            "NavigationBarItem",
            "Command Center",
            "Daily Coach Checklist",
            "DailyCoachTask",
            "dailyCoachTasks",
            "DailyCoachChecklistCard",
            "Plan prepared",
            "Training executed",
            "Food logged",
            "Metrics synced",
            "AI review locked",
            "Run review",
            "View review",
            "Beginner Friendly Flow",
            "Readiness",
            "Today Snapshot",
            "dailyReadiness",
            "recentLogs",
            "7-Day Trend",
            "TrendOverviewCard",
            "reviewHistory",
            "Saved AI Reviews",
            "Latest AI guidance",
            "ReviewHistoryCard",
            "Xiaomi/Mi Fitness -> Health Connect",
            "Huawei Health -> Health Connect or Health Kit",
            "Scale/watch/phone -> Health Connect",
            "Manual fallback",
            "ExerciseVisualGuide",
            "ExerciseVisualGuideLibrary",
            "ExerciseVisualGuideSample",
            "ExerciseVisualType",
            "drawExerciseVisual",
            "Exercise visual guide",
            "统一动作图例",
            "Visual guide ID",
            "Unified instance diagram",
            "Action path cue",
            "Beginner recognition cue",
            "Equipment markers",
            "Exercise Visual Library",
            "ExerciseVisualRecognitionPreview",
            "Live equipment/action preview",
            "Chinese equipment labels",
            "visualId",
            "figureTitle",
            "actionPathCue",
            "beginnerCue",
            "equipmentMarkers",
            "VG-01",
            "VG-10",
            "Instance diagram cue",
            "Common movement examples",
            "Equipment photo recognition",
            "Exercise name -> visual category",
            "equipmentZh",
            "instanceCue",
            "commonMovements",
            "史密斯机",
            "哑铃",
            "Smith machine",
            "Cable station",
            "Dumbbells",
            "Barbell",
            "Machine",
            "Bodyweight or open station",
            "Adjustable bench",
            "Pull-up/Dip station",
            "Resistance band",
            "Leg press or hack squat",
            "Example: ${spec.example}",
            "Look for two rails and a fixed bar",
            "Look for a cable, pulley, and handle",
            "Look for a flat or adjustable bench",
            "Look for an overhead bar, dip handles, or assist platform",
            "Look for a band anchored to a rack, door, or post",
            "Look for a sled platform, seat, and safety handles",
            "equipment/action instance diagrams",
            "Chinese equipment labels",
            "Instance diagram cue",
            "Common movement examples",
            "Dumbbell Row",
            "Barbell Squat",
            "Machine Chest Press",
            "Incline Dumbbell Press",
            "Band Face Pull",
            "Leg Press",
            "NutritionPacing",
            "Nutrition Pacing",
            "NextMealBuilder",
            "Next Meal Builder",
            "NextMealBuilderCard",
            "nextMealBuilder",
            "Next meal macro targets",
            "Meal timing cue",
            "Portion uncertainty cue",
            "MealTemplate",
            "Meal Templates",
            "MealTemplateLibrary",
            "MealTemplateCard",
            "mealTemplates",
            "Lean Protein Bowl",
            "Pre-Workout Carbs",
            "Low-Fat Protein Fix",
            "Salmon Potato Plate",
            "Fiber + Micronutrient Add",
            "BodyCompositionGuidance",
            "Body Composition Guidance",
            "BodyCompositionCard",
            "bodyCompositionGuidance",
            "Small calorie increase",
            "Small calorie decrease",
            "Hold targets",
            "Need trend data",
            "Kcal adjust",
            "Weight trend",
            "RecoveryGuidance",
            "Recovery Guidance",
            "RecoveryGuidanceCard",
            "recoveryGuidance",
            "Training action",
            "Sleep signal",
            "HR signal",
            "Controlled push",
            "Hold training stress",
            "Reduce volume",
            "Deload check",
            "TrainingReadinessBuilder",
            "TrainingReadinessBuilderCard",
            "trainingReadinessBuilder",
            "Training Readiness Builder",
            "Warm-up cue",
            "Ramp-up cue",
            "First working set",
            "Volume adjustment",
            "Stop rule",
            "Recovery gate",
            "warmUpCue",
            "rampUpCue",
            "firstWorkingSetCue",
            "volumeAdjustmentCue",
            "stopRule",
            "nextMealFocus",
            "Next meal focus",
            "formatRemaining",
            "$unit left",
            "over",
            "adherenceScore",
            "ProgressionCue",
            "Progression Cue",
            "ExerciseHistorySummary",
            "ExerciseHistoryCard",
            "Exercise History",
            "exerciseHistorySummary",
            "First tracked session",
            "Volume up",
            "Volume down",
            "Load PR",
            "Rep PR",
            "Matched last time",
            "Last volume",
            "Today volume",
            "Best load",
            "Best reps",
            "Avg RIR",
            "Add reps first",
            "Add load next time",
            "Hold load",
            "Modify or hold",
            "Finish baseline",
            "hasPainSignal",
            "no pain",
        ]
        activity = (APP / "app/src/main/java/com/iwanttobeanifbbpro/app/MainActivity.kt").read_text(encoding="utf-8")
        theme = (APP / "app/src/main/java/com/iwanttobeanifbbpro/app/ui/AppTheme.kt").read_text(encoding="utf-8")
        progression = (APP / "app/src/main/java/com/iwanttobeanifbbpro/app/core/ProgressionAdvisor.kt").read_text(
            encoding="utf-8"
        )
        history = (APP / "app/src/main/java/com/iwanttobeanifbbpro/app/core/ExerciseHistoryAdvisor.kt").read_text(
            encoding="utf-8"
        )
        body_composition = (
            APP / "app/src/main/java/com/iwanttobeanifbbpro/app/core/BodyCompositionAdvisor.kt"
        ).read_text(encoding="utf-8")
        recovery = (APP / "app/src/main/java/com/iwanttobeanifbbpro/app/core/RecoveryAdvisor.kt").read_text(
            encoding="utf-8"
        )
        training_readiness = (
            APP / "app/src/main/java/com/iwanttobeanifbbpro/app/core/TrainingReadinessAdvisor.kt"
        ).read_text(encoding="utf-8")
        visual = (APP / "app/src/main/java/com/iwanttobeanifbbpro/app/core/ExerciseVisualGuide.kt").read_text(
            encoding="utf-8"
        )
        combined = f"{ui}\n{view_model}\n{plan_model}\n{log_model}\n{activity}\n{theme}\n{progression}\n{history}\n{body_composition}\n{recovery}\n{training_readiness}\n{visual}"
        for term in expected_terms:
            with self.subTest(term=term):
                self.assertIn(term, combined)

    def test_health_connect_integration_is_structured_and_permissioned(self) -> None:
        gradle = (APP / "app/build.gradle.kts").read_text(encoding="utf-8")
        manifest = (APP / "app/src/main/AndroidManifest.xml").read_text(encoding="utf-8")
        repository = (
            APP / "app/src/main/java/com/iwanttobeanifbbpro/app/health/HealthConnectRepository.kt"
        ).read_text(encoding="utf-8")
        snapshot = (APP / "app/src/main/java/com/iwanttobeanifbbpro/app/health/HealthSnapshot.kt").read_text(
            encoding="utf-8"
        )
        expected_terms = [
            "androidx.health.connect:connect-client",
            "android.permission.health.READ_WEIGHT",
            "android.permission.health.READ_BODY_FAT",
            "android.permission.health.READ_STEPS",
            "android.permission.health.READ_SLEEP",
            "android.permission.health.READ_RESTING_HEART_RATE",
            "com.google.android.apps.healthdata",
            "android.permission.START_VIEW_PERMISSION_USAGE",
            "android.intent.action.VIEW_PERMISSION_USAGE",
            "android.intent.category.HEALTH_PERMISSIONS",
            "HealthConnectClient",
            "import androidx.health.connect.client.PermissionController",
            "PermissionController.createRequestPermissionResultContract",
            "HealthPermission.getReadPermission",
            "WeightRecord",
            "BodyFatRecord",
            "LeanBodyMassRecord",
            "StepsRecord",
            "SleepSessionRecord",
            "RestingHeartRateRecord",
            "TotalCaloriesBurnedRecord",
            "AggregateRequest",
            "ReadRecordsRequest",
            "HealthSnapshot",
            "hasImportableMetrics",
            "Xiaomi",
            "Huawei",
            "source app writes compatible records into Health Connect",
        ]
        combined = f"{gradle}\n{manifest}\n{repository}\n{snapshot}"
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
            "weekly training plan",
            "Plan Templates",
            "Beginner Full Body",
            "4-Day Hypertrophy",
            "5-Day Physique Priority",
            "Apply today",
            "Health Connect",
            "Xiaomi",
            "Mi Fitness",
            "Huawei",
            "body fat",
            "resting heart rate",
            "Huawei Health Kit",
            "Athlete Profile",
            "target weight/body fat",
            "weekly training days",
            "manual entry",
            "unified exercise visual guide",
            "Exercise Visual Library",
            "Smith machine",
            "cable station",
            "dumbbells",
            "barbell",
            "machine",
            "adjustable bench",
            "pull-up/dip station",
            "resistance band",
            "leg press or hack squat",
            "bodyweight/open-station",
            "Chinese equipment labels",
            "instance diagram cues",
            "visual guide IDs",
            "unified instance diagrams",
            "action path cues",
            "beginner recognition cues",
            "equipment markers",
            "common movement examples",
            "live equipment/action preview",
            "example movement",
            "look-for cue",
            "Nutrition Pacing",
            "Next Meal Builder",
            "next practical meal target",
            "meal timing cue",
            "portion uncertainty cue",
            "Meal Templates",
            "Lean Protein Bowl",
            "Pre-Workout Carbs",
            "Low-Fat Protein Fix",
            "remaining or over target",
            "macro adherence score",
            "next-meal focus",
            "Body Composition Guidance",
            "small calorie adjustment",
            "Recovery Guidance",
            "training-pressure guidance",
            "Training Readiness Builder",
            "warm-up strategy",
            "first working set",
            "volume adjustment",
            "stop rule",
            "reduce volume",
            "deload check",
            "Progression Cue",
            "Exercise History",
            "previous/current volume",
            "best load",
            "best reps",
            "average RIR",
            "add reps",
            "add load",
            "hold",
            "modify",
        ]
        for term in expected_terms:
            with self.subTest(term=term):
                self.assertIn(term, readme)

    def test_static_ui_preview_covers_core_app_workflows(self) -> None:
        html_path = APP / "preview/index.html"
        readme_path = APP / "preview/README.md"
        self.assertTrue(html_path.exists(), "preview/index.html")
        self.assertTrue(readme_path.exists(), "preview/README.md")
        html = html_path.read_text(encoding="utf-8")
        readme = readme_path.read_text(encoding="utf-8")
        expected_terms = [
            "Live design preview",
            "Apple",
            "Today",
            "Command Center",
            "Athlete Profile",
            "Plan Templates",
            "Beginner Full Body",
            "4-Day Hypertrophy",
            "5-Day Physique Priority",
            "Weekly Plan",
            "Training Execution",
            "Training Readiness Builder",
            "Warm-up cue",
            "Ramp-up cue",
            "First working set",
            "Volume adjustment",
            "Stop rule",
            "Rest timer",
            "Nutrition",
            "Meal Templates",
            "Lean Protein Bowl",
            "Pre-Workout Carbs",
            "Low-Fat Protein Fix",
            "Health Connect",
            "Xiaomi/Mi Fitness -> Health Connect",
            "Huawei Health -> Health Connect or Health Kit",
            "AI Coach",
            "AI Data Map",
            "Saved AI Reviews",
            "Daily Coach Checklist",
            "Plan prepared",
            "Training executed",
            "Food logged",
            "Metrics synced",
            "AI review locked",
            "Exercise visual guide",
            "Exercise Visual Library",
            "Live equipment/action preview",
            "Chinese equipment labels",
            "Visual guide ID",
            "Unified instance diagram",
            "Action path cue",
            "Beginner recognition cue",
            "Equipment markers",
            "Instance diagram cue",
            "Common movement examples",
            "Equipment photo recognition",
            "Exercise name -> visual category",
            "VG-01",
            "VG-10",
            "Smith machine",
            "Cable station",
            "Dumbbells",
            "Barbell",
            "Machine",
            "Adjustable bench",
            "Pull-up/Dip station",
            "Resistance band",
            "Leg press or hack squat",
            "Bodyweight or open station",
            "Guided press path",
            "Pulley resistance",
            "Example: Incline Smith Press",
            "史密斯机",
            "哑铃",
            "Smith Squat",
            "Dumbbell Press",
            "Look for two rails and a fixed bar",
            "Unified exercise visual guide",
            "equipment/action instance diagrams",
            "Nutrition Pacing",
            "Next Meal Builder",
            "Next meal macro targets",
            "Meal timing cue",
            "Portion uncertainty cue",
            "Meal Templates",
            "Body Composition Guidance",
            "Recovery Guidance",
            "Training Readiness Builder",
            "Warm-up cue",
            "Ramp-up cue",
            "First working set",
            "Volume adjustment",
            "Stop rule",
            "Training action",
            "Sleep signal",
            "HR signal",
            "Hold targets",
            "Kcal adjust",
            "Weight trend",
            "Next meal focus",
            "Nutrition totals + pacing",
            "Body Composition Guidance",
            "Protein behind",
            "Progression Cue",
            "Exercise History",
            "Add reps first",
            "Last volume",
            "Today volume",
            "Best load",
            "Best reps",
            "Avg RIR",
            "previous/current volume",
            "data-tab",
            "complete-set",
            "setInterval",
            "Open `index.html`",
        ]
        combined = f"{html}\n{readme}"
        for term in expected_terms:
            with self.subTest(term=term):
                self.assertIn(term, combined)

    def test_skill_assets_define_exercise_visual_guide_categories(self) -> None:
        skill_library = (ROOT / "skills/i-want-to-be-an-ifbb-pro/references/exercise-library.md").read_text(
            encoding="utf-8"
        )
        app_library = (APP / "app/src/main/assets/skill/references/exercise-library.md").read_text(
            encoding="utf-8"
        )
        expected_terms = [
            "Exercise Visual Guide",
            "Chinese equipment labels",
            "simple instance diagrams",
            "visual IDs",
            "VG-01",
            "VG-10",
            "Unified instance diagram",
            "Action path cue",
            "Beginner cue",
            "Equipment markers",
            "instance diagram cue",
            "common movements",
            "Smith machine",
            "史密斯机",
            "Cable station",
            "绳索龙门架/滑轮器械",
            "Dumbbells",
            "哑铃",
            "Barbell",
            "杠铃",
            "Machine",
            "固定轨迹器械",
            "Bodyweight or open station",
            "自重/开放训练空间",
            "guided incline press path",
            "align bench under the fixed bar path",
            "example movement",
            "look-for cue",
            "Common movements",
            "Look for two rails and a fixed bar",
            "Adjustable bench",
            "可调训练凳",
            "Pull-up/Dip station",
            "引体向上/双杠训练站",
            "Resistance band",
            "弹力带",
            "Leg press or hack squat",
            "腿举/哈克深蹲器械",
            "Look for a flat or adjustable bench",
            "Look for an overhead bar, dip handles, or assist platform",
            "Look for a band anchored to a rack, door, or post",
            "Look for a sled platform, seat, and safety handles",
        ]
        combined = f"{skill_library}\n{app_library}"
        for term in expected_terms:
            with self.subTest(term=term):
                self.assertIn(term, combined)


if __name__ == "__main__":
    unittest.main()
