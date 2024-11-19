package com.example.tokenschallenge.data.api

import com.example.tokenschallenge.data.domain.Repository
import com.example.tokenschallenge.user.User
import org.koin.core.annotation.Single

@Single
class RepositoryImpl(
    private val authRemoteDataSource: AuthRemoteDataSource,
) : Repository {
    override suspend fun getUserInfo(): User? {
        return authRemoteDataSource.getUserInfo()
    }
    override suspend fun login(postRequest: PostRequest): Result<Tokens> =
        runCatching {
            authRemoteDataSource.login(postRequest)
        }
}
