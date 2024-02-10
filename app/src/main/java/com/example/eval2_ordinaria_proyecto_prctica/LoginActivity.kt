package com.example.eval2_ordinaria_proyecto_prctica

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.InputStreamReader

class LoginActivity : AppCompatActivity() {

    private lateinit var editTextUsername: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button
    private lateinit var textViewRegister: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        editTextUsername = findViewById(R.id.editTextUserLogin) // Cambia el ID si es necesario
        editTextPassword = findViewById(R.id.editTextPassLogin)
        buttonLogin = findViewById(R.id.buttonLogin)
        textViewRegister = findViewById(R.id.textViewRegister)

        buttonLogin.setOnClickListener {
            val username = editTextUsername.text.toString().trim()
            val password = editTextPassword.text.toString().trim()
            if (loginUser(username, password)) {
                val intent = Intent(this, PrincipalActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Autenticación fallida.", Toast.LENGTH_SHORT).show()
            }
        }

        textViewRegister.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser(username: String, password: String): Boolean {
        try {
            openFileInput("Usuarios.txt").use { inputStream ->
                InputStreamReader(inputStream).use { isr ->
                    BufferedReader(isr).use { reader ->
                        var line: String?
                        while (reader.readLine().also { line = it } != null) {
                            val userInfo = line!!.split(",")
                            if (userInfo.size >= 3 && userInfo[0] == username && userInfo[2].trim() == password) {
                                val sharedPref = getSharedPreferences("UserInfo", MODE_PRIVATE)
                                with(sharedPref.edit()) {
                                    putString("currentUser", username)
                                    apply()
                                }
                                return true
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error al leer el archivo de usuarios.", Toast.LENGTH_SHORT).show()
        }
        return false
    }

}
