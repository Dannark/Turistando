package com.dannark.turistando.work

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dannark.turistando.repository.posts.DefaultPostsRepository
import retrofit2.HttpException

class RefreshDataWork (appContext: Context, params: WorkerParameters):
    CoroutineWorker(appContext, params) {

    companion object{
        const val WORK_NAME = "RefreshTuristandoDataWorker"
    }

    override suspend fun doWork(): Result {
//        val database = TuristandoDatabase.getInstance(applicationContext)

        val repository = DefaultPostsRepository.getRepository(applicationContext)

        return try {
            Log.e("RereshDataWork","REFRESHING POST DATA ")
            repository.refreshPosts()
            Result.success()
        }catch (exception: HttpException){
            Result.retry()
        }
    }


}