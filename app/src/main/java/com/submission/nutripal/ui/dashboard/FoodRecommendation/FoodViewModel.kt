package com.submission.nutripal.ui.dashboard.FoodRecommendation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.submission.nutripal.data.FoodRepository
import com.submission.nutripal.data.FoodResponse
import com.submission.nutripal.data.LoginResponse
import com.submission.nutripal.data.PreferenceManager
import com.submission.nutripal.data.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodViewModel @Inject constructor(private val foodRepository: FoodRepository,private val preferenceManager: PreferenceManager) : ViewModel(){
    val uiState = MutableLiveData<UiState<FoodResponse>>()

    private val _foodData = MutableLiveData<FoodResponse>()
    val foodData: LiveData<FoodResponse> get() = _foodData

    init {
        postAndFetchFoodData()
    }

    private fun postAndFetchFoodData() = viewModelScope.launch {
        postFood()
        fetchFoodData()
    }

    private fun postFood() = viewModelScope.launch {
        uiState.value = UiState.Loading
        Log.d("FoodViewModel", "postFood: ${preferenceManager.getSurveyResult()}")
        try {
            val result = foodRepository.postFood(
                preferenceManager.getSurveyResult().idSurvei,
                preferenceManager.getLoginResult().token,
                preferenceManager.getSurveyResult().calorieTarget
            )
            uiState.value = UiState.Success(result)
        } catch (e: Exception) {
            uiState.value = UiState.Error(e.message.toString())
        }
    }

    private fun fetchFoodData() = viewModelScope.launch {
        val result = foodRepository.getFood(
            preferenceManager.getSurveyResult().idHasilSurvei,
            preferenceManager.getLoginResult().token,
        )
        _foodData.postValue(result)
    }

}