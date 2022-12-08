package id.asad.movie.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import id.asad.movie.databinding.ActivityMainBinding
import id.asad.movie.ui.adapter.LoadingStateAdapter
import id.asad.movie.ui.adapter.MovieListAdapter
import id.asad.movie.utils.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels {
        ViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        showMovies()
    }

    private fun showMovies() {

        val movieAdapter = MovieListAdapter()

        binding.rvMovie.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            adapter = movieAdapter.withLoadStateFooter(
                footer =  LoadingStateAdapter{
                    movieAdapter.retry()
                }
            )
        }

        mainViewModel.movies.observe(this){
            movieAdapter.submitData(lifecycle, it)
        }

        movieAdapter.addLoadStateListener { loadState ->
            if (movieAdapter.itemCount <= 1) {
                binding.rvMovie.isVisible = false
                binding.tvEmptyData.isVisible = true
            }
            else {
                binding.tvEmptyData.isVisible = false
                binding.rvMovie.isVisible = true
            }
        }

    }
}