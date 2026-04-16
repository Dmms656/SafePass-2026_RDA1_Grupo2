package com.example.myapplication.utils

// Valida la mayoría de edad
fun Int.esMayorDeEdad(): Boolean {
    return this >= 18
}

// Limpia espacios perimetrales y capitaliza nombre
fun String.nombreFormateado(): String {
    return this.trim().replaceFirstChar { it.uppercase() }
}