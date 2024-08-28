package com.experiment.rickandmorty.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.experiment.rickandmorty.nav.NavScreens.HOME_SCREEN
import com.experiment.rickandmorty.nav.NavScreens.INDIVIDUAL_ITEM_SCREEN
import com.experiment.rickandmorty.nav.NavScreens.LOADING_SCREEN
import com.experiment.rickandmorty.ui.screens.CharacterListScreen


@Composable
fun RMNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = LOADING_SCREEN,
    navActions: RMNavActions = remember(navController) { RMNavActions(navController) }
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(LOADING_SCREEN) {
            CharacterListScreen()
        }
        composable(HOME_SCREEN) {}
        composable(INDIVIDUAL_ITEM_SCREEN) {}

    }
}