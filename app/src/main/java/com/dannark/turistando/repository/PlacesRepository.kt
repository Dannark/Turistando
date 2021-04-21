package com.dannark.turistando.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.dannark.turistando.database.PlaceTable
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

                val deletedItems = findDiff(placeList.asDatabaseModel(), places.value!!.toTypedArray())
                database.placeDao.delete(*deletedItems)
            }catch (e: Exception){
                Log.e("PlacesRepository","Couldn't update the list from the server")
            }
        }
    }

    // Data Structured function to find missing values between two lists
    fun findDiff(arr1: Array<PlaceTable>, arr2: Array<Place>): Array<PlaceTable>{
        Log.e("PlaceRepository","Place list size = ${arr1.size}")
        val found = hashMapOf<Int, Int>()
        val missing = mutableListOf<PlaceTable>()

        for (item in arr1){
            found[item.placeId.toInt()] = (found[item.placeId.toInt()]?:0) + 1
        }

        for (item in arr2){
            val id = item.placeId.toInt()
            found[id] = (found[id]?:0) - 1
            if(found[id]!! < 0){
                missing.add(item.asTableModel())
            }
        }
        Log.e("PostRepository -","missing ${missing.size} elements")

        return missing.toTypedArray()
    }
}