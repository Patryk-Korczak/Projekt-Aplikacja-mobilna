package com.example.projektaplikacjamobilna

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.Serializable
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()


        val buttonLogin = findViewById<Button>(R.id.buttonLogin)
        val textLogin = findViewById<EditText>(R.id.textLogin)
        val textPassword = findViewById<EditText>(R.id.textPassword)
        var userInfo : User
        val gson = Gson()

        val receiveLocationsTask = GetLocations()
        receiveLocationsTask.execute().get()
        DataHolder.myLocations = gson.fromJson<ArrayList<Location>>(receiveLocationsTask.answer, object :
            TypeToken<ArrayList<Location>>(){}.type)


        buttonLogin.setOnClickListener {
            val loginTask = GetUserData(textLogin.text.toString())
            loginTask.execute().get()
            val apiResponse = loginTask.answer
            if(apiResponse == "\"Error2\""){
                val alertDialog =
                    AlertDialog.Builder(this@MainActivity).create()
                alertDialog.setTitle("Uwaga!")
                alertDialog.setMessage("Użytkownik o podanym loginie nie istnieje!")
                alertDialog.setButton(
                    AlertDialog.BUTTON_NEUTRAL, "OK"
                ) { dialog, _ -> dialog.dismiss() }
                alertDialog.show()
                textLogin.setText("")
                textPassword.setText("")
            }else{
                userInfo = gson.fromJson(apiResponse, User::class.java)
                DataHolder.myUser = userInfo
                if(textLogin.text.toString() == userInfo.UserLogin &&
                    textPassword.text.toString() == userInfo.UserPassword) {
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

class GetUserData(login : String) : AsyncTask<Void, Void, String>() {
    private val req = EndPoints.URL_GET_USER + login
    lateinit var answer : String

    override fun doInBackground(vararg params: Void?): String? {
        answer = URL(req).readText()
        return answer
    }
}

class GetLocations() : AsyncTask<Void, Void, String>() {
    private val req = EndPoints.URL_GET_LOCATIONS
    lateinit var answer : String

    override fun doInBackground(vararg params: Void?): String? {
        answer = URL(req).readText()
        return answer
    }
}



