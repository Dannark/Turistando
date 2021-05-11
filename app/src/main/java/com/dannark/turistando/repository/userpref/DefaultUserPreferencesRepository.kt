package com.dannark.turistando.repository.userpref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class DefaultUserPreferencesRepository private constructor(context: Context) :
    UserPreferencesRepository {
    private val dataStore: DataStore<Preferences> =
        context.createDataStore(name = "settings")

    object PreferencesKeys {
        val FIRST_TIME = stringPreferencesKey("first_time")
        val EXPLORE_LAST_UPDATE = longPreferencesKey("explore_last_update")
    }

    override suspend fun saveExploreLastUpdate(){
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.EXPLORE_LAST_UPDATE] = System.currentTimeMillis()
        }
    }

    private val explorePreferencesFlow: Flow<Long> = dataStore.data
            .map { preferences ->
                preferences[PreferencesKeys.EXPLORE_LAST_UPDATE] ?: -1
            }
    override val exploreLastUpdate = explorePreferencesFlow.asLiveData()

    override suspend fun savePreferencesFirstIme(isSelected: Boolean){
        val selection = when (isSelected) {
            true -> FirstTimeSelection.TRUE.toString()
            false -> FirstTimeSelection.FALSE.toString()
        }

        dataStore.edit { preferences ->
            preferences[PreferencesKeys.FIRST_TIME] = selection
        }
    }

    private val firstTimePreferencesFlow: Flow<FirstTimeSelection> = dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { preferences ->
                val strValue =
                        preferences[PreferencesKeys.FIRST_TIME] ?: FirstTimeSelection.TRUE.toString()
                strValue.asEnumOrDefault()
            }

    override val isFirstLaunch = firstTimePreferencesFlow.asLiveData()

    companion object {
        @Volatile
        private var INSTANCE: DefaultUserPreferencesRepository? = null

        fun getInstance(context: Context): DefaultUserPreferencesRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = DefaultUserPreferencesRepository(context)
                INSTANCE = instance
                instance
            }
        }
    }

    private inline fun <reified T : Enum<T>> String.asEnumOrDefault(): T =
            enumValues<T>().first { it.name.equals(this, ignoreCase = true) }
}