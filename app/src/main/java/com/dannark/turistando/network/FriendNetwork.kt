package com.dannark.turistando.network

import com.dannark.turistando.database.FriendTable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FriendNetwork (

        @Json(name= "friendship_id")
        var friendshipId: Long,

        @Json(name = "user_id")
        var userId: Long,

        @Json(name = "friend_id")
        var friendId: Long,

        @Json(name = "creation_date")
        var creationDate: Long,

        @Json(name = "approved_date")
        var approvedDate: Long,

        @Json(name = "first_name")
        var firstName: String,

        @Json(name = "last_name")
        var lastName: String,

        var email: String,

        var city: String,

        var state: String,

        var country: String,

        var img: String,
)

@JsonClass(generateAdapter = true)
data class FriendNetworkContainer(val friends: List<FriendNetwork>){
    fun asDatabaseModel(): Array<FriendTable>{
        return friends.map{
            FriendTable(
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
        }.toTypedArray()
    }
}