package id.asad.movie.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.asad.movie.data.network.response.ResultsItem
import id.asad.movie.databinding.ItemMovieBinding
import id.asad.movie.ui.detail.DetailMovieActivity

class MovieListAdapter : PagingDataAdapter<ResultsItem, MovieListAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null){
            holder.bind(movie)
        }
    }

    class MyViewHolder(private val binding : ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root){
            fun bind(data : ResultsItem){
                Glide.with(itemView).load("https://image.tmdb.org/t/p/w400${data.posterPath}").into(binding.ivItemPhoto)
                binding.tvItemName.text = data.title

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailMovieActivity::class.java)
                    intent.putExtra(DetailMovieActivity.KEY_DETAIL, data)
                    itemView.context.startActivity(intent)
                }
            }
        }

    companion object{
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ResultsItem>(){
            override fun areItemsTheSame(oldItem: ResultsItem, newItem: ResultsItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: ResultsItem, newItem: ResultsItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

}