package com.iwanttobeanifbbpro.app.data

import android.content.Context
import org.json.JSONObject

class TrainingPlanStore(context: Context) {
    private val prefs = context.getSharedPreferences("training_plan", Context.MODE_PRIVATE)

    fun readPlan(): WeeklyTrainingPlan {
        val raw = prefs.getString("weekly_plan", null) ?: return WeeklyTrainingPlan()
        return runCatching { WeeklyTrainingPlan.fromJson(JSONObject(raw)) }.getOrElse { WeeklyTrainingPlan() }
    }

    fun savePlan(plan: WeeklyTrainingPlan) {
        prefs.edit()
            .putString("weekly_plan", plan.toJson().toString())
            .apply()
    }

    fun resetPlan() {
        savePlan(WeeklyTrainingPlan())
    }
}
