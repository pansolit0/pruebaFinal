package com.ceduc.comm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListarProductosActivity : AppCompatActivity() {

    private lateinit var db: SQLiteDB
    private lateinit var productosAdapter: ProductosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_productos)

        db = SQLiteDB(this)
        configurarRecyclerView()
    }

    private fun configurarRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewProductos)
        productosAdapter = ProductosAdapter(this, db.obtenerProductos())
        recyclerView.adapter = productosAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}
