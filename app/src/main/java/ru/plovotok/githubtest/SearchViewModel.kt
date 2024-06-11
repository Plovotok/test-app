package ru.plovotok.githubtest

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.plovotok.data.SearchRepositoriesUseCase
import ru.plovotok.domain.repository.SearchRepository
import ru.plovotok.githubtest.util.BaseViewModel
import ru.plovotok.githubtest.util.GHRepositoriesSource
import ru.plovotok.githubtest.util.UiEffect
import ru.plovotok.githubtest.util.UiEvent
import ru.plovotok.githubtest.util.UiState
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepositoryUseCase: SearchRepositoriesUseCase
): BaseViewModel<SearchUiState, SearchEvent, SearchEffect>(SearchUiState.Initial) {

    private val _textSearch = mutableStateOf("")
    val textSearch: State<String> = _textSearch

    private val _search = MutableStateFlow("")
    val search = _search.asSharedFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = "",
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _pager = search.flatMapLatest {
        Pager(PagingConfig(pageSize = 20)) {
            GHRepositoriesSource(search.value, searchRepositoryUseCase)
        }.flow
    }

    val pager = _pager.cachedIn(viewModelScope).stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = PagingData.empty(),
    )

    override fun handleEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.NavigateToProfile -> sendEffect(SearchEffect.GoToProfile(event.username))
            is SearchEvent.SearchRepositories -> search()
            is SearchEvent.UpdateSearchField -> _textSearch.value = event.newText
        }
    }

    private fun search() {
        viewModelScope.launch {
            _search.emit(textSearch.value)
        }
    }
}

data class SearchUiState(
    val isLoading: Boolean,
    val isError: Boolean
): UiState {
    companion object {
        val Initial = SearchUiState(
            isLoading = false,
            isError = false,
        )
    }
}

sealed interface SearchEvent: UiEvent {
    data object SearchRepositories: SearchEvent
    data class UpdateSearchField(val newText: String): SearchEvent
    data class NavigateToProfile(val username: String): SearchEvent
}

sealed interface SearchEffect: UiEffect {
    data class GoToProfile(val username: String): SearchEffect
}