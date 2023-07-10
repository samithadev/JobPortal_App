package com.example.appsquad

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import database.CompanyDatabase
import database.repositories.GigRepository
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EditGigInstrumentedTest {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(EditGig::class.java)

    private lateinit var activityScenario: ActivityScenario<EditGig>

    @Before
    fun setUp() {
        activityScenario = activityScenarioRule.scenario
        activityScenario.onActivity { activity ->
            // Do any setup you need for your activity here
        }
    }

    @Test
    fun testButton() {
        Intents.init()

        Espresso.onView(ViewMatchers.withId(R.id.btnGoBackFromEditGig))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.btnGoBackFromEditGig)).perform(ViewActions.click())

        Intents.intended(IntentMatchers.hasComponent(UserGigs::class.java.name))
    }

    @Test
    fun testEditGig() {
        val repository = GigRepository(
            CompanyDatabase.getInstance(
            InstrumentationRegistry.getInstrumentation().targetContext))

        val gigTitle = "New Gig Title"
        val gigDesc = "New Gig Description"
        val gigPrice = "500"

        Espresso.onView(ViewMatchers.withId(R.id.edtEditGigTitle)).perform(ViewActions.clearText())
        Espresso.onView(ViewMatchers.withId(R.id.edtEditGigTitle)).perform(ViewActions.typeText(gigTitle))
        Espresso.onView(ViewMatchers.withId(R.id.edtEditGigDesc)).perform(ViewActions.clearText())
        Espresso.onView(ViewMatchers.withId(R.id.edtEditGigDesc)).perform(ViewActions.typeText(gigDesc))
        Espresso.onView(ViewMatchers.withId(R.id.edtEditGigPrice)).perform(ViewActions.clearText())
        Espresso.onView(ViewMatchers.withId(R.id.edtEditGigPrice)).perform(ViewActions.typeText(gigPrice))

        Espresso.onView(ViewMatchers.withId(R.id.btnEditSaveGig)).perform(ViewActions.click())

        val updatedGig = repository.getgigDetails(1)

        Assert.assertEquals(updatedGig.title, gigTitle)
        Assert.assertEquals(updatedGig.description, gigDesc)
        Assert.assertEquals(updatedGig.price, gigPrice)
    }

    @After
    fun tearDown() {
        activityScenario.close()
    }
}
