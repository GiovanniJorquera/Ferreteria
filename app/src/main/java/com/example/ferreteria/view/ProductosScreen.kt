package com.example.ferreteria.view

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.ferreteria.model.Producto
import com.example.ferreteria.model.Rol
import com.example.ferreteria.repository.CarritoRepository
import com.example.ferreteria.repository.UsuarioRepository
import com.example.ferreteria.viewmodel.ProductoViewModel
import com.example.ferreteria.viewmodel.CarritoActivity
import com.example.ferreteria.viewmodel.LoginActivity
import com.example.ferreteria.viewmodel.HistorialActivity

@Composable
fun ProductosScreen(viewModel: ProductoViewModel) {

    val context = LocalContext.current
    val usuario = UsuarioRepository.usuarioActual
    val productos by viewModel.productos.collectAsState()

    var productoEditando by remember { mutableStateOf<Producto?>(null) }
    var nombre by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }
    var imagenSeleccionada by remember { mutableStateOf<Uri?>(null) }

    val galeriaLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            imagenSeleccionada = uri
        }

    val camaraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) {
            if (it != null) {
                Toast.makeText(context, "Foto tomada", Toast.LENGTH_SHORT).show()
            }
        }

    var mostrarProductos by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { mostrarProductos = true }

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
                Text(if (usuario == null) "Iniciar sesión" else "Usuario: ${usuario.nombre}")
            }

            if (usuario?.rol == Rol.CLIENTE) {

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        context.startActivity(Intent(context, CarritoActivity::class.java))
                    },
                    modifier = Modifier.fillMaxWidth()
                ) { Text("Ver carrito") }

                Button(
                    onClick = {
                        context.startActivity(Intent(context, HistorialActivity::class.java))
                    },
                    modifier = Modifier.fillMaxWidth()
                ) { Text("Ver historial de compras") }

                Spacer(modifier = Modifier.height(16.dp))
            }

            if (usuario?.rol == Rol.ADMIN) {

                Text("Agregar producto", style = MaterialTheme.typography.titleMedium)

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
                    onClick = { galeriaLauncher.launch("image/*") },
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                ) { Text("Seleccionar imagen") }

                val formularioValido =
                    nombre.isNotBlank() && precio.isNotBlank() && stock.isNotBlank()

                Button(
                    onClick = {
                        val uriString = imagenSeleccionada?.toString()

                        if (productoEditando == null) {
                            viewModel.agregarProducto(nombre, precio, stock, uriString)
                        } else {
                            viewModel.editarProducto(
                                productoEditando!!.id,
                                nombre,
                                precio,
                                stock,
                                uriString
                            )
                            productoEditando = null
                        }

                        nombre = ""
                        precio = ""
                        stock = ""
                        imagenSeleccionada = null
                    },
                    enabled = formularioValido,
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text(if (productoEditando == null) "Agregar" else "Guardar")
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            AnimatedVisibility(
                visible = mostrarProductos,
                enter = fadeIn() + slideInVertically { it }
            ) {
                LazyColumn {
                    items(productos) { producto ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {

                            producto.imagenUri?.let {
                                AsyncImage(
                                    model = it,
                                    contentDescription = "Imagen del producto",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(160.dp)
                                        .padding(bottom = 8.dp)
                                )
                            }

                            Text("${producto.nombre} - $${producto.precio}")
                            Text("Stock: ${producto.stock}")

                            if (usuario?.rol == Rol.CLIENTE) {
                                Button(
                                    onClick = {
                                        CarritoRepository.agregarProducto(producto)
                                        Toast.makeText(
                                            context,
                                            "Producto agregado al carrito",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                ) {
                                    Text("Agregar al carrito")
                                }
                            }

                            if (usuario?.rol == Rol.ADMIN) {

                                Button(
                                    onClick = {
                                        productoEditando = producto
                                        nombre = producto.nombre
                                        precio = producto.precio.toString()
                                        stock = producto.stock.toString()
                                        imagenSeleccionada =
                                            producto.imagenUri?.let { Uri.parse(it) }
                                    }
                                ) {
                                    Text("Editar")
                                }

                                Button(
                                    onClick = { viewModel.eliminarProducto(producto) }
                                ) {
                                    Text("Eliminar")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
