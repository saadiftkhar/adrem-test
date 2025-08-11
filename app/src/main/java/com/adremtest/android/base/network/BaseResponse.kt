package com.adremtest.android.base.network

import com.google.gson.annotations.SerializedName

abstract class BaseResponse(
    @SerializedName("status")
    var status: String? = null,

    @SerializedName("copyright")
    var copyright: Int? = null,

    @SerializedName("num_results")
    var numResults: Int? = null,
)