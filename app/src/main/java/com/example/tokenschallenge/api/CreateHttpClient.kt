package com.example.tokenschallenge.api

import com.example.tokenschallenge.dataStore.DataStoreManager
import com.example.tokenschallenge.screen.RefreshRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.Json

fun createAutHttpClient(
    dataStoreManager: DataStoreManager
): HttpClient {
    return HttpClient(OkHttp) {
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.HEADERS
            sanitizeHeader { header -> header == HttpHeaders.Authorization }
        }
        install(ContentNegotiation) {
            json(json = Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            })
        }

        install(Auth) {
            bearer {
                loadTokens {
                    BearerTokens(
                        accessToken = dataStoreManager.getAccessToken().first(),
                        refreshToken = dataStoreManager.getRefreshToken().first()
                    )
                }
                refreshTokens {

                    val response =
                        client.post(urlString = "https://api.lissene.com/api/v2/auth/refresh") {
                                contentType(ContentType.Application.Json)
                                setBody(
                                    RefreshRequest(
                                        refreshToken = dataStoreManager.getRefreshToken().first()
                                    )
                                )
                            }
                    println("refreshTokenInfo bodyAsText ${response.bodyAsText()}")

                    if (response.status.isSuccess()) {
                        val refreshTokenInfo: Tokens = response.body<Tokens>()

                        dataStoreManager.saveAccessToken(refreshTokenInfo.accessToken)
                        dataStoreManager.saveRefreshToken(refreshTokenInfo.refreshToken)
                        BearerTokens(
                            accessToken = dataStoreManager.getAccessToken().first(),
                            refreshToken = dataStoreManager.getRefreshToken().first()
                        )
                    } else {
                        dataStoreManager.clearDataStore()
                        BearerTokens("", "")
                    }
                }
            }
        }
    }
}