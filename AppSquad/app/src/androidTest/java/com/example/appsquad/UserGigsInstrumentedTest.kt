package com.example.appsquad

import adapters.GigUserAdapter
import android.content.Context.MODE_PRIVATE
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import database.CompanyDatabase
import database.repositories.GigRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserGigsInstrumentedTest {
    private lateinit var repository: GigRepository

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(UserGigs::class.java)
    private lateinit var activityScenario: ActivityScenario<UserGigs>

    @Before
    fun setUp() {
        activityScenario = activityScenarioRule.scenario
        activityScenario.onActivity { activity ->
            // Do any setup you need for your activity here
            val recyclerView = activity.findViewById<RecyclerView>(R.id.rvUserGigs)
            val adapter = recyclerView.adapter as GigUserAdapter
            repository = GigRepository(CompanyDatabase.getInstance(activity.applicationContext))
            val sharedPreferences = activity.getSharedPreferences("MySession", MODE_PRIVATE)
            val cookies = sharedPreferences.getString("user", null)

            activity.runOnUiThread {
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(activity.applicationContext)

                if (cookies != null) {
                    CoroutineScope(Dispatchers.IO).launch {
                        val data = cookies?.let { repository.getUserGigs(it.toInt()) }

                        if (data != null) {
                            activity.runOnUiThread {
                                adapter.setData(data, activity)
                            }
                        }
                    }
                }
            }
        }
    }

    @Test
    fun testRecyclerViewDisplayed() {
        Espresso.onView(ViewMatchers.withId(R.id.rvUserGigs))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @After
    fun tearDown() {
        activityScenario.close()
    }
}
