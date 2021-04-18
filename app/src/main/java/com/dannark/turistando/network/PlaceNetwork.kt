package com.dannark.turistando.network

import com.dannark.turistando.database.PlaceTable
import com.dannark.turistando.domain.Place
import com.dannark.turistando.util.toBoolean
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlaceNetwork (
    @Json(name = "place_id")
    var placeId: Long,
    @Json(name = "creation_date")
    var creationDate: Long,
    @Json(name = "created_by")
    var createdBy: Long,
    @Json(name = "last_update_date")
    var lastUpdateDate: Long,
    @Json(name = "place_name")
    var placeName: String,
    var city: String,
    var state: String,
    var country: String,
    var description: String,
    var img: String,
    var swimpool: Int,
    var buffet: Int,
    var bar: Int?
)

@JsonClass(generateAdapter = true)
data class PlaceNetworkContainer(val places: List<PlaceNetwork>){
    fun asDomainModel(): List<Place>{
        return places.map {
            Place(
                placeId = it.placeId,
                creationDate = it.creationDate,
                createdBy = it.createdBy,
                lastUpdateDate = it.lastUpdateDate,
                placeName = it.placeName,
                city = it.city,
                state = it.state,
                country = it.country,
                description = it.description,
                img = it.img,
                swimpool = it.swimpool.toBoolean(),
                buffet = it.buffet.toBoolean(),
                bar = (it.bar?:0).toBoolean()
            )
        }
    }

    fun asDatabaseModel(): Array<PlaceTable>{
        return places.map {
            PlaceTable(
                placeId = it.placeId,
                creationDate = it.creationDate,
                createdBy = it.createdBy,
                lastUpdateDate = it.lastUpdateDate,
                placeName = it.placeName,
                city = it.city,
                state = it.state,
                country = it.country,
                description = it.description,
                img = it.img,
                swimpool = it.swimpool.toBoolean(),
                buffet = it.buffet.toBoolean(),
                bar = (it.bar?:0).toBoolean()
            )
        }.toTypedArray()
    }
}