package com.dannark.turistando.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dannark.turistando.R
import java.sql.Date

@Entity(tableName = "posts")
data class Post (
        
        @PrimaryKey(autoGenerate = true)
        var postId: Long = 0L,

        @ColumnInfo(name = "creation_date")
        var creationDate: Long = System.currentTimeMillis(),

        @ColumnInfo(name = "created_by")
        var createdBy: Long = 1,

        @ColumnInfo(name = "last_update_date")
        var lastUpdateDate: Long = System.currentTimeMillis(),

        @ColumnInfo(name = "title")
        var title: String = "",

        @ColumnInfo(name = "description")
        var description: String = "",

        @ColumnInfo(name = "img")
        var img: Int = R.drawable.landscape1,
)