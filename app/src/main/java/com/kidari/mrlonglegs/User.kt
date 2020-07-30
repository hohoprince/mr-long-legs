package com.kidari.mrlonglegs

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User (
    var phoneNumber: String? = "",
    var name: String? = "",
    var location: String? = "",
    var payment: String? = "",
    var regDay: String? = "",
    var title: String? = ""
) {

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "phoneNumber" to phoneNumber,
            "name" to name,
            "location" to location,
            "payment" to payment,
            "regDay" to regDay,
            "title" to title
        )
    }
}