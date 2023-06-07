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

import android.net.Uri
import android.preference.Preference
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

const val simpleDateFormatPattern = "EEE, MMM d, yyyy"

class SurveyViewModel(
) : ViewModel() {

    private val questionOrder: List<SurveyQuestion> = listOf(
        SurveyQuestion.GENDER,
        SurveyQuestion.BIRTHDATE,
        SurveyQuestion.WEIGHT,
        SurveyQuestion.HEIGHT,
        SurveyQuestion.ACTIVITY,
        SurveyQuestion.SMOKE,
        SurveyQuestion.DRINK,
    )

    private var questionIndex = 0



    private val _genderResponse = mutableStateOf<Gender?>(null)
    val genderResponse: Gender?
        get() = _genderResponse.value

    private val _birthdateResponse = mutableStateOf<Long?>(null)
    val birthdateResponse: Long?
        get() = _birthdateResponse.value
    private val _weightResponse = mutableStateOf<Int?>(null)
    val weightResponse: Int?
        get() = _weightResponse.value
    private val _heightResponse = mutableStateOf<Int?>(null)
    val heightResponse: Int?
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
    private val preference = mutableStateListOf<Int?>()
    val preferenceResponse: List<Int?>
        get() = preference


    // ----- Survey status exposed as State -----

    private val _surveyScreenData = mutableStateOf(createSurveyScreenData())
    val surveyScreenData: SurveyScreenData?
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

    fun onDonePressed(onSurveyComplete: () -> Unit) {
        // Here is where you could validate that the requirements of the survey are complete
        onSurveyComplete()
    }



    fun onTakeawayResponse(timestamp: Long) {
    }


    private fun getIsNextEnabled(): Boolean {
        return questionIndex < questionOrder.size - 1
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

class SurveyViewModelFactory(
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SurveyViewModel::class.java)) {
            return SurveyViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

enum class SurveyQuestion {
    GENDER,
    WEIGHT,
    HEIGHT,
    BIRTHDATE,
    SMOKE,
    DRINK,
    ACTIVITY,
    FOOD_PREFERENCE

}

data class SurveyScreenData(
    val questionIndex: Int,
    val questionCount: Int,
    val shouldShowPreviousButton: Boolean,
    val shouldShowDoneButton: Boolean,
    val surveyQuestion: SurveyQuestion,
)
