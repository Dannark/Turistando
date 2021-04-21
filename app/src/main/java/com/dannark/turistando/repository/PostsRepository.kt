package com.dannark.turistando.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.dannark.turistando.database.PostTable
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

                val deletedItems = findDiff(postList.asDatabaseModel(), posts.value!!.toTypedArray())
                database.postDao.delete(*deletedItems)
            }catch (e: Exception){
                Log.e("PostsRepository","Couldn't update the list from the server")
            }

        }
    }


    // Data Structured function to find missing values between two lists
    fun findDiff(arr1: Array<PostTable>, arr2: Array<Post>): Array<PostTable>{
        Log.e("PostsRepository","Post list size = ${arr1.size}")
        val found = hashMapOf<Int, Int>()
        val missing = mutableListOf<PostTable>()

        for (item in arr1){
            found[item.postId.toInt()] = (found[item.postId.toInt()]?:0) + 1
        }

        for (item in arr2){
            val id = item.postId.toInt()
            found[id] = (found[id]?:0) - 1
            if(found[id]!! < 0){
                missing.add(item.asTableModel())
            }
        }
        Log.e("PostRepository -","missing ${missing.size} elements")

        return missing.toTypedArray()
    }
}