package com.dannark.turistando.viewmodels

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.dannark.turistando.api.PlacesApi
import com.dannark.turistando.database.TuristandoDatabase
import com.dannark.turistando.domain.User
import com.dannark.turistando.repository.FriendsRepository
import com.dannark.turistando.repository.PlacesRepository
import com.dannark.turistando.repository.SearchRepository
import com.dannark.turistando.repository.UsersRepository
import com.dannark.turistando.util.isConnectedToInternet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

private var queryOnce: Boolean = false

class ExploreViewModel(val userId: Int, activity: Activity)
    : AndroidViewModel(activity.application) {

    private var viewModelJob = SupervisorJob()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)

    private val database = TuristandoDatabase.getInstance(activity.application)
    private val placeApi = PlacesApi.getInstance(activity.application)

    private val placesRepository = PlacesRepository(database)
    private val userRepository = UsersRepository(database)
    private val friendRepository = FriendsRepository(database)
    private val searchRepository = SearchRepository(placeApi, database)

    val places = placesRepository.places
    val friends = friendRepository.friends
    val placesNearBy = searchRepository.placesNearBy


    val user : User?
        get() = userRepository.users.value?.get(0)

    init {
        val isConnected = isConnectedToInternet(activity.application)

        if (isConnected) {
            if(!queryOnce) {
                queryOnce = true
                uiScope.launch {
                    placesRepository.refreshPlaces()
                }
                uiScope.launch {
                    userRepository.refreshUsers()
                    friendRepository.refreshFriend()
                }
                uiScope.launch {
                    searchRepository.refreshPlacesNearBy(activity)
                }
            }
            else{
                Log.d("ExploreViewModel", "Ignoring second refresh until app restarts...")
            }
        }
        else{
            Toast.makeText(activity.application, "No Connection to the internet!", Toast.LENGTH_SHORT).show()
        }

    }
}