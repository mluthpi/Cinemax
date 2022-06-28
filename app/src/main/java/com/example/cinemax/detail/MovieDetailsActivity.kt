package com.example.cinemax.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.cinemax.R
import com.example.cinemax.ViewModelFactory
import com.example.cinemax.data.MovieDetailResponse
import com.example.cinemax.data.ResultsItem
import com.example.cinemax.databinding.ActivityMovieDetailsBinding
import com.example.cinemax.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailsBinding
    private lateinit var detailsViewModel: DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra("KEY_ID", 0)!!
        getMovieDetails(id = id)

        setupViewModel()

        detailsViewModel.detailMovie.observe(this, {movieDetails ->
            showMovieDetail(movieDetails)
        })


    }


    private fun setupViewModel() {
        detailsViewModel = obtainViewModel(this)
    }

    private fun getMovieDetails(id: Int) {
        ApiConfig.getApiService().getDetails(id = Int, apiKey = "ba7b7ec258e912a3c68b34e6dfba3ca5")
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

   private fun showMovieDetail(movieDetailResponse: MovieDetailResponse) {
       Glide.with(this)
           .load(movieDetailResponse.posterPath)
           .into(binding.imgDetail)
       binding.title.text = movieDetailResponse.title

       detailsViewModel.getFavoriteMovie().observe(this, {favMovie ->
           val isFavorite = favMovie.filter { it.title == movieDetailResponse.title }.isNotEmpty()
           setupFavoriteMovie(isFavorite, movieDetailResponse)
       })
   }

    private fun setupFavoriteMovie(isFavorite: Boolean, movie: MovieDetailResponse) {
        if (isFavorite) {
            binding.fbFavorite.setImageResource(R.drawable.ic_baseline_favorited_24)

            binding.fbFavorite.setOnClickListener {
                val movie = movie.title?.let { it1 ->
                    ResultsItem(
                        title = it1,
                        posterPath = movie.posterPath
                    )
                    detailsViewModel.deleteFromDB(movie)
                    Toast.makeText(this, "Berhasil dihapus dari favorite", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        } else {
            binding.fbFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)

            binding.fbFavorite.setOnClickListener{
                val movie = movie.title?.let { it1 ->
                    ResultsItem(
                        title = it1,
                        posterPath = movie.posterPath
                    )
                    detailsViewModel.insertToDB(movie)
                    Toast.makeText(this, "Berhasil ditambahkan ke favorite", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun obtainViewModel(activity: AppCompatActivity) : DetailsViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(DetailsViewModel::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.root
    }


}



//    private fun showDetails() {
//        binding.titleDesc.text = Response(MovieResponse).title
//    }
