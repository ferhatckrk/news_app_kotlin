package com.example.newsapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.model.Article
import com.example.newsapp.model.NewsResponse
import com.example.newsapp.repository.NewsRespository
import com.example.newsapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(val newRepository: NewsRespository) : ViewModel() {


    val breakingnews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var breakingNewsPage = 1

    init {
     // getBreakingNews("tr")
    }


    fun getBreakingNews(countryCode: String) = viewModelScope.launch {
        breakingnews.postValue(Resource.Loading())

        val response = newRepository.getBreakingNews(countryCode, breakingNewsPage)
        breakingnews.postValue(handleBreakingNewsResponse(response))
    }


    private fun handleBreakingNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {

        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->

                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(null)

    }


}












