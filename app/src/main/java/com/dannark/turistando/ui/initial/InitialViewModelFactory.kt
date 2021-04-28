package com.dannark.turistando.ui.initial

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dannark.turistando.viewmodels.InitialViewModel

class InitialViewModelFactory (
    private val activity: Activity
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InitialViewModel::class.java)) {
            return InitialViewModel(activity) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}