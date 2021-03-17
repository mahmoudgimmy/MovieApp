package com.example.movieapp.ui.movies.repos

import android.content.Context
import com.example.movieapp.data.local.MoviesDataSource
import com.example.movieapp.ui.movies.models.Movie

class MoviesRepository(private val moviesDataSource: MoviesDataSource) {
    /**
     * this function reads movies from a json local file
     * */
    fun getMovies(context: Context): Map<Int, List<Movie>> {
        val movies = moviesDataSource.getMovies(context)
        return movies.sortedByDescending { it.rating } // to show top rated movies first
            .groupBy { it.year } // then category those moves by year

    }
}