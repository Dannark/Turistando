package com.dannark.turistando.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dannark.turistando.domain.Place

@Entity(tableName = "places")
data class PlaceDatabase(

    @PrimaryKey(autoGenerate = true)
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

    @ColumnInfo(name = "contry")
    var contry: String,

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "img")
    var img: String,


    @ColumnInfo(name = "swimpool")
    var swimpool: Boolean,

    @ColumnInfo(name = "buffet")
    var buffet: Boolean,

    @ColumnInfo(name = "bar")
    var bar: Boolean
)

fun List<PlaceDatabase>.asDomainInModel(): List<Place>{
    return map{
        Place(
            placeId = it.placeId,
            creationDate = it.creationDate,
            createdBy = it.createdBy,
            lastUpdateDate = it.lastUpdateDate,
            placeName = it.placeName,
            city = it.city,
            state = it.state,
            contry = it.contry,
            description = it.description,
            img = it.img,
            swimpool = it.swimpool,
            buffet = it.buffet,
            bar = it.bar
        )
    }
}