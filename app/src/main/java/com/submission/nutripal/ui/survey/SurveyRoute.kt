package com.submission.nutripal.ui.survey

import ActivityQuestion
import AlcoholQuestion
import BirthQuestion
import GenderQuestion
import HeightQuestion
import SmokingQuestion
import StartQuestion
import WeightQuestion
import android.content.Context
import android.content.ContextWrapper
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.material.datepicker.MaterialDatePicker

private const val CONTENT_ANIMATION_DURATION = 300


/**
 * Displays a [SurveyQuestionsScreen] tied to the passed [SurveyViewModel]
// */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SurveyRoute(
    onNavUp: () -> Unit,
) {
    val viewModel: SurveyViewModel = viewModel(
        factory = SurveyViewModelFactory()
    )

    val surveyScreenData = viewModel.surveyScreenData ?: return

    BackHandler {
        if (!viewModel.onBackPressed()) {
            onNavUp()
        }
    }

    SurveyQuestionsScreen(
        surveyScreenData = surveyScreenData,
        isNextEnabled = viewModel.isNextEnabled,
        onPreviousPressed = { viewModel.onPreviousPressed() },
        onNextPressed = { viewModel.onNextPressed() },
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
                SurveyQuestion.BIRTHDATE -> {
                    val supportFragmentManager =
                        LocalContext.current.findActivity().supportFragmentManager
                    BirthQuestion(
                        dateInMillis = viewModel.birthdateResponse,
                        onClick = {
                            showBirthDatePicker(
                                date = viewModel.birthdateResponse,
                                supportFragmentManager = supportFragmentManager,
                                onDateSelected = viewModel::onBirthdateResponse
                            )
                        },
                        modifier = modifier
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



private fun showBirthDatePicker(
    date: Long?,
    supportFragmentManager: FragmentManager,
    onDateSelected: (date: Long) -> Unit,
) {
    val picker = MaterialDatePicker.Builder.datePicker()
        .setSelection(date)
        .build()
    picker.show(supportFragmentManager, picker.toString())
    picker.addOnPositiveButtonClickListener {
        picker.selection?.let {
            onDateSelected(it)
        }
    }
}

private tailrec fun Context.findActivity(): AppCompatActivity =
    when (this) {
        is AppCompatActivity -> this
        is ContextWrapper -> this.baseContext.findActivity()
        else -> throw IllegalArgumentException("Could not find activity!")
    }
