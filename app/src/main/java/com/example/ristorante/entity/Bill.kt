package com.example.ristorante.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Bill {

    @SerializedName("id") @Expose
    var id: Long = 0

    @SerializedName("completed") @Expose
    var completed: Int = 0

    @SerializedName("dateBill") @Expose
    lateinit var dateBill: String

    @SerializedName("diningTable") @Expose
    var diningTable: Long = 0

    @SerializedName("user") @Expose
    var user: Long = 0

}