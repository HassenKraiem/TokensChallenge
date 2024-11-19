package com.example.tokenschallenge.data.domain

import com.example.tokenschallenge.data.api.PostRequest
import com.example.tokenschallenge.data.api.Tokens
import com.example.tokenschallenge.user.User
import org.koin.core.annotation.Single


interface Repository {
    suspend fun getUserInfo():User?
    suspend fun login(postRequest: PostRequest): Result<Tokens>
}