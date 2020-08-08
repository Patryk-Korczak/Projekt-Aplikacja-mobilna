package com.example.projektaplikacjamobilna

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog

class MainScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)
        supportActionBar?.hide()


        val userInfo: User = intent.getSerializableExtra("userData") as User
        val alertDialog =
            AlertDialog.Builder(this@MainScreen).create()
        alertDialog.setTitle("Zalogowano pomyślnie!")
        alertDialog.setButton(
            AlertDialog.BUTTON_NEUTRAL, "OK"
        ) { dialog, _ -> dialog.dismiss() }
        alertDialog.show()
    }
}
