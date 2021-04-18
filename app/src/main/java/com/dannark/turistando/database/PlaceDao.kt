package com.dannark.turistando.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PlaceDao {
//    @Insert
//    fun insert (placeDatabase: PlaceDatabase)
//
//    @Update
//    fun update(placeDatabase: PlaceDatabase)
//
//    @Query("SELECT * FROM places WHERE placeId = :key")
//    fun get(key: Long): PlaceDatabase
//
//    @Query("SELECT * FROM places ORDER BY placeId DESC")
//    fun getAll(): LiveData<List<PlaceDatabase>>
//
//    @Query("SELECT * FROM places ORDER BY placeId DESC LIMIT 1")
//    fun getLast(): PlaceDatabase?
//
//    @Delete
//    fun delete(placeDatabase: PlaceDatabase): Int
//
//    @Delete
//    fun deleteFromList(placeDatabases: List<PlaceDatabase>): Int
//
//    @Query("DELETE FROM places")
//    fun clear()

    //upsert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg places: PlaceTable)

    @Query("SELECT * FROM places ORDER BY place_id DESC")
    fun getAll(): LiveData<List<PlaceTable>>
}