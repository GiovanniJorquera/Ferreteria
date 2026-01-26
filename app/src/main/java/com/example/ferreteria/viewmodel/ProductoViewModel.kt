package com.example.ferreteria.viewmodel

import androidx.lifecycle.ViewModel
import com.example.ferreteria.model.Producto
import com.example.ferreteria.repository.ProductoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProductoViewModel : ViewModel() {

    private val _productos =
        MutableStateFlow<List<Producto>>(ProductoRepository.obtenerProductos())

    val productos: StateFlow<List<Producto>> = _productos

    fun agregarProducto(
        nombre: String,
        precio: String,
        stock: String,
        imagenUri: String?
    ) {
        if (nombre.isBlank() || precio.isBlank() || stock.isBlank()) return

        val precioInt = precio.toIntOrNull() ?: return
        val stockInt = stock.toIntOrNull() ?: return

        val nuevoProducto = Producto(
            id = (_productos.value.maxOfOrNull { it.id } ?: 0) + 1,
            nombre = nombre,
            precio = precioInt,
            stock = stockInt,
            imagenUri = imagenUri
        )

        ProductoRepository.agregarProducto(nuevoProducto)
        _productos.value = ProductoRepository.obtenerProductos()
    }

    fun eliminarProducto(producto: Producto) {
        ProductoRepository.eliminarProducto(producto.id)
        _productos.value = ProductoRepository.obtenerProductos()
    }

    fun editarProducto(
        id: Int,
        nombre: String,
        precio: String,
        stock: String,
        imagenUri: String?
    ) {
        val precioInt = precio.toIntOrNull() ?: return
        val stockInt = stock.toIntOrNull() ?: return

        ProductoRepository.eliminarProducto(id)
        ProductoRepository.agregarProducto(
            Producto(
                id = id,
                nombre = nombre,
                precio = precioInt,
                stock = stockInt,
                imagenUri = imagenUri
            )
        )

        _productos.value = ProductoRepository.obtenerProductos()
    }
}
