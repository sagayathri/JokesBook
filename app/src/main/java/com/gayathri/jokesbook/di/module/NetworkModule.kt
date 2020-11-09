package com.gayathri.jokesbook.di.module

import com.gayathri.jokesbook.BuildConfig
import com.gayathri.jokesbook.data.api.IApiService
import com.gayathri.jokesbook.utils.Constants
import com.gayathri.jokesbook.utils.Constants.BASE_URL_NAMED_QUALIFIER
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    @Named(BASE_URL_NAMED_QUALIFIER)
    fun provideBaseUrl(): String = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(builder: OkHttpClient.Builder): OkHttpClient = builder.build()

    @Singleton
    @Provides
    fun provideJsonConfiguration() = JsonConfiguration.Stable.copy(ignoreUnknownKeys = true)

    @Singleton
    @Provides
    fun provideJson(configuration: JsonConfiguration) = Json(configuration)

    @Singleton
    @Provides
    fun provideMediaType(): MediaType = "application/json".toMediaTypeOrNull()!!

    @Singleton
    @Provides
    fun provideOkHttpClientBuilder(): OkHttpClient.Builder =
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            .connectTimeout(Constants.CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(Constants.CONNECT_TIMEOUT, TimeUnit.SECONDS)

    @Singleton
    @Provides
    fun provideRetrofitInstance(
        @Named(BASE_URL_NAMED_QUALIFIER) baseUrl: String,
        okHttpClient: OkHttpClient,
        config: JsonConfiguration,
        mediaType: MediaType
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(Json(config).asConverterFactory(mediaType))
            .client(okHttpClient)
            .build()

    @Provides
    fun provideApiService(retrofit: Retrofit): IApiService {
        return retrofit.create(IApiService::class.java)
    }
}