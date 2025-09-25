package com.calyrsoft.ucbp1.features.dollar.data.repository

import com.calyrsoft.ucbp1.features.dollar.data.datasource.DollarLocalDataSource
import com.calyrsoft.ucbp1.features.dollar.data.datasource.RealTimeRemoteDataSource
import com.calyrsoft.ucbp1.features.dollar.domain.model.DollarModel
import com.calyrsoft.ucbp1.features.dollar.domain.repository.IDollarRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class DollarRepository(
    private val realTimeRemoteDataSource: RealTimeRemoteDataSource,
    private val localDataSource: DollarLocalDataSource
) : IDollarRepository {

    override suspend fun getDollar(): Flow<DollarModel> {
        return realTimeRemoteDataSource
            .getDollarUpdates()
            .map { computeUsdtUsdc(it) }
            .onEach { localDataSource.insert(it) }
    }


    private fun computeUsdtUsdc(input: DollarModel): DollarModel {
        val oficial  = input.dollarOfficial?.toDoubleOrNull()
        val paralelo = input.dollarParallel?.toDoubleOrNull()
        val base     = paralelo ?: oficial

        val usdtBs = base?.let { "%.2f".format(it) } 
        val usdcBs = base?.let { "%.2f".format(it) }

        return input.copy(
            usdt = usdtBs ?: input.usdt,
            usdc = usdcBs ?: input.usdc,
            updatedAt = input.updatedAt ?: System.currentTimeMillis().toString(),
            timestamp = input.timestamp ?: System.currentTimeMillis()
        )
    }
}
