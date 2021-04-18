package com.dannark.turistando.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dannark.turistando.database.TuristandoDatabase
import com.dannark.turistando.domain.Place
import com.dannark.turistando.domain.User
import com.dannark.turistando.repository.PlacesRepository
import com.dannark.turistando.repository.PostsRepository
import com.dannark.turistando.repository.UsersRepository
import kotlinx.coroutines.*


class HomeViewModel(val userId: Int, application: Application)
    : AndroidViewModel(application) {

    private var viewModelJob = SupervisorJob()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)

    private val database = TuristandoDatabase.getInstance(application)
    private val postsRepository = PostsRepository(database)
    private val placesRepository = PlacesRepository(database)
    private val userRepository = UsersRepository(database)

    val places = placesRepository.places
    val posts = postsRepository.posts
    val user : User?
        get() = userRepository.users.value?.get(0)

    init {
        val cm = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnected == true

        if (isConnected) {
            uiScope.launch {
                postsRepository.refreshPosts()
                placesRepository.refreshPlaces()
                userRepository.refreshUsers()
            }
        }
        else{
            Toast.makeText(application, "No Connection to the internet!", Toast.LENGTH_SHORT).show()
        }

    }

    fun likePost(postId: Long){
        uiScope.launch { _likePost(postId) }
    }

    private suspend fun _likePost(postId: Long){
        withContext(Dispatchers.IO){

        }
    }

    private val _navigateToPlaceDetails = MutableLiveData<Place>()
    val navigateToPlaceDetails: LiveData<Place>
        get() = _navigateToPlaceDetails

    fun displayPlaceDetails(place: Place){
        _navigateToPlaceDetails.value = place
    }

    fun onPlaceDetailsCompleted(){
        _navigateToPlaceDetails.value = null
    }

}