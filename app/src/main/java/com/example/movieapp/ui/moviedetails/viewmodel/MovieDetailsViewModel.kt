package com.example.movieapp.ui.moviedetails.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.ui.moviedetails.repos.MovieDetailsRepository
import com.example.movieapp.utils.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MovieDetailsViewModel(private val repository: MovieDetailsRepository) : ViewModel() {
    private val _moviePhotos = MutableStateFlow<MovieDetailsViewState>(MovieDetailsViewState.LOADING)
    val moviePhotos: StateFlow<MovieDetailsViewState> = _moviePhotos

    fun getMovieImages(title: String) = viewModelScope.launch {
        repository.searchForMovieImages(title).collectLatest { status ->

            when (status) {
                is Result.Success -> {
                    val images = ArrayList<String>()
                    // return a list of images urls
                    status.result.forEach {
                        images.add(it.getPhotoUrl())
                    }
                    _moviePhotos.value = MovieDetailsViewState.SUCCESS(images)

                }
                is Result.Failure -> {
                    _moviePhotos.value = MovieDetailsViewState.FAILURE(status.msg ?: "")
                }
            }

        }

    }
}