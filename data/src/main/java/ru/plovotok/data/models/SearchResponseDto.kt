package ru.plovotok.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.plovotok.domain.models.SearchResponse

@Serializable
internal data class SearchResponseDto(
    val items: List<GHRepositoryDto>,
    @SerialName("total_count")
    val totalCount: Int
) {
    fun toModel() = SearchResponse(results = items.map { it.toGHRepository() }, totalResults = totalCount)
}
