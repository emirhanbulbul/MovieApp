package com.emirhanbb.movieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.emirhanbb.movieapp.data.model.response.movieresponse.MovieResponseResult
import com.emirhanbb.movieapp.databinding.HomeSliderLayoutAdapterBinding
import com.emirhanbb.movieapp.util.Constants


class HomeMovieAdapter(
    private var onItemClickListener:(Int,Int) ->Unit

) : RecyclerView.Adapter<HomeMovieAdapter.MyViewHolder>() {
    inner class MyViewHolder(val binding: HomeSliderLayoutAdapterBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<MovieResponseResult>() {
        override fun areItemsTheSame(
            oldItem: MovieResponseResult,
            newItem: MovieResponseResult
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MovieResponseResult,
            newItem: MovieResponseResult
        ): Boolean {
            return newItem == oldItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var movie: List<MovieResponseResult>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            HomeSliderLayoutAdapterBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentMovie = movie[position]
        holder.binding.imageView.load(Constants.POSTER_BASE_URL + currentMovie.poster_path) {
            crossfade(true)
            crossfade(1000)
        }

        holder.binding.root.setOnClickListener {
            onItemClickListener.invoke(position, movie[position].id)
        }


    }

    override fun getItemCount() = movie.size
}

