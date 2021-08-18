package com.stoffe.gym.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stoffe.gym.Database.Exercise;
import com.stoffe.gym.Database.Workout;
import com.stoffe.gym.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder> {

    private List<Exercise> dataSet;

    public interface OnItemClickListener {
        void onItemClick(Exercise exercise);
    }

    public interface OnItemLongClickListener {
        void onLongItemClick(Exercise exercise);
    }

    private final OnItemClickListener listener;
    private final OnItemLongClickListener longClickListener;


    public ExerciseAdapter(OnItemClickListener listener, OnItemLongClickListener longClickListener){
        this.dataSet = new ArrayList<>();
        this.listener = listener;
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exercise_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(dataSet.get(position),listener,longClickListener);

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView nameTextView;

        public ViewHolder(View view) {
            super(view);
            nameTextView = (TextView) view.findViewById(R.id.name);
        }


        public void bind(final Exercise exercise, final OnItemClickListener listener,final OnItemLongClickListener longClickListener) {
            nameTextView.setText(exercise.name);
            itemView.setOnClickListener(view ->
                    listener.onItemClick(exercise)
            );

            itemView.setOnLongClickListener(view -> {
                longClickListener.onLongItemClick(exercise);
                return false;
            });}
    }

    public void setData(List<Exercise> data){
        this.dataSet = data;
        notifyDataSetChanged();
    }
}
