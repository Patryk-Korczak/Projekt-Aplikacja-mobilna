package com.example.projektaplikacjamobilna

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

class InventoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventory)
        supportActionBar?.hide()



        val alertDialog =
            AlertDialog.Builder(this@InventoryActivity).create()
        alertDialog.setTitle("Uwaga!")
        alertDialog.setMessage("Trwa przygotowywanie inwentaryzacji...")
        alertDialog.setButton(
            AlertDialog.BUTTON_NEUTRAL, "OK"
        ) { dialog, _ -> dialog.dismiss() }
        alertDialog.show()

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

        buttonYes.setOnClickListener{

        }

        buttonNo.setOnClickListener{

        }
    }
}
