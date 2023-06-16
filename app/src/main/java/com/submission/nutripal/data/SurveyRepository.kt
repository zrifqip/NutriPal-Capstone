package com.submission.nutripal.data

import android.util.Log
import com.submission.nutripal.api.SurveyApiService
import javax.inject.Inject

class SurveyRepository @Inject constructor(private val apiService: SurveyApiService) {
    suspend fun postSurvey(token : String,
                           id : Int,
                           sex : String,
                           Height : Int,
                           Weight : Float,
                           Age : Int,
                           Smoking : Int,
                           activity:String,
                           alcohol : Int): SurveyResponse {
        Log.d("SurveyRepository", "postSurvey: $token $id $sex $Height $Weight $Age $Smoking $activity $alcohol")
        //get token
        return apiService.postSurvey(id,token,sex, Height, Weight, Age, activity,alcohol,Smoking)
    }
    suspend fun updateSurvey(token : String,
                             id : Int,
                             sex : String,
                             Height : Int,
                             Weight : Float,
                             Age : Int,
                             Smoking : Int,
                             activity:String,
                             alcohol : Int): SurveyResponse {
        Log.d("SurveyRepository", "updateSurvey: $token $id $sex $Height $Weight $Age $Smoking $activity $alcohol")
        //get token
        return apiService.updateSurvey(id,token,sex, Height, Weight, Age, activity,alcohol,Smoking)
    }
    suspend fun getSurvey(id:Int,token : String): SurveyResponse {
        return apiService.getSurvey(id,token)
    }
    suspend fun getSurveyData(id:Int,token : String): SurveyDataResponse {
        return apiService.getSurveyData(id,token)
    }
}