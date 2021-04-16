package com.dannark.turistando.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dannark.turistando.domain.Post

@Entity(tableName = "posts")
data class PostDatabase (

    @PrimaryKey(autoGenerate = true)
    var postId: Long,

    @ColumnInfo(name = "creation_date")
    var creationDate: Long,

    @ColumnInfo(name = "created_by")
    var createdBy: Long,

    @ColumnInfo(name = "last_update_date")
    var lastUpdateDate: Long,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "likes")
    var likes: Int = 0,

    @ColumnInfo(name = "img")
    var img: String,
)

fun List<PostDatabase>.asDomainInModel(): List<Post>{
    return map {
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