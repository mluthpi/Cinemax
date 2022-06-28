package com.example.cinemax.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cinemax.ViewModelFactory
import com.example.cinemax.data.ResultsItem
import com.example.cinemax.databinding.ActivityFavoriteBinding
import com.example.cinemax.detail.MovieDetailsActivity
import com.example.cinemax.model.MovieEntity

class FavoriteActivity : AppCompatActivity() {

    private var _binding : ActivityFavoriteBinding? = null
    private val binding get() = _binding!!

    private lateinit var favoriteViewModel: FavoriteViewModel

    private var favoriteAdapter = FavoriteAdapter {
        val intent = Intent(this, MovieDetailsActivity::class.java)
        intent.putExtra("KEY_ID", it.id)
        startActivity(intent)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()

        favoriteViewModel.getFavoriteMovie().observe(this, {
//            showFavoriteMovie(it)
        })
    }

    private fun setupView() {
        supportActionBar?.title = "Favorite"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupViewModel() {
        favoriteViewModel = obtainViewModel(this)
    }

    private fun showFavoriteMovie(favMovieList: List<ResultsItem>) {
        if (favMovieList.isNotEmpty()) {
            favoriteAdapter.addItems(favMovieList)

            binding.tvEmptyFavorite.visibility = View.GONE
            binding.rvMovies.visibility = View.VISIBLE
            binding.rvMovies.apply {
                layoutManager = LinearLayoutManager(
                    this@FavoriteActivity,
                    RecyclerView.VERTICAL, false
                )
                adapter = favoriteAdapter
            }
        } else {
            binding.tvEmptyFavorite.visibility = View.VISIBLE
            binding.rvMovies.visibility = View.GONE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteViewModel::class.java)
    }
}