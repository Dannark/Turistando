package com.dannark.turistando.viewmodels

import android.app.Activity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.dannark.turistando.repository.userpref.DefaultUserPreferencesRepository

class MainViewModel(activity: Activity) : AndroidViewModel(activity.application){
    private val pref = DefaultUserPreferencesRepository.getInstance(activity)

    fun checkFirstTimeEnabled() = pref.isFirstLaunch
}