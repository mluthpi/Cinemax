package com.example.cinemax.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View

import android.widget.Toast

import androidx.lifecycle.Observer

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cinemax.R
import com.example.cinemax.detail.MovieDetailsActivity
import com.example.cinemax.data.MovieResponse
import com.example.cinemax.data.ResultsItem
import com.example.cinemax.databinding.ActivityMainBinding
import com.example.cinemax.favorite.FavoriteActivity
import com.example.cinemax.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!


    private lateinit var mainViewModel: MainViewModel

    private var movieAdapter = MovieAdapter {
        val intent = Intent(this, MovieDetailsActivity::class.java)
        intent.putExtra("KEY_ID", it.id)
        startActivity(intent)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "CINEMAX"

        mainViewModel = ViewModelProvider(
            this, ViewModelProvider.NewInstanceFactory()
        )[MainViewModel::class.java]


        mainViewModel.getListMovie(apiKey = "ba7b7ec258e912a3c68b34e6dfba3ca5")
        mainViewModel.movies.observe(this, {
            showMovie(it)
        })
        mainViewModel.isLoading.observe(this, { isLoading ->
            showLoading(isLoading)
        })
    }

    private fun showMovie(movies: List<ResultsItem>?) {

        if (movies != null) {
            movieAdapter.addItems(movies)
            binding.rvMovies.apply {
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = movieAdapter
            }
        }
    }


    private fun showLoading(isLoading:Boolean) {
        binding.progressbar.visibility = if(isLoading) View.VISIBLE else View.GONE
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_favorite -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}