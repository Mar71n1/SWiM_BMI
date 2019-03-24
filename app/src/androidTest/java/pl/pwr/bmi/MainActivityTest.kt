package pl.pwr.bmi


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun mainActivityTest1() {
        val massEditText = onView(withId(R.id.bmi_main_mass_edittext))
        massEditText.perform(replaceText("75"), closeSoftKeyboard())

        val heightEditText = onView(withId(R.id.bmi_main_height_edittext))
        heightEditText.perform(replaceText("178"), closeSoftKeyboard())
        
        val countButton = onView(withId(R.id.bmi_main_count_button))
        countButton.perform(click())

        val bmiNumber = onView(withId(R.id.bmi_main_bmi_number))
        bmiNumber.check(matches(withText("23.67")))
    }

    @Test
    fun mainActivityIsEverythingDisplayed() {
        onView(withId(R.id.bmi_main_image)).check(matches(isDisplayed()))
        onView(withId(R.id.bmi_main_mass_label)).check(matches(isDisplayed()))
        onView(withId(R.id.bmi_main_mass_edittext)).check(matches(isDisplayed()))
        onView(withId(R.id.bmi_main_height_label)).check(matches(isDisplayed()))
        onView(withId(R.id.bmi_main_height_edittext)).check(matches(isDisplayed()))
        onView(withId(R.id.bmi_main_bmi_number)).check(matches(isDisplayed()))
        onView(withId(R.id.bmi_main_bmi_text)).check(matches(isDisplayed()))
        onView(withId(R.id.bmi_main_more_info_button)).check(matches(isDisplayed()))
        onView(withId(R.id.bmi_main_count_button)).check(matches(isDisplayed()))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
