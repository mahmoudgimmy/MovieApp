package com.example.movieapp.ui.movies.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.*
import com.example.movieapp.ui.movies.adapters.MovieListClicks
import com.example.movieapp.ui.movies.adapters.MoviesAdapter
import com.example.movieapp.data.local.MoviesDataSource
import com.example.movieapp.databinding.ActivityMoviesBinding
import com.example.movieapp.ui.movies.models.Movie
import com.example.movieapp.ui.moviedetails.activities.MovieDetailActivity
import com.example.movieapp.ui.moviedetails.fragments.MovieDetailFragment
import com.example.movieapp.ui.movies.repos.MoviesRepository
import com.example.movieapp.ui.movies.viewmodel.MoviesListViewState
import com.example.movieapp.ui.movies.viewmodel.MoviesViewModel
import com.example.movieapp.utils.viewModelFactory


class MoviesActivity : AppCompatActivity(), MovieListClicks {


    private var twoPane: Boolean =
        false // a boolean to indicate user opens application from tablet or not
    lateinit var binding: ActivityMoviesBinding
    lateinit var movieAdapter: MoviesAdapter
    private val viewModel: MoviesViewModel by viewModels {
        viewModelFactory {
            MoviesViewModel(
                MoviesRepository(MoviesDataSource()) // lazy initialization for MoviesViewModel
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoviesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        binding.toolbar.title = title
        detectTabletMode()
        initializeValues()
        initializeListeners()
        initializeObservers()
        initializeScreenView()
    }

    private fun initializeListeners() {
        binding.floatingSearchView.setOnQueryChangeListener { _, newQuery ->
            viewModel.searchForMovie(
                newQuery.trim()
            )
        }
    }

    private fun initializeScreenView() {
        viewModel.getAllMovies(this)
    }

    private fun initializeValues() {
        movieAdapter = MoviesAdapter(this)
        binding.itemList.rvMovieList.apply {
            layoutManager =
                LinearLayoutManager(this@MoviesActivity, LinearLayoutManager.VERTICAL, false)
            adapter = movieAdapter
        }
    }

    private fun initializeObservers() {
        viewModel.moviesList.observe(this, {
            render(it)
        })

        binding.itemList.rvMovieList.apply {

            // this observer made to observe data changes in adapter to scroll to first position in cycle (in searching case)
            movieAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
                override fun onChanged() {
                    scrollToPosition(0)
                }

                override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                    scrollToPosition(0)
                }

                override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
                    scrollToPosition(0)
                }

                override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                    scrollToPosition(0)
                }

                override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
                    scrollToPosition(0)
                }

                override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
                    scrollToPosition(0)
                }
            })
        }

    }

    private fun render(viewState: MoviesListViewState) {

        when (viewState) {
            is MoviesListViewState.LOADING ->
                binding.pbLoading.isVisible = true
            is MoviesListViewState.SUCCESS -> {
                binding.pbLoading.isVisible = false
                movieAdapter.submitList(viewState.payload)
            }
            is MoviesListViewState.FAILURE -> {
                binding.pbLoading.isVisible = false
            }
        }
    }

    override fun onMovieClicked(movie: Movie) {
        showMovieDetails(movie)
    }

    /**
     * in case of table we inflate movie details fragment
     * in case of mobile we navigate to movie details activity
     *  @param movie: selected movie by user
     */
    private fun showMovieDetails(movie: Movie) {
        if (twoPane) {
            val fragment = MovieDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(MovieDetailFragment.MOVIE, movie)
                }
            }
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.item_detail_container, fragment)
                .commit()
        } else {
            val intent = Intent(this, MovieDetailActivity::class.java).apply {
                putExtra(MovieDetailFragment.MOVIE, movie)
            }
            startActivity(intent)
        }
    }

    private fun detectTabletMode() {
        if (findViewById<NestedScrollView>(R.id.item_detail_container) != null)
            twoPane = true
    }
}