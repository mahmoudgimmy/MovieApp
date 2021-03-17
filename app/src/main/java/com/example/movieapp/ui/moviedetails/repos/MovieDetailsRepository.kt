package com.example.movieapp.ui.moviedetails.repos

import com.example.movieapp.data.remote.ApiFlicker
import com.example.movieapp.utils.Result
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class MovieDetailsRepository(private val flicker: ApiFlicker) {
    suspend fun searchForMovieImages(title: String) = flow {
        try {
            // search for images by movie title
            val result = flicker.searchForImages(title)
            // emit result of search
            emit(Result.Success(result.photosResult.photos))
        } catch (exception: Exception) {
            emit(Result.Failure("exception", exception))
        }
    }
}