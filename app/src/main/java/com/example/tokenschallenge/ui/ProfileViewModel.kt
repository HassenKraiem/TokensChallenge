package com.example.tokenschallenge.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tokenschallenge.dataStore.DataStoreManager
import com.example.tokenschallenge.domain.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val repository: Repository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    init {
        getProfile()
        }

    private val _profileUiState = MutableStateFlow(ProfileUiState())
    val profileUiState: StateFlow<ProfileUiState> = _profileUiState.asStateFlow()

    private fun getProfile(
    ) {
        viewModelScope.launch {
            val user = repository.getUserInfo()
            if (user != null) {
                dataStoreManager.saveUserInfoToDataStore(
                    user = user
                )
            }
            takeDataToUi()
        }
    }
    private suspend fun takeDataToUi() {
        val userInfo= dataStoreManager.getUSer().first()
        _profileUiState.update { currentState ->
            currentState.copy(
                name =userInfo.payload.user.firstName,
                country =userInfo.payload.user.country,
                id = userInfo.payload.user._id,
                phone = userInfo.payload.user.phone
            )
        }
    }

}
