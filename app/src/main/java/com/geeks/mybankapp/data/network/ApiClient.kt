package com.geeks.mybankapp.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private val loggingInterceptor: HttpLoggingInterceptor= HttpLoggingInterceptor().apply{
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://68ebaadb76b3362414ce5126.mockapi.io/")
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val accountsApi: AccountsApi = retrofit.create(AccountsApi::class.java )
}