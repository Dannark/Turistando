package com.dannark.turistando

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.dannark.turistando.home.RecommendedPlacesAdapter
import com.dannark.turistando.repository.posts.PostsRepository
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    private lateinit var repository: PostsRepository

    @Before
    fun init(){
        repository = ServiceLocator.providePostRepository(getApplicationContext())
    }

    @After
    fun reset() {
        ServiceLocator.resetRepository()
    }

    @Test
    fun checksNavigationThroughPages() = runBlocking {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.exploreFragment)).perform(click())
        onView(withId(R.id.friends_near_by)).check(matches(isDisplayed()))

        onView(withId(R.id.searchFragment)).perform(click())
        onView(withId(R.id.search_field)).check(matches(isDisplayed()))

        activityScenario.close()
    }

    @Test
    fun navigateToRecommendedPlacesDetail(){
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.exploreFragment)).perform(click())

        onView(withId(R.id.recommended_list))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecommendedPlacesAdapter.ViewHolder>(0, click()))

        onView(withId(R.id.book_the_hotel))
            .perform(scrollTo())
            .perform(click())

        activityScenario.close()
    }
}