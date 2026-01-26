package com.example.ferreteria.repository

import android.content.Context
import com.example.ferreteria.model.Rol
import com.example.ferreteria.model.Usuario

object UsuarioRepository {

    private val usuarios = listOf(
        Usuario(1, "Juan Cliente", "cliente@test.cl", "1234", Rol.CLIENTE),
        Usuario(2, "Ana Admin", "admin@test.cl", "admin", Rol.ADMIN)
    )

    var usuarioActual: Usuario? = null
        private set

    fun login(email: String, password: String, context: Context): Boolean {
        val usuario = usuarios.find {
            it.email == email && it.password == password
        }

        if (usuario != null) {
            usuarioActual = usuario
            guardarSesion(context, usuario)
            return true
        }
        return false
    }

    fun logout(context: Context) {
        usuarioActual = null
        val prefs = context.getSharedPreferences("sesion", Context.MODE_PRIVATE)
        prefs.edit().clear().apply()
    }

    fun cargarSesion(context: Context) {
        val prefs = context.getSharedPreferences("sesion", Context.MODE_PRIVATE)
        val id = prefs.getInt("id", -1)

        if (id != -1) {
            usuarioActual = usuarios.find { it.id == id }
        }
    }

    private fun guardarSesion(context: Context, usuario: Usuario) {
        val prefs = context.getSharedPreferences("sesion", Context.MODE_PRIVATE)
        prefs.edit()
            .putInt("id", usuario.id)
            .apply()
    }
}
