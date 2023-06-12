package com.submission.nutripal.data

import com.submission.nutripal.api.ApiService


import javax.inject.Inject

class UserRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun registerUser(user: User ): UserResponse {
        return apiService.registerUser(user.email, user.password, user.name, user.confirmationPassword)
    }
    suspend fun loginUser(email: String, password: String): LoginResponse {
        return apiService.postLogin(email, password)
    }
}