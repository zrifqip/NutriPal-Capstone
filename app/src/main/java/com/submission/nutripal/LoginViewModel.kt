package com.submission.nutripal

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.submission.submissionintermediate.api.ApiConfig
import com.submission.submissionintermediate.data.LoginBody
import com.submission.submissionintermediate.data.UserResponse

import retrofit2.Call
import retrofit2.Response

class LoginViewModel() : ViewModel() {
    private val _login = MutableLiveData<LoginBody>()
    val error = MutableLiveData("")
    val loginResult = MutableLiveData<UserResponse>()
    val login: MutableLiveData<LoginBody> = _login
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "LoginViewModel"
    }

    fun postLogin() {
        _isLoading.value = true
        val loginBody = login.value ?: return
        val client = ApiConfig.getApiService().postLogin(loginBody.email, loginBody.password)
        client.enqueue(object : retrofit2.Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                _isLoading.value = false
                when (response.code()) {
                    401 -> {
                        error.postValue("Email atau Password salah")
                    }

                    200 -> {
                        loginResult.postValue(response.body())
                        error.postValue("")
                    }

                    else -> {
                        error.postValue("ERROR ${response.code()} : ${response.message()}")
                    }
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}