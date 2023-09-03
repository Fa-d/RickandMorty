package com.experiment.rickandmorty.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.experiment.rickandmorty.R
import com.experiment.rickandmorty.data.CharacterViewModel
import com.experiment.rickandmorty.databinding.ActivityMainBinding
import com.experiment.rickandmorty.databinding.FragmentCharactersListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharactersListFragment : Fragment() {

    private var _binding: FragmentCharactersListBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharactersListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()

    }

    private fun initView() {

        val viewModel: CharacterViewModel by activityViewModels()
        val characterAdapter = CharacterAdapter()

        binding.bindAdapter(characterAdapter = characterAdapter)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.characterList.collectLatest { innerData ->
                    CharacterAdapter().apply {
                        characterAdapter.submitData(innerData)
                    }
                }
            }
        }
        characterAdapter.oonCharacterClicked = { selectedCharacter ->
            viewModel.selectedCharacterID = selectedCharacter
            findNavController().navigate(R.id.action_charactersListFragment_to_individualCharactersFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun FragmentCharactersListBinding.bindAdapter(characterAdapter: CharacterAdapter) {
        list.adapter = characterAdapter
        list.layoutManager = LinearLayoutManager(list.context)
        val decoration = DividerItemDecoration(list.context, DividerItemDecoration.VERTICAL)
        list.addItemDecoration(decoration)
    }
}