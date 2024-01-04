package com.example.newsapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.repository.NewsRespository


class NewsViewModelProviderFactory(
    val newsRespository: NewsRespository
) : ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(newsRespository) as T
    }


}