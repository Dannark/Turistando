package com.dannark.turistando

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dannark.turistando.viewmodels.MainViewModel

class MainViewModelFactory (
    private val activity: Activity): ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(activity) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}