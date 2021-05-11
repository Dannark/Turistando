package com.dannark.turistando.repository.posts

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dannark.turistando.domain.Post
import com.dannark.turistando.repository.userpref.DefaultUserPreferencesRepository
import com.dannark.turistando.repository.userpref.FakeUserPreferencesRepository
import com.dannark.turistando.repository.userpref.FirstTimeSelection
import com.dannark.turistando.util.getOrAwaitValue
import com.dannark.turistando.viewmodels.PostViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PostViewModelTest {

    private lateinit var postRepository: FakePostRepository
    private lateinit var userPreferencesRepository: FakeUserPreferencesRepository
    private lateinit var postViewModel: PostViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel(){
        Dispatchers.setMain(Dispatchers.Unconfined)

        postRepository = FakePostRepository()
        val post1 = Post(1,1620671312503, 3, 1620671312503,
            "Title1", "description1", 10, "img","Adam","user_img","Brazil")
        val post2 = Post(1,1620671312503, 1, 1620671312503,
            "Title2", "description2", 10, "img","Adam","user_img","Brazil")
        val post3 = Post(1,1620671312503, 1, 1620671312503,
            "Title3", "description3", 10, "img","Adam","user_img","Brazil")
        postRepository.addPostsHelper(post1, post2, post3)

        userPreferencesRepository = FakeUserPreferencesRepository(FirstTimeSelection.TRUE)
        postViewModel = PostViewModel(userPreferencesRepository, postRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @Test
    fun refreshPost_onInitialization(){

    }

    @Test
    fun isItFirstTime(){
        val isFirstTime = postViewModel.checkFirstTimeEnabled().getOrAwaitValue()
        assertThat(isFirstTime, IsEqual(FirstTimeSelection.TRUE))
    }
}