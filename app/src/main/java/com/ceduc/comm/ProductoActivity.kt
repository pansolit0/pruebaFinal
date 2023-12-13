package com.ceduc.comm

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class ProductoActivity : AppCompatActivity() {

    private lateinit var db: SQLiteDB
    private var productoId: Int = 0
    private lateinit var descripcionEditText: EditText
    private lateinit var precioEditText: EditText

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_producto)

        db = SQLiteDB(this)
        productoId = intent.getIntExtra("PRODUCTO_ID", 0)

        descripcionEditText = findViewById(R.id.descripcionEditText)
        precioEditText = findViewById(R.id.precioEditText)

        if (productoId != 0) {
            cargarDetallesProducto(productoId)
        }

        configurarBotones()
    }

    @SuppressLint("Range")
    private fun cargarDetallesProducto(id: Int) {
        val cursor = db.obtenerProductoPorId(id)
        if (cursor.moveToFirst()) {
            val descripcion = cursor.getString(cursor.getColumnIndex("descripcion"))
            val precio = cursor.getDouble(cursor.getColumnIndex("precio"))

            descripcionEditText.setText(descripcion)
            precioEditText.setText(precio.toString())
        }
        cursor.close()
    }

    private fun configurarBotones() {
        val agregarButton = findViewById<Button>(R.id.agregarCarritoButton)
        agregarButton.setOnClickListener {
            val descripcion = descripcionEditText.text.toString()
            val precio = precioEditText.text.toString().toDouble()
            val agregado = db.agregarProducto(descripcion, precio)
            if (agregado) {
                Toast.makeText(this, "Producto agregado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Error al agregar producto", Toast.LENGTH_SHORT).show()
            }
        }

        val actualizarButton = findViewById<Button>(R.id.actualizarButton)
        actualizarButton.setOnClickListener {
            val descripcion = descripcionEditText.text.toString()
            val precio = precioEditText.text.toString().toDouble()
            val actualizado = db.actualizarProducto(productoId, descripcion, precio)
            if (actualizado) {
                Toast.makeText(this, "Producto actualizado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Error al actualizar producto", Toast.LENGTH_SHORT).show()
            }
        }

        val eliminarButton = findViewById<Button>(R.id.eliminarButton)
        eliminarButton.setOnClickListener {
            val eliminado = db.borrarProducto(productoId)
            if (eliminado) {
                Toast.makeText(this, "Producto eliminado", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Error al eliminar producto", Toast.LENGTH_SHORT).show()
            }
        }

        val volverButton = findViewById<Button>(R.id.volverButton)
        volverButton.setOnClickListener {
            finish() // Cierra la actividad actual y vuelve a la anterior en la pila
        }
    }
}

