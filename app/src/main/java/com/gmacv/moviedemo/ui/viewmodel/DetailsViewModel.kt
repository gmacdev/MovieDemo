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
class DetailsViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _detailMovies = MutableLiveData<MovieSingle>()
    val detailMovies: LiveData<MovieSingle>
        get() = _detailMovies

    private val _reviewsMovies = MutableLiveData<List<ReviewSingle>>()
    val reviewsMovies: LiveData<List<ReviewSingle>>
        get() = _reviewsMovies

    private val _creditsMovies = MutableLiveData<List<CreditSingle>>()
    val creditsMovies: LiveData<List<CreditSingle>>
        get() = _creditsMovies

    private val _similarMovies = MutableLiveData<List<MovieSingle>>()
    val similarMovies: LiveData<List<MovieSingle>>
        get() = _similarMovies

    private val _networkError = MutableLiveData<Boolean>()
    val networkError: LiveData<Boolean>
        get() = _networkError

    fun loadAllData(id: Int) {
        fetchMovieDetails(id)
        fetchMovieReviews(id)
        fetchMovieCredits(id)
        fetchSimilarMovies(id)
    }

    private fun fetchMovieDetails(id: Int) {
        viewModelScope.launch {
            _detailMovies.postValue(MovieSingle(0, "", "", "")) //loading
            mainRepository.getMovieDetails(id).let {
                if (it.isSuccessful) {
                    _detailMovies.postValue(it.body())
                } else {
                    _networkError.postValue(true)
                    Log.e("Error API Movies Details", it.errorBody().toString())
                }
            }
        }
    }

    private fun fetchMovieReviews(id: Int) {
        viewModelScope.launch {
            _reviewsMovies.postValue(arrayListOf()) //loading
            mainRepository.getReviews(id).let {
                if (it.isSuccessful) {
                    _reviewsMovies.postValue(it.body()?.results ?: arrayListOf())
                } else {
                    _networkError.postValue(true)
                    Log.e("Error API Movies Reviews", it.errorBody().toString())
                }
            }
        }
    }

    private fun fetchMovieCredits(id: Int) {
        viewModelScope.launch {
            _creditsMovies.postValue(arrayListOf()) //loading
            mainRepository.getCredits(id).let {
                if (it.isSuccessful) {
                    _creditsMovies.postValue(it.body()?.cast ?: arrayListOf())
                } else {
                    _networkError.postValue(true)
                    Log.e("Error API Movies Credits", it.errorBody().toString())
                }
            }
        }
    }

    private fun fetchSimilarMovies(id: Int) {
        viewModelScope.launch {
            _similarMovies.postValue(arrayListOf()) //loading
            mainRepository.getNowPlayingMovies().let {
                if (it.isSuccessful) {
                    _similarMovies.postValue(it.body()?.results ?: arrayListOf())
                } else {
                    _networkError.postValue(true)
                    Log.e("Error API Movies Similar", it.errorBody().toString())
                }
            }
        }
    }
}