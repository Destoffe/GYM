package com.stoffe.gym.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stoffe.gym.Database.Workout;
import com.stoffe.gym.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.ViewHolder> {

    private List<Workout> dataSet;

    public interface OnItemClickListener {
        void onItemClick(Workout workout);
    }

    public interface OnItemLongClickListener {
        void onLongItemClick(Workout workout);
    }

    private final OnItemClickListener listener;
    private final OnItemLongClickListener longClickListener;

    public WorkoutAdapter(OnItemClickListener listener,OnItemLongClickListener longClickListener) {
        this.dataSet = new ArrayList<>();
        this.listener = listener;
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.workout_list, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(dataSet.get(position), listener,longClickListener);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public ViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.text_view);
        }

        public void bind(final Workout workout, final OnItemClickListener listener,final OnItemLongClickListener longClickListener) {
            textView.setText(workout.name);
            itemView.setOnClickListener(view ->
                    listener.onItemClick(workout));
            itemView.setOnLongClickListener(view -> {
                longClickListener.onLongItemClick(workout);
                return false;
            });
        }



    }

    public void setData(List<Workout> data) {
        this.dataSet = data;
        notifyDataSetChanged();
    }

}
