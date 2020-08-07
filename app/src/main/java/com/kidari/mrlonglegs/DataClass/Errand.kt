package com.kidari.mrlonglegs.DataClass

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Errand (
    var name: String? = "",
    var email: String? = "",
    var phoneNumber: String? = "",
    var photoUrl: String? = "",
    var location: String? = "",
    var address: String? = "",
    var payment: String? = "",
    var regDay: String? = "",
    var dueDay: String? = "",
    var content: String? = "",
    var title: String? = "",
    var urgencyDegree: String? = "",
    var category: String? = "",
    var supporter:String? = null,
    var state: String = "수행 전",
    var token:String = ""
)