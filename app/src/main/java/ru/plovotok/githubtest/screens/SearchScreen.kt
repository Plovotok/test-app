package ru.plovotok.githubtest.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.launch
import ru.plovotok.githubtest.SearchEffect
import ru.plovotok.githubtest.SearchEvent
import ru.plovotok.githubtest.SearchViewModel
import ru.plovotok.githubtest.ui.kit.ErrorBox
import ru.plovotok.githubtest.ui.kit.RepositoryItem
import ru.plovotok.githubtest.ui.kit.ScrollToTopButton
import ru.plovotok.githubtest.ui.kit.SearchField
import ru.plovotok.githubtest.ui.theme.Typography

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchScreen(
    scrollState: LazyListState,
    goToProfile: (String) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val effect by viewModel.effect.collectAsState(initial = null)
    val repositories = viewModel.pager.collectAsLazyPagingItems()
    val focusManager = LocalFocusManager.current
    LaunchedEffect(effect) {
        when (val currentEffect = effect) {
            is SearchEffect.GoToProfile -> goToProfile(currentEffect.username)
            null -> {}
        }
    }
    var isRefreshing by remember {
        mutableStateOf(false)
    }

    var isInitialState by remember {
        mutableStateOf(true)
    }
    val refreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = {
            if (viewModel.search.value.isNotEmpty()) {
                isRefreshing = true
                isInitialState = false
                repositories.refresh()
            }
        },
        refreshThreshold = 48.dp
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        SearchField(
            value = viewModel.textSearch.value,
            onValueChange = { viewModel.sendEvent(SearchEvent.UpdateSearchField(it)) },
            onSearch = {
                focusManager.clearFocus()
                isInitialState = false
                viewModel.sendEvent(SearchEvent.SearchRepositories)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 24.dp)
        )

        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxSize()
                    .pullRefresh(refreshState),
                state = scrollState
            ) {
                if (repositories.loadState.refresh == LoadState.Loading && !isRefreshing) {
                    item {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = Color.Blue)
                        }
                    }
                }

                if (repositories.loadState.refresh is LoadState.NotLoading) {
                    if (repositories.itemCount == 0 && !isInitialState) {
                        item {
                            Text(
                                text = "No results",
                                color = Color.Gray,
                                style = Typography.titleLarge,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp)
                            )
                        }
                    }
                    items(repositories.itemCount) {
                        repositories[it]?.let {
                            RepositoryItem(
                                repository = it,
                                onRepositoryClick = {
                                    viewModel.sendEvent(SearchEvent.NavigateToProfile(it.owner.username))
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp)
                            )
                        }
                    }
                }

                if (repositories.loadState.append is LoadState.Error) {
                    item {
                        ErrorBox(
                            cause = (repositories.loadState.append as LoadState.Error).error,
                            onRetry = repositories::retry
                        )
                    }
                } else if (repositories.loadState.refresh is LoadState.Error) {
                    item {
                        ErrorBox(
                            cause = (repositories.loadState.refresh as LoadState.Error).error,
                            onRetry = repositories::retry
                        )
                    }
                }

                if (repositories.loadState.append == LoadState.Loading) {
                    item {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(Alignment.CenterHorizontally),
                            color = Color.Blue
                        )
                    }
                }
            }
            PullRefreshIndicator(
                refreshing = isRefreshing,
                state = refreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )

            val scope = rememberCoroutineScope()

            ScrollToTopButton(
                scrollState = scrollState,
                onClick = {
                    scope.launch { scrollState.scrollToItem(0) }
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 24.dp, bottom = 24.dp)
            )
        }
    }

    LaunchedEffect(repositories.loadState.refresh) {
        if (repositories.loadState.source.refresh !is LoadState.Loading) {
            isRefreshing = false
        }
    }
}