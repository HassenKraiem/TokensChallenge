package com.example.tokenschallenge.data.dataStore.api

import com.example.tokenschallenge.data.domain.Repository
import com.example.tokenschallenge.user.User
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Single

@Single
class RepositoryImpl(
    @Provided private val authRemoteDataSource: AuthRemoteDataSource,
) : Repository {
    override suspend fun getUserInfo(): User? {
        return authRemoteDataSource.getUserInfo()
    }
    override suspend fun login(postRequest: PostRequest): Result<Tokens> =
        kotlin.runCatching {
            authRemoteDataSource.login(postRequest)
        }
}
