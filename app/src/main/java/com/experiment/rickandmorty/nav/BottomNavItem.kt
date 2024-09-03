package com.experiment.rickandmorty.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.experiment.rickandmorty.nav.NavScreens.CHARACTER_SCREEN
import com.experiment.rickandmorty.nav.NavScreens.EPISODE_SCREEN
import com.experiment.rickandmorty.nav.NavScreens.LOCATION_SCREEN

sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    data object Character : BottomNavItem(CHARACTER_SCREEN, Icons.Default.Person, "Character")
    data object Location : BottomNavItem(LOCATION_SCREEN, Icons.Default.LocationOn, "Location")
    data object Episode : BottomNavItem(EPISODE_SCREEN, Icons.Default.DateRange, "Episode")
}
