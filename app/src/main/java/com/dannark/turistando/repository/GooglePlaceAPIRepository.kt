package com.dannark.turistando.repository

import android.graphics.Bitmap
import android.util.Log
import androidx.fragment.app.FragmentActivity
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

class GooglePlaceAPIRepository(private val placeApi: PlacesApi, private val database: TuristandoDatabase) {
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
        Log.e("GooglePlaceAPIRepository","reading from database")
        it.asDomainInModel()
    }

    suspend fun refreshSearch(query: String){
        withContext(Dispatchers.IO){
            try {
                placeApi.findPlacesNearBy(query)

            }catch (e: Exception){
                Log.e("GooglePlaceAPIRepository","Couldn't update the list from the server")
            }
        }
    }

    suspend fun refreshPlacesNearBy(activity: FragmentActivity){
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
                Log.e("GooglePlaceAPIRepository","Couldn't retrieve the place near by list from the server")
            }
        }
    }

    private suspend fun saveOnLocalDatabase(places: List<com.google.android.libraries.places.api.model.Place>){
        withContext(Dispatchers.IO) {
            Log.e("GooglePlaceAPIRepository","saving to database")
            database.placeNearByDao.insertAllAndIgnoreDuplicates(*places.asDatabaseModel())

            Thread.sleep(100)
            val placesArray = placesNearBy.value

            val deletedItems = findDiff(places.asDatabaseModel(), placesArray!!.toTypedArray())
            database.placeNearByDao.delete(*deletedItems)


            Log.e("GooglePlaceAPIRepository","=== received ${placesArray.size} from database")
            placesArray.forEach {
                val downloadMissingImage = it.placeKey != null && it.imgBitmap == null
                if(downloadMissingImage){
                    Log.i("SearchRepository","requesting img of=${it.placeName}")
                    downloadImageFromGoogleApi(it.asPlaceNearByTableModel())
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
            Log.e("GooglePlaceAPIRepository","Image Downloaded! Saving to database imgId= $placeNearBy")
            placeNearBy.imgBitmap = bitmap.toByteArray()
            database.placeNearByDao.update(placeNearBy)
        }
    }

    // Data Structured function to find missing values between two lists
    fun findDiff(arr1: Array<PlaceNearByTable>, arr2: Array<Place>): Array<PlaceNearByTable>{
        Log.e("GooglePlaceAPIRepository","Place list size = ${arr1.size}")
        val found = hashMapOf<Int, Int>()
        val missing = mutableListOf<PlaceNearByTable>()

        for (item in arr1){
            found[item.placeId.toInt()] = (found[item.placeId.toInt()]?:0) + 1
        }

        for (item in arr2){
            val id = item.placeId.toInt()
            found[id] = (found[id]?:0) - 1
            if(found[id]!! < 0){
                missing.add(item.asPlaceNearByTableModel())
            }
        }
        Log.e("GooglePlaceAPIRepository -","missing ${missing.size} elements")

        return missing.toTypedArray()
    }
}