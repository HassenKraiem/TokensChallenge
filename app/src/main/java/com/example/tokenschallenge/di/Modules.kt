package com.example.tokenschallenge.di

import com.example.tokenschallenge.api.AuthRemoteDataSource
import com.example.tokenschallenge.api.RepositoryImpl
import com.example.tokenschallenge.api.createAutHttpClient
import com.example.tokenschallenge.dataStore.DataStoreManager
import com.example.tokenschallenge.domain.Repository
import com.example.tokenschallenge.screen.ProfileScreen
import com.example.tokenschallenge.ui.AppViewModel
import com.example.tokenschallenge.ui.LogInViewModel
import com.example.tokenschallenge.ui.ProfileViewModel
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
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named

import org.koin.dsl.module


val Modules = module {
    single<DataStoreManager> {
        DataStoreManager(get())
    }
    single<HttpClient>(named("noAuthClient")) {
    HttpClient(OkHttp) {
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
    }
    single<HttpClient>(named("authClient")) {
        createAutHttpClient(get())
    }

    single {
        AuthRemoteDataSource(get(named("authClient")),get(named("noAuthClient")))

    }
    single<Repository> {
        RepositoryImpl(get())
    }
    viewModel { LogInViewModel(get(),get()) }
    viewModel { ProfileViewModel(get(),get()) }
    viewModel { AppViewModel(get()) }



}