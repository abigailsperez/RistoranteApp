package com.example.ristorante.container

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Menu {

    @SerializedName("id") @Expose
    var id: Long = 0

    @SerializedName("name") @Expose
    var name: String = ""

    @SerializedName("category") @Expose
    var category: Long = 0

    @SerializedName("price") @Expose
    var price: Long = 0

    @SerializedName("available") @Expose
    var available: Long = 0
}