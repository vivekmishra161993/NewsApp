package com.mtt.newsapp.data.api

import com.mtt.newsapp.data.entity.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("v2/top-headlines")
    suspend fun getNewsHeadline(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String = "f2ab776c6f084852b025a01a5e57c59c"
    ): Response<NewsResponse>
}
//https://newsapi.org/v2/top-headlines?country=us&apiKey=f2ab776c6f084852b025a01a5e57c59c