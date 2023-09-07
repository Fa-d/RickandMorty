package com.experiment.rickandmorty.ui

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.experiment.rickandmorty.data.model.CharactersModel
import com.experiment.rickandmorty.databinding.CharactersViewholderBinding


class CharacterViewHolder(private val binding: CharactersViewholderBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(charactersModel: CharactersModel) {

        binding.apply {
            binding.title.text = charactersModel.name
            binding.description.text = charactersModel.status
            binding.created.text = charactersModel.gender
            bindImageFromUrl(binding.characterImage, charactersModel.image)
        }
    }

    private fun bindImageFromUrl(
        view: ImageView,
        imageUrl: String?,
    ) {
        Glide.with(view).load(imageUrl).diskCacheStrategy(DiskCacheStrategy.ALL).into(view)
    }
}
