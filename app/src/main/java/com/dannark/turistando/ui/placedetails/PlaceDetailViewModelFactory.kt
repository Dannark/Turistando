package com.dannark.turistando.ui.placedetails

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dannark.turistando.domain.Place
import com.dannark.turistando.viewmodels.PlaceDetailViewModel

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