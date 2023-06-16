package com.submission.nutripal.api

import com.submission.nutripal.data.LoginResponse
import com.submission.nutripal.data.SurveyResponse
import com.submission.nutripal.data.UserResponse
import retrofit2.http.*

interface ApiService{
    @FormUrlEncoded
    @POST("/auth/signup")
        suspend fun registerUser(@Field("email") email: String,
                                 @Field("password") password: String,
                                 @Field("name") name: String,
                                 @Field("confirmationPassword") confirmationPassword: String):  UserResponse
    @FormUrlEncoded
    @POST("/auth/login")
        suspend fun postLogin(@Field("email") email: String,
                  @Field("password") password: String): LoginResponse

}