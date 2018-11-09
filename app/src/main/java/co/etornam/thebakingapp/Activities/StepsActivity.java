package co.etornam.thebakingapp.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import co.etornam.thebakingapp.Fragments.DetailsFragment;
import co.etornam.thebakingapp.Models.Steps;
import co.etornam.thebakingapp.R;

public class StepsActivity extends AppCompatActivity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_steps);
		if (getSupportActionBar() != null) {

			getSupportActionBar().setElevation(20);
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			getSupportActionBar().setDisplayShowHomeEnabled(true);

			Steps steps = getIntent().getExtras().getParcelable(DetailsFragment.STEP_RECIPE_PARC_KEY);


			if (steps != null) {
				String actionbarname = steps.getShortDescription();
				getSupportActionBar().setTitle(actionbarname);
			}

		}


	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				// User chose the "Settings" item, show the app settings UI...
				return true;

			default:
				// If we got here, the user's action was not recognized.
				// Invoke the superclass to handle it.

				return super.onOptionsItemSelected(item);
		}

	}


}
