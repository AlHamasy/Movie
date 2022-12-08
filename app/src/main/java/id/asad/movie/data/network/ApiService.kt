package id.asad.movie.data.network

import id.asad.movie.data.network.response.ResponseMovie
import id.asad.movie.data.network.response.ResponseReviews
import id.asad.movie.data.network.response.ResponseVideos
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/now_playing")
    suspend fun getMovies(@Query("api_key") apiKey : String,
                  @Query("language") languageCode : String,
                  @Query("page") page : Int) : ResponseMovie

    @GET("movie/{movie_id}/videos")
    fun getVideo(@Path("movie_id") movieId : Int,
                 @Query("api_key") apiKey: String,
                 @Query("language") languageCode: String) : Call<ResponseVideos>

    @GET("movie/{movie_id}/reviews")
    fun getReviews(@Path("movie_id") movieId: Int,
                   @Query("api_key") apiKey: String,
                   @Query("language") languageCode: String) : Call<ResponseReviews>
}