package com.example.ristorante.container

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class BillMenu {

    @SerializedName("id") @Expose
    var id: Long = 0

    @SerializedName("bill") @Expose
    var bill: Long = 0

    @SerializedName("menu") @Expose
    var menu: Long = 0

    @SerializedName("quantity") @Expose
    var quantity: Long = 0
}