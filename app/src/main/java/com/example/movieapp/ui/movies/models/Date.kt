package com.example.movieapp.ui.movies.models

import com.example.movieapp.ui.movies.adapters.MoviesAdapter.MoviesAdapterViewTypes

data class Date(val year: String) : Item() {
    override fun getType(): MoviesAdapterViewTypes = MoviesAdapterViewTypes.DATE
}