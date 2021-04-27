package com.dannark.turistando.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dannark.turistando.domain.Place
import com.dannark.turistando.util.toBitmap

@Entity(tableName = "places")
data class PlaceTable(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "place_id")
    var placeId: Long,

    @ColumnInfo(name = "creation_date")
    var creationDate: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "created_by")
    var createdBy: Long,

    @ColumnInfo(name = "last_update_date")
    var lastUpdateDate: Long,

    @ColumnInfo(name = "place_name")
    var placeName: String,

    @ColumnInfo(name = "city")
    var city: String,

    @ColumnInfo(name = "state")
    var state: String,

    @ColumnInfo(name = "country")
    var country: String,

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "img")
    var img: String?,

    @ColumnInfo(name = "img_bitmap", typeAffinity = ColumnInfo.BLOB)
    val imgBitmap: ByteArray?,

    @ColumnInfo(name = "attributions")
    val attributions: String?,

    @ColumnInfo(name = "rating")
    val rating: Double?,

    @ColumnInfo(name = "user_ratings_total")
    val userRatingsTotal: Int?,

    @ColumnInfo(name = "price_level")
    val priceLevel: Int?,

    @ColumnInfo(name = "business_status")
    val businessStatus: String?,

    @ColumnInfo(name = "address")
    val address: String?,

    @ColumnInfo(name = "place_key")
    val placeKey: String?,
)

fun List<PlaceTable>.asDomainInModel(): List<Place>{
    return map{
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