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

        editTextUsername = findViewById(R.id.editTextUserLogin)
        editTextPassword = findViewById(R.id.editTextPassLogin)
        buttonLogin = findViewById(R.id.buttonLogin)
        textViewRegister = findViewById(R.id.textViewRegister)



        buttonLogin.setOnClickListener {
            val username = editTextUsername.text.toString().trim()
            val password = editTextPassword.text.toString().trim()
            when (val loginResult = loginUser(username, password)) {
                LoginResult.SUCCESS -> {
                    val intent = Intent(this, PrincipalActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                LoginResult.USER_NOT_FOUND -> {
                    Toast.makeText(this, "Usuario incorrecto.", Toast.LENGTH_SHORT).show()
                }
                LoginResult.INCORRECT_PASSWORD -> {
                    Toast.makeText(this, "Contraseña incorrecta.", Toast.LENGTH_SHORT).show()
                }
                LoginResult.ERROR -> {
                    Toast.makeText(this, "Error interno", Toast.LENGTH_SHORT).show()
                }

                else -> {}
            }
        }

        textViewRegister.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser(username: String, password: String): LoginResult {
        try {
            openFileInput("Usuarios.txt").use { inputStream ->
                InputStreamReader(inputStream).use { isr ->
                    BufferedReader(isr).use { reader ->
                        var line: String?
                        var userFound = false
                        while (reader.readLine().also { line = it } != null) {
                            val userInfo = line!!.split(",")
                            if (userInfo.size >= 3 && userInfo[0] == username) {
                                userFound = true
                                if (userInfo[2].trim() == password) {
                                    val sharedPref = getSharedPreferences("UserInfo", MODE_PRIVATE)
                                    with(sharedPref.edit()) {
                                        putString("currentUser", username)
                                        apply()
                                    }
                                    return LoginResult.SUCCESS
                                } else {
                                    return LoginResult.INCORRECT_PASSWORD
                                }
                            }
                        }
                        return if (userFound) LoginResult.INCORRECT_PASSWORD else LoginResult.USER_NOT_FOUND
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return LoginResult.ERROR
        }
    }

    enum class LoginResult {
        SUCCESS,
        USER_NOT_FOUND,
        INCORRECT_PASSWORD,
        ERROR
    }

}
