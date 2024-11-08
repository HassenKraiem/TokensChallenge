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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.tokenschallenge.UltimateClass
import com.example.tokenschallenge.authorization.AuthRemoteDataSource
import com.example.tokenschallenge.authorization.PostRequest
import com.example.tokenschallenge.authorization.Tokens
import com.example.tokenschallenge.dataStore.DataStoreManager
import com.example.tokenschallenge.ui.theme.TokensChallengeTheme
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.forms.submitForm
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

@SuppressLint("UnrememberedMutableState")
@Composable
fun LogScreen(
    onRegisterSuccess: () -> Unit,
    dataStoreManager: DataStoreManager,
) {


    val scope = rememberCoroutineScope()


    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val client = HttpClient(CIO) {
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.HEADERS
            sanitizeHeader { header -> header == HttpHeaders.Authorization }
        }
        install(ContentNegotiation) {
            json(json = Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            })
        }
        install(Auth) {
            bearer {
                scope.launch {
                    refreshTokens {
                        val refreshTokenInfo: Tokens = client.submitForm(
                            url = "https://api.lissene.com/api/v2/auth/refresh",
                        ) { markAsRefreshTokenRequest() }.body<Tokens>()
                        BearerTokens(refreshTokenInfo.accessToken, oldTokens?.refreshToken!!)
                    }
                }
            }
        }
    }

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
        }, keyboardActions = KeyboardActions(onNext = {
            focusRequester2.requestFocus() // Move to next TextField
        }), modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .focusRequester(focusRequester1)
        )
        TextField(value = password, onValueChange = { password = it }, label = {
            Text(text = "password")
        }, keyboardActions = KeyboardActions(onDone = {
            focusManager.clearFocus() // Hide the keyboard
        }), modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .focusRequester(focusRequester2)
        )
        var errorText by remember { mutableStateOf("") }

        Button(
            onClick = {
                if (phone.isEmpty()) {
                    Toast.makeText(mContext, "Phone number is Empty", Toast.LENGTH_SHORT).show()
                } else if (password.isEmpty()) {
                    Toast.makeText(mContext, "Password is Empty", Toast.LENGTH_SHORT).show()
                } else {
                    //Submit you data
                    scope.launch {
                        val postImplementation = AuthRemoteDataSource(client)
                        val token = postImplementation.login(PostRequest(phone, password))
                        val user = postImplementation.getUserInfo(PostRequest(phone, password))
                        // println("user is " + user)
                        if (user != null) {
                            dataStoreManager.saveToDataStore(
                                UltimateClass(
                                    user = user, tokens = token
                                )
                            )
                            onRegisterSuccess()
                        } else {
                            errorText = "Verify your phone number and your password"
                        }
                    }
                }

            }, modifier = Modifier.padding(16.dp)
        ) {
            Text("Submit")
        }

        Text(
            text = errorText, fontSize = 25.sp
        )
    }
}


@Preview(showBackground = true)
@Composable
fun LogPreview() {
    TokensChallengeTheme {

    }
}