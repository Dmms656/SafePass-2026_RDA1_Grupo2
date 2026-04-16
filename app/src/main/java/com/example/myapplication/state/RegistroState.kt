package com.example.myapplication.state

import com.example.myapplication.model.Asistente

// Controla los estados de la interfaz de usuario
sealed class RegistroState {

    // Esperando interacción inicial
    object Idle : RegistroState()

    // Validado correctamente
    data class Success(
        val asistente: Asistente,
        val mensaje: String
    ) : RegistroState()

    // Error de validación
    data class Error(
        val mensaje: String
    ) : RegistroState()
}