package com.ceduc.comm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CarritoActivity : AppCompatActivity() {

    private lateinit var productosAdapter: ProductosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrito)


        val db = SQLiteDB(this)
        val productosEnCarrito = db.obtenerProductos()

        mostrarProductosEnCarrito(productosEnCarrito)
        configurarBotones()
    }

    private fun mostrarProductosEnCarrito(productosEnCarrito: List<Producto>) {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewCarrito)
        productosAdapter = ProductosAdapter(this, productosEnCarrito)
        recyclerView.adapter = productosAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun configurarBotones() {
        val pagarButton = findViewById<Button>(R.id.pagarButton)
        pagarButton.setOnClickListener {
            // Lógica para el proceso de pago (aunque sea solo visual)
        }

        val volverMenuButton = findViewById<Button>(R.id.volverMenuButton)
        volverMenuButton.setOnClickListener {
            finish() // Vuelve a MainActivity
        }
    }
}
