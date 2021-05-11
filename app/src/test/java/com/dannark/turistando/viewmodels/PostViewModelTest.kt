package com.dannark.turistando.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dannark.turistando.util.getOrAwaitValue
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

//@RunWith(AndroidJUnit4::class)
//class PostViewModelTest{

//    private lateinit var viewModel: PostViewModel
//
//    @get:Rule
//    val instanteExecutorRule = InstantTaskExecutorRule()
//
//    @Before
//    fun setupViewModel(){
//        viewModel = PostViewModel(ApplicationProvider.getApplicationContext())
//    }
//
//    @Test
//    fun receivePosts_isNotNull(){
//        val value = viewModel.posts.getOrAwaitValue()
//
//        assertThat(value, not(nullValue()))
//    }
//}