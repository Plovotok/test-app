package ru.plovotok.githubtest.util

data class ScreenState<T>(
    val isLoading: Boolean,
    val error: Throwable?,
    val data: T?
): UiState
