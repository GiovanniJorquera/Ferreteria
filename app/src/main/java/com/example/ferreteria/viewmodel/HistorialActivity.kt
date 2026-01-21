package com.example.ferreteria.viewmodel

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.ferreteria.R
import com.example.ferreteria.repository.PedidoRepository
import com.example.ferreteria.repository.UsuarioRepository

class HistorialActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historial)

        val usuario = UsuarioRepository.usuarioActual
        if (usuario == null) {
            Toast.makeText(this, "Debes iniciar sesión", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val lstHistorial = findViewById<ListView>(R.id.lstHistorial)
        val btnVolver = findViewById<Button>(R.id.btnVolver)

        val pedidos = PedidoRepository.obtenerPedidosPorUsuario(usuario.id)

        val textos = if (pedidos.isEmpty()) {
            listOf("No tienes compras registradas")
        } else {
            pedidos.map {
                "Pedido #${it.id} | Total: $${it.total}"
            }
        }

        lstHistorial.adapter =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, textos)

        if (pedidos.isNotEmpty()) {
            lstHistorial.setOnItemClickListener { _, _, position, _ ->

                val pedido = pedidos[position]

                val detalle = pedido.items.joinToString("\n") {
                    "${it.nombre} x${it.cantidad} = $${it.precio * it.cantidad}"
                }

                AlertDialog.Builder(this)
                    .setTitle("Pedido #${pedido.id}")
                    .setMessage(
                        detalle + "\n\nTotal: $${pedido.total}"
                    )
                    .setPositiveButton("OK", null)
                    .show()
            }
        }

        btnVolver.setOnClickListener {
            finish()
        }
    }
}