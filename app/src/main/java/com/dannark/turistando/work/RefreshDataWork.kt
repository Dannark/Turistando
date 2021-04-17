package com.dannark.turistando.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dannark.turistando.database.TuristandoDatabase
import com.dannark.turistando.repository.PostsRepository
import retrofit2.HttpException

class RefreshDataWork (appContext: Context, params: WorkerParameters):
    CoroutineWorker(appContext, params) {

    companion object{
        const val WORK_NAME = "RefreshTuristandoDataWorker"
    }

    override suspend fun doWork(): Result {
        val database = TuristandoDatabase.getInstance(applicationContext)
        val repository = PostsRepository(database)

        return try {
            repository.refreshPosts()
            Result.success()
        }catch (exception: HttpException){
            Result.retry()
        }
    }


}