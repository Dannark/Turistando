package com.dannark.turistando.api

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.util.Log
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dannark.turistando.R
import com.dannark.turistando.database.PlaceNearByTable
import com.dannark.turistando.util.SingletonHolder
import com.dannark.turistando.util.toLongSum
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.*
import com.google.android.libraries.places.api.net.*

class PlacesApi private constructor(applicationContext:Context) {
    private val TAG = "PlacesApi"

    private val apiKey = applicationContext.getString(R.string.google_api_key)
    private val placesClient = Places.initialize(applicationContext, apiKey).run {
        Places.createClient(applicationContext)
    }
    private val token = AutocompleteSessionToken.newInstance()

    private val _lastPredictedPlacesResult = MutableLiveData<List<AutocompletePrediction>>()
    val lastPredictedPlacesResults : LiveData<List<AutocompletePrediction>>
        get() = _lastPredictedPlacesResult

    private val currentLocation = LatLng(-22.842464, -43.322582)


    companion object : SingletonHolder<PlacesApi, Context>(::PlacesApi)


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

    fun getLocationsNeayBy(activity: Activity, callback: (places: List<Place>) -> Unit){
        Log.e(TAG, "getLocationsNeayBy")
        val placeFields: List<Place.Field> = listOf(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.RATING,
                Place.Field.USER_RATINGS_TOTAL,
                Place.Field.PRICE_LEVEL,
                Place.Field.BUSINESS_STATUS,
                Place.Field.ADDRESS,
                Place.Field.PHOTO_METADATAS,
                Place.Field.TYPES)
        val request: FindCurrentPlaceRequest = FindCurrentPlaceRequest.newInstance(placeFields)
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {

            val placeResponse = placesClient.findCurrentPlace(request)
            placeResponse.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val response = task.result

                    val list = mutableListOf<Place>()
                    for (placeLikelihood: PlaceLikelihood in response?.placeLikelihoods ?: emptyList()) {
                        if(placeLikelihood.place.photoMetadatas != null
                                && placeLikelihood.place.rating ?: 0.0 > 3.9) {
                            list.add(placeLikelihood.place)
                        }
                        Log.i(TAG,"Place '${placeLikelihood.place.name}' - (${placeLikelihood.place.types}) - likelihood: ${placeLikelihood.likelihood}")

                    }
                    callback(list)
                } else {
                    val exception = task.exception
                    if (exception is ApiException) {
                        Log.e(TAG, "Place not found: ${exception.statusCode}")
                    }
                }
            }
        } else {
            requestPermissions(activity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    1)
        }
    }

    fun getPhoto(placeId: String, callback: (bitMap: Bitmap) -> Unit) {
        val fields = listOf(Place.Field.PHOTO_METADATAS)
        val placeRequest = FetchPlaceRequest.newInstance(placeId, fields)

        placesClient.fetchPlace(placeRequest)
                .addOnSuccessListener { response: FetchPlaceResponse ->
                    val place = response.place

                    // Get the photo metadata.
                    val metada = place.photoMetadatas
                    if (metada == null || metada.isEmpty()) {
                        Log.w(TAG, "No photo metadata.")
                        return@addOnSuccessListener
                    }

                    val photoMetadata = metada.first()

                    photoMetadata?.attributions.let {
                        val photoRequest = FetchPhotoRequest.builder(photoMetadata)
                                .setMaxHeight(500) //optional
                                .setMaxHeight(300)
                                .build()
                        placesClient.fetchPhoto(photoRequest)
                                .addOnSuccessListener {
                                    val bitmap = it.bitmap
                                    callback(bitmap)
                                }
                    }
                }
    }
}

fun List<Place>.asDatabaseModel(): Array<PlaceNearByTable>{
    return map{
        PlaceNearByTable(
                placeId = it.id?.toLongSum() ?: 0,
                creationDate = System.currentTimeMillis(),
                createdBy = 1,
                lastUpdateDate = System.currentTimeMillis(),
                placeName = it.name ?: "-",
                city = it.name ?: "-",
                state = "-",
                country = "${it.rating?:"-"}",
                description = "-",
                img = null,
                imgBitmap = null,
                attributions = it.photoMetadatas?.first()?.attributions,
                rating = it.rating ?: .0,
                userRatingsTotal = it.userRatingsTotal ?: 0,
                priceLevel = it.priceLevel ?: 0,
                businessStatus = it.businessStatus.toString(),
                address = it.address,
                placeKey = it.id
        )
    }.toTypedArray()
}