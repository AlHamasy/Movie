package id.asad.movie.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import id.asad.movie.data.MovieRepository
import id.asad.movie.data.network.response.ResultsItem

class MainViewModel(movieRepository: MovieRepository) : ViewModel() {

    val movies : LiveData<PagingData<ResultsItem>> =
        movieRepository.getMovies().cachedIn(viewModelScope)

}