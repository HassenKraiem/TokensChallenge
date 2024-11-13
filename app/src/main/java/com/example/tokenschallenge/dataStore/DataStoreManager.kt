package com.example.tokenschallenge.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.tokenschallenge.UltimateClass
import com.example.tokenschallenge.user.Payload
import com.example.tokenschallenge.user.User
import com.example.tokenschallenge.user.UserX
import kotlinx.coroutines.flow.map

const val USER_DATASTORE = "user_data"

val Context.preferenceData: DataStore<Preferences> by preferencesDataStore(name = USER_DATASTORE)

class DataStoreManager(private val context: Context) {
    companion object {
        val USER_ID = stringPreferencesKey("USER_ID")
        val USER_PHONE = stringPreferencesKey("USER_PHONE")
        val USER_FIRST_NAME = stringPreferencesKey("USER_FIRST_NAME")
        val ACCESS_TOKEN = stringPreferencesKey("ACCESS_TOKEN")
        val REFRESH_TOKEN = stringPreferencesKey("REFRESH_TOKEN")

    }

    suspend fun saveToDataStore(ultimateClass: UltimateClass) {
        context.preferenceData.edit {
            it[USER_ID] = ultimateClass.user.payload.user._id
            it[USER_FIRST_NAME] = ultimateClass.user.payload.user.firstName
            it[USER_PHONE] = ultimateClass.user.payload.user.phone
        }
    }


    suspend fun saveAccessToken(accessToken: String) {
        context.preferenceData.edit {
            it[ACCESS_TOKEN] = accessToken

        }
    }

     fun getAccessToken()  =
        context.preferenceData.data.map {
            it[ACCESS_TOKEN] ?: ""
        }

    fun getRefreshToken()  =
        context.preferenceData.data.map {
            it[REFRESH_TOKEN] ?: ""
        }


    suspend fun saveRefreshToken(refreshToken: String) {
        context.preferenceData.edit {
            it[REFRESH_TOKEN] = refreshToken

        }
    }

    fun getFromDataStore() = context.preferenceData.data.map {
        UltimateClass(
            user = User.Default.copy(
                    payload = Payload(
                        UserX(
                            _id = it[USER_ID] ?: "Hassen",
                            firstName = it[USER_FIRST_NAME] ?: "Hassen",
                            phone = it[USER_PHONE] ?: "58431128"

                        )
                    )
                ),
        )
    }

    suspend fun clearDataStore() = context.preferenceData.edit {
        it.clear()
    }
}