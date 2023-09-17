package com.stoffe.gym.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class ExerciseData(
    @PrimaryKey(autoGenerate = true) var uid: Int = 0,
    val sets: Int,
    val reps: Int,
    val weight: Float,
    val exerciseID: Int,
    val date: LocalDate = LocalDate.now(),
)
