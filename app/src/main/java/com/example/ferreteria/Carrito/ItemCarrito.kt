package com.example.ferreteria.Carrito

data class ItemCarrito(
    val productoId: Int,
    val nombre: String,
    val precio: Int,
    var cantidad: Int
)
