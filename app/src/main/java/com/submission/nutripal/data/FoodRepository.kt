package com.submission.nutripal.data

import android.util.Log
import com.submission.nutripal.api.FoodApiService
import com.submission.nutripal.api.RecommendationApiService
import com.submission.nutripal.api.SurveyApiService
import javax.inject.Inject

class FoodRepository @Inject constructor(private val foodApiService: FoodApiService,
                                         private val recommendationApiService: RecommendationApiService ) {
    suspend fun postFood(id :Int,token : String, calorie : Int): FoodResponse {
        val calorieData = hashMapOf("calorie" to calorie)
        return foodApiService.postCalorie(id,token,calorieData)
    }
    suspend fun getFood(id :Int,token : String): FoodResponse {
        return recommendationApiService.getFood(id,token)
    }

}