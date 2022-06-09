package com.example.cinemax

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "movie")
class MovieEntity (
    @field:ColumnInfo(name = "name")
    @field:PrimaryKey
    val name: String,

    @field:ColumnInfo(name = "logo_path")
    val logoPath: String? = null
        )