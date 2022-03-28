package com.bashkir.documentstasks.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.airbnb.mvrx.compose.mavericksActivityViewModel
import com.bashkir.documentstasks.ui.navigation.CreateMainNavHost
import com.bashkir.documentstasks.ui.theme.DocumentsTasksTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DocumentsTasksTheme {
                Surface(color = MaterialTheme.colors.background) {
                    StartMainActivity()
                }
            }
        }
    }

}

@Composable
private fun StartMainActivity() {
    CreateMainNavHost(
        navController = rememberNavController(),
        authViewModel = mavericksActivityViewModel(),
        tasksViewModel = mavericksActivityViewModel(),
        profileViewModel = mavericksActivityViewModel(),
        notificationsViewModel = mavericksActivityViewModel(),
        documentsViewModel = mavericksActivityViewModel()
    )
}
