package com.submission.nutripal.api

import com.submission.nutripal.data.SurveyDataResponse
import com.submission.nutripal.data.SurveyResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface SurveyApiService {
    @FormUrlEncoded
    @POST("/survey/{idUser}")
    suspend fun postSurvey(@Path("idUser") id: Int,
                           @Header("Authorization") token: String,
                           @Field("sex") sex: String,//sex as string, value either ‘Male’ or ‘Female’
                           @Field("height") height: Int,//height as int
                           @Field("weight") weight: Float,//weight as float, must be at least 6 characters
                           @Field("age") age : Int,//age as int
                           @Field("activity") activity_level: String,//activity as string, value can be one of these: 'Sedentary', 'Lightly Active', 'Moderately Active', 'Active', 'Very Active'
                           @Field("alcohol") alcohol: Int,
                           @Field("smoking") smoking: Int) : SurveyResponse
    @FormUrlEncoded
    @PUT("/survey/{idUser}")
    suspend fun updateSurvey(@Path("idUser") id: Int,
                             @Header("Authorization") token: String,
                             @Field("sex") sex: String,//sex as string, value either ‘Male’ or ‘Female’
                             @Field("height") height: Int,//height as int
                             @Field("weight") weight: Float,//weight as float, must be at least 6 characters
                             @Field("age") age : Int,//age as int
                             @Field("activity") activity_level: String,//activity as string, value can be one of these: 'Sedentary', 'Lightly Active', 'Moderately Active', 'Active', 'Very Active'
                             @Field("alcohol") alcohol: Int,
                             @Field("smoking") smoking: Int) : SurveyResponse
    //smoking as boolean, value either ‘true / 1’ or ‘false / 0’
    @GET("/dashboard/{idUser}")
    suspend fun getSurvey(@Path("idUser") id :Int,@Header("Authorization") token: String): SurveyResponse
    @GET("/survey/{idUser}")
    suspend fun getSurveyData(@Path("idUser") id :Int,@Header("Authorization") token: String): SurveyDataResponse

}
