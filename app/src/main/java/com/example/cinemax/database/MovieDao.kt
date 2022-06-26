package com.example.cinemax.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.cinemax.model.MovieEntity


@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(movie: MovieEntity)

    @Delete
    fun delete(movie: MovieEntity)

    @Query("SELECT * FROM movieentity ORDER BY title ASC")
    fun getAllMovies(): LiveData<List<MovieEntity>>
}