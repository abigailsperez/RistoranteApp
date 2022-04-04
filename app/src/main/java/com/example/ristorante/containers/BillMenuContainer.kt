package com.example.ristorante.containers

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class BillMenuContainer {

    @SerializedName("id") @Expose
    var id: Long = 0

    @SerializedName("bill") @Expose
    var bill: Long = 0

    @SerializedName("menu") @Expose
    var menu: Long = 0

    @SerializedName("name") @Expose
    var name: String = ""

    @SerializedName("price") @Expose
    var price: Float = 0F

    @SerializedName("quantity")@Expose
    var quantity: Long = 0

}