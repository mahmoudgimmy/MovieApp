package com.example.movieapp.ui.moviedetails.viewmodel

sealed class MovieDetailsViewState {
    class SUCCESS(val payload: List<String> = emptyList()) : MovieDetailsViewState()
    class FAILURE(val msg:String ="") : MovieDetailsViewState()
    object LOADING : MovieDetailsViewState()
    object IDLE : MovieDetailsViewState()

}