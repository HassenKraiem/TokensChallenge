package com.example.tokenschallenge.domain

import com.example.tokenschallenge.api.PostRequest
import com.example.tokenschallenge.api.Tokens
import com.example.tokenschallenge.user.User

interface Repository {
    suspend fun getUserInfo():User?
    suspend fun login(postRequest: PostRequest): Result<Tokens>
}