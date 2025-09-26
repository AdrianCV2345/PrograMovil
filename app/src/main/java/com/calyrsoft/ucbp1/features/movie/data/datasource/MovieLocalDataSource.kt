package com.calyrsoft.ucbp1.features.movie.data.datasource

import com.calyrsoft.ucbp1.features.movie.data.database.dao.IMovieDao
import com.calyrsoft.ucbp1.features.movie.data.mapper.toDomain
import com.calyrsoft.ucbp1.features.movie.data.mapper.toEntity
import com.calyrsoft.ucbp1.features.movie.domain.model.MovieModel

class MovieLocalDataSource(
    private val dao: IMovieDao
) {
    suspend fun saveAll(movies: List<MovieModel>) {
        dao.upsertAll(movies.map { it.toEntity() })
    }

    suspend fun getAll(): List<MovieModel> {
        return dao.getAll().map { it.toDomain() }
    }
}
