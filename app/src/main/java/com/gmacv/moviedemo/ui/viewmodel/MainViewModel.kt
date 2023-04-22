package com.gmacv.moviedemo.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmacv.moviedemo.data.model.movies.MovieSingle
import com.gmacv.moviedemo.data.repository.MainRepository
import com.gmacv.moviedemo.util.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val _nowPlayingMovies = MutableLiveData<List<MovieSingle>>()
    val nowPlayingMovies: LiveData<List<MovieSingle>>
        get() = _nowPlayingMovies

    private val _networkErrorNowPlayingMovies = MutableLiveData<Boolean>()
    val networkErrorNowPlayingMovies: LiveData<Boolean>
        get() = _networkErrorNowPlayingMovies

    init {
        loadAllData()
    }

    fun loadAllData() {
        fetchNowPlayingMovies()
    }

    private fun fetchNowPlayingMovies() {
        viewModelScope.launch {
            _nowPlayingMovies.postValue(arrayListOf()) //loading
            delay(1000) // so we can enjoy shimmer loading XD
            if (networkHelper.isNetworkConnected()) {
                mainRepository.getNowPlayingMovies().let {
                    if (it.isSuccessful) {
                        _nowPlayingMovies.postValue(it.body()?.results ?: arrayListOf())
                    } else {
                        _networkErrorNowPlayingMovies.postValue(true)
                        Log.e("Error API Movies List", it.errorBody().toString())
                    }
                }
            } else _networkErrorNowPlayingMovies.postValue(true)
        }
    }
}