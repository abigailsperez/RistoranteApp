package com.example.ristorantehttp.model.entity


data class User(
    var id: Long,
    var email: String,
    var name: String,
    var lastname: String,
    var nickname: String,
    var password: String,
    var restaurant: Long,
    var userGroup: Long
)
