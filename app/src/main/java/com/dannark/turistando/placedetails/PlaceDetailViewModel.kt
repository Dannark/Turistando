package com.dannark.turistando.placedetails

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dannark.turistando.domain.Place

class PlaceDetailViewModel(place: Place, app: Application) : AndroidViewModel(app) {

    private val _selectedPlace = MutableLiveData<Place>()
    val selectedPlaceDatabase: LiveData<Place>
        get() = _selectedPlace

    init {
        _selectedPlace.value = place
        Log.e("PlaceDetailVM","recebido placeName=${selectedPlaceDatabase.value!!.placeName} city=${selectedPlaceDatabase.value!!.city} img=${selectedPlaceDatabase.value!!.img}")
    }
}