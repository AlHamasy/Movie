package id.asad.movie.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.asad.movie.BuildConfig
import id.asad.movie.data.network.ApiConfig
import id.asad.movie.data.network.response.ResponseReviews
import id.asad.movie.data.network.response.ResponseVideos
import id.asad.movie.data.network.response.ResultsReview
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailMovieViewModel : ViewModel() {

    val resultTrailerVideo = MutableLiveData<String>()
    val emptyTrailerVideo = MutableLiveData<String>()
    val errorTrailerVideo = MutableLiveData<String>()

    val resultReviews = MutableLiveData<List<ResultsReview?>?>()
    val emptyReviews = MutableLiveData<String>()
    val errorReviews = MutableLiveData<String>()

    fun getVideo(movieId : Int){
        val service = ApiConfig.getApiService().getVideo(movieId, BuildConfig.API_KEY, BuildConfig.LANGUAGE)
        service.enqueue(object : Callback<ResponseVideos> {
            override fun onResponse(call: Call<ResponseVideos>, response: Response<ResponseVideos>) {
                if (response.isSuccessful){
                    val responseVideos = response.body()
                    val videos = responseVideos?.results
                    if (videos.isNullOrEmpty()){
                        emptyTrailerVideo.postValue("Youtube video trailer not found")
                    }
                    else{
                        videos.map { result ->
                            if (result?.name == "Official Trailer"){
                                resultTrailerVideo.postValue(result.key!!)
                            }else{
                                emptyTrailerVideo.postValue("Youtube video trailer not found")
                            }
                        }
                    }
                }
            }
            override fun onFailure(call: Call<ResponseVideos>, t: Throwable) {
                errorTrailerVideo.postValue("Please check your internet connection")
            }
        })
    }

    fun getReviews(movieId : Int){
        val service = ApiConfig.getApiService().getReviews(movieId, BuildConfig.API_KEY, BuildConfig.LANGUAGE)
        service.enqueue(object : Callback<ResponseReviews>{
            override fun onResponse(call: Call<ResponseReviews>, response: Response<ResponseReviews>) {
                if (response.isSuccessful){
                    val responseReview = response.body()
                    val reviews = responseReview?.results
                    if (reviews.isNullOrEmpty()){
                        emptyReviews.postValue("No review in this video")
                    }else{
                        resultReviews.postValue(reviews)
                    }
                }
            }
            override fun onFailure(call: Call<ResponseReviews>, t: Throwable) {
                errorReviews.postValue("Please check your internet connection")
            }
        })
    }

}