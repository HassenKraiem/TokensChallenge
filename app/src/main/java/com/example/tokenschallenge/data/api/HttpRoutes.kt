package com.example.tokenschallenge.data.api

object HttpRoutes {
    private const val BASE_URL=" https://api.lissene.com"
    const val LOG_IN="$BASE_URL/api/v2/auth/login"
    const val REFRESH="$BASE_URL/api/v2/auth/refresh"
    const val GET_PROFILE="$BASE_URL/api/v2/user/me"
}