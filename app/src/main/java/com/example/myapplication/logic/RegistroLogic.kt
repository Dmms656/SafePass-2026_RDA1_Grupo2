package com.example.myapplication.logic

import com.example.myapplication.model.Asistente
import com.example.myapplication.state.RegistroState
import com.example.myapplication.utils.esMayorDeEdad
import com.example.myapplication.utils.nombreFormateado

// Función de orden superior para comportamiento inyectable
fun procesarAsistente(
    asistente: Asistente,
    accionEspecial: (Asistente) -> String
): String {
    return accionEspecial(asistente)
}

// Lógica central de negocio: validaciones y manejo de nulos
fun registrarAsistente(
    nombre: String,
    edad: Int?,
    tipoEntrada: String
): RegistroState {

    val nombreLimpio = nombre.nombreFormateado()
    val tipoEntradaLimpio = tipoEntrada.trim().uppercase()

    // Manejo Estructurado de Errores mediante Early Returns
    if (nombreLimpio.isEmpty()) {
        return RegistroState.Error("El nombre no puede estar vacío.")
    }

    if (tipoEntradaLimpio != "VIP" && tipoEntradaLimpio != "GENERAL") {
        return RegistroState.Error("El tipo de entrada '$tipoEntrada' no es válido. Debe ser VIP o GENERAL.")
    }

    // Inicialización de modelo inmutable con Scope Function Apply
    val asistente = Asistente(
        nombre = nombreLimpio,
        edad = edad,
        tipoEntrada = tipoEntradaLimpio
    ).apply {
        println("Auditoría -> Asistente inicializado: nombre=$nombreLimpio, edad=$edad, tipo=$tipoEntradaLimpio")
    }

    // Evaluación asertiva de anulabilidad con Scope Function Let
    return asistente.edad?.let { edadValida ->
        
        if (!edadValida.esMayorDeEdad()) {
            return@let RegistroState.Error("El asistente debe ser mayor de edad.")
        }
        
        val mensajeExtra = procesarAsistente(asistente) {
            when (it.tipoEntrada) {
                "VIP" -> "Acceso prioritario habilitado."
                "GENERAL" -> "Acceso general estándar habilitado."
                else -> ""
            }
        }

        RegistroState.Success(
            asistente = asistente,
            mensaje = "Registro completado satisfactoriamente. $mensajeExtra"
        )
        
    } ?: RegistroState.Error("La información correspondiente a la edad es obligatoria y de formato numérico.")
}