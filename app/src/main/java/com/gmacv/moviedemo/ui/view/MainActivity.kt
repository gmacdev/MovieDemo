package com.gmacv.moviedemo.ui.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.gmacv.moviedemo.R
import com.gmacv.moviedemo.data.model.movies.MovieSingle
import com.gmacv.moviedemo.databinding.ActivityMainBinding
import com.gmacv.moviedemo.ui.adapter.NowPlayingMoviesAdapter
import com.gmacv.moviedemo.ui.inter.OnClickInterface
import com.gmacv.moviedemo.ui.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main), OnClickInterface {

    private val mainViewModel: MainViewModel by viewModels()
    private val binding by viewBinding(ActivityMainBinding::bind)
    private lateinit var nowPlayingMoviesAdapter: NowPlayingMoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupUI()
        setupObserver()
    }

    private fun setupUI() {
        nowPlayingMoviesAdapter = NowPlayingMoviesAdapter(arrayListOf(), this)
        binding.recyclerViewMovies.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewMovies.adapter = nowPlayingMoviesAdapter
    }

    private fun setupObserver() {
        mainViewModel.nowPlayingMovies.observe(this) {
            if (it.isNullOrEmpty())
//                showShimmerOnNowPlayingMovies()
                Log.e("Empty List", "MoviesList")
            else {
//                hideShimmerOnNowPlayingMovies()
                renderNowPlayingMovies(it)
            }
        }
        mainViewModel.networkErrorNowPlayingMovies.observe(this) {
            if (it == true) {
//                try {
//                    binding.shimmer.visibility = View.GONE
//                    binding.shimmer.stopShimmer()
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                }
                showSnackBarWithAction("Network Error")
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun renderNowPlayingMovies(it: List<MovieSingle>) {
        nowPlayingMoviesAdapter.addData(it)
        nowPlayingMoviesAdapter.notifyDataSetChanged()
    }

    override fun onClick(id: Int) {
        startActivity(
            Intent(this, MovieDetailActivity::class.java)
                .putExtra("movie_id", id)
        )
    }

    private fun showSnackBarWithAction(message: String) {
        val snack = Snackbar.make(binding.main, message, Snackbar.LENGTH_LONG)
        snack.setAction("Refresh") {
            mainViewModel.loadAllData()
        }
        snack.show()
    }
}