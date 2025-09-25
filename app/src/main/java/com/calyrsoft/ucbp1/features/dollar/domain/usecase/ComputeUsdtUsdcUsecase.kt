package com.calyrsoft.ucbp1.features.dollar.domain.usecase

import com.calyrsoft.ucbp1.features.dollar.domain.model.DollarModel
import kotlin.math.max

class ComputeUsdtUsdcUseCase(
    private val preferParallel: Boolean = true,    // usa paralelo por defecto
    private val feeUsdt: Double = 0.0,             // comisión opcional
    private val feeUsdc: Double = 0.0
) {
    operator fun invoke(input: DollarModel): DollarModel {
        // Lee oficial/paralelo (tú usas String? en Model; convierto con cuidado)
        val oficial = input.dollarOfficial?.toDoubleOrNull()
        val paralelo = input.dollarParallel?.toDoubleOrNull()

        val base = if (preferParallel) (paralelo ?: oficial) else (oficial ?: paralelo)

        val usdtBs = base?.let { it * (1.0 + feeUsdt) }
        val usdcBs = base?.let { it * (1.0 + feeUsdc) }

        return input.copy(
            usdt = usdtBs?.let { "%.2f".format(it) } ?: input.usdt,
            usdc = usdcBs?.let { "%.2f".format(it) } ?: input.usdc,
            // si no viene updatedAt desde Firebase, usamos ahora
            updatedAt = input.updatedAt ?: System.currentTimeMillis().toString(),
            timestamp = input.timestamp ?: System.currentTimeMillis()
        )
    }
}
