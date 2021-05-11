package com.dannark.turistando.ui.posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dannark.turistando.repository.posts.PostsRepository
import com.dannark.turistando.repository.userpref.UserPreferencesRepository
import com.dannark.turistando.viewmodels.PostViewModel
import java.lang.IllegalArgumentException

class PostViewModelFactory(
    private val pref: UserPreferencesRepository,
    private val postsRepository: PostsRepository
):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PostViewModel::class.java)){
            return PostViewModel(pref, postsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Factory")
    }
}