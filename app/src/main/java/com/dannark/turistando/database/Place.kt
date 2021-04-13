package com.dannark.turistando.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dannark.turistando.R

@Entity(tableName = "places")
data class Place(

        @PrimaryKey(autoGenerate = true)
        var placeId: Long = 0L,

        @ColumnInfo(name = "creation_date")
        var creationDate: Long = System.currentTimeMillis(),

        @ColumnInfo(name = "created_by")
        var createdBy: Long = 0,

        @ColumnInfo(name = "last_update_date")
        var lastUpdateDate: Long = System.currentTimeMillis(),

        @ColumnInfo(name = "city")
        var city: String? = "Poços de Caldas",

        @ColumnInfo(name = "contry")
        var contry: String? = "Brazil - MG",

        @ColumnInfo(name = "img")
        var img: String? = null
)