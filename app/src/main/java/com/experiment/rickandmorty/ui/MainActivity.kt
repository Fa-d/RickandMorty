package com.experiment.rickandmorty.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.experiment.rickandmorty.nav.RMNavGraph
import com.experiment.rickandmorty.ui.theme.RMTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)/*        val binding = ActivityMainBinding.inflate(layoutInflater)
                val view = binding.root
                setContentView(view)*/

        enableEdgeToEdge()
        setContent {
            RMTheme {
                RMNavGraph()
            }
        }
    }
}