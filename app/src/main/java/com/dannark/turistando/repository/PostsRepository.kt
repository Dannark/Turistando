package com.dannark.turistando.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.dannark.turistando.database.TuristandoDatabase
import com.dannark.turistando.database.asDomainInModel
import com.dannark.turistando.domain.Post
import com.dannark.turistando.network.Network
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PostsRepository (private val database: TuristandoDatabase){

    val posts : LiveData<List<Post>> = Transformations.map(database.postDao.getAll()){
        it.asDomainInModel()
    }

    suspend fun refreshPosts(){
        withContext(Dispatchers.IO){
            Log.e("PostRepo","buscando posts")
            val postList = Network.turistando.getPostList().await()
            Log.e("PostRepo","dados retornados, inserindo no banco")
            database.postDao.insertAll(*postList.asDatabaseModel())
        }
    }
}