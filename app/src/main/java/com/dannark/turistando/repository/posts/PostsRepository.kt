package com.dannark.turistando.repository.posts

import androidx.lifecycle.LiveData
import com.dannark.turistando.domain.Post

interface PostsRepository {
    val posts : LiveData<List<Post>>

    suspend fun refreshPosts()
}