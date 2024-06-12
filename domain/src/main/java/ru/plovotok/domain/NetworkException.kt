package ru.plovotok.domain

sealed class NetworkException(override val message: String) : Exception(message) {
    data class ServerResponseException(val localMessage: String): NetworkException(localMessage)
    data object NoNetworkConnectionException: NetworkException("Please, check your internet connection")
    data object UnexpectedException: NetworkException("Something went wrong")
}