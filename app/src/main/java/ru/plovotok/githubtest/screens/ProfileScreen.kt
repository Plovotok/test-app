package ru.plovotok.githubtest.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.plovotok.githubtest.ProfileEffect
import ru.plovotok.githubtest.ProfileEvent
import ru.plovotok.githubtest.ProfileViewModel
import ru.plovotok.githubtest.ui.kit.ErrorBox
import ru.plovotok.githubtest.ui.kit.UserInfo
import ru.plovotok.githubtest.ui.theme.Typography

@Composable
fun ProfileScreen(
    username: String,
    viewModel: ProfileViewModel = hiltViewModel(),
    onBack: () -> Unit
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    val lifecycleOwner = LocalLifecycleOwner.current

    val effect by viewModel.effect.collectAsStateWithLifecycle(initialValue = null)

    LaunchedEffect(effect) {
        when (effect) {
            is ProfileEffect.GoBack -> onBack()
            null -> {}
        }
    }

    LaunchedEffect(lifecycleOwner) {
        when (lifecycleOwner.lifecycle.currentState) {
            Lifecycle.State.DESTROYED -> Unit
            Lifecycle.State.INITIALIZED -> Unit
            Lifecycle.State.CREATED -> Unit
            Lifecycle.State.STARTED -> viewModel.sendEvent(ProfileEvent.LoadUserData(username))
            Lifecycle.State.RESUMED -> Unit
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Profile Info", style = Typography.titleMedium)
                },
                navigationIcon = {
                    IconButton(onClick = { viewModel.sendEvent(ProfileEvent.NavigateBack) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                backgroundColor = Color.White,
                elevation = 4.dp
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding()),
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color.Blue)
                }
            }

            if (state.error != null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    ErrorBox(
                        cause = state.error!!,
                        onRetry = {
                            viewModel.sendEvent(ProfileEvent.LoadUserData(username))
                        }
                    )
                }
            }
            state.data?.let {
                UserInfo(user = it)
            }
        }
    }
}