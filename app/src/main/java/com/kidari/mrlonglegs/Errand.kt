package com.kidari.mrlonglegs

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Errand (
    var phoneNumber: String? = "",
    var location: String? = "",
    var payment: String? = "",
    var regDay: String? = "",
    var content: String? = "",
    var name: String? = "",
    var title: String? = ""
) {

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "phoneNumber" to phoneNumber,
            "location" to location,
            "payment" to payment,
            "content" to content,
            "regDay" to regDay,
            "content" to content,
            "name" to name,
            "title" to title
        )
    }
}