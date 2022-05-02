package com.stoffe.gym.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.stoffe.gym.database.entities.Exercise;
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

    public interface OnShortcutButtonClickListener {
        void onItemClick(Exercise exercise);
    }

    public interface OnGraphButtonClickListener {
        void onItemClick(Exercise exercise);
    }

    private final OnItemClickListener listener;
    private final OnItemLongClickListener longClickListener;
    private final OnShortcutButtonClickListener shortcutButtonClickListener;
    private final OnGraphButtonClickListener graphButtonClickListener;


    public ExerciseAdapter(OnItemClickListener listener, OnItemLongClickListener longClickListener, OnShortcutButtonClickListener shortcutButtonClickListener,
                           OnGraphButtonClickListener graphButtonClickListener){
        this.dataSet = new ArrayList<>();
        this.listener = listener;
        this.longClickListener = longClickListener;
        this.shortcutButtonClickListener = shortcutButtonClickListener;
        this.graphButtonClickListener = graphButtonClickListener;
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
        holder.bind(dataSet.get(position),listener,longClickListener, shortcutButtonClickListener,graphButtonClickListener);

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView nameTextView;
        private final ImageButton shortcutBtn;
        private final ImageButton graphBut;

        public ViewHolder(View view) {
            super(view);
            nameTextView = (TextView) view.findViewById(R.id.name);
            shortcutBtn = view.findViewById(R.id.addDataShortcut);
            graphBut = view.findViewById(R.id.seeGraphButton);
        }


        public void bind(final Exercise exercise, final OnItemClickListener listener,final OnItemLongClickListener longClickListener,final OnShortcutButtonClickListener shortcutButtonClickListener,
                         final OnGraphButtonClickListener graphButtonClickListener) {
            nameTextView.setText(exercise.name);
            itemView.setOnClickListener(view ->
                    listener.onItemClick(exercise)
            );

            shortcutBtn.setOnClickListener(view -> shortcutButtonClickListener.onItemClick(exercise));

            graphBut.setOnClickListener(view -> {
                graphButtonClickListener.onItemClick(exercise);
            });

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
