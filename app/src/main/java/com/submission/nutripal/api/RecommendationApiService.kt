package com.submission.nutripal.api

import com.submission.nutripal.data.FoodResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface RecommendationApiService {

    @GET("/recommendation/{idHasilSurvey}")
    suspend fun getFood(@Path ("idHasilSurvey") idHasilSurvey : Int,
                            @Header("Authorization") token: String
                            ): FoodResponse

}