import unittest
from pathlib import Path


ROOT = Path(__file__).resolve().parents[1]
SKILL = ROOT / "skills" / "i-want-to-be-an-ifbb-pro"


def read(relative: str) -> str:
    return (SKILL / relative).read_text(encoding="utf-8").lower()


class SkillCompletenessTest(unittest.TestCase):
    def test_chinese_readme_covers_core_usage(self) -> None:
        path = ROOT / "README.zh-CN.md"
        self.assertTrue(path.exists(), "README.zh-CN.md")
        text = path.read_text(encoding="utf-8")
        expected_terms = [
            "I Want to be an IFBB PRO",
            "$i-want-to-be-an-ifbb-pro",
            "专业级个性化体型训练规划",
            "抽象的目标形象",
            "不是默认要求使用者真的去拿职业卡",
            "创建新的训练计划",
            "优化现有训练计划",
            "训练质量",
            "饮食",
            "恢复",
            "伤病风险控制",
            "IFBB Pro",
            "session-log.csv",
        ]
        for term in expected_terms:
            with self.subTest(term=term):
                self.assertIn(term, text)

    def test_branding_uses_aspirational_ifbb_pro_positioning(self) -> None:
        readme = (ROOT / "README.md").read_text(encoding="utf-8")
        skill = (SKILL / "SKILL.md").read_text(encoding="utf-8")
        combined = f"{readme}\n{skill}"
        expected_terms = [
            "I Want to be an IFBB PRO",
            "aspirational image",
            "not a promise or default plan to earn an actual pro card",
            "not necessarily to earn a real pro card",
            "not an official IFBB Pro League, NPC, or NPC Worldwide resource",
        ]
        for term in expected_terms:
            with self.subTest(term=term):
                self.assertIn(term, combined)

    def test_required_expert_references_exist_and_are_linked_from_skill(self) -> None:
        skill_text = read("SKILL.md")
        required = [
            "references/intake-assessment.md",
            "references/anatomy-and-movement.md",
            "references/goal-decision-system.md",
            "references/phase-templates.md",
            "references/adaptation-playbook.md",
            "references/plan-optimization.md",
            "references/session-execution-and-volume.md",
            "references/pro-level-physique-roadmap.md",
            "references/contest-prep-and-posing.md",
            "references/model-adaptation.md",
        ]
        for relative in required:
            with self.subTest(relative=relative):
                self.assertTrue((SKILL / relative).exists(), relative)
                self.assertIn(relative, skill_text)

    def test_templates_cover_intake_checkin_and_plan_outputs(self) -> None:
        required = [
            "assets/templates/intake-form.md",
            "assets/templates/check-in-form.md",
            "assets/templates/plan-template.md",
            "assets/templates/session-log.csv",
            "assets/templates/tracking-log.csv",
        ]
        for relative in required:
            with self.subTest(relative=relative):
                path = SKILL / relative
                self.assertTrue(path.exists(), relative)
                text = path.read_text(encoding="utf-8").lower()
                self.assertTrue("safety" in text or "red flag" in text or "pain" in text)
                self.assertIn("goal", text)

    def test_training_references_cover_major_professional_dimensions(self) -> None:
        combined = "\n".join(
            (SKILL / "references" / name).read_text(encoding="utf-8").lower()
            for name in [
                "training-programming.md",
                "exercise-library.md",
                "anatomy-and-movement.md",
                "phase-templates.md",
                "adaptation-playbook.md",
            ]
        )
        expected_terms = [
            "mesocycle",
            "rir",
            "movement pattern",
            "hypertrophy",
            "fat loss",
            "recomposition",
            "deload",
            "weak point",
            "joint",
            "substitution",
        ]
        for term in expected_terms:
            with self.subTest(term=term):
                self.assertIn(term, combined)

    def test_scripts_include_target_estimation_and_checkin_analysis(self) -> None:
        scripts = SKILL / "scripts"
        self.assertTrue((scripts / "estimate_targets.py").exists())
        self.assertTrue((scripts / "analyze_checkin.py").exists())
        self.assertTrue((scripts / "analyze_training_volume.py").exists())

    def test_execution_and_volume_references_cover_plan_optimization(self) -> None:
        combined = "\n".join(
            (SKILL / "references" / name).read_text(encoding="utf-8").lower()
            for name in [
                "plan-optimization.md",
                "session-execution-and-volume.md",
                "data-tracking-adjustment.md",
                "adaptation-playbook.md",
            ]
        )
        expected_terms = [
            "existing plan",
            "audit",
            "session log",
            "hard sets",
            "tonnage",
            "effective reps",
            "volume landmarks",
            "training quality",
            "progression decision",
            "exercise-level",
        ]
        for term in expected_terms:
            with self.subTest(term=term):
                self.assertIn(term, combined)

    def test_pro_card_references_cover_competitive_bodybuilding_goal(self) -> None:
        combined = "\n".join(
            (SKILL / "references" / name).read_text(encoding="utf-8").lower()
            for name in [
                "pro-level-physique-roadmap.md",
                "contest-prep-and-posing.md",
                "safety-screening.md",
                "nutrition-body-composition.md",
            ]
        )
        expected_terms = [
            "ifbb pro",
            "pro-level physique",
            "npc",
            "division",
            "improvement season",
            "contest prep",
            "posing",
            "stage conditioning",
            "peak week",
            "official rules",
            "drug",
        ]
        for term in expected_terms:
            with self.subTest(term=term):
                self.assertIn(term, combined)


if __name__ == "__main__":
    unittest.main()
