package com.dannark.turistando.api

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.*
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest

class PlacesApi(applicationContext:Context) {
    private val TAG = "PlacesApi"

    private val apiKey = "AIzaSyAUgViw6lUnZa2UPG04n2MELwLIrxvXOM8"
    private val placesClient = Places.initialize(applicationContext, apiKey).run {
        Places.createClient(applicationContext)
    }
    private val token = AutocompleteSessionToken.newInstance()

    private val _lastPredictedPlacesResult = MutableLiveData<List<AutocompletePrediction>>()
    val lastPredictedPlacesResults : LiveData<List<AutocompletePrediction>>
        get() = _lastPredictedPlacesResult

    private val currentLocation = LatLng(-22.842464, -43.322582)


    init {

    }

    fun findPlacesNearBy(query:String){
        val request =
            FindAutocompletePredictionsRequest.builder()
                // Call either setLocationBias() OR setLocationRestriction().
                //.setLocationBias(bounds)
                //.setLocationRestriction(bounds)
                .setOrigin(currentLocation)
                .setCountries("BR")
                .setTypeFilter(TypeFilter.ESTABLISHMENT)
                .setSessionToken(token)
                .setQuery(query)
                .build()

        placesClient.findAutocompletePredictions(request)
            .addOnSuccessListener { response: FindAutocompletePredictionsResponse ->
                _lastPredictedPlacesResult.value = response.autocompletePredictions

                for (prediction in response.autocompletePredictions) {
                    Log.e("test", "${prediction.getPrimaryText(null)} # ${prediction.getSecondaryText(null)} # ${prediction.placeTypes} # ${prediction.distanceMeters}")
                }

            }.addOnFailureListener { exception: Exception? ->
                if (exception is ApiException) {
                    Log.e("test", "Place not found. StatusCode: " + exception.statusCode)
                }
            }
    }

    fun getLocationsNeayBy(context:Context, activity: Activity){
        val placeFields: List<Place.Field> = listOf(Place.Field.NAME, Place.Field.RATING, Place.Field.ADDRESS)
        val request: FindCurrentPlaceRequest = FindCurrentPlaceRequest.newInstance(placeFields)
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {

            val placeResponse = placesClient.findCurrentPlace(request)
            placeResponse.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val response = task.result
                    for (placeLikelihood: PlaceLikelihood in response?.placeLikelihoods ?: emptyList()) {
                        Log.i(TAG,"Place '${placeLikelihood.place.name}' ${placeLikelihood.place.rating} ${placeLikelihood.place.address} has likelihood: ${placeLikelihood.likelihood}")
                    }
                } else {
                    val exception = task.exception
                    if (exception is ApiException) {
                        Log.e(TAG, "Place not found: ${exception.statusCode}")
                    }
                }
            }
        } else {
            // A local method to request required permissions;
            // See https://developer.android.com/training/permissions/requesting
            requestPermissions(activity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    1)
        }
    }
}