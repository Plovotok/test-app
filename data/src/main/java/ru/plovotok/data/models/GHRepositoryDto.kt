package ru.plovotok.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.plovotok.domain.models.GHRepository
import ru.plovotok.domain.models.Owner

@Serializable
internal data class GHRepositoryDto(
    val id: Int,
    val name: String,
    val owner: OwnerDto,
    val description: String?,
    @SerialName("updated_at")
    val updatedAt: String,
    @SerialName("stargazers_count")
    val stars: Int,
    val language: String?
) {
    fun toGHRepository() = GHRepository(
        id,
        name,
        owner.toOwner(),
        description,
        updatedAt,
        stars,
        language
    )
}

@Serializable
internal data class OwnerDto(
    val id: Int,
    val login: String,
    @SerialName("avatar_url")
    val avatarUrl: String
) {
    fun toOwner() = Owner(id, login, avatarUrl)
}