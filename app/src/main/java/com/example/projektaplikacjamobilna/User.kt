package com.example.projektaplikacjamobilna

import java.io.Serializable

data class User(
    var U_Id: Int,
    var UserLogin: String,
    var UserPassword: String,
    var User_Type: String,
    var Imie: String,
    var Nazwisko: String,
    var Data_Utworzenia: String,
    var Data_Urodzenia: String,
    var Wiek: Int
) : Serializable