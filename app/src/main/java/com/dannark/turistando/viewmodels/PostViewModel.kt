package com.dannark.turistando.viewmodels

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.dannark.turistando.database.TuristandoDatabase
import com.dannark.turistando.repository.PostsRepository
import com.dannark.turistando.util.isConnectedToInternet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

private var queryOnce: Boolean = false

class PostViewModel(application: Application) : AndroidViewModel(application) {
    private var viewModelJob = SupervisorJob()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)
    private val database = TuristandoDatabase.getInstance(application)
    private val postsRepository = PostsRepository(database)
    val posts = postsRepository.posts

    init {
        val isConnected = isConnectedToInternet(application)

        if (isConnected) {
            if(!queryOnce) {
                queryOnce = false
                uiScope.launch {
                    postsRepository.refreshPosts()
                }
            }
            else{
                Log.d("PostViewModel", "Ignoring second refresh data until app restarts...")
            }
        }
        else{
            Toast.makeText(application, "No Connection to the internet!", Toast.LENGTH_SHORT).show()
        }
    }
}