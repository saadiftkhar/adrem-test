package com.adremtest.android.nytimes.data.repository

import android.content.Context
import android.content.res.Resources
import com.adremtest.android.base.network.BaseRepository
import com.adremtest.android.base.network.NetworkResult
import com.adremtest.android.nytimes.data.remote.NyApiInterface
import com.adremtest.android.nytimes.domain.repository.NyRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.flow

class NyRepositoryImpl @Inject constructor(
    private val nyApiInterface: NyApiInterface,
    resource: Resources,
    context: Context
) : BaseRepository(resource, context), NyRepository {

    override suspend fun fetchNyRecords(apiKey: String) = flow {

        val result = safeApiCall { nyApiInterface.getNyTimes(apiKey) }

        if (result.successResponse != null) {
            emit(NetworkResult.Success(result.successResponse))
        } else emit(NetworkResult.Failure(result.toString()))
    }
}