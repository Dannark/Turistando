package com.dannark.turistando.viewmodels

import android.app.Activity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.dannark.turistando.repository.UserPreferencesRepository

class MainViewModel(activity: Activity) : AndroidViewModel(activity.application){
    private val pref = UserPreferencesRepository.getInstance(activity)

    fun checkFirstTimeEnabled(): LiveData<UserPreferencesRepository.FirstTimeSelection> {
        return pref.firstTimePreferencesFlow.asLiveData()
    }
}