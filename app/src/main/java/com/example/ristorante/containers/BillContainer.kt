package com.example.ristorante.containers

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class BillContainer {

    @SerializedName("id") @Expose
    var id: Long = 0

    @SerializedName("dateBill") @Expose
    var dateBill: String = ""

    @SerializedName("diningTable") @Expose
    var diningTable: Long = 0

    @SerializedName("code") @Expose
    var code: String = ""

    @SerializedName("user") @Expose
    var user: Long = 0

    @SerializedName("completed") @Expose
    var completed: Int = 0

}