package com.example.movieapp.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.movieapp.ui.movies.models.Item

object DiffCheck {
    var MOVIE_DIFF_CALLBACK: DiffUtil.ItemCallback<Item> =
        object :
            DiffUtil.ItemCallback<Item>() {

            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem.getType() == newItem.getType()
            }

            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
              return  oldItem.getType() == newItem.getType()
            }
        }
    var ITEM_DIFF_CALLBACK: DiffUtil.ItemCallback<String> =
        object :
            DiffUtil.ItemCallback<String>() {

            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
               return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }
}