package com.dannark.turistando.ui.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dannark.turistando.viewmodels.HomeViewModel

class HomeViewModelFactory (
    private val userId: Int,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(userId, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}