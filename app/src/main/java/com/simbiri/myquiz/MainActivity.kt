package com.simbiri.myquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.simbiri.myquiz.presentation.navigation.NavGraph
import com.simbiri.myquiz.presentation.theme.MyQuizTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            MyQuizTheme {
                val navController = rememberNavController()
                Scaffold { paddingValues ->

                    NavGraph(
                        navController = navController,
                        paddingValues = paddingValues
                    )
                }

            }
        }
    }
}
