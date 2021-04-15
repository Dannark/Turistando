package com.dannark.turistando.placedetails

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dannark.turistando.database.Place

class PlaceDetailViewModelFactory (
    private val myPlace: Place,
    private val application: Application): ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PlaceDetailViewModel::class.java)){
            return PlaceDetailViewModel(myPlace, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}