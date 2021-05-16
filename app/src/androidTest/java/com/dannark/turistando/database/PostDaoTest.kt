package com.dannark.turistando.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.dannark.turistando.domain.Post
import com.dannark.turistando.util.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
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
@SmallTest
class PostDaoTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: TuristandoDatabase

    val post1 = Post(1,1620671312503, 3, 1620671312503,"Title1", "description1", 10, "https://i.ibb.co/7y4mW2n/landscape2.png","Adam","user_img","Brazil")
    val post2 = Post(2,1620671312503, 3, 1620671312503,"Title2", "description2", 20, "https://i.ibb.co/NV3VM6q/lencois-maranhences.png","Daniel","user_img","Brazil")

    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(
            getApplicationContext(),
            TuristandoDatabase::class.java
        ).build()
    }

    @After
    fun closeDb() = database.close()

    @Test
    fun insertPostAndGetByAll() = runBlockingTest {
        // GIVEN - Insert a task.
        database.postDao.insertAll(post1.asTableModel())

        // WHEN - Get the task by id from the database.
        val loaded = database.postDao.getAll().getOrAwaitValue().asDomainInModel().first()

        // THEN - The loaded data contains the expected values.
        assertThat<List<Post>>(loaded as List<Post>, notNullValue())
        assertThat(loaded.postId, `is`(post1.postId))
        assertThat(loaded.title, `is`(post1.title))
        assertThat(loaded.description, `is`(post1.description))
        assertThat(loaded.shortDescription, `is`(post1.shortDescription))
        assertThat(loaded.country, `is`(post1.country))
        assertThat(loaded.createdBy, `is`(post1.createdBy))
        assertThat(loaded.creationDate, `is`(post1.creationDate))
        assertThat(loaded.lastUpdateDate, `is`(post1.lastUpdateDate))
        assertThat(loaded.img, `is`(post1.img))
        assertThat(loaded.likes, `is`(post1.likes))
        assertThat(loaded.first_name, `is`(post1.first_name))
    }

    @Test
    fun insertAllAndGetAll() = runBlockingTest {
        database.postDao.insertAll(post1.asTableModel(), post2.asTableModel())

        val loaded = database.postDao.getAll().getOrAwaitValue().asDomainInModel()

        assertThat(loaded.size, `is`(2))
    }

    @Test
    fun insertAndDelete() = runBlockingTest {
        database.postDao.insertAll(post1.asTableModel(), post2.asTableModel())

        database.postDao.delete(post1.asTableModel())

        val loaded = database.postDao.getAll().getOrAwaitValue().asDomainInModel()

        assertThat(loaded.size, `is`(1))
        assertThat(loaded.first().postId, `is`(post2.postId))
    }
}