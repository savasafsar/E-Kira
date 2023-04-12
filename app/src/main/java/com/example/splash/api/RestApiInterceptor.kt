package com.example.splash.api

import com.example.splash.BuildConfig
import okhttp3.*
import java.io.IOException

class RestApiInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        println("deneme main interceptor")
        val request: Request = chain.request()
            .newBuilder()
            .addHeader("appid", "e-kira")
            .addHeader("appversion", BuildConfig.VERSION_NAME)
            .addHeader("deviceplatform", "android")
            .removeHeader("User-Agent")
            .addHeader(
                "User-Agent",
                "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:38.0) Gecko/20100101 Firefox/38.0"
            )
            .build()
        val response = chain.proceed(request)
        println(response.body().toString())
        if(response.code() in 502..504) {
            throw IOException("Server error code: " + response.code().toString() + " with error message: " + response.message().toString());
        }
        return response
    }
}