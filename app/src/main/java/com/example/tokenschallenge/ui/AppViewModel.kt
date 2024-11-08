package com.example.tokenschallenge.ui

import androidx.lifecycle.ViewModel
import com.example.tokenschallenge.authorization.PostRequest
import com.example.tokenschallenge.authorization.AuthRemoteDataSource
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AppViewModel:ViewModel() {
    private val _uiState= MutableStateFlow(UiState())
    val uiState : StateFlow<UiState> = _uiState.asStateFlow()

    suspend fun takeDataToUi(client: HttpClient,postRequest: PostRequest){
        val postImplementation= AuthRemoteDataSource(client)
        val token=postImplementation.login(postRequest)
        val user=postImplementation.getUserInfo(postRequest)
        _uiState.update { currentState->
            currentState.copy(
                tokenInfo = token,
                userInfo = user

            )
        }

    }
}