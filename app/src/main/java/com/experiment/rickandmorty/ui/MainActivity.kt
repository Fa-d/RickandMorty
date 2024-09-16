package com.experiment.rickandmorty.ui

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.experiment.rickandmorty.nav.BottomNavigationBar
import com.experiment.rickandmorty.nav.RMNavGraph
import com.experiment.rickandmorty.ui.screens.PermissionBox
import com.experiment.rickandmorty.ui.theme.RMTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController: NavHostController = rememberNavController()
            val permissions = arrayListOf<String>()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                permissions.add(Manifest.permission.POST_NOTIFICATIONS)
            }
            RMTheme {
                PermissionBox(permissions = permissions) {
                    Scaffold(bottomBar = {
                        BottomNavigationBar(navController)
                    }) { paddingValues ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues)
                        ) {
                            RMNavGraph(navController)
                        }
                    }
                }
            }
        }
    }
}