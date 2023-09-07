package com.experiment.rickandmorty.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.experiment.rickandmorty.data.CharacterViewModel
import com.experiment.rickandmorty.databinding.FragmentIndividualCharactersBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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

        viewModel.individualCharacterResponse(viewModel.selectedCharacterID.toString())

        viewModel.individualCharacterData.observe(viewLifecycleOwner) { response ->
            Log.e("dsam fas", response.toString())
            bindImageFromUrl(binding.avatarImage, response?.image)
            binding.apply {
                nameOfCharacter.text = response?.name
                speciesOfCharacter.text = response?.species
                genderOfCharacter.text = response?.gender
            }
        }


    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    Glide.with(view).load(imageUrl).into(view)
}

