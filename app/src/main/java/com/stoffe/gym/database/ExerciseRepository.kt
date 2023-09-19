package com.stoffe.gym.database

import com.stoffe.gym.database.entities.Exercise
import com.stoffe.gym.database.entities.ExerciseData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class ExerciseRepository(private val database: AppDatabase) {


    val exercises: Flow<List<Exercise?>?> = database.workoutDao().getAllExercises()

    suspend fun getExerciseWithID(id: Int): List<Exercise>{
        return database.workoutDao().getAllExerciseWithId(id)
    }

     fun getExerciseWithIDTest(id: Int): Flow<List<Exercise>>{
        return database.workoutDao().getAllExerciseWithIdTest(id)
    }

    fun getExerciseDataWithID(id: Int): Flow<List<ExerciseData>>{
        return database.workoutDao().getAllExerciseDataWithId(id)
    }


    suspend fun insertExercise(exercise: Exercise) {
        withContext(Dispatchers.IO){
            database.workoutDao().insertAllExercise(exercise)
        }
    }

    suspend fun deleteExercise(exercise: Exercise?) {
        withContext(Dispatchers.IO){
            database.workoutDao().deleteExercise(exercise)
        }
    }

    suspend fun insertExerciseData(exercise: ExerciseData) {
        withContext(Dispatchers.IO){
            database.workoutDao().insertAllExerciseData(exercise)
        }
    }

    suspend fun deleteExerciseData(exercise: ExerciseData) {
        withContext(Dispatchers.IO){
            database.workoutDao().deleteExerciseData(exercise)
        }
    }

}