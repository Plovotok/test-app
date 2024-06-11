package ru.plovotok.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.plovotok.domain.models.GHUser

@Serializable
internal data class GHUserDto(
    val login: String,
    val name: String?,
    @SerialName("avatar_url")
    val avatarUrl: String,
    val bio: String?,
    val followers: Int,
    val following: Int,
    val blog: String?,
    val email: String?,
    @SerialName("twitter_username")
    val twitterUsername: String?,
) {
    fun toGHUser() = GHUser(login, name, avatarUrl, bio, followers, following, blog, email, twitterUsername)
}