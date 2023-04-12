package com.example.splash.api

import AuthInterceptor
import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceBuilder {
    private var client = OkHttpClient.Builder()
    val mainInterceptor = RestApiInterceptor();
    var retrofitBuilder = Retrofit.Builder()
        .baseUrl(ApiConfig.baseUrl) // change this IP for testing by your actual machine IP
        .addConverterFactory(GsonConverterFactory.create())
        .client(client.build())

    constructor(context: Context) {
        var networkInterceptor = NetworkInterceptor(context)
        var authInterceptor = AuthInterceptor(context)
        this.client = client.addInterceptor(networkInterceptor).addInterceptor(mainInterceptor).addInterceptor(authInterceptor)
        this.retrofitBuilder = retrofitBuilder.client(this.client.build())
        println("ServiceBuilder")
    }

    fun<T> buildService(service: Class<T>): T{
        return retrofitBuilder.build().create(service)
    }
}
