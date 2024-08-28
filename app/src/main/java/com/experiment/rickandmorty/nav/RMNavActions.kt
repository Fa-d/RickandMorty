package com.experiment.rickandmorty.nav

import androidx.navigation.NavHostController
import com.experiment.rickandmorty.nav.NavScreens.HOME_SCREEN
import com.experiment.rickandmorty.nav.NavScreens.INDIVIDUAL_ITEM_SCREEN
import com.experiment.rickandmorty.nav.NavScreens.LOADING_SCREEN

class RMNavActions(private val navController: NavHostController) {
    fun navigateToLoadingScreen() {
        navController.navigate(LOADING_SCREEN) { launchSingleTop = true }
    }

    fun navigateToHomeScreen() {
        navController.navigate(HOME_SCREEN) { launchSingleTop = true }
    }

    fun navigateToIndividualItemScreen() {
        navController.navigate(INDIVIDUAL_ITEM_SCREEN) { launchSingleTop = true }
    }
}