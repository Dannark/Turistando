package com.dannark.turistando.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.dannark.turistando.database.TuristandoDatabase
import com.dannark.turistando.database.asDomainInModel
import com.dannark.turistando.domain.Friend
import com.dannark.turistando.network.Network
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FriendsRepository(private val database: TuristandoDatabase) {

    val friends: LiveData<List<Friend>> = Transformations.map(database.friendDao.getAll()){
        it.asDomainInModel()
    }

    suspend fun refreshFriend(userId: Long = 1){
        withContext(Dispatchers.IO){
            try {
                val friendList = Network.turistando.getFriendList(userId).await()
                database.friendDao.insertAll(*friendList.asDatabaseModel())

            }catch (e: Exception){
                Log.e("FriendsRepository","Couldn't update the list from the server:")
                Log.e("FriendsRepository","Error: ${e.message}")
            }
        }
    }
}