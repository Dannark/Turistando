package com.dannark.turistando.network

import com.dannark.turistando.database.PostDatabase
import com.dannark.turistando.domain.Post
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostNetworkContainer (val posts: List<PostNetwork>){
    fun asDomainModel(): List<Post>{
        return posts.map {
            Post(
                postId = it.postId,
                creationDate = it.creationDate,
                createdBy = it.createdBy,
                lastUpdateDate = it.lastUpdateDate,
                title = it.title,
                description = it.description,
                likes = it.likes,
                img = it.img
            )
        }
    }

    fun asDatabaseModel(): Array<PostDatabase>{
        return posts.map {
            PostDatabase(
                postId = it.postId,
                creationDate = it.creationDate,
                createdBy = it.createdBy,
                lastUpdateDate = it.lastUpdateDate,
                title = it.title,
                description = it.description,
                likes = it.likes,
                img = it.img
            )
        }.toTypedArray()
    }
}

@JsonClass(generateAdapter = true)
data class PostNetwork (
    val postId: Long,

    @Json(name = "creation_date")
    val creationDate: Long,

    @Json(name = "created_by")
    val createdBy: Long,

    @Json(name = "last_update_date")
    val lastUpdateDate: Long,

    val title: String,
    val description: String,
    val likes: Int,
    val img: String)