package com.calyrsoft.ucbp1.features.movie.data.mapper

import com.calyrsoft.ucbp1.features.movie.data.api.dto.MovieDto
import com.calyrsoft.ucbp1.features.movie.data.database.entity.MovieEntity
import com.calyrsoft.ucbp1.features.movie.domain.model.MovieModel

private const val TMDB_IMG = "https://image.tmdb.org/t/p/w185"

fun MovieDto.toDomain(): MovieModel = MovieModel(
    id = id,
    title = title,
    pathUrl = pathUrl?.let { "$TMDB_IMG$it" } ?: ""
)

fun MovieDto.toEntity(): MovieEntity = MovieEntity(
    id = id,
    title = title,
    posterPath = pathUrl,
    popularity = null,
    overview = null,
    updatedAt = System.currentTimeMillis()
)

fun MovieEntity.toDomain(): MovieModel = MovieModel(
    id = id,
    title = title,
    pathUrl = posterPath?.let { "$TMDB_IMG$it" } ?: ""
)

fun MovieModel.toEntity(): MovieEntity = MovieEntity(
    id = id,
    title = title,
    posterPath = if (pathUrl.startsWith("http")) pathUrl.removePrefix(TMDB_IMG) else pathUrl,
    popularity = null,
    overview = null,
    updatedAt = System.currentTimeMillis()
)
