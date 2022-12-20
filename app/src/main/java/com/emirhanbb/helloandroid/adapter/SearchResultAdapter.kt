package com.emirhanbb.helloandroid.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.emirhanbb.helloandroid.data.model.MovieResponseResult
import com.emirhanbb.helloandroid.databinding.SearchResultLayoutAdapterBinding
import com.emirhanbb.helloandroid.util.Constants

class SearchResultAdapter:RecyclerView.Adapter<SearchResultAdapter.MyViewHolder>()  {
    inner class MyViewHolder(val binding: SearchResultLayoutAdapterBinding) :
        RecyclerView.ViewHolder(binding.root)


    private val diffCallback = object : DiffUtil.ItemCallback<MovieResponseResult>() {
        override fun areItemsTheSame(oldItem: MovieResponseResult, newItem: MovieResponseResult): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieResponseResult, newItem: MovieResponseResult): Boolean {
            return newItem == oldItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var searchResult: List<MovieResponseResult>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            SearchResultLayoutAdapterBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentTvShow = searchResult[position]
        holder.binding.poster.load(Constants.POSTER_BASE_URL + currentTvShow.poster_path){
            crossfade(true)
            crossfade(1000)
        }
        holder.binding.movieTitle.text = currentTvShow.title
        holder.binding.movieReleaseDate.text = currentTvShow.release_date
//        holder.binding.apply {
//
//            imageView.load(Constants.POSTER_BASE_URL + currentTvShow.poster_path) {
//                crossfade(true)
//                crossfade(1000)
//            }
//        }
    }

    override fun getItemCount() = searchResult.size
}