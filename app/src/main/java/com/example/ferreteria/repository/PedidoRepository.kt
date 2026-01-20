package com.example.ferreteria.repository

import com.example.ferreteria.Carrito.ItemCarrito
import com.example.ferreteria.Pedidos.Pedido

/**
 * Repositorio en memoria para registrar compras (historial por usuario).
 */
object PedidoRepository {

    private val pedidos = mutableListOf<Pedido>()
    private var nextId = 1

    fun registrarCompra(usuarioId: Int, items: List<ItemCarrito>): Pedido {
        val total = items.sumOf { it.precio * it.cantidad }
        val pedido = Pedido(
            id = nextId++,
            usuarioId = usuarioId,
            fechaMillis = System.currentTimeMillis(),
            items = items.map { it.copy() },
            total = total
        )
        pedidos.add(pedido)
        return pedido
    }

    fun obtenerPedidosPorUsuario(usuarioId: Int): List<Pedido> {
        return pedidos
            .filter { it.usuarioId == usuarioId }
            .sortedByDescending { it.fechaMillis }
    }
}
