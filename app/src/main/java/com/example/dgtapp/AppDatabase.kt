package com.example.dgtapp

import android.content.Context
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [ToDo::class], autoMigrations = [AutoMigration (from = 1, to = 2), AutoMigration (from = 2, to = 3), AutoMigration (from = 3, to = 4)], version = 4, exportSchema = true)
//sql does NOT like boolean for some odd reason, so here I'm using a custom converter to turn the bool variables into integers
@TypeConverters(todoConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao

    //Because I only have one database instance, I turned it into a Singleton Pattern object, which stops alot of weird duplication bugs from happening.
    //Making this a singleton restricts it to only one instance, so no weird duplications
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = buildDatabase(context)
                }
            }
            return INSTANCE!!
        }

        //this builds the database
        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "AppDatabase"
            )
                .allowMainThreadQueries()
                .build()
        }
    }
}
