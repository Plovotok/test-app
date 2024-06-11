package ru.plovotok.domain.models

data class GHUser(
    val login: String,
    val name: String?,
    val avatarUrl: String,
    val bio: String?,
    val followers: Int,
    val following: Int,
    val blog: String?,
    val email: String?,
    val twitterUsername: String?,
)