package com.calyrsoft.ucbp1.features.movie.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.koin.androidx.compose.koinViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun PopularMoviesScreen(
    popularMoviesViewModel: PopularMoviesViewModel = koinViewModel()
) {
    val state = popularMoviesViewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(popularMoviesViewModel) {
        popularMoviesViewModel.fetchPopularMovies()
    }

    when (val s = state.value) {
        is PopularMoviesViewModel.UiState.Error -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(s.message)
            }
        }
        is PopularMoviesViewModel.UiState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is PopularMoviesViewModel.UiState.Success -> {
            PopularMoviesView(movies = s.movies)
        }
    }
}
