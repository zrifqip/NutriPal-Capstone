import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.submission.nutripal.R
import com.submission.nutripal.ui.survey.DateQuestion
import com.submission.nutripal.ui.survey.Gender
import com.submission.nutripal.ui.survey.MultipleChoiceQuestion
import com.submission.nutripal.ui.survey.SingleChoiceQuestion
import com.submission.nutripal.ui.survey.SingleChoiceWithoutImage
import com.submission.nutripal.ui.survey.TextInputQuestion

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
//
//package com.submission.nutripal.ui.survey
//
//import android.net.Uri
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import com.submission.nutripal.R
//
@Composable
fun PersonalizationQuestion(
    selectedAnswers: List<Int>,
    onOptionSelected: (selected: Boolean, answer: Int) -> Unit,
    modifier: Modifier = Modifier,
) {

}

@Composable
fun GenderQuestion(
    selectedAnswer: Gender?,
    onOptionSelected: (Gender) -> Unit,
    modifier: Modifier = Modifier,
) {
    SingleChoiceQuestion(
        titleResourceId = R.string.gender_question,
        directionsResourceId = R.string.select_one,
        possibleAnswers = listOf(
            Gender(R.string.male, R.drawable.male),
            Gender(R.string.female, R.drawable.female),
        ),
        selectedAnswer = selectedAnswer,
        onOptionSelected = onOptionSelected,
        modifier = modifier,
    )
}

@Composable
fun BirthQuestion(
    dateInMillis: Long?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    DateQuestion(
        titleResourceId = R.string.birth_date,
        directionsResourceId = R.string.select_date,
        dateInMillis = dateInMillis,
        onClick = onClick,
        modifier = modifier,
    )
}
@Composable
fun WeightQuestion(
    fillText: String,
    modifier: Modifier = Modifier,
){
    TextInputQuestion(titleResourceId = R.string.weight_question
        , directionsResourceId = R.string.weight_directions
        , hintTextId = R.string.weight_hint
        , filledText = fillText
        , modifier = modifier)
}
@Composable
fun HeightQuestion(
    fillText: String,
    modifier: Modifier = Modifier,
) {
    TextInputQuestion(
        titleResourceId = R.string.height_question,
        directionsResourceId = R.string.height_directions,
        hintTextId = R.string.height_hint,
        filledText = fillText,
        modifier = modifier
    )
}
@Composable
fun ActivityQuestion(
    selectedAnswer: Int,
    onOptionSelected: (selected: Boolean, answer: Int) -> Unit,
    modifier: Modifier = Modifier,
)
{
    MultipleChoiceQuestion(
        titleResourceId = R.string.activity_question,
        directionsResourceId = R.string.select_one,
        possibleAnswers = listOf(
            R.string.activity_1,
            R.string.activity_2,
            R.string.activity_3,
        ),
        selectedAnswers = listOf(selectedAnswer),
        onOptionSelected = onOptionSelected,
        modifier = modifier,
    )
}
@Composable
fun SmokingQuestion(
    selectedAnswer: Int,
    onOptionSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
){
    SingleChoiceWithoutImage(
        titleResourceId = R.string.smoking_question,
        directionsResourceId = R.string.select_one,
        possibleAnswers = listOf(
            R.string.answer_yes,
            R.string.answer_no,
        ),
        selectedAnswer = selectedAnswer,
        onOptionSelected = onOptionSelected,
        modifier = modifier,
    )
}
@Composable
fun AlcoholQuestion(
    selectedAnswer: Int,
    onOptionSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
)
{
    SingleChoiceWithoutImage(
        titleResourceId = R.string.alcohol_question,
        directionsResourceId = R.string.select_one,
        possibleAnswers = listOf(
            R.string.answer_yes,
            R.string.answer_no,
        ),
        selectedAnswer = selectedAnswer,
        onOptionSelected = onOptionSelected,
        modifier = modifier,
    )
}
@Composable
fun FoodPreferenceQuestion(
    selectedAnswer: Int,
    onOptionSelected: (Boolean,Int) -> Unit,
    modifier: Modifier = Modifier,
)
{
    MultipleChoiceQuestion(
        titleResourceId = R.string.food_preference_question,
        directionsResourceId = R.string.select_one,
        possibleAnswers = listOf(
            R.string.answer_yes,
            R.string.answer_no,
        ),
        selectedAnswers = listOf(selectedAnswer),
        onOptionSelected = onOptionSelected,
        modifier = modifier,
    )
}



