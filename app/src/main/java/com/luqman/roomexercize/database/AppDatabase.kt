package com.luqman.roomexercize.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NoteEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {
        var instance: AppDatabase? = null
            private set

        fun init(context: Context): AppDatabase? {
            if (instance == null) {
                synchronized(AppDatabase::class) {
                    instance = Room.databaseBuilder<AppDatabase>(
                        context.applicationContext,
                        AppDatabase::class.java,
                        AppDatabase::class.java.simpleName
                    )
                        .build()
                }
            }

            return instance
        }

        fun destroyInstance() {
            instance = null
        }
    }
}