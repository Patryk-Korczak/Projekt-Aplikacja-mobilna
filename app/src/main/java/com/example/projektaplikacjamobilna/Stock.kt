package com.example.projektaplikacjamobilna


class Stock {

    var Stock_ID = 0
    var User = ""
    var Stock_Name = ""
    var Location_ID = 0
    var Creation_Date = ""
    var Date_Load_Inv = ""
    var Is_Ended = 0
    var Note = ""


    override fun toString(): String {
        return Stock_Name + " - Magazyn " + Location_ID
    }
}