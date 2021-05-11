package com.dannark.turistando.repository.userpref

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {
    val exploreLastUpdate: LiveData<Long>
    val isFirstLaunch: LiveData<FirstTimeSelection>
    suspend fun saveExploreLastUpdate()
    suspend fun savePreferencesFirstIme(isSelected: Boolean)
}