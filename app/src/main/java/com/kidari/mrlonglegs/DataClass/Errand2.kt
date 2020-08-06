package com.kidari.mrlonglegs.DataClass

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Errand2 (
    var supName: String? = "",
    var supPhoneNumber:String? = "",
    var supNickname:String? ="",
    var selectedSex:String? ="",
    var supWorkTime:String? = ""
) {

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "supName" to supName,
            "supPhoneNumber" to supPhoneNumber,
            "supNickName" to supNickname,
            "selectedSex" to selectedSex,
            "supWorkTime" to supWorkTime
        )
    }
}