package com.dannark.turistando.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(entities = [PostTable::class, PlaceTable::class, UserTable::class], version = 9, exportSchema = false)
abstract class TuristandoDatabase: RoomDatabase() {
    abstract val postDao: PostDao
    abstract val placeDao: PlaceDao
    abstract val userDao: UserDao

    companion object{

        /*
            Setting @Volatile make sure its value will never be cached and
            all writes and reads will be done directly to the memory so
            we avoid problems where two threads are accessing the same object
            at the same time...
         */
        @Volatile
        private var INSTANCE: TuristandoDatabase? = null

        @OptIn(InternalCoroutinesApi::class)
        fun getInstance(context: Context) : TuristandoDatabase{
            synchronized(this) {
                var instance = INSTANCE

                if(instance == null){
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            TuristandoDatabase::class.java,
                            "turistando_history_database"
                    )
                            .fallbackToDestructiveMigration()
                            .build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}