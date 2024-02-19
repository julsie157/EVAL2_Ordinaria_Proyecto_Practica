package com.example.eval2_ordinaria_proyecto_prctica

import ItemsAdapter
import android.content.Context
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
        setContentView(R.layout.layout_principal)

        userIcon = findViewById(R.id.userIcon)
        seriesIcon = findViewById(R.id.seriesIcon)
        moviesIcon = findViewById(R.id.moviesIcon)
        recyclerView = findViewById(R.id.recyclerViewItems)
        recyclerView.layoutManager = LinearLayoutManager(this)

        establecerAvatarInicial()

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

    override fun onResume() {
        super.onResume()
        actualizarAvatarUsuario()
    }

    private fun establecerAvatarInicial() {
        val sharedPreferences = getSharedPreferences("Perfil", Context.MODE_PRIVATE)
        if (!sharedPreferences.contains("photoId")) {
            userIcon.setImageResource(R.drawable.perfil)
        } else {
            val photoId = sharedPreferences.getInt("photoId", R.drawable.perfil)
            userIcon.setImageResource(photoId)
        }
    }

    private fun actualizarAvatarUsuario() {
        val sharedPreferences = getSharedPreferences("Perfil", Context.MODE_PRIVATE)
        val photoId = sharedPreferences.getInt("photoId", R.drawable.perfil)
        userIcon.setImageResource(photoId)
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
