package ru.plovotok.data.network

import java.lang.Exception

sealed interface ApiResponse<out T>  {

    data class Success<T>(val data: T): ApiResponse<T>

    data class Error(val exception: Exception): ApiResponse<Nothing>
}