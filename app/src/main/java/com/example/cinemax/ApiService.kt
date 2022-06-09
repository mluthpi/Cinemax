package com.example.cinemax

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    @GET("/ba7b7ec258e912a3c68b34e6dfba3ca5")
    fun getMovie(): Call<List<Response>>
}