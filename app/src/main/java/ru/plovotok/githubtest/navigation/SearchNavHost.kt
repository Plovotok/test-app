package ru.plovotok.githubtest.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import ru.plovotok.githubtest.screens.ProfileScreen
import ru.plovotok.githubtest.screens.SearchScreen

@Composable
fun SearchNavHost(navController: NavHostController, scrollState: LazyListState) {
    NavHost(
        navController = navController,
        startDestination = Screen.Search,
        enterTransition = {
            slideInHorizontally(initialOffsetX = { it })
        },
        exitTransition = {
            slideOutHorizontally(targetOffsetX = { -it })
        },
        popEnterTransition = {
            slideInHorizontally(initialOffsetX = { -it })
        },
        popExitTransition = {
            slideOutHorizontally(targetOffsetX = { it })
        },
    ) {
        composable<Screen.Search> {
            SearchScreen(scrollState = scrollState, goToProfile = { username -> navController.navigate(Screen.Profile(username)) })
        }

        composable<Screen.Profile> {
            val username = it.toRoute<Screen.Profile>().username
            ProfileScreen(username = username, onBack = navController::popBackStack)
        }
    }
}