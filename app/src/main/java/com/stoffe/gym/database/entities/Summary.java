package com.stoffe.gym.database.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Summary {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    private int currentActiveWorkout;
    private String lastWorkoutName;
    private LocalDate lastWorkoutDate;

    public Summary(int currentActiveWorkout, String lastWorkoutName, LocalDate lastWorkoutDate) {
        this.currentActiveWorkout = currentActiveWorkout;
        this.lastWorkoutName = lastWorkoutName;
        this.lastWorkoutDate = lastWorkoutDate;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getCurrentActiveWorkout() {
        return currentActiveWorkout;
    }

    public void setCurrentActiveWorkout(int currentActiveWorkout) {
        this.currentActiveWorkout = currentActiveWorkout;
    }

    public String getLastWorkoutName() {
        return lastWorkoutName;
    }

    public void setLastWorkoutName(String lastWorkoutName) {
        this.lastWorkoutName = lastWorkoutName;
    }

    public LocalDate getLastWorkoutDate() {
        return lastWorkoutDate;
    }

    public void setLastWorkoutDate(LocalDate lastWorkoutDate) {
        this.lastWorkoutDate = lastWorkoutDate;
    }
}
