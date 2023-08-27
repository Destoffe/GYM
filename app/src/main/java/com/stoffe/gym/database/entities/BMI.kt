package com.stoffe.gym.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class BMI(
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0,
    val weight: Float,
    val height: Float,
    val gender: Int,
    val date: LocalDateTime,
)
