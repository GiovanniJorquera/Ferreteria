package com.example.ferreteria.repository

import com.example.ferreteria.model.ItemCarrito
import com.example.ferreteria.model.Producto

object CarritoRepository {

    private val items = mutableListOf<ItemCarrito>()

    fun obtenerItems(): List<ItemCarrito> =
        items.toList()

    fun agregarProducto(producto: Producto) {
        val item = items.find { it.productoId == producto.id }

        if (item != null) {
            item.cantidad++
        } else {
            items.add(ItemCarrito(productoId = producto.id, nombre = producto.nombre, precio = producto.precio, cantidad = 1))
        }
    }

    fun eliminarProducto(productoId: Int) {
        items.removeIf { it.productoId == productoId }
    }

    fun vaciarCarrito() {
        items.clear()
    }

    fun total(): Int {
        return items.sumOf { it.precio * it.cantidad }
    }

    fun estaVacio(): Boolean = items.isEmpty()
}
