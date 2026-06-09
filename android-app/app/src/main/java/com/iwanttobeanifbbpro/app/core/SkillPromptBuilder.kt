package com.iwanttobeanifbbpro.app.core

enum class CoachMode(val title: String, val defaultPrompt: String) {
    NEW_PLAN(
        "New plan",
        "Create a safe 12-week professional-level physique plan. Ask for missing safety and intake data first."
    ),
    CHECK_IN(
        "Check-in",
        "Analyze my latest check-in, trends, adherence, recovery, and training quality. Recommend the smallest useful adjustment."
    ),
    TRAINING_VISUAL(
        "Training visual",
        "Analyze the attached exercise photos/video frames for equipment identification, exercise form analysis, target muscle stimulus, pain flags, and session-log changes. Match the visible setup to the app's unified exercise visual guide ID when possible."
    ),
    FOOD_VISUAL(
        "Food visual",
        "Analyze the attached food photo, label, or menu. Estimate calories/macros as a range, explain portion-size uncertainty, and compare it with my nutrition target."
    ),
    LINKED_TRAINING_NUTRITION(
        "Linked training + nutrition",
        "Review the attached training visuals and food photos together. Decide whether training execution, volume, calories, carbs, or recovery should change."
    ),
    EXISTING_PLAN_AUDIT(
        "Existing plan audit",
        "Audit my current plan for weekly hard sets, exercise order, progression rules, weak-point coverage, fatigue, and recovery limits."
    ),
    EVIDENCE_EXPLAINER(
        "Evidence",
        "Explain what research supports the recommendation, including limits and uncertainty."
    )
}

class SkillPromptBuilder(private val repository: SkillAssetRepository) {
    fun buildInstructions(mode: CoachMode): String {
        val references = referencesFor(mode)
        val skill = repository.read("SKILL.md")
        val referenceText = repository.readMany(references)
        return """
            You are the Android app runtime for the local skill "${'$'}i-want-to-be-an-ifbb-pro".
            Follow SKILL.md as the controlling workflow. Use the bundled references below as retrieval context.
            Do not diagnose medical conditions, prescribe drug protocols, or imply official IFBB/NPC affiliation.
            When visual inputs are present, preserve uncertainty and link training and nutrition evidence before changing the plan.

            # SKILL.md

            $skill

            # Retrieved References

            $referenceText
        """.trimIndent()
    }

    fun buildUserPrompt(mode: CoachMode, userText: String): String {
        return """
            Mode: ${mode.name} (${mode.title})

            User request:
            ${userText.ifBlank { mode.defaultPrompt }}

            Required response shape:
            - Briefly list assumptions and missing safety-critical data.
            - Give practical recommendations tied to this mode.
            - If photos are attached, separate visual observations from uncertain inferences.
            - Use Photo Evidence metadata first: identify whether each image is a food/label photo, exercise form frame, equipment photo, physique progress photo, menu/nutrition label, or other check-in photo before interpreting it.
            - For exercise names or equipment photos, map the likely setup to the unified exercise visual guide ID, category, Chinese equipment label, unified instance diagram, quick visual cue, find-equipment cue, movement path cue, action path cue, beginner recognition cue, equipment markers, example movement, and look-for cue.
            - When equipment is unavailable, crowded, painful, or technically unstable, use Exercise Substitution Coach rules: preserve same target muscle, same movement pattern, planned rep range, planned RIR, fatigue cost, and visual guide ID continuity before changing the plan.
            - Before changing tomorrow's plan, use Training Closeout Coach as the final review gate: completed sets, missing set logs, pain/technique flags, form/equipment photo evidence, post-workout nutrition cue, metrics sync cue, closeout score, and AI review readiness.
            - Include an action-oriented closeout that can feed the app's AI Review Action Queue: training action, nutrition action, recovery action, tracking action, and plan action when plan-wide structure should be reviewed.
            - Label whether each action should open Training, Nutrition, Metrics, Plan, or AI Coach, and mention the evidence cue that justifies it.
            - For linked training/nutrition analysis, compare training execution, session quality, food-photo nutrition estimate, recovery, and current goal before changing variables.
            - End with what to track before the next check-in.
        """.trimIndent()
    }

    private fun referencesFor(mode: CoachMode): List<String> {
        val shared = listOf(
            "references/safety-screening.md",
            "references/intake-assessment.md",
            "references/evidence-map.md",
            "references/adaptation-playbook.md",
            "references/model-adaptation.md"
        )
        return when (mode) {
            CoachMode.NEW_PLAN -> shared + listOf(
                "references/pro-level-physique-roadmap.md",
                "references/training-programming.md",
                "references/phase-templates.md",
                "references/nutrition-body-composition.md",
                "references/recovery-injury-risk.md",
                "references/data-tracking-adjustment.md"
            )
            CoachMode.CHECK_IN -> shared + listOf(
                "references/data-tracking-adjustment.md",
                "references/session-execution-and-volume.md",
                "references/nutrition-body-composition.md",
                "references/recovery-injury-risk.md"
            )
            CoachMode.TRAINING_VISUAL -> shared + listOf(
                "references/visual-analysis-and-food-estimation.md",
                "references/exercise-library.md",
                "references/anatomy-and-movement.md",
                "references/session-execution-and-volume.md",
                "references/recovery-injury-risk.md"
            )
            CoachMode.FOOD_VISUAL -> shared + listOf(
                "references/visual-analysis-and-food-estimation.md",
                "references/nutrition-body-composition.md",
                "references/data-tracking-adjustment.md"
            )
            CoachMode.LINKED_TRAINING_NUTRITION -> shared + listOf(
                "references/visual-analysis-and-food-estimation.md",
                "references/session-execution-and-volume.md",
                "references/nutrition-body-composition.md",
                "references/data-tracking-adjustment.md",
                "references/recovery-injury-risk.md"
            )
            CoachMode.EXISTING_PLAN_AUDIT -> shared + listOf(
                "references/plan-optimization.md",
                "references/training-programming.md",
                "references/exercise-library.md",
                "references/session-execution-and-volume.md"
            )
            CoachMode.EVIDENCE_EXPLAINER -> listOf(
                "references/evidence-map.md",
                "references/sources.md",
                "references/model-adaptation.md"
            )
        }
    }
}
