package ru.plovotok.domain.models

data class SearchResponse(
    val results: List<GHRepository>,
    val totalResults: Int
)
