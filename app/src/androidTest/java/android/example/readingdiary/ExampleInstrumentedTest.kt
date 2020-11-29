package android.example.readingdiary

import android.example.readingdiary.adapters.BookAdapter
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule
import kotlin.concurrent.fixedRateTimer

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    companion object
    {
        /**
         * Safely waits for specified time in ms
         *
         * @param ms
         */
        fun sleep(ms: Int)
        {
            try
            {
                Thread.sleep(ms.toLong())
            } catch (e: InterruptedException)
            {
                e.printStackTrace()
            }
        }
    }

    @get:Rule
    var mActivityTestRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun scrollTopositionTest(){
        // launch desired activity
        //var firstActivity: IntentsTestRule<MainActivity> = IntentsTestRule(MainActivity::class.java)
        onView(withId(R.id.book_list))
            .perform(RecyclerViewActions.scrollToPosition<BookAdapter.ViewHolder>(5))
        sleep(5000)
        onView(withId(R.id.book_list))
            .perform(RecyclerViewActions.actionOnItemAtPosition<BookAdapter.ViewHolder>(3, click()))
    }
}