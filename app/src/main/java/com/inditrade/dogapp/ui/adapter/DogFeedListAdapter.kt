package com.inditrade.dogapp.ui.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.CachePolicy
import coil.transform.RoundedCornersTransformation
import com.inditrade.dogapp.R
import com.inditrade.dogapp.databinding.LayoutFeedItemBinding
import com.inditrade.dogapp.ui.feed.DogItemClickListener

class DogFeedListAdapter(
    var imagesList: MutableList<String>,
    private val listener: DogItemClickListener
) :
    RecyclerView.Adapter<DogFeedListAdapter.DogFeedViewHolder>() {
    class DogFeedViewHolder(var binding: LayoutFeedItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dogImage: String, listener: DogItemClickListener) {
            binding.image.load(dogImage) {
                crossfade(true)
                transformations(RoundedCornersTransformation(10f))
            }
            binding.image.setOnClickListener {
                listener.onClick(dogImage)
                return@setOnClickListener
            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DogFeedViewHolder {
        return DogFeedViewHolder(
            LayoutFeedItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DogFeedViewHolder, position: Int) {
        val dogImage = imagesList[position]
        if (!Uri.parse(dogImage).isAbsolute) {
            return
        }
        holder.bind(dogImage, listener)
    }

    override fun getItemCount(): Int {
        return imagesList.size
    }
}