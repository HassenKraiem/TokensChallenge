package com.example.tokenschallenge.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tokenschallenge.data.dataStore.DataStoreManager
import com.example.tokenschallenge.data.domain.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Provided


@KoinViewModel
class ProfileViewModel(
    @Provided private val repository: Repository,
    @Provided private val dataStoreManager: DataStoreManager
) : ViewModel() {

    /*init {
        getProfile()
        }*/

    private val _profileUiState = MutableStateFlow(ProfileUiState())
    val profileUiState: StateFlow<ProfileUiState> = _profileUiState.asStateFlow()

    fun getProfile(
    ) {
        viewModelScope.launch {
            val user = repository.getUserInfo()
            if (user != null) {
                dataStoreManager.saveUserInfoToDataStore(
                    user = user
                )
                takeDataToUi()
            }

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
