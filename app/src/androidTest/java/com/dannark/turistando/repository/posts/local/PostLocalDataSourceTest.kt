package com.dannark.turistando.repository.posts.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.dannark.turistando.database.TuristandoDatabase
import com.dannark.turistando.domain.Post
import com.dannark.turistando.util.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.notNullValue
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@MediumTest
class PostLocalDataSourceTest{

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var localDataSource: PostLocalDataSource
    private lateinit var database: TuristandoDatabase

    val post1 = Post(1,1620671312503, 3, 1620671312503,"Title1", "description1", 10, "https://i.ibb.co/7y4mW2n/landscape2.png","Adam","user_img","Brazil")

    @Before
    fun setup() {
        // Using an in-memory database for testing, because it doesn't survive killing the process.
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TuristandoDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        localDataSource =
            PostLocalDataSource(
                database.postDao
            )
    }

    @After
    fun cleanUp() {
        database.close()
    }

    @Test
    fun savePost_retrievesTask() = runBlocking {
        localDataSource.postDao.insertAll(post1.asTableModel())

        val result = localDataSource.posts.getOrAwaitValue()

        result?.let {
            assertThat(it.size, `is`(1))
            assertThat(it.first().postId, `is`(post1.postId))
            assertThat(it.first().title, `is`(post1.title))
            assertThat(it.first().description, `is`(post1.description))
            assertThat(it.first().shortDescription, `is`(post1.shortDescription))
            assertThat(it.first().country, `is`(post1.country))
            assertThat(it.first().createdBy, `is`(post1.createdBy))
            assertThat(it.first().creationDate, `is`(post1.creationDate))
            assertThat(it.first().lastUpdateDate, `is`(post1.lastUpdateDate))
            assertThat(it.first().img, `is`(post1.img))
            assertThat(it.first().likes, `is`(post1.likes))
            assertThat(it.first().first_name, `is`(post1.first_name))
        }

    }
}