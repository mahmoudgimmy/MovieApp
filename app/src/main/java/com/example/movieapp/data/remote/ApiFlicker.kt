package com.example.movieapp.data.remote

import com.example.movieapp.ui.moviedetails.models.PhotosSearchResponse
import com.example.movieapp.utils.Constants.FLICKR_API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiFlicker {
    @GET("?method=flickr.photos.search&format=json&nojsoncallback=1&page=1&per_page=10&sort=relevance&api_key=$FLICKR_API_KEY")
    suspend fun searchForImages(@Query("text") query: String): PhotosSearchResponse
}