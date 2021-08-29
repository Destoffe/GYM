package com.stoffe.gym.database;

import android.app.Application;
import android.os.AsyncTask;

import com.stoffe.gym.database.entities.Exercise;
import com.stoffe.gym.database.entities.ExerciseData;

import java.util.List;

import androidx.lifecycle.LiveData;

public class ExerciseRepository {

    WorkoutDao workoutDao;

    ExerciseRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        workoutDao = db.workoutDao();
    }

    LiveData<List<Exercise>> getExercise(){
        return workoutDao.getAllExercise();
    }

    LiveData<List<Exercise>> getExerciseWithID(int id){
        return workoutDao.getAllExerciseWithId(id);
    }

    void insertExercise(Exercise exercise){
        new insertExerciseAsyncTask(workoutDao).execute(exercise);
    }

    void deleteExercise(Exercise exercise){
        new deleteExerciseAsyncTask(workoutDao).execute(exercise);
    }

    LiveData<List<ExerciseData>> getExerciseDataWithID(int id){
        return workoutDao.getAllExerciseDataWithId(id);
    }

    void insertExerciseData(ExerciseData exerciseData){
        new insertExerciseDataAsyncTask(workoutDao).execute(exerciseData);
    }

    void deleteExerciseData(ExerciseData exerciseData){
        new deleteExerciseDataAsyncTask(workoutDao).execute(exerciseData);
    }
    private static class insertExerciseAsyncTask extends AsyncTask<Exercise,Void,Void>{
        private WorkoutDao taskDao;

        insertExerciseAsyncTask(WorkoutDao workoutDao){
            taskDao = workoutDao;
        }

        @Override
        protected Void doInBackground(Exercise... exercises) {
            taskDao.insertAllExercise(exercises[0]);
            return null;
        }
    }

    private static class deleteExerciseAsyncTask extends AsyncTask<Exercise,Void,Void>{
        private WorkoutDao taskDao;

        deleteExerciseAsyncTask(WorkoutDao workoutDao){
            taskDao = workoutDao;
        }

        @Override
        protected Void doInBackground(Exercise... exercises) {
            taskDao.deleteExercise(exercises[0]);
            return null;
        }
    }

    private static class insertExerciseDataAsyncTask extends AsyncTask<ExerciseData,Void,Void>{
        private WorkoutDao taskDao;

        insertExerciseDataAsyncTask(WorkoutDao workoutDao){
            taskDao = workoutDao;
        }

        @Override
        protected Void doInBackground(ExerciseData... exerciseData) {
            taskDao.insertAllExerciseData(exerciseData[0]);
            return null;
        }
    }

    private static class deleteExerciseDataAsyncTask extends AsyncTask<ExerciseData,Void,Void>{
        private WorkoutDao taskDao;

        deleteExerciseDataAsyncTask(WorkoutDao workoutDao){
            taskDao = workoutDao;
        }

        @Override
        protected Void doInBackground(ExerciseData... exerciseData) {
            taskDao.deleteExerciseData(exerciseData[0]);
            return null;
        }
    }

}
