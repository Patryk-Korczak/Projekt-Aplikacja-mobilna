package com.example.projektaplikacjamobilna

import java.io.Serializable

data class Item(
    var Item_ID: String,
    var Item_Name: String,
    var Invoice_Number: String,
    var Location_ID: Int,
    var Location_Name: String,
    var Group_ID: Int,
    var NetValue: Float,
    var GrossValue: Float,
    var Room_ID: Int,
    var Room_Name: String,
    var Qr_code: String
) : Serializable
