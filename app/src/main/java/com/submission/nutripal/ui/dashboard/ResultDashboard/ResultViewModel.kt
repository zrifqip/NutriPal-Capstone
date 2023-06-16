package com.submission.nutripal.ui.dashboard.ResultDashboard

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.submission.nutripal.data.PreferenceManager
import com.submission.nutripal.data.SurveyRepository
import com.submission.nutripal.data.SurveyResponse
import com.submission.nutripal.data.SurveyResult
import com.submission.nutripal.data.UiState
import com.submission.nutripal.data.User
import com.submission.nutripal.data.UserRepository
import com.submission.nutripal.data.UserResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(private val surveyRepository: SurveyRepository,private val preferenceManager: PreferenceManager) : ViewModel() {
    val uiState = MutableLiveData<UiState<SurveyResult>>()
    val id = preferenceManager.getLoginResult().id_user
    val token = preferenceManager.getLoginResult().token
    fun getSurveyResult() {
        viewModelScope.launch {
            uiState.value = UiState.Loading
            try{
                val result = surveyRepository.getSurvey(id,token)
                uiState.value = UiState.Success(result.surveyResult)
                Log.d("ResultViewModel", "getSurveyResult: $result")

            }
            catch (e: Exception){
                uiState.value = UiState.Error(e.message.toString())
                Log.d("ResultViewModel", "getSurveyResult: ${e.message}")
            }
        }
    }
    fun saveSurveyResult(surveyResult: SurveyResult){
        preferenceManager.saveSurveyResult(surveyResult)
    }

}