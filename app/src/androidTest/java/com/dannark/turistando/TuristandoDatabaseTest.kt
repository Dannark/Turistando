package com.dannark.turistando

import android.util.Log
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.dannark.turistando.database.Post
import com.dannark.turistando.database.PostDao
import com.dannark.turistando.database.TuristandoDatabase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class TuristandoDatabaseTest {
    private lateinit var pDao: PostDao
    private lateinit var db: TuristandoDatabase

    @Before
    fun createDb(){
        Log.e("test","criando base de dados")
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, TuristandoDatabase::class.java)
                // Allowing main thread queries, just for testing.
                .allowMainThreadQueries()
                .build()
        pDao = db.postDao

        Log.e("test","testando")
    }

    @After
    @Throws(IOException::class)
    fun closeDb(){
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetPost(){

        val post = Post()
        pDao.insert(post)
        val lastPost = pDao.getLastPost()
        Assert.assertEquals(lastPost?.postId, 1L)
    }
}