package com.dannark.turistando.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dannark.turistando.domain.Place
import com.dannark.turistando.util.getOrAwaitValue
import junit.framework.TestCase
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PlaceDetailViewModelTest {

    private lateinit var viewModel: PlaceDetailViewModel

    @get:Rule
    val instanteExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel(){
        val placeSelected = Place(
            placeId = 123,
            creationDate = System.currentTimeMillis(),
            createdBy = 1,
            lastUpdateDate = System.currentTimeMillis(),
            placeName = "lugar legal",
            city = "Rio",
            state = "Rio",
            country = "Brazil",
            description = "desc",
            img = "",
            imgBitmap = null,
            attributions = null,
            rating = 0.0,
            userRatingsTotal = 0,
            priceLevel = 0,
            businessStatus = null,
            address = null,
            placeKey = null,
        )
        viewModel = PlaceDetailViewModel(placeSelected, ApplicationProvider.getApplicationContext())
    }

    @Test
    fun receiveSelectedPlace_isNotNull(){
        val value = viewModel.selectedPlaceDatabase.getOrAwaitValue()

        assertThat(value, not(nullValue()))
        assertThat(value.placeId, `is`(123))
    }
}