package com.example.ristorantehttp.container

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.text.DateFormat
import java.time.DateTimeException
import java.time.LocalDateTime

class SaveBill {

    @SerializedName("id") @Expose
    var id: Long = 0

    @SerializedName("completed") @Expose
    var completed: Int = 0

    @SerializedName("dateBill") @Expose
    lateinit var dateBill: LocalDateTime

    @SerializedName("date_Bill") @Expose
    var date_Bill: String? = ""

    @SerializedName("diningTable") @Expose
    var diningTable: Long = 0

    @SerializedName("user") @Expose
    var user: Long = 0

}