package com.submission.nutripal.data

import com.google.gson.annotations.SerializedName

data class SurveyResponse(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("dashboardResult")
    val surveyResult: SurveyResult
)
data class SurveyResult(
    @SerializedName("id_hasil_survei")
    val idHasilSurvei: Int,
    @SerializedName("id_survei")
    val idSurvei: Int,
    @SerializedName("id_user")
    val idUser: Int,
    @SerializedName("bmi_category")
    val bmiCategory: String,
    @SerializedName("bmi")
    val bmi: Float,
    @SerializedName("calorie_target")
    val calorieTarget: Int,
    @SerializedName("ideal_weight")
    val idealWeight: Float,
)

