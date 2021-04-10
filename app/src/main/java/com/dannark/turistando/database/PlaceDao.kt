package com.dannark.turistando.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PlaceDao {
    @Insert
    fun insert (place: Place)

    @Update
    fun update(place: Place)

    @Query("SELECT * FROM places WHERE placeId = :key")
    fun get(key: Long): Place

    @Query("SELECT * FROM places ORDER BY placeId DESC")
    fun getAll(): LiveData<List<Place>>

    @Query("SELECT * FROM places ORDER BY placeId DESC LIMIT 1")
    fun getLast(): Place?

    @Delete
    fun delete(place: Place): Int

    @Delete
    fun deleteFromList(places: List<Place>): Int

    @Query("DELETE FROM places")
    fun clear()
}