package com.amin.oqatsharee.di

import com.amin.oqatsharee.api.ApiServices
import com.amin.oqatsharee.utils.Constants.BASE_URL
import com.amin.oqatsharee.utils.Constants.NETWORK_TIMEOUT
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    @Singleton
    fun provideBaseURL() : String = BASE_URL

    @Provides
    @Singleton
    fun provideNetworkTimeout() : Long = NETWORK_TIMEOUT

    @Provides
    @Singleton
    fun provideGson() : Gson =  GsonBuilder().setLenient().create()

    @Provides
    @Singleton
    fun provideInterceptor() : HttpLoggingInterceptor  = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Singleton
    fun provideClient(time : Long , interceptor : HttpLoggingInterceptor ) : OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .writeTimeout(time , TimeUnit.SECONDS)
        .readTimeout(time , TimeUnit.SECONDS)
        .connectTimeout(time , TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit( baseUrl : String , gson: Gson , client: OkHttpClient) : ApiServices = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client)
        .build()
        .create(ApiServices::class.java)

}