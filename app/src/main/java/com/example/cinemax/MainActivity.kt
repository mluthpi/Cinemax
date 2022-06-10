package com.example.cinemax

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cinemax.data.MovieResponse
import com.example.cinemax.data.ResultsItem
import com.example.cinemax.databinding.ActivityMainBinding
import com.example.cinemax.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "CINEMAX"

        getMovies()
    }

    private fun getMovies() {
        ApiConfig.getApiService().getMovie(apiKey = "ba7b7ec258e912a3c68b34e6dfba3ca5").enqueue(
            object : Callback<MovieResponse> {
                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    val data = response.body()
                    val movies = data?.results

                    showMovie(movies!!)
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                }

            })
    }

    private fun showMovie(movies: List<ResultsItem>) {
        val movieAdapter = MovieAdapter {}

        movieAdapter.addItems(movies)
        binding.rvMovies.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = movieAdapter
        }
    }


}