package com.dannark.turistando.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "places")
@Parcelize
data class Place(

        @PrimaryKey(autoGenerate = true)
        var placeId: Long = 0L,

        @ColumnInfo(name = "creation_date")
        var creationDate: Long = System.currentTimeMillis(),

        @ColumnInfo(name = "created_by")
        var createdBy: Long = 0,

        @ColumnInfo(name = "last_update_date")
        var lastUpdateDate: Long = System.currentTimeMillis(),

        @ColumnInfo(name = "place_name")
        var placeName: String? = null,

        @ColumnInfo(name = "city")
        var city: String? = null,

        @ColumnInfo(name = "state")
        var state: String? = null,

        @ColumnInfo(name = "contry")
        var contry: String? = "Brazil",

        @ColumnInfo(name = "description")
        var description: String? = null,

        @ColumnInfo(name = "img")
        var img: String? = null,


        @ColumnInfo(name = "swimpool")
        var swimpool: Boolean? = false,

        @ColumnInfo(name = "buffet")
        var buffet: Boolean? = false,

        @ColumnInfo(name = "bar")
        var bar: Boolean? = false
): Parcelable