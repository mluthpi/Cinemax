package com.example.cinemax.network

import com.example.cinemax.data.MovieDetailResponse
import com.example.cinemax.data.MovieResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {
    @GET("movie/now_playing")
    fun getMovie(@Query("api_key") apiKey: String): Call<MovieResponse>

    @GET("movie/{movie_id}")
    fun getDetails(
        @Path("movie_id") id: Int.Companion,
        @Query("api_key") apiKey: String
    ) : Call<MovieDetailResponse>
}