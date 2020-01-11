package com.codechacha.robolectric

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowLog


@RunWith(RobolectricTestRunner::class)
class ExampleUnitTest {

    @Before
    fun setup() {
        ShadowLog.stream = System.out;
    }

    @Test
    fun textView_text_is_right() {
        val activity = Robolectric.setupActivity(MainActivity::class.java)
        assertEquals("Not set", activity.mainTextView.text)
    }

    @Test
    fun textView_when_click_button() {
        val activity = Robolectric.setupActivity(MainActivity::class.java)
        assertEquals("Not set", activity.mainTextView.text)

        activity.mainButton.performClick()
        assertEquals("Hello world!", activity.mainTextView.text)
    }

    @Test
    fun textView_text_is_right2() {
        val controller: ActivityController<MainActivity> =
                Robolectric.buildActivity(MainActivity::class.java)
        val activity = controller
                .create()
                .start()
                .resume()
                .visible()
                .get()

        assertEquals("Not set", activity.mainTextView.text)
        activity.mainButton.performClick()
        assertEquals("Hello world!", activity.mainTextView.text)

        controller
            .pause()
            .stop()
            .destroy()
    }


    @Test
    fun recreatesActivity() {
        val bundle = Bundle()

        var controller: ActivityController<MainActivity> =
                Robolectric.buildActivity(MainActivity::class.java)
        controller
            .create()
            .start()
            .resume()
            .visible()
            .get()

        // Destroy the original activity
        controller
            .saveInstanceState(bundle)
            .pause()
            .stop()
            .destroy()

        // Bring up a new activity
        controller = Robolectric.buildActivity(MainActivity::class.java)
            .create(bundle)
            .start()
            .restoreInstanceState(bundle)
            .resume()
            .visible()

        val activity = controller.get()

        // ... add assertions ...
    }

    @Test
    @Config(qualifiers = "en")
    fun localizedEnglishHello() {
        val activity = Robolectric.setupActivity(MainActivity::class.java)
        assertEquals(activity.helloTextView.text.toString(), "Hello")
    }

    @Test
    @Config(qualifiers = "ko")
    fun localizedKoreanHello() {
        val activity = Robolectric.setupActivity(MainActivity::class.java)
        assertEquals(activity.helloTextView.text.toString(), "안녕하세요")
    }

}
