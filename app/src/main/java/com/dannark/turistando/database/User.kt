package com.dannark.turistando.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User (

    @PrimaryKey(autoGenerate = true)
    var userId: Long = 0L,

    @ColumnInfo(name = "creation_date")
    var creationDate: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "last_update_date")
    var lastUpdateDate: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "first_name")
    var firstName: String = "",

    @ColumnInfo(name = "last_name")
    var lastName: String = "",

    @ColumnInfo(name = "email")
    var email: String = "",

    @ColumnInfo(name = "contry")
    var contry: String = "",

    @ColumnInfo(name = "img_profile")
    var imgProfile: String = "",
)