package com.stoffe.gym.database;

import android.app.Application;
import android.os.AsyncTask;

import com.stoffe.gym.database.entities.Summary;

import java.util.List;

import androidx.lifecycle.LiveData;

public class SummaryRepository {

    final WorkoutDao workoutDao;

    SummaryRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        workoutDao = db.workoutDao();
    }

    LiveData<List<Summary>> getSummary(){
        return workoutDao.getAllSummary();
    }

    void insert(Summary summary){
        new SummaryRepository.insertSummaryAsyncTask(workoutDao).execute(summary);
    }

    void delete(Summary summary){
        new SummaryRepository.deleteSummaryAsyncTask(workoutDao).execute(summary);
    }

    void update(Summary summary){
        new SummaryRepository.updateSummaryAsyncTask(workoutDao).execute(summary);
    }

    private static class insertSummaryAsyncTask extends AsyncTask<Summary,Void,Void> {
        private final WorkoutDao taskDao;

        insertSummaryAsyncTask(WorkoutDao workoutDao){
            taskDao = workoutDao;
        }

        @Override
        protected Void doInBackground(Summary... summaries) {
            taskDao.insertAllSummary(summaries[0]);
            return null;
        }
    }

    private static class deleteSummaryAsyncTask extends AsyncTask<Summary,Void,Void>{
        private final WorkoutDao taskDao;

        deleteSummaryAsyncTask(WorkoutDao workoutDao){
            taskDao = workoutDao;
        }

        @Override
        protected Void doInBackground(Summary... summaries) {
            taskDao.deleteSummary(summaries[0]);
            return null;
        }
    }

    private static class updateSummaryAsyncTask extends AsyncTask<Summary,Void,Void>{
        private final WorkoutDao taskDao;

        updateSummaryAsyncTask(WorkoutDao workoutDao){
            taskDao = workoutDao;
        }

        @Override
        protected Void doInBackground(Summary... summaries) {
            taskDao.updateSummary(summaries[0]);
            return null;
        }
    }

}
