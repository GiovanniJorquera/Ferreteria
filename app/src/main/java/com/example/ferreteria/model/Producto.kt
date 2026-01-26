package com.example.ferreteria.model

data class Producto(
    val id: Int,
    var nombre: String,
    var precio: Int,
    var stock: Int,
    var imagenUri : String? = null
)