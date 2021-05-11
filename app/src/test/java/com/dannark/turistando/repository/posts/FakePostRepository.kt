package com.dannark.turistando.repository.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dannark.turistando.domain.Post
import kotlinx.coroutines.runBlocking

class FakePostRepository: PostsRepository {

    var postServiceData: LinkedHashMap<Long, Post> = LinkedHashMap()

    private val _posts = MutableLiveData<List<Post>>()
    override val posts: LiveData<List<Post>> get() = _posts

    override suspend fun refreshPosts() {
        _posts.value = postServiceData.values.toList()
    }

    fun addPostsHelper(vararg posts: Post){
        for(post in posts){
            postServiceData[post.postId] = post
        }
        runBlocking{ refreshPosts() }
    }
}