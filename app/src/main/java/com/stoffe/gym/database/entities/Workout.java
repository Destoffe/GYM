package com.stoffe.gym.database.entities;

import java.time.LocalDate;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Workout {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "name")
    public String name;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setActive(boolean isActive) {
        if(!isActive){
            setLastTime(LocalDate.now());
        }
        this.isActive = isActive;
    }

    @Ignore
    public Workout(String name) {
        this.name = name;
    }

    public Workout() {
    }

    public LocalDate lastTime;

    public boolean isActive;

    public LocalDate getLastTime() {
        if(lastTime  == null){
            return LocalDate.MIN;
        }else{
            return lastTime;
        }

    }

    public void setLastTime(LocalDate lastTime) {
        this.lastTime = lastTime;
    }

    public boolean isActive() {
        return isActive;
    }
}
