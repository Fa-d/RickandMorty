package com.experiment.rickandmorty.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.experiment.rickandmorty.ui.CharacterViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun CharacterListScreen() {
    val viewModel: CharacterViewModel = hiltViewModel()
    val characterList = viewModel.characterList.collectAsLazyPagingItems()
    Surface {
        Column {
            LazyVerticalGrid(columns = GridCells.Adaptive(100.dp)) {
                items(characterList.itemCount) { index ->
                    val item = characterList[index]
                    item?.let {
                        IndividualCharacter(it)
                    }
                }
            }
        }
    }
}

@Composable
private fun initView() {
    val lifecycleScope = rememberCoroutineScope { Dispatchers.IO }

    LaunchedEffect(key1 = Unit) {
        lifecycleScope.launch {}

    }
}