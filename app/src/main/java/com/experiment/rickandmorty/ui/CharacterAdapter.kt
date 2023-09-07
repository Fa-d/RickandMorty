package com.experiment.rickandmorty.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.experiment.rickandmorty.data.model.CharactersModel
import com.experiment.rickandmorty.databinding.CharactersViewholderBinding

class CharacterAdapter :
    PagingDataAdapter<CharactersModel, CharacterViewHolder>(ARTICLE_DIFF_CALLBACK) {

    var oonCharacterClicked: ((selectedCharacterID: CharactersModel) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder =
        CharacterViewHolder(
            CharactersViewholderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        )

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val tile = getItem(position)
        if (tile != null) {
            holder.bind(tile)
            holder.itemView.rootView.setOnClickListener {
                Toast.makeText(
                    holder.itemView.context, tile.name + ":" + tile.id, Toast.LENGTH_SHORT
                ).show()
                oonCharacterClicked?.invoke(tile)
            }
        }
    }

    companion object {
        private val ARTICLE_DIFF_CALLBACK = object : DiffUtil.ItemCallback<CharactersModel>() {
            override fun areItemsTheSame(
                oldItem: CharactersModel, newItem: CharactersModel
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: CharactersModel, newItem: CharactersModel
            ): Boolean = oldItem == newItem
        }
    }
}
