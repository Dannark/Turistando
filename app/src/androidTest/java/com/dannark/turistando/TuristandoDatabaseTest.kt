package com.dannark.turistando

import android.util.Log
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.dannark.turistando.database.*
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class TuristandoDatabaseTest {
    private lateinit var postDao: PostDao
    private lateinit var placeDao: PlaceDao
    private lateinit var userDao: UserDao
    private lateinit var db: TuristandoDatabase

    @Before
    fun createDb(){
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, TuristandoDatabase::class.java)
                // Allowing main thread queries, just for testing.
                .allowMainThreadQueries()
                .build()
        postDao = db.postDao
        placeDao = db.placeDao
        userDao = db.userDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb(){
        db.close()
    }

    // ========== post ============
    @Test
    @Throws(Exception::class)
    fun insertAndGetPost(){
        val post = Post(title = "post")
        postDao.insert(post)
        val lastPost = postDao.getLast()
        Assert.assertEquals("post", lastPost?.title)
    }

    @Test
    @Throws(Exception::class)
    fun deleteLastPost(){
        val post = Post()
        postDao.insert(post)

        val lastPost = postDao.getLast()
        var deletedRowsCount = lastPost?.let { postDao.delete(it) }

        Assert.assertEquals(1, deletedRowsCount)
    }

    // ========== place ============
    @Test
    @Throws(Exception::class)
    fun insertAndGetPlace(){
        val place = Place(city = "rio")
        placeDao.insert(place)
        val lastPlace = placeDao.getLast()
        Assert.assertEquals("rio", lastPlace?.city)
    }

    @Test
    @Throws(Exception::class)
    fun deleteLastPlace(){
        placeDao.insert(Place())
        val lastPlace = placeDao.getLast()
        val deletedRowsCount = lastPlace?.let { placeDao.delete(it) }

        Assert.assertEquals(1, deletedRowsCount)
    }

    // ========== user ============
    @Test
    @Throws(Exception::class)
    fun insertAndGetUser(){
        userDao.insert(User(firstName = "user"))
        val lastUser = userDao.getLast()
        Assert.assertEquals(lastUser?.firstName, "user")
    }

    @Test
    @Throws(Exception::class)
    fun deleteLastUser(){
        userDao.insert(User())
        val lastUser = userDao.getLast()
        val deletedRowsCount = lastUser?.let { userDao.delete(it) }

        Assert.assertEquals(1, deletedRowsCount)
    }

}