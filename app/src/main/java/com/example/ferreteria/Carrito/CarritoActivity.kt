package com.example.ferreteria.Carrito


import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.ferreteria.R
import com.example.ferreteria.repository.CarritoRepository

class CarritoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrito)

        val txtCarrito = findViewById<TextView>(R.id.txtCarrito)
        val txtTotal = findViewById<TextView>(R.id.txtTotal)

        val items = CarritoRepository.carrito.obtenerItems()

        txtCarrito.text = items.joinToString("\n") {
            "${it.nombre} x${it.cantidad} = ${it.precio * it.cantidad}"
        }

        txtTotal.text = "Total: ${CarritoRepository.carrito.calcularTotal()}"
    }
}
