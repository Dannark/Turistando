package com.dannark.turistando.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dannark.turistando.repository.userpref.DefaultUserPreferencesRepository
import com.dannark.turistando.repository.posts.PostsRepository
import com.dannark.turistando.repository.userpref.UserPreferencesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import timber.log.Timber

private var queryOnce: Boolean = false

class PostViewModel(
    private val pref: UserPreferencesRepository,
    private val postsRepository: PostsRepository) : ViewModel() {

    fun checkFirstTimeEnabled() = pref.isFirstLaunch

    val posts = postsRepository.posts

    init {
        if(!queryOnce) {
            queryOnce = false
            viewModelScope.launch {
                postsRepository.refreshPosts()
            }
        }
        else{
            Timber.i("Ignoring second refresh data until app restarts...")
        }
    }
}