package com.stoffe.gym;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.stoffe.gym.Helpers.Utils;
import com.stoffe.gym.database.entities.Workout;
import com.stoffe.gym.database.WorkoutViewModel;
import com.stoffe.gym.Adapters.AddExerciseLayout;
import com.stoffe.gym.Adapters.WorkoutAdapter;
import com.stoffe.gym.databinding.FragmentDashboardBinding;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

public class DashboardFragment extends Fragment {
    private WorkoutAdapter workoutAdapter;
    private RecyclerView recyclerView;
    List<Workout> testData;
    private WorkoutViewModel viewModel;
    private FragmentDashboardBinding binding;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //testData = generateDumbData();
        viewModel = new ViewModelProvider(getActivity()).get(WorkoutViewModel.class);
        ExtendedFloatingActionButton fab = view.findViewById(R.id.fab);
        recyclerView = view.findViewById(R.id.list_view);
        fab.setOnClickListener(view1 -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.dialog_style);
            builder.setTitle(R.string.create_new_workout);

            AddExerciseLayout LL = new AddExerciseLayout(getContext(), false, true);
            LL.setBackgroundColor(getResources().getColor(R.color.white_background));
            LL.setEditText(getString(R.string.hint_workout_name));
            builder.setView(LL);
            builder.setPositiveButton(R.string.positive_button, (dialog, which) -> {
                Workout workout = new Workout(LL.editText.getText().toString());
                testData.add(workout);
                viewModel.insertWorkout(workout);
                workoutAdapter.setData(testData);
                Utils.showSnackbar("Workout created",binding.getRoot());
            });
            builder.setNegativeButton(R.string.negative_button, (dialog, which) -> dialog.cancel());
            builder.show();
        });

        testData = new ArrayList<>();
        workoutAdapter = new WorkoutAdapter(workout -> {
            viewModel.setCurrentWorkout(workout);
            NavHostFragment.findNavController(DashboardFragment.this)
                    .navigate(R.id.action_FirstFragment_to_SecondFragment);
        }, workout -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.dialog_style);
            builder.setTitle(R.string.delete_workout);
            builder.setPositiveButton(R.string.positive_button, (dialog, which) -> {
                viewModel.deleteWorkout(workout);
                testData.remove(workout);
                Utils.showSnackbar("Workout deleted",binding.getRoot());
            });
            builder.setNegativeButton(R.string.negative_button, (dialog, which) -> dialog.cancel());
            builder.show();
        }, workout -> {

            for (int i = 0; i < workoutAdapter.getDataSet().size(); i++) {
                if (workoutAdapter.getDataSet().get(i).isActive && workoutAdapter.getDataSet().get(i).getUid() != workout.getUid()){
                    Utils.showSnackbar("Another workout is already active!",binding.getRoot());
                    return;
                }
            }

            workout.setActive(!workout.isActive);
            viewModel.updateWorkout(workout);
        });
        workoutAdapter.setData(testData);
        recyclerView.setAdapter(workoutAdapter);

        viewModel.getAllWorkouts().observe(getViewLifecycleOwner(), workouts -> {
            if (workouts == null) {
                binding.setIsWorkoutListEmpty(true);
                return;
            }
            binding.setIsWorkoutListEmpty(workouts.size() < 1);
            testData = workouts;
            workoutAdapter.setData(testData);
        });

        viewModel.getAllSummaries().observe(getViewLifecycleOwner(), summaries -> {
            if (summaries == null || summaries.size() == 0) {
                return;
            }

            binding.setSummary(summaries.get(0));
        });

    }

    public List<Workout> generateDumbData() {
        List<Workout> dumbData = new ArrayList<>();
        Workout workout1 = new Workout();
        workout1.setName("workout1");
        Workout workout2 = new Workout();
        workout2.setName("workout2");
        Workout workout3 = new Workout();
        workout3.setName("workout3");

        dumbData.add(workout1);
        dumbData.add(workout2);
        dumbData.add(workout3);
        return dumbData;
    }

}