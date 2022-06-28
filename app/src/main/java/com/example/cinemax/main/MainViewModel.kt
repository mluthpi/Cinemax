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

    private val _movies = MutableLiveData<List<ResultsItem>>()
    val movies: LiveData<List<ResultsItem>> = _movies

//    private val _movieDetail = MutableLiveData<MovieDetailsActivity>()
//    val movieDetail: LiveData<MovieDetailsActivity> = _movieDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getListMovie(apiKey: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getMovie(apiKey)
        client.enqueue(object : Callback<MovieResponse>{
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _movies.value = response.body()?.results?: emptyList()
//                    _movieDetail.value = (response.body()?.results?: ProductionCompaniesItem()) as MovieDetailsActivity
                } else {
                    _movies.value = emptyList()
                    Log.e(TAG, "onResponse: ${response.message()}")}
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    companion object{
        private const val TAG = "MainViewModel"
    }

}