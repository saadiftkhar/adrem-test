package com.adremtest.android.base

data class ResponseModel<T>(val successResponse: T?, val errorResponse: ErrorResponse?)