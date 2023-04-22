package com.gmacv.moviedemo.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmacv.moviedemo.data.model.credits.CreditSingle
import com.gmacv.moviedemo.data.model.movies.MovieSingle
import com.gmacv.moviedemo.data.model.reviews.ReviewSingle
import com.gmacv.moviedemo.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _nowPlayingMovies = MutableLiveData<List<MovieSingle>>()
    val nowPlayingMovies: LiveData<List<MovieSingle>>
        get() = _nowPlayingMovies

    private val _networkErrorNowPlayingMovies = MutableLiveData<Boolean>()
    val networkErrorNowPlayingMovies: LiveData<Boolean>
        get() = _networkErrorNowPlayingMovies

    private val _detailsMovies = MutableLiveData<List<MovieSingle>>()
    val detailsMovies: LiveData<List<MovieSingle>>
        get() = _detailsMovies

    private val _networkErrorDetailsMovies = MutableLiveData<Boolean>()
    val networkErrorDetailsMovies: LiveData<Boolean>
        get() = _networkErrorDetailsMovies

    private val _reviewsMovies = MutableLiveData<List<ReviewSingle>>()
    val reviewsMovies: LiveData<List<ReviewSingle>>
        get() = _reviewsMovies

    private val _networkErrorReviewMovies = MutableLiveData<Boolean>()
    val networkErrorReviewMovies: LiveData<Boolean>
        get() = _networkErrorReviewMovies

    private val _creditsMovies = MutableLiveData<List<CreditSingle>>()
    val creditsMovies: LiveData<List<CreditSingle>>
        get() = _creditsMovies

    private val _networkErrorCreditMovies = MutableLiveData<Boolean>()
    val networkErrorCreditMovies: LiveData<Boolean>
        get() = _networkErrorCreditMovies

    private val _similarMovies = MutableLiveData<List<MovieSingle>>()
    val similarMovies: LiveData<List<MovieSingle>>
        get() = _similarMovies

    private val _networkErrorSimilarMovies = MutableLiveData<Boolean>()
    val networkErrorSimilarMovies: LiveData<Boolean>
        get() = _networkErrorSimilarMovies

    init {
        loadAllData()
    }

    fun loadAllData() {
        fetchNowPlayingMovies()
        fetchMovieDetails()
        fetchMovieReviews()
        fetchMovieCredits()
        fetchSimilarMovies()
    }

    private fun fetchNowPlayingMovies() {
        viewModelScope.launch {
            _nowPlayingMovies.postValue(arrayListOf()) //loading
            mainRepository.getNowPlayingMovies().let {
                if (it.isSuccessful) {
                    _nowPlayingMovies.postValue(it.body()?.results ?: arrayListOf())
                } else {
                    _networkErrorNowPlayingMovies.postValue(true)
                    Log.e("Error API Movies List", it.errorBody().toString())
                }
            }
        }
    }

    private fun fetchMovieDetails() {

    }

    private fun fetchMovieReviews() {

    }

    private fun fetchMovieCredits() {

    }

    private fun fetchSimilarMovies() {

    }
}