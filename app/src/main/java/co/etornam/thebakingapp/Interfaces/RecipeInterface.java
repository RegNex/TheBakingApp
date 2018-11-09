package co.etornam.thebakingapp.Interfaces;

import java.util.List;

import co.etornam.thebakingapp.Models.Recipe;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeInterface {

    @GET("baking.json")
    Call<List<Recipe>> getRecipes();
}
