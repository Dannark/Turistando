package com.dannark.turistando.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.dannark.turistando.database.TuristandoDatabase
import com.dannark.turistando.domain.User
import com.dannark.turistando.repository.FriendsRepository
import com.dannark.turistando.repository.PlacesRepository
import com.dannark.turistando.repository.UsersRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch


class ExploreViewModel(val userId: Int, application: Application)
    : AndroidViewModel(application) {

    private var viewModelJob = SupervisorJob()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)

    private val database = TuristandoDatabase.getInstance(application)

    private val placesRepository = PlacesRepository(database)
    private val userRepository = UsersRepository(database)
    private val friendRepository = FriendsRepository(database)

    val places = placesRepository.places
    val friends = friendRepository.friends

    val user : User?
        get() = userRepository.users.value?.get(0)

    init {
        val cm = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnected == true

        if (isConnected) {
            uiScope.launch {
                placesRepository.refreshPlaces()
                userRepository.refreshUsers()
                friendRepository.refreshFriend()
            }
        }
        else{
            Toast.makeText(application, "No Connection to the internet!", Toast.LENGTH_SHORT).show()
        }

    }
}