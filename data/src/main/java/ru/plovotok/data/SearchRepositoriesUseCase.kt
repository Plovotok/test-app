package ru.plovotok.data

import ru.plovotok.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoriesUseCase @Inject constructor(private val repository: SearchRepository) {
    suspend operator fun invoke(query: String, page: Int = 1, perPage: Int = 20) = repository.getSearchResults(query, page, perPage)
}
