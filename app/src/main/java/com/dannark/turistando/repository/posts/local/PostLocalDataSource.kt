package com.dannark.turistando.repository.posts.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.dannark.turistando.database.PostDao
import com.dannark.turistando.database.PostTable
import com.dannark.turistando.database.asDomainInModel
import com.dannark.turistando.domain.Post
import com.dannark.turistando.domain.asDatabaseModel
import com.dannark.turistando.repository.posts.PostDataSource

class PostLocalDataSource(val postDao: PostDao): PostDataSource {
    override val posts: LiveData<List<Post>> = Transformations.map(postDao.getAll()){
        it.asDomainInModel()
    }

    override suspend fun getPostList() : List<Post>{
        TODO("Not yet implemented")
    }

    override suspend fun setPosts(postList: List<Post>) {
        postDao.insertAll(*postList.asDatabaseModel())
    }

    override fun deleteItems(postList: Array<PostTable>){
        postDao.delete(*postList)
    }

}