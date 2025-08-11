package com.adremtest.android.base.network

sealed class NetworkResult<T> {
    data class NoInternetConnection<T>(val errorMsg: String) : NetworkResult<T>()
    data class Loading<T>(val isLoading: Boolean) : NetworkResult<T>()
    data class Success<T>(val data: T) : NetworkResult<T>()
    data class Failure<T>(val errorMessage: String) : NetworkResult<T>()
//    data class Idle<T>(val isIdle: Boolean) : NetworkResult<T>()
}