package com.dannark.turistando.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dannark.turistando.R
import com.dannark.turistando.database.Place

class HomeViewModel : ViewModel() {

    private val _count = MutableLiveData<Int>() //readable and writable
    val count : LiveData<Int> //readable
        get() = _count

    private val _eventButtonPressed = MutableLiveData<Boolean>()
    val eventButtonPressed: LiveData<Boolean>
        get() = _eventButtonPressed

    private lateinit var placeList: MutableList<Place>

    val data = mutableListOf<Place>()

    init {
        data.add(Place(0 ,"Bom Jardin de Minas","Minas Gerais", R.drawable.landscape1))
        data.add(Place(1, "Nova Iguaçu","Rio de Janeiro", R.drawable.landscape2))
        data.add(Place(2, "Rocinha","Rio de Janeiro", R.drawable.landscape3))
        data.add(Place(3, "Acari","Rio de Janeiro", R.drawable.landscape1))

        data.add(Place(4, "Nova Iguaçu","Rio de Janeiro", R.drawable.landscape2))
        data.add(Place(5, "Rocinha","Rio de Janeiro", R.drawable.landscape3))
        data.add(Place(6, "Acari","Rio de Janeiro", R.drawable.landscape1))
        data.add(Place(7, "Bom Jardin de Minas","Minas Gerais", R.drawable.landscape2))

        data.add(Place(8, "Bom Jardin de Minas","Minas Gerais", R.drawable.landscape1))
        data.add(Place(9, "Nova Iguaçu","Rio de Janeiro", R.drawable.landscape2))
        data.add(Place(10, "Rocinha","Rio de Janeiro", R.drawable.landscape3))
        data.add(Place(11, "Acari","Rio de Janeiro", R.drawable.landscape1))

        data.add(Place(12, "Nova Iguaçu","Rio de Janeiro", R.drawable.landscape2))
        data.add(Place(13, "Rocinha","Rio de Janeiro", R.drawable.landscape3))
        data.add(Place(14, "Acari","Rio de Janeiro", R.drawable.landscape1))
        data.add(Place(15, "Bom Jardin de Minas","Minas Gerais", R.drawable.landscape2))

        data.add(Place(16, "Bom Jardin de Minas","Minas Gerais", R.drawable.landscape1))
        data.add(Place(17, "Nova Iguaçu","Rio de Janeiro", R.drawable.landscape2))
        data.add(Place(18, "Rocinha","Rio de Janeiro", R.drawable.landscape3))
        data.add(Place(19, "Acari","Rio de Janeiro", R.drawable.landscape1))

        data.add(Place(19, "Nova Iguaçu","Rio de Janeiro", R.drawable.landscape2))
        data.add(Place(20, "Rocinha","Rio de Janeiro", R.drawable.landscape3))
        data.add(Place(21, "Acari","Rio de Janeiro", R.drawable.landscape1))
        data.add(Place(22, "Bom Jardin de Minas","Minas Gerais", R.drawable.landscape2))
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