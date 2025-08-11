package com.adremtest.android.base.network

import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(
    @SerializedName("data")
    var data: T? = null
) : BaseResponse()
