package com.example.ferreteria.repository

import com.example.ferreteria.model.Pedido
import com.example.ferreteria.model.ItemCarrito

object PedidoRepository {

    private val pedidos = mutableListOf<Pedido>()
    private var autoId = 1

    fun crearPedido(
        usuarioId: Int,
        items: List<ItemCarrito>,
        total: Int
    ) {
        pedidos.add(
            Pedido(
                id = autoId++,
                usuarioId = usuarioId,
                fechaMillis = System.currentTimeMillis(),
                items = items,
                total = total
            )
        )
    }

    fun obtenerPedidosPorUsuario(usuarioId: Int): List<Pedido> {
        return pedidos.filter { it.usuarioId == usuarioId }
    }
}
