package com.example.splash.api

import com.example.splash.api.models.*
import retrofit2.Call
import retrofit2.http.*

interface RestApi {
    @Headers("Content-Type: application/json")
    @POST("v1/auth/login")
    fun loginPost(@Body data: LoginPost): Call<ApiResponse<LoginRes>>

    @Headers("Content-Type: application/json")
    @POST("v1/auth/register")
    fun registerPost(@Body data: RegisterPost): Call<ApiResponse<RegisterRes>>

    @Headers("Content-Type: application/json")
    @GET("v1/auth/check")
    fun checkAuth(): Call<ApiResponse<AuthCheckRes>>

    @Headers("Content-Type: application/json")
    @GET("v1/auth/logout")
    fun logoutAuth(): Call<ApiResponse<String>>

    @Headers("Content-Type: application/json")
    @GET("v1/get-cities/1")
    fun getCities(): Call<ApiResponse<List<City>>>

    @Headers("Content-Type: application/json")
    @GET("v1/get-towns/{cityId}")
    fun getTowns(@Path(value = "cityId") cityId : Int): Call<ApiResponse<List<Town>>>

    @Headers("Content-Type: application/json")
    @GET("v1/get-districts/{townId}")
    fun getDistricts(@Path(value = "townId") townId : Int): Call<ApiResponse<List<District>>>

    @Headers("Content-Type: application/json")
    @GET("v1/get-quarters/{districtId}")
    fun getQuarters(@Path(value = "districtId") districtId : Int): Call<ApiResponse<List<Quarter>>>
}