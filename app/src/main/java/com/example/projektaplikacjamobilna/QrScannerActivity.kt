package com.example.projektaplikacjamobilna

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class QrScannerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_scanner)
        val intent = intent

        var expectedValue = intent.getStringExtra("expectedValue")
        var readValue = "1 Warszawa ?w 98-275"

        /*
        Tutaj musi znajdować się kod odpowiedzialny za czytanie kodu QR z kamery.
         */

        if(expectedValue == readValue){
            setResult(0, intent)
        }else{
            setResult(-2, intent)
        }

        finish()
    }

}