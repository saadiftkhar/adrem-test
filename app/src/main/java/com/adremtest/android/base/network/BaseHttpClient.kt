package com.adremtest.android.base.network

import android.content.Context
import android.util.Log
import androidx.multidex.BuildConfig
import com.adremtest.android.common.api.ApiHeaderKey
import com.adremtest.android.common.api.ApiHeaderValue
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.security.UnrecoverableKeyException
import java.security.spec.InvalidKeySpecException
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton
import javax.security.cert.CertificateException


private const val TIMEOUT = 120L

@Singleton
class BaseHttpClient @Inject constructor(
    @ApplicationContext val context: Context
) {

    fun okHttpClient(): OkHttpClient {
        try {

            val okHttpClient = OkHttpClient()
                .newBuilder()
                .addInterceptor(
                    HttpLoggingInterceptor().apply {
                        level =
                            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.BODY
                    }
                )
                .addInterceptor(authInterceptor)
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .build()

            return okHttpClient
        } catch (e: CertificateException) {
            Log.d("OK_HTTP_ISSUE", "okHttpClient: ${e.message}")
            return OkHttpClient()
        } catch (e: IOException) {
            Log.d("OK_HTTP_ISSUE", "okHttpClient:  ${e.message}")
            return OkHttpClient()
        } catch (e: NoSuchAlgorithmException) {
            Log.d("OK_HTTP_ISSUE", "okHttpClient:  ${e.message}")
            return OkHttpClient()
        } catch (e: UnrecoverableKeyException) {
            Log.d("OK_HTTP_ISSUE", "okHttpClient:  ${e.message}")
            return OkHttpClient()
        } catch (e: KeyManagementException) {
            Log.d("OK_HTTP_ISSUE", "okHttpClient:  ${e.message}")
            return OkHttpClient()
        } catch (e: InvalidKeySpecException) {
            Log.d("OK_HTTP_ISSUE", "okHttpClient:  ${e.message}")
            return OkHttpClient()
        }
    }

}


private val authInterceptor = Interceptor { chain ->

    var request = chain.request()
    val requestBuilder = request.newBuilder()
    request = requestBuilder
        .addHeader(ApiHeaderKey.CONTENT_TYPE, ApiHeaderValue.APPLICATION_JSON)
        .addHeader(ApiHeaderKey.APP_ID, ApiHeaderValue.APP_ID)
        .addHeader(ApiHeaderKey.PASSWORD, ApiHeaderValue.PASSWORD)
        .addHeader(ApiHeaderKey.LANGUAGE, ApiHeaderValue.LOCALE_EN)
        .addHeader(ApiHeaderKey.DEVICE_TYPE, ApiHeaderValue.DEVICE_TYPE)
        .build()
    val response = chain.proceed(request)
    val responseBuilder = response.newBuilder()

    responseBuilder.build()
}



