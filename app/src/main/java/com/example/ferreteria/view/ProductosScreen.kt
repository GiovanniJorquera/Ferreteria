package com.example.ferreteria.view

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.ferreteria.viewmodel.CarritoActivity
import com.example.ferreteria.viewmodel.LoginActivity
import com.example.ferreteria.model.Rol
import com.example.ferreteria.repository.CarritoRepository
import com.example.ferreteria.repository.UsuarioRepository
import com.example.ferreteria.viewmodel.ProductoViewModel
import com.example.ferreteria.viewmodel.HistorialActivity
import com.example.ferreteria.model.Producto


@Composable
fun ProductosScreen(viewModel: ProductoViewModel) {

    val context = LocalContext.current
    val usuario = UsuarioRepository.usuarioActual
    val productos by viewModel.productos.collectAsState()

    var productoEditando by remember { mutableStateOf<Producto?>(null) }
    var nombre by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }

    Scaffold { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {


            Button(
                onClick = {
                    context.startActivity(Intent(context, LoginActivity::class.java))
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    if (usuario == null) "Iniciar sesión"
                    else "Usuario: ${usuario.nombre}"
                )
            }


            if (usuario?.rol == Rol.CLIENTE) {
                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        context.startActivity(
                            Intent(context, CarritoActivity::class.java)
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Ver carrito")
                }
            }
            if (usuario?.rol == Rol.CLIENTE) {
                Button(
                    onClick = {
                        context.startActivity(
                            Intent(context, HistorialActivity::class.java)
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Ver historial de compras")
                }

                Spacer(modifier = Modifier.height(8.dp))
            }


            Spacer(modifier = Modifier.height(16.dp))

            if (usuario?.rol == Rol.ADMIN) {

                Text(
                    text = "Agregar producto",
                    style = MaterialTheme.typography.titleMedium
                )

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
            }

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

                        if (usuario?.rol == Rol.CLIENTE) {
                            Button(
                                onClick = {
                                    CarritoRepository.agregarProducto(producto)
                                    Toast.makeText(
                                        context,
                                        "Producto agregado al carrito",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                },
                                modifier = Modifier.padding(top = 4.dp)
                            ) {
                                Text("Agregar al carrito")
                            }
                        }

                        if (usuario?.rol == Rol.ADMIN) {

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
    }
}
