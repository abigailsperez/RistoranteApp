package com.example.ristorantehttp.container

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ListBillResPonseKT {
    @SerializedName("id") @Expose
    var id: Long = 0
    @SerializedName("date_bill") @Expose
    var dateBill: String? = ""
    @SerializedName("dining_table") @Expose
    var diningTable: Long = 0
    @SerializedName("user") @Expose
    var user: Long = 0

}