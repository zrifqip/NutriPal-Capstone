package com.submission.nutripal.ui.auth

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.submission.nutripal.data.LoginBody
import com.submission.nutripal.data.LoginResponse
import com.submission.nutripal.data.LoginResult
import com.submission.nutripal.data.PreferenceManager
import com.submission.nutripal.data.UiState
import com.submission.nutripal.data.User
import com.submission.nutripal.data.UserRepository
import com.submission.nutripal.data.UserResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val userRepository: UserRepository,private val preferenceManager: PreferenceManager) : ViewModel() {
    val uiState = MutableLiveData<UiState<LoginResponse>>()

    fun checkLogin() : Boolean{
        return preferenceManager.getLoginResult().token != ""
    }

    fun loginUser(loginBody: LoginBody) {
        uiState.value = UiState.Loading
        viewModelScope.launch {
            uiState.value = UiState.Loading
            try {
                val result = userRepository.loginUser(loginBody.email, loginBody.password)
                preferenceManager.saveLoginResult(result.result)
                Log.d("LoginViewModel", "loginUser: ${result.result}")
                Log.d("LoginViewModel", "loginUser: ${preferenceManager.getLoginResult()}")
                uiState.value = UiState.Success(result)
            } catch (e: Exception) {
                uiState.value = UiState.Error(e.message.toString())
                Log.d("LoginViewModel", "loginUser: ${e.message}")
            }
        }
    }
}