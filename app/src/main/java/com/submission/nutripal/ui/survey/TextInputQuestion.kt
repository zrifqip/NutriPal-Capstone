package com.submission.nutripal.ui.survey

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.submission.nutripal.InputType
import com.submission.nutripal.R
import com.submission.nutripal.ui.theme.NutripalTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextInputQuestion(
    @StringRes titleResourceId: Int,
    @StringRes directionsResourceId: Int,
    modifier: Modifier = Modifier,
    @StringRes hintTextId: Int,
    filledText : String,
    onValueChange : (String) -> Unit,
) {
    QuestionWrapper(
        titleResourceId = titleResourceId,
        directionsResourceId = directionsResourceId,
        modifier = modifier.selectableGroup(),
    ) {
        //add box for weight
       Row(modifier = Modifier
           .padding(20.dp)
           .fillMaxWidth(),
           horizontalArrangement = Arrangement.Center){
           var value by remember { mutableStateOf(filledText) }
           TextField(
               modifier = Modifier
                   .align(Alignment.CenterVertically)
                   .width(100.dp),
               value = value,
               textStyle = TextStyle(fontSize = 36.sp),
               onValueChange = { newValue ->
                   onValueChange(newValue)
                   value = newValue
               },
               keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
           )
           Spacer(modifier = Modifier.width(16.dp))
           Text(text = stringResource(id = hintTextId),
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    //set font size
                    .sizeIn(minWidth = 0.dp, minHeight = 0.dp),
                    style = TextStyle( fontSize = 36.sp)

           )
       }




    }
}
@Preview(showBackground = true)
@Composable
fun PreviewTextInputQuestion() {
    NutripalTheme {
        TextInputQuestion(
            titleResourceId = R.string.gender_question,
            directionsResourceId = R.string.gender_question,
            hintTextId = R.string.weight_hint,
            filledText = "",
            onValueChange = {  }
        )
    }

}
