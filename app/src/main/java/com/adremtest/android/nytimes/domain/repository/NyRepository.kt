package com.adremtest.android.nytimes.domain.repository

import com.adremtest.android.base.network.NetworkResult
import com.adremtest.android.nytimes.domain.entity.NyTimesResponse
import kotlinx.coroutines.flow.Flow

interface NyRepository {
    suspend fun fetchNyRecords(apiKey: String): Flow<NetworkResult<NyTimesResponse>>
}