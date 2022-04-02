package com.example.ristorante.container

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

class Session {

    @SerializedName("id") @Expose
    var id: Long = 0

    @SerializedName("dateSession") @Expose
    var dateSession: String? = null

    @SerializedName("inOut") @Expose
    var inOut: Int = 0

    @SerializedName("user") @Expose
    var user: Long = 0

}