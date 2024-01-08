package com.example.newsapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.model.NewsResponse
import com.example.newsapp.repository.NewsRespository
import com.example.newsapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class NewsViewModel @Inject constructor(val newRepository: NewsRespository) : ViewModel() {


    private val _breakingnews = MutableSharedFlow<Resource<NewsResponse>>()
    private val _searchNews= MutableSharedFlow<Resource<NewsResponse>>()
    val breakingnews = _breakingnews.asSharedFlow()
    val searchNews = _searchNews.asSharedFlow()
    var breakingNewsPage = 1

    init {
        getBreakingNews("us")
    }


    fun getBreakingNews(countryCode: String) = viewModelScope.launch {

        viewModelScope.launch {
            _breakingnews.emit(Resource.Loading())
        }


        val response = newRepository.getBreakingNews(countryCode, breakingNewsPage)
        viewModelScope.launch {
            _breakingnews.emit(handleBreakingNewsResponse(response))
        }

    }
    fun getSearchNews(countryCode: String) = viewModelScope.launch {

        viewModelScope.launch {
            _searchNews.emit(Resource.Loading())
        }


        val response = newRepository.searchNews(countryCode, breakingNewsPage)
        viewModelScope.launch {
            _searchNews.emit(handleSearchNewsResponse(response))
        }

    }


    private fun handleBreakingNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {

        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(null)

    }
    private fun handleSearchNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {

        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(null)

    }


}