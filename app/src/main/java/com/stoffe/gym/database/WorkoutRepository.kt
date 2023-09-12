package com.stoffe.gym.database

import com.stoffe.gym.database.entities.Workout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class WorkoutRepository(private val database: AppDatabase) {
    suspend fun insert(workout: Workout){
        withContext(Dispatchers.IO){
            database.workoutDao().insertAllWorkout(workout)
        }
    }

    val workouts: Flow<List<Workout?>?> = database.workoutDao().getAllWorkouts()

    suspend fun update(workout: Workout){
        withContext(Dispatchers.IO){
            database.workoutDao().updateWorkout(workout)
        }
    }

    suspend fun delete(workout: Workout){
        withContext(Dispatchers.IO){
            database.workoutDao().deleteWorkout(workout)
        }
    }

}