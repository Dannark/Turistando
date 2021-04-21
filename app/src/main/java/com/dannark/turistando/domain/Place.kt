package com.dannark.turistando.domain

import android.os.Parcelable
import com.dannark.turistando.database.PlaceTable
import com.dannark.turistando.util.smartTruncate
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Place (
    var placeId: Long,
    var creationDate: Long,
    var createdBy: Long,
    var lastUpdateDate: Long,
    var placeName: String,
    var city: String,
    var state: String,
    var country: String,
    var description: String,
    var img: String,
    var swimpool: Boolean,
    var buffet: Boolean,
    var bar: Boolean
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
                swimpool = swimpool,
                buffet = buffet,
                bar = bar
        )
    }
}