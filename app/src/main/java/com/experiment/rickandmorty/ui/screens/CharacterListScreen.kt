package com.experiment.rickandmorty.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.experiment.rickandmorty.ui.CharacterViewModel

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun CharacterListScreen() {
    val viewModel: CharacterViewModel = hiltViewModel()
    val characterList = viewModel.characterList.collectAsLazyPagingItems()
    Surface {
        Column {
            LazyVerticalGrid(columns = GridCells.Fixed(3)) {
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
