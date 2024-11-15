package com.example.tokenschallenge.di

import com.example.tokenschallenge.data.dataStore.api.AuthRemoteDataSource
import com.example.tokenschallenge.data.dataStore.api.RepositoryImpl
import com.example.tokenschallenge.data.dataStore.api.createAutHttpClient
import com.example.tokenschallenge.data.dataStore.DataStoreManager
import com.example.tokenschallenge.data.dataStore.api.Tokens
import com.example.tokenschallenge.data.domain.Repository
import com.example.tokenschallenge.screen.main.AppViewModel
import com.example.tokenschallenge.screen.login.LogInViewModel
import com.example.tokenschallenge.screen.main.RefreshRequest
import com.example.tokenschallenge.screen.profile.ProfileViewModel
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
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Single
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.ksp.generated.*

import org.koin.dsl.module

@Module
@ComponentScan("com.example.tokenschallenge.mainActivity")
class DataStoreModule
/*val dataStoreModule= module {
    single<DataStoreManager> {
        DataStoreManager(get())
    }
}*/
@Module
@ComponentScan("com.example.tokenschallenge.data")
class ApiModule{
    @Single
    @Named("noAuthHttpClient")
    fun noAuthHttpClient()=HttpClient(OkHttp) {
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

@Single
@Named("authHttpClient")
fun createAutHttpClient(
    @Provided dataStoreManager: DataStoreManager
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

/*val apiModule = module {
    /*single<HttpClient>(named("noAuthClient")) {
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
    }*/
     single<HttpClient>(named("authClient")) {
        createAutHttpClient(get())
    }

    single {
        AuthRemoteDataSource(get(named("authClient")),get(named("noAuthClient")))

    }
    single<Repository> {
        RepositoryImpl(get())
    }

}*/
    @Module
    @ComponentScan("com.example.tokenschallenge.screen")
    class ViewModelModelModule

   /* fun initKoin() {
        startKoin {
            module {
                DataStoreModule().module
                ApiModule().module
                ViewModelModelModule().module
            }
        }
    }
/*val viewModelModule= module {
    viewModel { LogInViewModel(get(),get()) }
    viewModel { ProfileViewModel(get(),get()) }
    viewModel { AppViewModel(get()) }

}*/