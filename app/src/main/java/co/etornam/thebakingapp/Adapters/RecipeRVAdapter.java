package co.etornam.thebakingapp.Adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import co.etornam.thebakingapp.Models.Recipe;
import co.etornam.thebakingapp.R;


public class RecipeRVAdapter extends RecyclerView.Adapter<RecipeRVAdapter.myViewHolder> {

    private static final String TAG = "TAG";
    List<Recipe> list = new ArrayList<>();
    Context context;
    LayoutInflater layoutInflater;
    OnItemClickListener onItemClickListener;

    public RecipeRVAdapter(Context context) {
        this.context = context;
    }


    public void updateData(List newList) {
        this.list = newList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_recipe, parent, false);

        RecipeRVAdapter.myViewHolder myViewHolder = new RecipeRVAdapter.myViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, final int position) {
        Recipe RModel = list.get(position);
        holder.RecipeName.setText(RModel.getName());
        holder.textServings.setText("Servings: " + RModel.getServings()); // text Tag lib

        if (!RModel.getImage().isEmpty()) {
            Picasso.get()
                    .load(RModel.getImage())
                    .placeholder(R.drawable.placeholder)
                    .into(holder.thumbnail);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {

        this.onItemClickListener = onItemClickListener;
    }

    class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView RecipeName;
        TextView textServings;
        ConstraintLayout constraintLayout;
        LinearLayout linearLayout;
        ImageView thumbnail;

        myViewHolder(View itemView) {
            super(itemView);
            RecipeName = itemView.findViewById(R.id.item_recipe_name);
            textServings = itemView.findViewById(R.id.item_servings_tag);
            constraintLayout = itemView.findViewById(R.id.constrainLayout);
            linearLayout = itemView.findViewById(R.id.linearLayout);
            thumbnail = itemView.findViewById(R.id.recipe_thumbnail);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(getAdapterPosition());
        }
    }


}
