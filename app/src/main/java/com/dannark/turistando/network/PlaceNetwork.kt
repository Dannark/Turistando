package com.dannark.turistando.network

import com.dannark.turistando.database.PlaceTable
import com.dannark.turistando.domain.Place
import com.dannark.turistando.util.toBitmap
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
    var img: String?,
    @Json(name = "img_bitmap")
    val imgBitmap: ByteArray?,
    val attributions: String?,
    val rating: Double?,
    @Json(name = "user_ratings_total")
    val userRatingsTotal: Int?,
    @Json(name = "price_level")
    val priceLevel: Int?,
    @Json(name = "business_status")
    val businessStatus: String?,
    val address: String?,
    @Json(name = "place_key")
    val placeKey: String?,
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
                imgBitmap = it.imgBitmap?.toBitmap(),
                attributions = it.attributions,
                rating = it.rating,
                userRatingsTotal = it.userRatingsTotal,
                priceLevel = it.priceLevel,
                businessStatus = it.businessStatus,
                address = it.address,
                placeKey = it.placeKey,
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
                imgBitmap = it.imgBitmap,
                attributions = it.attributions,
                rating = it.rating,
                userRatingsTotal = it.userRatingsTotal,
                priceLevel = it.priceLevel,
                businessStatus = it.businessStatus,
                address = it.address,
                placeKey = it.placeKey,
            )
        }.toTypedArray()
    }
}