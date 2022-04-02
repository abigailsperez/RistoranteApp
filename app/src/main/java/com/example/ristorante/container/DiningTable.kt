package com.example.ristorante.container

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DiningTable {

    @SerializedName("id") @Expose
    var id: Long = 0

    @SerializedName("code") @Expose
    var code: String? = ""

    @SerializedName("people") @Expose
    var people: Int = 0

    @SerializedName("restaurant") @Expose
    var restaurant: Long = 0

}