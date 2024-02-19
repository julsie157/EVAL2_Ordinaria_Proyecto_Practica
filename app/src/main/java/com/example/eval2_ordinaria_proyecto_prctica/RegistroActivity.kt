package com.example.eval2_ordinaria_proyecto_prctica

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.IOException

class RegistroActivity : AppCompatActivity() {

    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextUser: EditText
    private lateinit var buttonConfirmar: Button
    private lateinit var buttonAtras: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_registro)


        editTextEmail = findViewById(R.id.editTextEmailRegister)
        editTextPassword = findViewById(R.id.editTextPasswordRegister)
        editTextUser = findViewById(R.id.editTextUserRegister)
        buttonConfirmar = findViewById(R.id.buttonConfirmar)
        buttonAtras = findViewById(R.id.botonAtrasReggistro)

        buttonConfirmar.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            val username = editTextUser.text.toString()

            if (formaValida(email, password, username)) {
                guardarInfo(username, email, password)
            }
        }

        buttonAtras.setOnClickListener {
            finish()
        }

    }

    private fun formaValida(email: String, password: String, username: String): Boolean {
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Ingrese un correo electrónico válido.", Toast.LENGTH_SHORT).show()
            return false
        }

        if (password.length < 6 || !password.any { it.isDigit() } || !password.any { it.isUpperCase() }) {
            Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres, una mayúscula y un número.", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun guardarInfo(username: String, email: String, password: String) {
        val userInfo = "$username,$email,$password\n"
        try {
            openFileOutput("FicheroUsuarios.txt", Context.MODE_APPEND).use { fos ->
                fos.write(userInfo.toByteArray())
            }
            Toast.makeText(this, "Registro exitoso.", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, "Error al guardar los datos del usuario.", Toast.LENGTH_SHORT).show()
        }
    }
}
