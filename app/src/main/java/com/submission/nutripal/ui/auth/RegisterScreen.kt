package com.submission.nutripal.ui.auth

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.submission.nutripal.ui.InputType
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

    // Email, password, and name validation
    val isValidEmail = remember(email.value) { android.util.Patterns.EMAIL_ADDRESS.matcher(email.value).matches() }
    val isValidPassword = remember(password.value) { password.value.length >= 8 }
    val isValidName = remember(name.value) { name.value.isNotBlank() }

    // Whether to enable the register button
    val isButtonEnabled = remember(isValidEmail, isValidPassword, isValidName) { isValidEmail && isValidPassword && isValidName }

    // Warning text
    val warningTextName = "Name must be filled"
    val warningTextEmail = "Invalid email address"
    val warningTextPassword = "Password must be at least 8 characters"
    val warningTextConfirmationPassword = "Passwords do not match"

    BackHandler() {
        onRegister()
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .align(Alignment.Center)
                .background(MaterialTheme.colors.surface, RoundedCornerShape(16.dp))
        ) {
            Column(
                modifier = Modifier
                    .navigationBarsPadding()
                    .padding(16.dp),
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
                if (!isValidName) {
                    Text(
                        text = warningTextName,
                        color = Color.Red,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
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
                if (!isValidEmail) {
                    Text(
                        text = warningTextEmail,
                        color = Color.Red,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
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
                if (!isValidPassword) {
                    Text(
                        text = warningTextPassword,
                        color = Color.Red,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
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
                if(uiState is UiState.Error) {
                    Text(
                        text =  warningTextConfirmationPassword,
                        color = Color.Red,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
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
                }, modifier = Modifier.widthIn(min = 200.dp, max = 400.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.primary,
                    ),
                    enabled = isButtonEnabled
                ) {
                    Text("SIGN UP", Modifier.padding(vertical = 8.dp))
                }
                Divider(
                    color = Color.White.copy(alpha = 0.3f),
                    thickness = 1.dp,
                    modifier = Modifier.padding(top = 48.dp)
                )
            }
        }
        when (uiState) {
            is UiState.Idle -> {
                //do nothing
            }
            is UiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
            is UiState.Success -> {
                LaunchedEffect(uiState) {
                    onRegister()
                }
            }
            is UiState.Error -> {

            }
        }
    }
}

@Preview
@Composable
fun PreviewRegisterScreen() {
    RegisterScreen(
        onRegister = {}
    )
}





