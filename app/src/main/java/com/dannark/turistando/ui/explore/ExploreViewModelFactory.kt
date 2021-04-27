package com.dannark.turistando.ui.explore

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dannark.turistando.viewmodels.ExploreViewModel

class ExploreViewModelFactory (
    private val userId: Int,
    private val activity: Activity
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExploreViewModel::class.java)) {
            return ExploreViewModel(userId, activity) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}