package com.dannark.turistando.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

interface TuristandoServices{

    @GET("posts")
    fun getPostList():
            Deferred<PostNetworkContainer>

    @GET("places")
    fun getPlaceList():
            Deferred<PlaceNetworkContainer>
}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

object Network{
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.0.127:3333/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val turistando = retrofit.create(TuristandoServices::class.java)
}