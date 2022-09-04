package com.bookfinder.di

import com.bookfinder.constants.Constants
import com.bookfinder.network.ApiService
import com.bookfinder.network.error.Rx2ErrorHandlingCallAdapterFactory
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.net.CookieManager
import java.net.CookiePolicy
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Network에 대한 객체 생성을 함
 * Hilt를 통한 의존성 주입으로 repository 를 통해 사용
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(Constants.NetWork.CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(Constants.NetWork.WRITE_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(Constants.NetWork.READ_TIMEOUT, TimeUnit.SECONDS)
            .cookieJar(JavaNetCookieJar(CookieManager(null, CookiePolicy.ACCEPT_ALL)))
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    private fun getRetrofit(url: String): Retrofit {
        return Retrofit.Builder()
            .addCallAdapterFactory(Rx2ErrorHandlingCallAdapterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(provideGs()))
            .baseUrl(url)
            .client(provideOkHttpClient())
            .build()
    }

    private fun provideGs(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun api(): ApiService {
        return getRetrofit(
            "https://www.googleapis.com/",
        ).create(ApiService::class.java)
    }

}