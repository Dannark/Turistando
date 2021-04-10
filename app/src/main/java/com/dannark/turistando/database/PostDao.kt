package com.dannark.turistando.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PostDao {
    @Insert
    fun insert(post: Post)

    @Update
    fun update(post: Post)

    @Query("SELECT * FROM posts WHERE postId = :key")
    fun get(key: Long): Post

    @Query("SELECT * FROM posts ORDER BY postId DESC")
    fun getAll(): LiveData<List<Post>>

    @Delete
    fun delete(post: Post)

    @Delete
    fun deleteFromList(posts: List<Post>): Int

    @Query("DELETE FROM posts")
    fun clear()

    @Query("SELECT * FROM posts ORDER BY postId DESC LIMIT 1")
    fun getLastPost(): Post?
}