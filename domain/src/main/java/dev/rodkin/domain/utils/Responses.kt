package dev.rodkin.domain.utils

sealed class Response<T> {
    data class Success<T>(val data: T) : Response<T>()
    data class Error<T>(val exception: String) : Response<T>()
}