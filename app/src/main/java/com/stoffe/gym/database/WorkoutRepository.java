package com.stoffe.gym.database;

import android.app.Application;
import android.os.AsyncTask;

import com.stoffe.gym.database.entities.Workout;

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

    void update(Workout workout){
        new updateWorkoutAsyncTask(workoutDao).execute(workout);
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

    private static class updateWorkoutAsyncTask extends AsyncTask<Workout,Void,Void>{
        private WorkoutDao taskDao;

        updateWorkoutAsyncTask(WorkoutDao workoutDao){
            taskDao = workoutDao;
        }

        @Override
        protected Void doInBackground(Workout... workouts) {
            taskDao.updateWorkout(workouts[0]);
            return null;
        }
    }

}
