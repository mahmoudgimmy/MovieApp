package com.example.movieapp.utils

import android.content.Context
import java.io.IOException


object Utils {
    /**
     * this function reads string from a file
     * */
    fun getJsonDataFromAsset(context: Context, fileName: String): String {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return ""
        }
        return jsonString
    }
}