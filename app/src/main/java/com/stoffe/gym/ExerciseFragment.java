package com.stoffe.gym;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stoffe.gym.Adapters.ExerciseDataAdapter;
import com.stoffe.gym.Adapters.LogDataDialogLayout;
import com.stoffe.gym.Database.ExerciseData;
import com.stoffe.gym.Database.WorkoutViewModel;
import com.stoffe.gym.databinding.FragmentExerciseBinding;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

public class ExerciseFragment extends Fragment {

    private FragmentExerciseBinding binding;
    private WorkoutViewModel viewModel;
    ExerciseDataAdapter exerciseDataAdapter;
    List<ExerciseData> exerciseDataList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentExerciseBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(WorkoutViewModel.class);
        exerciseDataList = new ArrayList<>();
        exerciseDataAdapter = new ExerciseDataAdapter(null, null);
        exerciseDataAdapter.setData(exerciseDataList);
        binding.recycleView.setAdapter(exerciseDataAdapter);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(view12 -> NavHostFragment.findNavController(ExerciseFragment.this)
                .navigate(R.id.action_exerciseFragment_to_SecondFragment));

        viewModel.getCurrentExercise().observe(getViewLifecycleOwner(), exercise -> {
            if (exercise == null) {
                return;
            }
            binding.toolbar.setTitle(exercise.name);

        });

        binding.fabExercise.setOnClickListener(view1 -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.dialog_style);
            builder.setTitle(R.string.create_new_exercise_data);
            LogDataDialogLayout LL = new LogDataDialogLayout(getContext());
            builder.setView(LL);
            builder.setPositiveButton(R.string.positive_button, (dialog, which) -> {
                int sets = Integer.parseInt(LL.setsEditText.getText().toString());
                int reps = Integer.parseInt(LL.repsEditText.getText().toString());
                int weight = Integer.parseInt(LL.weightEditText.getText().toString());

                ExerciseData exerciseData = new ExerciseData(sets, reps, weight, viewModel.getCurrentExercise().getValue().uid);
                viewModel.insertExerciseData(exerciseData);
            });
            builder.setNegativeButton(R.string.negative_button, (dialog, which) -> dialog.cancel());
            builder.show();
        });

        viewModel.getAllExercisesDataWithId(viewModel.getCurrentExercise().getValue().uid).observe(getViewLifecycleOwner(), exerciseData -> {
            if (exerciseData == null) {
                return;
            }
            exerciseDataList = exerciseData;
            exerciseDataAdapter.setData(exerciseDataList);
        });
    }

}
