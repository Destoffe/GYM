package com.stoffe.gym.Database;

import java.time.LocalDate;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ExerciseData {

    public ExerciseData(int sets, int reps, int weight, int exerciseID) {
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
        this.exerciseID = exerciseID;
        date = LocalDate.now();
    }

    @PrimaryKey(autoGenerate = true)
    public int uid;

    public int exerciseID;

    @ColumnInfo(name = "sets")
    public int sets;

    @ColumnInfo(name = "reps")
    public int reps;

    @ColumnInfo(name = "weight")
    public int weight;

    @ColumnInfo(name = "date")
    public LocalDate date;

}
