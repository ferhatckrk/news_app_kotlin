package com.example.newsapp.util

sealed class Resource <T> (
    val data: T? =null,
    val message: T? = null,
) {
    class Success<T>(data: T?): Resource<T>(data)
    class Error<T>(message: T?, data: T? =null): Resource<T>(message, data)
    class Loading<T>: Resource<T>()
}