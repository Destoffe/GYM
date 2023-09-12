package com.stoffe.gym.database;

import android.app.Application;

import com.stoffe.gym.database.entities.Exercise;
import com.stoffe.gym.database.entities.ExerciseData;
import com.stoffe.gym.database.entities.Summary;
import com.stoffe.gym.database.entities.Workout;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class WorkoutViewModel extends AndroidViewModel {

    final WorkoutRepository workoutRepository;
    final ExerciseRepository exerciseRepository;
    final SummaryRepository summaryRepository;
    final LiveData<List<Workout>> workoutList;
    private final MutableLiveData<Workout> currentWorkout;
    private final MutableLiveData<Exercise> currentExercise;

    public WorkoutViewModel(Application application){
        super(application);
        workoutRepository = new WorkoutRepository(application);
        exerciseRepository = new ExerciseRepository(application);
        summaryRepository = new SummaryRepository(application);
        workoutList = workoutRepository.getWorkout();
        currentWorkout = new MutableLiveData<>();
        currentExercise = new MutableLiveData<>();
    }

    public LiveData<List<Workout>> getAllWorkouts(){
        return workoutList;
    }

    public void insertWorkout(Workout workout){
        workoutRepository.insert(workout);
    }

    public void updateWorkout(Workout workout){
        workoutRepository.update(workout);
    }

    public void setCurrentWorkout(Workout workout){
        currentWorkout.setValue(workout);
    }

    public LiveData<Workout> getCurrentWorkout(){
        return currentWorkout;
    }

    public void setCurrentExercise(Exercise exercise){
        currentExercise.setValue(exercise);
    }

    public LiveData<Exercise> getCurrentExercise(){
        return currentExercise;
    }

    public void deleteWorkout(Workout workout){
        workoutRepository.delete(workout);
    }

    public LiveData<List<Exercise>> getAllExercises(){
        return exerciseRepository.getExercise();
    }

    public LiveData<List<Exercise>> getAllExercisesWithId(int id){
        return exerciseRepository.getExerciseWithID(id);
    }

    public void insertExercise(Exercise exercise){
        exerciseRepository.insertExercise(exercise);
    }

    public void deleteExercise(Exercise exercise){
        exerciseRepository.deleteExercise(exercise);
    }

    public LiveData<List<ExerciseData>> getAllExercisesDataWithId(int id){
        return exerciseRepository.getExerciseDataWithID(id);
    }

    public void insertExerciseData(ExerciseData exerciseData){
        exerciseRepository.insertExerciseData(exerciseData);
    }

    public void deleteExerciseData(ExerciseData exerciseData){
        exerciseRepository.deleteExerciseData(exerciseData);
    }

    public LiveData<List<Summary>> getAllSummaries(){
        return summaryRepository.getSummary();
    }

    public void insertSummary(Summary summary){
        summaryRepository.insert(summary);
    }

    public void deleteSummary(Summary summary){
        summaryRepository.delete(summary);
    }
}
