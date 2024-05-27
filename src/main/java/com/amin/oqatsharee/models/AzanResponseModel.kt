package com.amin.oqatsharee.models


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class AzanResponseModel(
    @SerializedName("result")
    val result: Result,
    @SerializedName("status")
    val status: Int // 200
) {
    data class Result(
        @SerializedName("azan_maghreb")
        val azanMaghreb: String, // ۱۸:۰۴
        @SerializedName("azan_sobh")
        val azanSobh: String, // ۰۵:۳۱
        @SerializedName("azan_zohre")
        val azanZohre: String, // ۱۲:۲۰
        @SerializedName("city")
        val city: String, // کرج
        @SerializedName("day")
        val day: String, // ۲۵
        @SerializedName("ghorob_aftab")
        val ghorobAftab: String, // ۱۷:۴۵
        @SerializedName("latitude")
        val latitude: String, // ۳۵٫۷۲۴۷۲۰۰۰
        @SerializedName("longitude")
        val longitude: String, // ۵۰٫۹۵۲۷۶۶۴۲
        @SerializedName("month")
        val month: String, // ۱۱
        @SerializedName("nime_shabe_sharie")
        val nimeShabeSharie: String, // ۲۳:۳۸
        @SerializedName("toloe_aftab")
        val toloeAftab: String // ۰۶:۵۶
    )
}