package com.example.movieapp.ui.movies.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.utils.Constants
import com.example.movieapp.utils.DiffCheck
import com.example.movieapp.databinding.ItemHeaderYearBinding
import com.example.movieapp.databinding.ItemMovieBinding
import com.example.movieapp.ui.movies.models.Date
import com.example.movieapp.ui.movies.models.Item
import com.example.movieapp.ui.movies.models.Movie

class MoviesAdapter(private val movieListClicks: MovieListClicks) :
    ListAdapter<Item, RecyclerView.ViewHolder>(DiffCheck.MOVIE_DIFF_CALLBACK) {

    /**
     * inflate view according to item type (year / movie)
     * */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            MoviesAdapterViewTypes.DATE.type -> {
                val binding = ItemHeaderYearBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                DateViewHolder(binding)
            }

            MoviesAdapterViewTypes.Movie.type -> {
                val binding = ItemMovieBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                MovieViewHolder(binding)
            }

            else -> ViewHolder(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MovieViewHolder -> holder.bindTo(getItem(position) as Movie, movieListClicks)
            is DateViewHolder -> holder.bindTo(getItem(position) as Date)
        }
    }

    class MovieViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bindTo(movie: Movie, movieListClicks: MovieListClicks) {
            binding.apply {
                tvMovieTitle.text = movie.title
                llMovie.setOnClickListener {
                    movieListClicks.onMovieClicked(movie)
                }
                rbMovie.rating = movie.rating.toFloat()
            }

        }
    }

    class DateViewHolder(private val binding: ItemHeaderYearBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bindTo(date: Date) {
            binding.tvYear.text = date.year
        }

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun getItemViewType(position: Int): Int = getItem(position).getType().type

    enum class MoviesAdapterViewTypes(val type: Int) { DATE(0), Movie(1) }
}
