package com.example.projektaplikacjamobilna

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Base64
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import java.net.URL

class InsideRoomActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inside_room)
        supportActionBar?.hide()
        DataHolder.currentID = 0
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
        }


        noteButton.setOnClickListener {
            val input = EditText(this)
            val alertDialog =
                AlertDialog.Builder(this@InsideRoomActivity).create()
            alertDialog.setTitle("Notatka o przedmiocie:")
            alertDialog.setView(input)
            alertDialog.setButton(
                AlertDialog.BUTTON_POSITIVE, "Zapisz"
            ) { dialog, _ -> DataHolder.tempItems[DataHolder.currentID].Note =
                input.text.toString()
            }
            alertDialog.setButton(
                AlertDialog.BUTTON_NEUTRAL, "Anuluj"
            ) { dialog, _ -> dialog.dismiss() }
            alertDialog.show()
        }

        nextButton.setOnClickListener {
            if(DataHolder.tempItems[DataHolder.currentID].Note.isNullOrEmpty()){
                DataHolder.tempItems[DataHolder.currentID].Note = ""
            }
            val updateItemTask = UpdateItem(DataHolder.tempItems[DataHolder.currentID].Row_ID,
                                            DataHolder.tempItems[DataHolder.currentID].Real_Amount,
                                            DataHolder.tempItems[DataHolder.currentID].IsChecked,
                                            DataHolder.tempItems[DataHolder.currentID].Note)
            updateItemTask.execute().get()
            DataHolder.itemsChecked++
            if(DataHolder.currentID == DataHolder.tempItems.size - 1){
                //val intent = Intent(this, RoomSelectActivity::class.java)
                finish()
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
            val input = EditText(this)
            input.inputType = InputType.TYPE_CLASS_NUMBER
            val alertDialog =
                AlertDialog.Builder(this@InsideRoomActivity).create()
            alertDialog.setTitle("Ile takich przedmiotów znajduje się w tej lokacji?")
            alertDialog.setView(input)
            alertDialog.setButton(
                AlertDialog.BUTTON_POSITIVE, "Zapisz"
            ) { dialog, _ -> DataHolder.tempItems[DataHolder.currentID].Real_Amount =
                input.text.toString().toInt()
                nextButton.isEnabled = true
            }
            alertDialog.setButton(
                AlertDialog.BUTTON_NEUTRAL, "Anuluj"
            ) { dialog, _ -> dialog.dismiss() }
            alertDialog.show()

        }

        missingItemButton.setOnClickListener {
            DataHolder.tempItems[DataHolder.currentID].IsChecked = 1
            DataHolder.tempItems[DataHolder.currentID].Real_Amount = 0
            qrButton.isEnabled = false
            amountButton.isEnabled = false
            nextButton.isEnabled = true
        }


    }

    private fun getItemQrCode(v: Item): String{
        /*
        Tutaj powinien znajdować się kod odpowiadający za odczytanie kodu QR z bloba.
         */
        return "test"
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 0 && resultCode == 0){
            DataHolder.tempItems[DataHolder.currentID].IsChecked = 1
            val missingItemButton = findViewById<Button>(R.id.MissingItemButton)
            val qrButton = findViewById<Button>(R.id.QrButton)
            val nextButton = findViewById<Button>(R.id.NextButton)
            val amountButton = findViewById<Button>(R.id.AmountButton)
            missingItemButton.isEnabled = false
            qrButton.isEnabled = false
            if (DataHolder.tempItems[DataHolder.currentID].Sys_Amount == 1) {
                nextButton.isEnabled = true
                DataHolder.tempItems[DataHolder.currentID].Real_Amount = 1
            } else {
                amountButton.isEnabled = true
            }
        }else{
            val alertDialog =
                AlertDialog.Builder(this@InsideRoomActivity).create()
            alertDialog.setTitle("Uwaga!")
            alertDialog.setMessage("Zeskanowano niepoprawny kod QR, spróbuj ponownie.")
            alertDialog.setButton(
                AlertDialog.BUTTON_NEUTRAL, "OK"
            ) { dialog, _ -> dialog.dismiss() }
            alertDialog.show()
        }
    }

    class UpdateItem(Row_ID: Int, Real_Amount: Int, IsChecked: Int, Note: String) : AsyncTask<Void, Void, String>() {
        private val req = EndPoints.URL_UPDATE_ITEM + Row_ID.toString() + "&Real_Amount=" + Real_Amount.toString() + "&IsChecked=" + IsChecked.toString() + "&Note=" + Note
        lateinit var answer : String

        override fun doInBackground(vararg params: Void?): String? {
            answer = URL(req).readText()
            return answer
        }
    }

}
