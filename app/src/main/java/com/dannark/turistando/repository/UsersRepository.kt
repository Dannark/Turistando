package com.dannark.turistando.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.dannark.turistando.database.TuristandoDatabase
import com.dannark.turistando.database.asDomainInModel
import com.dannark.turistando.domain.User
import com.dannark.turistando.network.Network
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UsersRepository(private val database: TuristandoDatabase) {

    val users: LiveData<List<User>> = Transformations.map(database.userDao.getAll()){
        it.asDomainInModel()
    }

    suspend fun refreshUsers(){
        withContext(Dispatchers.IO){
            try {
                val userList = Network.turistando.getUserList().await()
                database.userDao.insertAll(*userList.asDatabaseModel())
            }catch (e: Exception){
                Log.e("UserRepository","Couldn't update the list from the server")
            }
        }
    }
}