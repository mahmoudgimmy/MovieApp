package com.example.movieapp.ui.moviedetails.models

import com.google.gson.annotations.SerializedName

data class PhotoItem(
    val id: String? = "",
    val secret: String? = "",
    val server: String? = "",
    val farm: Int? = 0
) {
    fun getPhotoUrl(): String =
        "https://farm${farm}.static.flickr.com/${server}/${id}_${secret}.png"
}

data class PhotosResult(
    @SerializedName("photo") val photos: ArrayList<PhotoItem>
)

data class PhotosSearchResponse(
    @SerializedName("photos") val photosResult: PhotosResult
)