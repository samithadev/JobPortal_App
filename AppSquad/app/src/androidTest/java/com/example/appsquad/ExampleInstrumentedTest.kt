package com.example.appsquad

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.appsquad", appContext.packageName)
    }

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(EditCompanyProfile::class.java)
    private lateinit var activityScenario: ActivityScenario<EditCompanyProfile>
    @Before
    fun setUp() {
        activityScenario = activityScenarioRule.scenario
        activityScenario.onActivity { activity ->
            // Do any setup you need for your activity here
        }
    }



    @Test
    fun testButton(){


        Intents.init()
        onView(withId(R.id.btnUpdateCompany)).check(matches(isDisplayed()))
        onView(withId(R.id.btnUpdateCompany)).perform(click());



        intended(hasComponent(CompanyProfile::class.java.name))
    }
    @After
    fun tearDown() {
        activityScenario.close()
    }
}