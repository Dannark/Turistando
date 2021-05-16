package com.dannark.turistando.repository.posts

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.dannark.turistando.database.PostTable
import com.dannark.turistando.database.TuristandoDatabase
import com.dannark.turistando.domain.Post
import com.dannark.turistando.domain.asDatabaseModel
import com.dannark.turistando.repository.posts.local.PostLocalDataSource
import com.dannark.turistando.repository.posts.remote.PostRemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DefaultPostsRepository (
    private val postLocalDataSource: PostDataSource,
    private val postRemoteDataSource: PostDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO) : PostsRepository {

    override val posts : LiveData<List<Post>> = postLocalDataSource.posts

    override suspend fun refreshPosts(){
        withContext(ioDispatcher){
            try {
                val postList = postRemoteDataSource.getPostList()
                postLocalDataSource.setPosts(postList)

                val deletedItems = findDiff(postList.asDatabaseModel(), posts.value!!.toTypedArray())
                postLocalDataSource.deleteItems(deletedItems)
            }catch (e: Exception){
                Log.e("PostsRepository","Couldn't update the list from the server")
            }
        }
    }

    // Data Structured function to find missing values between two lists
    fun findDiff(arr1: Array<PostTable>, arr2: Array<Post>): Array<PostTable>{
//        Log.e("PostsRepository","Post list size = ${arr1.size}")
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
//        Log.e("PostRepository -","extra ${missing.size} elements to be deleted")

        return missing.toTypedArray()
    }
}