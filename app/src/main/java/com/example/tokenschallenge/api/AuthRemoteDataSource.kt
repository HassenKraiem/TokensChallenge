package com.example.tokenschallenge.api

import com.example.tokenschallenge.dataStore.DataStoreManager
import com.example.tokenschallenge.user.User
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType

class AuthRemoteDataSource(
    private val authClient:HttpClient,
    private val noAuthClient:HttpClient,) {
    suspend fun getUserInfo(
        dataStoreManager: DataStoreManager
    ): User? {
       // println("accessToken$accessToken")
        return try {
            val response = authClient.get("https://api.lissene.com/api/v2/user/me") {

               /* headers {
                    append(HttpHeaders.Authorization, value = "Bearer $accessToken")
                }*/
            }


            println("response user body " + response.body<User>())
            println("response text body " + response.bodyAsText())

            println("getAccessToken ${dataStoreManager.getAccessToken()}")
            println("getRefreshToken ${dataStoreManager.getRefreshToken()}")

            response.body<User>()

        } catch (e: Exception) {

            e.printStackTrace()
            null
        }

    }

    suspend fun login(postRequest: PostRequest): Tokens {
        return try {

            val response = noAuthClient.post(" https://api.lissene.com/api/v2/auth/login") {
                contentType(ContentType.Application.Json)
                setBody(postRequest)

            }
            println()
            response.body<Tokens>()
        } catch (e: Exception) {
            Tokens(
                accessToken = "", ""
            )

        }
    }
}