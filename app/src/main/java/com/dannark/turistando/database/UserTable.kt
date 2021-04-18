package com.dannark.turistando.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dannark.turistando.domain.User

@Entity(tableName = "users")
data class UserTable (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    var userId: Long,

    @ColumnInfo(name = "creation_date")
    var creationDate: Long,

    @ColumnInfo(name = "last_update_date")
    var lastUpdateDate: Long,

    @ColumnInfo(name = "first_name")
    var firstName: String,

    @ColumnInfo(name = "last_name")
    var lastName: String,

    @ColumnInfo(name = "email")
    var email: String,

    @ColumnInfo(name = "city")
    var city: String,

    @ColumnInfo(name = "state")
    var state: String,

    @ColumnInfo(name = "country")
    var country: String,

    @ColumnInfo(name = "img")
    var img: String,
)

fun List<UserTable>.asDomainInModel(): List<User>{
    return map{
        User(
            userId = it.userId,
            creationDate = it.creationDate,
            lastUpdateDate = it.lastUpdateDate,
            firstName = it.firstName,
            lastName = it.lastName,
            email = it.email,
            city = it.city,
            state = it.state,
            country = it.country,
            img = it.img
        )
    }
}