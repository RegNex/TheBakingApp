package co.etornam.thebakingapp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import co.etornam.thebakingapp.Activities.MainActivity;
import co.etornam.thebakingapp.Activities.StepsActivity;
import co.etornam.thebakingapp.Adapters.StepRVAdapter;
import co.etornam.thebakingapp.Interfaces.PassDataInterface;
import co.etornam.thebakingapp.Models.Ingredient;
import co.etornam.thebakingapp.Models.Recipe;
import co.etornam.thebakingapp.Models.Steps;
import co.etornam.thebakingapp.R;
import co.etornam.thebakingapp.Utils.PrefUtil;


public class DetailsFragment extends Fragment {
    public static final String STEP_RECIPE_PARC_KEY = "stepparclable";
    public static final String TOTAL_SIZE_PARC_KEY = "size";
    public static final String POSITION_PARC_KEY = "postion";
    public static final String LIST_PARC_KEY = "list";
    TextView textView;
    String test = "";
    List<Ingredient> ingredientList = new ArrayList<>();
    List<Steps> stepsList = new ArrayList<>();

    Recipe recipe;

    PassDataInterface passDataInterface;

    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            passDataInterface = (PassDataInterface) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Error in retrieving data. Please try again");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        textView = view.findViewById(R.id.ingredientTV);

        if (getActivity().getIntent().getExtras() != null) {

            recipe = getActivity().getIntent().getExtras().getParcelable(MainActivity.RECIPE_PARC_KEY);
            ingredientList = recipe.getIngredients();
            stepsList = recipe.getSteps();
            setupingredientListtoString(ingredientList);
            recyclerview(view, stepsList);

        } else {

            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
        }


        return view;
    }


    private void recyclerview(View view, final List<Steps> stepsList) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_steps);
        StepRVAdapter recyclerAdaptor = new StepRVAdapter(getActivity());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerAdaptor.updateData(stepsList);
        recyclerView.setAdapter(recyclerAdaptor);

        recyclerAdaptor.setOnItemClickListener(position -> {

            //tablet
            if (PrefUtil.getPhoneOrTablet(getActivity()) == PrefUtil.TABLET) {

                PrefUtil.setPositionfortabletonly(getActivity(), position);
                StepDetailsFragment recipeDetailStepFragment = new StepDetailsFragment();
                // recipeDetailStepFragment.setArguments(bundle);
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.FragmentStepDetail, recipeDetailStepFragment)
                        .commit();

            }


            //phone
            else if (PrefUtil.getPhoneOrTablet(getActivity()) == PrefUtil.PHONE) {
                Intent i = new Intent(getActivity(), StepsActivity.class);
                Steps steps = stepsList.get(position);
                recipe = getActivity().getIntent().getExtras().getParcelable(MainActivity.RECIPE_PARC_KEY);
                i.putExtra(STEP_RECIPE_PARC_KEY, steps); //Parcelable
                i.putExtra(TOTAL_SIZE_PARC_KEY, recyclerAdaptor.getItemCount());
                i.putExtra(POSITION_PARC_KEY, position);
                startActivity(i);

            }
        });
    }

    private void setupingredientListtoString(List<Ingredient> ingredientList) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int j = 0; j < ingredientList.size(); j++) {
            String a = " \u273B " + ingredientList.get(j).getIngredient()
                    + " (" + ingredientList.get(j).getQuantity()
                    + " " + ingredientList.get(j).getMeasure() + ").\n";
            stringBuilder.append(a);
        }
        String finalString = stringBuilder.toString();
        textView.setText(finalString);
    }
}

