package com.dannark.turistando.repository.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dannark.turistando.database.PostTable
import com.dannark.turistando.domain.Post

class FakePostDataSource(postList: List<Post> = mutableListOf()): PostDataSource {

    private var postServiceData: LinkedHashMap<Long, Post> = LinkedHashMap()

    private val _posList = MutableLiveData(postList)
    override val posts: LiveData<List<Post>> = _posList

    init {
        fillArray(postList)
    }

    private fun fillArray(postList: List<Post>){
        for (post in postList){
            postServiceData[post.postId] = post
        }
    }

    override suspend fun getPostList(): List<Post> {
        return posts.value!!
    }

    override suspend fun setPosts(postList: List<Post>) {
        fillArray(postList)
        _posList.value = postServiceData.values.toList()
        //_posList.value = postList
    }

    override fun deleteItems(postList: Array<PostTable>) {
        for (post in postList) {
            postServiceData.remove(post.postId)
        }
        _posList.value = postServiceData.values.toList()
    }
}