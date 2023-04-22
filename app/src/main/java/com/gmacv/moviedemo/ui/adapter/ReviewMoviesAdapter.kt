package com.gmacv.moviedemo.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gmacv.moviedemo.data.model.reviews.ReviewSingle
import com.gmacv.moviedemo.databinding.ItemReviewsBinding

class ReviewMoviesAdapter(
    private val list: ArrayList<ReviewSingle>
) : RecyclerView.Adapter<ReviewMoviesAdapter.DataViewHolder>() {

    class DataViewHolder(private val binding: ItemReviewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(reviewSingle: ReviewSingle) {
            binding.author.text = reviewSingle.author
            binding.reviewContent.text = reviewSingle.content
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        return DataViewHolder(
            ItemReviewsBinding.inflate(
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

    fun addData(newList: List<ReviewSingle>) {
        list.clear()
        list.addAll(newList)
    }
}