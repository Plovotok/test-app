package ru.plovotok.githubtest.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen {
    @Serializable
    data object Search: Screen
    @Serializable
    data class Profile(val username: String)
}

@Serializable
sealed interface BottomScreen {
    @Serializable
    data object Searching: BottomScreen
    @Serializable
    data object About: BottomScreen
}