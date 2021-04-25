package com.dannark.turistando.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.dannark.turistando.R
import com.dannark.turistando.api.PlacesApi
import com.dannark.turistando.domain.Suggestion
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchRepository(private val placeApi: PlacesApi) {
    val suggestions: LiveData<List<Suggestion>> = Transformations.map(placeApi.lastPredictedPlacesResults){
        it.run{
            map{
                Suggestion(
                        id = it.placeId,
                        iconResId = R.drawable.ic_schedule,
                        title = it.getPrimaryText(null).toString(),
                        subtitle = it.getSecondaryText(null).toString(),
                        date = System.currentTimeMillis())
            }
        }
    }

    suspend fun refreshSearch(query: String){
        withContext(Dispatchers.IO){
            try {
                placeApi.findPlacesNearBy(query)
            }catch (e: Exception){
                Log.e("SearchRepository","Couldn't update the list from the server")
            }
        }
    }
}