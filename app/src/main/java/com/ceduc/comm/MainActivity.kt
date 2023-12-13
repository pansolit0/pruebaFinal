package com.ceduc.comm

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configurarBotonesProducto()
        configurarBotonVerCarrito()
        configurarBotonListar()
    }

    private fun configurarBotonesProducto() {
        val button1 = findViewById<ImageButton>(R.id.button1)
        button1.setOnClickListener { abrirProductoActivity(1) } // ID para el producto 1

        val button2 = findViewById<ImageButton>(R.id.button2)
        button2.setOnClickListener { abrirProductoActivity(2) } // ID para el producto 2

        val button3 = findViewById<ImageButton>(R.id.button3)
        button3.setOnClickListener { abrirProductoActivity(3) } // ID para el producto 3

        val button4 = findViewById<ImageButton>(R.id.button4)
        button4.setOnClickListener { abrirProductoActivity(4) } // ID para el producto 4
    }


    private fun configurarBotonVerCarrito() {
        val verCarritoButton = findViewById<Button>(R.id.button5)
        verCarritoButton.setOnClickListener {
            val intent = Intent(this, CarritoActivity::class.java)
            startActivity(intent)
        }
    }

    private fun configurarBotonListar() {
        val listarButton = findViewById<Button>(R.id.button6)
        listarButton.setOnClickListener {
            val intent = Intent(this, ListarProductosActivity::class.java)
            startActivity(intent)
        }
    }


    private fun abrirProductoActivity(productId: Int) {
        val intent = Intent(this, ProductoActivity::class.java)
        intent.putExtra("PRODUCTO_ID", productId)
        startActivity(intent)
    }
}