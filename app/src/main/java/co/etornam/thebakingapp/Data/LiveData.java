package co.etornam.thebakingapp.Data;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.util.List;

import co.etornam.thebakingapp.Interfaces.RecipeInterface;
import co.etornam.thebakingapp.Models.Recipe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static co.etornam.thebakingapp.Common.Constants.URL;

public class LiveData extends ViewModel {


    // Create a LiveData with a String
    private MutableLiveData<List<Recipe>> mCurrentName;

    public MutableLiveData<List<Recipe>> getCurrentName() {
        if (mCurrentName == null) {
            mCurrentName = new MutableLiveData<>();
            getRecipes();
        }
        //mCurrentName.postValue(respons[0]);
        return mCurrentName;
    }


    public void getRecipes() {
        final String[] respons = {"000"};
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RecipeInterface recipesInterface = retrofit.create(RecipeInterface.class);

        recipesInterface.getRecipes().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                List<Recipe> recipes = response.body();
                if (recipes != null) {
                    mCurrentName.postValue(recipes);
                    Log.e("tag", "onResponse: recipes.size() = " + recipes.size());
                }

            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                t.getLocalizedMessage();
                Log.e("tag", "failure to retrive data");
            }
        });
    }


// Rest of the ViewModel...
}