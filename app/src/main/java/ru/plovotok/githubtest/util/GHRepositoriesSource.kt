package ru.plovotok.githubtest.util

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.plovotok.data.SearchRepositoriesUseCase
import ru.plovotok.domain.models.GHRepository

class GHRepositoriesSource (
    private val query: String,
    private val searchRepositoryUseCase: SearchRepositoriesUseCase
): PagingSource<Int, GHRepository>() {
    override fun getRefreshKey(state: PagingState<Int, GHRepository>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GHRepository> {
        val searchingPage = params.key ?: 1
        val perPage = params.loadSize

        if (query.isEmpty()) return LoadResult.Page(
            data = listOf(),
            prevKey = null,
            nextKey = null
        )
        val result = searchRepositoryUseCase(query = query, page = searchingPage, perPage = perPage)

        return if (result.isSuccess) {
            if (result.getOrThrow().results.isEmpty()) {
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            } else {
                LoadResult.Page(
                    data = result.getOrThrow().results,
                    prevKey = null,
                    nextKey = if (searchingPage * perPage < result.getOrThrow().totalResults) searchingPage + 1 else null
                )
            }
        } else {
            LoadResult.Error(result.exceptionOrNull()!!)
        }
    }
}