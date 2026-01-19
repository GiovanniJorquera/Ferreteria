package com.example.ferreteria.repository

import com.example.ferreteria.Usuario.Rol
import com.example.ferreteria.Usuario.Usuario

object UsuarioRepository {

    private val usuarios = listOf(
        Usuario(1, "Juan Cliente", "cliente@test.cl", "1234", Rol.CLIENTE),
        Usuario(2, "Ana Admin", "admin@test.cl", "admin", Rol.ADMIN)
    )

    var usuarioActual: Usuario? = null
        private set

    fun login(email: String, password: String): Boolean {
        val usuario = usuarios.find {
            it.email == email && it.password == password
        }
        usuarioActual = usuario
        return usuario != null
    }

    fun logout() {
        usuarioActual = null
    }
}