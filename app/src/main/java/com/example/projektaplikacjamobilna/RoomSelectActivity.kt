package com.example.projektaplikacjamobilna

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_room_select.*
import java.net.URL

class RoomSelectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_select)

        val receiveRoomsTask = GetRooms(DataHolder.selectedStock.Location_ID)
        receiveRoomsTask.execute().get()
        val apiAnswer = receiveRoomsTask.answer

        val gson = GsonBuilder().create()
        DataHolder.myRooms = gson.fromJson<ArrayList<Room>>(apiAnswer, object :
            TypeToken<ArrayList<Room>>(){}.type)

        val roomSpinner = findViewById<Spinner>(R.id.spinner2)
        val roomAdapter = ArrayAdapter<Room>(this@RoomSelectActivity, android.R.layout.simple_list_item_1, DataHolder.myRooms)
        roomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        roomSpinner.adapter = roomAdapter
    }

}


class GetRooms(Location_ID: Int) : AsyncTask<Void, Void, String>() {
    private val req = EndPoints.URL_GET_ROOMS + Location_ID
    lateinit var answer : String

    override fun doInBackground(vararg params: Void?): String? {
        answer = URL(req).readText()
        return answer
    }
}
