package com.example.movieapp.utils

// a class to wrapping api results
sealed class Result<out R> {
    // rapping success calls
    data class Success<R> constructor(
        val result: R
    ) : Result<R>()

    // rapping failures
    data class Failure(
        var msg: String? = null,
        val exp: Throwable
    ) : Result<Nothing>()

}