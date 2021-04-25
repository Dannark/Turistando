package com.dannark.turistando.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.dannark.turistando.database.TuristandoDatabase
import com.dannark.turistando.repository.PostsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

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
        val cm = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnected == true

        if (isConnected) {
            uiScope.launch {
                postsRepository.refreshPosts()
            }
        }
        else{
            Toast.makeText(application, "No Connection to the internet!", Toast.LENGTH_SHORT).show()
        }
    }
}