package com.dannark.turistando.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.dannark.turistando.database.TuristandoDatabase
import com.dannark.turistando.database.asDomainInModel
import com.dannark.turistando.domain.Place
import com.dannark.turistando.network.Network
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PlacesRepository (private val database: TuristandoDatabase){

    val places : LiveData<List<Place>> = Transformations.map(database.placeDao.getAll()){
        it.asDomainInModel()
    }

    suspend fun refreshPlaces(){
        withContext(Dispatchers.IO){
            try {
                val placeList = Network.turistando.getPlaceList().await()
                database.placeDao.insertAll(*placeList.asDatabaseModel())
            }catch (e: Exception){
                Log.e("PlacesRepository","Couldn't update the list from the server")
            }
        }
    }
}