package com.submission.submissionintermediate.api
import com.submission.submissionintermediate.data.*
import retrofit2.Call
import okhttp3.MultipartBody
import okhttp3.RequestBody

import retrofit2.http.*

interface Api{
    @FormUrlEncoded
    @POST("register")
    fun postRegister(@Field("email") email: String,
                     @Field("name") name: String,
                     @Field("password") password: String): Call<UserResponse>
    @FormUrlEncoded
    @POST("login")
    fun postLogin(@Field("email") email: String,
                  @Field("password") password: String): Call<UserResponse>


}