package com.adremtest.android.nytimes.domain.usecase

import com.adremtest.android.nytimes.domain.repository.NyRepository
import javax.inject.Inject

class NyTimesUseCase @Inject constructor(private val nyRepository: NyRepository) {
    suspend fun invokeNyRecords(apiKey: String) =
        nyRepository.fetchNyRecords(apiKey)
}