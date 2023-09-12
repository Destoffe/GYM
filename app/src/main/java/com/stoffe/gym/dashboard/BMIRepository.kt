package com.stoffe.gym.dashboard

import androidx.lifecycle.LiveData
import com.stoffe.gym.database.AppDatabase
import com.stoffe.gym.database.WorkoutDao
import com.stoffe.gym.database.entities.BMI
import com.stoffe.gym.database.entities.Workout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext


class BMIRepository(private val database: AppDatabase) {

    val bmi: Flow<List<BMI?>?> = database.workoutDao().getAllBMI()

    suspend fun insert(bmi: BMI){
        withContext(Dispatchers.IO){
            database.workoutDao().insertBMI(bmi)
        }
    }

   suspend fun update(bmi: BMI) {
       withContext(Dispatchers.IO){
           database.workoutDao().updateBMI(bmi)
       }
    }
}