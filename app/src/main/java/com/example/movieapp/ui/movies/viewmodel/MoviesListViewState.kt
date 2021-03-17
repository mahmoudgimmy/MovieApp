package com.example.movieapp.ui.movies.viewmodel

import com.example.movieapp.ui.movies.models.Item

/**
 * viewState class for Movies to return payload for screen ui
 * */
sealed class MoviesListViewState {
    class SUCCESS(val payload: List<Item> = emptyList()) : MoviesListViewState()
    class FAILURE(msg: String = "") : MoviesListViewState()
    object LOADING : MoviesListViewState()

}