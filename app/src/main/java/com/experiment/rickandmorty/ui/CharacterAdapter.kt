/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.experiment.rickandmorty.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.experiment.rickandmorty.data.character.CharactersModel
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
