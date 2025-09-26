package com.calyrsoft.ucbp1.features.movie.data.repository

import com.calyrsoft.ucbp1.features.movie.data.datasource.MovieLocalDataSource
import com.calyrsoft.ucbp1.features.movie.data.datasource.MovieRemoteDataSource
import com.calyrsoft.ucbp1.features.movie.domain.model.MovieModel
import com.calyrsoft.ucbp1.features.movie.domain.repository.IMoviesRepository

class MovieRepository(
    private val remote: MovieRemoteDataSource,
    private val local: MovieLocalDataSource
) : IMoviesRepository {

    override suspend fun fetchPopularMovies(): Result<List<MovieModel>> {
        val result = remote.fetchPopularMovies()
        result.onSuccess { movies ->
            if (movies.isNotEmpty()) {
                local.saveAll(movies)
            }
        }
        return result
    }

    suspend fun getPopularFromLocal(): List<MovieModel> = local.getAll()
}
