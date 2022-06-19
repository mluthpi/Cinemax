package com.example.cinemax

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.cinemax.data.MovieDetailResponse
import com.example.cinemax.data.MovieResponse
import com.example.cinemax.data.ResultsItem
import com.example.cinemax.databinding.ActivityMovieDetailsBinding
import com.example.cinemax.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra("KEY_ID", 0)!!
        getMovieDetails(id = id)

    }

    private fun getMovieDetails(id: Int) {
        ApiConfig.getApiService().getDetails(id = id, apiKey = "ba7b7ec258e912a3c68b34e6dfba3ca5")
            .enqueue(object : Callback<MovieDetailResponse> {
                override fun onResponse(
                    call: Call<MovieDetailResponse>,
                    response: Response<MovieDetailResponse>
                ) {
                    val movie = response.body()
                    if (movie != null) {
                        binding.titleDesc.text = movie.title
                        binding.overviewDesc.text = movie.overview
                        binding.rating.text = movie.voteAverage.toString()
                        Glide.with(this@MovieDetailsActivity)
                            .load("https://image.tmdb.org/t/p/original${movie.posterPath}")
                            .into(binding.imgDetail)
                    }
                }

                override fun onFailure(call: Call<MovieDetailResponse>, t: Throwable) {
                }

            })
    }



//    private fun showDetails() {
//        binding.titleDesc.text = Response(MovieResponse).title
//    }
}