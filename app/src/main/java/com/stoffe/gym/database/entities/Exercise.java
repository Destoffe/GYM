package com.stoffe.gym.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Exercise {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    public final int workoutID;

    @ColumnInfo(name = "name")
    public final String name;


    public Exercise(String name,int workoutID) {
        this.name = name;
        this.workoutID = workoutID;
    }
}
