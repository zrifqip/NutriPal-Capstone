/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.submission.nutripal.ui.survey

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.submission.nutripal.data.LoginResponse
import com.submission.nutripal.data.LoginResult
import com.submission.nutripal.data.PreferenceManager
import com.submission.nutripal.data.SurveyRepository
import com.submission.nutripal.data.SurveyResponse
import com.submission.nutripal.data.SurveyResult
import com.submission.nutripal.data.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SurveyViewModel @Inject constructor(
    private val surveyRepository: SurveyRepository,
    private val preferenceManager: PreferenceManager,
    @ApplicationContext private val context: Context
) : ViewModel() {
    val uiState = MutableLiveData<UiState<SurveyResponse>>()
    private val questionOrder: List<SurveyQuestion> = listOf(
        SurveyQuestion.STARTER,
        SurveyQuestion.GENDER,
        SurveyQuestion.WEIGHT,
        SurveyQuestion.AGE,
        SurveyQuestion.HEIGHT,
        SurveyQuestion.ACTIVITY,
        SurveyQuestion.SMOKE,
        SurveyQuestion.DRINK,
    )

    private var questionIndex = 0



    private val _genderResponse = mutableStateOf<Gender?>(null)
    val genderResponse: Gender?
        get() = _genderResponse.value

    private val _ageResponse = mutableStateOf("")
    val ageResponse: String
        get() = _ageResponse.value
    private val _weightResponse = mutableStateOf("")
    val weightResponse: String
        get() = _weightResponse.value
    private val _heightResponse = mutableStateOf("")
    val heightResponse: String
        get() = _heightResponse.value
    private val _activityResponse = mutableStateOf<Int?>(null)
    val activityResponse: Int?
        get() = _activityResponse.value
    private val _smokeResponse = mutableStateOf<Int?>(null)
    val smokeResponse: Int?
        get() = _smokeResponse.value
    private val _drinkResponse = mutableStateOf<Int?>(null)
    val drinkResponse: Int?
        get() = _drinkResponse.value



    // ----- Survey status exposed as State -----

    private val _surveyScreenData = mutableStateOf(createSurveyScreenData())
    val surveyScreenData: SurveyScreenData
        get() = _surveyScreenData.value

    private val _isNextEnabled = mutableStateOf(false)
    val isNextEnabled: Boolean
        get() = _isNextEnabled.value


    fun onBackPressed(): Boolean {
        if (questionIndex == 0) {
            return false
        }
        changeQuestion(questionIndex - 1)
        return true
    }

    fun onPreviousPressed() {
        if (questionIndex == 0) {
            throw IllegalStateException("onPreviousPressed when on question 0")
        }
        changeQuestion(questionIndex - 1)
    }

    fun onNextPressed() {
        changeQuestion(questionIndex + 1)
    }

    private fun changeQuestion(newQuestionIndex: Int) {
        questionIndex = newQuestionIndex
        _isNextEnabled.value = getIsNextEnabled()
        _surveyScreenData.value = createSurveyScreenData()
    }

    fun onDonePressed(onSurveyComplete: () -> Unit){

        viewModelScope.launch {
            try {
                // You will need to replace these parameters with your actual survey data
                val loginResult = preferenceManager.getLoginResult()
                val id = loginResult.id_user
                Log.d("id", id.toString())
                val sex = context.getString(_genderResponse.value?.stringResourceId ?: 0)
                val height = _heightResponse.value.toIntOrNull() ?: 0
                val weight = _weightResponse.value.toFloatOrNull() ?: 0f
                val age = _ageResponse.value.toIntOrNull() ?: 0
                val smoking = if (context.getString(_smokeResponse.value ?: 0) == "Yes") 1 else 0
                val activity = if( context.getString(_activityResponse.value ?: 0) == "Sangat Aktif")
                    "Very Active"
                else if (context.getString(_activityResponse.value ?: 0) == "Aktif")
                    "Active"
                else if (context.getString(_activityResponse.value ?: 0) == "Jarang")
                    "Moderately Active"
                else if (context.getString(_activityResponse.value ?: 0) == "Tidak Aktif")
                    "Sedentary" else TODO()
                val alcohol = if (context.getString(_drinkResponse.value ?: 0) == "Yes") 1 else 0
                val token = loginResult.token
                //log all the data
                val response = if(loginResult.survey == 0)surveyRepository.postSurvey(
                    token,id, sex, height, weight, age, smoking,activity, alcohol)
                else surveyRepository.updateSurvey(
                    token,id, sex, height, weight, age, smoking,activity, alcohol)
                uiState.value = UiState.Success(response)
                // Handle the response here if needed
                preferenceManager.saveSurveyResult(response.surveyResult)
                preferenceManager.saveSurvey()
                onSurveyComplete()
            } catch (e: Exception) {
                uiState.value = UiState.Error(e.message ?: "An unknown error occurred")
            }
        }
    }
    fun clearLoginResult() {
        preferenceManager.clearLoginResult()
    }


    fun onGenderResponse(gender: Gender) {
        _genderResponse.value = gender
        _isNextEnabled.value = getIsNextEnabled()
    }
    fun StartResponse(){
        _isNextEnabled.value = getIsNextEnabled()
    }
    fun isSurveyFilled(): Boolean {
        return preferenceManager.getLoginResult().survey != 0
    }


    fun onWeightResponse(weight: String) {
        _weightResponse.value = weight
        _isNextEnabled.value = getIsNextEnabled()
    }
    fun onHeightResponse(height: String) {
        _heightResponse.value = height
        _isNextEnabled.value = getIsNextEnabled()
    }
    fun onActivityResponse(activity: Int) {
        _activityResponse.value = activity
        _isNextEnabled.value = getIsNextEnabled()
    }
    fun onSmokeResponse(smoke: Int) {
        _smokeResponse.value = smoke
        _isNextEnabled.value = getIsNextEnabled()
    }
    fun onDrinkResponse(drink: Int) {
        _drinkResponse.value = drink
        _isNextEnabled.value = getIsNextEnabled()
    }
    fun onAgeResponse(age: String) {
        _ageResponse.value = age
        _isNextEnabled.value = getIsNextEnabled()
    }


    private fun getIsNextEnabled(): Boolean {
        return when(questionOrder[questionIndex]) {
            SurveyQuestion.STARTER -> true
            SurveyQuestion.GENDER -> genderResponse != null
            SurveyQuestion.AGE -> ageResponse != ""
            SurveyQuestion.WEIGHT -> weightResponse != ""
            SurveyQuestion.HEIGHT -> heightResponse != ""
            SurveyQuestion.ACTIVITY -> activityResponse != null
            SurveyQuestion.SMOKE -> smokeResponse != null
            SurveyQuestion.DRINK -> drinkResponse != null
        }
    }
    //get user
    suspend fun saveUser(){
        preferenceManager.saveSurveyResult(surveyRepository.getSurvey(preferenceManager.getLoginResult().id_user,preferenceManager.getLoginResult().token).surveyResult)
        //get survey result
        Log.d("survey",preferenceManager.getSurveyResult().toString())
    }
    private fun createSurveyScreenData(): SurveyScreenData {
        return SurveyScreenData(
            questionIndex = questionIndex,
            questionCount = questionOrder.size,
            shouldShowPreviousButton = questionIndex > 0,
            shouldShowDoneButton = questionIndex == questionOrder.size - 1,
            surveyQuestion = questionOrder[questionIndex],
        )
    }
}

enum class SurveyQuestion {
    STARTER,
    GENDER,
    AGE,
    WEIGHT,
    HEIGHT,
    SMOKE,
    DRINK,
    ACTIVITY,
}

data class SurveyScreenData(
    val questionIndex: Int,
    val questionCount: Int,
    val shouldShowPreviousButton: Boolean,
    val shouldShowDoneButton: Boolean,
    val surveyQuestion: SurveyQuestion,
)
