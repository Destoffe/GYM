package com.stoffe.gym.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.stoffe.gym.database.entities.ExerciseData;

import com.stoffe.gym.Helpers.ItemTouchHelperViewHolder;
import com.stoffe.gym.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ExerciseDataAdapter extends RecyclerView.Adapter<ExerciseDataAdapter.ItemViewHolder> {

    private List<ExerciseData> dataSet;

    public interface OnItemClickListener {
        void onItemClick(ExerciseData exercise);
    }

    public interface OnItemLongClickListener {
        void onLongItemClick(ExerciseData exercise);
    }

    public interface OnDragStartListener {
        void onDragStarted(RecyclerView.ViewHolder viewHolder);
    }
    private final OnDragStartListener mDragStartListener;
    private final OnItemClickListener listener;
    private final OnItemLongClickListener longClickListener;


    public ExerciseDataAdapter(OnItemClickListener listener, OnItemLongClickListener longClickListener,OnDragStartListener dragStartListener) {
        this.dataSet = new ArrayList<>();
        this.listener = listener;
        this.longClickListener = longClickListener;
        this.mDragStartListener = dragStartListener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exercise_data_list_item, parent, false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.bind(dataSet.get(position), listener, longClickListener);
        /* TODO SUPPORT FOR DRAG/SWIPE IN FUTURE
        holder.handleView.setOnTouchListener((view, motionEvent) -> {
            if(motionEvent.getActionMasked() == MotionEvent.ACTION_DOWN) {
                mDragStartListener.onDragStarted(holder);
            }
            return false;
        });

         */

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {
        private final TextView setsTextView;
        private final TextView repsTextView;
        private final TextView weightTextView;
        private final TextView dateTextView;
        public final ImageView handleView;

        public ItemViewHolder(View view) {
            super(view);
            setsTextView = view.findViewById(R.id.set);
            repsTextView = view.findViewById(R.id.rep);
            weightTextView = view.findViewById(R.id.weight);
            dateTextView = view.findViewById(R.id.date);
            handleView =  view.findViewById(R.id.handle);
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

        @Override
        public void onItemSelected() {
        }

        @Override
        public void onItemClear() {
        }
    }

    public void setData(List<ExerciseData> data) {
        this.dataSet = data;
        notifyDataSetChanged();
    }
}
