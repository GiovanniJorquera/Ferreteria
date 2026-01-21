package com.example.ferreteria

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.ferreteria.view.ProductosScreen
import com.example.ferreteria.ui.theme.FerreteriaTheme
import com.example.ferreteria.viewmodel.ProductoViewModel

class MainActivity : ComponentActivity() {
    private val productoViewModel: ProductoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            FerreteriaTheme {
                ProductosScreen(productoViewModel)
                }
            }
        }
    }



