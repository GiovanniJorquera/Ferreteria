package com.example.ferreteria.Usuario

data class Usuario(
    val id: Int,
    val nombre: String,
    val email: String,
    val password: String,
    val rol: Rol
)

enum class Rol {
    CLIENTE,
    ADMIN
}
