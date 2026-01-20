package com.example.ferreteria.Carrito

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ferreteria.R
import com.example.ferreteria.repository.CarritoRepository
import com.example.ferreteria.repository.PedidoRepository
import com.example.ferreteria.repository.UsuarioRepository

class CarritoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrito)

        val lstCarrito = findViewById<ListView>(R.id.lstCarrito)
        val txtTotal = findViewById<TextView>(R.id.txtTotal)
        val btnConfirmar = findViewById<Button>(R.id.btnConfirmar)
        val btnVolver = findViewById<Button>(R.id.btnVolver)

        val items = CarritoRepository.obtenerItems()

        if (items.isEmpty()) {
            Toast.makeText(this, "El carrito está vacío", Toast.LENGTH_SHORT).show()
        }

        val textos = items.map {
            "${it.nombre} x${it.cantidad} = $${it.precio * it.cantidad}"
        }

        lstCarrito.adapter =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, textos)

        txtTotal.text = "Total: $${CarritoRepository.total()}"

        btnConfirmar.setOnClickListener {

            val usuario = UsuarioRepository.usuarioActual
            if (usuario == null) {
                Toast.makeText(this, "Debes iniciar sesión", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val items = CarritoRepository.obtenerItems()
            if (items.isEmpty()) {
                Toast.makeText(this, "El carrito está vacío", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val itemsPedido = items.map { it.copy() }

            PedidoRepository.crearPedido(
                usuarioId = usuario.id,
                items = itemsPedido,
                total = CarritoRepository.total()
            )

            CarritoRepository.vaciarCarrito()

            Toast.makeText(this, "Compra realizada", Toast.LENGTH_SHORT).show()
            finish()
        }


        btnVolver.setOnClickListener {
            finish()
        }
    }
}
