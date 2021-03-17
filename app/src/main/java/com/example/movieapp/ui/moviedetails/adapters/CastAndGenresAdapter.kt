package com.example.movieapp.ui.moviedetails.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.utils.DiffCheck
import com.example.movieapp.databinding.ItemCastGenresBinding

class CastAndGenresAdapter :
    ListAdapter<String, CastAndGenresAdapter.ItemViewHolder>(DiffCheck.ITEM_DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        val binding = ItemCastGenresBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }


    class ItemViewHolder(private val binding: ItemCastGenresBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bindTo(item: String) {
            binding.tvItem.text = item
        }

    }

}
