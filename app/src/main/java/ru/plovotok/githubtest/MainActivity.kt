package ru.plovotok.githubtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.plovotok.githubtest.navigation.SearchNavHost
import ru.plovotok.githubtest.navigation.BottomScreen
import ru.plovotok.githubtest.screens.AboutScreen
import ru.plovotok.githubtest.ui.theme.GithubTestTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            var currentBottomScreen: BottomScreen by remember {
                mutableStateOf(BottomScreen.Searching)
            }
            val scrollState = rememberLazyListState()
            GithubTestTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = Color.White
                ) { innerPadding ->
                    Scaffold(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        bottomBar = {
                            BottomNavigation(
                                backgroundColor = Color.White,
                            ) {
                                BottomNavItems.forEach {
                                    val isSelected = it.screen == currentBottomScreen
                                    BottomNavigationItem(
                                        selected = isSelected,
                                        onClick = {
                                            currentBottomScreen = it.screen
                                        },
                                        icon = {
                                            Icon(
                                                imageVector = it.icon,
                                                contentDescription = null,
                                                tint = if (isSelected) Color.Black else Color.Gray
                                            )
                                        },
                                        label = {
                                            Text(
                                                text = it.label,
                                                color = if (isSelected) Color.Black else Color.Gray
                                            )
                                        },
                                    )
                                }
                            }
                        },
                        containerColor = Color.White
                    ) {
                        Box(
                            modifier = Modifier.padding(bottom = it.calculateBottomPadding())
                        ) {
                            if (currentBottomScreen is BottomScreen.Searching) {
                                SearchNavHost(
                                    scrollState = scrollState,
                                    navController = navController,
                                )
                            } else {
                                AboutScreen()
                            }
                        }
                    }

                }
            }
        }
    }
}

private data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val screen: BottomScreen,
)

private val BottomNavItems = listOf(
    BottomNavItem(
        label = "Search",
        icon = Icons.Default.Search,
        screen = BottomScreen.Searching
    ),
    BottomNavItem(
        label = "About",
        icon = Icons.Default.Info,
        screen = BottomScreen.About
    )
)
