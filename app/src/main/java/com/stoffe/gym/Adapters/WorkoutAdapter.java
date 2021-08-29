package com.stoffe.gym.Adapters;

import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;
import com.stoffe.gym.database.entities.Workout;
import com.stoffe.gym.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.ViewHolder> {

    private List<Workout> dataSet;

    public interface OnItemClickListener {
        void onItemClick(Workout workout);
    }

    public interface OnItemLongClickListener {
        void onLongItemClick(Workout workout);
    }

    public interface OnButtonClickListener {
        void onItemClick(Workout workout);
    }
    private final OnItemClickListener listener;
    private final OnItemLongClickListener longClickListener;
    private final OnButtonClickListener buttonClickListener;

    public WorkoutAdapter(OnItemClickListener listener,OnItemLongClickListener longClickListener,OnButtonClickListener buttonClickListener) {
        this.dataSet = new ArrayList<>();
        this.listener = listener;
        this.longClickListener = longClickListener;
        this.buttonClickListener = buttonClickListener;
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
        holder.bind(dataSet.get(position), listener,longClickListener,buttonClickListener);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final TextView lastTimeTextView;
        private final ImageButton playButton;
        private final ImageButton stopButton;
        private final MaterialCardView card;

        public ViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.text_view);
            lastTimeTextView = view.findViewById(R.id.last_time_text);
            playButton = view.findViewById(R.id.startWorkout);
            stopButton = view.findViewById(R.id.stopWorkout);
            card = view.findViewById(R.id.card);
        }

        public void bind(final Workout workout, final OnItemClickListener listener,final OnItemLongClickListener longClickListener,final OnButtonClickListener buttonClickListener) {
            textView.setText(workout.name);
            itemView.setOnClickListener(view ->
                    listener.onItemClick(workout));
            itemView.setOnLongClickListener(view -> {
                longClickListener.onLongItemClick(workout);
                return false;
            });
            playButton.setOnClickListener(view -> buttonClickListener.onItemClick(workout));
            stopButton.setOnClickListener(view -> buttonClickListener.onItemClick(workout));
            if(workout.isActive){
                card.setCardBackgroundColor(ContextCompat.getColor(itemView.getContext(),R.color.green));
                stopButton.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(itemView.getContext(),R.color.green)));
                playButton.setVisibility(View.GONE);
                stopButton.setVisibility(View.VISIBLE);
            }else{
                card.setCardBackgroundColor(ContextCompat.getColor(itemView.getContext(),R.color.white));
                playButton.setVisibility(View.VISIBLE);
                stopButton.setVisibility(View.GONE);
            }

            if(workout.getLastTime() == null){
                lastTimeTextView.setVisibility(View.GONE);
            }else{
                lastTimeTextView.setVisibility(View.VISIBLE);
                lastTimeTextView.setText(workout.getLastTime().toString());
            }
        }

    }

    public void setData(List<Workout> data) {
        data.sort(Comparator.comparing(Workout::getLastTime));
        this.dataSet = data;
        notifyDataSetChanged();
    }


    public List<Workout> getDataSet(){
        return dataSet;
    }

}
