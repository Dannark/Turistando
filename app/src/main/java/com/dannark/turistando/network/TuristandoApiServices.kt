package com.dannark.turistando.network

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://turistandoApi.dannark.com/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface TuristandoApiServices{
    @GET("endpoint")
    fun getPropreties():
        Call<String>
}

object TuristandoApi{
    val retrofitServices: TuristandoApiServices by lazy {
        retrofit.create(TuristandoApiServices::class.java)
    }
}