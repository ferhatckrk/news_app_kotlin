package com.example.newsapp.repository

import com.example.newsapp.api.RestrofitInstance
import com.example.newsapp.db.ArticleDatabase
import com.example.newsapp.model.Article
import javax.inject.Inject

class NewsRespository @Inject constructor ( private val db: ArticleDatabase) {
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RestrofitInstance.api.getBreakingNews(countryCode, pageNumber)


    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        RestrofitInstance.api.searhNews(searchQuery, pageNumber)

    suspend fun  upsert(article : Article) = db.getArticleDao().upsert(article)

    fun getSavedNews() = db.getArticleDao().getAllArticles()

}