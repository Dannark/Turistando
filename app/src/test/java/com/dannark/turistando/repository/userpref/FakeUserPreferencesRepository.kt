package com.dannark.turistando.repository.userpref

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.flow.Flow

class FakeUserPreferencesRepository(private val isFirstTime : FirstTimeSelection): UserPreferencesRepository {

    private val _isFirstLaunch: MutableLiveData<FirstTimeSelection> = MutableLiveData(isFirstTime)

    override val exploreLastUpdate: LiveData<Long>
        get() = TODO("Not yet implemented")


    override val isFirstLaunch: LiveData<FirstTimeSelection>
        get() = _isFirstLaunch

    override suspend fun saveExploreLastUpdate() {
        TODO("Not yet implemented")
    }

    override suspend fun savePreferencesFirstIme(isSelected: Boolean) {
        TODO("Not yet implemented")
    }
}