package com.example.recognitionsdk.presentation.camerascreen

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.recognitionsdk.R
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Tests for [CameraActivity]
 */
@RunWith(AndroidJUnit4::class)
class CameraActivityTest {

    @get:Rule
    internal var activityRule: ActivityScenarioRule<CameraActivity> = ActivityScenarioRule(
        CameraActivity::class.java
    )

    @Test
    fun viewsAreDisplayed() {
        onView(withId(R.id.recognizeButton)).check(matches(isDisplayed()))
        onView(withId(R.id.previewView)).check(matches(isDisplayed()))
        onView(withId(R.id.progressBar)).check(matches(not(isDisplayed())))
    }

    @Test
    fun recognizeButton_click() {
        onView(withId(R.id.recognizeButton)).perform(click())

        onView(withId(R.id.recognizeButton)).check(matches(not(isDisplayed())))
        onView(withId(R.id.previewView)).check(matches(isDisplayed()))
        onView(withId(R.id.progressBar)).check(matches(isDisplayed()))
    }
}