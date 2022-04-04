package com.example.ristorante.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Category : Serializable{

    @SerializedName("id") @Expose
    var id: Long = 0

    @SerializedName("name") @Expose
    var name: String = ""

    @SerializedName("restaurant") @Expose
    var restaurant: Long = 0

    override fun toString(): String {
        return name
    }

}