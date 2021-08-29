package com.stoffe.gym.database;

import com.stoffe.gym.database.entities.Exercise;
import com.stoffe.gym.database.entities.ExerciseData;
import com.stoffe.gym.database.entities.Summary;
import com.stoffe.gym.database.entities.Workout;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface WorkoutDao {

    @Query("SELECT * FROM workout")
    LiveData<List<Workout>> getAllWorkout();

    @Query("SELECT * FROM workout WHERE uid IN (:workoutIds)")
    List<Workout> loadAllByIds(int[] workoutIds);

    @Query("SELECT * FROM Workout WHERE name LIKE :first LIMIT 1")
    Workout findByName(String first);

    @Update()
    void updateWorkout(Workout workout);

    @Insert
    void insertAllWorkout(Workout... workouts);

    @Delete
    void deleteWorkout(Workout workout);


    @Query("SELECT * FROM exercise")
    LiveData<List<Exercise>> getAllExercise();

    @Query("SELECT * FROM exercise WHERE workoutID IN (:workoutID)")
    LiveData<List<Exercise>> getAllExerciseWithId(int workoutID);

    @Insert
    void insertAllExercise(Exercise... exercises);

    @Delete
    void deleteExercise(Exercise exercise);

    @Query("SELECT * FROM exercisedata")
    LiveData<List<ExerciseData>> getAllExerciseData();

    @Query("SELECT * FROM exercisedata WHERE exerciseID IN (:exerciseID)")
    LiveData<List<ExerciseData>> getAllExerciseDataWithId(int exerciseID);

    @Insert
    void insertAllExerciseData(ExerciseData... exerciseData);

    @Delete
    void deleteExerciseData(ExerciseData exerciseData);

    @Delete
    void insertAllSummary(Summary... summary);

    @Delete
    void deleteSummary(Summary summary);

    @Update()
    void updateSummary(Summary summary);

    @Query("SELECT * FROM summary")
    LiveData<List<Summary>> getAllSummary();
}
