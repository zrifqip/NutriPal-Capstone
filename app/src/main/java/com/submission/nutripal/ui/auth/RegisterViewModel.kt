package com.submission.nutripal.ui.auth

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.submission.nutripal.data.UiState
import com.submission.nutripal.data.User
import com.submission.nutripal.data.UserRepository
import com.submission.nutripal.data.UserResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay

import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class RegisterViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {
    val uiState = MutableLiveData<UiState<UserResponse>>()

    fun registerUser(user: User) {
        uiState.value = UiState.Loading
        viewModelScope.launch {
            uiState.value = UiState.Loading
            try {
                val result = userRepository.registerUser(user)
                uiState.value = UiState.Success(result)
                Log.d("RegisterViewModel", "registerUser: $result")
                delay(100)
                uiState.value = UiState.Idle
            } catch (e: Exception) {
                uiState.value = UiState.Error(e.message.toString())
                Log.d("RegisterViewModel", "registerUser: ${e.message}")
            }
        }
    }

}