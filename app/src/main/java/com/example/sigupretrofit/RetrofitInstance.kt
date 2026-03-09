package com.example.sigupretrofit

import com.squareup.moshi.Moshi

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitInstance {

    private val moshi = Moshi.Builder()

        .build()

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://69aa6b44e051e9456fa145e1.mockapi.io/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(ApiService::class.java)
    }
}