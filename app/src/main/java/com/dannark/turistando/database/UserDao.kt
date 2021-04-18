package com.dannark.turistando.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    fun insert (user: UserTable)

    @Query("SELECT * FROM users WHERE user_id = :key")
    fun get(key: Long): UserTable

    //upsert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg user: UserTable)

    @Query("SELECT * FROM users")
    fun getAll(): LiveData<List<UserTable>>
}