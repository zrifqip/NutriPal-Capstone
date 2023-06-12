package com.submission.nutripal

import android.content.Context
import android.graphics.drawable.shapes.Shape
import android.util.Log
import android.widget.Toast
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
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusOrder
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.submission.nutripal.data.LoginBody
import com.submission.nutripal.data.UiState
import com.submission.nutripal.ui.TextInput
import com.submission.nutripal.ui.auth.LoginViewModel
import com.submission.nutripal.ui.theme.Shapes


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

    when (uiState) {
        is UiState.Idle -> {
            //do nothing
        }
        is UiState.Loading -> {
            CircularProgressIndicator(color = colorResource(id = R.color.green_900))
        }
        is UiState.Success -> {
            LaunchedEffect(uiState) {
                onLogin()
            }
        }
        is UiState.Error -> {
            Text(text = (uiState as UiState.Error).message,color = Color.Red)
        }
    }
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
                Divider(
                    color = Color.White.copy(alpha = 0.3f),
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
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.green_700))) {
                    Text("SIGN IN", Modifier.padding(vertical = 8.dp))
                }
                Divider(
                    color = Color.White.copy(alpha = 0.3f),
                    thickness = 1.dp,
                    modifier = Modifier.padding(top = 48.dp)
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Don't have an account?", color = colorResource(id =R.color.green_700 ),
                        fontWeight = FontWeight.Medium
                    )
                    TextButton(onClick = onRegister) {
                        Text("SIGN UP",color = colorResource(id =R.color.green_900 ), fontWeight = FontWeight.Bold)
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



