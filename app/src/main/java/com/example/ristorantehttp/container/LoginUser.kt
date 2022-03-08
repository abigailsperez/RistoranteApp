package com.example.ristorantehttp.container

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginUser {
    @SerializedName("email") @Expose var email: String? = ""

    @SerializedName("password") @Expose var password: String? = ""
}