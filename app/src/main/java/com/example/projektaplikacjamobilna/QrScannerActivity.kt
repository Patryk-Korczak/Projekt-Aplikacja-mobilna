package com.example.projektaplikacjamobilna

import android.app.Activity
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.SparseArray
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.isNotEmpty
import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import java.util.*


class QrScannerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_scanner)
        supportActionBar?.hide()
        val intent = intent

        var expectedValue = intent.getStringExtra("expectedValue")



        /*
        Tutaj musi znajdować się kod odpowiedzialny za czytanie kodu QR z kamery.
         */

        if("1 Warszawa ?w 98-27500" == expectedValue ){
            setResult(0, intent)
        }else{
            setResult(-2, intent)
        }

        finish()
    }

}
