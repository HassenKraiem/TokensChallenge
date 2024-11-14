package com.example.tokenschallenge.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.tokenschallenge.MainActivity
import com.example.tokenschallenge.screen.login.LoginScreen
import com.example.tokenschallenge.ui.AppViewModel
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
class RefreshRequest(
    val refreshToken: String
)

@Composable
fun MainScreen(
    mainActivity: MainActivity,
    preferenceDataStore: DataStore<Preferences>,
  //  dataStoreManager: DataStoreManager,
    appViewModel: AppViewModel = koinViewModel()
) {

   // val userDetails by dataStoreManager.getFromDataStore().collectAsState(UltimateClass())
    val uiState by appViewModel.uiState.collectAsState()

  //  val accessToken by dataStoreManager.getAccessToken().collectAsState("")
   // val refreshToken by dataStoreManager.getRefreshToken().collectAsState("")
    /*val noAuthClient = HttpClient(CIO) {
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
    }
    val authClient = remember(accessToken, refreshToken) {
        HttpClient(CIO) {
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

                    loadTokens {
                        BearerTokens(
                            accessToken = dataStoreManager.getAccessToken().first(),
                            refreshToken = dataStoreManager.getRefreshToken().first()
                        )
                    }
                    refreshTokens {
                        println("refresh token")

                        val response = client
                            .post(urlString = "https://api.lissene.com/api/v2/auth/refresh") {
                                contentType(ContentType.Application.Json)
                                setBody(
                                    RefreshRequest(
                                        refreshToken = dataStoreManager.getRefreshToken().first()
                                    )
                                )
                            }
                        println("refreshTokenInfo bodyAsText ${response.bodyAsText()}")

                        if (response.status.isSuccess()) {
                            println("refreshTokenInfo ${response.bodyAsText()}")
//                            val refreshTokenInfo: Tokens = response.body<Tokens>()

//                            dataStoreManager.saveAccessToken(refreshTokenInfo.accessToken)
//                            dataStoreManager.saveRefreshToken(refreshTokenInfo.refreshToken)
                            BearerTokens(
                                "refreshTokenInfo.accessToken",
                                "refreshTokenInfo.refreshToken"
                            )
                        } else {
                            dataStoreManager.clearDataStore()
                            BearerTokens("", "")
                        }
                    }
                }
            }
        }
    }*/

    /*LaunchedEffect(Unit) {
        dataStoreManager.saveAccessToken("refreshTokenInfo.accessToken")
        appViewModel.getUserInfoToDataStore(
            dataStoreManager = dataStoreManager,
           /* authClient = authClient,
            noAuthClient = noAuthClient*/
        )

    }*/
    LaunchedEffect(Unit) {
        appViewModel.checkRegisterState(preferenceDataStore = preferenceDataStore)
    }
    if (uiState.isLoggedIn) {
        ProfileScreen(appViewModel)
    } else {
        LoginScreen(
            appViewModel = appViewModel,

            )
    }


}

