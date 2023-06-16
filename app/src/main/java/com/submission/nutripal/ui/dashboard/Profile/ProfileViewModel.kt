package com.submission.nutripal.ui.dashboard.Profile

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.submission.nutripal.data.PreferenceManager
import com.submission.nutripal.data.SurveyDataResponse
import com.submission.nutripal.data.SurveyRepository
import com.submission.nutripal.data.SurveyResponse
import com.submission.nutripal.data.SurveyResult
import com.submission.nutripal.data.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val surveyRepository: SurveyRepository,private val preferenceManager: PreferenceManager) : ViewModel() {
    val uiState = MutableLiveData<UiState<SurveyDataResponse>>()

    fun clearSession() {
        preferenceManager.clearLoginResult()
    }
    fun getName(): String {
        return preferenceManager.getLoginResult().name
    }
    fun getEmail(): String {
        return preferenceManager.getLoginResult().email
    }
    fun getSurveyData(){
        viewModelScope.launch {
            try{
                val result = surveyRepository.getSurveyData(preferenceManager.getLoginResult().id_user,preferenceManager.getLoginResult().token)
                uiState.value = UiState.Success(result)

            }
            catch (e: Exception){
                uiState.value = UiState.Error(e.message.toString())
            }
        }
    }


}