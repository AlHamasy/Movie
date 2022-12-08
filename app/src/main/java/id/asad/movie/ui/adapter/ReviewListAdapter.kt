package id.asad.movie.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.asad.movie.data.network.response.ResultsReview
import id.asad.movie.databinding.ItemReviewBinding

class ReviewListAdapter : RecyclerView.Adapter<ReviewListAdapter.MyViewHolder>() {

    private val listReview = ArrayList<ResultsReview>()

    fun setData(reviews: List<ResultsReview>){
        listReview.clear()
        listReview.addAll(reviews)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val review = listReview[position]
        holder.bind(review)
    }

    override fun getItemCount(): Int = listReview.size

    class MyViewHolder(private val binding : ItemReviewBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(review : ResultsReview){
            binding.apply {
                tvItemUsername.text = review.authorDetails?.username
                tvItemReview.text = review.content
            }
        }

    }
}