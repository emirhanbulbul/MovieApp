package com.emirhanbb.movieapp.ui.detail

import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import coil.load
import com.emirhanbb.movieapp.R
import com.emirhanbb.movieapp.base.BaseFragment
import com.emirhanbb.movieapp.databinding.FragmentDetailBinding
import com.emirhanbb.movieapp.ext.makeTextViewResizable
import com.emirhanbb.movieapp.util.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {
    private val viewModel: DetailViewModel by viewModels()
    override fun initListeners() {
        getArg()
        backButton()
    }

    private fun getArg() {
        val movieId = arguments?.getInt(Constants.MOVIE_ID) ?: 0
        viewModel.loading.observe(this) {
            when (it) {
                true -> binding.progressBar.visibility = View.VISIBLE
                false -> binding.progressBar.visibility = View.GONE
            }
        }
        viewModel.getMovieDetail(movieId.toString())
        viewModel.movieDetail.observe(requireActivity()) {
            binding.apply {
                binding.title.text = it.original_title
                binding.poster.load(Constants.POSTER_BASE_URL + it.poster_path)
                binding.bgImage.load(Constants.POSTER_BASE_URL + it.backdrop_path)
                binding.releaseDate.text = it.release_date

                val list: MutableList<String> = mutableListOf()
                for (i in it.spoken_languages) {
                    list.add(i.english_name)
                }
                val listViewAdapter =
                    ArrayAdapter(requireActivity(), R.layout.list_view_layout, list)
                binding.popularity.text = getString(R.string.popularity_value,it.popularity)
                binding.spokenLanguages.adapter = listViewAdapter
                binding.infoText.text = it.overview
                makeTextViewResizable(binding.infoText, 4, "...Read More", true)

                binding.ratingBar.rating = (it.vote_average / 2).toFloat()
                binding.averageVoteNumber.text = getString(R.string.detail_floor, it.vote_average)
            }
        }
    }

    private fun backButton() {
        binding.backButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }
}