package com.stoffe.gym.Database;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

public class WorkoutRepository {

    WorkoutDao workoutDao;

    WorkoutRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        workoutDao = db.workoutDao();
    }

    LiveData<List<Workout>> getWorkout(){
        return workoutDao.getAllWorkout();
    }

    void insert(Workout workout){
        new insertWorkoutAsyncTask(workoutDao).execute(workout);
    }

    void delete(Workout workout){
        new deleteWorkoutAsyncTask(workoutDao).execute(workout);
    }

    private static class insertWorkoutAsyncTask extends AsyncTask<Workout,Void,Void>{
        private WorkoutDao taskDao;

        insertWorkoutAsyncTask(WorkoutDao workoutDao){
            taskDao = workoutDao;
        }

        @Override
        protected Void doInBackground(Workout... workout) {
            taskDao.insertAllWorkout(workout[0]);
            return null;
        }
    }

    private static class deleteWorkoutAsyncTask extends AsyncTask<Workout,Void,Void>{
        private WorkoutDao taskDao;

        deleteWorkoutAsyncTask(WorkoutDao workoutDao){
            taskDao = workoutDao;
        }

        @Override
        protected Void doInBackground(Workout... workout) {
            taskDao.deleteWorkout(workout[0]);
            return null;
        }
    }

}
