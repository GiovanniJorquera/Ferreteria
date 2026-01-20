package com.example.ferreteria.Productos

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ferreteria.viewmodel.ProductoViewModel

@Composable
fun ProductosScreen(viewModel: ProductoViewModel) {

    val productos by viewModel.productos.collectAsState()
    var productoEditando by remember { mutableStateOf<Producto?>(null) }


    var nombre by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {

        Text(text = "Agregar producto", style = MaterialTheme.typography.titleMedium)

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = precio,
            onValueChange = { precio = it },
            label = { Text("Precio") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = stock,
            onValueChange = { stock = it },
            label = { Text("Stock") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (productoEditando == null) {
                    viewModel.agregarProducto(nombre, precio, stock)
                } else {
                    viewModel.editarProducto(
                        productoEditando!!.id,
                        nombre,
                        precio,
                        stock
                    )
                    productoEditando = null
                }

                nombre = ""
                precio = ""
                stock = ""
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(if (productoEditando == null) "Agregar" else "Guardar")
        }


        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(productos) { producto ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(
                        text = "${producto.nombre} - $${producto.precio}",
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Text(
                        text = "Stock: ${producto.stock}",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Button(
                        onClick = { viewModel.eliminarProducto(producto) },
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Text("Eliminar")
                    }
                    Button(
                        onClick = {
                            productoEditando = producto
                            nombre = producto.nombre
                            precio = producto.precio.toString()
                            stock = producto.stock.toString()
                        },
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Text("Editar")
                    }

                }
            }
        }

    }
}