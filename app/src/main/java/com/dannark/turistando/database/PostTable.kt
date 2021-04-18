package com.dannark.turistando.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dannark.turistando.domain.Post

@Entity(tableName = "posts")
data class PostTable (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "post_id")
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

    // Joins
    @ColumnInfo(name = "first_name")
    var first_name: String,

    @ColumnInfo(name = "user_img")
    var user_img: String,

    @ColumnInfo(name = "country")
    var country: String,
)

fun List<PostTable>.asDomainInModel(): List<Post>{
    return map {
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