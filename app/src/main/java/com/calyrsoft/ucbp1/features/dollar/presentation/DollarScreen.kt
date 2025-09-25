package com.calyrsoft.ucbp1.features.dollar.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DollarScreen(viewModelDollar: DollarViewModel = koinViewModel()) {
    val state = viewModelDollar.uiState.collectAsState()
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        when (val stateValue = state.value) {
            is DollarViewModel.DollarUIState.Error -> Text(stateValue.message)
            DollarViewModel.DollarUIState.Loading -> CircularProgressIndicator()
            is DollarViewModel.DollarUIState.Success -> {
                val data = stateValue.data

                Text("Actualizado: ${formatUpdatedAt(data.updatedAt)}")

                Text("Oficial: ${data.dollarOfficial ?: "—"}")
                Text("Paralelo: ${data.dollarParallel ?: "—"}")
                Text("USDT: ${data.usdt ?: "—"}")
                Text("USDC: ${data.usdc ?: "—"}")
            }
        }
    }
}

private fun formatUpdatedAt(updatedAt: String?): String {
    if (updatedAt.isNullOrBlank()) return "—"
    return try {
        val millis = updatedAt.toLong()
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        sdf.format(Date(millis))
    } catch (_: Exception) {
        updatedAt
    }
}
