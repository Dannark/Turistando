package com.dannark.turistando.repository.posts

import androidx.lifecycle.LiveData
import com.dannark.turistando.database.PostTable
import com.dannark.turistando.domain.Post

interface PostDataSource {

    val posts: LiveData<List<Post>>

    suspend fun getPostList(): List<Post>

    suspend fun setPosts(postList: List<Post>)

    fun deleteItems(postList: Array<PostTable>)
}