package com.emirhanbb.helloandroid.ui.home

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.View.OnFocusChangeListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.emirhanbb.helloandroid.R
import com.emirhanbb.helloandroid.adapter.HomeMovieAdapter
import com.emirhanbb.helloandroid.adapter.SearchResultAdapter
import com.emirhanbb.helloandroid.base.BaseFragment
import com.emirhanbb.helloandroid.databinding.FragmentHomeBinding
import com.emirhanbb.helloandroid.ext.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var homeMovieAdapter: HomeMovieAdapter
    private lateinit var searchResultAdapter: SearchResultAdapter
    private lateinit var popularAdapter: HomeMovieAdapter
    private lateinit var upcomingAdapter: HomeMovieAdapter
    override fun initListeners() {
        searchBar()
        setUpRv()
        hideKeyboard()
    }

    private fun setUpRv() {
        homeMovieAdapter = HomeMovieAdapter()
        searchResultAdapter = SearchResultAdapter()
        popularAdapter = HomeMovieAdapter()
        upcomingAdapter = HomeMovieAdapter()

        binding.topRatedRv.apply {
            adapter = homeMovieAdapter
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }


        binding.searchListRv.apply {
            adapter = searchResultAdapter
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
        }

        binding.popularRv.apply {
            adapter = popularAdapter
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }

        binding.upcomingRv.apply {
            adapter = upcomingAdapter
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }

        viewModel.topRated.observe(requireActivity()) { result ->
            binding.apply {
                homeMovieAdapter.movie = result.results
            }
        }

        viewModel.searchResult.observe(requireActivity()) { search ->
            binding.apply {
                searchResultAdapter.searchResult = search.results
            }
        }

        viewModel.popular.observe(requireActivity()) { result ->
            binding.apply {
                popularAdapter.movie = result.results
            }
        }

        viewModel.upcoming.observe(requireActivity()) { result ->
            binding.apply {
                upcomingAdapter.movie = result.results
            }
        }

    }

    private fun searchBar() {
        binding.search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.i("onTextChanged s", s.toString())
                Log.i("onTextChanged count", count.toString())

                if (s != null) {
                    if (s.isNotEmpty()) {
                        binding.homeFragmentGroup.visibility = View.GONE
                        binding.searchListRv.visibility = View.VISIBLE
                        viewModel.getSearchResult(1, s.toString())
                    } else if (s.isEmpty()) {
                        binding.homeFragmentGroup.visibility = View.VISIBLE
                        binding.searchListRv.visibility = View.GONE
                        binding.title.text = getString(R.string.topRated)
                    }
                }

            }

            override fun afterTextChanged(s: Editable?) {
            }

        })


    }

    @SuppressLint("ClickableViewAccessibility")
    fun hideKeyboard() {
        binding.search.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
            binding.search.hideKeyboard()
            binding.search.clearFocus()
            }
        }

        binding.homeFragmentGroup.setOnTouchListener { _, _ ->
            binding.search.hideKeyboard()
            binding.search.clearFocus()
            true
        }

    }


}