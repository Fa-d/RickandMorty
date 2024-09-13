package com.experiment.rickandmorty.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.experiment.rickandmorty.MainApplication
import com.experiment.rickandmorty.nav.NavScreens.CHARACTER_SCREEN
import com.experiment.rickandmorty.nav.NavScreens.EPISODE_SCREEN
import com.experiment.rickandmorty.nav.NavScreens.INDIVIDUAL_ITEM_SCREEN
import com.experiment.rickandmorty.nav.NavScreens.LOADING_SCREEN
import com.experiment.rickandmorty.nav.NavScreens.LOCATION_SCREEN
import com.experiment.rickandmorty.ui.MainViewModel
import com.experiment.rickandmorty.ui.screens.CharacterListScreen
import com.experiment.rickandmorty.ui.screens.LoadingScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest


@Composable
fun RMNavGraph(
    navController: NavHostController,
    startDestination: String = LOADING_SCREEN, scope: CoroutineScope = rememberCoroutineScope(),
    navActions: RMNavActions = remember(navController) { RMNavActions(navController) }
) {

    val mainViewModel: MainViewModel = hiltViewModel()
    val context = LocalContext.current

    val isLoadingComplete = (context.applicationContext as MainApplication).isLoadingComplete

    NavHost(navController = navController, startDestination = startDestination) {
        composable(LOADING_SCREEN) {
            LoadingScreen()
            LaunchedEffect(key1 = "") {
                isLoadingComplete.collectLatest { res ->
                    if (res) {
                        navActions.navigateToHomeScreen()
                    } else if (!mainViewModel.isDatabaseEmpty()) {
                        navActions.navigateToHomeScreen()
                    }
                }
            }
        }
        composable(INDIVIDUAL_ITEM_SCREEN) {

        }
        composable(CHARACTER_SCREEN) {
            CharacterListScreen()
        }
        composable(LOCATION_SCREEN) {

        }
        composable(EPISODE_SCREEN) {

        }
    }
}