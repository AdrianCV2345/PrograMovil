package com.calyrsoft.ucbp1.features.movie.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey val id: Long,          // TMDb id
    val title: String,
    val posterPath: String?,           // "/xxx.jpg"
    val popularity: Double?,           // opcional
    val overview: String?,             // opcional
    val updatedAt: Long                // System.currentTimeMillis()
)
