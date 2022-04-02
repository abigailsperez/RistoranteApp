package com.example.ristorante.container

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class User : Serializable {

    @SerializedName("id") @Expose var id: Long = 0

    @SerializedName("email") @Expose var email: String? = ""

    @SerializedName("lastname") @Expose var lastName: String? = ""

    @SerializedName("name") @Expose var name: String? = ""

    @SerializedName("nickname") @Expose var nickName: String? = ""

    @SerializedName("password") @Expose var password: String? = ""

    @SerializedName("restaurant") @Expose var restaurant: Long = 0

    @SerializedName("userGroup") @Expose var userGroup: Long = 0

}