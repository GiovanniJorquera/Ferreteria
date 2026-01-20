package com.example.ferreteria.Pedidos

import com.example.ferreteria.Carrito.ItemCarrito

data class Pedido(
    val id: Int,
    val usuarioId: Int,
    val fechaMillis: Long,
    val items: List<ItemCarrito>,
    val total: Int
)
