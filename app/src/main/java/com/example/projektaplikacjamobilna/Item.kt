package com.example.projektaplikacjamobilna

import java.io.Serializable

data class Item(
    var Row_ID: Int,
    var Item_ID: String,
    var Item_Name: String,
    var Item_Image: String,
    var Qr_Code_Item: String,
    var Room_ID: Int,
    var Room_Name: String,
    var Sys_Amount: Int,
    var Real_Amount: Int,
    var IsChecked: Int,
    var Note: String,
    var Stock_ID: Int
) : Serializable

