package com.example.cinemax.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.cinemax.data.MovieDetailResponse
import com.example.cinemax.data.ResultsItem
import com.example.cinemax.model.MovieEntity


@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(movie: MovieDetailResponse)

    @Delete
    fun delete(movie: MovieDetailResponse)

    @Query("SELECT * FROM movieentity ORDER BY title ASC")
    fun getAllMovies(): LiveData<List<ResultsItem>>
}