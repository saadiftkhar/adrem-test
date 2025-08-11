package com.adremtest.android.nytimes.domain.usecase

import com.adremtest.android.base.network.NetworkResult
import com.adremtest.android.nytimes.domain.repository.NyRepository
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NyTimesUseCase @Inject constructor(private val nyRepository: NyRepository) {
    suspend fun invokeNyRecords(apiKey: String?) = flow {
        if (!apiKey.isNullOrEmpty()) {
            /**
             * Use **emitAll()** when you're just passing the flow along.
             * Use **collect { emit(...) }** when you need logic per item (e.g., filtering, mapping, logging, validation).
             * */
            emitAll(nyRepository.fetchNyRecords(apiKey))
        } else {
            emit(NetworkResult.Failure(""))
        }
    }

}