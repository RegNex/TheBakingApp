package co.etornam.thebakingapp;


import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import co.etornam.thebakingapp.Intro.WelcomeActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

	@Rule
	public ActivityTestRule<WelcomeActivity> mActivityTestRule = new ActivityTestRule<>(WelcomeActivity.class);

	private static Matcher<View> childAtPosition(
			final Matcher<View> parentMatcher, final int position) {

		return new TypeSafeMatcher<View>() {
			@Override
			public void describeTo(Description description) {
				description.appendText("Child at position " + position + " in parent ");
				parentMatcher.describeTo(description);
			}

			@Override
			public boolean matchesSafely(View view) {
				ViewParent parent = view.getParent();
				return parent instanceof ViewGroup && parentMatcher.matches(parent)
						&& view.equals(((ViewGroup) parent).getChildAt(position));
			}
		};
	}

	@Test
	public void mainActivityTest() {
		// Added a sleep statement to match the app's execution delay.
		// The recommended way to handle such scenarios is to use Espresso idling resources:
		// https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		ViewInteraction recyclerView = onView(
				allOf(withId(R.id.recycler_view),
						childAtPosition(
								withId(R.id.constrainLayout1),
								0)));
		recyclerView.perform(actionOnItemAtPosition(0, click()));

		// Added a sleep statement to match the app's execution delay.
		// The recommended way to handle such scenarios is to use Espresso idling resources:
		// https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
		try {
			Thread.sleep(700);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		ViewInteraction actionMenuItemView = onView(
				allOf(withId(R.id.action_addto_widgets), withText("add to widgets"),
						childAtPosition(
								childAtPosition(
										withId(R.id.action_bar),
										2),
								0),
						isDisplayed()));
		actionMenuItemView.perform(click());

		ViewInteraction recyclerView2 = onView(
				allOf(withId(R.id.recycler_view_steps),
						childAtPosition(
								withClassName(is("android.widget.LinearLayout")),
								4)));
		recyclerView2.perform(actionOnItemAtPosition(0, click()));

		// Added a sleep statement to match the app's execution delay.
		// The recommended way to handle such scenarios is to use Espresso idling resources:
		// https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		ViewInteraction appCompatImageButton = onView(
				allOf(withId(R.id.exo_ffwd), withContentDescription("Fast-forward"),
						childAtPosition(
								childAtPosition(
										withClassName(is("android.widget.LinearLayout")),
										0),
								6),
						isDisplayed()));
		appCompatImageButton.perform(click());

		// Added a sleep statement to match the app's execution delay.
		// The recommended way to handle such scenarios is to use Espresso idling resources:
		// https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		ViewInteraction appCompatImageButton2 = onView(
				allOf(withId(R.id.exo_prev), withContentDescription("Previous track"),
						childAtPosition(
								childAtPosition(
										withClassName(is("android.widget.LinearLayout")),
										0),
								0),
						isDisplayed()));
		appCompatImageButton2.perform(click());

		// Added a sleep statement to match the app's execution delay.
		// The recommended way to handle such scenarios is to use Espresso idling resources:
		// https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		ViewInteraction appCompatImageButton3 = onView(
				allOf(withId(R.id.exo_ffwd), withContentDescription("Fast-forward"),
						childAtPosition(
								childAtPosition(
										withClassName(is("android.widget.LinearLayout")),
										0),
								6),
						isDisplayed()));
		appCompatImageButton3.perform(click());

		// Added a sleep statement to match the app's execution delay.
		// The recommended way to handle such scenarios is to use Espresso idling resources:
		// https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		ViewInteraction appCompatImageButton4 = onView(
				allOf(withId(R.id.exo_pause), withContentDescription("Pause"),
						childAtPosition(
								childAtPosition(
										withClassName(is("android.widget.LinearLayout")),
										0),
								5),
						isDisplayed()));
		appCompatImageButton4.perform(click());

		// Added a sleep statement to match the app's execution delay.
		// The recommended way to handle such scenarios is to use Espresso idling resources:
		// https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		ViewInteraction appCompatImageButton5 = onView(
				allOf(withContentDescription("Navigate up"),
						childAtPosition(
								allOf(withId(R.id.action_bar),
										childAtPosition(
												withId(R.id.action_bar_container),
												0)),
								1),
						isDisplayed()));
		appCompatImageButton5.perform(click());

		// Added a sleep statement to match the app's execution delay.
		// The recommended way to handle such scenarios is to use Espresso idling resources:
		// https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
		try {
			Thread.sleep(700);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		ViewInteraction appCompatImageButton6 = onView(
				allOf(withContentDescription("Navigate up"),
						childAtPosition(
								allOf(withId(R.id.action_bar),
										childAtPosition(
												withId(R.id.action_bar_container),
												0)),
								1),
						isDisplayed()));
		appCompatImageButton6.perform(click());

		// Added a sleep statement to match the app's execution delay.
		// The recommended way to handle such scenarios is to use Espresso idling resources:
		// https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
		try {
			Thread.sleep(700);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		ViewInteraction recyclerView3 = onView(
				allOf(withId(R.id.recycler_view),
						childAtPosition(
								withId(R.id.constrainLayout1),
								0)));
		recyclerView3.perform(actionOnItemAtPosition(3, click()));

		// Added a sleep statement to match the app's execution delay.
		// The recommended way to handle such scenarios is to use Espresso idling resources:
		// https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
		try {
			Thread.sleep(700);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		ViewInteraction recyclerView4 = onView(
				allOf(withId(R.id.recycler_view_steps),
						childAtPosition(
								withClassName(is("android.widget.LinearLayout")),
								4)));
		recyclerView4.perform(actionOnItemAtPosition(8, click()));

		// Added a sleep statement to match the app's execution delay.
		// The recommended way to handle such scenarios is to use Espresso idling resources:
		// https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
		try {
			Thread.sleep(4895);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		ViewInteraction appCompatImageButton7 = onView(
				allOf(withId(R.id.exo_prev), withContentDescription("Previous track"),
						childAtPosition(
								childAtPosition(
										withClassName(is("android.widget.LinearLayout")),
										0),
								0),
						isDisplayed()));
		appCompatImageButton7.perform(click());
	}
}
