package ru.plovotok.domain.models

data class GHRepository(
    val id: Int,
    val name: String,
    val owner: Owner,
    val description: String?,
    val updatedAt: String,
    val stars: Int,
    val language: String?
)

data class Owner(
    val id: Int,
    val username: String,
    val avatarUrl: String
)
