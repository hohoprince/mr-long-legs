package com.kidari.mrlonglegs

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Errand (
    var email: String? = "",
    var phoneNumber: String? = "",
    var location: String? = "",
    var address: String? = "",
    var payment: String? = "",
    var regDay: String? = "",
    var dueDay: String? = "",
    var content: String? = "",
    var title: String? = "",
    var urgencyDegree: String? = "",
    var category: String? = ""
) {

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "email" to email,
            "phoneNumber" to phoneNumber,
            "location" to location,
            "address" to address,
            "payment" to payment,
            "regDay" to regDay,
            "dueDay" to dueDay,
            "content" to content,
            "title" to title,
            "urgencyDegree" to urgencyDegree,
            "category" to category
        )
    }
}