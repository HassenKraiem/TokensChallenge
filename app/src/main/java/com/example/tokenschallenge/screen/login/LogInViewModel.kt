package com.example.tokenschallenge.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.tokenschallenge.data.api.PostRequest
import com.example.tokenschallenge.data.dataStore.DataStoreManager
import com.example.tokenschallenge.data.domain.Repository
import com.example.tokenschallenge.navigation.Route
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Provided

@KoinViewModel
class LogInViewModel(
    private val repository: Repository,
    private val dataStoreManager: DataStoreManager,
) : ViewModel() {

    private val _logInUiState = MutableStateFlow(LogInUiState())
    val logInUiState: StateFlow<LogInUiState> = _logInUiState.asStateFlow()
    fun login(
        phone: String,
        password: String,
    ) {
        viewModelScope.launch {
            repository.login(postRequest = PostRequest(phone, password))
                .onSuccess { token ->
                    println("on success worked")
                    dataStoreManager.saveAccessToken(token.accessToken)
                    dataStoreManager.saveRefreshToken(token.refreshToken)
                    _logInUiState.update { currentState ->
                        currentState.copy(
                            errorMessage = "",
                        )
                    }
                    onLoggedIn()
                    _logInUiState.update {currentState->
                        currentState.copy(
                            loggedIn = true
                        )

                    }
                }.onFailure {
                    println("on fqilure"+it)
                    println("on fqilure"+it.cause)
                    println("on fqilure"+it.message)
                    _logInUiState.update { currentState ->
                        currentState.copy(
                            errorMessage =it.message?:""
                        )
                    }
                }
           /* if (dataStoreManager.getAccessToken().first()==""){
                _logInUiState.update { currentState ->
                    currentState.copy(
                        errorMessage = "Sorry"
                    )
                }

            }*/
        }
    }
    private fun onLoggedIn() {
        viewModelScope.launch {
            delay(2000L)
            if (dataStoreManager.getAccessToken().first() != "") {
               dataStoreManager.loggedIn()
            }
        }
    }
}