package com.example.tokenschallenge.screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tokenschallenge.ui.LogInViewModel
import com.example.tokenschallenge.ui.theme.TokensChallengeTheme

@SuppressLint("UnrememberedMutableState")
@Composable
fun LoginScreen(
    logInViewModel: LogInViewModel,
    logIn:()->Unit
) {
    val logInUiState by logInViewModel.logInUiState.collectAsState()



    var phone by remember { mutableStateOf("55529601") }
    var password by remember { mutableStateOf("123456789") }

    val mContext = LocalContext.current

    val focusRequester1 = remember { FocusRequester() }
    val focusRequester2 = remember { FocusRequester() }

    val focusManager = LocalFocusManager.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        TextField(value = phone, onValueChange = { phone = it }, label = {
            Text(text = "phone")
        },
            keyboardActions = KeyboardActions(onNext = {
            focusRequester2.requestFocus() // Move to next TextField
        }),
            modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .focusRequester(focusRequester1)
        )
        TextField(value = password,
            onValueChange = { password = it },
            label = {
            Text(text = "password")
        },
            keyboardActions = KeyboardActions(
                onDone = {
            focusManager.clearFocus() // Hide the keyboard
        }),
            modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .focusRequester(focusRequester2)
        )
        Button(
            onClick = {
                if (phone.isEmpty()) {
                    Toast.makeText(mContext, "Phone number is Empty", Toast.LENGTH_SHORT).show()
                } else if (password.isEmpty()) {
                    Toast.makeText(mContext, "Password is Empty", Toast.LENGTH_SHORT).show()
                } else {
                    logInViewModel.login(
                            phone = phone,
                            password = password,
                        )
                    logIn()
                    }

            }, modifier = Modifier.padding(16.dp)
        ) {
            Text("Submit")
        }

        Text(
            text = logInUiState.errorMessage, fontSize = 25.sp
        )
    }
}


@Preview(showBackground = true)
@Composable
fun LogPreview() {
    TokensChallengeTheme {

    }
}