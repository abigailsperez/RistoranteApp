package com.example.ristorante.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.math.RoundingMode
import java.text.DecimalFormat

class Menu {

    @SerializedName("id") @Expose
    var id: Long = 0

    @SerializedName("name") @Expose
    var name: String = ""

    @SerializedName("category") @Expose
    var category: Long = 0

    @SerializedName("price") @Expose
    var price: Float = 0F

    @SerializedName("available") @Expose
    var available: Long = 0

    override fun toString(): String {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.DOWN
        return name + ", $" + df.format(price)
    }
}