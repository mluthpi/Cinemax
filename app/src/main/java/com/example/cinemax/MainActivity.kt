package com.example.cinemax

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cinemax.databinding.ActivityMainBinding
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
        showMovie()
    }

    private fun showMovie() {

        binding.rvMovies.setHasFixedSize(true)
        binding.rvMovies.layoutManager = LinearLayoutManager(this)

        ApiConfig.getApiService().getMovie().enqueue(object : Callback<List<MovieEntity>>{
            override fun onResponse(
                call: Call<List<MovieEntity>>,
                response: Response<List<MovieEntity>>
            ) {
                val list = response.body()
                val adapter = list.let { MovieAdapter(it) }
                binding.rvMovies.adapter = adapter
            }

            override fun onFailure(call: Call<List<MovieEntity>>, t: Throwable) {
                Toast.makeText(this@MainActivity,"${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


}