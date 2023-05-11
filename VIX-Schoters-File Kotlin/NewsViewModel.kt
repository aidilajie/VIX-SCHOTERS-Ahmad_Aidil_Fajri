package com.example.vix_schoters_newsapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class NewsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: NewsRepository = NewsRepository(application)

    private val _news = MutableLiveData<List<Article>>()
    val news: LiveData<List<Article>> = _news

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getTopHeadlines() {
        viewModelScope.launch {
            _isLoading.value = true
            val articles = repository.getTopHeadlines()
            _isLoading.value = false
            _news.value = articles
        }
    }
    fun getTopHeadlines(country: String) = viewModelScope.launch {
        try {
            val response = repository.getTopHeadlines(country)
            if (response.isSuccessful) {
                response.body()?.let { newsResponse ->
                    val articles = newsResponse.articles.map { article ->
                        article.apply {
                            this.country = country
                            this.bookmarked = database.isArticleBookmarked(this)
                        }
                    }
                    database.insertArticles(articles)
                }
            } else {
                Log.e(TAG, "Response failed: ${response.errorBody()?.string()}")
            }
        } catch (e: IOException) {
            Log.e(TAG, "Network error: ${e.message}")
        }
    }

}
