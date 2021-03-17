package com.example.movieapp.data.local

import android.content.Context
import com.example.movieapp.utils.Utils
import com.example.movieapp.ui.movies.models.Movie
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import java.lang.reflect.Type
/**
 * this class is reasonable for getting movies from local data source (json file)
 * */
class MoviesDataSource {
    fun getMovies(context: Context): List<Movie> = readMoviesFromJsonFile(context)

    /**
     * this function reads movies from a json file
     * in case of error reading those moves it returns emptyList
     * */
    private fun readMoviesFromJsonFile(context: Context): List<Movie> {
        return try {
            val jsonFileString = Utils.getJsonDataFromAsset(context, "movies.json")
            val obj = JSONObject(jsonFileString)
            val moviesArray = obj.getJSONArray("movies").toString()
            val listMoviesType: Type = object : TypeToken<List<Movie>>() {}.type
            Gson().fromJson(moviesArray, listMoviesType)

        } catch (e: Exception) {
            emptyList()
        }
    }
}