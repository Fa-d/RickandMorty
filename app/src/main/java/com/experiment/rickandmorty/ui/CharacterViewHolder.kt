package com.experiment.rickandmorty.ui

import androidx.recyclerview.widget.RecyclerView
import com.experiment.rickandmorty.data.model.CharactersModel
import com.experiment.rickandmorty.databinding.CharactersViewholderBinding

class CharacterViewHolder(private val binding: CharactersViewholderBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(charactersModel: CharactersModel) {
        binding.apply {
            binding.title.text = charactersModel.name
            binding.description.text = charactersModel.status
            binding.created.text = charactersModel.gender
        }
    }
}
