package com.dannark.turistando

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Room
import com.dannark.turistando.database.TuristandoDatabase
import com.dannark.turistando.repository.posts.DefaultPostsRepository
import com.dannark.turistando.repository.posts.PostDataSource
import com.dannark.turistando.repository.posts.PostsRepository
import com.dannark.turistando.repository.posts.local.PostLocalDataSource
import com.dannark.turistando.repository.posts.remote.PostRemoteDataSource

object ServiceLocator {
    private var database: TuristandoDatabase? = null

    @Volatile
    var postsRepository: PostsRepository? = null
        @VisibleForTesting set

    private val lock = Any()

    fun providePostRepository(context: Context): PostsRepository {
        synchronized(this) {
            return postsRepository ?: createPostsRepository(context)
        }
    }

    private fun createPostsRepository(context: Context): PostsRepository {
        val newRepo = DefaultPostsRepository(createPostLocalDataSource(context), PostRemoteDataSource())
        postsRepository = newRepo
        return newRepo
    }

    private fun createPostLocalDataSource(context: Context): PostDataSource {
        val database = database ?: createDataBase(context)
        return PostLocalDataSource(database.postDao)
    }

    private fun createDataBase(context: Context): TuristandoDatabase {
        val result = Room.databaseBuilder(
            context.applicationContext,
            TuristandoDatabase::class.java, "Turistando.db"
        ).build()
        database = result
        return result
    }

    @VisibleForTesting
    fun resetRepository() {
        synchronized(lock) {
            // Clear all data to avoid test pollution.
            database?.apply {
                clearAllTables()
                close()
            }
            database = null
            postsRepository = null
        }
    }
}