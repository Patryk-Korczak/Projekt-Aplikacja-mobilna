package com.example.projektaplikacjamobilna

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main_screen.*
import java.net.URL

class MainScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)
        supportActionBar?.hide()


        val receiveNamesTask = GetStockNames()
        receiveNamesTask.execute().get()
        var apiAnswer = receiveNamesTask.answer
        val gson = GsonBuilder().create()
        val stockList = gson.fromJson<ArrayList<String>>(apiAnswer, object :
            TypeToken<ArrayList<String>>(){}.type)


        val userInfo: User = intent.getSerializableExtra("userData") as User
        val alertDialog =
            AlertDialog.Builder(this@MainScreen).create()
        alertDialog.setTitle("Zalogowano pomyślnie!")
        alertDialog.setButton(
            AlertDialog.BUTTON_NEUTRAL, "OK"
        ) { dialog, _ -> dialog.dismiss() }
        alertDialog.show()

        val welcomeMessage = findViewById<TextView>(R.id.message1)
        val welcomeText = "Witaj " + userInfo.Imie +"!\n" + "Poziom uprawnień: " + userInfo.User_Type
        welcomeMessage.text = welcomeText

        val mySpinner = findViewById<Spinner>(R.id.spinner)
        val myAdapter = ArrayAdapter<String>(this@MainScreen, android.R.layout.simple_list_item_1, stockList)
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = myAdapter
    }
}

class GetStockNames() : AsyncTask<Void, Void, String>() {
    val req = EndPoints.URL_GET_STOCK_NAMES
    lateinit var answer : String

    override fun doInBackground(vararg params: Void?): String? {
        answer = URL(req).readText()
        return answer
    }
}
