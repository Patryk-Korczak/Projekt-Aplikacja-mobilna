package com.example.projektaplikacjamobilna

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import java.net.URL


// LOGOWANIE

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonLogin = findViewById<Button>(R.id.buttonLogin)
        val textLogin = findViewById<EditText>(R.id.textLogin)
        val textPassword = findViewById<EditText>(R.id.textPassword)
        var user : User
        var gson = Gson()
        buttonLogin.setOnClickListener {
            val loginTask = GetUserData(textLogin.text.toString())
            loginTask.execute().get()
            val apiResponse = loginTask.answer
            if(apiResponse == "Error"){
                val alertDialog =
                    AlertDialog.Builder(this@MainActivity).create()
                alertDialog.setTitle("Uwaga!")
                alertDialog.setMessage("Błąd podczas łączenia z bazą danych!")
                alertDialog.setButton(
                    AlertDialog.BUTTON_NEUTRAL, "OK"
                ) { dialog, _ -> dialog.dismiss() }
                alertDialog.show()
                textLogin.setText("")
                textPassword.setText("")
            }else{
                user = gson.fromJson(apiResponse, User::class.java)
                if(textLogin.text.toString() == user.UserLogin &&
                    textPassword.text.toString() == user.UserPassword) {
                    val intent = Intent(this, MainScreen::class.java)
                    startActivity(intent)
                }else{
                    val alertDialog =
                        AlertDialog.Builder(this@MainActivity).create()
                    alertDialog.setTitle("Uwaga!")
                    alertDialog.setMessage("Błędne dane logowania!")
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


}

class GetUserData(val login : String) : AsyncTask<Void, Void, String>() {
    val req = EndPoints.URL_GET_USER + login
    lateinit var answer : String

    override fun doInBackground(vararg params: Void?): String? {
        answer = URL(req).readText()
        if(answer == ""){
            answer = "Error"
        }
        return answer
    }
}



