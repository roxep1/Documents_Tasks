package com.bashkir.documentstasks.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.bashkir.documentstasks.navigation.CreateAuthNavHost
import com.bashkir.documentstasks.ui.theme.DocumentsTasksTheme

class AuthActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DocumentsTasksTheme {
                Surface(color = MaterialTheme.colors.background) {
                    StartAuthActivity()
                }
            }
        }
    }

    @Composable
    private fun StartAuthActivity() =
        CreateAuthNavHost(navController = rememberNavController()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
}
