package com.dannark.turistando.placedetails

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dannark.turistando.database.Place

class PlaceDetailViewModel(place: Place, app: Application) : AndroidViewModel(app) {

    private val _selectedPlace = MutableLiveData<Place>()
    val selectedPlace: LiveData<Place>
        get() = _selectedPlace

    init {
        _selectedPlace.value = place
        Log.e("PlaceDetailVM","recebido placeName=${selectedPlace.value!!.placeName} city=${selectedPlace.value!!.city} img=${selectedPlace.value!!.img}")
    }
}