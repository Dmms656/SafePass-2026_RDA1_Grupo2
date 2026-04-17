package com.example.safepass_rda1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.safepass_rda1.logic.registrarAsistente
import com.example.safepass_rda1.state.RegistroState
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

// Pantalla principal de registro y manejo de estados
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen() {
    var nombre by remember { mutableStateOf("") }
    var edadTexto by remember { mutableStateOf("") }
    var tipoEntrada by remember { mutableStateOf("") }
    var estado by remember { mutableStateOf<RegistroState>(RegistroState.Idle) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("SafePass 2026 - Check-in") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = edadTexto,
                onValueChange = { edadTexto = it },
                label = { Text("Edad") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = tipoEntrada,
                onValueChange = { tipoEntrada = it },
                label = { Text("Tipo de Entrada (VIP / GENERAL)") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    val edad = edadTexto.toIntOrNull()
                    estado = registrarAsistente(nombre, edad, tipoEntrada)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrar")
            }


            when (val estadoActual = estado) {
                is RegistroState.Idle -> {
                    Text("Ingrese los datos del asistente para continuar.")
                }
                is RegistroState.Success -> {
                    Text("Confirmación: ${estadoActual.mensaje}")
                    Text("Nombre: ${estadoActual.asistente.nombre}")
                    Text("Edad: ${estadoActual.asistente.edad}")
                    Text("Tipo de entrada: ${estadoActual.asistente.tipoEntrada}")
                }
                is RegistroState.Error -> {
                    Text("Error de validación: ${estadoActual.mensaje}")
                }
            }
        }
    }
}