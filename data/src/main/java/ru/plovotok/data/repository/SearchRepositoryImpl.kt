package ru.plovotok.data.repository

import io.ktor.client.HttpClient
import io.ktor.http.HttpMethod
import io.ktor.util.StringValues
import ru.plovotok.data.models.SearchResponseDto
import ru.plovotok.data.network.ApiResponse
import ru.plovotok.data.network.safeRequest
import ru.plovotok.domain.models.SearchResponse
import ru.plovotok.domain.repository.SearchRepository
import javax.inject.Inject

internal class SearchRepositoryImpl @Inject constructor(
    private val client: HttpClient
) : SearchRepository {

    override suspend fun getSearchResults(query: String, page: Int, perPage: Int): Result<SearchResponse> {
        val response = client.safeRequest<SearchResponseDto>(
            host = "api.github.com",
            path = "search/repositories",
            method = HttpMethod.Get,
            params = StringValues.build {
                append("q", query)
                append("per_page", perPage.toString())
                append("page", page.toString())
            }
        )
        return when (response) {
            is ApiResponse.Error -> Result.failure(response.exception)
            is ApiResponse.Success -> Result.success(response.data.toModel())
        }
    }
}