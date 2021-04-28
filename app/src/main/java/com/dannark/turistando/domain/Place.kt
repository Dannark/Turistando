package com.dannark.turistando.domain

import android.graphics.Bitmap
import android.os.Parcelable
import com.dannark.turistando.database.PlaceNearByTable
import com.dannark.turistando.database.PlaceTable
import com.dannark.turistando.util.smartTruncate
import com.dannark.turistando.util.toByteArray
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Place (
    val placeId: Long,
    val creationDate: Long,
    val createdBy: Long,
    val lastUpdateDate: Long,
    val placeName: String,
    val city: String,
    val state: String,
    val country: String,
    val description: String,
    val img: String?,
    val imgBitmap: Bitmap?,
    val attributions: String?,
    val rating: Double?,
    val userRatingsTotal: Int?,
    val priceLevel: Int?,
    val businessStatus: String?,
    val address: String?,
    val placeKey: String?,

): Parcelable {

    val shortDescription: String
    get() = description.smartTruncate(200)

    fun asTableModel(): PlaceTable {
        return PlaceTable(
                placeId = placeId,
                creationDate = creationDate,
                createdBy = createdBy,
                lastUpdateDate = lastUpdateDate,
                placeName = placeName,
                city = city,
                state = state,
                country = country,
                description = description,
                img = img,
                imgBitmap = imgBitmap?.toByteArray(),
                attributions = attributions,
                rating = rating,
                userRatingsTotal = userRatingsTotal,
                priceLevel = priceLevel,
                businessStatus = businessStatus,
                address = address,
                placeKey = placeKey,
        )
    }

    fun asPlaceNearByTableModel(): PlaceNearByTable {
        return PlaceNearByTable(
                placeId = placeId,
                creationDate = creationDate,
                createdBy = createdBy,
                lastUpdateDate = lastUpdateDate,
                placeName = placeName,
                city = city,
                state = state,
                country = country,
                description = description,
                img = img,
                imgBitmap = imgBitmap?.toByteArray(),
                attributions = attributions,
                rating = rating,
                userRatingsTotal = userRatingsTotal,
                priceLevel = priceLevel,
                businessStatus = businessStatus,
                address = address,
                placeKey = placeKey,
        )
    }
}