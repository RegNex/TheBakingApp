package co.etornam.thebakingapp.Activities;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.fxn.cue.Cue;
import com.fxn.cue.enums.Duration;
import com.fxn.cue.enums.Type;

import java.util.List;

import butterknife.ButterKnife;
import co.etornam.thebakingapp.Fragments.StepDetailsFragment;
import co.etornam.thebakingapp.Interfaces.PassDataInterface;
import co.etornam.thebakingapp.Models.Ingredient;
import co.etornam.thebakingapp.Models.Recipe;
import co.etornam.thebakingapp.R;
import co.etornam.thebakingapp.Utils.PrefUtil;

public class DetailsActivity extends AppCompatActivity implements PassDataInterface {

	final static String INGREDIANT_LIST_KEY = "ingrediantlist";
	final static String RECIPE_MODEL_SAVEINSTANCE_KEY = "reciperotate";


	private Recipe recipe;
	private List<Ingredient> arrayList;
	private boolean mTwopane = false;

	private FragmentManager fragmentManager;
	private StepDetailsFragment recipeDetailStepFragment;

	private SharedPreferences shPref;
	private SharedPreferences.Editor shEditor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		ButterKnife.bind(this);
		if (savedInstanceState == null) {
			recipe = getIntent().getExtras().getParcelable(MainActivity.RECIPE_PARC_KEY);
		} else {
			recipe = savedInstanceState.getParcelable(RECIPE_MODEL_SAVEINSTANCE_KEY);
		}

		if (getSupportActionBar() != null) {
			getSupportActionBar().setTitle(recipe.getName());
			getSupportActionBar().setElevation(1);
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			getSupportActionBar().setDisplayShowHomeEnabled(true);

		}

		if (findViewById(R.id.linearLayoutforStepfragment) != null) {
			mTwopane = true;
			PrefUtil.setPhoneOrTablet(DetailsActivity.this, PrefUtil.TABLET);
		} else {
			mTwopane = false;
			PrefUtil.setPhoneOrTablet(DetailsActivity.this, PrefUtil.PHONE);

		}

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putParcelable(RECIPE_MODEL_SAVEINSTANCE_KEY, recipe);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				return true;

			case R.id.action_addto_widgets:
				Cue.init()
						.with(DetailsActivity.this)
						.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM)
						.setMessage("Added To Widgets")
						.setType(Type.SUCCESS)
						.setDuration(Duration.LONG)
						.show();

				PrefUtil.setSharedPIngredientForWidget(this, parseToString(recipe.getIngredients()));
				PrefUtil.setSharedPRecipeNameForWidget(this, recipe.getName());
				return true;
			default:
				// If we got here, the user's action was not recognized.
				// Invoke the superclass to handle it.

				return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		PrefUtil.setPositionfortabletonly(this, 0);
	}


	private String parseToString(List<Ingredient> ingredientList) {
		StringBuilder stringBuilder = new StringBuilder();
		for (int j = 0; j < ingredientList.size(); j++) {
			String a = " \u273B " + ingredientList.get(j).getIngredient()
					+ " (" + ingredientList.get(j).getQuantity()
					+ " " + ingredientList.get(j).getMeasure() + ").\n";
			stringBuilder.append(a);
		}
		return stringBuilder.toString();
	}

	@Override
	public void passData(int data) {

		StepDetailsFragment f = (StepDetailsFragment) getSupportFragmentManager().findFragmentByTag("fragment_tag_fragmentStepDetail");
		assert f != null;
		f.displayReceivedData(data);


	}

}
