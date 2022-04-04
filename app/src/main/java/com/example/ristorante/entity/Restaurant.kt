package com.example.ristorante.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Restaurant {

    @SerializedName("id") @Expose
    var id: Long = 0

    @SerializedName("name") @Expose
    var name: Long = 0

}