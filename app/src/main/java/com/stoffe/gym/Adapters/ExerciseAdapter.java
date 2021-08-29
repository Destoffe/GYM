package com.stoffe.gym.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.stoffe.gym.database.entities.Exercise;
import com.stoffe.gym.R;
import com.stoffe.gym.database.entities.Workout;

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

    public interface OnButtonClickListener {
        void onItemClick(Exercise exercise);
    }

    private final OnItemClickListener listener;
    private final OnItemLongClickListener longClickListener;
    private final OnButtonClickListener buttonClickListener;


    public ExerciseAdapter(OnItemClickListener listener, OnItemLongClickListener longClickListener, OnButtonClickListener buttonClickListener){
        this.dataSet = new ArrayList<>();
        this.listener = listener;
        this.longClickListener = longClickListener;
        this.buttonClickListener = buttonClickListener;
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
        holder.bind(dataSet.get(position),listener,longClickListener,buttonClickListener);

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView nameTextView;
        private final ImageButton shortcutBtn;

        public ViewHolder(View view) {
            super(view);
            nameTextView = (TextView) view.findViewById(R.id.name);
            shortcutBtn = view.findViewById(R.id.addDataShortcut);
        }


        public void bind(final Exercise exercise, final OnItemClickListener listener,final OnItemLongClickListener longClickListener,final OnButtonClickListener buttonClickListener) {
            nameTextView.setText(exercise.name);
            itemView.setOnClickListener(view ->
                    listener.onItemClick(exercise)
            );

            shortcutBtn.setOnClickListener(view -> buttonClickListener.onItemClick(exercise));

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
