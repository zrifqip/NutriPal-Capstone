package com.submission.nutripal

import android.content.Context
import android.graphics.drawable.shapes.Shape
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.*
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
import com.submission.nutripal.ui.TextInput
import com.submission.nutripal.ui.theme.Shapes


@Composable
fun LoginScreen(
    onLogin: () -> Unit,
    onRegister: () -> Unit,
) {
    val context = LocalContext.current
    val passwordFocusRequest = remember { FocusRequester() }
    val FocusManager = LocalFocusManager.current
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
                    "Sign In",
                    fontSize = 20.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Text(
                    "Email",
                    modifier = Modifier.align(Alignment.Start)
                )
                TextInput(
                    inputType = InputType.Email, keyboardActions = KeyboardActions(
                        onDone = {
                            FocusManager.clearFocus()
                            passwordFocusRequest.requestFocus()
                        })
                )

                Text(
                    "Password",
                    modifier = Modifier.align(Alignment.Start)
                )
                TextInput(InputType.Password, keyboardActions = KeyboardActions(onDone = {
                    FocusManager.clearFocus()
                    context.doLogin()
                }), focusRequester = passwordFocusRequest)
                Divider(
                    color = Color.White.copy(alpha = 0.3f),
                    thickness = 1.dp,
                    modifier = Modifier.padding(top = 18.dp)
                )
                Button(onClick = onLogin, modifier = Modifier.widthIn(min = 200.dp, max = 400.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id =R.color.green_700))) {
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
private fun Context.doLogin() {
    Toast.makeText(
        this,
        "Something went wrong, try again later!",
        Toast.LENGTH_SHORT
    ).show()
}
@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {

}



