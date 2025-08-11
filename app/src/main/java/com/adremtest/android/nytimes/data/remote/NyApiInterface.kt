package com.adremtest.android.nytimes.data.remote

import com.adremtest.android.base.network.ApiParams
import com.adremtest.android.base.network.ApiResponse
import com.adremtest.android.common.api.ApiEndPoints
import com.adremtest.android.nytimes.domain.entity.NyTimesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NyApiInterface {

    @GET(ApiEndPoints.GET_NY_RECORDS)
    suspend fun getNyTimes(
        @Query(ApiParams.API_KEY) apiKey: String,
    ): Response<ApiResponse<NyTimesResponse>>
}