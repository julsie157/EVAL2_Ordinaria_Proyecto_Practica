package com.example.eval2_ordinaria_proyecto_prctica

import ItemsAdapter
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException
import java.nio.charset.Charset
data class Item(val id: Int, val nombre: String, val sinopsis: String, val imagen: String, val trailer: String)

class PrincipalActivity : AppCompatActivity() {

    private lateinit var userIcon: ImageView
    private lateinit var seriesIcon: ImageView
    private lateinit var moviesIcon: ImageView
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)

        userIcon = findViewById(R.id.userIcon)
        seriesIcon = findViewById(R.id.seriesIcon)
        moviesIcon = findViewById(R.id.moviesIcon)
        recyclerView = findViewById(R.id.recyclerViewItems)
        recyclerView.layoutManager = LinearLayoutManager(this)

        userIcon.setOnClickListener {
            val intent = Intent(this, PerfilActivity::class.java)
            startActivity(intent)
        }

        seriesIcon.setOnClickListener {
            cargarDatos("SeriesApp.txt")
        }

        moviesIcon.setOnClickListener {
            cargarDatos("PeliculasApp.txt")
        }
    }

    private fun cargarDatos(filename: String) {
        val json = loadJSONFromAsset(filename)
        json?.let {
            val items = parseItems(it)
            recyclerView.adapter = ItemsAdapter(items, this)
        }
    }

    private fun loadJSONFromAsset(filename: String): String? {
        return try {
            val inputStream = assets.open(filename)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, Charset.forName("UTF-8"))
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
    }

    private fun parseItems(json: String): List<Item> {
        val type = object : TypeToken<List<Item>>() {}.type
        return Gson().fromJson(json, type)
    }

}
