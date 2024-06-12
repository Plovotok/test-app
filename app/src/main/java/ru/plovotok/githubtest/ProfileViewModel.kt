package ru.plovotok.githubtest

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.plovotok.domain.models.GHUser
import ru.plovotok.domain.repository.UsersRepository
import ru.plovotok.githubtest.util.BaseViewModel
import ru.plovotok.githubtest.util.ScreenState
import ru.plovotok.githubtest.util.UiEffect
import ru.plovotok.githubtest.util.UiEvent
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: UsersRepository
) : BaseViewModel<ScreenState<GHUser>, ProfileEvent, ProfileEffect>(
    ScreenState(
        false,
        null,
        null
    )
) {

    override fun handleEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.LoadUserData -> getUserProfile(event.username)
            ProfileEvent.NavigateBack -> sendEffect(ProfileEffect.GoBack)
        }
    }

    private fun getUserProfile(username: String) {
        viewModelScope.launch {
            setState(
                ScreenState(
                    isLoading = true,
                    error = null,
                    data = null
                )
            )
            val result = repository.getUser(username)
            if (result.isSuccess) {
                setState(
                    ScreenState(
                        isLoading = false,
                        error = null,
                        data = result.getOrThrow()
                    )
                )
            } else {
                setState(
                    ScreenState(
                        isLoading = false,
                        error = result.exceptionOrNull(),
                        data = null
                    )
                )
            }
        }
    }
}

sealed interface ProfileEvent : UiEvent {
    data class LoadUserData(val username: String) : ProfileEvent
    data object NavigateBack: ProfileEvent
}

sealed interface ProfileEffect : UiEffect {
    data object GoBack: ProfileEffect
}