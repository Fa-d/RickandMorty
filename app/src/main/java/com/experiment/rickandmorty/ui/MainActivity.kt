package com.experiment.rickandmorty.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.experiment.rickandmorty.nav.BottomNavigationBar
import com.experiment.rickandmorty.nav.RMNavGraph
import com.experiment.rickandmorty.ui.theme.RMTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController: NavHostController = rememberNavController()
            RMTheme {
                Scaffold(bottomBar = {
                    BottomNavigationBar(navController)
                }) { paddingValues ->
                    Box(modifier = Modifier.padding(paddingValues)) {
                        RMNavGraph(navController)
                    }
                }
            }
        }
    }
}