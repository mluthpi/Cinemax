package com.example.cinemax.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cinemax.model.MovieEntity


@Database(entities = [MovieEntity::class], version = 2)
abstract class MovieRoomDatabase : RoomDatabase(){
    abstract fun movieDao(): MovieDao

    companion object {
        @Volatile
        private var INSTANCE: MovieRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): MovieRoomDatabase {
            if(INSTANCE == null) {
                synchronized(MovieRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                    MovieRoomDatabase::class.java, "movie_database")
                        .build()
                }
            }
            return INSTANCE as MovieRoomDatabase
        }
    }
}