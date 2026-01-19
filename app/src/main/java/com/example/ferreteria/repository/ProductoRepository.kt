package com.example.ferreteria.repository

import com.example.ferreteria.Productos.Producto

object ProductoRepository {

    val productos = mutableListOf(
        Producto(1, "Martillo", 6000, 10),
        Producto(2, "Taladro", 45000, 5),
        Producto(3, "Caja de tornillos", 3000, 20)
    )

    fun obtenerProductos(): List<Producto> = productos

    fun obtenerProductoPorId(id: Int): Producto? {
        return productos.find { it.id == id }
    }

    fun agregarProducto(producto: Producto) {
        productos.add(producto)
    }

    fun eliminarProducto(id: Int) {
        productos.removeIf { it.id == id }
    }
}