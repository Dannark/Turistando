package com.dannark.turistando.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dannark.turistando.database.PlaceDao
import com.dannark.turistando.database.PostDao

class HomeViewModelFactory (
    private val placeDataSource: PlaceDao,
    private val postDataSource: PostDao,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(placeDataSource, postDataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}