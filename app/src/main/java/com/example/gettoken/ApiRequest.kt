package com.example.gettoken

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiRequest {
    @GET("/api/news")
    fun getNews(): Call<List<NewsModel>>
    @Headers("accept: application/json")
    @POST("/api/sendCode")
    fun postEmail(@Header("email") email: String): Call<String>
    @POST("/api/signin")
    fun postCode(@Header("code") code: String, @Header("email") email: String): Call<Token>
}