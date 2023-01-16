package com.emirhanbb.movieapp.ui.home

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.emirhanbb.movieapp.R
import com.emirhanbb.movieapp.adapter.HomeMovieAdapter
import com.emirhanbb.movieapp.adapter.SearchOnItemClickListener
import com.emirhanbb.movieapp.adapter.SearchResultAdapter
import com.emirhanbb.movieapp.base.BaseFragment
import com.emirhanbb.movieapp.databinding.FragmentHomeBinding
import com.emirhanbb.movieapp.ext.addOnScrollListenerForPaging
import com.emirhanbb.movieapp.util.Constants
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var topRatedAdapter: HomeMovieAdapter
    private lateinit var searchResultAdapter: SearchResultAdapter
    private lateinit var popularAdapter: HomeMovieAdapter
    private lateinit var upcomingAdapter: HomeMovieAdapter
    override fun initListeners() {
        searchBar()
        setUpRv()
        getArgs()
    }

    private fun setUpRv() {
        topRatedAdapter = HomeMovieAdapter { position, id ->
            val bundle = bundleOf(Constants.MOVIE_ID to id)
            findNavController().navigate(R.id.action_homeFragment_to_detailFragment, bundle)
        }

        popularAdapter = HomeMovieAdapter { position, id ->
            val bundle = bundleOf(Constants.MOVIE_ID to id)
            findNavController().navigate(R.id.action_homeFragment_to_detailFragment, bundle)
        }

        upcomingAdapter = HomeMovieAdapter { position, id ->
            val bundle = bundleOf(Constants.MOVIE_ID to id)
            findNavController().navigate(R.id.action_homeFragment_to_detailFragment, bundle)
        }

        searchResultAdapter = SearchResultAdapter(object : SearchOnItemClickListener {
            override fun onItemClick(position: Int, id: Int) {
                val bundle = bundleOf(Constants.MOVIE_ID to id)
                findNavController().navigate(R.id.action_homeFragment_to_detailFragment, bundle)
            }
        })

        binding.topRatedRv.apply {
            adapter = topRatedAdapter
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }

        binding.topRatedRv.addOnScrollListenerForPaging {
            viewModel.pagination(1)
        }

        binding.popularRv.apply {
            adapter = popularAdapter
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }

        binding.popularRv.addOnScrollListenerForPaging {
            viewModel.pagination(2)
        }

        binding.upcomingRv.apply {
            adapter = upcomingAdapter
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }
        binding.upcomingRv.addOnScrollListenerForPaging {
            viewModel.pagination(3)
        }

        binding.searchListRv.apply {
            adapter = searchResultAdapter
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
        }
    }

    private fun getArgs() {
        viewModel.loading.observe(this) {
            when (it) {
                true -> binding.progressBar.visibility = View.VISIBLE
                false -> binding.progressBar.visibility = View.GONE
            }
        }

        viewModel.topRatedPage.observe(requireActivity()) {
            viewModel.getTopRated()
            viewModel.topRated.observe(requireActivity()) { result ->
                binding.apply {
                    topRatedAdapter.movie = result
                    topRatedAdapter.notifyDataSetChanged()
                }
            }
        }

        viewModel.popularPage.observe(requireActivity()) {
            viewModel.getPopular()
            viewModel.popular.observe(requireActivity()) { result ->
                binding.apply {
                    popularAdapter.movie = result
                    popularAdapter.notifyDataSetChanged()
                }
            }
        }

        viewModel.upcomingPage.observe(requireActivity()) {
            viewModel.getUpcoming()
            viewModel.upcoming.observe(requireActivity()) { result ->
                binding.apply {
                    upcomingAdapter.movie = result
                    upcomingAdapter.notifyDataSetChanged()
                }
            }
        }

        viewModel.searchResult.observe(requireActivity()) { search ->
            binding.apply {
                searchResultAdapter.searchResult = search
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
}