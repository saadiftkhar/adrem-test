package com.adremtest.android.base.network

import android.content.Context
import android.content.res.Resources
import com.google.gson.Gson
import com.sxadminapp.android.R
import com.sxadminapp.android.base.ErrorResponse
import com.adremtest.android.base.ResponseModel
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

            /*if (response.code() == 401 || response.code() == 403 || response.code() == 801) {
                AppConstants.appSharedStorage.logout()
                val broadcastIntent = Intent(BaseFragment.FORCE_LOGOUT_BROADCAST_EVENT)
                LocalBroadcastManager.getInstance(context).sendBroadcast(broadcastIntent)
                return NetworkResult.Failure(resource.getString(R.string.session_expired))
            }*/

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
            errorResponse.message = resource.getString(R.string.internet_lost)
//        } else if (exception is SSLPeerUnverifiedException) {
//            errorResponse.message = getAppContext().getString(R.string.error_message_ssl_pining)
//            CommonConfig.onSslException()
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
                errorResponse.message = resource.getString(R.string.error_message_502)
            }

            in 401..403 -> {
                errorResponse.message = resource.getString(R.string.error_message_403)
            }

            else -> {
                errorResponse.message = resource.getString(R.string.internet_lost)
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