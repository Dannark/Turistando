package com.dannark.turistando.network

import com.dannark.turistando.database.UserTable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserNetwork (
    @Json(name = "user_id")
    var userId: Long,
    @Json(name = "creation_date")
    var creationDate: Long,
    @Json(name = "last_update_date")
    var lastUpdateDate: Long,
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
data class UserNetworkContainer(val users: List<UserNetwork>){
    fun asDatabaseModel(): Array<UserTable>{
        return users.map{
            UserTable(
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
        }.toTypedArray()
    }
}