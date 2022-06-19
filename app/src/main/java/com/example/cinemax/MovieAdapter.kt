package com.example.cinemax

import android.content.Intent
import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinemax.data.ResultsItem
import com.example.cinemax.databinding.ItemMovieBinding


class MovieAdapter(val onItemClick: (movieItem: ResultsItem) -> Unit) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>(){

    private val movieItemList = mutableListOf<ResultsItem>()

    fun addItems(movieItemList: List<ResultsItem>) {
        this.movieItemList.clear()
        this.movieItemList.addAll(movieItemList)
        notifyDataSetChanged()


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapter.ViewHolder {
        val itemMovieBinding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemMovieBinding)

    }

    override fun onBindViewHolder(holder: MovieAdapter.ViewHolder, position: Int) {
        val movieItem = movieItemList[position]
        holder.bind(movieItem)
        holder.itemView.setOnClickListener{onItemClick(movieItem)}
    }

    override fun getItemCount(): Int = movieItemList.size

    inner class ViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(movieEntity: ResultsItem){
            with(binding) {
                Glide.with(binding.root)
                    .load("https://image.tmdb.org/t/p/original${movieEntity.posterPath}")
                    .into(imgMovie)
                tvName.text = movieEntity.title
            }
        }
    }

}