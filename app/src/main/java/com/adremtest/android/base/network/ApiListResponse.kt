package com.adremtest.android.base.network

import com.google.gson.annotations.SerializedName

data class ApiListResponse<T>(
    @SerializedName("data")
    var data: ArrayList<T> = arrayListOf()
) : BaseResponse()
