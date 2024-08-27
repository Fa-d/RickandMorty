package com.experiment.rickandmorty.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController


@Composable
fun RMNavGraph(
    navController: NavHostController = rememberNavController(),
    navActions: RMNavActions = remember(navController) { RMNavActions(navController) }
) {

}