package com.example.projektaplikacjamobilna

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity



// LOGOWANIE

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonLogin = findViewById<Button>(R.id.buttonLogin)
        val textLogin = findViewById<EditText>(R.id.textLogin)
        val textPassword = findViewById<EditText>(R.id.textPassword)

        buttonLogin.setOnClickListener {
            val placeholderLogin = "test"
            val placeholderPassword = "test123"

            if(textLogin.text.toString() == placeholderLogin &&
                    textPassword.text.toString() == placeholderPassword) {
                val intent = Intent(this, MainScreen::class.java)
                startActivity(intent)
            }else{
                val alertDialog =
                        AlertDialog.Builder(this@MainActivity).create()
                alertDialog.setTitle("Uwaga!")
                alertDialog.setMessage("Podane dane są nieprawidłowe!")
                alertDialog.setButton(
                        AlertDialog.BUTTON_NEUTRAL, "OK"
                ) { dialog, _ -> dialog.dismiss() }
                alertDialog.show()
                textLogin.setText("")
                textPassword.setText("")

            }
        }
    }


}
