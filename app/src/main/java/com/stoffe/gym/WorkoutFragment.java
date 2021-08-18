package com.stoffe.gym;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.stoffe.gym.Database.Exercise;
import com.stoffe.gym.Database.WorkoutViewModel;
import com.stoffe.gym.Adapters.AddExerciseLayout;
import com.stoffe.gym.Adapters.ExerciseAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

public class WorkoutFragment extends Fragment {

    RecyclerView exerciseRecycleView;
    ExerciseAdapter exerciseAdapter;
    List<Exercise> exerciseList;
    WorkoutViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_workout, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        exerciseList = new ArrayList<>();
        viewModel = new ViewModelProvider(getActivity()).get(WorkoutViewModel.class);
        /*
        view.findViewById(R.id.btnSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });

         */
        exerciseAdapter = new ExerciseAdapter(exercise -> {
            viewModel.setCurrentExercise(exercise);
            NavHostFragment.findNavController(WorkoutFragment.this)
                    .navigate(R.id.action_SecondFragment_to_exerciseFragment);
        }, exercise -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.dialog_style);
            builder.setTitle(getString(R.string.delete_exercise) + " " + exercise.name + "?");
            builder.setPositiveButton(R.string.positive_button, (dialog, which) -> {
                viewModel.deleteExercise(exercise);
                exerciseList.remove(exercise);
            });
            builder.setNegativeButton(R.string.negative_button, (dialog, which) -> dialog.cancel());
            builder.show();
        });


        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(view12 -> NavHostFragment.findNavController(WorkoutFragment.this)
                .navigate(R.id.action_SecondFragment_to_FirstFragment));

        viewModel.getCurrentWorkout().observe(getViewLifecycleOwner(), workout -> {
            if (workout == null) {
                return;
            }
            toolbar.setTitle(workout.getName());

            viewModel.getAllExercisesWithId(workout.uid).observe(getViewLifecycleOwner(), exercises -> {
                if (exercises == null) {
                    return;
                }
                exerciseList = exercises;
                exerciseAdapter.setData(exerciseList);
            });
        });

        exerciseRecycleView = view.findViewById(R.id.recycle_view);

        exerciseRecycleView.setAdapter(exerciseAdapter);

        ExtendedFloatingActionButton button = view.findViewById(R.id.fab_exercise);
        button.setOnClickListener(view1 -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.dialog_style);
            builder.setTitle(R.string.create_new_exercise);
            AddExerciseLayout LL = new AddExerciseLayout(getContext(), false, true);
            LL.setBackgroundColor(getResources().getColor(R.color.white_background));
            LL.setEditText(getString(R.string.hint_exercise_name));
            builder.setView(LL);
            builder.setPositiveButton(R.string.positive_button, (dialog, which) -> {
                Exercise exercise = new Exercise(LL.editText.getText().toString(), viewModel.getCurrentWorkout().getValue().uid);
                viewModel.insertExercise(exercise);
            });
            builder.setNegativeButton(R.string.negative_button, (dialog, which) -> dialog.cancel());
            builder.show();
        });
    }
}