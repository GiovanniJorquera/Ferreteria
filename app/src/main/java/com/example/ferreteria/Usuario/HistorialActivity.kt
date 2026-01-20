package com.example.ferreteria.Usuario

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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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

        val lst = findViewById<ListView>(R.id.lstHistorial)
        val btnVolver = findViewById<Button>(R.id.btnVolver)

        val pedidos = PedidoRepository.obtenerPedidosPorUsuario(usuario.id)
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

        val textos = if (pedidos.isEmpty()) {
            listOf("Aún no tienes compras registradas.")
        } else {
            pedidos.map { p ->
                "Pedido #${p.id}  |  ${sdf.format(Date(p.fechaMillis))}  |  Total: $${p.total}"
            }
        }

        lst.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, textos)

        // Solo si hay pedidos reales: muestra detalle al tocar
        if (pedidos.isNotEmpty()) {
            lst.setOnItemClickListener { _, _, position, _ ->
                val pedido = pedidos[position]
                val detalle = pedido.items.joinToString("\n") {
                    "${it.nombre} x${it.cantidad} = $${it.precio * it.cantidad}"
                }

                AlertDialog.Builder(this)
                    .setTitle("Detalle Pedido #${pedido.id}")
                    .setMessage(detalle + "\n\nTotal: $${pedido.total}")
                    .setPositiveButton("OK", null)
                    .show()
            }
        }

        btnVolver.setOnClickListener { finish() }
    }
}
