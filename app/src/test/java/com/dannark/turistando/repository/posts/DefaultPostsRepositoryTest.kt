package com.dannark.turistando.repository.posts

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dannark.turistando.MainCoroutineRule
import com.dannark.turistando.domain.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DefaultPostsRepositoryTest{
    private val post1 = Post(1,1620671312503, 3, 1620671312503,
    "Title1", "description1", 10, "https://encrypted-tbn0.gstatic.com/licensed-image?q=tbn:ANd9GcR_wl3_49klBH8OflJGyVNTs54l9dcZGGx2jpmLVbONGgmQFgWItY6px9gwo59V6WAzHA4eXZwRxK6mNvVKZEN6Uw",
    "Adam","https://i.ibb.co/5jFyXwc/avatar-0.jpg","Brazil")

    private val post2 = Post(2,1619464050738, 1, 1619464050738,
    "Title2", "description2", 20, "https://i.ibb.co/7y4mW2n/landscape2.png",
    "Admin","https://lh3.googleusercontent.com/ogw/ADGmqu841BrInpHTWvqxdqfHwOxc-T99iboJmneZ9bH58cY=s83-c-mo","Brazil")

    private val post3 = Post(3,1619464069714, 6, 1619464069714,
    "Title3", "description3", 20, "https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcS7P_zl7vlEPs9W5eh9ewx_-rOQt7NsS2f3zz_Xyv9PbdoimGvzjKRlp980_2HgeS4TWfLG5pXGcAfvHQ",
    "Leni","https://i.ibb.co/yqdwM7d/avatar-3.jpg","Brazil")

    private val localPosts = listOf(post1, post2).sortedBy { it.postId }
    private val remotePosts = listOf(post3)

    private lateinit var postLocalDataSource: FakePostDataSource
    private lateinit var postRemoteDataSource: FakePostDataSource

    // Class under test
    private lateinit var postRepositoryDefault: DefaultPostsRepository

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun createRepository(){
        postLocalDataSource = FakePostDataSource(localPosts)
        postRemoteDataSource = FakePostDataSource(remotePosts)

        postRepositoryDefault = DefaultPostsRepository(
            postLocalDataSource, postRemoteDataSource, Dispatchers.Main
        )
    }

    @Test
    fun refreshPosts_requestAllPostsFromRemoterServerAndRemovesOldOnes() = mainCoroutineRule.runBlockingTest{
        postRepositoryDefault.refreshPosts()

        val posts = postRepositoryDefault.posts

        assertThat(posts.value, IsEqual(remotePosts))
    }
}