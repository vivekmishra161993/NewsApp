package com.mtt.newsapp.data.datasource

import com.mtt.newsapp.data.entity.NewsResponse
import retrofit2.Response

interface NewsDataSource {
    suspend fun getNewsHeadline(country: String): Response<NewsResponse>
}