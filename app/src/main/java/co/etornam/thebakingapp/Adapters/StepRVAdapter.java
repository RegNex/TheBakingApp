package co.etornam.thebakingapp.Adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.etornam.thebakingapp.Models.Steps;
import co.etornam.thebakingapp.R;


public class StepRVAdapter extends RecyclerView.Adapter<StepRVAdapter.myViewHolder> {

    private static final String TAG = "TAG";
    List<Steps> list = new ArrayList<>();
    Context context;
    LayoutInflater layoutInflater;
    OnItemClickListener onItemClickListener;

    public StepRVAdapter(Context context) {
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
        View view = layoutInflater.inflate(R.layout.item_step, parent, false);

        StepRVAdapter.myViewHolder myViewHolder = new StepRVAdapter.myViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, final int position) {
        Steps SModel = list.get(position);
        holder.step.setText(position + "-" + " " + SModel.getShortDescription());
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
        TextView step;

        myViewHolder(View itemView) {
            super(itemView);
            step = itemView.findViewById(R.id.item_step_name);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(getAdapterPosition());
        }
    }


}
