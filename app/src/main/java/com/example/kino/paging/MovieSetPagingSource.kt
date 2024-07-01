package com.example.kino.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.kino.data.Movie
import com.example.kino.repo.MovieAPI
import com.example.kino.util.Resource

private const val START_PAGE_INDEX = 1

class MovieSetPagingSource(private val movieAPI: MovieAPI /*TODO DI*/) :
	PagingSource<Int, Movie>() {
	override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
		return state.anchorPosition?.let {
			state.closestPageToPosition(it)?.prevKey?.plus(1)
				?: state.closestPageToPosition(it)?.nextKey?.minus(1)
		}
	}

	override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
		try {
			val pageNumber = params.key ?: START_PAGE_INDEX
			when (val resource = movieAPI.getTopMovies(pageNumber)) {
				is Resource.Success -> {
					resource.data?.items?.let {
						return LoadResult.Page(
							data = resource.data.items,
							prevKey = if (pageNumber > START_PAGE_INDEX) pageNumber - 1 else null,
							nextKey = if (pageNumber < resource.data.totalPages) pageNumber + 1 else null
						)
					}
					return LoadResult.Error(Exception(resource.message))
				}

				else -> return LoadResult.Error(Exception(resource.message))
			}
		} catch (e: Exception) {
			return LoadResult.Error(e)
		}
	}
}
