package com.example.ristorantehttp.container

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SaveBill {
    @SerializedName("dining_table") @Expose
    var dinning_table: Long = 0

    @SerializedName("user") @Expose
    var user: Long = 0

}