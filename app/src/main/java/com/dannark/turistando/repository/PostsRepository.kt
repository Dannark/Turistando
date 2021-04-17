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
            try {
                val postList = Network.turistando.getPostList().await()
                database.postDao.insertAll(*postList.asDatabaseModel())
            }catch (e: Exception){
                Log.e("PostsRepository","Couldn't update the list from the server")
            }

        }
    }
}