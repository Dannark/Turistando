package com.dannark.turistando.network

data class PostProperty (
    var postId: Long,
    var creationDate: Long,
    var createdBy: Long = 1,
    var lastUpdateDate: Long,
    var title: String,
    var description: String,
    var likes: Int,
    var img: String?,
)