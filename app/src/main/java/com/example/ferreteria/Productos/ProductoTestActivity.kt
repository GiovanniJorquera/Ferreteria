package com.example.ferreteria.Productos

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.ferreteria.R
import com.example.ferreteria.repository.ProductoRepository

class ProductoTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_producto_test)

        probarProductos()
    }

    private fun probarProductos() {
        ProductoRepository.obtenerProductos().forEach {
            Log.d("PRODUCTO", it.toString())
        }
    }
}
