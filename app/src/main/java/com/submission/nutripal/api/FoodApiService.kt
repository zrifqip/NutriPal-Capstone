package com.submission.nutripal.api

import com.submission.nutripal.data.FoodResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Body
import retrofit2.http.Part

interface FoodApiService {
    @POST("/api/predict/{idHasilSurvey}")
    suspend fun postCalorie(@Path ("idHasilSurvey") idHasilSurvey : Int,
                            @Header("Authorization") token: String,
                            @Body calorieData: HashMap<String, Int>): FoodResponse
}