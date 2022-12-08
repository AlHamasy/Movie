package id.asad.movie.ui.detail

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import id.asad.movie.data.network.response.ResultsItem
import id.asad.movie.data.network.response.ResultsReview
import id.asad.movie.databinding.ActivityDetailMovieBinding
import id.asad.movie.ui.adapter.ReviewListAdapter
import id.asad.movie.utils.ViewModelFactory

class DetailMovieActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailMovieBinding
    private val detailMovieViewModel: DetailMovieViewModel by viewModels {
        ViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val data = intent.getParcelableExtra<ResultsItem>(KEY_DETAIL)

        showData(data)
        getTrailerVideo(data?.id)
        getReviews(data?.id)
        back()
    }

    private fun getReviews(id : Int?) {
        detailMovieViewModel.getReviews(id!!)
        detailMovieViewModel.let {
            it.resultReviews.observe(this){ reviews ->
                showReviews(reviews)
            }
            it.emptyReviews.observe(this){ message ->
                showEmptyReview(message)
            }
        }
    }

    private fun showEmptyReview(message: String){
        binding.apply {
            tvEmptyReview.visibility = View.VISIBLE
            rvReview.visibility = View.GONE

            tvEmptyReview.text = message
        }
    }

    private fun showReviews(reviews: List<ResultsReview?>?) {

        val reviewListAdapter = ReviewListAdapter()
        reviewListAdapter.setData(reviews as List<ResultsReview>)

        binding.apply {
            tvEmptyReview.visibility = View.GONE
            rvReview.visibility = View.VISIBLE

            rvReview.apply {
                layoutManager = LinearLayoutManager(this@DetailMovieActivity)
                adapter = reviewListAdapter
            }
        }
    }

    private fun getTrailerVideo(id: Int?) {
        detailMovieViewModel.getVideo(id!!)
        detailMovieViewModel.let {
            it.resultTrailerVideo.observe(this){ key ->
                binding.ivBackdrop.setOnClickListener {
                    intentToYoutube(key)
                }
            }
            it.emptyTrailerVideo.observe(this){ message ->
                binding.ivBackdrop.setOnClickListener {
                    showToast(message)
                }
            }
        }
    }

    private fun intentToYoutube(key : String){
        val youtubeLink = "https://www.youtube.com/watch?v=$key"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
        startActivity(intent)
    }

    private fun showToast(message : String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun back(){
        binding.fabBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun showData(data : ResultsItem?) {
        binding.apply {
            tvOverview.text = data?.overview
            tvTitle.text = data?.title
            Glide.with(this@DetailMovieActivity)
                .load("https://image.tmdb.org/t/p/w400${data?.posterPath}")
                .into(ivPhoto)
            Glide.with(this@DetailMovieActivity)
                .load("https://image.tmdb.org/t/p/w400${data?.backdropPath}")
                .into(ivBackdrop)
        }
    }

    companion object{
        const val KEY_DETAIL = "detail"
    }
}