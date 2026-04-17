package com.example.safepass_rda1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.safepass_rda1.ui.screens.RegistroScreen
import com.example.safepass_rda1.ui.theme.SafePassRDA1Theme

// Punto de entrada principal con configuración Edge-to-Edge
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SafePassRDA1Theme {
                RegistroScreen()
            }
        }
    }
}