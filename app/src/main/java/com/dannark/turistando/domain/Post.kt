package com.dannark.turistando.domain

import com.dannark.turistando.util.smartTruncate


data class Post (
    val postId: Long,
    val creationDate: Long,
    val createdBy: Long,
    val lastUpdateDate: Long,
    val title: String,
    val description: String,
    val likes: Int,
    val img: String){

    val shortDescription: String
        get() = description.smartTruncate(200)
}