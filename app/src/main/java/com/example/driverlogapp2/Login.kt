package com.example.driverlogapp2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText

class Login : AppCompatActivity() {
    private lateinit var editLogin: EditText;
    private lateinit var editPassword: EditText;
    private lateinit var buttonLogin: Button

    private val TrueLogin = "driver11"
    private val TruePassword = "123qwe"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        editLogin = findViewById(R.id.username)
        editPassword = findViewById(R.id.password)
        buttonLogin = findViewById(R.id.login_button)

        buttonLogin.setOnClickListener {
            val login = editLogin.text.toString()
            val password = editPassword.text.toString()

            // Проверка на пустые поля
            if (TextUtils.isEmpty(login) || TextUtils.isEmpty(password)) {
                Toast.makeText(this@Login, "Необходимо ввести логин и пароль", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            // Проверка на правильность ввода логина и пароля
            if (login == TrueLogin && password == TruePassword) {
                Toast.makeText(this@Login, "Вход выполнен", Toast.LENGTH_SHORT)
                    .show()

                // Смена окна и запоминание
                val intent = Intent(applicationContext, ProfileMenu::class.java)
                startActivity(intent)
                finish()

                return@setOnClickListener

            } else {
                Toast.makeText(this@Login, "Неправильные логин или пароль", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
        }
    }


}