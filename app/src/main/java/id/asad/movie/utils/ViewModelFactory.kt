package id.asad.movie.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.asad.movie.data.Injection
import id.asad.movie.ui.detail.DetailMovieViewModel
import id.asad.movie.ui.main.MainViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(Injection.provideRepository()) as T
        }
        else if (modelClass.isAssignableFrom(DetailMovieViewModel::class.java)){
            return DetailMovieViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}