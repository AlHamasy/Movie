package id.asad.movie.data

import android.content.Context
import id.asad.movie.data.network.ApiConfig

object Injection {
    fun provideRepository() : MovieRepository{
        val apiService = ApiConfig.getApiService()
        return MovieRepository(apiService)
    }
}