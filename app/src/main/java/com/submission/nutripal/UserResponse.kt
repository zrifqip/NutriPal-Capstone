package com.submission.submissionintermediate.data

import com.google.gson.annotations.SerializedName

data class UserResponse (
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("loginResult")
    val loginResult: loginResult

)
data class loginResult(
    @SerializedName("userID")
    val userID: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("token")
    val token: String
)


