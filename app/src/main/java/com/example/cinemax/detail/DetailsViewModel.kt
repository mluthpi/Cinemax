package com.example.cinemax.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cinemax.data.MovieDetailResponse
import com.example.cinemax.data.ResultsItem
import com.example.cinemax.model.MovieRepository
import com.example.cinemax.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsViewModel(application: Application): ViewModel() {
    private val mMovieRepository: MovieRepository = MovieRepository(application)

    companion object {
        private const val TAG = "DetailsViewModel"
    }

    private val _detailMovie = MutableLiveData<MovieDetailResponse>()
    val detailMovie: LiveData<MovieDetailResponse> = _detailMovie

    private val _repositoryNumber = MutableLiveData<Int>()
    val repositoryNumber: LiveData<Int> = _repositoryNumber

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getDetailMovie(id: Int) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetails(id = Int, apiKey = "ba7b7ec258e912a3c68b34e6dfba3ca5" )
        client.enqueue(object: Callback<MovieDetailResponse>{
            override fun onResponse(
                call: Call<MovieDetailResponse>,
                response: Response<MovieDetailResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _detailMovie.value = response.body()
                } else {
                    Log.e(TAG, "onResponse onResponse: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<MovieDetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure onFailue: ${t.message.toString()}")
            }

        })
    }

    fun insertToDB(movie: MovieDetailResponse){
        mMovieRepository.insert(movie)
    }

    fun deleteFromDB(movie: MovieDetailResponse) {
        mMovieRepository.delete(movie)
    }
    fun getFavoriteMovie() : LiveData<List<ResultsItem>> = mMovieRepository.getAllMovie()

}