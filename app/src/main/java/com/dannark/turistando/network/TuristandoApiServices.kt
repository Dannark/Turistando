package com.dannark.turistando.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://turistandoApi.dannark.com/"

private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface TuristandoApiServices{
    @GET("endpoint")
    fun getPropreties():
        Call<List<PostProperty>>
}

object TuristandoApi{
    val retrofitServices: TuristandoApiServices by lazy {
        retrofit.create(TuristandoApiServices::class.java)
    }
}