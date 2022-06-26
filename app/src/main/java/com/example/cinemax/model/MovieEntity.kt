package com.example.cinemax.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity
data class MovieEntity (
    @PrimaryKey(autoGenerate = true)
    @SerializedName("title")
    val title: String,

    @SerializedName("poster_path")
    val posterPath: String? = null
        )