package com.dannark.turistando.domain

import com.dannark.turistando.database.PostTable
import com.dannark.turistando.util.smartTruncate


data class Post (
    val postId: Long,
    val creationDate: Long,
    val createdBy: Long,
    val lastUpdateDate: Long,
    val title: String,
    val description: String,
    val likes: Int,
    val img: String,

    //join table
    val first_name: String,
    val user_img: String,
    val country: String){

    val shortDescription: String
        get() = description.smartTruncate(120)

    val shortTitle: String
        get() = title.smartTruncate(35)

    fun asTableModel(): PostTable {
        return PostTable(
                postId = postId,
                creationDate = creationDate,
                createdBy = createdBy,
                lastUpdateDate = lastUpdateDate,
                title = title,
                description = description,
                likes = likes,
                img = img,
                first_name = first_name,
                user_img = user_img,
                country = country,
        )
    }
}

fun List<Post>.asDatabaseModel(): Array<PostTable>{
    return this.map {
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