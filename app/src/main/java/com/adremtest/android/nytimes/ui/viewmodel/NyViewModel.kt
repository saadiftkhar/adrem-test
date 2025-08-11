package com.adremtest.android.nytimes.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adremtest.android.base.network.NetworkResult
import com.adremtest.android.nytimes.domain.entity.Article
import com.adremtest.android.nytimes.domain.entity.NyTimesResponse
import com.adremtest.android.nytimes.domain.usecase.NyTimesUseCase
import com.adremtest.android.utils.AppConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NyViewModel @Inject constructor(
    private val nyTimesUseCase: NyTimesUseCase
) : ViewModel() {

    private val _nyRecords = MutableStateFlow<List<Article>>(emptyList())
    val nyRecords: StateFlow<List<Article>> = _nyRecords

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading


    init {
        fetchNyRecords()
    }


    fun fetchNyRecords() {
        viewModelScope.launch {
            _isLoading.value = true

            val flow1: Flow<NetworkResult<NyTimesResponse>> =
                nyTimesUseCase.invokeNyRecords(AppConstants.API_KEY)
            val flow2: Flow<NetworkResult<NyTimesResponse>> =
                nyTimesUseCase.invokeNyRecords(AppConstants.API_KEY)

            combine(flow1, flow2) { first, second ->
                when {
                    first is NetworkResult.Success && second is NetworkResult.Success -> {
                        val combined = buildList {
                            addAll(first.data.results ?: emptyList())
                            addAll(second.data.results ?: emptyList())
                        }
                        _isLoading.value = false
                        _errorMessage.value = null
                        combined
                    }

                    first is NetworkResult.Failure || second is NetworkResult.Failure -> {
                        _isLoading.value = false
                        _errorMessage.value = "Something went wrong"
                        emptyList()
                    }

                    else -> {
                        _isLoading.value = false
                        _errorMessage.value = "Unknown error"
                        emptyList()
                    }
                }
            }.collect { combinedList ->
                _nyRecords.value = combinedList
            }
        }
    }

}