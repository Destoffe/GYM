package com.stoffe.gym.database;

import android.content.Context;

import com.stoffe.gym.database.Converters.LocalDateConverter;
import com.stoffe.gym.database.entities.Exercise;
import com.stoffe.gym.database.entities.ExerciseData;
import com.stoffe.gym.database.entities.Summary;
import com.stoffe.gym.database.entities.Workout;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Workout.class, Exercise.class, ExerciseData.class, Summary.class},version = 9)
@TypeConverters({LocalDateConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract WorkoutDao workoutDao();

    public static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (AppDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class,"person-databse").fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
