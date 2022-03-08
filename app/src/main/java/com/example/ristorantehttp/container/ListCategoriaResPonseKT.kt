package com.example.ristorantehttp.container

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ListCategoriaResPonseKT {
    @SerializedName("id") @Expose
    var id: Long = 0
    @SerializedName("name") @Expose
    var name: String? = null
    @SerializedName("restaurant") @Expose
    var restaurant: Long = 0
}