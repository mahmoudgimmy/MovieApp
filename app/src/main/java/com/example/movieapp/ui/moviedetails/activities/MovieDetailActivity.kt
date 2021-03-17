package com.example.movieapp.ui.moviedetails.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.movieapp.ui.moviedetails.fragments.MovieDetailFragment
import com.example.movieapp.R

class MovieDetailActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_movie_detail)

      if (savedInstanceState == null) {
          // Create the detail fragment and add it to the activity
          // using a fragment transaction.
              // pass movie object to detail fragment
          val fragment = MovieDetailFragment().apply {
              arguments = Bundle().apply {
                  putParcelable(
                      MovieDetailFragment.MOVIE,
                      intent.getParcelableExtra(MovieDetailFragment.MOVIE)
                  )
              }
          }

          supportFragmentManager.beginTransaction()
                  .add(R.id.item_detail_container, fragment)
                  .commit()
      }
  }

}