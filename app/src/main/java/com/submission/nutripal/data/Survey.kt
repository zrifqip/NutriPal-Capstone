package com.submission.nutripal.data

import com.google.gson.annotations.SerializedName

data class SurveyDataResponse(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("surveyResult")
    val SurveyData: Survey
)

data class Survey(
    @SerializedName("id_survei")
    val idHasilSurvei: Int,
    @SerializedName("id_user")
    val idSurvei: Int,
    @SerializedName("sex")
    val sex: String,
    @SerializedName("height")
    val height: Int,
    @SerializedName("weight")
    val weight: Float,
    @SerializedName("age")
    val age: Int,
    @SerializedName("activity")
    val activity: String,
    @SerializedName("alcoholism")
    val alcohol: Int,
    @SerializedName("smoker")
    val smoking: Int,

)

