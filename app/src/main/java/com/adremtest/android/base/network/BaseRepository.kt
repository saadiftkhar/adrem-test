package com.adremtest.android.base.network

import android.content.Context
import android.content.res.Resources
import com.adremtest.android.base.ErrorResponse
import com.adremtest.android.base.ResponseModel
import com.google.gson.Gson
import okhttp3.RequestBody
import okio.Buffer
import org.json.JSONObject
import retrofit2.Response
import java.io.IOException
import java.net.UnknownHostException
import java.net.UnknownServiceException


open class BaseRepository(private val resource: Resources, val context: Context) {


    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>): ResponseModel<T> {

        return when (val result: Result<T> = safeApiResult(call)) {
            is Result.Success ->
                ResponseModel(result.data, null)

            is Result.Error -> {
                ResponseModel(null, result.errorResponse)
            }

        }
    }

    private suspend fun <T : Any> safeApiResult(call: suspend () -> Response<T>): Result<T> {
        try {
            val response = call.invoke()
            if (response.isSuccessful) {
                return Result.Success(response.body()!!)
            }

            return Result.Error(parseError(response))
        } catch (ex: Exception) {
            ex.printStackTrace()
            return Result.Error(parseError(ex))
        }
    }

    /**
     * Parse Error Response from Server Side Response
     */
    private fun parseError(response: Response<*>): ErrorResponse {
        val errorBody = response.errorBody()?.string()
        var errorResponse: ErrorResponse
        try {
            errorResponse = Gson().fromJson(
                JSONObject(errorBody).toString(),
                ErrorResponse::class.java
            )
        } catch (ex: Exception) {
            errorResponse = parseError(response.code())
            ex.printStackTrace()
        }
        return errorResponse
    }

    /**
     * Generate Local Error Response from any exception
     */
    private fun parseError(exception: Exception): ErrorResponse {
        val errorResponse = ErrorResponse()
        if (exception is UnknownHostException || exception is UnknownServiceException) {
            errorResponse.message = "internet lost"
        } else {
            errorResponse.message = ""
        }
        return errorResponse
    }

    /**
     * Generate Local Error Response from http error code
     */
    private fun parseError(code: Int): ErrorResponse {
        val errorResponse = ErrorResponse()
        errorResponse.code = code
        when (code) {
            in 500..510 -> {
                errorResponse.message = "Error code 502"
            }

            in 401..403 -> {
                errorResponse.message = "Error code 403"
            }

            else -> {
                errorResponse.message = "internet lost"
            }
        }
        return errorResponse
    }

    private fun bodyToString(request: RequestBody?): String {
        try {
            val buffer = Buffer()
            if (request != null)
                request.writeTo(buffer)
            else
                return ""
            return buffer.readUtf8()
        } catch (e: IOException) {
            return "Error Response"
        }
    }
}