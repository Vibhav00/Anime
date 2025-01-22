package com.vk.demoapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vk.demoapp.R
import com.vk.demoapp.data.Data
import com.vk.demoapp.databinding.AnimeItemBinding

class AnimeAdapter(private var list: List<Data>, val onClick: (Int) -> Unit) :
    RecyclerView.Adapter<AnimeAdapter.AnimeViewHolder>() {
    inner class AnimeViewHolder(val binding: AnimeItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        return AnimeViewHolder(
            AnimeItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        holder.binding.apply {
            list[position].let {
                Glide.with(this.root)
                    .load(it.images.jpg.image_url)
                    .placeholder(R.drawable.ic_android_black_24dp)
                    .error(R.drawable.ic_android_black_24dp)
                    .into(ivStart)
                tvTitle.text = it.title
                if(it.episodes==1) noOfEpisodeds.text = it.episodes.toString() +" Episode"
                else noOfEpisodeds.text = it.episodes.toString() +" Episodes"
                rating.text = it.score.toString() +" * "
                root.setOnClickListener { _ ->
                    onClick(it.mal_id)
                }
            }
        }
    }

    public fun updateList(list: List<Data>) {
        this.list = list
        notifyDataSetChanged()  // todo can be optimized
    }
}