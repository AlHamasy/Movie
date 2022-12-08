package id.asad.movie.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import id.asad.movie.BuildConfig
import id.asad.movie.data.network.ApiService
import id.asad.movie.data.network.response.ResultsItem

class MoviePagingSource(private val apiService: ApiService) : PagingSource<Int, ResultsItem>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultsItem> {
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val responseMovie = apiService.getMovies(BuildConfig.API_KEY, BuildConfig.LANGUAGE, page).results
            LoadResult.Page(
                data = responseMovie,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (responseMovie.isNullOrEmpty()) null else page + 1
            )

        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ResultsItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}