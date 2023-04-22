package com.gmacv.moviedemo.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gmacv.moviedemo.R
import com.gmacv.moviedemo.data.model.movies.MovieSingle
import com.gmacv.moviedemo.databinding.ItemSimilarBinding
import com.gmacv.moviedemo.ui.inter.OnClickInterface
import com.gmacv.moviedemo.util.Reuse

class SimilarMoviesAdapter(
    private val list: ArrayList<MovieSingle>,
    private val onClickInterface: OnClickInterface
) : RecyclerView.Adapter<SimilarMoviesAdapter.DataViewHolder>() {

    class DataViewHolder(private val binding: ItemSimilarBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movieSingle: MovieSingle, onClickInterface: OnClickInterface) {
            binding.title.text = movieSingle.title
            val posterUrl = Reuse.getImagePrefixUrl() + movieSingle.poster_path
            Glide.with(binding.photo.context)
                .load(posterUrl)
                .placeholder(R.drawable.placeholder_image)
                .centerCrop()
                .error(R.drawable.placeholder_image)
                .into(binding.photo)
            binding.photo.setOnClickListener {
                onClickInterface.onClick(movieSingle.id)
            }
            binding.title.setOnClickListener {
                onClickInterface.onClick(movieSingle.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        return DataViewHolder(
            ItemSimilarBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(list[position], onClickInterface)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun addData(newList: List<MovieSingle>) {
        list.clear()
        list.addAll(newList)
    }
}