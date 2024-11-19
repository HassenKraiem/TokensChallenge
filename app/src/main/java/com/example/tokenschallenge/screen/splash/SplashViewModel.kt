package com.example.tokenschallenge.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tokenschallenge.data.dataStore.DataStoreManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class SplashViewModel(
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
    private val _splashState = MutableStateFlow(SplashState())
    val splashState=_splashState.asStateFlow()

    init {
        viewModelScope.launch {
            _splashState.update { currentState ->
                currentState.copy(
                    isLoggedIn = dataStoreManager.getIsLoggedIn().first()
                )
            }
        }
    }

}