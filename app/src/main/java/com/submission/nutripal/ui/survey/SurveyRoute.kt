package com.submission.nutripal.ui.survey

import ActivityQuestion
import AgeQuestion
import AlcoholQuestion
import GenderQuestion
import HeightQuestion
import SmokingQuestion
import StartQuestion
import WeightQuestion
import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.with
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.IntOffset
import androidx.fragment.app.FragmentManager
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.submission.nutripal.R
import com.submission.nutripal.data.UiState
import com.submission.nutripal.ui.theme.NutripalTheme

private const val CONTENT_ANIMATION_DURATION = 300



@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SurveyRoute(
    onSurveyComplete: () -> Unit,
    onNavUp: () -> Unit,
    isUpdate : Boolean,
) {
    val viewModel: SurveyViewModel = hiltViewModel()

    val surveyScreenData = viewModel.surveyScreenData ?: return
    val uiState by viewModel.uiState.observeAsState(UiState.Idle)
    Log.d("ViewModel", "SurveyRoute: ${viewModel.isSurveyFilled()}, $isUpdate")
    if (viewModel.isSurveyFilled() && !isUpdate) {

        LaunchedEffect(uiState) {
            onSurveyComplete()
        }
    }

    when (uiState) {
        is UiState.Idle -> {
            //do nothing
        }
        is UiState.Loading -> {
            CircularProgressIndicator()
        }
        is UiState.Success -> {
            LaunchedEffect(uiState) {
                onSurveyComplete()
            }
        }
        is UiState.Error -> {
            Text(text = (uiState as UiState.Error).message,color = Color.Red)
        }
        else -> {
            //do nothing
        }
    }
    BackHandler {
        //if in starter
        if (viewModel.surveyScreenData.surveyQuestion == SurveyQuestion.STARTER) {
            onNavUp()
            viewModel.clearLoginResult()
        }
        else
            viewModel.onBackPressed()
    }
    NutripalTheme {
        SurveyQuestionsScreen(
            surveyScreenData = surveyScreenData,
            isNextEnabled = viewModel.isNextEnabled,
            onPreviousPressed = { viewModel.onPreviousPressed() },
            onNextPressed = { viewModel.onNextPressed() },
            onDonePressed = { viewModel.onDonePressed(onSurveyComplete) },
        ) { paddingValues ->

            val modifier = Modifier.padding(paddingValues)
            AnimatedContent(
                targetState = surveyScreenData,
                transitionSpec = {
                    val animationSpec: TweenSpec<IntOffset> =
                        tween(CONTENT_ANIMATION_DURATION)
                    val direction = getTransitionDirection(
                        initialIndex = initialState.questionIndex,
                        targetIndex = targetState.questionIndex,
                    )
                    slideIntoContainer(
                        towards = direction,
                        animationSpec = animationSpec,
                    ) with slideOutOfContainer(
                        towards = direction,
                        animationSpec = animationSpec
                    )
                }
            ) { targetState ->

                when (targetState.surveyQuestion) {
                    SurveyQuestion.STARTER -> {
                        StartQuestion(modifier = modifier)
                        viewModel.StartResponse()
                    }

                    SurveyQuestion.GENDER -> {
                        GenderQuestion(
                            modifier = modifier,
                            selectedAnswer = viewModel.genderResponse,
                            onOptionSelected = viewModel::onGenderResponse
                        )
                    }

                    SurveyQuestion.AGE -> {
                        AgeQuestion(
                            modifier = modifier,
                            fillText = viewModel.ageResponse,
                            onValueChange = viewModel::onAgeResponse,
                        )
                    }

                    SurveyQuestion.ACTIVITY -> {
                        ActivityQuestion(
                            modifier = modifier,
                            selectedAnswer = viewModel.activityResponse,
                            onOptionSelected = viewModel::onActivityResponse
                        )
                    }

                    SurveyQuestion.HEIGHT -> {
                        HeightQuestion(
                            modifier = modifier,
                            fillText = viewModel.heightResponse,
                            onValueChange = viewModel::onHeightResponse
                        )
                    }

                    SurveyQuestion.WEIGHT -> {
                        WeightQuestion(
                            modifier = modifier,
                            fillText = viewModel.weightResponse,
                            onValueChange = viewModel::onWeightResponse
                        )
                    }

                    SurveyQuestion.DRINK -> {
                        AlcoholQuestion(
                            modifier = modifier,
                            selectedAnswer = viewModel.drinkResponse,
                            onOptionSelected = viewModel::onDrinkResponse
                        )
                    }

                    SurveyQuestion.SMOKE -> {
                        SmokingQuestion(
                            modifier = modifier,
                            selectedAnswer = viewModel.smokeResponse,
                            onOptionSelected = viewModel::onSmokeResponse
                        )
                    }
                }
            }
        }
    }
}
@OptIn(ExperimentalAnimationApi::class)
fun getTransitionDirection(
    initialIndex: Int,
    targetIndex: Int
): AnimatedContentScope.SlideDirection {
    return if (targetIndex > initialIndex) {
        // Going forwards in the survey: Set the initial offset to start
        // at the size of the content so it slides in from right to left, and
        // slides out from the left of the screen to -fullWidth
        AnimatedContentScope.SlideDirection.Left
    } else {
        // Going back to the previous question in the set, we do the same
        // transition as above, but with different offsets - the inverse of
        // above, negative fullWidth to enter, and fullWidth to exit.
        AnimatedContentScope.SlideDirection.Right
    }
}




