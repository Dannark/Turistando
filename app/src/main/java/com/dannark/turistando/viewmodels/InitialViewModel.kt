package com.dannark.turistando.viewmodels

import android.app.Activity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dannark.turistando.repository.userpref.DefaultUserPreferencesRepository
import kotlinx.coroutines.launch

class InitialViewModel(activity: Activity): AndroidViewModel(activity.application) {
    private val pref = DefaultUserPreferencesRepository.getInstance(activity)

    fun saveFirstTime(enabled: Boolean) {
        viewModelScope.launch {
            pref.savePreferencesFirstIme(enabled)
        }
    }

    fun checkFirstTimeEnabled() = pref.isFirstLaunch
}