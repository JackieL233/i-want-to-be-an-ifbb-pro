package com.iwanttobeanifbbpro.app.data

import android.content.Context
import org.json.JSONObject

data class AthleteProfile(
    val displayName: String = "",
    val primaryGoal: String = "Build an IFBB PRO-inspired physique",
    val currentPhase: String = "Hypertrophy",
    val trainingExperience: String = "Intermediate",
    val sex: String = "",
    val age: Int? = null,
    val heightCm: Double? = null,
    val startWeightKg: Double? = null,
    val targetWeightKg: Double? = null,
    val targetBodyFatPercent: Double? = null,
    val weeklyTrainingDays: Int = 5,
    val availableEquipment: String = "Full gym",
    val dietaryPreference: String = "",
    val constraints: String = "",
    val weakPoints: String = ""
) {
    fun toJson(): JSONObject {
        return JSONObject()
            .put("displayName", displayName)
            .put("primaryGoal", primaryGoal)
            .put("currentPhase", currentPhase)
            .put("trainingExperience", trainingExperience)
            .put("sex", sex)
            .put("age", age)
            .put("heightCm", heightCm)
            .put("startWeightKg", startWeightKg)
            .put("targetWeightKg", targetWeightKg)
            .put("targetBodyFatPercent", targetBodyFatPercent)
            .put("weeklyTrainingDays", weeklyTrainingDays)
            .put("availableEquipment", availableEquipment)
            .put("dietaryPreference", dietaryPreference)
            .put("constraints", constraints)
            .put("weakPoints", weakPoints)
    }

    companion object {
        fun fromJson(json: JSONObject): AthleteProfile {
            return AthleteProfile(
                displayName = json.optString("displayName", ""),
                primaryGoal = json.safeString("primaryGoal", "Build an IFBB PRO-inspired physique"),
                currentPhase = json.safeString("currentPhase", "Hypertrophy"),
                trainingExperience = json.safeString("trainingExperience", "Intermediate"),
                sex = json.optString("sex", ""),
                age = json.nullableInt("age"),
                heightCm = json.nullableDouble("heightCm"),
                startWeightKg = json.nullableDouble("startWeightKg"),
                targetWeightKg = json.nullableDouble("targetWeightKg"),
                targetBodyFatPercent = json.nullableDouble("targetBodyFatPercent"),
                weeklyTrainingDays = json.safeInt("weeklyTrainingDays", 5),
                availableEquipment = json.safeString("availableEquipment", "Full gym"),
                dietaryPreference = json.optString("dietaryPreference", ""),
                constraints = json.optString("constraints", ""),
                weakPoints = json.optString("weakPoints", "")
            )
        }
    }
}

class AthleteProfileStore(context: Context) {
    private val prefs = context.getSharedPreferences("athlete_profile", Context.MODE_PRIVATE)

    fun readProfile(): AthleteProfile {
        val raw = prefs.getString(PROFILE_KEY, null) ?: return AthleteProfile()
        return runCatching { AthleteProfile.fromJson(JSONObject(raw)) }.getOrElse { AthleteProfile() }
    }

    fun saveProfile(profile: AthleteProfile) {
        prefs.edit()
            .putString(PROFILE_KEY, profile.toJson().toString())
            .apply()
    }

    companion object {
        private const val PROFILE_KEY = "profile"
    }
}

private fun JSONObject.nullableDouble(name: String): Double? {
    return if (has(name) && !isNull(name)) optDouble(name) else null
}

private fun JSONObject.nullableInt(name: String): Int? {
    return if (has(name) && !isNull(name)) optInt(name) else null
}

private fun JSONObject.safeInt(name: String, fallback: Int): Int {
    return if (has(name) && !isNull(name)) optInt(name, fallback) else fallback
}

private fun JSONObject.safeString(name: String, fallback: String): String {
    val value = if (has(name) && !isNull(name)) optString(name) else ""
    return value.takeIf { it.isNotBlank() } ?: fallback
}
