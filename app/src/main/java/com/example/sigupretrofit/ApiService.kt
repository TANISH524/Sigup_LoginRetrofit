package com.example.sigupretrofit

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("users")
    suspend fun signup(
        @Body request: SignupRequest
    ): SignupResponse
    @GET("users")
    suspend fun login(
        @Query("email") email: String
    ): List<LoginResponse>
}