package com.gmacv.moviedemo.ui.view

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.gmacv.moviedemo.R
import com.gmacv.moviedemo.data.model.credits.CreditSingle
import com.gmacv.moviedemo.data.model.movies.MovieSingle
import com.gmacv.moviedemo.data.model.reviews.ReviewSingle
import com.gmacv.moviedemo.databinding.ActivityMovieDetailsBinding
import com.gmacv.moviedemo.ui.adapter.CastMoviesAdapter
import com.gmacv.moviedemo.ui.adapter.ReviewMoviesAdapter
import com.gmacv.moviedemo.ui.adapter.SimilarMoviesAdapter
import com.gmacv.moviedemo.ui.inter.OnClickInterface
import com.gmacv.moviedemo.ui.viewmodel.DetailsViewModel
import com.gmacv.moviedemo.util.Reuse
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity(R.layout.activity_movie_details), OnClickInterface {

    private val detailViewModel: DetailsViewModel by viewModels()
    private val binding by viewBinding(ActivityMovieDetailsBinding::bind)
    private var movieId by Delegates.notNull<Int>()
    private lateinit var reviewMoviesAdapter: ReviewMoviesAdapter
    private lateinit var castMoviesAdapter: CastMoviesAdapter
    private lateinit var similarMoviesAdapter: SimilarMoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieId = intent.getIntExtra("movie_id", 0)
        setupStatusBar()
        setupUI()
        setupObserver()
        detailViewModel.loadAllData(movieId)
    }

    private fun setupUI() {
        reviewMoviesAdapter = ReviewMoviesAdapter(arrayListOf())
        binding.recyclerViewReviews.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewReviews.adapter = reviewMoviesAdapter

        castMoviesAdapter = CastMoviesAdapter(arrayListOf())
        binding.recyclerViewCast.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewCast.adapter = castMoviesAdapter

        similarMoviesAdapter = SimilarMoviesAdapter(arrayListOf(), this)
        binding.recyclerViewSimilar.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewSimilar.adapter = similarMoviesAdapter
    }

    private fun setupObserver() {
        detailViewModel.detailMovies.observe(this) {
            if (it.id != 0) {
                binding.title.text = it.title
                binding.releaseDate.text = it.release_date
                val rating = Reuse.roundTheNumber(it.vote_average) + " / 10"
                binding.rating.text = rating
                val posterUrl = Reuse.getImagePrefixUrl() + it.poster_path
                Glide.with(this)
                    .load(posterUrl)
                    .placeholder(R.drawable.placeholder_image)
                    .centerCrop()
                    .error(R.drawable.placeholder_image)
                    .into(binding.poster)
                val runtime = "" + it.runtime + " minutes"
                binding.runtime.text = runtime
                binding.overview.text = it.overview
            }
        }

        detailViewModel.reviewsMovies.observe(this) {
            if (!it.isNullOrEmpty())
                renderReviewMovies(it)
        }

        detailViewModel.creditsMovies.observe(this) {
            if (!it.isNullOrEmpty())
                renderCreditMovies(it)
        }

        detailViewModel.similarMovies.observe(this) {
            if (!it.isNullOrEmpty())
                renderSimilarMovies(it)
        }

        detailViewModel.networkError.observe(this) {
            if (it == true) {
                showSnackBarWithAction("Network Error")
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun renderSimilarMovies(it: List<MovieSingle>) {
        similarMoviesAdapter.addData(it)
        similarMoviesAdapter.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun renderCreditMovies(it: List<CreditSingle>) {
        castMoviesAdapter.addData(it)
        castMoviesAdapter.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun renderReviewMovies(it: List<ReviewSingle>) {
        reviewMoviesAdapter.addData(it)
        reviewMoviesAdapter.notifyDataSetChanged()
    }

    private fun showSnackBarWithAction(message: String) {
        val snack = Snackbar.make(binding.main, message, Snackbar.LENGTH_LONG)
        snack.setAction("Refresh") {
            detailViewModel.loadAllData(movieId)
        }
        snack.show()
    }

    private fun setupStatusBar() {
        this.window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            statusBarColor = Color.TRANSPARENT
        }
    }

    override fun onClick(id: Int) {
        movieId = id
        detailViewModel.loadAllData(movieId)
        binding.main.isFocusableInTouchMode = true
        binding.main.fullScroll(View.FOCUS_UP)
        binding.main.smoothScrollTo(0, 0)
    }
}