package com.gmacv.moviedemo.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gmacv.moviedemo.R
import com.gmacv.moviedemo.data.model.movies.MovieSingle
import com.gmacv.moviedemo.databinding.ItemMoviesListBinding
import com.gmacv.moviedemo.util.Reuse

class NowPlayingMoviesAdapter(
    private val list: ArrayList<MovieSingle>
) : RecyclerView.Adapter<NowPlayingMoviesAdapter.DataViewHolder>() {

    class DataViewHolder(private val binding: ItemMoviesListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movieSingle: MovieSingle) {
            binding.title.text = movieSingle.title
            binding.releaseDate.text = movieSingle.release_date
            val rating = Reuse.roundTheNumber(movieSingle.vote_average) + " / 10"
            binding.rating.text = rating
            val posterUrl = Reuse.getImagePrefixUrl() + movieSingle.poster_path
            Glide.with(binding.photo.context)
                .load(posterUrl)
                .placeholder(R.drawable.placeholder_image)
                .centerCrop()
                .error(R.drawable.placeholder_image)
                .into(binding.photo)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        return DataViewHolder(
            ItemMoviesListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun addData(newList: List<MovieSingle>) {
        list.clear()
        list.addAll(newList)
    }
}