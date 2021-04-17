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
import com.dannark.turistando.repository.PlacesRepository
import com.dannark.turistando.repository.PostsRepository
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

    val places = placesRepository.places
    val posts = postsRepository.posts

    init {
        val cm = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnected == true

        if (isConnected) {
            uiScope.launch {
                postsRepository.refreshPosts()
                placesRepository.refreshPlaces()
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