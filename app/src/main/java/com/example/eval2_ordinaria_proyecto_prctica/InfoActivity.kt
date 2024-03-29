package com.example.eval2_ordinaria_proyecto_prctica


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide


class InfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_infoelementos)


        val nombre = intent.getStringExtra("nombre") ?: ""
        val sinopsis = intent.getStringExtra("sinopsis") ?: ""
        val imagen = intent.getStringExtra("imagen")
        val trailer = intent.getStringExtra("trailer")


        val textViewNombre: TextView = findViewById(R.id.textViewNombre)
        val textViewSinopsis: TextView = findViewById(R.id.textViewSinopsis)
        val imageViewImagen: ImageView = findViewById(R.id.imageViewPicture)
        val buttonTrailer: Button = findViewById(R.id.botonPlayInfo)
        val buttonAtras: Button = findViewById(R.id.botonAtrasInfo)

        textViewNombre.text = nombre
        textViewSinopsis.text = sinopsis
        if (imagen != null) {
            Glide.with(this).load(imagen).into(imageViewImagen)
        }


        buttonTrailer.setOnClickListener {
            trailer?.let {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                startActivity(intent)
            }
        }

        buttonAtras.setOnClickListener {
            finish()
        }
    }
}


