package com.example.tokenschallenge.api

import com.example.tokenschallenge.user.User
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class AuthRemoteDataSource(
    private val authClient: HttpClient,
    private val noAuthClient: HttpClient,
) {
    suspend fun getUserInfo(
    ): User? {
        return try {
            val response = authClient.get("https://api.lissene.com/api/v2/user/me") {}
            response.body<User>()
        } catch (e: Exception) {
            null
        }

    }
    suspend fun login(postRequest: PostRequest): Tokens {
        val response = noAuthClient.post(" https://api.lissene.com/api/v2/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(postRequest)
        }
        return response.body<Tokens>()
    }
}