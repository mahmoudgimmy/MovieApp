package com.example.movieapp.ui.moviedetails.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.movieapp.*
import com.example.movieapp.ui.moviedetails.adapters.CastAndGenresAdapter
import com.example.movieapp.data.remote.ApiClient
import com.example.movieapp.databinding.FragmentMovieDetailsBinding
import com.example.movieapp.ui.movies.models.Movie
import com.example.movieapp.ui.moviedetails.repos.MovieDetailsRepository
import com.example.movieapp.ui.moviedetails.viewmodel.MovieDetailsViewModel
import com.example.movieapp.ui.moviedetails.viewmodel.MovieDetailsViewState
import com.example.movieapp.utils.viewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a [MoviesActivity]
 * in two-pane mode (on tablets) or a [MovieDetailActivity]
 * on handsets.
 */
class MovieDetailFragment : Fragment() {
    lateinit var movie: Movie
    lateinit var binding: FragmentMovieDetailsBinding
    lateinit var genresAdapter: CastAndGenresAdapter
    lateinit var castAdapter: CastAndGenresAdapter

    private val viewModel: MovieDetailsViewModel by viewModels {
        viewModelFactory {
            MovieDetailsViewModel(MovieDetailsRepository(ApiClient.apiFlicker))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { it ->
            if (it.containsKey(MOVIE)) {
                movie = it.getParcelable(MOVIE)!!
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeObservers()
        loadMovieImages()
        initializeVariables()
        setUpRecyclesView()
        updateScreenUI()
    }

    private fun updateScreenUI() {
        binding.apply {
            tvMovieName.text = movie.title
            tvYear.text = movie.year.toString()
            if (movie.cast.isEmpty())
                tvCast.isVisible = false
            if (movie.genres.isEmpty())
                tvGenres.isVisible = false
            castAdapter.submitList(movie.cast)
            genresAdapter.submitList(movie.genres)
        }
    }

    private fun setUpRecyclesView() {
        binding.apply {
            rvCast.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = castAdapter
            }
            rvGenres.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = genresAdapter
            }
        }
    }

    private fun initializeVariables() {
        castAdapter = CastAndGenresAdapter()
        genresAdapter = CastAndGenresAdapter()
    }

    private fun loadMovieImages() {
        viewModel.getMovieImages(movie.title)
    }

    private fun initializeObservers() {
        lifecycleScope.launch {
            viewModel.moviePhotos.collectLatest {
                render(it)
            }
        }
    }

    private fun render(viewStat: MovieDetailsViewState) {
        when (viewStat) {
            is MovieDetailsViewState.LOADING ->
                binding.pbLoading.isVisible = true

            is MovieDetailsViewState.SUCCESS -> {
                binding.pbLoading.isVisible = false

                val imageList = ArrayList<SlideModel>()
                // make array of SlideModel according to images urls
                viewStat.payload.forEach {
                    imageList.add(SlideModel(it))
                }
                binding.isMovieImages.setImageList(imageList, ScaleTypes.CENTER_INSIDE)
            }
            is MovieDetailsViewState.FAILURE -> {
                binding.pbLoading.isVisible = false
                binding.isMovieImages.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.empty_image_place_holder)
            }
            is MovieDetailsViewState.IDLE -> {
                // no screen ui changes
            }
        }
    }

    companion object {
        const val MOVIE = "movie"
    }
}