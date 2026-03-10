package com.example.sigupretrofit

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // Signup
    @POST("users2")
    suspend fun signup(
        @Body request: SignupRequest
    ): SignupResponse

    // Login
    @GET("users2")
    suspend fun login(
        @Query("email") email: String
    ): List<LoginResponse>

    // Saare Users lo — Home Screen ke liye
    @GET("users2")
    suspend fun getAllUsers(): List<LoginResponse>

    // Update User
    @PUT("users2/{id}")
    suspend fun updateUser(
        @Path("id") id: String,
        @Body request: SignupRequest
    ): LoginResponse

    //  Delete User
    @DELETE("users2/{id}")
    suspend fun deleteUser(
        @Path("id") id: String
    )
}