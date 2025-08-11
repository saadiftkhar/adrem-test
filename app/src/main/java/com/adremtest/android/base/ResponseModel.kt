package com.adremtest.android.base

import com.sxadminapp.android.base.ErrorResponse

data class ResponseModel<T>(val successResponse: T?, val errorResponse: ErrorResponse?)