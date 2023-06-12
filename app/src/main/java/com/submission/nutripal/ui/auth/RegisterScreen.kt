package com.submission.nutripal.ui.auth

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.submission.nutripal.InputType
import com.submission.nutripal.R
import com.submission.nutripal.data.UiState
import com.submission.nutripal.data.User
import com.submission.nutripal.ui.TextInput
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun RegisterScreen(
    onRegister: () -> Unit,
) {
    val name = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmationPassword = remember { mutableStateOf("") }
    val passwordFocusRequest = remember { FocusRequester() }
    val FocusManager = LocalFocusManager.current
    val registerViewModel: RegisterViewModel = hiltViewModel()

    val emailInputType = InputType.Email
    val usernameInputType = InputType.Username
    val passwordInputType = InputType.Password
    val confirmationPasswordInputType = InputType.ConfirmationPassword



    val uiState by registerViewModel.uiState.observeAsState(UiState.Idle)

    when (uiState) {
        is UiState.Idle -> {
            //do nothing
        }
        is UiState.Loading -> {
            CircularProgressIndicator()
        }
        is UiState.Success ->{
            LaunchedEffect(uiState) {
                onRegister()
            }
        }
        is UiState.Error -> {
            Text(text = (uiState as UiState.Error).message)
        }
    }
    //add box in center for input text
    Box(
        modifier = Modifier
            //set background color
            .background(colorResource(id = R.color.green_500))
            .fillMaxSize()
    ) {

        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                //make the color white
                .background(Color.White, RoundedCornerShape(16.dp))
                .align(Alignment.Center)

        )
        {
            Column(
                modifier = Modifier
                    .navigationBarsPadding()
                    .padding(16.dp)
,
                verticalArrangement = Arrangement.spacedBy(
                    16.dp,
                    alignment = Alignment.CenterVertically
                ),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    "Register",
                    fontSize = 20.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Text(
                    "Email",
                    modifier = Modifier.align(Alignment.Start)
                )
                TextInput(
                    value = email,
                    onValueChange = { newValue -> email.value = newValue },
                    inputType = emailInputType,
                    keyboardActions = KeyboardActions(
                        onDone = {
                            FocusManager.clearFocus()
                            passwordFocusRequest.requestFocus()
                        })
                )
                Text(
                    "Username",
                    modifier = Modifier.align(Alignment.Start)
                )
                TextInput(
                    value = name,
                    onValueChange = { newValue -> name.value = newValue },
                    inputType = usernameInputType,
                    keyboardActions = KeyboardActions(
                        onDone = {
                            FocusManager.clearFocus()
                            passwordFocusRequest.requestFocus()
                        })
                )
                Text(
                    "Password",
                    modifier = Modifier.align(Alignment.Start)
                )
                TextInput(
                    value = password,
                    onValueChange = { newValue -> password.value = newValue },
                    inputType = passwordInputType,
                    keyboardActions = KeyboardActions(onDone = {
                        FocusManager.clearFocus()
                    }),
                    focusRequester = passwordFocusRequest
                )
                Text(
                    "Reconfirm Password",
                    modifier = Modifier.align(Alignment.Start)
                )
                TextInput(
                    value = confirmationPassword,
                    onValueChange = { newValue -> confirmationPassword.value = newValue },
                    inputType = confirmationPasswordInputType,
                    keyboardActions = KeyboardActions(onDone = {
                        FocusManager.clearFocus()
                    }),
                    focusRequester = passwordFocusRequest
                )
                Divider(
                    color = Color.White.copy(alpha = 0.3f),
                    thickness = 1.dp,
                    modifier = Modifier.padding(top = 18.dp)
                )
                Button(onClick = {
                    val user = User(
                        name = name.value,
                        email = email.value,
                        password = password.value,
                        confirmationPassword = confirmationPassword.value
                    )
                    Log.d("RegisterScreen", user.toString())
                    registerViewModel.registerUser(user)
//                    onRegister()
                }, modifier = Modifier.widthIn(min = 200.dp, max = 400.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.green_700))) {
                    Text("SIGN UP", Modifier.padding(vertical = 8.dp))
                }
                Divider(
                    color = Color.White.copy(alpha = 0.3f),
                    thickness = 1.dp,
                    modifier = Modifier.padding(top = 48.dp)
                )
            }
        }
        //create user for input


    }
}

@Preview
@Composable
fun PreviewRegisterScreen() {
    RegisterScreen(
        onRegister = {}
    )
}





