package com.kidari.mrlonglegs

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Errand (
    var phoneNumber: String? = "",
    var location: String? = "",
    var address: String? = "",
    var payment: String? = "",
    var regDay: String? = "",
    var dueDay: String? = "",
    var content: String? = "",
    var title: String? = ""
) {

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "phoneNumber" to phoneNumber,
            "location" to location,
            "address" to address,
            "payment" to payment,
            "regDay" to regDay,
            "dueDay" to dueDay,
            "content" to content,
            "title" to title
        )
    }
}