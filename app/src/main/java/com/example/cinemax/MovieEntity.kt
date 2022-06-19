package com.example.cinemax

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "movie")
data class MovieEntity (
    @SerializedName("name")
    val name: String,

    @SerializedName("poster_path")
    val posterPath: String? = null
        )