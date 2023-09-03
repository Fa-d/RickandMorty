package com.experiment.rickandmorty.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.experiment.rickandmorty.api.ApiService
import com.experiment.rickandmorty.data.character.CharactersModel

class CharactersPagingSource(
    private val service: ApiService,
) : PagingSource<Int, CharactersModel>() {
    override fun getRefreshKey(state: PagingState<Int, CharactersModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharactersModel> {
        val page = params.key ?: CHARACTERS_STARTING_PAGE_INDEX
        return try {
            val response = service.getAllCharacter(page)
            val allResults = response.results
            LoadResult.Page(
                data = allResults,
                prevKey = if (page == CHARACTERS_STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (page == response.info.count) null else page + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }
}

private const val CHARACTERS_STARTING_PAGE_INDEX = 1
