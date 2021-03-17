package com.example.movieapp.ui.movies.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.ui.movies.repos.MoviesRepository
import com.example.movieapp.ui.movies.models.Date
import com.example.movieapp.ui.movies.models.Item
import com.example.movieapp.ui.movies.models.Movie
import kotlinx.coroutines.launch

class MoviesViewModel(private val repository: MoviesRepository) : ViewModel() {
    private val _moviesList = MutableLiveData<MoviesListViewState>()
    val moviesList: LiveData<MoviesListViewState> = _moviesList

    /**
     * allMovies is a map which contains movies grouped by year and sorted by rate
     *
     * allMovies ->{
     *           2009 ->{
     *               movie1 rate 5
     *               movie2 rate 5
     *               movie3 rate 2
     *               .
     *               .
     *           }
     *
     *          2010 ->{
     *               movie4 rate 3
     *               movie5 rate 2
     *               movie6 rate 1
     *               .
     *               .
     *           }
     *           .
     *           .
     *           .
     *           2018 ->{
     *               movie100 rate 3
     *               movie101 rate 1
     *               movie102 rate 1
     *               .
     *               .
     *           }
     * }
     *
     * */
    private var allMovies: Map<Int, List<Movie>> = HashMap()

    fun getAllMovies(context: Context) {
        viewModelScope.launch {

            allMovies = repository.getMovies(context)

            val itemsList = ArrayList<Item>()

            // create list of items to show each year and it's movie in one flat list
            // year1 movie1 movie2 movie3 .. year2 movie10 movie11 movie12..
            allMovies.keys.forEach { year ->
                itemsList.add(Date(year.toString()))
                allMovies[year]?.forEach { movie ->
                    itemsList.add(movie)
                }
            }

            _moviesList.postValue(MoviesListViewState.SUCCESS(itemsList))

        }
    }

    fun searchForMovie(query: String) {
        _moviesList.postValue(MoviesListViewState.LOADING)

        val itemsList = ArrayList<Item>()
        allMovies.keys.forEach { year ->
            // filter all movies list according to query in current year
            val filteredMovies =
                allMovies[year]?.filter { movie -> movie.title.contains(query, true) }

            filteredMovies?.let {
                // if there is movies in the current year add the year to the list
                if (it.isNotEmpty()) {
                    itemsList.add(Date(year.toString()))
                    // if query empty return all movies
                    if (query.isEmpty())
                        it.forEach { movie -> itemsList.add(movie) }
                    else // return first 5 top rated movies
                        it.take(5).forEach { movie -> itemsList.add(movie) }
                }
            }
        }

        _moviesList.postValue(MoviesListViewState.SUCCESS(itemsList))
    }
}