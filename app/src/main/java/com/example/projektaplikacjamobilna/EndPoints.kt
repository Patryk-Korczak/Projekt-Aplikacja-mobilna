package com.example.projektaplikacjamobilna

object EndPoints {
    private const val URL_ROOT = "http://192.168.0.105/Asset/v1/?op=" //root address of api
    const val URL_GET_USER = URL_ROOT + "getUser&login="
    const val URL_GET_STOCK_NAMES = URL_ROOT +"getStockNames"
}