package com.experiment.rickandmorty.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.experiment.rickandmorty.R
import com.experiment.rickandmorty.data.CharacterViewModel
import com.experiment.rickandmorty.databinding.FragmentIndividualCharactersBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IndividualCharactersFragment : Fragment() {

    private var _binding: FragmentIndividualCharactersBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIndividualCharactersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        val viewModel: CharacterViewModel by activityViewModels()
        bindImageFromUrl(binding.avatarImage, viewModel.selectedCharacterID.image)
        binding.apply {
            nameOfCharacter.text = viewModel.selectedCharacterID.name
            speciesOfCharacter.text = viewModel.selectedCharacterID.species
            genderOfCharacter.text = viewModel.selectedCharacterID.gender
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    if (imageUrl.isNullOrEmpty()) {
        Toast.makeText(view.context, "Null URL", Toast.LENGTH_SHORT).show()
        return
    } else {
        Log.e("TImageURL", imageUrl)
    }
    Glide.with(view).load(imageUrl).into(view)
}

