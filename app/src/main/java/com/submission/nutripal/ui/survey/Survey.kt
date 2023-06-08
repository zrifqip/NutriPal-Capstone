import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.submission.nutripal.R
import com.submission.nutripal.ui.survey.DateQuestion
import com.submission.nutripal.ui.survey.Gender
import com.submission.nutripal.ui.survey.MultipleChoiceQuestion
import com.submission.nutripal.ui.survey.PreviewStartQuestion
import com.submission.nutripal.ui.survey.SingleChoiceQuestion
import com.submission.nutripal.ui.survey.SingleChoiceWithoutImage
import com.submission.nutripal.ui.survey.StartSurvey
import com.submission.nutripal.ui.survey.TextInputQuestion
@Composable
fun StartQuestion(modifier: Modifier = Modifier) {
    StartSurvey(
        title = "Survey Profile",
        description = "Kami akan mengkalkulasi kebutuhan sehari hari tubuh anda agar mendapat makanan yang cocok bagi tubuh anda. Maka dari itu, kami memerlukan anda untuk mengisi survey mengenai kondisi tubuh anda.",
        modifier = modifier
    )
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
    onValueChange: (String) -> Unit,
){
    TextInputQuestion(titleResourceId = R.string.weight_question
        , directionsResourceId = R.string.weight_directions
        , hintTextId = R.string.weight_hint
        , filledText = fillText,
        modifier = modifier,
        onValueChange = onValueChange,
    )
}
@Composable
fun HeightQuestion(
    fillText: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
) {
    TextInputQuestion(
        titleResourceId = R.string.height_question,
        directionsResourceId = R.string.height_directions,
        hintTextId = R.string.height_hint,
        filledText = fillText,
        modifier = modifier,
        onValueChange = onValueChange,
    )
}
@Composable
fun ActivityQuestion(
    selectedAnswer: Int?,
    onOptionSelected: (answer: Int) -> Unit,
    modifier: Modifier = Modifier,
)
{
    SingleChoiceWithoutImage(
        titleResourceId = R.string.activity_question,
        directionsResourceId = R.string.select_one,
        possibleAnswers = listOf(
            R.string.activity_4,
            R.string.activity_1,
            R.string.activity_2,
            R.string.activity_3,
        ),
        selectedAnswer = selectedAnswer,
        onOptionSelected = onOptionSelected,
        modifier = modifier,
    )
}
@Composable
fun SmokingQuestion(
    selectedAnswer: Int?,
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
    selectedAnswer: Int?,
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


