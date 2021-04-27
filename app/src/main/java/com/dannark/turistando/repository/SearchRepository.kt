package com.dannark.turistando.repository

import android.app.Activity
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.dannark.turistando.R
import com.dannark.turistando.api.PlacesApi
import com.dannark.turistando.api.asDatabaseModel
import com.dannark.turistando.database.PlaceNearByTable
import com.dannark.turistando.database.TuristandoDatabase
import com.dannark.turistando.database.asDomainInModel
import com.dannark.turistando.domain.Place
import com.dannark.turistando.domain.Suggestion
import com.dannark.turistando.util.toByteArray
import kotlinx.coroutines.*

class SearchRepository(private val placeApi: PlacesApi, private val database: TuristandoDatabase) {
    private var viewModelJob = SupervisorJob()
    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)

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

    val placesNearBy: LiveData<List<Place>> = Transformations.map(database.placeNearByDao.getAll()){
        Log.e("SearchRepository","reading from database")
        it.asDomainInModel()
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

    suspend fun refreshPlacesNearBy(activity: Activity){
        withContext(Dispatchers.IO){
            try {
                placeApi.getLocationsNeayBy(activity, callback = { places ->
                    uiScope.launch {
                        withContext(Dispatchers.IO) {
                            saveOnLocalDatabase(places)
                        }
                    }
                })

            }catch (e: Exception){
                Log.e("SearchRepository","Couldn't retrieve the place near by list from the server")
            }
        }
    }

    private suspend fun saveOnLocalDatabase(places: List<com.google.android.libraries.places.api.model.Place>){
        withContext(Dispatchers.IO) {
            Log.e("SearchRepository","saving to database")
            val placesArray = places.asDatabaseModel()
            database.placeNearByDao.insertAll(*placesArray)

            placesArray.forEach {
                val downloadMissingImage = it.placeKey != null && it.imgBitmap == null
                if(downloadMissingImage){
                    downloadImageFromGoogleApi(it)
                }
            }
        }
    }

    private suspend fun downloadImageFromGoogleApi(placeNearBy: PlaceNearByTable){
        withContext(Dispatchers.IO) {
            placeNearBy.placeKey?.let { it ->
                placeApi.getPhoto(placeNearBy.placeKey, callback = { bitmap ->
                    uiScope.launch {
                        saveImageToDatabase(placeNearBy, bitmap)
                    }
                })
            }
        }
    }

    private suspend fun saveImageToDatabase(placeNearBy: PlaceNearByTable, bitmap: Bitmap){
        withContext(Dispatchers.IO){
            Log.e("SearchRepository","Image Downloaded! Saving to database imgId= $placeNearBy")
            placeNearBy.imgBitmap = bitmap.toByteArray()
            database.placeNearByDao.update(placeNearBy)
        }
    }
}