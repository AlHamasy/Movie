package id.asad.movie.data

import androidx.lifecycle.LiveData
import androidx.paging.*
import id.asad.movie.data.network.ApiService
import id.asad.movie.data.network.response.ResultsItem

class MovieRepository(private val apiService: ApiService) {

    fun getMovies() : LiveData<PagingData<ResultsItem>>{
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                MoviePagingSource(apiService)
            }
        ).liveData
    }
}