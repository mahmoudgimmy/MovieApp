package com.example.movieapp.ui.movies.models

import android.os.Parcelable
import com.example.movieapp.ui.movies.adapters.MoviesAdapter.MoviesAdapterViewTypes
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val title: String = "",
    val year: Int = 0,
    val cast: List<String> = emptyList(),
    val genres: List<String> = emptyList(),
    val rating: Int = 0
) : Item(), Parcelable {
    override fun getType(): MoviesAdapterViewTypes = MoviesAdapterViewTypes.Movie
}
