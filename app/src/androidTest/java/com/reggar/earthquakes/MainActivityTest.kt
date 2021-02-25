package com.reggar.earthquakes

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.reggar.earthquakes.earthquakelist.presentation.adapter.EarthquakeListAdapter
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class MainActivityTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    private var mockWebServer = MockWebServer()

    @Before
    fun setup() {
        mockWebServer.start(8080)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun onLaunch_seeLoadingSpinner() {
        onView(withId(R.id.progressbar_earthquakelist))
            .check(matches(isDisplayed()))

        queueSuccessResponse()
    }

    @Test
    fun onLaunch_withContent_seeItemsInList() {
        queueSuccessResponse()

        onView(withId(R.id.recyclerview_earthquakelist))
            .check(matches(isDisplayed()))
    }

    @Test
    fun onLaunch_clickItem_seeDetailScreen() {
        queueSuccessResponse()

        onView(withId(R.id.recyclerview_earthquakelist))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<EarthquakeListAdapter.ViewHolder>(
                    0,
                    click()
                )
            )

        onView(withId(R.id.mapview_detail))
            .check(matches(isDisplayed()))
    }

    @Test
    fun onLaunch_withContent_seeErrorMessage() {
        queueErrorResponse()

        onView(withId(R.id.textview_earthquakelist_error))
            .check(matches(isDisplayed()))
    }

    private fun queueSuccessResponse() {
        mockWebServer.enqueue(
            MockResponse().setBody(
                """
                    {
                      "earthquakes": [
                        {
                          "datetime": "2011-03-11 04:46:23",
                          "depth": 24.4,
                          "lng": 142.369,
                          "src": "us",
                          "eqid": "c0001xgp",
                          "magnitude": 8.8,
                          "lat": 38.322
                        },
                        {
                          "datetime": "2012-04-11 06:38:37",
                          "depth": 22.9,
                          "lng": 93.0632,
                          "src": "us",
                          "eqid": "c000905e",
                          "magnitude": 8.6,
                          "lat": 2.311
                        },
                        {
                          "datetime": "2007-09-12 09:10:26",
                          "depth": 30,
                          "lng": 101.3815,
                          "src": "us",
                          "eqid": "2007hear",
                          "magnitude": 8.4,
                          "lat": -4.5172
                        }
                      ]
                    }
                """.trimIndent()
            )
        )
    }

    private fun queueErrorResponse() {
        mockWebServer.enqueue(MockResponse().setResponseCode(404))
    }
}
