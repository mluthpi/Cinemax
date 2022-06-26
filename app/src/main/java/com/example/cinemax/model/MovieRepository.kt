package com.example.cinemax.model

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.cinemax.database.MovieDao
import com.example.cinemax.database.MovieRoomDatabase
import java.util.concurrent.Executors

class MovieRepository(application: Application) {
    private val mMovieDao: MovieDao
    private val executorService = Executors.newSingleThreadExecutor()

    init {
        val db = MovieRoomDatabase.getDatabase(application)
        mMovieDao = db.movieDao()
    }

    fun getAllMovie(): LiveData<List<MovieEntity>> = mMovieDao.getAllMovies()

    fun insert(movie: MovieEntity) {
        executorService.execute { mMovieDao.insert(movie) }
    }

    fun delete(movie: MovieEntity) {
        executorService.execute { mMovieDao.delete(movie) }
    }

}