package com.adremtest.android.base.network

import com.sxadminapp.android.base.ErrorResponse

sealed class Result<out T> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val errorResponse: ErrorResponse) : Result<Nothing>()
}

