package com.example.projektaplikacjamobilna

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.net.URL

class InventoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventory)
        supportActionBar?.hide()



        val alertDialog =
            AlertDialog.Builder(this@InventoryActivity).create()
        alertDialog.setTitle("Uwaga!")
        alertDialog.setMessage("Trwa przygotowywanie inwentaryzacji...")
        alertDialog.show()

        val receiveItemsTask = GetItems()
        receiveItemsTask.execute().get()
        val apiAnswer = receiveItemsTask.answer

        val gson = GsonBuilder().create()
        val itemList = gson.fromJson<ArrayList<Item>>(apiAnswer, object : TypeToken<ArrayList<Item>>(){}.type)

        alertDialog.hide()

        var currentID = 0

        val userInfo: User = intent.getSerializableExtra("userData") as User
        val tableName = intent.getStringExtra("tableName")
        val textInventory = findViewById<TextView>(R.id.textView)
        val textName = findViewById<TextView>(R.id.textView3)
        val textLocation = findViewById<TextView>(R.id.textView4)
        val buttonYes = findViewById<Button>(R.id.button)
        val buttonNo = findViewById<Button>(R.id.button3)
        val imageItem = findViewById<ImageView>(R.id.imageView2)
        textInventory.text = tableName
        textName.text = itemList[currentID].Item_Name
        val temp = itemList[currentID].Location_Name + " : " + itemList[currentID].Room_Name
        textLocation.text = temp

        buttonYes.setOnClickListener{

        }

        buttonNo.setOnClickListener{

        }
    }
}

class GetItems : AsyncTask<Void, Void, String>() {
    private val req = EndPoints.URL_GET_ITEMS
    lateinit var answer : String

    override fun doInBackground(vararg params: Void?): String? {
        answer = URL(req).readText()
        return answer
    }
}
