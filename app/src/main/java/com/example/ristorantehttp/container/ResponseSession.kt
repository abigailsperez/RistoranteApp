package com.example.ristorantehttp.container

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ResponseSession {
    @SerializedName("id") @Expose
    var id: Long = 0
    @SerializedName("date_session") @Expose
    var dateSession: String? = null
    @SerializedName("in_out") @Expose
    var inOut: Int = 0
    @SerializedName("user") @Expose
    var user: Long = 0
}