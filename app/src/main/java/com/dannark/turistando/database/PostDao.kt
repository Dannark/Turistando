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

    @Query("SELECT * FROM posts ORDER BY postId DESC LIMIT 1")
    fun getLast(): Post?

    @Query("UPDATE posts SET likes = likes+1, last_update_date  = :now WHERE postId = :postId")
    fun likePost(postId: Long, now: Long)

    @Delete
    fun delete(post: Post): Int

    @Delete
    fun deleteFromList(posts: List<Post>): Int

    @Query("DELETE FROM posts")
    fun clear()
}