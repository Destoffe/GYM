package com.stoffe.gym.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.stoffe.gym.database.Converters.LocalDateConverter
import com.stoffe.gym.database.Converters.LocalDateTimeConverter
import com.stoffe.gym.database.entities.*

@Database(
    entities = [Workout::class, Exercise::class, ExerciseData::class, Summary::class, BMI::class],
    version = 10,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(
            from = 9,
            to = 10,
        ),
    ]
)
@TypeConverters(LocalDateConverter::class, LocalDateTimeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun workoutDao(): WorkoutDao?

    companion object {
        var INSTANCE: AppDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java, "person-databse"
                        )
                            .build()
                    }
                }
            }
            return INSTANCE
        }
    }
}