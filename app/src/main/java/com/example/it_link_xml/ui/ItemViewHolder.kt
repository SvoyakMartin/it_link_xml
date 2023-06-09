package com.example.it_link_xml.ui

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.it_link_xml.databinding.ItemBinding

class ItemViewHolder(private val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(itemUrl: String) {
        with(binding) {
            url.text = itemUrl

            Glide.with(image.context)
                .load(itemUrl)
                .into(image)

            itemView.setOnClickListener {}
        }
    }
}