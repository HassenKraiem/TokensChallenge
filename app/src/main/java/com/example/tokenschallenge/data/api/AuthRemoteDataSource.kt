package com.example.tokenschallenge.data.api

import android.accounts.NetworkErrorException
import com.example.tokenschallenge.data.exeptions.ConflictException
import com.example.tokenschallenge.data.exeptions.PayloadTooLargeException
import com.example.tokenschallenge.data.exeptions.ServerException
import com.example.tokenschallenge.data.exeptions.UnauthorizedException
import com.example.tokenschallenge.user.User
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import java.util.concurrent.TimeoutException


@Single
class AuthRemoteDataSource(
    @Named(AuthHttpClient)private val authClient: HttpClient,
    @Named(NoAuthHttpClient) private val noAuthClient: HttpClient,
) {
    suspend fun getUserInfo():User? =
        try {
            val response = authClient.get(HttpRoutes.GET_PROFILE) {}
            response.body<User>()
        }catch (e:Exception){
            null
        }

    suspend fun login(postRequest: PostRequest): Tokens {
        val response = noAuthClient.post(HttpRoutes.LOG_IN) {
            contentType(ContentType.Application.Json)
            setBody(postRequest)
        }
        //println("failure code "+response.status)
        when(response.status.value){
            in 200..299-> return response.body<Tokens>()
            in 401..405->throw UnauthorizedException()
            409->throw ConflictException()
            408->throw TimeoutException("Time out")
            413->throw PayloadTooLargeException()
            in 500..599->throw ServerException()
            else -> throw NetworkErrorException("Check your internet access")
        }

    }
}