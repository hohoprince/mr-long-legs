package com.kidari.mrlonglegs.DataClass

data class User(
    var name: String = "",
    var email: String = "",
    var phoneNumber: String = "",
    var supporter: Boolean = false,
    var pushtoken: String = "",
    var comCount: Int = 0,
    var giveUpCount: Int = 0
)