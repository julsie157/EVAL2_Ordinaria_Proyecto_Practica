package com.example.eval2_ordinaria_proyecto_prctica

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class InfoActivity : AppCompatActivity() {

    private lateinit var imageViewPicture: ImageView
    private lateinit var textViewSinopsis: TextView
    private lateinit var botonPlayInfo: Button
    private lateinit var botonAtrasInfo: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_informacion)

        imageViewPicture = findViewById(R.id.imageViewPicture)
        textViewSinopsis = findViewById(R.id.textViewSinopsis)
        botonPlayInfo = findViewById(R.id.botonPlayInfo)
        botonAtrasInfo = findViewById(R.id.botonAtrasInfo)



        botonPlayInfo.setOnClickListener {

        }


        botonAtrasInfo.setOnClickListener {

            finish()
        }
    }
}