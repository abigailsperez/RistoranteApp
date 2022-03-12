package com.example.ristorantehttp.container

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.text.DateFormat
import java.text.SimpleDateFormat

class ResponseBill {

    @SerializedName("id") @Expose
    var id: Long = 0
    @SerializedName("completed") @Expose
    var completed: Int = 0
    @SerializedName("dateBill") @Expose
    lateinit var dateBill: DateFormat

    @SerializedName("date_Bill") @Expose
    var date_Bill: String? = ""

    @SerializedName("dining_table") @Expose
    var dining_table: Long = 0
    @SerializedName("user") @Expose
    var user: Long = 0
}