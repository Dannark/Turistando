package com.dannark.turistando.viewmodels

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.dannark.turistando.api.PlacesApi
import com.dannark.turistando.database.TuristandoDatabase
import com.dannark.turistando.repository.*
import com.dannark.turistando.repository.userpref.DefaultUserPreferencesRepository
import com.dannark.turistando.util.isConnectedToInternet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class ExploreViewModel(val userId: Int, activity: Activity)
    : AndroidViewModel(activity.application) {

    private var viewModelJob = SupervisorJob()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
    private val pref = DefaultUserPreferencesRepository.getInstance(activity)
    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)
    private val database = TuristandoDatabase.getInstance(activity.application)
    private val placeApi = PlacesApi.getInstance(activity.application)

    private val placesRepository = PlacesRepository(database)
    private val userRepository = UsersRepository(database)
    private val friendRepository = FriendsRepository(database)
    private val searchRepository = GooglePlaceAPIRepository(placeApi, database)

    val places = placesRepository.places
    val friends = friendRepository.friends
    val placesNearBy = searchRepository.placesNearBy

    init {

    }

    fun refreshNewDataOnceADay(minutesSinceLastUpdate: Long, activity: FragmentActivity){
        val isConnected = isConnectedToInternet(activity.application)

        if (isConnected) {
            if(minutesSinceLastUpdate > 10) {
                Log.d("ExploreViewModel", "Updating all data")

                uiScope.launch {
                    placesRepository.refreshPlaces()
                }
                uiScope.launch {
                    userRepository.refreshUsers()
                    friendRepository.refreshFriend()
                }
                refreshPlacesNearBy(activity)

                updateExploreLastUpdate()
            }
            else{
                Log.d("ExploreViewModel", "Ignoring second refresh until app restarts...")
            }
        }
        else{
            Toast.makeText(activity.application, "No Connection to the internet!", Toast.LENGTH_SHORT).show()
        }
    }

    fun refreshPlacesNearBy(activity: FragmentActivity) {
        uiScope.launch {
            searchRepository.refreshPlacesNearBy(activity)
        }
    }

    fun getExploreLastUpdate(): LiveData<Long> {
        return pref.exploreLastUpdate
    }

    private fun updateExploreLastUpdate(){
        uiScope.launch {
            pref.saveExploreLastUpdate()
        }
    }
}