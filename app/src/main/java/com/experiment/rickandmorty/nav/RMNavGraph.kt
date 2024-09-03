package com.experiment.rickandmorty.nav

import android.os.Handler
import android.os.Looper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.experiment.rickandmorty.nav.NavScreens.CHARACTER_SCREEN
import com.experiment.rickandmorty.nav.NavScreens.EPISODE_SCREEN
import com.experiment.rickandmorty.nav.NavScreens.INDIVIDUAL_ITEM_SCREEN
import com.experiment.rickandmorty.nav.NavScreens.LOADING_SCREEN
import com.experiment.rickandmorty.nav.NavScreens.LOCATION_SCREEN
import com.experiment.rickandmorty.ui.screens.CharacterListScreen
import com.experiment.rickandmorty.ui.screens.LoadingScreen
import kotlinx.coroutines.CoroutineScope


@Composable
fun RMNavGraph(
    navController: NavHostController,
    startDestination: String = LOADING_SCREEN, scope: CoroutineScope = rememberCoroutineScope(),
    navActions: RMNavActions = remember(navController) { RMNavActions(navController) }
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(LOADING_SCREEN) {
            LoadingScreen()
            Handler(Looper.getMainLooper()).postDelayed({
                navActions.navigateToHomeScreen()
            }, 700L)
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