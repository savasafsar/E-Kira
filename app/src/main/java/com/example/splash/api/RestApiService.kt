package com.example.splash.api

import android.content.Context
import com.example.splash.api.models.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestApiService {

    private var mContext: Context

    constructor(context: Context) {
        mContext = context
    }

    fun loginPost(data: LoginPost, onResult: (ApiResponse<LoginRes>?) -> Unit){
        val retrofit = ServiceBuilder(this.mContext).buildService(RestApi::class.java)
        retrofit.loginPost(data).enqueue(
            object : Callback<ApiResponse<LoginRes>> {
                override fun onFailure(call: Call<ApiResponse<LoginRes>>, t: Throwable) {
                    println(t.message)
                    //onResult(null)
                    onResult(ApiResponse(
                        isSuccess = false,
                        error = t.message.toString(),
                        headers = null,
                        result = LoginRes(user = User(), accessToken = "")
                    ))
                }
                override fun onResponse( call: Call<ApiResponse<LoginRes>>, response: Response<ApiResponse<LoginRes>>) {
                    println(response.body().toString())
                    val loginResult = response.body()
                    println(loginResult)
                    onResult(loginResult)
                }
            }
        )
    }

    fun registerPost(data: RegisterPost, onResult: (ApiResponse<RegisterRes>?) -> Unit){
        val retrofit = ServiceBuilder(this.mContext).buildService(RestApi::class.java)
        retrofit.registerPost(data).enqueue(
            object : Callback<ApiResponse<RegisterRes>> {
                override fun onFailure(call: Call<ApiResponse<RegisterRes>>, t: Throwable) {
                    println(t.message)
                    onResult(null)
                }
                override fun onResponse( call: Call<ApiResponse<RegisterRes>>, response: Response<ApiResponse<RegisterRes>>) {
                    println(response.body().toString())
                    val loginResult = response.body()
                    println(loginResult)
                    onResult(loginResult)
                }
            }
        )
    }

    fun checkAuth(onResult: (ApiResponse<AuthCheckRes>?) -> Unit) {
        val retrofit = ServiceBuilder(this.mContext).buildService(RestApi::class.java)
        retrofit.checkAuth().enqueue(
            object : Callback<ApiResponse<AuthCheckRes>> {
                override fun onFailure(call: Call<ApiResponse<AuthCheckRes>>, t: Throwable) {
                    println(t.message)
                    //onResult(null)
                    onResult(ApiResponse(
                        isSuccess = false,
                        error = t.message.toString(),
                        headers = null,
                        result = AuthCheckRes()
                    ))
                }
                override fun onResponse( call: Call<ApiResponse<AuthCheckRes>>, response: Response<ApiResponse<AuthCheckRes>>) {
                    println(response.body().toString())
                    val authResult = response.body()
                    println(authResult)
                    onResult(authResult)
                }
            }
        )
    }

    fun getCities(onResult: (ApiResponse<List<City>>?) -> Unit) {
        val retrofit = ServiceBuilder(this.mContext).buildService(RestApi::class.java)
        retrofit.getCities().enqueue(
            object : Callback<ApiResponse<List<City>>> {
                override fun onFailure(call: Call<ApiResponse<List<City>>>, t: Throwable) {
                    println("getCities Fail")
                    var d : List<City> = listOf()
                    println(t.message)
                    //onResult(null)
                    onResult(ApiResponse(
                        isSuccess = false,
                        error = t.message.toString(),
                        headers = null,
                        result = d
                    ))
                }
                override fun onResponse( call: Call<ApiResponse<List<City>>>, response: Response<ApiResponse<List<City>>>) {
                    println("getCities onResponse")
                    println(response.body().toString())
                    val authResult = response.body()
                    println(authResult)
                    onResult(authResult)
                }
            }
        )
    }

    fun getTowns(cityId : Int, onResult: (ApiResponse<List<Town>>?) -> Unit) {
        val retrofit = ServiceBuilder(this.mContext).buildService(RestApi::class.java)
        retrofit.getTowns(cityId).enqueue(
            object : Callback<ApiResponse<List<Town>>> {
                override fun onFailure(call: Call<ApiResponse<List<Town>>>, t: Throwable) {
                    println("getTowns Fail")
                    var d : List<Town> = listOf()
                    println(t.message)
                    //onResult(null)
                    onResult(ApiResponse(
                        isSuccess = false,
                        error = t.message.toString(),
                        headers = null,
                        result = d
                    ))
                }
                override fun onResponse( call: Call<ApiResponse<List<Town>>>, response: Response<ApiResponse<List<Town>>>) {
                    println("getTowns onResponse")
                    println(response.body().toString())
                    val authResult = response.body()
                    println(authResult)
                    onResult(authResult)
                }
            }
        )
    }

    fun getDistricts(townId : Int, onResult: (ApiResponse<List<District>>?) -> Unit) {
        val retrofit = ServiceBuilder(this.mContext).buildService(RestApi::class.java)
        retrofit.getDistricts(townId).enqueue(
            object : Callback<ApiResponse<List<District>>> {
                override fun onFailure(call: Call<ApiResponse<List<District>>>, t: Throwable) {
                    var d : List<District> = listOf()
                    println(t.message)
                    //onResult(null)
                    onResult(ApiResponse(
                        isSuccess = false,
                        error = t.message.toString(),
                        headers = null,
                        result = d
                    ))
                }
                override fun onResponse( call: Call<ApiResponse<List<District>>>, response: Response<ApiResponse<List<District>>>) {
                    println(response.body().toString())
                    val authResult = response.body()
                    println(authResult)
                    onResult(authResult)
                }
            }
        )
    }

    fun getQuarters(districtId : Int, onResult: (ApiResponse<List<Quarter>>?) -> Unit) {
        val retrofit = ServiceBuilder(this.mContext).buildService(RestApi::class.java)
        retrofit.getQuarters(districtId).enqueue(
            object : Callback<ApiResponse<List<Quarter>>> {
                override fun onFailure(call: Call<ApiResponse<List<Quarter>>>, t: Throwable) {
                    var d : List<Quarter> = listOf()
                    println(t.message)
                    //onResult(null)
                    onResult(ApiResponse(
                        isSuccess = false,
                        error = t.message.toString(),
                        headers = null,
                        result = d
                    ))
                }
                override fun onResponse( call: Call<ApiResponse<List<Quarter>>>, response: Response<ApiResponse<List<Quarter>>>) {
                    println(response.body().toString())
                    val authResult = response.body()
                    println(authResult)
                    onResult(authResult)
                }
            }
        )
    }

    fun logoutAuth(onResult: (ApiResponse<String>?) -> Unit) {
        val retrofit = ServiceBuilder(this.mContext).buildService(RestApi::class.java)
        retrofit.logoutAuth().enqueue(
            object : Callback<ApiResponse<String>> {
                override fun onFailure(call: Call<ApiResponse<String>>, t: Throwable) {
                    println(t.message)
                    //onResult(null)
                    onResult(ApiResponse(
                        isSuccess = false,
                        error = t.message.toString(),
                        headers = null,
                        result = "NO",
                    ))
                }
                override fun onResponse( call: Call<ApiResponse<String>>, response: Response<ApiResponse<String>>) {
                    println(response.body().toString())
                    val authResult = response.body()
                    println(authResult)
                    onResult(authResult)
                }
            }
        )
    }
}