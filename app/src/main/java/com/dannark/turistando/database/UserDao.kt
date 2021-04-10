package com.dannark.turistando.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {
    @Insert
    fun insert (user: User)

    @Update
    fun update(user: User)

    @Query("SELECT * FROM users WHERE userId = :key")
    fun get(key: Long): User

    @Query("SELECT * FROM users ORDER BY userId DESC")
    fun getAll(): LiveData<List<User>>

    @Query("SELECT * FROM users ORDER BY userId DESC LIMIT 1")
    fun getLast(): User?

    @Delete
    fun delete(user: User): Int

    @Delete
    fun deleteFromList(user: List<User>): Int

    @Query("DELETE FROM users")
    fun clear()
}