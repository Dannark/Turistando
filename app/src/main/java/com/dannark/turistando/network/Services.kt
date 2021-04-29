package com.dannark.turistando.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

const val PRD_URL = "http://ec2-18-224-184-195.us-east-2.compute.amazonaws.com:3335/"
const val DEV_URL = "http://192.168.0.127:3335/"

interface TuristandoServices{

    @GET("posts")
    fun getPostList():
            Deferred<PostNetworkContainer>

    @GET("places")
    fun getPlaceList():
            Deferred<PlaceNetworkContainer>

    @GET("users")
    fun getUserList():
            Deferred<UserNetworkContainer>

    @GET("friends/{id}")
    fun getFriendList(@Path("id") id: Long):
            Deferred<FriendNetworkContainer>
}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

object Network{
    private val retrofit = Retrofit.Builder()
        .baseUrl(PRD_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val turistando = retrofit.create(TuristandoServices::class.java)
}