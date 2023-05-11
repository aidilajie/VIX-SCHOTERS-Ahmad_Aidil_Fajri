package com.example.vix_schoters_newsapp

import android.app.Application
import retrofit2.Response

class NewsRepository(private val application: Application) {

    private val service: NewsService = RetrofitClient.getRetrofit().create(NewsService::class.java)
    private val newsDao: NewsDao = AppDatabase.getInstance(application).newsDao()

    suspend fun getTopHeadlines(): List<Article> {
        val response = service.getTopHeadlines()
        if (response.isSuccessful) {
            response.body()?.articles?.let { articles ->
                // Save articles to local database
                newsDao.insertArticles(articles)
                // Return articles from API response
                return articles
            }
        }
        // If the API call failed or the response body is null, return articles from local database
        return newsDao.getArticles()
    }
    suspend fun getTopHeadlines(country: String): Response<NewsResponse> {
        return apiService.getTopHeadlines(country)
    }

}
