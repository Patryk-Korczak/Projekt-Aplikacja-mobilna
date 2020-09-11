package com.example.projektaplikacjamobilna

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
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


        val enterRoomButton = findViewById<Button>(R.id.enterRoomButton)
        enterRoomButton.setOnClickListener {
            DataHolder.selectedRoom = getSelectedRoom(roomSpinner)
            val intent = Intent(this, QrScannerActivity::class.java)
            intent.putExtra("expectedValue", getRoomQrCode(getSelectedRoom(roomSpinner)))
            startActivityForResult(intent, 0)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 0 && resultCode == 0){
            val receiveRoomItemsTask = GetRoomItems(DataHolder.selectedStock.Stock_ID, DataHolder.selectedRoom.Room_ID)
            receiveRoomItemsTask.execute().get()
            val gson = GsonBuilder().create()
            val apiAnswer = receiveRoomItemsTask.answer
            DataHolder.tempItems = gson.fromJson<ArrayList<Item>>(apiAnswer, object : TypeToken<ArrayList<Item>>(){}.type)
            val intent = Intent(this, InsideRoomActivity::class.java)
            startActivity(intent)
        }else{
            val alertDialog =
                AlertDialog.Builder(this@RoomSelectActivity).create()
            alertDialog.setTitle("Uwaga!")
            alertDialog.setMessage("Zeskanowano niepoprawny kod QR, spróbuj ponownie.")
            alertDialog.setButton(
                AlertDialog.BUTTON_NEUTRAL, "OK"
            ) { dialog, _ -> dialog.dismiss() }
            alertDialog.show()
        }
    }

    public fun getSelectedRoom(v: Spinner): Room {
        return v.selectedItem as Room
    }

    public fun getRoomQrCode(v: Room): String{
        /*
        Tutaj powinien znajdować się kod odpowiadający za odczytanie kodu QR z bloba.
         */
        return "test"
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

class GetRoomItems(Stock_ID: Int, Room_ID: Int) : AsyncTask<Void, Void, String>() {
    private val req = EndPoints.URL_GET_ROOM_ITEMS + Stock_ID.toString() + "&Room_ID=" + Room_ID.toString()
    lateinit var answer : String

    override fun doInBackground(vararg params: Void?): String? {
        answer = URL(req).readText()
        return answer
    }
}
