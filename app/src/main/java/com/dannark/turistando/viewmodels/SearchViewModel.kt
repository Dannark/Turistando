package com.dannark.turistando.viewmodels

import android.app.Activity
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.dannark.turistando.api.PlacesApi
import com.dannark.turistando.database.TuristandoDatabase
import com.dannark.turistando.repository.SearchRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class SearchViewModel(application:Application, activity: Activity): AndroidViewModel(application) {
    private var viewModelJob = SupervisorJob()
    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)

    private val database = TuristandoDatabase.getInstance(activity.application)

    private val placeApi = PlacesApi.getInstance(application)
    private val searchRepository = SearchRepository(placeApi, database)

    val suggestions = searchRepository.suggestions

    init {
        //val isConnected = isConnectedToInternet(activity.application)
        //placeApi.getLocationsNeayBy(activity)
    }

    fun requestSearch(typedText: String){
        uiScope.launch {
            searchRepository.refreshSearch(typedText)
        }
    }

}