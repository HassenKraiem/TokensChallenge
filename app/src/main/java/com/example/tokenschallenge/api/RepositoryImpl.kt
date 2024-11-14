package com.example.tokenschallenge.api

import com.example.tokenschallenge.dataStore.DataStoreManager
import com.example.tokenschallenge.domain.Repository
import com.example.tokenschallenge.user.User

class RepositoryImpl(
    private val authRemoteDataSource: AuthRemoteDataSource,
) : Repository {
    override suspend fun getUserInfo(dataStoreManager: DataStoreManager): User? {
        return authRemoteDataSource.getUserInfo(
            dataStoreManager = dataStoreManager
        )
    }

    override suspend fun login(postRequest: PostRequest): Result<Tokens> =
        kotlin.runCatching {
            authRemoteDataSource.login(postRequest)
        }
}
