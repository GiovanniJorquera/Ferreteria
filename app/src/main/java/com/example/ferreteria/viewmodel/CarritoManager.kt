package com.example.ferreteria.viewmodel

import com.example.ferreteria.Carrito.ItemCarrito

class CarritoViewModel {

    private val items = mutableListOf<ItemCarrito>()

    fun agregarItem(item: ItemCarrito): Boolean {
        if (item.cantidad <= 0) return false

        val existente = items.find { it.productoId == item.productoId }

        if (existente != null) {
            existente.cantidad += item.cantidad
        } else {
            items.add(item)
        }
        return true
    }

    fun eliminarItem(productoId: Int) {
        items.removeIf { it.productoId == productoId }
    }

    fun obtenerItems(): List<ItemCarrito> = items

    fun calcularTotal(): Int {
        return items.sumOf { it.precio * it.cantidad }
    }

    fun vaciarCarrito() {
        items.clear()
    }

    fun estaVacio(): Boolean = items.isEmpty()
}