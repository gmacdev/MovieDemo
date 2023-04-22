package com.gmacv.moviedemo.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gmacv.moviedemo.R
import com.gmacv.moviedemo.data.model.credits.CreditSingle
import com.gmacv.moviedemo.databinding.ItemCastsBinding
import com.gmacv.moviedemo.util.Reuse

class CastMoviesAdapter(
    private val list: ArrayList<CreditSingle>
) : RecyclerView.Adapter<CastMoviesAdapter.DataViewHolder>() {

    class DataViewHolder(private val binding: ItemCastsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(creditSingle: CreditSingle) {
            binding.title.text = creditSingle.name
            val posterUrl = Reuse.getImagePrefixUrl() + creditSingle.profile_path
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
            ItemCastsBinding.inflate(
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

    fun addData(newList: List<CreditSingle>) {
        list.clear()
        list.addAll(newList)
    }
}