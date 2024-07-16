package com.mtt.newsapp.di

import com.mtt.newsapp.data.AppConstants
import com.mtt.newsapp.data.api.ApiService
import com.mtt.newsapp.data.datasource.NewsDataSource
import com.mtt.newsapp.data.datasource.NewsDataSourceImpl
import com.mtt.newsapp.ui.repository.NewsRepository
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
class AppModule {
    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val httpClient = OkHttpClient().newBuilder().apply {
            addInterceptor(interceptor)
            readTimeout(60, TimeUnit.SECONDS)
        }


        return Retrofit.Builder().baseUrl(AppConstants.BASE_URL)
            .client(httpClient.build()).addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providesApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesNewsDataSource(apiService: ApiService): NewsDataSource {
        return NewsDataSourceImpl(apiService)
    }

    @Provides
    @Singleton
    fun providesNewsRepo(dataSource: NewsDataSource): NewsRepository {
        return NewsRepository(dataSource)
    }
}