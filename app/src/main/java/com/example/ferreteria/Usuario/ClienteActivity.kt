package com.example.ferreteria.Usuario

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ferreteria.Carrito.CarritoActivity
import com.example.ferreteria.Productos.Producto

import com.example.ferreteria.R
import com.example.ferreteria.repository.ProductoRepository
import com.example.ferreteria.repository.UsuarioRepository
import kotlin.jvm.java

class ClienteActivity : AppCompatActivity() {

    private lateinit var lstProductos: ListView
    private var productos: List<Producto> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cliente)

        val usuario = UsuarioRepository.usuarioActual
        if (usuario == null) {
            Toast.makeText(this, "Debes iniciar sesión", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        findViewById<TextView>(R.id.txtBienvenida).text = "Bienvenido, ${usuario.nombre}"

        lstProductos = findViewById(R.id.lstProductos)
        val btnVerCarrito = findViewById<Button>(R.id.btnVerCarrito)
        val btnVerHistorial = findViewById<Button>(R.id.btnVerHistorial)
        val btnLogout = findViewById<Button>(R.id.btnLogout)

        renderProductos()

        lstProductos.setOnItemClickListener { _, _, position, _ ->
            val producto = productos[position]
            val i = Intent(this, ProductoDetalleActivity::class.java)
            i.putExtra(ProductoDetalleActivity.EXTRA_PRODUCTO_ID, producto.id)
            startActivity(i)
        }

        btnVerCarrito.setOnClickListener {
            startActivity(Intent(this, CarritoActivity::class.java))
        }

        btnVerHistorial.setOnClickListener {
            startActivity(Intent(this, HistorialActivity::class.java))
        }

        btnLogout.setOnClickListener {
            UsuarioRepository.logout()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        renderProductos()
    }

    private fun renderProductos() {
        productos = ProductoRepository.obtenerProductos()
        val textos = productos.map { p ->
            "#${p.id}  ${p.nombre}  | $${p.precio}  | Stock: ${p.stock}"
        }
        lstProductos.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, textos)
    }
}
