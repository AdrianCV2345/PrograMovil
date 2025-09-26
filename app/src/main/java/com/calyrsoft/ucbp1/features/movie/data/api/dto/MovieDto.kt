package com.calyrsoft.ucbp1.features.movie.data.api.dto

import com.google.gson.annotations.SerializedName

data class MovieDto(
    val id: Long,
    @SerializedName("poster_path") val pathUrl: String?,
    val title: String,
    val popularity: Double?,
    val overview: String?
)
