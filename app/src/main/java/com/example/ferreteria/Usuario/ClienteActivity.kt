package com.example.ferreteria.Usuario

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.ferreteria.repository.CarritoRepository
import com.example.ferreteria.R
import com.example.ferreteria.Carrito.CarritoActivity
import com.example.ferreteria.Carrito.ItemCarrito

class ClienteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cliente)

        val btnAgregar = findViewById<Button>(R.id.btnAgregar)
        val btnVerCarrito = findViewById<Button>(R.id.btnVerCarrito)

        btnAgregar.setOnClickListener {
            CarritoRepository.carrito.agregarItem(
                ItemCarrito(1, "Martillo", 6000, 1)
            )
        }

        btnVerCarrito.setOnClickListener {
            startActivity(Intent(this, CarritoActivity::class.java))
        }
    }
}
