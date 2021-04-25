package com.dannark.turistando.ui.search

import android.app.Activity
import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dannark.turistando.viewmodels.SearchViewModel

class SearchViewModelFactory (
    private val application: Application,
    private val activity: Activity
    ) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(application, activity) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
