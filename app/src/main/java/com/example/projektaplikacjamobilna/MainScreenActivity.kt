package com.example.projektaplikacjamobilna

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Base64
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.util.isNotEmpty
import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import kotlinx.android.synthetic.main.activity_main_screen.*
import java.io.Serializable
import java.net.URL

class MainScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)
        supportActionBar?.hide()
        val buttonStart = findViewById<Button>(R.id.button2)

        //Get stock data from database
        val receiveNamesTask = GetStockNames()
        receiveNamesTask.execute().get()
        val apiAnswer = receiveNamesTask.answer

        //Transform Json answer to kotlin data
        val gson = GsonBuilder().create()
        DataHolder.myStocks = gson.fromJson<ArrayList<Stock>>(apiAnswer, object :
            TypeToken<ArrayList<Stock>>(){}.type)

        if(DataHolder.myStocks.isNullOrEmpty()){
            buttonStart.isEnabled = false
        }

        //UI
        val welcomeMessage = findViewById<TextView>(R.id.message1)
        val welcomeText = "Witaj ${DataHolder.myUser.Imie}!\nPoziom uprawnień: ${DataHolder.myUser.User_Type}"
        welcomeMessage.text = welcomeText

        val mySpinner = findViewById<Spinner>(R.id.spinner)
        val myAdapter = ArrayAdapter<Stock>(this@MainScreen, android.R.layout.simple_list_item_1, DataHolder.myStocks)
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = myAdapter





        buttonStart.setOnClickListener {
            DataHolder.selectedStock = getSelectedStock(mySpinner)
            val intent = Intent(this, QrScannerActivity::class.java)
            intent.putExtra("expectedValue", getStockQrCode(getSelectedStock(mySpinner)))
            startActivityForResult(intent, 0)

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 0 && resultCode == 0){
            val receiveItemsTask = GetItems(DataHolder.selectedStock.Stock_ID)
            receiveItemsTask.execute().get()
            val gson = GsonBuilder().create()
            val apiAnswer = receiveItemsTask.answer
            DataHolder.myItems = gson.fromJson<ArrayList<Item>>(apiAnswer, object : TypeToken<ArrayList<Item>>(){}.type)
            val intent = Intent(this, RoomSelectActivity::class.java)
            DataHolder.itemsChecked = 0
            startActivity(intent)
        }else{
            val alertDialog =
                AlertDialog.Builder(this@MainScreen).create()
            alertDialog.setTitle("Uwaga!")
            alertDialog.setMessage("Zeskanowano niepoprawny kod QR, spróbuj ponownie!")
            alertDialog.setButton(
                AlertDialog.BUTTON_NEUTRAL, "OK"
            ) { dialog, _ -> dialog.dismiss() }
            alertDialog.show()
        }
    }

    private fun getSelectedStock(v: Spinner): Stock {
        return v.selectedItem as Stock
    }

    private fun getStockQrCode(v: Stock): String{
        var filtered = DataHolder.myLocations.filter { c -> c.Location_ID == v.Location_ID }
        var decodedString = Base64.decode(filtered[0].QrCode, Base64.DEFAULT)
        var createdImage = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)

        val detectorOptions = BarcodeScannerOptions.Builder().setBarcodeFormats(com.google.mlkit.vision.barcode.Barcode.FORMAT_QR_CODE).build()
        val qrReader = BarcodeScanning.getClient(detectorOptions)

        var input = InputImage.fromBitmap(createdImage, 0)

        var valueFromDatabase = qrReader.process(input)
        while(!valueFromDatabase.isSuccessful) {

            }

        val foundValue = valueFromDatabase.result?.get(0)?.rawValue


        return if(foundValue.isNullOrBlank()){
            "ERROR"
        }else{
            foundValue
        }



    }

}

class GetStockNames : AsyncTask<Void, Void, String>() {
    private val req = EndPoints.URL_GET_STOCK_NAMES
    lateinit var answer : String

    override fun doInBackground(vararg params: Void?): String? {
        answer = URL(req).readText()
        return answer
    }
}

class GetItems(Stock_ID: Int) : AsyncTask<Void, Void, String>() {
    private val req = EndPoints.URL_GET_ITEMS + Stock_ID.toString()
    lateinit var answer : String

    override fun doInBackground(vararg params: Void?): String? {
        answer = URL(req).readText()
        return answer
    }
}


