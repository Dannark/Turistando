package com.dannark.turistando

import android.app.Application
import com.dannark.turistando.repository.posts.PostsRepository
import timber.log.Timber

class TuristandoApp: Application() {

    val postRepository: PostsRepository
        get() = ServiceLocator.providePostRepository(this)

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}