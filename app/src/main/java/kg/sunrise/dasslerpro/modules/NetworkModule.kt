package kg.sunrise.dasslerpro.modules

import android.content.Context
import kg.sunrise.dasslerpro.BuildConfig
import kg.sunrise.dasslerpro.utils.preference.getToken
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private val sLogLevel =
        if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

private const val version = "v1"
private const val testUrl = "http://dassler.sunrisestudio.pro/api/$version/"

private const val currentUrl = testUrl

fun createNetworkClient(context: Context) = retrofitClient(
    okHttpClient(context)
)

private fun retrofitClient(httpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(currentUrl)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}


private fun getLogInterceptor() = HttpLoggingInterceptor().apply { level = sLogLevel }

private fun okHttpClient(context: Context) = OkHttpClient.Builder()
        .addInterceptor(getLogInterceptor()).apply {
            setTimeOutToOkHttpClient(
                    this
            )
        }
        .addInterceptor(headersInterceptor(context)).build()

fun headersInterceptor(context: Context) = Interceptor { chain ->
    chain.proceed(
            chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .also {
                        val token = getToken(context)
                        if (token != "empty") {
                            it.addHeader(
                                    "Authorization",
                                    "Token $token"
                            )
                        }
                    }
                    .build()
    )
}

private fun setTimeOutToOkHttpClient(okHttpClientBuilder: OkHttpClient.Builder) =
        okHttpClientBuilder.apply {
            readTimeout(30L, TimeUnit.SECONDS)
            connectTimeout(30L, TimeUnit.SECONDS)
            writeTimeout(30L, TimeUnit.SECONDS)
        }