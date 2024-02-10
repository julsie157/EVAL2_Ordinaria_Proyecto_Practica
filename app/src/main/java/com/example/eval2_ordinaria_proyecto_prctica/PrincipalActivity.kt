package com.example.eval2_ordinaria_proyecto_prctica

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity


class PrincipalActivity : AppCompatActivity() {

    private lateinit var userIcon: ImageView
    private lateinit var seriesIcon: ImageView
    private lateinit var moviesIcon: ImageView
    private lateinit var scrollView: ScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)

        userIcon = findViewById(R.id.userIcon)
        seriesIcon = findViewById(R.id.seriesIcon)
        moviesIcon = findViewById(R.id.moviesIcon)
        scrollView = findViewById(R.id.scrollView)


        userIcon.setOnClickListener {
            val intent = Intent(this, PerfilActivity::class.java)
            startActivity(intent)
        }


        seriesIcon.setOnClickListener {

        }


        moviesIcon.setOnClickListener {

        }


    }
}
