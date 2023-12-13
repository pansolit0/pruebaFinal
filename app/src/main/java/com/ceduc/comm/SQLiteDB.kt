package com.ceduc.comm

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class SQLiteDB(context: Context) : SQLiteOpenHelper(context, "productosDB", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableStatement = """
            CREATE TABLE productos (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                descripcion TEXT,
                precio REAL
            )
        """.trimIndent()
        db?.execSQL(createTableStatement)
        val createCarritoTableStatement = """
            CREATE TABLE carrito (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                producto_id INTEGER,
                cantidad INTEGER,
                FOREIGN KEY (producto_id) REFERENCES productos(id)
            )
        """.trimIndent()
        db?.execSQL(createCarritoTableStatement)
    }

    @SuppressLint("Range")
    fun obtenerProductosEnCarrito(): List<Producto> {
        val listaProductosEnCarrito = mutableListOf<Producto>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT p.* FROM productos AS p INNER JOIN carrito AS c ON p.id = c.producto_id", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val descripcion = cursor.getString(cursor.getColumnIndex("descripcion"))
                val precio = cursor.getDouble(cursor.getColumnIndex("precio"))
                listaProductosEnCarrito.add(Producto(id, descripcion, precio))
            } while (cursor.moveToNext())
        }

        cursor.close()
        return listaProductosEnCarrito
    }



    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS productos")
        onCreate(db)
    }

    fun agregarProducto(descripcion: String, precio: Double): Boolean {
        val db = this.writableDatabase
        val cv = ContentValues().apply {
            put("descripcion", descripcion)
            put("precio", precio)
        }

        val result = db.insert("productos", null, cv)
        return result != (-1).toLong()
    }

    fun obtenerProductoPorId(id: Int): Cursor {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM productos WHERE id = ?", arrayOf(id.toString()))
    }

    @SuppressLint("Range")
    fun obtenerProductos(): List<Producto> {
        val listaProductos = mutableListOf<Producto>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM productos", null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val descripcion = cursor.getString(cursor.getColumnIndex("descripcion"))
                val precio = cursor.getDouble(cursor.getColumnIndex("precio"))
                listaProductos.add(Producto(id, descripcion, precio))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return listaProductos
    }

    fun actualizarProducto(id: Int, descripcion: String, precio: Double): Boolean {
        val db = this.writableDatabase
        val cv = ContentValues().apply {
            put("descripcion", descripcion)
            put("precio", precio)
        }

        val result = db.update("productos", cv, "id=?", arrayOf(id.toString()))
        return result > 0
    }

    fun borrarProducto(id: Int): Boolean {
        val db = this.writableDatabase
        val result = db.delete("productos", "id=?", arrayOf(id.toString()))
        return result > 0
    }
}