package com.example.eval2_ordinaria_proyecto_prctica

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

data class UserInputs(
    var nombre: String = "",
    var apellidos: String = "",
    var localidad: String = "",
    var fechaNacimiento: String = "",
    var descripcion: String = ""
)

class InformacionExtraUsuario : AppCompatActivity() {
    companion object {
        var datosTemporales: UserInputs? = null
    }

    private lateinit var avatarImageView: ImageView
    private lateinit var usernameTextView: TextView
    private lateinit var nombreEditText: EditText
    private lateinit var apellidosEditText: EditText
    private lateinit var localidadEditText: EditText
    private lateinit var fechaEditText: EditText
    private lateinit var descripcionEditText: EditText
    private lateinit var backButton: Button
    private lateinit var saveButton: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_infouser)

        avatarImageView = findViewById(R.id.avatarImageView)
        usernameTextView = findViewById(R.id.UsuarioDeInfo)
        nombreEditText = findViewById(R.id.NombreInfo)
        apellidosEditText = findViewById(R.id.Apellidoinfo)
        localidadEditText = findViewById(R.id.localidadInfo)
        fechaEditText = findViewById(R.id.FechaInfo)
        descripcionEditText = findViewById(R.id.DescripcionInfo)
        backButton = findViewById(R.id.botonInfoAtrasAdicional)
        saveButton = findViewById(R.id.botonGuardar)

        val avatarId = intent.getIntExtra("photoId", R.drawable.perfil)
        avatarImageView.setImageResource(avatarId)

        datosTemporales?.let { datos ->
            nombreEditText.setText(datos.nombre)
            apellidosEditText.setText(datos.apellidos)
            localidadEditText.setText(datos.localidad)
            fechaEditText.setText(datos.fechaNacimiento)
            descripcionEditText.setText(datos.descripcion)
        }

        backButton.setOnClickListener {
            finish()
        }

        saveButton.setOnClickListener {
            datosTemporales = UserInputs(
                nombre = nombreEditText.text.toString(),
                apellidos = apellidosEditText.text.toString(),
                localidad = localidadEditText.text.toString(),
                fechaNacimiento = fechaEditText.text.toString(),
                descripcion = descripcionEditText.text.toString()
            )
            finish()
            Toast.makeText(this, "Datos guardados", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
