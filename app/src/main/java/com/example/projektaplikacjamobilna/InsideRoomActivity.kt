package com.example.projektaplikacjamobilna

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView

class InsideRoomActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inside_room)
        supportActionBar?.hide()

        val qrButton = findViewById<Button>(R.id.QrButton)
        val noteButton = findViewById<Button>(R.id.NoteButton)
        val nextButton = findViewById<Button>(R.id.NextButton)
        val amountButton = findViewById<Button>(R.id.AmountButton)
        val missingItemButton = findViewById<Button>(R.id.MissingItemButton)
        val itemName = findViewById<TextView>(R.id.currentItemName)
        val itemImage = findViewById<ImageView>(R.id.ItemBmp)

        amountButton.isEnabled = false
        nextButton.isEnabled = false
        missingItemButton.isEnabled = true
        qrButton.isEnabled = true

        DataHolder.currentID = 0

        //UI update start
        itemName.text = DataHolder.tempItems[DataHolder.currentID].Item_Name
        var decodedString = Base64.decode(DataHolder.tempItems[DataHolder.currentID].Item_Image, Base64.DEFAULT)
        var createdImage = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        itemImage.setImageBitmap(createdImage)
        //UI update finish

        //buttons listeners

        qrButton.setOnClickListener {
            val expectedValue = getItemQrCode(DataHolder.tempItems[DataHolder.currentID])
            val intent = Intent(this, QrScannerActivity::class.java)
            intent.putExtra("expectedValue", expectedValue)
            startActivityForResult(intent, 0)
            if (DataHolder.tempItems[DataHolder.currentID].IsChecked == 1) {
                missingItemButton.isEnabled = false
                qrButton.isEnabled = false
                if (DataHolder.tempItems[DataHolder.currentID].Sys_Amount == 1) {
                    nextButton.isEnabled = true
                    DataHolder.tempItems[DataHolder.currentID].Real_Amount = 1
                } else {
                    amountButton.isEnabled = true
                }
            }
        }


        noteButton.setOnClickListener {
            //popup z miejscem na dodanie notatki, zapisanie notatki do danych
        }

        nextButton.setOnClickListener {
            if(DataHolder.currentID == DataHolder.tempItems.size - 1){
                //Update itemów z tego pokoju (temp items) do bazy

                val intent = Intent(this, RoomSelectActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            }else {
                DataHolder.currentID++
                itemName.text = DataHolder.tempItems[DataHolder.currentID].Item_Name
                decodedString = Base64.decode(
                    DataHolder.tempItems[DataHolder.currentID].Item_Image,
                    Base64.DEFAULT)
                createdImage = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
                itemImage.setImageBitmap(createdImage)

                amountButton.isEnabled = false
                nextButton.isEnabled = false
                missingItemButton.isEnabled = true
                qrButton.isEnabled = true
            }

        }

        amountButton.setOnClickListener {
            //Pop up z wyborem liczby, zapisanie liczby do real_amount

            //placeholder code
            DataHolder.tempItems[DataHolder.currentID].Real_Amount = 1

            //placeholder code end

            nextButton.isEnabled = true
        }

        missingItemButton.setOnClickListener {
            DataHolder.tempItems[DataHolder.currentID].IsChecked = 1
            DataHolder.tempItems[DataHolder.currentID].Real_Amount = 0
            qrButton.isEnabled = false
            amountButton.isEnabled = false
            nextButton.isEnabled = true
        }


    }

    public fun getItemQrCode(v: Item): String{
        /*
        Tutaj powinien znajdować się kod odpowiadający za odczytanie kodu QR z bloba.
         */
        return "test"
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 0 && resultCode == 0){
            DataHolder.tempItems[DataHolder.currentID].IsChecked = 1
        }else{
            //Popup o błędzie
        }
    }

}
