package com.example.projektaplikacjamobilna

object DataHolder{
    lateinit var myUser: User
    lateinit var myStocks: ArrayList<Stock>
    lateinit var myItems: ArrayList<Item>
    lateinit var selectedStock: Stock
    lateinit var myRooms: ArrayList<Room>
    lateinit var myLocations: ArrayList<Location>
    lateinit var selectedRoom: Room
    lateinit var tempItems: ArrayList<Item>
    var currentID = 0
    var itemsChecked = 0
}