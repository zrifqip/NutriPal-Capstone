package com.submission.nutripal.ui.auth

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*

import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter.State.Empty.painter
import com.submission.nutripal.R
import com.submission.nutripal.data.LoginBody
import com.submission.nutripal.data.UiState
import com.submission.nutripal.ui.InputType
import com.submission.nutripal.ui.TextInput


@Composable
fun LoginScreen(
    onLogin: () -> Unit,
    onRegister: () -> Unit,
) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val passwordFocusRequest = remember { FocusRequester() }
    val FocusManager = LocalFocusManager.current
    val loginViewModel: LoginViewModel = hiltViewModel()
    //add box in center for input text
    val emailInputType = InputType.Email
    val passwordInputType = InputType.Password
    val uiState by loginViewModel.uiState.observeAsState(UiState.Idle)

    if( loginViewModel.checkLogin()){
        LaunchedEffect(uiState) {
            onLogin()
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        when (uiState) {
            is UiState.Idle -> {
                //do nothing
            }
            is UiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            is UiState.Success -> {
                LaunchedEffect(uiState) {
                    onLogin()
                }
            }
            is UiState.Error -> {
                Text(text = (uiState as UiState.Error).message, color = Color.Red)
            }
        }
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
                Image(painter = painterResource(id = R.drawable.nutripal_logo), contentDescription = "Logo")
                Text("Welcome to Nutripal", fontSize = 24.sp, modifier = Modifier.align(Alignment.CenterHorizontally))
                Text("Nutripal is a food recommendation app for your diet", fontSize = 16.sp, modifier = Modifier.align(Alignment.CenterHorizontally), textAlign = TextAlign.Center)

                // Here is the rest of your code
                Text(
                    "Sign In",
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
                if(uiState is UiState.Error){
                    Text(text = "Wrong Email and password", color = Color.Red)
                }
                Divider(
                    thickness = 1.dp,
                    modifier = Modifier.padding(top = 18.dp)
                )
                Button(onClick = {
                    val login = LoginBody(
                        email = email.value,
                        password = password.value
                    )
                    Log.d("LoginScreen", "LoginScreen: $login")
                    loginViewModel.loginUser(login)
                }
                    , modifier = Modifier.widthIn(min = 200.dp, max = 400.dp),
                ) {
                    Text("SIGN IN", Modifier.padding(vertical = 8.dp),
                        color = MaterialTheme.colors.onPrimary)
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Don't have an account?",
                        fontWeight = FontWeight.Medium
                    )
                    TextButton(onClick = onRegister) {
                        Text("SIGN UP", color = MaterialTheme.colors.primary)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {

}



