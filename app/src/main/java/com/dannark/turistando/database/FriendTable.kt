package com.dannark.turistando.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dannark.turistando.domain.Friend

@Entity(tableName = "friends")
data class FriendTable (

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "friendship_id")
        var friendshipId: Long,

        @ColumnInfo(name = "user_id")
        var userId: Long,

        @ColumnInfo(name = "friend_id")
        var friendId: Long,

        @ColumnInfo(name = "creation_date")
        var creationDate: Long,

        @ColumnInfo(name = "approved_date")
        var approvedDate: Long,

        @ColumnInfo(name = "first_name")
        var firstName: String,

        @ColumnInfo(name = "last_name")
        var lastName: String,

        var email: String,

        var city: String,

        var state: String,

        var country: String,

        var img: String,
)

fun List<FriendTable>.asDomainInModel(): List<Friend>{
    return map{
        Friend(
            friendshipId = it.friendshipId,
            userId = it.userId,
            friendId = it.friendId,
            creationDate = it.creationDate,
            approvedDate = it.approvedDate,
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
