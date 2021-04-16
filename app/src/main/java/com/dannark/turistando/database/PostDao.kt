package com.dannark.turistando.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PostDao {
//    @Insert
//    fun insert(postDatabase: PostDatabase)
//
//    @Update
//    fun update(postDatabase: PostDatabase)
//
//    @Query("SELECT * FROM posts WHERE postId = :key")
//    fun get(key: Long): PostDatabase
//
//    @Query("SELECT * FROM posts ORDER BY postId DESC LIMIT 1")
//    fun getLast(): PostDatabase?
//
//    @Query("UPDATE posts SET likes = likes+1, last_update_date  = :now WHERE postId = :postId")
//    fun likePost(postId: Long, now: Long)
//
//    @Delete
//    fun delete(postDatabase: PostDatabase): Int
//
//    @Delete
//    fun deleteFromList(postDatabases: List<PostDatabase>): Int
//
//    @Query("DELETE FROM posts")
//    fun clear()

    //upsert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg posts: PostDatabase)

    @Query("SELECT * FROM posts ORDER BY postId DESC")
    fun getAll(): LiveData<List<PostDatabase>>
}