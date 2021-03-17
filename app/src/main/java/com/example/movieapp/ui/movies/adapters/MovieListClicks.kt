package com.example.movieapp.ui.movies.adapters

import com.example.movieapp.ui.movies.models.Movie

interface MovieListClicks {
    fun onMovieClicked(movie: Movie)
}