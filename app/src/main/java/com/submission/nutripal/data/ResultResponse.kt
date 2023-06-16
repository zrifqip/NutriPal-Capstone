package com.submission.nutripal.data

data class ResultResponse (
    val error: Boolean,
    val message: String,
    val dashboardResult: DashboardResult
)
data class DashboardResult(
    val id_hasil_survei: Int,
    val id_survei: Int,
    val bmi_category: String,
    val bmi: Double,
    val calorie_target: Int,
    val ideal_weight: Double
)