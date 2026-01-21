package com.example.ferreteria.model

import com.example.ferreteria.model.ItemCarrito

data class Pedido(
    val id: Int,
    val usuarioId: Int,
    val fechaMillis: Long,
    val items: List<ItemCarrito>,
    val total: Int
)