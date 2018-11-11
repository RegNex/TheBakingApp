package co.etornam.thebakingapp;


import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import co.etornam.thebakingapp.Activities.MainActivity;
import co.etornam.thebakingapp.Activities.StepsActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static co.etornam.thebakingapp.Activities.MainActivity.RECIPE_PARC_KEY;
import static org.hamcrest.Matchers.not;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class IntentTesting {

	/**
	 * A JUnit {@link Rule @Rule} to init and release Espresso Intents before and after each
	 * test run.
	 * <p>
	 * Rules are interceptors which are executed for each test method and will run before
	 * any of your setup code in the {@link Before @Before} method.
	 * <p>
	 * This rule is based on {@link ActivityTestRule} and will create and launch the activity
	 * for you and also expose the activity under test.
	 */
	@Rule
	public IntentsTestRule<MainActivity> mActivityRule = new IntentsTestRule<>(MainActivity.class);

	@Before
	public void stubAllExternalIntents() {
		// By default Espresso Intents does not stub any Intents. Stubbing needs to be setup before
		// every test run. In this case all external Intents will be blocked.
		intending(not(isInternal())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));
	}

	@Test
	public void intentTest() {

		// Let the UI load completely first
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		//Recyclerview scroll to position
		onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.scrollToPosition(2));

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		//Perform Recyclerview click on item at position
		onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

		//Check if intent (MainActivity to DetailsActivity) has RECIPE_PARC_KEY
		intended(hasExtraWithKey(RECIPE_PARC_KEY));

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		//Perform click action on add_to_widget menu button
		onView(withId(R.id.action_addto_widgets)).perform(ViewActions.click());

		//Goto recyclerview position
		onView(withId(R.id.recycler_view_steps)).perform(RecyclerViewActions.scrollToPosition(4));

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		//click on item at position
		onView(withId(R.id.recycler_view_steps)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		//Check if intent (MainActivity to StepsActivity) has RECIPE_INTENT_EXTRA
		intended(hasComponent(StepsActivity.class.getName()));
	}

}
