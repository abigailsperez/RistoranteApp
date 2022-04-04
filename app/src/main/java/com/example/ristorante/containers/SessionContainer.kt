package com.example.ristorante.containers

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SessionContainer {

    @SerializedName("id") @Expose
    var id: Long = 0

    @SerializedName("dateSession") @Expose
    var dateSession: String? = null

    @SerializedName("user") @Expose
    var user: Long = 0

    @SerializedName("name") @Expose
    var name: String? = ""

}