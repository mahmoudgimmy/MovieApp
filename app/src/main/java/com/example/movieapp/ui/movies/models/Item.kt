package com.example.movieapp.ui.movies.models

import com.example.movieapp.ui.movies.adapters.MoviesAdapter

/**
 * this is an abstract class to contains movie and Year classes
 * */
abstract class Item {
    abstract fun getType(): MoviesAdapter.MoviesAdapterViewTypes // abstract method to indicates which type is item (year / movie)
}