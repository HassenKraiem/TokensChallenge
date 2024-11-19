package com.example.tokenschallenge.data.api

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single



const val NoAuthHttpClient = "NoAuthHttpClient"
@Single
@Named(NoAuthHttpClient)
fun noAuthHttpClient() = HttpClient(OkHttp) {
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
}