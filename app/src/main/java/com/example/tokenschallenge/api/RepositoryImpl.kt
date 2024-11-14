package com.example.tokenschallenge.api

import com.example.tokenschallenge.domain.Repository
import com.example.tokenschallenge.user.User

class RepositoryImpl(
    private val authRemoteDataSource: AuthRemoteDataSource,
) : Repository {
    override suspend fun getUserInfo(): User? {
        return authRemoteDataSource.getUserInfo()
    }
    override suspend fun login(postRequest: PostRequest): Result<Tokens> =
        kotlin.runCatching {
            authRemoteDataSource.login(postRequest)
        }
}
