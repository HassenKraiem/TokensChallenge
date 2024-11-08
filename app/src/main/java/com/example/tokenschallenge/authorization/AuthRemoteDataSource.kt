package com.example.tokenschallenge.authorization

import com.example.tokenschallenge.user.User
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType

class AuthRemoteDataSource(private val client: HttpClient) {
    suspend fun getUserInfo(postRequest: PostRequest): User? {
        val accessToken = login(postRequest).accessToken
        return try {
            val response = client.get("https://api.lissene.com/api/v2/user/me") {
                headers {
                    append(HttpHeaders.Authorization, value = "Bearer $accessToken")
                }
            }
            println("response text is " + response.bodyAsText())
            println("response user body " + response.body<User>())
            response.body<User>()

        } catch (e: Exception) {
            null
        }

    }

    suspend fun login(postRequest: PostRequest): Tokens {
        return try {
            val response = client.post(" https://api.lissene.com/api/v2/auth/login") {
                contentType(ContentType.Application.Json)
                setBody(postRequest)

            }
            response.body<Tokens>()
        } catch (e: Exception) {
            Tokens(
                accessToken = "", ""
            )

        }
    }
}