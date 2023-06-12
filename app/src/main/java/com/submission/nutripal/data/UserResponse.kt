package com.submission.nutripal.data

import com.google.gson.annotations.SerializedName
import java.util.StringTokenizer

data class User(
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("confirmationPassword")
    val confirmationPassword: String
)

data class UserResponse(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("message")
    val message: String,
)
data class LoginResponse(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("msg")
    val message: String,
    @SerializedName("loginResult")
    val result: LoginResult
)
data class LoginResult(
    @SerializedName("id_user")
    val id_user: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("fill_survey")
    val fill_survey: Int,
    @SerializedName("last_login")
    val last_login: String,
    @SerializedName("token")
    val token : String,

)








