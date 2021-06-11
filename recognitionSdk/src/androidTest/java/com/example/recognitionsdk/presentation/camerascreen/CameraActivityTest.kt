package com.example.recognitionsdk.presentation.camerascreen

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.recognitionsdk.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Tests for [CameraActivity]
 */
@RunWith(AndroidJUnit4::class)
class CameraActivityTest {

    @get:Rule
    internal var activityRule: ActivityTestRule<CameraActivity> = ActivityTestRule(
        CameraActivity::class.java
    )

    @Test
    fun viewsAreDisplayed() {
        onView(withId(R.id.recognizeButton)).check(matches(isDisplayed()))
        onView(withId(R.id.previewView)).check(matches(isDisplayed()))
    }

}