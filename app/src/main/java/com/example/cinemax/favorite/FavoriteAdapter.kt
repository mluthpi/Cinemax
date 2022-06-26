package com.example.cinemax.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinemax.data.ResultsItem
import com.example.cinemax.databinding.ItemMovieBinding
import com.example.cinemax.model.MovieEntity

class FavoriteAdapter(private val onItemClick: (item: ResultsItem)-> Unit) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>(){

    private val movieItemList = mutableListOf<ResultsItem>()

    fun addItems(movieItemList: List<MovieEntity>){
        this.movieItemList.clear()
        this.movieItemList.addAll(movieItemList)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteAdapter.ViewHolder {
        val itemMovieBinding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemMovieBinding)
    }

    override fun onBindViewHolder(holder: FavoriteAdapter.ViewHolder, position: Int) {
        val movieItem = movieItemList[position]
        holder.bind(movieItem)
        holder.itemView.setOnClickListener {onItemClick(movieItem)}
    }

    override fun getItemCount(): Int = movieItemList.size

    inner class ViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movieItem: ResultsItem) {
            with(binding) {
                Glide.with(binding.root)
                    .load("https://image.tmdb.org/t/p/original${movieItem.posterPath}")
                    .into(imgMovie)
            }
        }
    }
}