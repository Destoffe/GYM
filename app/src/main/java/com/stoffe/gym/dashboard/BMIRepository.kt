package com.stoffe.gym.dashboard;

import android.app.Application;
import android.os.AsyncTask;

import com.stoffe.gym.database.AppDatabase;
import com.stoffe.gym.database.WorkoutDao;
import com.stoffe.gym.database.entities.BMI;
import com.stoffe.gym.database.entities.Workout;

import java.util.List;

import androidx.lifecycle.LiveData;

public class BMIRepository {

    final WorkoutDao workoutDao;

    public BMIRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        workoutDao = db.workoutDao();
    }

    public LiveData<List<BMI>> getBmi(){
        return workoutDao.getAllBMI();
    }

    public void insert(BMI bmi){
        new insertBMIAsyncTask(workoutDao).execute(bmi);
    }

    public void update(BMI bmi){
        new updateWorkoutAsyncTask(workoutDao).execute(bmi);
    }

    private static class insertBMIAsyncTask extends AsyncTask<BMI,Void,Void>{
        private final WorkoutDao taskDao;

        insertBMIAsyncTask(WorkoutDao workoutDao){
            taskDao = workoutDao;
        }

        @Override
        protected Void doInBackground(BMI... bmi) {
            taskDao.insertBMI(bmi[0]);
            return null;
        }
    }


    private static class updateWorkoutAsyncTask extends AsyncTask<BMI,Void,Void>{
        private final WorkoutDao taskDao;

        updateWorkoutAsyncTask(WorkoutDao workoutDao){
            taskDao = workoutDao;
        }

        @Override
        protected Void doInBackground(BMI... bmi) {
            taskDao.updateBMI(bmi[0]);
            return null;
        }
    }

}
