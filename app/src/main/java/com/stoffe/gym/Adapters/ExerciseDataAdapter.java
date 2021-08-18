package com.stoffe.gym.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stoffe.gym.Database.ExerciseData;

import com.stoffe.gym.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ExerciseDataAdapter extends RecyclerView.Adapter<ExerciseDataAdapter.ViewHolder> {

    private List<ExerciseData> dataSet;

    public interface OnItemClickListener {
        void onItemClick(ExerciseData exercise);
    }

    public interface OnItemLongClickListener {
        void onLongItemClick(ExerciseData exercise);
    }

    private final OnItemClickListener listener;
    private final OnItemLongClickListener longClickListener;


    public ExerciseDataAdapter(OnItemClickListener listener, OnItemLongClickListener longClickListener) {
        this.dataSet = new ArrayList<>();
        this.listener = listener;
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exercise_data_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(dataSet.get(position), listener, longClickListener);

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView setsTextView;
        private final TextView repsTextView;
        private final TextView weightTextView;
        private final TextView dateTextView;

        public ViewHolder(View view) {
            super(view);
            setsTextView = view.findViewById(R.id.set);
            repsTextView = view.findViewById(R.id.rep);
            weightTextView = view.findViewById(R.id.weight);
            dateTextView = view.findViewById(R.id.date);
        }


        public void bind(final ExerciseData exercise, final OnItemClickListener listener, final OnItemLongClickListener longClickListener) {
            Context context = this.itemView.getContext();
            setsTextView.setText(context.getString(R.string.exercise_sets_ammount, exercise.sets));
            repsTextView.setText(context.getString(R.string.exercise_reps_ammount, exercise.reps));
            weightTextView.setText(context.getString(R.string.exercise_weight_ammount, exercise.weight));
            dateTextView.setText(exercise.date.toString());
            itemView.setOnClickListener(view -> {
                        return;
                        //listener.onItemClick(exercise);
                    }
            );

            itemView.setOnLongClickListener(view -> {
                //longClickListener.onLongItemClick(exercise);
                return false;
            });
        }
    }

    public void setData(List<ExerciseData> data) {
        this.dataSet = data;
        notifyDataSetChanged();
    }
}
