package com.example.it_link_xml.ui

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.it_link_xml.R
import com.example.it_link_xml.databinding.ItemBinding

class ItemViewHolder(private val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(imageUrl: String) {
        val newText = imageUrl.split('/').last()

        with(binding) {
            if (newText != url.text){
                url.text = newText

                Glide.with(image.context)
                    .load(imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.not_image)
                    .fitCenter()
                    .centerCrop()
                    .into(image)

                itemView.setOnClickListener {}
            }
        }
    }
}