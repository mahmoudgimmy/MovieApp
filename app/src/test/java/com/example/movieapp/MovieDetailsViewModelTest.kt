package com.example.movieapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.movieapp.ui.moviedetails.repos.MovieDetailsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MovieDetailsViewModelTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()
    @Mock
    private lateinit var repository: MovieDetailsRepository

    @Before
    fun setUp() {
        // do something if required
        // 1- prepare
        // 2- action
        // 3- assertion
    }
}