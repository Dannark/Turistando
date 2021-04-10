package com.dannark.turistando.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dannark.turistando.R
import com.dannark.turistando.database.Place
import com.dannark.turistando.database.PlaceDao
import com.dannark.turistando.database.PostDao

class HomeViewModel(val database: PlaceDao, application: Application)
    : AndroidViewModel(application) {

    private val _count = MutableLiveData<Int>() //readable and writable
    val count : LiveData<Int> //readable
        get() = _count

    private val _eventButtonPressed = MutableLiveData<Boolean>()
    val eventButtonPressed: LiveData<Boolean>
        get() = _eventButtonPressed

    private lateinit var placeList: MutableList<Place>

    val data = mutableListOf<Place>()

    init {
        val now = System.currentTimeMillis()
        data.add(Place(0, now,1, now, "Bom Jardin de Minas","Minas Gerais", R.drawable.landscape1))
        data.add(Place(1, now,1, now,"Nova Iguaçu","Rio de Janeiro", R.drawable.landscape2))
        data.add(Place(2, now,1, now,"Rocinha","Rio de Janeiro", R.drawable.landscape3))
        data.add(Place(3, now,1, now,"Acari","Rio de Janeiro", R.drawable.landscape1))

        data.add(Place(4, now,1, now,"Nova Iguaçu","Rio de Janeiro", R.drawable.landscape2))
        data.add(Place(5, now,1, now,"Rocinha","Rio de Janeiro", R.drawable.landscape3))
        data.add(Place(6, now,1, now,"Acari","Rio de Janeiro", R.drawable.landscape1))
        data.add(Place(7, now,1, now,"Bom Jardin de Minas","Minas Gerais", R.drawable.landscape2))

        data.add(Place(8, now,1, now,"Bom Jardin de Minas","Minas Gerais", R.drawable.landscape1))
        data.add(Place(9, now,1, now,"Nova Iguaçu","Rio de Janeiro", R.drawable.landscape2))
        data.add(Place(10, now,1, now,"Rocinha","Rio de Janeiro", R.drawable.landscape3))
        data.add(Place(11, now,1, now,"Acari","Rio de Janeiro", R.drawable.landscape1))

        data.add(Place(12, now,1, now,"Nova Iguaçu","Rio de Janeiro", R.drawable.landscape2))
        data.add(Place(13, now,1, now,"Rocinha","Rio de Janeiro", R.drawable.landscape3))
        data.add(Place(14, now,1, now,"Acari","Rio de Janeiro", R.drawable.landscape1))
        data.add(Place(15, now,1, now,"Bom Jardin de Minas","Minas Gerais", R.drawable.landscape2))

        data.add(Place(16, now,1, now,"Bom Jardin de Minas","Minas Gerais", R.drawable.landscape1))
        data.add(Place(17, now,1, now,"Nova Iguaçu","Rio de Janeiro", R.drawable.landscape2))
        data.add(Place(18, now,1, now,"Rocinha","Rio de Janeiro", R.drawable.landscape3))
        data.add(Place(19, now,1, now,"Acari","Rio de Janeiro", R.drawable.landscape1))

        data.add(Place(19, now,1, now,"Nova Iguaçu","Rio de Janeiro", R.drawable.landscape2))
        data.add(Place(20, now,1, now,"Rocinha","Rio de Janeiro", R.drawable.landscape3))
        data.add(Place(21, now,1, now,"Acari","Rio de Janeiro", R.drawable.landscape1))
        data.add(Place(22, now,1, now,"Bom Jardin de Minas","Minas Gerais", R.drawable.landscape2))
        Log.i("HomeViewModel", "PlaceViewModel created!")
        _count.value = 0
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("HomeViewModel", "PlaceViewModel destroyed!")
    }

    fun add() {
        _count.value = _count.value?.plus(1)
    }

    fun nextValue() {
        _count.value = (_count.value?:0)+1

        if(count.value!! > 5){
            _eventButtonPressed.value = true
        }
    }

    fun onButtonPressed(){
        _eventButtonPressed.value = false
    }

    private val _navigateToPlaceDetails = MutableLiveData<Long>()
    val navigateToPlaceDetails
        get() = _navigateToPlaceDetails

    fun onRecommendedPlaceClicked(id: Long){
        _navigateToPlaceDetails.value = id
    }

    fun onPlaceDetailsNavigated(){
        _navigateToPlaceDetails.value = null
    }
}