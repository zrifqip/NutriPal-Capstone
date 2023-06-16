package com.submission.nutripal.ui.dashboard.Profile

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.submission.nutripal.data.SurveyDataResponse
import com.submission.nutripal.data.SurveyResult
import com.submission.nutripal.data.UiState
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun ProfileScreen(
    navigateToSurvey: () -> Unit,
    Logout: () -> Unit,
) {

    val profileViewModel: ProfileViewModel = hiltViewModel()
    val uiState by profileViewModel.uiState.observeAsState(UiState.Idle)

    var name by remember{ mutableStateOf("") }
    var email by remember{ mutableStateOf("") }
    var sex by remember{ mutableStateOf("") }
    var age by remember{ mutableStateOf("") }
    var height by remember{ mutableStateOf("") }
    var activityLevel by remember{ mutableStateOf("") }
    LaunchedEffect(profileViewModel) {
        profileViewModel.getSurveyData()
    }

    when (uiState) {
        is UiState.Idle -> {
            //do nothing
        }
        is UiState.Loading -> {
            // Display a loading spinner, progress bar or some placeholder UI
        }
        is UiState.Error -> {
            // Display an error message
            Log.e("ProfileScreen", "Error: ${(uiState as UiState.Error).message}")
        }
        is UiState.Success -> {
            val result = (uiState as UiState.Success<SurveyDataResponse>).data
            name = profileViewModel.getName()
            email = profileViewModel.getEmail()
            sex = result.SurveyData.sex
            age = "${result.SurveyData.age}"
            height = "${result.SurveyData.height}"
            activityLevel = result.SurveyData.activity
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Header", style = MaterialTheme.typography.h5)
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = 4.dp,
            //set color
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                DetailLine("Name", name)
                Divider(color = Color.Gray, thickness = 0.5.dp)
                DetailLine("Email", email)
                Divider(color = Color.Gray, thickness = 0.5.dp)
                DetailLine("Sex", sex)
                Divider(color = Color.Gray, thickness = 0.5.dp)
                DetailLine("Age", age)
                Divider(color = Color.Gray, thickness = 0.5.dp)
                DetailLine("Height", height)
                Divider(color = Color.Gray, thickness = 0.5.dp)
                DetailLine("Activity Level", activityLevel)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            navigateToSurvey()
        }) {
            Text(text = "Edit Survey")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            profileViewModel.clearSession()
            Logout()
        }) {
            Text(text = "Logout")
        }
    }
}

@Composable
fun DetailLine(label: String, content: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.body1)
        Text(text = content, textAlign = TextAlign.End)
    }
}
@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(navigateToSurvey = {}, Logout = {})
}

