package com.stoffe.gym.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.stoffe.gym.database.entities.*
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {

    @Query("SELECT * FROM workout")
    fun getAllWorkouts(): Flow<List<Workout>>

    @Query("SELECT * FROM workout WHERE uid IN (:workoutIds)")
    fun loadAllByIds(workoutIds: IntArray?): List<Workout?>?

    @Query("SELECT * FROM Workout WHERE name LIKE :first LIMIT 1")
    fun findByName(first: String?): Workout?

    @Update
    fun updateWorkout(workout: Workout)

    @Insert
    fun insertAllWorkout(vararg workouts: Workout?)

    @Delete
    fun deleteWorkout(workout: Workout)

    @Query("SELECT * FROM exercise")
    fun getAllExercises(): Flow<List<Exercise>>

    @Query("SELECT * FROM exercise WHERE workoutID IN (:workoutID)")
    suspend fun getAllExerciseWithId(workoutID: Int): List<Exercise>

    @Insert
    fun insertAllExercise(vararg exercises: Exercise?)

    @Delete
    fun deleteExercise(exercise: Exercise?)

    @get:Query("SELECT * FROM exercisedata")
    val allExerciseData: LiveData<List<ExerciseData?>?>?

    @Query("SELECT * FROM exercisedata WHERE exerciseID IN (:exerciseID)")
    suspend fun getAllExerciseDataWithId(exerciseID: Int):List<ExerciseData>

    @Insert
    fun insertAllExerciseData(vararg exerciseData: ExerciseData)

    @Delete
    fun deleteExerciseData(exerciseData: ExerciseData?)

    @Delete
    fun insertAllSummary(vararg summary: Summary?)

    @Delete
    fun deleteSummary(summary: Summary?)

    @Update
    fun updateSummary(summary: Summary?)

    @get:Query("SELECT * FROM summary")
    val allSummary: LiveData<List<Summary?>?>?

    @Insert
    fun insertBMI(vararg bmi: BMI?)

    @Query("SELECT * FROM BMI")
    fun getAllBMI(): Flow<List<BMI>>

    @Update
    fun updateBMI(bmi: BMI?)
}