package com.example.cinemax.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.cinemax.data.ResultsItem
import com.example.cinemax.model.MovieEntity
import com.example.cinemax.model.MovieRepository

class FavoriteViewModel(application: Application) : ViewModel() {
    private val mMovieRepository = MovieRepository(application)

    fun getFavoriteMovie() : LiveData<List<ResultsItem>> = mMovieRepository.getAllMovie()
}