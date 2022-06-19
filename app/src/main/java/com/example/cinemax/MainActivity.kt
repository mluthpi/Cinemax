package com.example.cinemax

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cinemax.data.MovieResponse
import com.example.cinemax.data.ResultsItem
import com.example.cinemax.databinding.ActivityMainBinding
import com.example.cinemax.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
//    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "CINEMAX"

        getMovies()

        val mainViewModel = ViewModelProvider(
            this, ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)

        mainViewModel.isLoading.observe(this,{showLoading(it)})

//        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
//        mainViewModel.movie
//        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
//        mainViewModel.movieDetail


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

    private fun showMovie(movies: List<ResultsItem>?) {
        val movieAdapter = MovieAdapter {
            val intent = Intent(this, MovieDetailsActivity::class.java)
            intent.putExtra("KEY_ID", it.id)
            startActivity(intent)
        }

        if (movies != null) {
            movieAdapter.addItems(movies)
        }
        binding.rvMovies.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = movieAdapter
        }
    }

    private fun showLoading(isLoading:Boolean) {
        binding.progressbar.visibility = if(isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}