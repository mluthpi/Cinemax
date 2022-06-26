package com.example.cinemax.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cinemax.data.MovieResponse
import com.example.cinemax.data.ResultsItem
import com.example.cinemax.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {

    private val _movie = MutableLiveData<List<ResultsItem>>()
    val movie: LiveData<List<ResultsItem>> = _movie

//    private val _movieDetail = MutableLiveData<MovieDetailsActivity>()
//    val movieDetail: LiveData<MovieDetailsActivity> = _movieDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object{
        private const val TAG = "MainViewModel"
    }

    private fun listMovie(movie: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getMovie(movie)
        client.enqueue(object : Callback<MovieResponse>{
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _movie.value = response.body()?.results?: emptyList()
//                    _movieDetail.value = (response.body()?.results?: ProductionCompaniesItem()) as MovieDetailsActivity
                } else {
                    _movie.value = emptyList()
                    Log.e(TAG, "onResponse: ${response.message()}")}
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

}