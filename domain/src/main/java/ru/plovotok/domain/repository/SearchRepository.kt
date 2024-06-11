package ru.plovotok.domain.repository

import ru.plovotok.domain.models.SearchResponse

interface SearchRepository {

    suspend fun getSearchResults(query: String, page: Int = 1, perPage: Int = 20): Result<SearchResponse>
}