package com.stoffe.gym.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.stoffe.gym.database.entities.Exercise
import com.stoffe.gym.database.entities.ExerciseData
import com.stoffe.gym.database.entities.Summary
import com.stoffe.gym.database.entities.Workout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WorkoutViewModel(application: Application) : AndroidViewModel(
    application
) {

    private val workoutRepository = WorkoutRepository(getDatabase(application))
    private val exerciseRepository = ExerciseRepository(getDatabase(application))
   // val summaryRepository: SummaryRepository
    val allWorkouts = workoutRepository.workouts
    private val currentWorkout: MutableLiveData<Workout>
    val currentExercise: MutableLiveData<Exercise>

    val allExercisesWithId = MutableStateFlow<List<Exercise>?>(null)
    val allExercisesDataWithId = MutableStateFlow<List<ExerciseData>?>(null)

    private val exerciseId: MutableStateFlow<Int?> = MutableStateFlow(null)

    val allExercises = exerciseRepository.exercises

    init {
       // summaryRepository = SummaryRepository(application)
        currentWorkout = MutableLiveData()
        currentExercise = MutableLiveData()
    }

    fun setExerciseID(id: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val exercises = exerciseRepository.getExerciseWithID(id)
                allExercisesWithId.value = exercises
            }
        }
    }

    fun setExerciseDataID(id: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val exercises = exerciseRepository.getExerciseDataWithID(id)
                allExercisesDataWithId.value = exercises
            }
        }
    }

    fun insertWorkout(workout: Workout) {
        viewModelScope.launch {
            workoutRepository.insert(workout)
        }

    }

    fun updateWorkout(workout: Workout) {
        viewModelScope.launch {
            workoutRepository.update(workout)
        }
    }

    fun setCurrentWorkout(workout: Workout) {
        currentWorkout.value = workout
    }

    fun getCurrentWorkout(): LiveData<Workout> {
        return currentWorkout
    }

    fun setCurrentExercise(exercise: Exercise) {
        currentExercise.value = exercise
    }

    fun getCurrentExercise(): LiveData<Exercise> {
        return currentExercise
    }

    fun deleteWorkout(workout: Workout) {
        viewModelScope.launch {
            workoutRepository.delete(workout)
        }
    }

    fun insertExercise(exercise: Exercise) {
        viewModelScope.launch {
            exerciseRepository.insertExercise(exercise)
        }
    }

    fun deleteExercise(exercise: Exercise) {
        viewModelScope.launch {
            exerciseRepository.deleteExercise(exercise)
        }
    }

    fun insertExerciseData(exerciseData: ExerciseData) {
        viewModelScope.launch {
            exerciseRepository.insertExerciseData(exerciseData)
        }
    }

    fun deleteExerciseData(exerciseData: ExerciseData) {
        viewModelScope.launch {
            exerciseRepository.deleteExerciseData(exerciseData)
        }
    }

    /*
    val allSummaries: LiveData<List<Summary>>
        get() = summaryRepository.summary

     */

}