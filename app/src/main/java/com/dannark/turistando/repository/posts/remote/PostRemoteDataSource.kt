package com.dannark.turistando.repository.posts.remote

import androidx.lifecycle.LiveData
import com.dannark.turistando.database.PostTable
import com.dannark.turistando.domain.Post
import com.dannark.turistando.network.Network
import com.dannark.turistando.repository.posts.PostDataSource

class PostRemoteDataSource(): PostDataSource {
    override val posts: LiveData<List<Post>>
        get() = TODO("Not yet implemented")

    override suspend fun getPostList() : List<Post>
        = Network.turistando.getPostList().await().asDomainModel()

    override suspend fun setPosts(postList: List<Post>) {
        //NO-OP
    }

    override fun deleteItems(postList: Array<PostTable>){
        //NO-OP
    }
}