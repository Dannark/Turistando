package com.dannark.turistando.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FriendDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg friends: FriendTable)

    @Query("SELECT * FROM friends")
    fun getAll(): LiveData<List<FriendTable>>
}