package com.adremtest.android.nytimes.data.remote

import com.adremtest.android.base.network.ApiListResponse
import com.adremtest.android.base.network.ApiParams
import com.adremtest.android.nytimes.domain.entity.NyTimesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NyApiInterface {

    @GET
    suspend fun getNyTimes(
        @Query(ApiParams.API_KEY) pageNo: String,
    ): Response<ApiListResponse<NyTimesResponse>>
}