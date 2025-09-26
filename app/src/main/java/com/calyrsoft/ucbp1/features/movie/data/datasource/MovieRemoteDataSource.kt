package com.calyrsoft.ucbp1.features.movie.data.datasource

import com.calyrsoft.ucbp1.features.movie.data.api.MovieService
import com.calyrsoft.ucbp1.features.movie.domain.model.MovieModel

class MovieRemoteDataSource(
    private val movieService: MovieService,
    private val apiKey: String
) {
    suspend fun fetchPopularMovies(): Result<List<MovieModel>> {
        return try {
            val response = movieService.fetchPopularMovies(apiKey = apiKey)

            if (response.isSuccessful) {
                val moviePage = response.body()
                val movies = moviePage?.results?.map { dto ->
                    MovieModel(
                        pathUrl = "https://image.tmdb.org/t/p/w185${dto.pathUrl}",
                        title = dto.title,
                        id = dto.id
                    )
                } ?: emptyList()

                Result.success(movies)
            } else {
                Result.failure(Exception("Error HTTP ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
