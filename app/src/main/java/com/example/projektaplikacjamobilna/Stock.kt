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
        var filtered = DataHolder.myLocations.filter { c -> c.Location_ID == this.Location_ID }
        return Stock_Name + " - " + filtered[0].Location_Name
    }
}