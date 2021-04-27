package com.dannark.turistando.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PlaceNearByDao {
    //upsert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg places: PlaceNearByTable)

    @Query("SELECT * FROM places_near_by ORDER BY place_id DESC")
    fun getAll(): LiveData<List<PlaceNearByTable>>

    @Delete
    fun delete(vararg placeNearByTable: PlaceNearByTable): Int

    @Update
    fun update(placeNearByTable: PlaceNearByTable)
}