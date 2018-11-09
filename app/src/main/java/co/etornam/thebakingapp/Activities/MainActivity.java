package co.etornam.thebakingapp.Activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.fxn.cue.Cue;
import com.fxn.cue.enums.Duration;
import com.fxn.cue.enums.Type;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.etornam.thebakingapp.Adapters.RecipeRVAdapter;
import co.etornam.thebakingapp.Data.LiveData;
import co.etornam.thebakingapp.IdlingResource.SimpleIdlingResource;
import co.etornam.thebakingapp.Models.Recipe;
import co.etornam.thebakingapp.R;
import co.etornam.thebakingapp.Utils.NetworkUtil;


public class MainActivity extends AppCompatActivity {

	public static String RECIPE_PARC_KEY = "recipe";

	Context context;
	@Nullable
	SimpleIdlingResource mSimpleIdlingResource;
	@BindView(R.id.recycler_view)
	RecyclerView recyclerView;
	@BindView(R.id.progressLoader)
	LinearLayout progressLoader;
	@BindView(R.id.constrainLayout1)
	ConstraintLayout constrainLayout1;
	private LiveData mLiveData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);


		final RecipeRVAdapter recyclerAdaptor = new RecipeRVAdapter(MainActivity.this);
		GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, numberOfColumns());
		recyclerView.setLayoutManager(gridLayoutManager);


		mLiveData = ViewModelProviders.of(this).get(LiveData.class);


		if (NetworkUtil.isNetworkAvailable(MainActivity.this)) {
			// Create the observer which updates the UI.
			Observer<List<Recipe>> nameObserver = recipeModels -> {

				progressLoader.setVisibility(View.GONE);

				recyclerAdaptor.updateData(recipeModels);
				recyclerView.setAdapter(recyclerAdaptor);


				assert mSimpleIdlingResource != null;
				mSimpleIdlingResource.setIdleState(true);

				recyclerAdaptor.setOnItemClickListener(position -> {
					Log.d("TAG", "onItemClick: pos" + position);

					Recipe recipe = recipeModels.get(position);

					Intent intent = new Intent(MainActivity.this, DetailsActivity.class);

					intent.putExtra(RECIPE_PARC_KEY, recipe); //Parcelable

					startActivity(intent);

				});

			};

			mLiveData.getCurrentName().observe(this, nameObserver);
		} else {
			Cue.init()
					.with(MainActivity.this)
					.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM)
					.setMessage("No internet Connection!")
					.setType(Type.DANGER)
					.setDuration(Duration.LONG)
					.show();
			startActivity(new Intent(getApplicationContext(), NoInternetActivity.class));
			finish();
		}

		//for esspresso Test
		getIdlingResource();

	}


	public IdlingResource getIdlingResource() {
		if (mSimpleIdlingResource == null) {
			mSimpleIdlingResource = new SimpleIdlingResource();
		}
		return mSimpleIdlingResource;
	}

	public int numberOfColumns() {
		DisplayMetrics displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

		int width = displayMetrics.widthPixels;
		// You can change this divider to adjust the size of the poster
		int widthDivider = 550;
		if (width < 1079 && width > 1000) {
			widthDivider = 500;
		}
		int nColumns = width / widthDivider;
		if (nColumns < 2) return 1; //to keep the grid aspect
		return nColumns;
	}


}

