package com.dannark.turistando.network

import com.dannark.turistando.database.PostTable
import com.dannark.turistando.domain.Post
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostNetwork (
    @Json(name = "post_id")
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
    val img: String,

    //join table
    val first_name: String,
    val user_img: String,
    val country: String
)

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
                img = it.img,
                first_name = it.first_name,
                user_img = it.user_img,
                country = it.country,
            )
        }
    }

    fun asDatabaseModel(): Array<PostTable>{
        return posts.map {
            PostTable(
                postId = it.postId,
                creationDate = it.creationDate,
                createdBy = it.createdBy,
                lastUpdateDate = it.lastUpdateDate,
                title = it.title,
                description = it.description,
                likes = it.likes,
                img = it.img,
                first_name = it.first_name,
                user_img = it.user_img,
                country = it.country,
            )
        }.toTypedArray()
    }
}