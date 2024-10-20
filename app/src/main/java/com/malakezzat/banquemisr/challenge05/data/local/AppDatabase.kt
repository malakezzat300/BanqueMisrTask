package com.malakezzat.banquemisr.challenge05.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MovieDB::class , MovieDetailsDB::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract val movieDetailsDao: MovieDetailsDao
    abstract val movieDao: MovieDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "movie_database"
                ).fallbackToDestructiveMigration()
                    .build().also { instance = it }
            }
        }
    }
}